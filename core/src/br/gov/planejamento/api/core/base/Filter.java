package br.gov.planejamento.api.core.base;
/**
 * 
 * @author matheus
 * Type of filter in a request
 *
 */
public class Filter {
	private String value;
	private String parameterName;
	private String type;
	
	public Filter(String value, String parameterName, String type){
		this.type = type;
		this.parameterName = parameterName;
		this.value = value;
	}
	
	public String getParameterName() {
		return parameterName;
	}
	
	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

}
