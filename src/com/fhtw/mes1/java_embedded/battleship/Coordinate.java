package com.fhtw.mes1.java_embedded.battleship;

import com.fhtw.mes1.java_embedded.battleship.Exceptions.CoordinateException;
import com.fhtw.mes1.java_embedded.battleship.lib.ICoordinate;

public class Coordinate implements ICoordinate {

	int xCoordinate;
	int yCoordinate;

	/**
	 * @param x
	 * @param y
	 */
	public Coordinate(int x, int y) {
		xCoordinate = x;
		yCoordinate = y;
	}

	/**
	 * @param coordinate
	 * @throws CoordinateException
	 */
	public Coordinate(String coordinate) throws CoordinateException {
		if (coordinate.length() == 2) {
			char x = coordinate.charAt(0);
			char y = coordinate.charAt(1);
			if ((x - 'A') < 0) {
				throw new CoordinateException("wrong X value");
			}else if ((y - '0') < 0) {
				throw new CoordinateException("wrong Y value");
			}else{
			xCoordinate = x - 'A' + 1;
			yCoordinate = y - '0' + 1;
			}
		} else {
			throw new CoordinateException("Wrong Coordinate length");
		}

	}

	/* (non-Javadoc)
	 * @see com.fhtw.mes1.java_embedded.battleship.lib.ICoordinate#getXNr()
	 */
	@Override
	public int getXNr() {
		return xCoordinate;
	}

	/* (non-Javadoc)
	 * @see com.fhtw.mes1.java_embedded.battleship.lib.ICoordinate#getYNr()
	 */
	@Override
	public int getYNr() {
		return yCoordinate;
	}

}
