package br.gov.planejamento.api.core.exceptions;

import br.gov.planejamento.api.core.database.DatabaseAlias;

public class AliasAlreadyDefinedException extends Exception {
	public AliasAlreadyDefinedException(DatabaseAlias alias){
		super("O alias { dbName:'"+alias.getDbName()+"', uriName: '"+alias.getUriName()+"' } não pôde ser definido.\n"
				+ "Foi passado na uri o parâmetro de nome igual ao dbName.");
	}
}
