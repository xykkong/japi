package br.gov.planejamento.api.licitacoes.resource;

import br.gov.planejamento.api.core.base.Link;
import br.gov.planejamento.api.core.base.Property;
import br.gov.planejamento.api.core.base.Resource;
import br.gov.planejamento.api.core.base.SelfLink;
import br.gov.planejamento.api.core.database.DataRow;

public class OrgaoResource extends Resource {
	
	private String tipoAdm;
	private String ativo;

	public OrgaoResource() {
		super();
	}	
	
	public OrgaoResource(DataRow orgao) {
		super();
		this.tipoAdm = orgao.get("codigo_tipo_adm");
		this.ativo = orgao.get("ativo");
	}


	public Property getTipoAdm() {
		return new Property("Codigo do Tipo Administrativo", tipoAdm);
	}

	public void setTipoAdm(String tipoAdm) {
		this.tipoAdm = tipoAdm;
	}
	
	public Property getAtivo(DataRow orgao){		
		return new Property("Ativo", ativo);
	}
	
	public void setAtivo(String ativo){
		this.ativo = ativo;
	}

	@Override
	public Link getSelfLink() {
		return new SelfLink();
	}

}
