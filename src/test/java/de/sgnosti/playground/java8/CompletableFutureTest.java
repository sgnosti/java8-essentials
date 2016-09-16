package de.sgnosti.playground.java8;

import static org.junit.Assert.*;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Supplier;

import org.junit.Test;

import de.sgnosti.playground.util.ExternalService;

public class CompletableFutureTest {
	
	private static final int NUMBER_OF_THREADS = 5;
	
	private ExternalService<String> externalService = new ExternalService<>("external service", "ok");
	private ExecutorService pool = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    @Test
    public void pullApproach() {
    	Future<String> future = pool.submit((Callable<String>) externalService);
    	
    	String result = "n.a.";
    	try {
			result = future.get();
		} catch (InterruptedException | ExecutionException e) {
			fail(e.getMessage());
		}
    	
    	assertEquals(result, "ok");
    }
    

    @Test
    public void pushApproach() {
    	
    	CompletableFuture<String> future = CompletableFuture.supplyAsync((Supplier<String>) externalService, pool);
    	future.thenAccept(r -> assertEquals(r, "ok"));
    }

}
