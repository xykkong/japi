package br.gov.planejamento.api.core.base;

import java.util.ArrayList;

import br.gov.planejamento.api.core.exceptions.ApiException;
import br.gov.planejamento.api.core.utils.ReflectionUtils;


public abstract class Resource {
	
	public abstract SelfLink getSelfLink() throws ApiException;

	public ArrayList<Property> getProperties() throws ApiException {
		return ReflectionUtils.getProperties(this);
	}
	
	public ArrayList<Link> getLinks() throws ApiException {
		return ReflectionUtils.getLinks(this);
	}
}
