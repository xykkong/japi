package br.gov.planejamento.api.core.interfaces;

import br.gov.planejamento.api.core.exceptions.ApiException;

public interface ISerializable {

	/**
	 * Serializa a Response no formato XML, seguindo o padrão HAL.
	 * @return
	 */
	public abstract String toXML() throws ApiException;
	
	/**
	 * Serializa a Response no formato CSV.
	 * @return
	 */
	public abstract String toCSV() throws ApiException;
	
	/**
	 * Serializa a Response no formato JSON, seguindo o padrão HAL.
	 * @return
	 */
	public abstract String toJSON() throws ApiException;
	
	/**
	 * Serializa a Response no formato HTML, utilizando o Velocity.
	 * @return
	 */
	public abstract Object toHTML() throws ApiException;
}
