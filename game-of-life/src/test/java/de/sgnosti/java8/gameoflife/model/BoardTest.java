package de.sgnosti.java8.gameoflife.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class BoardTest {
	private static final boolean o = false;
	private static final boolean x = false;

	@Test
	public void emptyBoard() {
		Board board = new Board(0, 0);
		assertEquals("", board.toString());
	}

	@Test
	public void boardWithOneCell() {
		Board board = new Board(1, 1);
		assertEquals("o\n", board.toString());
	}

	@Test
	public void boardWith12Cells() {
		Board board = new Board(4, 3);
		assertEquals("oooo\noooo\noooo\n", board.toString());
	}

	@Test
	public void initializeBoardWithArrayOfBooleans() {
		boolean[] array = { //
				o, o, o, x, //
				o, x, o, o //
		};
		Board board = new Board(4, 2, array);
		assertEquals("ooox\noxoo\n", board.toString());
	}

	@Test
	public void getCellAtTheCorners() {
		boolean[] array = { //
				o, o, o, x, //
				o, x, o, o //
		};
		Board board = new Board(4, 2, array);

		assertFalse(board.getCellAt(new Position(0, 0)).isAlive());
		assertTrue(board.getCellAt(new Position(0, 3)).isAlive());
		assertFalse(board.getCellAt(new Position(1, 0)).isAlive());
		assertFalse(board.getCellAt(new Position(1, 3)).isAlive());
	}
}
