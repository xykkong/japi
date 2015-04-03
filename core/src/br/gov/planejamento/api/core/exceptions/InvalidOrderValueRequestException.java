package br.gov.planejamento.api.core.exceptions;

import java.util.Arrays;

import br.gov.planejamento.api.core.constants.Constants;
import br.gov.planejamento.api.core.utils.StringUtils;

public class InvalidOrderValueRequestException extends RequestException {

	private static final long serialVersionUID = 7033737635583980791L;

	public InvalidOrderValueRequestException(String order) {
		super("A ordem informada '"
				+ order
				+ "' não é válida.\n As ordens válidas são: "
				+ StringUtils.join(",",
						Arrays.asList(Constants.FixedParameters.VALID_ORDERS)));
	}
}
