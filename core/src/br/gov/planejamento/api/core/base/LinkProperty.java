package br.gov.planejamento.api.core.base;


public class LinkProperty extends Property {
	
	protected Link link = new Link();
	
	public LinkProperty(String name, String text, String href, String rel) {
		super(name, text);
		this.link.setHref(href);
		this.link.setRel(rel);
	}
	
	public LinkProperty(String name, String text, String href, String rel, String title) {
		this(name, text, rel, href);
		this.link.setTitle(title);
	}	
	
	public void setLink(Link link) {
		this.link = link;
	}
	
	public Link getLink() {
		return link;
	}
	
}
