package br.gov.planejamento.api.core.base;


public class LinkProperty extends Property {	
	
	protected Link link = new Link();
	
	/**
	 * 
	 * @param name
	 * @param value
	 * @param href
	 * @param rel
	 */
	public LinkProperty(String name, String value,  String href, String rel) {
		super(name, value);
		this.link.setHref(href);
		this.link.setTitle(name);
		this.link.setRel(rel);
	}	
	
	public void setLink(Link link) {
		this.link = link;
	}
	
	public Link getLink() {
		return link;
	}
	
}
