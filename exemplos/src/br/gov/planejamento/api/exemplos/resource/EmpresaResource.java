package br.gov.planejamento.api.exemplos.resource;

import br.gov.planejamento.api.commons.routers.ExemplosRouter;
import br.gov.planejamento.api.core.base.Property;
import br.gov.planejamento.api.core.base.Resource;
import br.gov.planejamento.api.core.base.SelfLink;
import br.gov.planejamento.api.core.database.DataRow;

public class EmpresaResource extends Resource {
	private String nome,id_empresa,descricao,representante_legal,cnpj;
	
	public EmpresaResource(DataRow contrato) {
		super(contrato);
		this.descricao = contrato.get("descricao");
		this.id_empresa = contrato.get("id_empresa");
		this.nome= contrato.get("nome");
		this.representante_legal = contrato.get("representante_legal");
		this.cnpj= contrato.get("cnpj");		
	}
	
	

	@Override
	public SelfLink getSelfLink() {
		 return new SelfLink(ExemplosRouter.EMPRESAS + this.id_empresa, ""+ this.getNome());
	}

	public Property<String> getNome() {
		return new Property<String>("Nome da empresa", nome);
	}


	public Property<String> getId_empresa() {
		return new Property<String>("Identificador único",id_empresa);
	}

	public Property<String> getDescricao() {
		return new Property<String>("Descricao da empresa",descricao);
	}

	public Property<String> getRepresentante_legal() {
		return new Property<String>("Nome do representante legal",representante_legal);
	}

	public Property<String> getCnpj() {
		return new Property<String>("CNPJ da empresa",cnpj);
	}
}