package br.gov.planejamento.api.core.base;

public class SelfLink extends Link {
	
	public SelfLink(String href, String title) {
		this.setRel("self");
		this.setHref(href);
		this.setTitle(title);
	}
	
	public String getTitle(){
		if(this.title.equals(null) || this.title.equals("")) return "Visualizar o elemento";
		return this.title;
	}
}
