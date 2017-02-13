package de.sgnosti.java8.gameoflife.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BoardTest {

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
				false, false, false, true, //
				false, true, false, false //
		};
		Board board = new Board(4, 2, array);
		assertEquals("ooo+\no+oo\n", board.toString());
	}
}
