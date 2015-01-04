package br.unicamp.ic.anubis.mechanism.plugin;

import br.unicamp.ic.anubis.exception.AnubisException;

public class FeatureDependencyException extends AnubisException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FeatureDependencyException(String message, Exception ex) {
		super(message,ex);
	}
	
	public FeatureDependencyException(String message) {
		super(message);
	}
}
