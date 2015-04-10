package br.gov.planejamento.api.core.interfaces;

import br.gov.planejamento.api.core.base.SelfLink;
import br.gov.planejamento.api.core.exceptions.ApiException;

public interface ISelfLink {
	
	/**
	 * Obtém o SelfLink para o objeto em questão
	 * @return SelfLink
	 */
	public SelfLink getSelfLink() throws ApiException;
}
