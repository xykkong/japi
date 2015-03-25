package br.gov.planejamento.api.core.base;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Set;

import javax.ws.rs.Path;
import javax.ws.rs.core.Application;

import org.reflections.Reflections;
import org.reflections.scanners.MethodParameterScanner;
import org.reflections.util.ClasspathHelper;

import br.gov.planejamento.api.core.annotations.About;
import br.gov.planejamento.api.core.annotations.Description;
import br.gov.planejamento.api.core.annotations.Ignore;
import br.gov.planejamento.api.core.annotations.Parameter;
import br.gov.planejamento.api.core.annotations.Returns;
import br.gov.planejamento.api.core.utils.ReflectionUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public abstract class Module extends Application {
	
	/**
	 * Extrai a documenta��o de um dado package atrav�s de Reflection, procurando por todos os m�todos
	 * de todas as classes deste package que retornem um Response e estejam anotados por @Path e @ResourceType.
	 * 
	 * � aconselh�vel que o package informado seja aquele que cont�m as classes de Request, ou seja,
	 * a camada mais externa que mapeia cada URL para seu m�todo correspondente. 
	 * @param packageName
	 * @return
	 */
	protected String extractDocumentation(String packageName) {
		Reflections reflections = new Reflections(ClasspathHelper.forPackage(packageName), new MethodParameterScanner());
		Set<Method> methods = reflections.getMethodsReturn(Response.class);
		
		JsonObject json = new JsonObject();
		JsonArray requests = new JsonArray();
		
		for(Method requestMethod : methods) {
			if(requestMethod.isAnnotationPresent(Path.class) && requestMethod.isAnnotationPresent(Returns.class)) {
				
				JsonObject request = new JsonObject();
				
				//Obtendo documenta��o do m�todo requisitado
				String requestDescription = "";
				String requestMethodName = "";
				String requestExampleQueryString = "";
				String examplePath = requestMethod.getAnnotation(Path.class).value();
				if(requestMethod.isAnnotationPresent(About.class)) {
					requestDescription = requestMethod.getAnnotation(About.class).description();
					requestMethodName = requestMethod.getAnnotation(About.class).name();
					requestExampleQueryString = requestMethod.getAnnotation(About.class).exampleQuery();
					request.addProperty("example_query_string",examplePath+requestExampleQueryString);
					request.addProperty("method_name", requestMethodName);
					request.addProperty("description", requestDescription);
				}
				
				String requestPath = requestMethod.getAnnotation(Path.class).value();
				request.addProperty("path", requestPath);
				String returnsResource = requestMethod.getAnnotation(Returns.class).resource().getSimpleName();
				Boolean returnsIsList = requestMethod.getAnnotation(Returns.class).isList();
				JsonObject returns = new JsonObject();
				returns.addProperty("resource", returnsResource);
				returns.addProperty("isList", returnsIsList);
				request.add("returns", returns);
				
				//Obtendo informa��es dos par�metros do m�todo
				JsonArray parameters = new JsonArray();
				Class<?> paramTypes[] = requestMethod.getParameterTypes();
				int i = 0;
				for(Annotation[] annotations : requestMethod.getParameterAnnotations()) {
					for(Annotation annotation : annotations) {
						if(annotation.annotationType().equals(Parameter.class)) {
							JsonObject paramObject = new JsonObject();	
							Parameter parameter = (Parameter) annotation;
							paramObject.addProperty("name", parameter.name());
							paramObject.addProperty("description", parameter.description());
							paramObject.addProperty("type", paramTypes[i].getSimpleName());
							paramObject.addProperty("required", parameter.required());
							parameters.add(paramObject);
						}
					}
					i++;
				}
				request.add("parameters", parameters);
				
				
				//Otendo informa��es do retorno do m�todo
				JsonArray properties = new JsonArray();
				Class<? extends Object> resourceType = requestMethod.getAnnotation(Returns.class).resource();
				for(Method propertyMethod : resourceType.getMethods()) {
					if(propertyMethod.getReturnType().equals(Property.class) && !propertyMethod.isAnnotationPresent(Ignore.class)) {
												
						String propertyName = ReflectionUtils.getterToPropertyName(propertyMethod.getName());
						String propertyDescription = "";
						if(propertyMethod.isAnnotationPresent(Description.class)) {
							propertyDescription = propertyMethod.getAnnotation(Description.class).value();
						}
										
						JsonObject property = new JsonObject();
						property.addProperty("name", propertyName);
						property.addProperty("description", propertyDescription);
						properties.add(property);
					}					
				}
				request.add("properties", properties);
				
				requests.add(request);
			}
		}
		
		json.add("requests", requests);
		
		return json.toString();
	}
	
	public abstract String getDocs();
}
