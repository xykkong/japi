package br.gov.planejamento.api.core.base;

import java.util.HashMap;
import java.util.Map.Entry;

import br.gov.planejamento.api.core.exceptions.ApiException;
import br.gov.planejamento.api.core.exceptions.CoreException;

public abstract class Router {
	
	protected String modulePath;
	
	protected Router(String modulePath) {
		this.modulePath = modulePath;
	}
	
	/**
	 * Gera uma URL completa para um determinado path, utilizando como parâmetros o conjunto de chaves-valor passados no segundo argumento.
	 * Quando um parâmetro é passado com o nome de uma variável de path (/{param}, por exemplo), o valor é substituído nessa posição do Path.
	 * Já se não há uma variável de path com o nome passado, o par chave-valor será concatenado à querystring.
	 * @param pathName
	 * @param params
	 * @return
	 * @throws ApiException
	 */
	public String urlTo(String path, HashMap<String, String> params) throws ApiException {
		
		StringBuilder urlBuilder = new StringBuilder(RequestContext.getContext().getRootURL());
		
		urlBuilder.append(modulePath + "/");
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
			throw new CoreException("Não foi possível criar a URL para o path informado pois não foram passados os path params necessários para sua construção.");
		}
		
		return urlBuilder.toString();
	}
	
	/**
	 * Gera uma URL completa para um determinado path, utilizando como parâmetros os params passados a partir do segundo argumento.
	 * Quando um parâmetro é passado com o nome de uma variável de path (/{param}, por exemplo), o valor é substituído nessa posição do Path.
	 * Já se não há uma variável de path com o nome passado, o par chave-valor será concatenado à querystring.
	 * 
	 * O formato de chamada deve ser: urlTo(path, chave1, valor1, chave2, valor2, ..., chaveN, valorN);
	 * Exemplo: ExemplosRouter.getRouter().urlTo(ExemplosRouter.EXEMPLO, "id", "123", "nome", "Exemplo");
	 * @param path
	 * @param params
	 * @return
	 * @throws ApiException
	 */
	public String urlTo(String path, String... params) throws ApiException {
		
		if(params.length % 2 != 0) {
			throw new CoreException("O número de chaves e valores passados como parâmetro no urlTo do Router precisa ser igual.");
		}
		
		HashMap<String, String> map = new HashMap<String, String>();
		for(int i = 0; i < params.length; i+=2) {
			if(map.containsKey(params[i])) {
				throw new CoreException("Não foi possível gerar a URL para o link '" + path + "' pois foram informados dois valores para um mesmo parâmetros. Verifique a chamada do método urlTo de seu Router.");
			} 
			map.put(params[i], params[i+1]);
		}
		return urlTo(path, map);
	}
}
