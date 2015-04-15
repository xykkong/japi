package br.gov.planejamento.api.core.masks;

import br.gov.planejamento.api.core.interfaces.IMask;

/**
 * 
 * Apenas funciona se o valor, no banco, estiver no formato "11111111111111"
 *
 */
public class CNPJMask implements IMask {

	@Override
	public String apply(String unmaskedValue) {
		if(unmaskedValue.matches("\\d{14}")){
			String masked = unmaskedValue.replaceAll("(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2})", "$1.$2.$3/$4-$5");
			return masked;
		}
		return unmaskedValue;
	}

}
