package de.sgnosti.java8.gameoflife.model;

import java.util.Arrays;

public class Board {

	private final Cell[] cells;
	private final int width;
	private final int height;

	public Board(int width, int height) {
		this.width = width;
		this.height = height;
		this.cells = new Cell[width * height];
		for (int y = 0; y < height; y++)
			for (int x = 0; x < width; x++)
				cells[indexOf(x, y)] = new Cell(new Position(x, y), false);
	}

	public Board(int width, int height, boolean[] values) {
		this.width = width;
		this.height = height;
		this.cells = new Cell[width * height];
		for (int y = 0; y < height; y++)
			for (int x = 0; x < width; x++)
				cells[indexOf(x, y)] = new Cell(new Position(x, y), values[indexOf(x, y)]);
	}

	public Cell getCellAt(Position position) {
		System.out.println(
				"Position " + position + " will be found in index " + indexOf(position.getX(), position.getY()));
		return cells[indexOf(position.getX(), position.getY())];
	}

	public void evolve() {
		for (int y = 0; y < height; y++)
			for (int x = 0; x < width; x++)
				cells[indexOf(x, y)].apply();

		for (int y = 0; y < height; y++)
			for (int x = 0; x < width; x++)
				cells[indexOf(x, y)].transition();
	}

	/**
	 * Return the index in the array corresponding to the position (x, y) of the
	 * board.
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	int indexOf(int x, int y) {
		return y * width + x;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder(height * width + height);
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++)
				builder.append(cells[y * width + x]);
			builder.append("\n");
		}
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(cells);
		result = prime * result + height;
		result = prime * result + width;
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
		final Board other = (Board) obj;
		if (!Arrays.equals(cells, other.cells))
			return false;
		if (height != other.height)
			return false;
		if (width != other.width)
			return false;
		return true;
	}

}
