package br.gov.planejamento.api.core.masks;

import br.gov.planejamento.api.core.interfaces.IMask;
/**
 * 
 * Apenas funciona se o valor, no banco, for numeric.
 *
 */
public class MoneyMask implements IMask {
	String moeda = "R$";
	
	/**
	 * 
	 * @param moeda
	 */
	public MoneyMask(String moeda) {
		this.moeda = moeda;
	}
	
	public MoneyMask(){
		
	}

	@Override
	public String apply(String unmaskedValue) {
		String intValue = ""+(int) (Float.parseFloat(unmaskedValue)*100);
		return moeda+intValue.substring(0, intValue.length()-2)+","+intValue.substring(intValue.length()-2, intValue.length());
	}

}
