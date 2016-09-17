package de.sgnosti.function;

import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingFunction {
	private static final Logger LOGGER = LoggerFactory.getLogger(LoggingFunction.class);
	
	public static Runnable loggingRunnable(Runnable f) {
		return () -> {
			LOGGER.debug("run {}", f);
			f.run();
		};
	}

	public static <V> Callable<V> loggingCallable(Callable<V> f) {
		return () -> {
			LOGGER.debug("call {}", f);
			return f.call();
		};
	}

	public static <T, R> Function<T, R> loggingFunction(Function<T, R> f) {
		return t -> {
			LOGGER.debug("apply {} to {}", f, t);
			return f.apply(t);
		};
	}

	public static <T> Consumer<T> loggingConsumer(Consumer<T> f) {
		return t -> {
			LOGGER.debug("accept {} to {}", f, t);
			f.accept(t);
		};
	}

	public static <T> Supplier<T> loggingSupplier(Supplier<T> f) {
		return () -> {
			LOGGER.debug("get {}", f);
			return f.get();
		};
	}

}
