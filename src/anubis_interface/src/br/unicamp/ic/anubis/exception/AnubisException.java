package br.unicamp.ic.anubis.exception;

public class AnubisException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AnubisException(String message, Exception exception) {
		super(message,exception);
	}
	
	public AnubisException(String message) {
		super(message);
	}

}
