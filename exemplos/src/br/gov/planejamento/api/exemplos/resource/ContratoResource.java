package br.gov.planejamento.api.exemplos.resource;

import br.gov.planejamento.api.commons.routers.ExemplosRouter;
import br.gov.planejamento.api.core.base.Property;
import br.gov.planejamento.api.core.base.Resource;
import br.gov.planejamento.api.core.base.SelfLink;
import br.gov.planejamento.api.core.database.DataRow;
import br.gov.planejamento.api.core.exceptions.ApiException;
import br.gov.planejamento.api.core.parameters.DateParam;

public class ContratoResource extends Resource {
	private String descricao;
	private String id_contrato;
	private String status;
	private String data_termino;
	private String valor_inicial;
	/*JOIN*/
	//private String id_contratante;
	
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
	
	public Property<String> getDescricao() {
		return new Property<String>("Descrição de um contrato", descricao);
	}

	
	public Property<Integer> getId_contrato() {
		return new Property<Integer>("Chave única do contrato", id_contrato);
	}


	public Property<Boolean> getStatus() {
		return new Property<Boolean>("Fator booleano que determina se um contrato está ativo ou não", status);
	}

	
	public Property<DateParam> getData_termino() {
		return new Property<DateParam>("Data de término do contrato", data_termino);
	}

	
	public Property<Float> getValor_inicial() {
		return new Property<Float>("Valor inicial do contrato", valor_inicial);
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
		String url = ExemplosRouter.getRouter().urlTo(ExemplosRouter.CONTRATOS, "id", id_contrato);
		return new SelfLink(url,  "Contrato de numero: " + this.getId_contrato());
	}
	

}