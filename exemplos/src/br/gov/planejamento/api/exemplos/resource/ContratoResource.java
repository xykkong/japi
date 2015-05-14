package br.gov.planejamento.api.exemplos.resource;

import java.util.HashMap;

import br.gov.planejamento.api.commons.routers.ExemplosRouter;
import br.gov.planejamento.api.core.annotations.Type;
import br.gov.planejamento.api.core.base.Property;
import br.gov.planejamento.api.core.base.Resource;
import br.gov.planejamento.api.core.base.SelfLink;
import br.gov.planejamento.api.core.database.DataRow;
import br.gov.planejamento.api.core.exceptions.ApiException;

public class ContratoResource extends Resource {
	private String descricao;
	private String id_contrato;
	private String status;
	private String data_termino;
	private String valor_inicial;
	/*JOIN*/
	//private String id_contratante;
	
	private ExemplosRouter contratosRouter = new ExemplosRouter();
	
	public ContratoResource(DataRow contrato) {
		super(contrato);
		this.descricao = contrato.get("descricao");
		this.id_contrato = contrato.get("id_contrato");
		this.status= contrato.get("status");
		this.data_termino = contrato.get("data_termino");
		this.valor_inicial= contrato.get("valor_inicial");
		/*JOIN*/
		//this.id_contratante = contrato.get("id_contratante");		
	}
	
	public Property getDescricao() {
		return new Property("Descrição de um contrato", descricao);
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	@Type("integer")
	public Property getId_contrato() {
		return new Property("Chave única do contrato", id_contrato);
	}

	public void setId_contrato(String id_contrato) {
		this.id_contrato = id_contrato;
	}

	@Type("boolean")
	public Property getStatus() {
		return new Property("Fator booleano que determina se um contrato está ativo ou não", status);
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Type("datetime")
	public Property getData_termino() {
		return new Property("Data de término do contrato", data_termino);
	}

	public void setData_termino(String data_termino) {
		this.data_termino = data_termino;
	}
	
	@Type("float")
	public Property getValor_inicial() {
		return new Property("Valor inicial do contrato", valor_inicial);
	}

	public void setValor_inicial(String valor_inicial) {
		this.valor_inicial = valor_inicial;
	}

	
	//TODO: Acertar esse self-link;
	/*
	public Property getId_contratante() {
		return id_contratante;
	}

	public void setId_contratante(String id_contratante) {
		this.id_contratante = id_contratante;
	}
*/
	@Override
	public SelfLink getSelfLink() throws ApiException {		
		HashMap<String, String> meuMap = new HashMap<String, String>();
		meuMap.put("id",this.id_contrato);
		
		return new SelfLink(contratosRouter.urlTo(ExemplosRouter.CONTRATOS, meuMap), "Contrato de numero: " + this.getId_contrato());
	}
	

}