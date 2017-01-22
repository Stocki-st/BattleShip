package com.fhtw.mes1.java_embedded.battleship.Exceptions;

/**
 * @author stocki
 */
public class BattleShipGameException extends Exception {
	private static final long serialVersionUID = 3716652858823355774L;

	/**
	 * Creates an instance of the exeption.
	 * @param message The descriptive error message.
	 */
	public BattleShipGameException(String msg) {
		super(msg);
	}
}
