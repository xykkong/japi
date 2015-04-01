package br.gov.planejamento.api.core.parameters;

public class DateParam extends Param {
	String original;
	String value;
	
	public DateParam(String original) {
		super(original);
	}
	
	@Override
	public void parse(){
		String[] parts = this.original.split("/");
		if(parts.length == 3)
			this.value = parts[2]+"-"+parts[1]+"-"+parts[0];
		else this.value= this.original;
	}
	
}
