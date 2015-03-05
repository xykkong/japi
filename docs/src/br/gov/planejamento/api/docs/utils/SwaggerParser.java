package br.gov.planejamento.api.docs.utils;

import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;

import br.gov.planejamento.api.common.constants.CommonConstants;
import br.gov.planejamento.api.core.base.Session;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class SwaggerParser {
	
	public static DocumentationObject parse(String modulo){
		String docUrl = "http://" + Session.getCurrentSession().getRootURL() + modulo + CommonConstants.URIConstants.VERSION + "/" + modulo + "docs";
		System.out.println("A url para a documentação é " + docUrl);
		
		try{
            Reader reader = new InputStreamReader(new URL(docUrl+".json").openStream()); //Read the json output
            Gson gson = new GsonBuilder().create();
            DocumentationObject obj = gson.fromJson(reader, DocumentationObject.class);
            return obj;
        }catch(Exception e){
            System.out.println(e);

        	return null;
        }

	}
	
	
}
