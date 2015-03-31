package br.gov.planejamento.api.core.exceptions;

import java.util.List;

import br.gov.planejamento.api.core.utils.StringUtils;

public class InvalidOrderByValueRequestException extends RequestException {

	public InvalidOrderByValueRequestException(String value, List<String> availableOrderByValues) {
		super("Valor de order by'"+ value +"' não está disponível.\n"
				+ "Valores disponíveis nesta URI: "+ StringUtils.join(", ", availableOrderByValues));
	}
}
