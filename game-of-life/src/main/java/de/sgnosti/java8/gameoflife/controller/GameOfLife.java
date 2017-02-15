package de.sgnosti.java8.gameoflife.controller;

import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Set;

import de.sgnosti.java8.gameoflife.model.Board;
import de.sgnosti.java8.gameoflife.model.Position;

public class GameOfLife {

	private final Board board;

	public GameOfLife(int width, int height) {
		board = new Board(width, height);
	}

	public void start(Set<Position> seed) {
		board.start(seed);
		System.out.println(board);
		while (!board.extinct()) {
			board.evolve();
			System.out.println(board);
			System.out.println("\n---------------------\n");
		}
		System.out.println(MessageFormat.format("Game over after {0} generations.", board.getGeneration()));
	}

	public static void main(String[] args) {
		final Set<Position> seed = new HashSet<>();
		seed.add(new Position(0, 0));
		seed.add(new Position(0, 1));
		seed.add(new Position(0, 2));
		seed.add(new Position(0, 3));
		new GameOfLife(3, 4).start(seed);
	}

}
