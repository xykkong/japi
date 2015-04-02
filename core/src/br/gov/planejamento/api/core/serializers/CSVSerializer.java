package br.gov.planejamento.api.core.serializers;

import java.io.IOException;
import java.io.StringWriter;

import au.com.bytecode.opencsv.CSVWriter;
import br.gov.planejamento.api.core.base.Property;
import br.gov.planejamento.api.core.base.Resource;
import br.gov.planejamento.api.core.base.Response;
import br.gov.planejamento.api.core.exceptions.ApiException;
import br.gov.planejamento.api.core.exceptions.CoreException;

public abstract class CSVSerializer {

	public static String fromResponse(Response response) throws ApiException {
		
		StringWriter sw = new StringWriter();
		CSVWriter writer = new CSVWriter(sw);
		int numeroColunas = response.get(0).getProperties().size();
		String[] linha = new String[numeroColunas];
		
		
		for(Resource resource : response) {
			int counter = 0;
			for(Property p : resource.getProperties()) {
				linha[counter] = p.getValue();
				counter++;
			}
			writer.writeNext(linha);
		}
		try {
			writer.close();
		} catch (IOException e) {
			throw new CoreException("Houve um erro ao serializar os dados para o formato CSV.", e);
		}
		return sw.toString();
	}
}
