package br.gov.planejamento.api.core.base;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
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
import br.gov.planejamento.api.core.exceptions.ApiException;
import br.gov.planejamento.api.core.exceptions.CoreException;
import br.gov.planejamento.api.core.responses.ResourceListResponse;
import br.gov.planejamento.api.core.responses.ResourceResponse;
import br.gov.planejamento.api.core.responses.SwaggerResponse;
import br.gov.planejamento.api.core.utils.ReflectionUtils;
import br.gov.planejamento.api.core.utils.StringUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public abstract class Module extends Application {
	
	/**
	 * Extrai a documentação de um dado package através de Reflection, procurando por todos os métodos
	 * de todas as classes deste package que retornem um Response e estejam anotados por @Path e @ApiRequest.
	 * 
	 * é aconselhável que o package informado seja aquele que contém as classes de Request, ou seja,
	 * a camada mais externa que mapeia cada URL para seu método correspondente. 
	 * @param packageName
	 * @return
	 * @throws CoreException 
	 */
	
	@SuppressWarnings("unchecked") //Não pode acontecer
	protected SwaggerResponse extractDocumentation(String packageName,
			String requestClassPath) throws ApiException {
		Reflections reflections = new Reflections(ClasspathHelper.forPackage(packageName), new MethodAnnotationsScanner());
		Set<Method> methods = reflections.getMethodsAnnotatedWith(ApiRequest.class);
		
		
		JsonObject swaggerJson = new JsonObject();
		JsonArray requests = new JsonArray();
		
		swaggerJson.addProperty("apiVersion", "1.2.0: The Great Bug of Docs");
		swaggerJson.addProperty("swaggerVersion", "1.2: pois é o que estava escrito na antiga");
		
		String root = RequestContext.getContext().getRootURL();
		List<Method> lm = new ArrayList<>(methods);
		String classModule = lm.get(0).getDeclaringClass().getAnnotation(br.gov.planejamento.api.core.annotations.ApiModule.class).value();
		
		swaggerJson.addProperty("basePath", root+classModule);
		
		for(Method requestMethod : methods) {
			
			if(validaMetodo(requestMethod)) {
				continue;
			}
			
			JsonObject request = new JsonObject();				
			
			if(requestMethod.isAnnotationPresent(ApiRequest.class)) {
				adicionaInformacoesDoMetodoNoRequest(root, classModule, requestMethod,
						request);
			}
			
			String requestPath = requestMethod.getAnnotation(Path.class).value();
			request.addProperty("path", requestPath);
			
			Class<? extends Resource> returnType = ((Class<? extends Resource>)((ParameterizedType)requestMethod.getGenericReturnType()).getActualTypeArguments()[0]);
			
			JsonObject returns = new JsonObject();
			returns.addProperty("resource", returnType.getSimpleName());
			returns.addProperty("response_type", requestMethod.getReturnType().getSimpleName());
			request.add("returns", returns);
			
			request.add("parameters", informacoesDosParametrosDoMetodo(requestMethod));
			
			request.add("properties", informacoesDeRetornoDoMetodo(returnType));
			
			requests.add(request);
		}
		
		swaggerJson.add("requests", requests);
		
		
		return new SwaggerResponse(swaggerJson);
	}

	@SuppressWarnings("unchecked")
	private JsonArray informacoesDeRetornoDoMetodo(Class<? extends Resource> returnType) {
		JsonArray properties = new JsonArray();
		for(Method propertyMethod : returnType.getMethods()) {
			if(propertyMethod.getReturnType().equals(Property.class) && !propertyMethod.isAnnotationPresent(Ignore.class)) {
										
				String propertyName = ReflectionUtils.getterToPropertyName(propertyMethod.getName());
				String propertyDescription = "";
				Class<? extends Object> propertyType;
				
				if(propertyMethod.isAnnotationPresent(Description.class)) {
					propertyDescription = propertyMethod.getAnnotation(Description.class).value();
				}
				
				try {
					propertyType = ((Class<? extends Object>)((ParameterizedType)propertyMethod.getGenericReturnType()).getActualTypeArguments()[0]);
				} catch(Exception e) {
					propertyType = String.class;
				}
								
				JsonObject property = new JsonObject();
				property.addProperty("name", propertyName);
				property.addProperty("description", propertyDescription);
				property.addProperty("type", propertyType.getSimpleName());
				properties.add(property);
			}					
		}
		return properties;
	}

	private JsonArray informacoesDosParametrosDoMetodo(Method requestMethod) {
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
		return parameters;
	}

	private void adicionaInformacoesDoMetodoNoRequest(String root, String classModule,
			Method requestMethod, JsonObject requestQueRecebeInfo) {
		String requestDescription = "";
		String requestMethodName = "";
		String requestExampleQueryString = "";
		String[] requestExampleParams;
		requestDescription = requestMethod.getAnnotation(ApiRequest.class).description();
		requestMethodName = requestMethod.getAnnotation(ApiRequest.class).name();
		
		//Só insere uma example_query_string caso a @Module for definida no Request
		if(requestMethod.getDeclaringClass().isAnnotationPresent(br.gov.planejamento.api.core.annotations.ApiModule.class)){
			String examplePath = requestMethod.getAnnotation(Path.class).value();
			
			requestExampleQueryString = requestMethod.getAnnotation(ApiRequest.class).exampleQuery();
			requestExampleParams = requestMethod.getAnnotation(ApiRequest.class).exampleParams();
			
			if(requestExampleParams.length > 0){
				String examplePathWithParams = examplePath;
				for(String exampleParam : requestExampleParams) {
					examplePathWithParams = examplePathWithParams.replaceFirst("\\{[^{}]+\\}", exampleParam);
				}
				requestQueRecebeInfo.addProperty("example_url",StringUtils.urlPartEndingWithSlash(root, classModule, examplePathWithParams));
			}
			else{
				requestQueRecebeInfo.addProperty("example_url", StringUtils.urlPartEndingWithSlash(root, classModule, examplePath, requestExampleQueryString));
			}

			requestQueRecebeInfo.addProperty("path_filterless", StringUtils.urlPartEndingWithSlash(root, classModule, examplePath ));
		}
		
		requestQueRecebeInfo.addProperty("method_name", requestMethodName);
		requestQueRecebeInfo.addProperty("description", requestDescription);
	}

	/**
	 * 
	 * @param requestMethod
	 * @return Validando método (Se possui annotation Path e se retorna ResourceResponse ou ResourceListResponse)
	 */
	private boolean validaMetodo(Method requestMethod) {
		return !requestMethod.isAnnotationPresent(Path.class) ||
				!(requestMethod.getReturnType().isAssignableFrom(ResourceResponse.class) ||
						requestMethod.getReturnType().isAssignableFrom(ResourceListResponse.class));
	}
	
	protected SwaggerResponse extractDocumentation(String string) throws ApiException {
		return extractDocumentation(string, null);
	}
	
	public abstract SwaggerResponse getDocs() throws ApiException;
	public abstract SwaggerResponse getDocs(String requestClassPath) throws ApiException;
}
