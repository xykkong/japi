package br.gov.planejamento.api.core.serializers;

import java.io.IOException;
import java.io.StringWriter;

import au.com.bytecode.opencsv.CSVWriter;
import br.gov.planejamento.api.core.base.Property;
import br.gov.planejamento.api.core.base.Resource;
import br.gov.planejamento.api.core.exceptions.ApiException;
import br.gov.planejamento.api.core.exceptions.CoreException;
import br.gov.planejamento.api.core.responses.ErrorResponse;
import br.gov.planejamento.api.core.responses.ResourceListResponse;
import br.gov.planejamento.api.core.responses.ResourceResponse;

public abstract class CSVSerializer {

	private static String[] resourceToLinha(Resource resource) throws ApiException {
		String[] linha = new String[resource.getProperties().size()];
		int counter = 0;
		for(Property p : resource.getProperties()) {
			linha[counter] = p.getValue();
			counter++;
		}
		return linha;
	}
	
	private static void closeWriter(CSVWriter writer) throws ApiException {
		try {
			writer.close();
		} catch (IOException e) {
			throw new CoreException("Houve um erro ao serializar os dados para o formato CSV.", e);
		}
	}
	
	public static <T extends Resource> String fromResourceListResponse(ResourceListResponse<T> response) throws ApiException {
		
		if(response.isEmpty() || response.get(0) == null) return "";
		
		StringWriter sw = new StringWriter();
		CSVWriter writer = new CSVWriter(sw);
		
		for(Resource resource : response) {
			writer.writeNext(resourceToLinha(resource));
		}
		
		closeWriter(writer);
		return sw.toString();
	}
	
	public static String fromResourceResponse(ResourceResponse<?> response) throws ApiException {
		
		if(response.getResource() == null) return "";
		
		StringWriter sw = new StringWriter();
		CSVWriter writer = new CSVWriter(sw);
		
		writer.writeNext(resourceToLinha(response.getResource()));
		
		closeWriter(writer);
		return sw.toString();
	}
	
	public static String fromErrorResponse(ErrorResponse response) throws ApiException {
		
		if(response.getApiException() == null) return "";
		
		StringWriter sw = new StringWriter();
		CSVWriter writer = new CSVWriter(sw);
		
		String[] linha = new String[3];
		linha[0] = "Error";
		linha[1] = Integer.toString(response.getApiException().getHttpStatusCode());
		linha[2] = response.getApiException().getMessage();
		
		writer.writeNext(linha);
		
		closeWriter(writer);
		return sw.toString();
	}
}
