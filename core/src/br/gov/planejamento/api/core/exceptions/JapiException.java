package br.gov.planejamento.api.core.exceptions;

public class JapiException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6597604914989085094L;
	private boolean userShowable = false;
	
	/**
	 * 
	 * @return se a mensagem da exception deve ou não ser printada para o usuário
	 */
	public boolean isUserShowable() {
		return userShowable;
	}
	
	protected void setUserShowable(boolean userShowable) {
		this.userShowable = userShowable;
	}
	
	public JapiException(Exception e){
		super(e);
	}

	public JapiException(String message) {
		super(message);
	}

}
