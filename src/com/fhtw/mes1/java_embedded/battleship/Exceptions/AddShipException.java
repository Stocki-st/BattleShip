package com.fhtw.mes1.java_embedded.battleship.Exceptions;

/**
 * @author stocki
 */
public class AddShipException extends Exception {
	private static final long serialVersionUID = 2377872394244028422L;

	/**
	 * Creates an instance of the exeption.
	 * @param message The descriptive error message.
	 */
	public AddShipException(String msg) {
		super(msg);
	}
}
