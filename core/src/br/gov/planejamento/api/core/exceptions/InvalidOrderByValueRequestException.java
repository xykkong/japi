package br.gov.planejamento.api.core.exceptions;

import java.util.List;

import br.gov.planejamento.api.core.constants.Errors;
import br.gov.planejamento.api.core.utils.StringUtils;

public class InvalidOrderByValueRequestException extends RequestException {

	private static final long serialVersionUID = 8450986921231387097L;

	public InvalidOrderByValueRequestException(String value, List<String> availableOrderByValues) {
		super(Errors.ORDER_BY_INVALIDO, "Valor de order by'"+ value +"' não está disponível.\n"
				+ "Valores disponíveis nesta URI: "+ StringUtils.join(", ", availableOrderByValues));
	}
}
