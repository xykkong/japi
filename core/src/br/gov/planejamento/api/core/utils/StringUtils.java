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
}
