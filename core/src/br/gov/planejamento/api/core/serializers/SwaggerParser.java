package br.gov.planejamento.api.core.serializers;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

import br.gov.planejamento.api.core.constants.Errors;
import br.gov.planejamento.api.core.exceptions.ApiException;
import br.gov.planejamento.api.core.exceptions.CoreException;
import br.gov.planejamento.api.core.responses.DocumentationResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class SwaggerParser {
	
	public static DocumentationResponse parse(String docUrl) throws ApiException {
		
		System.out.println("A url para a documentação é " + docUrl);
		
        Reader reader;
		try {
			reader = new InputStreamReader(new URL(docUrl).openStream());
		} catch (IOException e) {
			throw new CoreException(Errors.SWAGGER_PARSER_ERRO_CRIACAO_JSON, "Houve um erro ao criar o arquivo JSON do SwaggerParser.", e);
		} //Read the json output
        Gson gson = new GsonBuilder().create();
        DocumentationResponse obj = gson.fromJson(reader, DocumentationResponse.class);
        return obj;

	}
	
	/**
	 * ITERAR pelo JSON recebido e ir pegando as informações e concatenando na docmentationResponse
	 * criar um parse diferente pra essa documentação 'root'
	 */
	
	
}
