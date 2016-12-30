package com.fhtw.mes1.java_embedded.battleship;

import com.fhtw.mes1.java_embedded.battleship.lib.BattleShipInstantiationFailed;
import com.fhtw.mes1.java_embedded.battleship.lib.IBattleShipFactory;

public class BattleShipFactoryProducer {

	/**
	 * @param initStringElems
	 * @return battleShipFactory 
	 * @throws BattleShipInstantiationFailed
	 */
	public IBattleShipFactory createBattleShipFactory(String[] initStringElems) throws BattleShipInstantiationFailed {
		if (initStringElems.length < 4) {
			throw new BattleShipInstantiationFailed("Problem in BattleShip Factory: missing ship parameter");
		}
		String shipType = initStringElems[1];
		// load BattleShipFactory
		try {
			Class<?> factoryClass = Class.forName("com.fhtw.mes1.java_embedded.battleship.ships." + shipType + "Factory");
			return (IBattleShipFactory) factoryClass.newInstance();
		} catch (InstantiationException e) {
			throw new BattleShipInstantiationFailed("Problem while loading BattleShip Factory: " + e.getMessage());
		} catch (IllegalAccessException e) {
			throw new BattleShipInstantiationFailed("Problem while loading BattleShip Factory: " + e.getMessage());
		} catch (ClassNotFoundException e) {
			throw new BattleShipInstantiationFailed("Problem while loading BattleShip Factory: -> file not found: " + e.getMessage());
		}
	}
}
