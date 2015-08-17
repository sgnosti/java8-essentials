package de.sgnosti.playground.java8;

import java.time.Duration;
import java.util.ArrayList;

public class StreamPerformance {

    private static final int SIZE = 1000000;

    public static void main(String[] args) {
	final Benchmark benchmark = new Benchmark();

	final int[] intArray = new int[SIZE];
	final Action intArrayMaximum = new Action() {

	    @Override
	    public void perform() {
		int max = Integer.MIN_VALUE;
		for (int i = 0; i < SIZE; i++) {
		    max = (intArray[i] > max) ? intArray[i] : max;
		}

	    }
	};

	System.out.println("Maximum of an int array: " + benchmark.benchmark(intArrayMaximum));

	final ArrayList<Integer> arrayList = new ArrayList<>(SIZE);
	for (int i = 0; i < SIZE; i++) {
	    arrayList.add(i);
	}
	final Action arrayListMaximum = new Action() {

	    @Override
	    public void perform() {
		Integer max = Integer.MIN_VALUE;
		for (final Integer integer : arrayList) {
		    max = (integer.compareTo(max) > 0) ? integer : max;
		}

	    }
	};

	System.out.println("Maximum of an ArrayList of Integer: " + benchmark.benchmark(arrayListMaximum));

	System.out.println("Maximum of a stream from an ArrayList of Integer: "
		+ benchmark.benchmark(() -> arrayList.stream().reduce(Integer.MIN_VALUE, Math::max)));

	System.out.println("Maximum of a stream from an ArrayList of Integer: "
		+ benchmark.benchmark(() -> arrayList.stream().parallel().reduce(Integer.MIN_VALUE, Math::max)));
    }

    public interface Action {
	public void perform();
    }

    public static class Benchmark {
	public Duration benchmark(Action action) {
	    long time = System.currentTimeMillis();
	    action.perform();
	    time = System.currentTimeMillis() - time;
	    return Duration.ofMillis(time);
	}
    }

}
