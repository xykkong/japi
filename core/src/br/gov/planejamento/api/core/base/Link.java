package br.gov.planejamento.api.core.base;

public class Link {

	private String title;
	private String href;
	private String rel;
	
	public Link() {}
	
	public Link(String href, String title, String rel) {
		this.href = href;
		this.title = title;
		this.rel = rel;
	}
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public String getRel() {
		return rel;
	}
	public void setRel(String rel) {
		this.rel = rel;
	}
	
	
}
