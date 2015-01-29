package br.gov.planejamento.api.core.base;

public class SelfLink extends Link {

	public SelfLink() {
		super();
		this.setRel("self");
	}
	
	public SelfLink(String href) {
		this();
		this.setHref(href);
	}
	
	public SelfLink(String href, String title) {
		this(href);
		this.setTitle(title);
	}
}
