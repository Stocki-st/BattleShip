package com.fhtw.mes1.java_embedded.battleship.Exceptions;

/**
 * @author stocki
 */
public class AddBattleFieldException extends Exception {
	private static final long serialVersionUID = -5777829874124065363L;

	/**
	 * Creates an instance of the exeption.
	 * @param message The descriptive error message.
	 */
  public AddBattleFieldException(String message) {
    super(message);
  }

}
