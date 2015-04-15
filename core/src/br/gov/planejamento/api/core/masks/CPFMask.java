package br.gov.planejamento.api.core.masks;

import br.gov.planejamento.api.core.interfaces.IMask;

/**
 * 
 * Apenas funciona se o valor, no banco, estiver no formato "11111111111"
 *
 */
public class CPFMask implements IMask {

	@Override
	public String apply(String unmaskedValue) {
		if(unmaskedValue.matches("\\d{11}")){
			String masked = unmaskedValue.replaceAll("(\\d{3})(\\d{5})(\\d{3})", "***$2***");
			return masked;
		}
		return unmaskedValue;
	}

}
