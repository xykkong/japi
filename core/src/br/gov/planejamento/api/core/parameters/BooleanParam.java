package br.gov.planejamento.api.core.parameters;

public class BooleanParam {
	String original;
	String value;
	
	public BooleanParam(String original) {
		super();
		this.original = original;
		parse();
	}
	
	public void parse(){
		switch(original){
			case "v":
			case "t":
			case "true":
			case "verdadeiro":
			case "sim":
			case "1":
				this.value="true";
				break;
			case "f":
			case "false":
			case "falso":
			case "nao":
			case "0":
			default:
				this.value="false";
				break;
		}
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
