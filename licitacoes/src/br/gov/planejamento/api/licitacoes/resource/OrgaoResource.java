package br.gov.planejamento.api.licitacoes.resource;

import br.gov.planejamento.api.core.base.Property;
import br.gov.planejamento.api.core.base.Resource;
import br.gov.planejamento.api.core.base.SelfLink;
import br.gov.planejamento.api.core.database.DataRow;

public class OrgaoResource extends Resource {
	
	private String tipoAdm;
	private String ativo;


	public OrgaoResource(DataRow orgao) {
		super(orgao);
		this.tipoAdm = orgao.get("codigo_tipo_adm");
		this.ativo = orgao.get("ativo");
	}


	public Property<Integer> getTipoAdm() {
		return new Property<Integer>("Codigo do Tipo Administrativo", tipoAdm);
	}
	
	public Property<Boolean> getAtivo(){		
		return new Property<Boolean>("Ativo", ativo);
	}

	@Override
	public SelfLink getSelfLink() {
		return new SelfLink("", "");
	}

}
