package br.gov.planejamento.api.core.masks;

import br.gov.planejamento.api.core.interfaces.IMask;
/**
 * 
 * Apenas funciona se o valor, no banco, for um date no formato "aaaa-mm-dd"
 *
 */
public class DateMask implements IMask {

	@Override
	public String apply(String unmaskedValue) {
		if(unmaskedValue.matches("(\\d{4})([-])(\\d{2})([-])(\\d{2})")){
			String masked = unmaskedValue.replaceAll("(\\d{4})([-])(\\d{2})([-])(\\d{2})", "$5/$3/$1");
			return masked;
		}
		return unmaskedValue;
	}

}
