package br.gov.planejamento.api.exemplos.resource;

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
	private String cnpj_contratada;
	private String cnpj_contratante;
	
	public ContratoResource(DataRow contrato) {
		super(contrato);
		this.descricao = contrato.get("descricao");
		this.id_contrato = contrato.get("id_contrato");
		this.status= contrato.get("status");
		this.data_termino = contrato.get("data_termino");
		this.valor_inicial= contrato.get("valor_inicial");
		/*JOIN*/
		this.cnpj_contratante = contrato.get("cnpj_contratante");
		this.cnpj_contratada = contrato.get("cnpj_contratada");
	}
	
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getId_contrato() {
		return id_contrato;
	}

	public void setId_contrato(String id_contrato) {
		this.id_contrato = id_contrato;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getData_termino() {
		return data_termino;
	}

	public void setData_termino(String data_termino) {
		this.data_termino = data_termino;
	}

	public String getValor_inicial() {
		return valor_inicial;
	}

	public void setValor_inicial(String valor_inicial) {
		this.valor_inicial = valor_inicial;
	}

	public String getCnpj_contratada() {
		return cnpj_contratada;
	}

	public void setCnpj_contratada(String cnpj_contratada) {
		this.cnpj_contratada = cnpj_contratada;
	}

	public String getCnpj_contratante() {
		return cnpj_contratante;
	}

	public void setCnpj_contratante(String cnpj_contratante) {
		this.cnpj_contratante = cnpj_contratante;
	}
	
	//TODO: Acertar esse self-link;

	@Override
	public SelfLink getSelfLink() {
		 return new SelfLink("url para o meu resource", "nome do meu resource");
	}
	

}