package br.gov.planejamento.api.core.exceptions;

import java.util.Arrays;

import br.gov.planejamento.api.core.constants.Constants;
import br.gov.planejamento.api.core.utils.StringUtils;

public class InvalidOrderValueRequestException extends RequestException {

	public InvalidOrderValueRequestException(String order) {
		super("A ordem informada '"
				+ order
				+ "' não é válida.\n As ordens válidas são: "
				+ StringUtils.join(",",
						Arrays.asList(Constants.FixedParameters.VALID_ORDERS)));
	}
}
