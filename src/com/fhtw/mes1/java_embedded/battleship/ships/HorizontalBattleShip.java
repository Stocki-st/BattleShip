package com.fhtw.mes1.java_embedded.battleship.ships;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Optional;

import com.fhtw.mes1.java_embedded.battleship.Coordinate;
import com.fhtw.mes1.java_embedded.battleship.lib.IBattleShip;
import com.fhtw.mes1.java_embedded.battleship.lib.ICoordinate;

public class HorizontalBattleShip implements IBattleShip {

  private String name;
  private int shipLength;
  private ICoordinate leftUpperCorner;
  private HashMap<ICoordinate, Boolean> shipCoordinates;
  
  public HorizontalBattleShip(ICoordinate leftUpper, String name, int shipLen) {
    this.name = name;
    this.shipLength = shipLen;
    this.leftUpperCorner = leftUpper;
    
    shipCoordinates = new HashMap<ICoordinate, Boolean>();
    
    initPossibleShipCoordinates();
  }

  private void initPossibleShipCoordinates() {
    // init possible BattleShip Coordinates 
    shipCoordinates.put(leftUpperCorner, false);
    for (int x = 1; x < this.shipLength; x++) {
      shipCoordinates.put(new Coordinate(leftUpperCorner.getXNr() + x, leftUpperCorner.getYNr()), false);
    }
  }
  
  @Override
  public String getName() {
    return name;
  }
  
  @Override
  public boolean isShipDestroyed() {
    return shipCoordinates.entrySet().stream().allMatch(entry -> entry.getValue());
  }
  
  @Override
  public boolean isHit(ICoordinate leftUpperCorner, ICoordinate guess) {
    // NOTE: ignore leftUpperCorner parameter here!
    // leftUpperCorner has already been passed to the ctor and should never change again
    if (this.leftUpperCorner == null)
    {
      this.leftUpperCorner = leftUpperCorner;
      initPossibleShipCoordinates();
    }
    
    Optional<Entry<ICoordinate, Boolean>> guessEntry = shipCoordinates.entrySet().stream()
        .filter(entry -> entry.getKey().getXNr() == guess.getXNr() && entry.getKey().getYNr() == guess.getYNr()).findFirst();
    
    if (guessEntry.isPresent())
    {
      guessEntry.get().setValue(true);
      return true;
    }
    else
    {
      return false;
    }
  }
}
