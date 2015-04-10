package br.gov.planejamento.api.core.base;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import br.gov.planejamento.api.core.database.DataRow;
import br.gov.planejamento.api.core.database.DatabaseData;
import br.gov.planejamento.api.core.exceptions.ApiException;
import br.gov.planejamento.api.core.exceptions.CoreException;
import br.gov.planejamento.api.core.interfaces.ILinks;
import br.gov.planejamento.api.core.interfaces.IProperties;
import br.gov.planejamento.api.core.interfaces.ISelfLink;
import br.gov.planejamento.api.core.responses.ResourceListResponse;
import br.gov.planejamento.api.core.utils.ReflectionUtils;


public abstract class Resource implements ISelfLink, ILinks, IProperties {
	public SelfLink selfLink;
	public String title = "resource";
	
	public Resource(DataRow dRow){
		
	}
	
	/*public static Resource resourceFactory(DataRow dRow, Class<? extends Resource> type ) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		return type.getConstructor(DataRow.class).newInstance(dRow);
	}
	
	public static ResourceListResponse resourceFactory(DatabaseData data, Class<? extends Resource> type) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		ResourceListResponse retorno = new ResourceListResponse(data.getCount());		

        for(DataRow dRow : data) {
            retorno.add(resourceFactory(dRow, type));
        }
        
		return retorno;
	}*/
	
	
	
	@Override
	public ArrayList<Property> getProperties() throws ApiException {
		return ReflectionUtils.getProperties(this);
	}
	
	@Override
	public ArrayList<Link> getLinks() throws ApiException {
		return ReflectionUtils.getLinks(this);
	}
	
	/*
	LicitacaoResource.factory(data);
	Resource.factory();
	LicitacaoResource.factory(DatabaseData); >> for new LicitacaoResource(DataRow) <<<<<< COREEXCEPTION
	*/
}
