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


	public Property getTipoAdm() {
		return new Property("Codigo do Tipo Administrativo", tipoAdm);
	}

	public void setTipoAdm(String tipoAdm) {
		this.tipoAdm = tipoAdm;
	}
	
	public Property getAtivo(){		
		return new Property("Ativo", ativo);
	}
	
	public void setAtivo(String ativo){
		this.ativo = ativo;
	}

	@Override
	public SelfLink getSelfLink() {
		return new SelfLink("", "");
	}

}
