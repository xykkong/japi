package br.gov.planejamento.api.core.utils;

import java.util.ArrayList;

public class StringUtils {

	public static String join(String separator, ArrayList<String> array) {
		if (array.size() > 0) {
			StringBuilder join = new StringBuilder();
			for (String n : array) {
				join.append(n.trim());
				join.append(separator);
			}
			join.deleteCharAt(join.length() - 1);

			return join.toString();
		}
		return "";
	}
	
	public static String removeLeftZero(String number){
		StringBuilder response = new StringBuilder("");
		boolean foundNotZeroChar = false;
		for(int i=0; i<number.length(); i++){
			char c = number.charAt(i);
			if(foundNotZeroChar |= (c!='0')){
				response.append(c);
			}
		}
		return response.toString();
	}
}
