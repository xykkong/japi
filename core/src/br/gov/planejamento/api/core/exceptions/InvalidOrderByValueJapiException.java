package br.gov.planejamento.api.core.exceptions;

import java.util.List;

import br.gov.planejamento.api.core.utils.StringUtils;

public class InvalidOrderByValueJapiException extends JapiException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8266163582633316557L;

	public InvalidOrderByValueJapiException(String value,
			List<String> availableOrderByValues) {
		super("Valor de order by'"+ value +"' não está disponível.\n"
				+ "Valores disponíveis nesta URI: "+ StringUtils.join(", ", availableOrderByValues));
		setUserShowable(true);
	}
}
