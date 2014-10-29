package br.gov.planejamento.api.core.exceptions;

import java.util.List;

import br.gov.planejamento.api.core.utils.StringUtils;

public class InvalidOrderByValueException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8266163582633316557L;

	public InvalidOrderByValueException(String value,
			List<String> availableOrderByValues) {
		super("Valor de order by'"+ value +"' n�o est� dispon�vel.\n"
				+ "Valores dispon�veis nesta URI: "+ StringUtils.join(", ", availableOrderByValues));
	}
}
