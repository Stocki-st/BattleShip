package com.fhtw.mes1.java_embedded.battleship.ships;

import com.fhtw.mes1.java_embedded.battleship.Coordinate;
import com.fhtw.mes1.java_embedded.battleship.Exceptions.CoordinateException;
import com.fhtw.mes1.java_embedded.battleship.lib.BattleShipInstantiationFailed;
import com.fhtw.mes1.java_embedded.battleship.lib.IBattleShip;
import com.fhtw.mes1.java_embedded.battleship.lib.IBattleShipFactory;

public class HorizontalBattleShipFactory implements IBattleShipFactory {

  @Override
  public IBattleShip create(String[] initStringElems) throws BattleShipInstantiationFailed {
    
    String leftUpperCorner = initStringElems[0];
    Coordinate coordinateLeftUpperCorner;
    try {
      coordinateLeftUpperCorner = new Coordinate(leftUpperCorner);
    } catch (CoordinateException e) {
      throw new BattleShipInstantiationFailed("Invalid left upper corner coordinate: " + e.getMessage());
    }
    
    String battleShipName = initStringElems[2];
    
    try
    {
      int shipLen = Integer.parseInt(initStringElems[3]);
      return new HorizontalBattleShip(coordinateLeftUpperCorner, battleShipName, shipLen);
    }
    catch (NumberFormatException ex)
    {
      throw new BattleShipInstantiationFailed("Invalid ship length!");
    }
  }

}
