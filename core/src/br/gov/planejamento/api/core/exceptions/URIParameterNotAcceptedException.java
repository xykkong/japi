package br.gov.planejamento.api.core.exceptions;

public class URIParameterNotAcceptedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5021204822423136795L;
	
	public URIParameterNotAcceptedException(String foundParameter){
		super("O parâmetro "+foundParameter+" não é aceito nesta URI");
	}

}
