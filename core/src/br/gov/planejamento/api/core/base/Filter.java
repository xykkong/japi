package br.gov.planejamento.api.core.base;

import java.util.ArrayList;


public abstract class Filter {

	public abstract String getStatement();
	public abstract ArrayList<String> getValues();
}
