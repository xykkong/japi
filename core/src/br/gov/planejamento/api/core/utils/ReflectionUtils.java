package br.gov.planejamento.api.core.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import br.gov.planejamento.api.core.annotations.CSVIgnore;
import br.gov.planejamento.api.core.annotations.HTMLIgnore;
import br.gov.planejamento.api.core.annotations.Ignore;
import br.gov.planejamento.api.core.annotations.JSONIgnore;
import br.gov.planejamento.api.core.annotations.XMLIgnore;
import br.gov.planejamento.api.core.base.Link;
import br.gov.planejamento.api.core.base.Property;
import br.gov.planejamento.api.core.base.Session;

import com.google.common.base.CaseFormat;

public abstract class ReflectionUtils {

	public static ArrayList<Method> getFilteredMethods(Object object) {
		Session currentSession = Session.getCurrentSession();
		
		Method allMethods[] = object.getClass().getMethods();
		ArrayList<Method> methods = new ArrayList<Method>();
		for(Method method : allMethods) {
			Boolean ignore = false;
			if(currentSession.isHTML()) {
				ignore = (method.getAnnotation(HTMLIgnore.class) != null);
			} else if(currentSession.isJSON()) {
				ignore = (method.getAnnotation(JSONIgnore.class) != null);
			} else if(currentSession.isXML()) {
				ignore = (method.getAnnotation(XMLIgnore.class) != null);
			} else if(currentSession.isCSV()) {
				ignore = (method.getAnnotation(CSVIgnore.class) != null);
			} else {
				ignore = (method.getAnnotation(Ignore.class) != null);
			}
			
			if(ignore) continue;
			
			methods.add(method);
		}
		
		return methods;
	}
	
	public static ArrayList<Property> getProperties(Object object) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		ArrayList<Property> properties = new ArrayList<Property>();
		
		for(Method method : ReflectionUtils.getFilteredMethods(object)) {
			if(Property.class.isAssignableFrom(method.getReturnType())) {
				Property property = (Property) method.invoke(object);
				
				//Setting id for the properties, based on getter name (getPropertyName => property_name)
				String methodName = method.getName();				
				String id = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, methodName);
				id = id.substring(id.indexOf('_')+1);
				property.setId(id);
				
				properties.add(property);
			}
		}
		
		return properties;
	}
	
	public static ArrayList<Link> getLinks(Object object) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		ArrayList<Link> links = new ArrayList<Link>();
		
		for(Method method : ReflectionUtils.getFilteredMethods(object)) {
			if(Link.class.isAssignableFrom(method.getReturnType())) {
				links.add((Link) method.invoke(object));
			}
		}
		
		return links;
	}
}
