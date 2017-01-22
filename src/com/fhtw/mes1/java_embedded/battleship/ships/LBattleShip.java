package com.fhtw.mes1.java_embedded.battleship.ships;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Optional;

import com.fhtw.mes1.java_embedded.battleship.Coordinate;
import com.fhtw.mes1.java_embedded.battleship.lib.IBattleShip;
import com.fhtw.mes1.java_embedded.battleship.lib.ICoordinate;

/**
 * @author stocki
 * implementation of L battle ship
 */
public class LBattleShip implements IBattleShip {

	private String name;
	private int shipLenX;
	private int shipLenY;
	private ICoordinate leftUpperCorner;
	private HashMap<ICoordinate, Boolean> shipMap;

	/**
	 * @param leftUpper
	 * @param name
	 * @param shipLenY
	 * @param shipLenX
	 */
	public LBattleShip(ICoordinate leftUpper, String name, int shipLenY, int shipLenX) {
		this.name = name;
		this.shipLenX = shipLenX;
		this.shipLenY = shipLenY;
		this.leftUpperCorner = leftUpper;
		shipMap = new HashMap<ICoordinate, Boolean>();

		for (int y = 0; y < this.shipLenY; y++) {
			shipMap.put(new Coordinate(leftUpperCorner.getXNr(), leftUpperCorner.getYNr() + y), false);
		}
		for (int x = 1; x < this.shipLenX; x++) {
			shipMap.put(new Coordinate(leftUpperCorner.getXNr() + x, leftUpperCorner.getYNr()+shipLenY-1), false);
		}
	}

	/* (non-Javadoc)
	 * @see com.fhtw.mes1.java_embedded.battleship.lib.IBattleShip#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/* (non-Javadoc)
	 * @see com.fhtw.mes1.java_embedded.battleship.lib.IBattleShip#isShipDestroyed()
	 */
	@Override
	public boolean isShipDestroyed() {
		return shipMap.entrySet().stream().allMatch(entry -> entry.getValue());
	}

	/* (non-Javadoc)
	 * @see com.fhtw.mes1.java_embedded.battleship.lib.IBattleShip#isHit(com.fhtw.mes1.java_embedded.battleship.lib.ICoordinate, com.fhtw.mes1.java_embedded.battleship.lib.ICoordinate)
	 */
	@Override
	public boolean isHit(ICoordinate leftUpperCorner, ICoordinate guess) {
		Optional<Entry<ICoordinate, Boolean>> guessedCoordinate = shipMap.entrySet().stream()
				.filter(entry -> entry.getKey().getXNr() == guess.getXNr() && entry.getKey().getYNr() == guess.getYNr())
				.findFirst();
		if (guessedCoordinate.isPresent()) {
			guessedCoordinate.get().setValue(true);
			return true;
		} else {
			return false;
		}
	}
}
