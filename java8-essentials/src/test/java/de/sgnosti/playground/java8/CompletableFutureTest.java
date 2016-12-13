package de.sgnosti.playground.java8;

import static de.sgnosti.function.LoggingFunction.loggingConsumer;
import static de.sgnosti.function.LoggingFunction.loggingFunction;
import static de.sgnosti.function.LoggingFunction.loggingSupplier;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import org.junit.After;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.sgnosti.playground.util.ExternalService;

public class CompletableFutureTest {
	private final Logger LOGGER = LoggerFactory.getLogger(CompletableFutureTest.class);

	private static final int NUMBER_OF_THREADS = 5;

	private final ExecutorService pool = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

	@After
	public void dispose() throws InterruptedException {
		pool.awaitTermination(5, TimeUnit.SECONDS);
	}

	@Test
	public void pullApproach() {
		ExternalService<String> externalService = new ExternalService<>("pullApproach", "ok");
		Future<String> future = pool.submit((Callable<String>) externalService);
		LOGGER.info("submitted pull approach");

		String result = "n.a.";
		try {
			result = future.get();
		} catch (InterruptedException | ExecutionException e) {
			fail(e.getMessage());
		}

		assertEquals(result, "ok");
		LOGGER.info("done pull approach\n");
	}

	@Test
	public void pushApproach() {
		final ExternalService<String> externalService = new ExternalService<>("pushApproach", "ok");
		final CompletableFuture<String> future = CompletableFuture.supplyAsync((Supplier<String>) externalService,
				pool);
		LOGGER.info("supplied push approach");
		future.thenAccept(r -> assertEquals(r, "ok"));
		LOGGER.info("done push approach\n");
	}

	@Test
	public void pushApproachUsingCommonPool() {
		final ExternalService<String> externalService = new ExternalService<>("pushApproachUsingCommonPool", "ok");
		final CompletableFuture<String> future = CompletableFuture.supplyAsync((Supplier<String>) externalService);
		LOGGER.info("supplied push approach using common pool");
		future.thenAccept(r -> assertEquals(r, "ok"));
		LOGGER.info("done push approach using common pool\n");
	}

	@Test(expected = AssertionError.class)
	public void concatenateTasks() {
		CompletableFuture.supplyAsync(new ExternalService<String>("first", "second")).thenApply(s -> s + " | third")
				.thenAccept(s -> assertFalse("second | third".equals(s)));
		LOGGER.error("never got to the assert but did not throw any exception\n");
	}

	@Test(expected = AssertionError.class)
	public void concatenateTasksAndThrowExceptions() throws Throwable {
		try {
			CompletableFuture.supplyAsync(new ExternalService<String>("first", "second")).thenApply(s -> s + " | third")
					.thenAccept(s -> assertFalse("second | third".equals(s))).get();
		} catch (final ExecutionException e) {
			LOGGER.info(e.getCause() + " as ExecutionException");
			throw e.getCause();
		}
		fail("never got to the assert\n");
	}

	@Test
	public void concatenateTasksUsingLoggingFunctions() throws Throwable {
		final CompletionStage<String> other = CompletableFuture.supplyAsync(loggingSupplier(() -> " | second"));
		CompletableFuture.supplyAsync(loggingSupplier(() -> "first")).thenCombine(other, String::concat)
				.thenApply(loggingFunction(s -> s + " | third"))
				.thenAccept(loggingConsumer(s -> assertTrue("first | second | third".equals(s)))).get();
	}

//	@Test
//	 public void concatenateTasksUsingDelayedFunctions() throws Throwable {
//	 final CompletionStage<String> other =
//	 CompletableFuture.supplyAsync(delayedSupplier(() -> " | second"));
//	 CompletableFuture.supplyAsync(delayedSupplier(() ->
//	 "first")).thenCombine(other, String::concat)
//	 .thenApply(delayedFunction(s -> s + " |
//	 third")).thenApply(delayedFunction(s -> s))
//	 .thenAccept(delayedConsumer(s -> assertTrue("first | second |
//	 third".equals(s)))).get();
//	 }
//
//	@Test
//	public void concatenateAndCombine() throws Throwable {
//		final CompletionStage<String> first = CompletableFuture.supplyAsync(delayedSupplier(() -> "first"));
//		final CompletionStage<String> second = CompletableFuture.supplyAsync(delayedSupplier(() -> "second"));
//	}

	@Test
	public void addTasksAndThenSendResult() throws Throwable {
		List<CompletableFuture<Void>> futures = new ArrayList<>();
		futures.add(CompletableFuture.runAsync(task(1, 3000)));
		futures.add(CompletableFuture.runAsync(task(2, 2000)));
		futures.add(CompletableFuture.runAsync(task(3, 1000)));
		CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()]))
				.thenRun(() -> System.out.println("Done"));
	}

	private Runnable task(int counter, long sleepTime) {
		return () -> {
			System.out.println("start " + counter);
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {

			}
			System.out.println("end " + counter);
		};
	}

}
