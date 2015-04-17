package br.gov.planejamento.api.commons.masks;

import br.gov.planejamento.api.core.base.RequestContext;
import br.gov.planejamento.api.core.masks.DateMask;

public class HtmlOnlyDateMask extends DateMask {
	@Override
	public String apply(String unmaskedValue) {
		if(RequestContext.getContext().isHTML()){
			if(unmaskedValue.matches("(\\d{4})([-])(\\d{2})([-])(\\d{2})")){
				String masked = unmaskedValue.replaceAll("(\\d{4})([-])(\\d{2})([-])(\\d{2})", "$5/$3/$1");
				return masked;
			}
			return unmaskedValue;
		}
		else return unmaskedValue;	
	}
}
