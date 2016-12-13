package de.sgnosti.playground.java8;

import java.time.Duration;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;

public class ForkJoinVsCompletableFuture {

	private static final int NUMBER_OF_THREADS = 5;
	private static final int NUMBER_OF_TASKS = 100;
	private static final int MAX_TIME = 20;

	private static final Random RANDOM = new Random();

	private ExecutorService threadPool = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
	private ForkJoinPool forkJoinPool = new ForkJoinPool(NUMBER_OF_THREADS);

	@After
	public void tearDown() throws InterruptedException {
		if (!threadPool.awaitTermination(0, TimeUnit.SECONDS))
			System.out.println("tasks have been cancelled!!!");
		if (!forkJoinPool.awaitTermination(0, TimeUnit.SECONDS))
			System.out.println("tasks have been cancelled!!!");
	}

	@Test
	public void runTaskOnce() throws Exception {
		System.out.println("start");
		test(loopTask(1, threadPool, computation((long) 1e9)));
		System.out.println("end");
	}

	@Test
	public void threadPoolWithoutJoin() throws Exception {

		test(loopTask(NUMBER_OF_TASKS, threadPool, computation(RANDOM.nextInt(MAX_TIME) * (long) 1e8)));
	}

	@Test
	@Ignore
	public void forkJoinQueue() throws InterruptedException {
		long startTime = System.currentTimeMillis();
		IntStream.rangeClosed(1, NUMBER_OF_TASKS)
				.forEach(taskId -> forkJoinPool.execute(computation(RANDOM.nextInt(MAX_TIME) * (long) 1e9)));
		forkJoinPool.awaitTermination(30, TimeUnit.SECONDS);
		long elapsedTime = System.currentTimeMillis() - startTime;
		System.out.println("Fork join pool took " + elapsedTime);
		System.out.println("########################\n\n\n");
	}

	private Runnable computation(long maxIterations) {
		return () -> {
			double result = LongStream.range(0, maxIterations).average().orElse(0) + 0;
			System.out.println(result);
		};
	}

	private Runnable loopTask(int numberOfTasks, ExecutorService executorService, Runnable task) {
		return () -> {
			IntStream.rangeClosed(1, numberOfTasks).forEach(taskId -> executorService.execute(task));
			try {
				if (!executorService.awaitTermination(30, TimeUnit.SECONDS))
					System.out.println("Timeout on " + executorService);
			} catch (InterruptedException e) {
				System.out.println("Exception on " + executorService);
			}
		};
	}

	private long test(Runnable task) throws Exception {
		long startTime = System.currentTimeMillis();
		task.run();
		long elapsedTime = System.currentTimeMillis() - startTime;
		System.out.println("Execution took " + elapsedTime / 1000.0);
		return elapsedTime;

	}

}
