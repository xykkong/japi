package br.gov.planejamento.api.core.base;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Set;

import javax.ws.rs.Path;
import javax.ws.rs.core.Application;

import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.util.ClasspathHelper;

import br.gov.planejamento.api.core.annotations.ApiRequest;
import br.gov.planejamento.api.core.annotations.Description;
import br.gov.planejamento.api.core.annotations.Ignore;
import br.gov.planejamento.api.core.annotations.Parameter;
import br.gov.planejamento.api.core.annotations.Type;
import br.gov.planejamento.api.core.exceptions.ApiException;
import br.gov.planejamento.api.core.exceptions.CoreException;
import br.gov.planejamento.api.core.responses.ResourceResponse;
import br.gov.planejamento.api.core.responses.SwaggerResponse;
import br.gov.planejamento.api.core.responses.ResourceListResponse;
import br.gov.planejamento.api.core.utils.ReflectionUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public abstract class Module extends Application {
	
	/**
	 * Extrai a documentação de um dado package através de Reflection, procurando por todos os métodos
	 * de todas as classes deste package que retornem um Response e estejam anotados por @Path e @ResourceType.
	 * 
	 * é aconselhável que o package informado seja aquele que contém as classes de Request, ou seja,
	 * a camada mais externa que mapeia cada URL para seu método correspondente. 
	 * @param packageName
	 * @return
	 * @throws CoreException 
	 */
	protected SwaggerResponse extractDocumentation(String packageName) throws ApiException {
		Reflections reflections = new Reflections(ClasspathHelper.forPackage(packageName), new MethodAnnotationsScanner());
		Set<Method> methods = reflections.getMethodsAnnotatedWith(ApiRequest.class);
		
		JsonObject json = new JsonObject();
		JsonArray requests = new JsonArray();
		
		for(Method requestMethod : methods) {
			
			//Validando método (Se possui annotation Path e se retorna ResourceResponse ou ResourceListResponse)
			if(!requestMethod.isAnnotationPresent(Path.class) ||
					!(requestMethod.getReturnType().isAssignableFrom(ResourceResponse.class) ||
							requestMethod.getReturnType().isAssignableFrom(ResourceListResponse.class))) {
				continue;
			}
			
			JsonObject request = new JsonObject();				
			
			//Obtendo documentação do método requisitado
			String requestDescription = "";
			String requestMethodName = "";
			String requestExampleQueryString = "";
			String requestExampleId = "";
			
			if(requestMethod.isAnnotationPresent(ApiRequest.class)) {
				requestDescription = requestMethod.getAnnotation(ApiRequest.class).description();
				requestMethodName = requestMethod.getAnnotation(ApiRequest.class).name();
				
				//Só insere uma example_query_string caso a @Module for definida no Request
				if(requestMethod.getDeclaringClass().isAnnotationPresent(br.gov.planejamento.api.core.annotations.ApiModule.class)){
					String root = RequestContext.getContext().getRootURL();
					String classModule = requestMethod.getDeclaringClass().getAnnotation(br.gov.planejamento.api.core.annotations.ApiModule.class).value();
					String examplePath = requestMethod.getAnnotation(Path.class).value();
					
					requestExampleQueryString = requestMethod.getAnnotation(ApiRequest.class).exampleQuery();
					requestExampleId = requestMethod.getAnnotation(ApiRequest.class).exampleId();
											
					
					if(!requestExampleId.equals("")){
						String[] pathParts = examplePath.split("\\{");
						request.addProperty("example_url",root + classModule +pathParts[0]+requestExampleId);
					}
					else{
						request.addProperty("example_url",root + classModule +examplePath+requestExampleQueryString);
					}

					request.addProperty("path_filterless", root + classModule + examplePath );
				}
				
				request.addProperty("method_name", requestMethodName);
				request.addProperty("description", requestDescription);
			}
			
			String requestPath = requestMethod.getAnnotation(Path.class).value();
			request.addProperty("path", requestPath);
			
			@SuppressWarnings("unchecked") //Nunca pode acontecer, já que os Responses são do tipo ResourceResponse ou ResourceListResponse e por definição das classes possuem argumentos que estendem Resource
			Class<? extends Resource> returnType = ((Class<? extends Resource>)((ParameterizedType)requestMethod.getGenericReturnType()).getActualTypeArguments()[0]);
			
			JsonObject returns = new JsonObject();
			returns.addProperty("resource", returnType.getSimpleName());
			returns.addProperty("response_type", requestMethod.getReturnType().getSimpleName());
			request.add("returns", returns);
			
			//Obtendo informações dos parâmetros do método
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
			
			
			//Otendo informaçães do retorno do método
			JsonArray properties = new JsonArray();
			for(Method propertyMethod : returnType.getMethods()) {
				if(propertyMethod.getReturnType().equals(Property.class) && !propertyMethod.isAnnotationPresent(Ignore.class)) {
											
					String propertyName = ReflectionUtils.getterToPropertyName(propertyMethod.getName());
					String propertyDescription = "";
					String propertyType = "string";
					if(propertyMethod.isAnnotationPresent(Description.class)) {
						propertyDescription = propertyMethod.getAnnotation(Description.class).value();
					}
					if(propertyMethod.isAnnotationPresent(Type.class)) {
						propertyType= propertyMethod.getAnnotation(Type.class).value();
					}
									
					JsonObject property = new JsonObject();
					property.addProperty("name", propertyName);
					property.addProperty("description", propertyDescription);
					property.addProperty("type", propertyType);
					properties.add(property);
				}					
			}
			request.add("properties", properties);
			
			requests.add(request);
		}
		
		json.add("requests", requests);
		
		return new SwaggerResponse(json);
	}
	
	public abstract SwaggerResponse getDocs() throws ApiException;
}
