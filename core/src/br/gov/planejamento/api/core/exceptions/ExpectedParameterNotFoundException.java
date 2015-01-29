package br.gov.planejamento.api.core.exceptions;

import java.util.List;

import br.gov.planejamento.api.core.database.DatabaseAlias;
import br.gov.planejamento.api.core.utils.StringUtils;

public class ExpectedParameterNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6809942194987514392L;

	public ExpectedParameterNotFoundException(List<DatabaseAlias> expectedParameters,
			List<String> availableParameters, List<String> missingParameters) {
		super(
				"Um ou mais parâmetros necessários não foram encontrados.\nParâmetros esperados: "
						+ StringUtils.joinDatabaseAliases(",\n", expectedParameters)
						+ ".\n\nParâmetros enviados pelo usuário: "
						+ StringUtils.join(",", availableParameters)
						+ "\n\nParâmetros que faltaram: "
						+ StringUtils.join(",", missingParameters));
	}
}
