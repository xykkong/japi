package br.gov.planejamento.api.core.base;

import java.util.ArrayList;

import br.gov.planejamento.api.core.exceptions.ApiException;
import br.gov.planejamento.api.core.utils.ReflectionUtils;


public abstract class Resource {
	public SelfLink selfLink;
	public String title = "resource";

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	/*
	public abstract SelfLink getSelfLink() throws ApiException;
	*/
	
	public SelfLink getSelfLink(){
		return selfLink;
	}
	
	public void setSelfLink(String href){
		SelfLink selflink = new SelfLink(href, title);
		this.selfLink = selflink;
		
	}

	public ArrayList<Property> getProperties() throws ApiException {
		return ReflectionUtils.getProperties(this);
	}
	
	public ArrayList<Link> getLinks() throws ApiException {
		return ReflectionUtils.getLinks(this);
	}
}
