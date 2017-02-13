package de.sgnosti.java8.gameoflife.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class BoardTest {
	private static final boolean o = false;
	private static final boolean x = true;

	@Test
	public void emptyBoard() {
		final Board board = new Board(0, 0);
		assertEquals("", board.toString());
	}

	@Test
	public void boardWithOneCell() {
		final Board board = new Board(1, 1);
		assertEquals("o\n", board.toString());
	}

	@Test
	public void boardWith12Cells() {
		final Board board = new Board(4, 3);
		assertEquals("oooo\noooo\noooo\n", board.toString());
	}

	@Test
	public void initializeBoardWithArrayOfBooleans() {
		final boolean[] array = { //
				o, o, o, x, //
				o, x, o, o //
		};
		final Board board = new Board(4, 2, array);
		System.out.println(board);
		assertEquals("ooox\noxoo\n", board.toString());
	}

	@Test
	public void getCellAtTheCorners() {
		final boolean[] array = { //
				o, o, o, x, //
				o, x, o, o //
		};
		final Board board = new Board(4, 2, array);

		assertFalse(board.getCellAt(new Position(0, 0)).isAlive());
		assertTrue(board.getCellAt(new Position(3, 0)).isAlive());
		assertFalse(board.getCellAt(new Position(0, 1)).isAlive());
		assertFalse(board.getCellAt(new Position(3, 1)).isAlive());
	}

	@Test
	public void evolveEmptyBoardIsEmpty() {
		final Board board = new Board(0, 0);
		board.evolve();
		assertEquals(new Board(0, 0), board);
	}

	@Test
	public void evolveOneElementIsAlwaysDead() {
		// one dead cell evolves to dead
		Board board = new Board(1, 1, new boolean[] { false });
		board.evolve();
		assertFalse(board.getCellAt(new Position(0, 0)).isAlive());
		// one cell alive evolves to dead
		board = new Board(1, 1, new boolean[] { true });
		board.evolve();
		assertFalse(board.getCellAt(new Position(0, 0)).isAlive());
	}
}
