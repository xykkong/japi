package br.gov.planejamento.api.core.parameters;

public class DateParam {
	String original;
	String value;
	
	public DateParam(String original) {
		super();
		this.original = original;
		parse();
	}
	
	public void parse(){
		String[] parts = this.original.split("/");
		if(parts.length == 3)
			this.value = parts[2]+"-"+parts[1]+"-"+parts[0];
		else this.value= this.original;
	}
	
	public String getOriginal() {
		return original;
	}
	public void setOriginal(String original) {
		this.original = original;
	}
	public String getValue() {
		return value;
	}
	
}
