package br.gov.planejamento.api.core.utils;

import java.util.List;

import br.gov.planejamento.api.core.database.DatabaseAlias;

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
	
	public static String joinDatabaseAliases(String separator, List<DatabaseAlias> list) {
		if (list.size() > 0) {
			StringBuilder join = new StringBuilder();
			for (DatabaseAlias n : list) {
				join.append("\t{ URI name: ");
				join.append(n.getUriName());
				join.append(", DB name: ");
				join.append(n.getUriName());
				join.append("}");
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
}
