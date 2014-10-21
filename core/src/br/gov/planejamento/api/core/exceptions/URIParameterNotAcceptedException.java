package br.gov.planejamento.api.core.exceptions;

public class URIParameterNotAcceptedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5021204822423136795L;
	
	public URIParameterNotAcceptedException(String foundParameter){
		super("O par�metro "+foundParameter+" n�o � aceito nesta URI");
	}

}
