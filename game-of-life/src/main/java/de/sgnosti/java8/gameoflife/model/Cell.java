package de.sgnosti.java8.gameoflife.model;

import java.util.HashSet;
import java.util.Set;

class Cell {

	private final Position position;
	private final Set<Cell> neighbours;
	private boolean alive;
	private boolean next;

	public Cell(Position position, boolean alive) {
		this(position, alive, new HashSet<>());
	}

	Cell(Position position, boolean alive, Set<Cell> neighbours) {
		this.position = position;
		this.alive = alive;
		this.next = false;
		this.neighbours = neighbours;
	}

	public Position getPosition() {
		return position;
	}

	public boolean isAlive() {
		return alive;
	}

	public void addNeighbour(Cell neighbour) {
		neighbour.addNeighbour(neighbour);
	}

	public Cell apply() {
		this.alive = next;
		return this;
	}

	// FIXME this logic cannot be tested
	public Cell transition() {
		final int aliveNeighbours = (int) neighbours.stream().filter(Cell::isAlive).count();
		next = aliveNeighbours == 3 || (aliveNeighbours == 2 && alive);
		return this;
	}

	void setAlive(boolean alive) {
		this.alive = alive;
	}

	boolean isNext() {
		return next;
	}

	@Override
	public String toString() {
		return alive ? "x" : "o";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (alive ? 1231 : 1237);
		result = prime * result + ((position == null) ? 0 : position.hashCode());
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
		final Cell other = (Cell) obj;
		if (alive != other.alive)
			return false;
		if (position == null) {
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
			return false;
		return true;
	}

}
