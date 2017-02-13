package de.sgnosti.java8.gameoflife.model;

import java.util.HashSet;
import java.util.Set;

public class Cell {

	private final Position position;
	private final Set<Cell> neighbours = new HashSet<>();
	private boolean alive;
	private boolean next;

	public Cell(Position position, boolean alive) {
		this.position = position;
		this.alive = alive;
		this.next = false;
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

	public void transition() {
		this.alive = next;
	}

	// FIXME this logic cannot be tested
	public void apply() {
		final int aliveNeighbours = (int) neighbours.stream().filter(Cell::isAlive).count();
		next = aliveNeighbours == 3 || (aliveNeighbours == 2 && alive);
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
