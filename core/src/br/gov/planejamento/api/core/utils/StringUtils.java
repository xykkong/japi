package br.gov.planejamento.api.core.utils;

import java.util.HashMap;
import java.util.List;

import br.gov.planejamento.api.core.constants.Errors;
import br.gov.planejamento.api.core.exceptions.ApiException;
import br.gov.planejamento.api.core.exceptions.CoreException;

public class StringUtils {

	public static String join(String separator, List<String> list) {
		if (list.size() > 0) {
			StringBuilder join = new StringBuilder();
			for (String n : list) {
				join.append(n.trim());
				join.append(separator);
			}
			for (int i = 0; i < separator.length(); i++)
				join.deleteCharAt(join.length() - 1);

			return join.toString();
		}
		return "";
	}

	public static String removeLeftZero(String number) {
		StringBuilder response = new StringBuilder("");
		boolean foundNotZeroChar = false;
		for (int i = 0; i < number.length(); i++) {
			char c = number.charAt(i);
			if (foundNotZeroChar |= (c != '0')) {
				response.append(c);
			}
		}
		return response.toString();
	}

	public static String lastSplitOf(String text, String regex) {
		String[] segments = text.split(regex);
		if (segments.length <= 1) {
			return "";
		}
		return segments[segments.length - 1];
	}

	public static String capitalize(String word) {
		return Character.toUpperCase(word.charAt(0)) + word.substring(1);
	}
	
	public static HashMap<String, String> jsonListToHashMap(String jsonList) throws ApiException {
		try {
			HashMap<String, String> map = new HashMap<String,String>();
			String[] entries = jsonList.split(",");
			
			for(String entry : entries) {
				String[] property = entry.split(":");
				map.put(property[0], property[1]);
			}
			
			return map;
		} catch(Exception e) {
			throw new CoreException(Errors.STRING_UTILS_ERRO_CONVERSAO_HASHMAP_JSON, "Houve um erro ao parsear a string json informada em HashMap", e);
		}
	}
	
	public static String urlPartEndingWithSlash(String...parts){
		if(parts.length ==0)
			throw new IllegalArgumentException("A função StringUtils.urlPartEndingWithSlash deve possuir pelo menos um argumento");
		StringBuilder result = new StringBuilder("");
		for(String part : parts){
			char last = part.charAt(part.length() -1);
			System.out.println(part);
			System.out.println(last);
			result.append(part);
			if(last!='/'){
				result.append('/');
			}
			System.out.println(result.toString());
		}
		return result.toString();
	}
}
