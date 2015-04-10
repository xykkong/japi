package br.gov.planejamento.api.core.interfaces;

import java.util.ArrayList;

import br.gov.planejamento.api.core.base.Link;
import br.gov.planejamento.api.core.exceptions.ApiException;

public interface ILinks {
	
	/**
	 * Obtém todos os Links associados ao objeto em questão
	 * @return Lista de Links
	 */
	public ArrayList<Link> getLinks() throws ApiException;
}
