package com.fhtw.mes1.java_embedded.battleship;

import java.util.HashMap;
import com.fhtw.mes1.java_embedded.battleship.Exceptions.BattleShipGameException;
import com.fhtw.mes1.java_embedded.battleship.Exceptions.CoordinateException;
import com.fhtw.mes1.java_embedded.battleship.lib.ICoordinate;

/**
 * This class implements the game and the game related methods
 * @author stocki
 *
 */
public class Game {
	private Player Player1;
	private Player Player2;
	private final static int FIELD_SIZE_X = 10;
	private final static int FIELD_SIZE_Y = 10;
	
	private int fieldSizeX;
	private int fieldSizeY;
	boolean gameFinished;
	enum MapFieldType {
		NOTTRIED, FAILED, HIT
	}

	/**
	 * Constructor
	 */
	public Game() {
		this.setFieldSizeX(FIELD_SIZE_X);
		this.setFieldSizeY(FIELD_SIZE_Y);
	}

	/**
	 * get size of battlefield in X direction
	 * @return fieldSizeX
	 */
	public int getFieldSizeX() {
		return fieldSizeX;
	}

	/**
	 * set size of battlefield in X direction
	 * @param fieldSizeX
	 */
	public void setFieldSizeX(int fieldSizeX) {
		this.fieldSizeX = fieldSizeX;
	}

	/**
	 * get size of battlefield in Y direction
	 * @return fieldSizeY
	 */
	public int getFieldSizeY() {
		return fieldSizeY;
	}

	/**
	 * set size of battlefield in Y direction
	 * @param fieldSizeY
	 */
	public void setFieldSizeY(int fieldSizeY) {
		this.fieldSizeY = fieldSizeY;
	}

	/**
	 * set name of Player 2
	 * @param player
	 */
	public void setPlayer1(Player player) {
		this.Player1 = player;
	}

	/**
	 * set name of Player 2
	 * @param player
	 */
	public void setPlayer2(Player player) {
		this.Player2 = player;
	}

	/**
	 * get name of Player 1
	 * @return Player1
	 */
	public Player getPlayer1() {
		return this.Player1;
	}

	/**
	 * get name of Player 2
	 * @return Player2
	 */
	public Player getPlayer2() {
		return this.Player2;
	}


	/**
	 * starts the battleship game
	 * @throws BattleShipGameException
	 */
	public void start() throws BattleShipGameException {
		if ((Player1 == null) || (Player2 == null)) {
			throw new BattleShipGameException("Error: Player not set");
		}
		HashMap<ICoordinate, MapFieldType> mapPlayer1 = new HashMap<ICoordinate, MapFieldType>();
		HashMap<ICoordinate, MapFieldType> mapPlayer2 = new HashMap<ICoordinate, MapFieldType>();
		//initialise player maps
		for (int i = 1; i <= fieldSizeX; i++) {
			for (int j = 1; j <= fieldSizeY; j++) {
				mapPlayer1.put(new Coordinate(i, j), MapFieldType.NOTTRIED);
				mapPlayer2.put(new Coordinate(i, j), MapFieldType.NOTTRIED);
			}
		}
		Player1.setPlayerMap(mapPlayer1);
		Player2.setPlayerMap(mapPlayer2);
		gameFinished = false;
	}

	/**
	 * checks if the guessed coordinate was a hit
	 * @param player
	 * @param guess
	 * @return true, if it is a hit - false, if not
	 * @throws CoordinateException
	 */
	public boolean makeMove(Player player, ICoordinate guess) throws CoordinateException {
		if ((guess.getXNr() < 0) || (guess.getXNr() > fieldSizeX)) {
			throw new CoordinateException("Invalid X coordinate!");
		} else if ((guess.getYNr() < 0) || (guess.getYNr() > fieldSizeX)) {
			throw new CoordinateException("Invalid Y coordinate!");
		}
		if (player == Player1) {
			return Player2.makeGuess(guess);
		} else {
			return Player1.makeGuess(guess);
		}
	}

	/**
	 * sets gameFinished true 
	 */
	public void setGameFinished() {
		gameFinished = true;
	}

	/**
	 * delivers the name of the winner
	 * @return name of winner
	 */
	public String getWinnerName() {
		if (Player1.getHasWon()) {
			return Player1.getName();
		} else if (Player2.getHasWon()) {
			return Player2.getName();
		}
		return "no Winner";
	}
}
