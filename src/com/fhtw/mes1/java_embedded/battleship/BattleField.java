package com.fhtw.mes1.java_embedded.battleship;

import java.util.ArrayList;
import java.util.List;
import com.fhtw.mes1.java_embedded.battleship.Exceptions.AddShipException;
import com.fhtw.mes1.java_embedded.battleship.lib.IBattleShip;
import com.fhtw.mes1.java_embedded.battleship.lib.ICoordinate;

/**
 * This class defines a battlefield and provides methods to set the size of the battlefield, add ships, check if all ships are destroyed, ...
 * @author stocki
 *
 */
public class BattleField {
	private List<BattleShips> shipList;
	private int fieldSizeX;
	private int fieldSizeY;

	/**
	 * Constructor
	 * @param fieldSizeX
	 * @param fieldSizeY
	 */
	public BattleField(int fieldSizeX, int fieldSizeY) {
		shipList = new ArrayList<BattleShips>();
		this.fieldSizeX = fieldSizeX;
		this.fieldSizeY = fieldSizeY;
	}

	/**
	 * adds a new ship to the shipList
	 * @param leftUpperCorner
	 * @param ship
	 * @throws AddShipException
	 */
	public void addShip(ICoordinate leftUpperCorner, IBattleShip ship) throws AddShipException {
		BattleShips newShip = new BattleShips(leftUpperCorner, ship);
		//check if a ship is on this coordinate
		if (shipList.stream().anyMatch(entry -> entry.leftUpperCorner.getXNr() == newShip.leftUpperCorner.getXNr()
				&& entry.leftUpperCorner.getYNr() == newShip.leftUpperCorner.getYNr())) {
			throw new AddShipException("There is already a Ship where you wanted to place " + ship.getName());
		}
		shipList.add(newShip);
	}

	/**
	 * check if all ships are destroyed
	 * @return true if all battle ships are destroyed, false if not
	 */
	public boolean allShipsDestroyed() {
		return shipList.stream().allMatch(entry -> entry.ship.isShipDestroyed());
	}

	/**
	 * get the size of the battlefield in X direction
	 * @return fieldSizeX
	 */
	public int getBattleFieldSizeX() {
		return fieldSizeX;
	}

	/** 
	 * get the size of the battlefield in Y direction
	 * @return fieldSizeY
	 */
	public int getBattleFieldSizeY() {
		return fieldSizeY;
	}

	/**
	 * checks if a battleship was hit on the guessed coordinate
	 * @param guess coordinate where a ship is supposed
	 * @return true if a battle ship was hit, false if not
	 */
	public boolean isHitAnyBattleShip(ICoordinate guess) {
		return shipList.stream().anyMatch(entry -> entry.ship.isHit(entry.leftUpperCorner, guess));
	}

	/**
	 * set the size of the battlefield in X direction
	 * @param sizeX
	 */
	public void setBattleFieldSizeX(int sizeX) {
		this.fieldSizeX = sizeX;
	}

	/**
	 * set the size of the battlefield in Y direction
	 * @param sizeY
	 */
	public void setBattleFieldSizeY(int sizeY) {
		this.fieldSizeY = sizeY;
	}
	
	/**
	 * defines the objects in the shipList look like
	 */
	private final class BattleShips {
		private IBattleShip ship;
		private ICoordinate leftUpperCorner;

		public BattleShips(ICoordinate leftUpperCorner, IBattleShip battleShip) {
			this.ship = battleShip;
			this.leftUpperCorner = leftUpperCorner;
		}
	}
}
