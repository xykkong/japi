package br.gov.planejamento.api.core.base;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import br.gov.planejamento.api.core.database.DataRow;
import br.gov.planejamento.api.core.database.DatabaseData;
import br.gov.planejamento.api.core.exceptions.ApiException;
import br.gov.planejamento.api.core.utils.ReflectionUtils;


public abstract class Resource {
	public SelfLink selfLink;
	public String title = "resource";
	
	public Resource(DataRow dRow){
		
	}

	
	public abstract SelfLink getSelfLink() throws ApiException;
	
	public ArrayList<Property> getProperties() throws ApiException {
		return ReflectionUtils.getProperties(this);
	}
	
	public ArrayList<Link> getLinks() throws ApiException {
		return ReflectionUtils.getLinks(this);
	}
	
	public static Resource resourceFactory(DataRow dRow, Class<? extends Resource> type ) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		return type.getConstructor(DataRow.class).newInstance(dRow);
	}
	
	public static Response resourceFactory(DatabaseData data, Class<? extends Resource> type) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Response retorno = new Response(data.getCount());		

        for(DataRow dRow : data) {
            retorno.add(resourceFactory(dRow, type));
        }
        
		return retorno;
	}
	
	
	
	/*
	LicitacaoResource.factory(data);
	Resource.factory();
	LicitacaoResource.factory(DatabaseData); >> for new LicitacaoResource(DataRow) <<<<<< COREEXCEPTION
	*/
}
