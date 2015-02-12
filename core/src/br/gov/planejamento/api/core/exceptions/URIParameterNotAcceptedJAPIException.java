package br.gov.planejamento.api.core.exceptions;

public class URIParameterNotAcceptedJAPIException extends JapiException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5021204822423136795L;
	
	public URIParameterNotAcceptedJAPIException(String foundParameter){
		super("O parametro "+foundParameter+" nao eh aceito nesta URI");
		setUserShowable(true);
	}

}
