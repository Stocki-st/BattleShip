package com.fhtw.mes1.java_embedded.battleship;

/**
 * This class extends the Player class by a ConsolePlayer
 * @author stocki
 *
 */
public class ConsolePlayer extends Player {

	/**
	 * Constructor
	 * @param name
	 * @param battleField
	 */
	public ConsolePlayer(String name, BattleField battleField) {
		super(name, battleField);
	}
}
