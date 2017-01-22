package com.fhtw.mes1.java_embedded.battleship.ships;

import com.fhtw.mes1.java_embedded.battleship.Coordinate;
import com.fhtw.mes1.java_embedded.battleship.Exceptions.CoordinateException;
import com.fhtw.mes1.java_embedded.battleship.lib.BattleShipInstantiationFailed;
import com.fhtw.mes1.java_embedded.battleship.lib.IBattleShip;
import com.fhtw.mes1.java_embedded.battleship.lib.IBattleShipFactory;

public class RectBattleShipFactory implements IBattleShipFactory {

	@Override
	public IBattleShip create(String[] initStringElems) throws BattleShipInstantiationFailed {
		try {
			return new RectBattleShip(new Coordinate(initStringElems[0]), initStringElems[2],
					Integer.parseInt(initStringElems[3]),Integer.parseInt(initStringElems[4]));
		} catch (CoordinateException e) {
			throw new BattleShipInstantiationFailed(
					"Coordinates of '" + initStringElems[2] + "' are wrong: " + e.getMessage());
		} catch (NumberFormatException e) {
			throw new BattleShipInstantiationFailed("Please check the length of your ship '" + initStringElems[2] + "'");
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new BattleShipInstantiationFailed("There is a parameter missing for your ship '" + initStringElems[2] + "'");
		}
	}
}
