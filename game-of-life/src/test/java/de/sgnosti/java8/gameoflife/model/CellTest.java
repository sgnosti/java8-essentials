package de.sgnosti.java8.gameoflife.model;

import static org.junit.Assert.*;

import java.util.HashSet;

import org.junit.Test;

public class CellTest {

	private static final Position POSITION = new Position(0, 0);
	private static final Cell NEIGHBOUR_1 = new Cell(new Position(0, 1), true);
	private static final Cell NEIGHBOUR_2 = new Cell(new Position(0, 2), true);
	private static final Cell NEIGHBOUR_3 = new Cell(new Position(0, 3), true);
	private static final Cell NEIGHBOUR_4 = new Cell(new Position(0, 4), true);

	@Test
	public void anyCellWithLessThanTwoNeighboursDies() {
		final HashSet<Cell> neighbours = new HashSet<>();
		assertFalse(new Cell(POSITION, true, neighbours).transition().isNext());
		assertFalse(new Cell(POSITION, false, neighbours).transition().isNext());
		neighbours.add(NEIGHBOUR_1);
		assertFalse(new Cell(POSITION, true, neighbours).transition().isNext());
		assertFalse(new Cell(POSITION, false, neighbours).transition().isNext());
	}

	@Test
	public void aliveWithTwoOrThreeNeighboursStaysAlive() {
		final HashSet<Cell> neighbours = new HashSet<>();
		neighbours.add(NEIGHBOUR_1);
		neighbours.add(NEIGHBOUR_2);
		assertTrue(new Cell(POSITION, true, neighbours).transition().isNext());
		neighbours.add(NEIGHBOUR_3);
		assertTrue(new Cell(POSITION, true, neighbours).transition().isNext());
	}

	@Test
	public void deadWithTwoNeighboursStaysDead() {
		final HashSet<Cell> neighbours = new HashSet<>();
		neighbours.add(NEIGHBOUR_1);
		neighbours.add(NEIGHBOUR_2);
		assertFalse(new Cell(POSITION, false, neighbours).transition().isNext());
	}

	@Test
	public void deadWithThreeNeighboursBecomesAlive() {
		final HashSet<Cell> neighbours = new HashSet<>();
		neighbours.add(NEIGHBOUR_1);
		neighbours.add(NEIGHBOUR_2);
		neighbours.add(NEIGHBOUR_3);
		assertTrue(new Cell(POSITION, false, neighbours).transition().isNext());
	}

	@Test
	public void anyCellWithMoreThanThreeNeighboursDies() {
		final HashSet<Cell> neighbours = new HashSet<>();
		neighbours.add(NEIGHBOUR_1);
		neighbours.add(NEIGHBOUR_2);
		neighbours.add(NEIGHBOUR_3);
		neighbours.add(NEIGHBOUR_4);
		assertFalse(new Cell(POSITION, true, neighbours).transition().isNext());
		assertFalse(new Cell(POSITION, false, neighbours).transition().isNext());
	}

	@Test
	public void transitionIsOnlyVisibleAfterApplying() {
		final HashSet<Cell> neighbours = new HashSet<>();
		assertTrue(new Cell(POSITION, true, neighbours).transition().isAlive());
		assertFalse(new Cell(POSITION, true, neighbours).apply().isAlive());
	}

}
