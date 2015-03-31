package br.gov.planejamento.api.core.exceptions;

import br.gov.planejamento.api.core.database.DatabaseAlias;

public class AliasAlreadyDefinedRequestException extends RequestException {
	
	public AliasAlreadyDefinedRequestException(DatabaseAlias alias){
		super("O alias { dbName:'"+alias.getDbName()+"', uriName: '"+alias.getUriName()+"' } não pôde ser definido.\n"
				+ "Foi passado na uri o parâmetro de nome igual ao dbName.");
	}
}
