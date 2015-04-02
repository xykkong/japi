package br.gov.planejamento.api.docs.utils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

import br.gov.planejamento.api.commons.constants.CommonConstants;
import br.gov.planejamento.api.core.exceptions.ApiException;
import br.gov.planejamento.api.core.exceptions.CoreException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class SwaggerParser {
	
	public static DocumentationObject parse(String modulo) throws ApiException {
		String docUrl = "";
		docUrl = CommonConstants.URIConstants.BASE_URL + modulo + "/" + modulo + "docs";
		
		System.out.println("A url para a documentação é " + docUrl);
		
        Reader reader;
		try {
			reader = new InputStreamReader(new URL(docUrl+".json").openStream());
		} catch (IOException e) {
			throw new CoreException("Houve um erro ao criar o arquivo JSON do SwaggerParser.", e);
		} //Read the json output
        Gson gson = new GsonBuilder().create();
        DocumentationObject obj = gson.fromJson(reader, DocumentationObject.class);
        return obj;

	}
	
	
}
