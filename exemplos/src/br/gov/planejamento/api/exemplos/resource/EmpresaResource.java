package br.gov.planejamento.api.exemplos.resource;

import br.gov.planejamento.api.commons.routers.ExemplosRouter;
import br.gov.planejamento.api.core.annotations.Type;
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



	public Property getNome() {
		return new Property("Nome da empresa", nome);
	}



	public void setNome(String nome) {
		this.nome = nome;
	}


	@Type("integer")
	public Property getId_empresa() {
		return new Property("Identificador único",id_empresa);
	}



	public void setId_empresa(String id_empresa) {
		this.id_empresa = id_empresa;
	}



	public Property getDescricao() {
		return new Property("Descricao da empresa",descricao);
	}



	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}



	public Property getRepresentante_legal() {
		return new Property("Nome do representante legal",representante_legal);
	}



	public void setRepresentante_legal(String representante_legal) {
		this.representante_legal = representante_legal;
	}



	public Property getCnpj() {
		return new Property("CNPJ da empresa",cnpj);
	}



	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	

}