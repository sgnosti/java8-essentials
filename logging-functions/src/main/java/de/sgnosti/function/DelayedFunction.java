package de.sgnosti.function;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Wrapper for different interfaces of the java.util.function package that will
 * let the thread sleep for a random time before actually executing the task
 * 
 * @author sgnosti
 *
 */
public class DelayedFunction {
	private static final Logger LOGGER = LoggerFactory.getLogger(DelayedFunction.class);
	private static final Random RANDOM = new Random();

	public static Runnable delayedRunnable(Runnable f) {
		return () -> {
			delay();
			LOGGER.debug("run {}", f);
			f.run();
		};
	}

	public static <V> Callable<V> delayedCallable(Callable<V> f) {
		return () -> {
			delay();
			LOGGER.debug("call {}", f);
			return f.call();
		};
	}

	public static <T, R> Function<T, R> delayedFunction(Function<T, R> f) {
		return t -> {
			delay();
			LOGGER.debug("apply {} to {}", f, t);
			return f.apply(t);
		};
	}

	public static <T> Consumer<T> delayedConsumer(Consumer<T> f) {
		return t -> {
			delay();
			LOGGER.debug("accept {} to {}", f, t);
			f.accept(t);
		};
	}

	public static <T> Supplier<T> delayedSupplier(Supplier<T> f) {
		return () -> {
			delay();
			LOGGER.debug("get {}", f);
			return f.get();
		};
	}

	private static void delay() {
		try {
			Thread.sleep((long) (RANDOM.nextDouble() * 1000));
		} catch (InterruptedException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}
}

