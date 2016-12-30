package com.fhtw.mes1.java_embedded.battleship;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

import com.fhtw.mes1.java_embedded.battleship.Exceptions.AddBattleFieldException;
import com.fhtw.mes1.java_embedded.battleship.Exceptions.CoordinateException;
import com.fhtw.mes1.java_embedded.battleship.Game.MapFieldType;
import com.fhtw.mes1.java_embedded.battleship.Exceptions.AddShipException;
import com.fhtw.mes1.java_embedded.battleship.lib.BattleShipInstantiationFailed;
import com.fhtw.mes1.java_embedded.battleship.lib.IBattleShip;
import com.fhtw.mes1.java_embedded.battleship.lib.ICoordinate;

public abstract class Player {

	private String name;
	private BattleField battleField;
	private boolean hasWon;
	private HashMap<ICoordinate, MapFieldType> playerMap;

	/**
	 * @param name
	 * @param battleField
	 */
	public Player(String name, BattleField battleField) {
		this.name = name;
		this.battleField = battleField;
		this.hasWon = false;
	}

	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return battleField
	 */
	public BattleField getBattleField() {
		return battleField;
	}

	/**
	 * @param fileName
	 * @throws AddBattleFieldException
	 */
	public void loadShipMap(String fileName) throws AddBattleFieldException {
		try (BufferedReader br = new BufferedReader(new FileReader(fileName));) {
			String line = br.readLine();
			do {
				String[] lineSegments = line.split(";");
				IBattleShip battleShip = new BattleShipFactoryProducer().createBattleShipFactory(lineSegments).create(lineSegments);
				Coordinate coordinateLeftUpperCorner = new Coordinate(lineSegments[0]); // leftUpperCorner
				battleField.addShip(coordinateLeftUpperCorner, battleShip);
			} while ((line = br.readLine()) != null);
		} catch (IOException e) {
			throw new AddBattleFieldException(e.getMessage());
		} catch (CoordinateException e) {
			throw new AddBattleFieldException(e.getMessage());
		} catch (BattleShipInstantiationFailed e) {
			throw new AddBattleFieldException(e.getMessage());
		} catch (AddShipException e) {
			throw new AddBattleFieldException(e.getMessage());
		} catch (NullPointerException e) {
			throw new AddBattleFieldException("The Config file '" + fileName + "' is empty! Please adjust the file...");
		}
	}

	/**
	 * 
	 */
	public void setHasWon() {
		hasWon = true;
	}

	/**
	 * @return hasWon
	 */
	public boolean getHasWon() {
		return hasWon;
	}

	/**
	 * @return playerMap
	 */
	public HashMap<ICoordinate, MapFieldType> getPlayerMap() {
		return playerMap;
	}

	/**
	 * @param playerMap
	 */
	public void setPlayerMap(HashMap<ICoordinate, MapFieldType> playerMap) {
		this.playerMap = playerMap;
	}

	/**
	 * @param guess
	 * @return true, if hit - false, if not hit
	 */
	public boolean makeGuess(ICoordinate guess) {
		Entry<ICoordinate, MapFieldType> mapEntry = playerMap.entrySet().stream()
				.filter(entry -> entry.getKey().getXNr() == guess.getXNr() && entry.getKey().getYNr() == guess.getYNr())
				.findFirst().get();
		if (battleField.isHitAnyBattleShip(guess)) {
			mapEntry.setValue(MapFieldType.HIT);
			return true;
		} else {
			mapEntry.setValue(MapFieldType.FAILED);
			return false;
		}
	}

	/**
	 * @return battleFieldMap as String
	 */
	public String battlefieldMapToString() {
		StringBuilder battleFieldMap = new StringBuilder();

		battleFieldMap.append("\nSymbol explanation: '   ' = not tried, O = not hit , X = hit\n\n   |");
		for (char xLine = 'A'; xLine < 'A' + battleField.getBattleFieldSizeX(); xLine++) {
			battleFieldMap.append(" ").append(String.format("%c", xLine)).append(" |");
		}
		battleFieldMap.append("\n");
		for (int x = 0; x < battleField.getBattleFieldSizeX() + 1; x++) {
			battleFieldMap.append("~~~~");
		}
		battleFieldMap.append("\n");
		for (int y = 0; y < battleField.getBattleFieldSizeY(); y++) {
			battleFieldMap.append(" ").append(String.format("%d", y)).append(" |");
			int yNr = y;
			for (int x = 1; x <= battleField.getBattleFieldSizeX(); x++) {
				int xNr = x;
				Entry<ICoordinate, MapFieldType> mapEntry = playerMap.entrySet().stream()
						.filter(entry -> entry.getKey().getXNr() == xNr && entry.getKey().getYNr() == yNr + 1)
						.findFirst().get();
				battleFieldMap.append(" ");
				switch (mapEntry.getValue()) {
				case FAILED:
					battleFieldMap.append("O");
					break;
				case HIT:
					battleFieldMap.append("X");
					break;
				case NOTTRIED:
					battleFieldMap.append(" ");
					break;
				}
				battleFieldMap.append(" :");
			}
			battleFieldMap.append("\n");
			for (int x = 0; x < battleField.getBattleFieldSizeX() + 1; x++) {
				battleFieldMap.append("....");
			}
			battleFieldMap.append("\n");
		}
		return battleFieldMap.toString();
	}
}
