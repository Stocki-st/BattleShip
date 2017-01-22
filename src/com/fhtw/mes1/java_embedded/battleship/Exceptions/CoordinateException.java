package com.fhtw.mes1.java_embedded.battleship.Exceptions;

/**
 * @author stocki
 */
public class CoordinateException extends Exception {
	private static final long serialVersionUID = 183183143156801368L;

	/**
	 * Creates an instance of the exeption.
	 * @param message The descriptive error message.
	 */
	public CoordinateException(String msg) {
		super(msg);
	}
}
