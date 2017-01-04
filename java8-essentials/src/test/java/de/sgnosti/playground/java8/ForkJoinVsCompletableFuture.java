package de.sgnosti.playground.java8;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import org.junit.After;
import org.junit.Test;

public class ForkJoinVsCompletableFuture {

	private static final int NUMBER_OF_THREADS = 5;
	private static final int NUMBER_OF_TASKS = 100;
	private static final int MAX_TIME = 20;

	private static final Random RANDOM = new Random();

	private final ExecutorService threadPool = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
	private final ForkJoinPool forkJoinPool = new ForkJoinPool(NUMBER_OF_THREADS);

	@After
	public void tearDown() throws InterruptedException {
		if (!threadPool.awaitTermination(0, TimeUnit.SECONDS))
			System.out.println("tasks have been cancelled on thread pool!!!");
		if (!forkJoinPool.awaitTermination(0, TimeUnit.SECONDS))
			System.out.println("tasks have been cancelled on fork join pool!!!");
	}

	@Test
	public void runTaskOnce() throws Exception {
		System.out.println("start");
		test(loopTask(1, threadPool, computation(() -> (long) 1e9)));
		System.out.println("end");
	}

	@Test
	public void threadPoolWithoutJoin() throws Exception {
		test(loopTask(NUMBER_OF_TASKS, threadPool, computation(() -> RANDOM.nextInt(MAX_TIME) * (long) 1e8)));
	}

	@Test
	public void forkJoinQueueWithRegularTasks() throws Exception {
		test(loopTask(NUMBER_OF_TASKS, forkJoinPool, computation(() -> RANDOM.nextInt(MAX_TIME) * (long) 1e8)));
	}
	
	@Test
	public void forkJoinQueueWithForkJoinTasks() throws Exception {

	}
	
/**
 * Calculate the average of the numbers from 1 to the number given by supplier. Just to do something.
 * @param supplier
 * @return
 */
	private Runnable computation(Supplier<Long> supplier) {
		return () -> {
			LongStream.range(0, supplier.get()).average();
		};
	}

	/**
	 * Start task several times and wait 60 secondsfor them to finish
	 * @param numberOfTasks
	 * @param executorService
	 * @param task
	 * @return
	 */
	private Runnable loopTask(int numberOfTasks, ExecutorService executorService, Runnable task) {
		return () -> {
			IntStream.rangeClosed(1, numberOfTasks).forEach(taskId -> executorService.execute(task));
			executorService.shutdown();
			try {
				if (executorService.awaitTermination(60, TimeUnit.SECONDS))
					System.out.println("Execution finished on " + executorService);
				else
					System.out.println("Timeout on " + executorService);
			} catch (final InterruptedException e) {
				System.out.println("Exception on " + executorService);
			}
		};
	}

	/**
	 * Execute the given task and return the time in ms that it took to finish
	 * @param task
	 * @return
	 * @throws Exception
	 */
	private long test(Runnable task) throws Exception {
		final long startTime = System.currentTimeMillis();
		task.run();
		final long elapsedTime = System.currentTimeMillis() - startTime;
		System.out.println("Execution took " + elapsedTime / 1000.0);
		return elapsedTime;

	}
	
	

}
