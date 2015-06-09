package br.gov.planejamento.api.core.base;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map.Entry;

import br.gov.planejamento.api.core.constants.Errors;
import br.gov.planejamento.api.core.exceptions.ApiException;
import br.gov.planejamento.api.core.exceptions.CoreException;

public abstract class Router {
	
	public abstract String getModulePath();
	
	public String urlTo(String pathName, HashMap<String, String> params) throws ApiException {
		
		StringBuilder urlBuilder = new StringBuilder(RequestContext.getContext().getRootURL());
		
		try {
			Field field = this.getClass().getDeclaredField(pathName);
			field.setAccessible(true);
			String path = (String) field.get(this);
			
			urlBuilder.append(getModulePath() + "/");
			urlBuilder.append(path);
			
			for(Entry<String, String> entry : params.entrySet()) {
				String key = "{" + entry.getKey() + "}";
				Integer keyIndex = urlBuilder.indexOf(key);
				
				if(keyIndex != -1) {
					urlBuilder.replace(keyIndex, keyIndex + key.length(), entry.getValue());
				} else {
					if(urlBuilder.indexOf("?") == -1) {
						urlBuilder.append("?");
					}
					urlBuilder.append(entry.getKey() + "=" + entry.getValue());
				}
			}
			
			if(urlBuilder.indexOf("{") != -1) {
				throw new CoreException(Errors.ROUTER_PARAMS_NAO_INFORMADOS,"Não foi possível criar a URL para o path informado pois não foram passados os path params necessários para sua construção.");
			}
			
			return urlBuilder.toString();
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			throw new CoreException(Errors.ROUTER_PATH_NAME_INCORRETO, "Não foi possível acessar o path desejado na construção URL. Verifique se o pathName passado está correto.", e);
		}
	}
}
