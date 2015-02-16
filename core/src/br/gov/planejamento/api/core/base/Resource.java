package br.gov.planejamento.api.core.base;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import br.gov.planejamento.api.core.utils.ReflectionUtils;


public abstract class Resource {
	
	public abstract Link getSelfLink();

	public ArrayList<Property> getProperties() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		return ReflectionUtils.getProperties(this);
	}
	
	public ArrayList<Link> getLinks() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		return ReflectionUtils.getLinks(this);
	}
}
