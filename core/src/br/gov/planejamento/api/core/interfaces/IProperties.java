package br.gov.planejamento.api.core.interfaces;

import java.util.ArrayList;

import br.gov.planejamento.api.core.base.Property;
import br.gov.planejamento.api.core.exceptions.ApiException;

public interface IProperties {
	
	/**
	 * Obtém a lista de Properties associadas ao objeto em questão	
	 * @return Lista de Properties
	 */
	public ArrayList<Property> getProperties() throws ApiException;
}
