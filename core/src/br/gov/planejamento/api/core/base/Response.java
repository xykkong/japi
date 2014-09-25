package br.gov.planejamento.api.core.base;

import java.util.ArrayList;

public class Response {

	private ArrayList<GenericModel> models;

	public Response(ArrayList<GenericModel> models) {
		this.models = models;
	}

	public ArrayList<GenericModel> getModels() {
		return models;
	}

	public String toString() {
		StringBuilder result = new StringBuilder("");
		for (GenericModel model : models) {
			result.append(model);
			result.append("\n");
		}
		return result.toString();
	}
}
