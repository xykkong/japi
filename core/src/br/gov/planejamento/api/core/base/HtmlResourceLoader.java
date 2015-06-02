package br.gov.planejamento.api.core.base;

public class HtmlResourceLoader {
	private String path;
	
	public HtmlResourceLoader(String path){
		this.path = "/br/gov/planejamento/api/commons/html/pages/"+path;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
}
