package br.gov.planejamento.api.core.base;

public class LinkProperty extends Property {
	
	protected String href;
	protected String title;
	
	public LinkProperty(String name, String text, String href) {
		super(name, text);
		this.href = href;
	}
	
	public LinkProperty(String name, String text, String href, String title) {
		this(name, text, href);
		this.title = title;
	}
	
	public String getText() {
		return this.value;
	}
	
	public void setText(String text) {
		this.value = text;
	}
	
	public void setHref(String href) {
		this.href = href;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getHref() {
		return href;
	}
	
	public String getTitle() {
		return title;
	}
}
