package br.gov.planejamento.api.exemplos.resource;

import br.gov.planejamento.api.core.annotations.Type;
import br.gov.planejamento.api.core.base.Resource;
import br.gov.planejamento.api.core.base.SelfLink;
import br.gov.planejamento.api.core.database.DataRow;

public class ContratoResource extends Resource {
	private String descricao;
	private String id_contrato;
	private String status;
	private String data_termino;
	private String valor_inicial;
	/*JOIN*/
	private String id_contratante;
	
	public ContratoResource(DataRow contrato) {
		super(contrato);
		this.descricao = contrato.get("descricao");
		this.id_contrato = contrato.get("id_contrato");
		this.status= contrato.get("status");
		this.data_termino = contrato.get("data_termino");
		this.valor_inicial= contrato.get("valor_inicial");
		/*JOIN*/
		this.id_contratante = contrato.get("id_contratante");		
	}
	
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	@Type("integer")
	public String getId_contrato() {
		return id_contrato;
	}

	public void setId_contrato(String id_contrato) {
		this.id_contrato = id_contrato;
	}

	@Type("boolean")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Type("datetime")
	public String getData_termino() {
		return data_termino;
	}

	public void setData_termino(String data_termino) {
		this.data_termino = data_termino;
	}
	
	@Type("float")
	public String getValor_inicial() {
		return valor_inicial;
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