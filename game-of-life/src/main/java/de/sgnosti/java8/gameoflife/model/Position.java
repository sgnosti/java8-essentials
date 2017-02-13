package de.sgnosti.java8.gameoflife.model;

import java.text.MessageFormat;

public class Position {

	private final int x;
	private final int y;

	public Position(int x, int y) {
		if (x < 0 || y < 0)
			throw new IllegalArgumentException(MessageFormat.format("({0},{1}) is not a valid position", x, y));
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	@Override
	public String toString() {
		return MessageFormat.format("({0},{1})", x, y);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Position other = (Position) obj;
		return x == other.x && y == other.y;
	}

}
