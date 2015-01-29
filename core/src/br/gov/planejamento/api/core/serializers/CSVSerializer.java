package br.gov.planejamento.api.core.serializers;

import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map.Entry;

import au.com.bytecode.opencsv.CSVWriter;
import br.gov.planejamento.api.core.base.Property;
import br.gov.planejamento.api.core.base.Resource;
import br.gov.planejamento.api.core.base.Response;
import br.gov.planejamento.api.core.utils.SerializeUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public abstract class CSVSerializer {

	public static String fromResponse(Response response) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException {
		
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
		writer.close();
		return sw.toString();
	}
}
