package br.gov.planejamento.api.exemplos.resource;

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
		 return new SelfLink("url para o meu resource", "nome do meu resource");
	}



	public String getNome() {
		return nome;
	}



	public void setNome(String nome) {
		this.nome = nome;
	}



	public String getId_empresa() {
		return id_empresa;
	}



	public void setId_empresa(String id_empresa) {
		this.id_empresa = id_empresa;
	}



	public String getDescricao() {
		return descricao;
	}



	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}



	public String getRepresentante_legal() {
		return representante_legal;
	}



	public void setRepresentante_legal(String representante_legal) {
		this.representante_legal = representante_legal;
	}



	public String getCnpj() {
		return cnpj;
	}



	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	

}