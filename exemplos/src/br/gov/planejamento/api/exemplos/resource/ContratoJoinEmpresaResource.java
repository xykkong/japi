package br.gov.planejamento.api.exemplos.resource;

import br.gov.planejamento.api.core.base.Property;
import br.gov.planejamento.api.core.base.Resource;
import br.gov.planejamento.api.core.base.SelfLink;
import br.gov.planejamento.api.core.database.DataRow;

public class ContratoJoinEmpresaResource extends Resource {
	private String descricao;
	private String id_contrato;
	private String status;
	private String data_termino;
	private String valor_inicial;
	/*JOIN*/
	private String id_contratante;
	
	public ContratoJoinEmpresaResource(DataRow contrato) {
		super(contrato);
		this.descricao = contrato.get("descricao");
		this.id_contrato = contrato.get("id_contrato");
		this.status= contrato.get("status");
		this.data_termino = contrato.get("data_termino");
		this.valor_inicial= contrato.get("valor_inicial");
	}
	
	public Property<String> getDescricao() {
		return new Property<String>("Descrição de um contrato", descricao);
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public Property<Integer> getId_contrato() {
		return new Property<Integer>("Chave única do contrato", id_contrato);
	}

	public void setId_contrato(String id_contrato) {
		this.id_contrato = id_contrato;
	}

	public Property<Boolean> getStatus() {
		return new Property<Boolean>("Fator booleano que determina se um contrato está ativo ou não", status);
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public Property<String> getData_termino() {
		return new Property<String>("Data de término do contrato", data_termino);
	}

	public void setData_termino(String data_termino) {
		this.data_termino = data_termino;
	}
	
	public Property<Float> getValor_inicial() {
		return new Property<Float>("Valor inicial do contrato", valor_inicial);
	}

	public void setValor_inicial(String valor_inicial) {
		this.valor_inicial = valor_inicial;
	}

	
	//TODO: Acertar esse self-link;

	public String getId_contratante() {
		return id_contratante;
	}

	public void setId_contratante(String id_contratante) {
		this.id_contratante = id_contratante;
	}

	@Override
	public SelfLink getSelfLink() {
		 return new SelfLink("url para o meu resource", "nome do meu resource");
	}
	

}