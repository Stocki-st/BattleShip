package com.fhtw.mes1.java_embedded.battleship.ships;

import com.fhtw.mes1.java_embedded.battleship.Coordinate;
import com.fhtw.mes1.java_embedded.battleship.Exceptions.CoordinateException;
import com.fhtw.mes1.java_embedded.battleship.lib.BattleShipInstantiationFailed;
import com.fhtw.mes1.java_embedded.battleship.lib.IBattleShip;
import com.fhtw.mes1.java_embedded.battleship.lib.IBattleShipFactory;

/**
 * @author stocki
 * Factory for square battleships
 */
public class SquareBattleShipFactory implements IBattleShipFactory {

	/* (non-Javadoc)
	 * @see com.fhtw.mes1.java_embedded.battleship.lib.IBattleShipFactory#create(java.lang.String[])
	 */
	@Override
	public IBattleShip create(String[] initStringElems) throws BattleShipInstantiationFailed {
		try {
			return new SquareBattleShip(new Coordinate(initStringElems[0]), initStringElems[2],
					Integer.parseInt(initStringElems[3]));
		} catch (CoordinateException e) {
			throw new BattleShipInstantiationFailed(
					"Coordinates of '" + initStringElems[2] + "' are wrong: " + e.getMessage());
		} catch (NumberFormatException ex) {
			throw new BattleShipInstantiationFailed("Please check the length of your ship '" + initStringElems[2]);
		}
	}
}
