package br.gov.planejamento.api.core.exceptions;

import java.util.Arrays;

import br.gov.planejamento.api.core.constants.Constants;
import br.gov.planejamento.api.core.utils.StringUtils;

public class InvalidOrderSQLParameterException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5358988994096896741L;

	public InvalidOrderSQLParameterException(String order) {
		super("A ordem "
				+ order
				+ " não é válida.\n As ordens válidas são: "
				+ StringUtils.join(",",
						Arrays.asList(Constants.FixedParameters.VALID_ORDERS)));
	}
}
