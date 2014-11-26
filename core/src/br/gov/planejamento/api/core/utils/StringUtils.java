package br.gov.planejamento.api.core.utils;

import java.util.List;

import javax.ws.rs.core.PathSegment;

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

	/**
	 * 
	 * @param pathSegments
	 * @return uma string concatenada de todos os segmentos menos o primeiro
	 */	
	public static String getRelativePath(List<PathSegment> pathSegments) {
		StringBuilder returnStringBuilder = new StringBuilder();
		for (int i = 1; i < pathSegments.size() - 1; i++) {
			returnStringBuilder.append(pathSegments.get(i).getPath());
		}
		returnStringBuilder.append(allButLastSplitOf(
				pathSegments.get(pathSegments.size() - 1).getPath(), "."));
		return returnStringBuilder.toString();
	}

	public static String allButLastSplitOf(String text, String regex) {
		String[] segments = text.split(regex);
		if (segments.length <= 1) {
			return "";
		}
		StringBuilder returnStringBuilder = new StringBuilder();
		for (int i = 0; i < segments.length - 1; i++) {
			returnStringBuilder.append(segments[i]);
		}
		return returnStringBuilder.toString();
	}
}
