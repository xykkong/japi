package br.gov.planejamento.api.core.serializers;

import java.io.StringWriter;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import br.gov.planejamento.api.core.base.Property;
import br.gov.planejamento.api.core.base.Resource;
import br.gov.planejamento.api.core.exceptions.ApiException;
import br.gov.planejamento.api.core.exceptions.CoreException;
import br.gov.planejamento.api.core.responses.ErrorResponse;
import br.gov.planejamento.api.core.responses.ResourceListResponse;
import br.gov.planejamento.api.core.responses.ResourceResponse;
import br.gov.planejamento.api.core.utils.SerializeUtils;

public abstract class XMLSerializer {

	private static Document newXMLDocument() throws ApiException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw new CoreException("Houve um erro ao construir o DocumentBuilder do XML.", e);
		}
		
		return builder.newDocument();	
	}
	
	private static String xmlToString(Document xml) throws ApiException {
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer;
		try {
			transformer = tf.newTransformer();
		} catch (TransformerConfigurationException e) {
			throw new CoreException("Houve um erro ao construir o Transformer do XML.", e);
		}
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		StringWriter writer = new StringWriter();
		try {
			transformer.transform(new DOMSource(xml), new StreamResult(writer));
		} catch (TransformerException e) {
			throw new CoreException("Houve um erro ao processar o Transformer do XML", e);
		}
		String output = writer.getBuffer().toString().replaceAll("\n|\r", "");
		
		return output;
	}
	
	private static Element resourceToElement(Document xml, Resource resource) throws ApiException {
		if(resource == null) return null;
		Element item = xml.createElement("resource");
		Element links = xml.createElement("_links");
		for(Element link : SerializeUtils.linksToXML(xml, resource.getLinks())) {
			links.appendChild(link);
		}
		item.appendChild(links);
		for(Property<?> property : resource.getProperties()) {
			if(property == null) continue;
			Element element = xml.createElement(property.getId());
			element.setTextContent(property.getValue());
			item.appendChild(element);
		}
		return item;
	}
	
	public static <T extends Resource> String fromResourceListResponse(ResourceListResponse<T> response) throws ApiException {
		
		Document xml = newXMLDocument();
		
		ArrayList<Element> resources = new ArrayList<Element>();	
		for(Resource resource : response) {
			resources.add(resourceToElement(xml, resource));
		}		
		
		Element root = xml.createElement("resource");
		Element links = xml.createElement("_links");
		for(Element link : SerializeUtils.linksToXML(xml, response.getLinks())) {
			links.appendChild(link);
		}
		root.appendChild(links);
		Element embedded = xml.createElement("_embedded");
		for(Element resource : resources) {
			embedded.appendChild(resource);
		}
		root.appendChild(embedded);
		xml.appendChild(root);
		
		return xmlToString(xml);
	}
	
	public static String fromResourceResponse(ResourceResponse<?> response) throws ApiException {
		
		Document xml = newXMLDocument();
		Element root = resourceToElement(xml, response.getResource());
		xml.appendChild(root);
		
		return xmlToString(xml);
	}
	
	public static String fromErrorResponse(ErrorResponse response) throws ApiException {
		
		Document xml = newXMLDocument();
		Element root = xml.createElement("error");
		
		Element status = xml.createElement("status");
		status.setTextContent(Integer.toString(response.getApiException().getHttpStatusCode()));
		
		Element mensagem = xml.createElement("mensagem");
		mensagem.setTextContent(response.getApiException().getMessage());
		
		root.appendChild(status);
		root.appendChild(mensagem);
		
		xml.appendChild(root);
		
		return xmlToString(xml);
	}
	
}
