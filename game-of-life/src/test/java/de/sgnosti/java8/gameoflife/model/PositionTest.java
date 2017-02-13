package de.sgnosti.java8.gameoflife.model;

import org.junit.Test;

public class PositionTest {

	@Test(expected = IllegalArgumentException.class)
	public void coordinatesMustBeGreaterThanZero() {
		new Position(-1, -1);
	}

	@Test
	public void coordinatesCanBeZero() {
		System.out.println(new Position(0, 0));
	}

}
