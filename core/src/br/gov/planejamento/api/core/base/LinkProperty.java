package br.gov.planejamento.api.core.base;


public class LinkProperty extends Property {	
	
	protected Link link = new Link();
	
	public LinkProperty(String name,  String href, String title, String rel) {
		super(name);
		this.link.setHref(href);
		this.link.setTitle(title);
		this.link.setRel(rel);
	}	
	
	public void setLink(Link link) {
		this.link = link;
	}
	
	public Link getLink() {
		return link;
	}
	
}
