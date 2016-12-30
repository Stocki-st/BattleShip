package com.fhtw.mes1.java_embedded.battleship;

import java.util.ArrayList;
import java.util.List;
import com.fhtw.mes1.java_embedded.battleship.Exceptions.AddShipException;
import com.fhtw.mes1.java_embedded.battleship.lib.IBattleShip;
import com.fhtw.mes1.java_embedded.battleship.lib.ICoordinate;

public class BattleField {

	private List<BattleShips> shipList;
	private int fieldSizeX;
	private int fieldSizeY;

	/**
	 * @param fieldSizeX
	 * @param fieldSizeY
	 */
	public BattleField(int fieldSizeX, int fieldSizeY) {
		shipList = new ArrayList<BattleShips>();
		this.fieldSizeX = fieldSizeX;
		this.fieldSizeY = fieldSizeY;
	}

	/**
	 * @return fieldSizeX
	 */
	public int getBattleFieldSizeX() {
		return fieldSizeX;
	}

	/**
	 * @return fieldSizeY
	 */
	public int getBattleFieldSizeY() {
		return fieldSizeY;
	}

	/**
	 * @param sizeX
	 */
	public void setBattleFieldSizeX(int sizeX) {
		this.fieldSizeX = sizeX;
	}

	/**
	 * @param sizeY
	 */
	public void setBattleFieldSizeY(int sizeY) {
		this.fieldSizeY = sizeY;
	}

	/**
	 * @param leftUpperCorner
	 * @param ship
	 * @throws AddShipException
	 */
	public void addShip(ICoordinate leftUpperCorner, IBattleShip ship) throws AddShipException {
		BattleShips newShip = new BattleShips(leftUpperCorner, ship);
		
		//check if a ship is on this coordinate
		if (shipList.stream().anyMatch(entry -> entry.leftUpperCorner.getXNr() == newShip.leftUpperCorner.getXNr()
				&& entry.leftUpperCorner.getYNr() == newShip.leftUpperCorner.getYNr())) {
			throw new AddShipException("There is already a Ship where you wanted to place" + ship.getName());
		}
		shipList.add(newShip);
	}

	/**

	 * @param guess
	 * @return true if a battle ship is hit, false if not
	 */
	public boolean isHitAnyBattleShip(ICoordinate guess) {
		return shipList.stream().anyMatch(entry -> entry.ship.isHit(entry.leftUpperCorner, guess));
	}


	/**
	 * @return true if all battle ships are destroyed, false if not
	 */
	public boolean allShipsDestroyed() {
		return shipList.stream().allMatch(entry -> entry.ship.isShipDestroyed());
	}

	private final class BattleShips {
		private IBattleShip ship;
		private ICoordinate leftUpperCorner;

		public BattleShips(ICoordinate leftUpperCorner, IBattleShip battleShip) {
			this.ship = battleShip;
			this.leftUpperCorner = leftUpperCorner;
		}
	}

}
