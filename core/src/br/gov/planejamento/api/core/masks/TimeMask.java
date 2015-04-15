package br.gov.planejamento.api.core.masks;

import br.gov.planejamento.api.core.interfaces.IMask;

public class TimeMask implements IMask {
	String hourIdentifier = "h";
	String minutesIdentifier = "min";
	String secondsIdentifier = "s";
	
	public TimeMask() {
	
	}
	
	public TimeMask(String hourIdentifier, String minutesIdentifier, String secondsIdentifier){
		this.hourIdentifier = hourIdentifier;
		this.minutesIdentifier = minutesIdentifier;
		this.secondsIdentifier = secondsIdentifier;
		
	}

	@Override
	public String apply(String unmaskedValue) {
		if(unmaskedValue.matches("(\\d{2})([:])(\\d{2})([:])(\\d{2})")){
			String masked = unmaskedValue.replaceAll("(\\d{2})([:])(\\d{2})([:])(\\d{2})", "$1"+hourIdentifier+"$3"+minutesIdentifier+"$5"+secondsIdentifier);
			return masked;
		}
		return unmaskedValue;
	}

}
