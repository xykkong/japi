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
import br.gov.planejamento.api.core.base.RequestContext;
import br.gov.planejamento.api.core.base.SelfLink;
import br.gov.planejamento.api.core.constants.Errors;
import br.gov.planejamento.api.core.exceptions.ApiException;
import br.gov.planejamento.api.core.exceptions.CoreException;

import com.google.common.base.CaseFormat;

public abstract class ReflectionUtils {
	
	/**
	 * Converte o nome de um getter camel-case de Property (formato getNomePropriedade)
	 * para o nome correto da propriedade em snake-case (formato nome_propriedade) 
	 * @param getterName
	 * @return
	 */
	public static String getterToPropertyName(String getterName) {
		String id = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, getterName);
		id = id.substring(id.indexOf('_')+1);
		return id;
	}

	public static ArrayList<Method> getFilteredMethods(Object object) {
		RequestContext context = RequestContext.getContext();
		
		Method allMethods[] = object.getClass().getMethods();
		ArrayList<Method> methods = new ArrayList<Method>();
		for(Method method : allMethods) {
			Boolean ignore = false;
			if(context.isHTML()) {
				ignore = (method.getAnnotation(HTMLIgnore.class) != null);
			} else if(context.isJSON()) {
				ignore = (method.getAnnotation(JSONIgnore.class) != null);
			} else if(context.isXML()) {
				ignore = (method.getAnnotation(XMLIgnore.class) != null);
			} else if(context.isCSV()) {
				ignore = (method.getAnnotation(CSVIgnore.class) != null);
			} else {
				ignore = (method.getAnnotation(Ignore.class) != null);
			}
			
			if(ignore) continue;
			
			methods.add(method);
		}
		
		return methods;
	}
	
	public static ArrayList<Property<?>> getProperties(Object object) throws ApiException {
		ArrayList<Property<?>> properties = new ArrayList<Property<?>>();
		
		for(Method method : ReflectionUtils.getFilteredMethods(object)) {
			if(Property.class.isAssignableFrom(method.getReturnType())) {
				//TODO testar se o metodo possui argumento(s), se sim, retornar uma getterNoResourceJapiExcepton, por exemplo
				Property<?> property;
				try {
					property = (Property<?>) method.invoke(object);
				} catch (IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e) {
					throw new CoreException(Errors.REFLECTION_ERRO_OBTENCAO_PROPRIEDADES_RESOURCE, "Houve um erro ao obter as propriedades do resource.", e);
				}
				
				//Setting id for the properties, based on getter name (getPropertyName => property_name)
				String id = getterToPropertyName(method.getName());				
				property.setId(id);
				
				properties.add(property);
			}
		}
		
		return properties;
	}
	
	public static ArrayList<Link> getLinks(Object object) throws CoreException {
		
		ArrayList<Link> links = new ArrayList<Link>();
		
		for(Method method : ReflectionUtils.getFilteredMethods(object)) {			
			if(Link.class.isAssignableFrom(method.getReturnType())) {
				if(!method.getReturnType().equals(SelfLink.class)){
					try {
						links.add((Link) method.invoke(object));
					} catch (IllegalAccessException | IllegalArgumentException
							| InvocationTargetException e) {
						throw new CoreException(Errors.REFLECTION_ERRO_OBTENCAO_LINKS_RESOURCE, "Houve um erro ao obter os links do resource.", e);
					}
				}
			}
		}
		
		return links;
	}
}
