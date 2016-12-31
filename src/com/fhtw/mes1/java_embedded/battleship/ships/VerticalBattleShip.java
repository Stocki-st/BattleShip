package com.fhtw.mes1.java_embedded.battleship.ships;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Optional;

import com.fhtw.mes1.java_embedded.battleship.Coordinate;
import com.fhtw.mes1.java_embedded.battleship.lib.IBattleShip;
import com.fhtw.mes1.java_embedded.battleship.lib.ICoordinate;

public class VerticalBattleShip implements IBattleShip {

	private String name;
	private int shipLen;
	private ICoordinate leftUpperCorner;
	private HashMap<ICoordinate, Boolean> shipMap;

	public VerticalBattleShip(ICoordinate leftUpper, String name, int shipLen) {
		this.name = name;
		this.shipLen = shipLen;
		this.leftUpperCorner = leftUpper;
		shipMap = new HashMap<ICoordinate, Boolean>();

		for (int y = 0; y < this.shipLen; y++) {
			shipMap.put(new Coordinate(leftUpperCorner.getXNr(), leftUpperCorner.getYNr() + y), false);
		}
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean isShipDestroyed() {
		return shipMap.entrySet().stream().allMatch(entry -> entry.getValue());
	}

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
