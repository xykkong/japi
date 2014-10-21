package br.gov.planejamento.api.core.base;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;

public class DatabasePropertiesFileLoader {

	private static DatabasePropertiesFileLoader instance = null;

	private String user;
	private String password;
	private String url;
	private String port;
	private String databaseName;

	public static DatabasePropertiesFileLoader getInstance(String filename)
			throws ParserConfigurationException, SAXException, IOException {
		if (instance == null)
			instance = new DatabasePropertiesFileLoader(filename);
		return instance;
	}

	/**
	 * copiado de -
	 * http://www.mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/
	 * 
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	private DatabasePropertiesFileLoader(String filename)
			throws ParserConfigurationException, SAXException, IOException {
		File fXmlFile = new File(filename + ".xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);

		// optional, but recommended
		// read this -
		// http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
		doc.getDocumentElement().normalize();

		System.out.println("Root element :"
				+ doc.getDocumentElement().getNodeName());

		NodeList nList = doc.getElementsByTagName("property");

		System.out.println("----------------------------");

		Node userNode = nList.item(0);

		System.out.println("\nCurrent Element :" + userNode.getNodeName());

		if (userNode.getNodeType() == Node.ELEMENT_NODE) {

			Element eElement = (Element) userNode;

			System.out.println("id : " + eElement.getAttribute("id"));
			this.user = eElement.getElementsByTagName("user").item(0)
					.getTextContent();
			System.out.println("user : " + user);
			this.password = eElement.getElementsByTagName("password").item(0)
					.getTextContent();
			System.out.println("password : " + password);
		}

		Node settingsNode = nList.item(1);

		System.out.println("\nCurrent Element :" + settingsNode.getNodeName());

		if (userNode.getNodeType() == Node.ELEMENT_NODE) {

			Element eElement = (Element) settingsNode;

			System.out.println("id : " + eElement.getAttribute("id"));
			this.url = eElement.getElementsByTagName("url").item(0)
					.getTextContent();
			System.out.println("url : " + url);
			this.port = eElement.getElementsByTagName("port").item(0)
					.getTextContent();
			System.out.println("port : " + port);

			this.databaseName = eElement.getElementsByTagName("databasename")
					.item(0).getTextContent();
			System.out.println("databaseName : " + databaseName);
		}
	}

	public String getPassword() {
		return password;
	}

	public String getPort() {
		return port;
	}

	public String getUrl() {
		return url;
	}

	public String getUser() {
		return user;
	}

	public String getDatabaseName() {
		return databaseName;
	}
}
