package br.gov.planejamento.api.exemplos.resource;

import br.gov.planejamento.api.core.base.Resource;
import br.gov.planejamento.api.core.base.SelfLink;
import br.gov.planejamento.api.core.database.DataRow;
import br.gov.planejamento.api.core.exceptions.ApiException;

public class ContratoJoinResource extends Resource {

	public ContratoJoinResource(DataRow dRow) {
		super(dRow);
	}

	@Override
	public SelfLink getSelfLink() throws ApiException {
		return new SelfLink("oi", "oi oi oi");
	}

}
