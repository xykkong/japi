package br.gov.planejamento.api.core.base;

import java.util.ArrayList;

public class ResourceList extends ArrayList<Resource> {

	public String build() {
		StringBuilder sb = new StringBuilder();
		for(Resource resource : this) {
			sb.append(resource.build());
			sb.append("<hr>");
		}
		return sb.toString();
	}
}
