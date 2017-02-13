package de.sgnosti.java8.gameoflife.model;

public class Board {

	private final Cell[] cells;
	private int width;
	private int height;

	public Board(int width, int height) {
		this.width = width;
		this.height = height;
		this.cells = new Cell[width * height];
		for (int y = 0; y < height; y++)
			for (int x = 0; x < width; x++)
				cells[y * width + x] = new Cell(new Position(x, y), false);
	}

	public Board(int width, int height, boolean[] values) {
		this.width = width;
		this.height = height;
		this.cells = new Cell[width * height];
		for (int y = 0; y < height; y++)
			for (int x = 0; x < width; x++)
				cells[y * width + x] = new Cell(new Position(x, y), values[y * width + x]);
	}

	public Cell getCellAt(Position position) {
		return cells[position.getY() * width + position.getX()];
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(height * width + height);
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++)
				builder.append(cells[y * width + x]);
			builder.append("\n");
		}
		return builder.toString();
	}
}
