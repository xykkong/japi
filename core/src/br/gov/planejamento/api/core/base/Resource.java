package br.gov.planejamento.api.core.base;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import com.google.common.base.CaseFormat;

import br.gov.planejamento.api.core.annotations.CSVIgnore;
import br.gov.planejamento.api.core.annotations.HTMLIgnore;
import br.gov.planejamento.api.core.annotations.Ignore;
import br.gov.planejamento.api.core.annotations.JSONIgnore;
import br.gov.planejamento.api.core.annotations.XMLIgnore;
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
