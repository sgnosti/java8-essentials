package de.sgnosti.playground.util;

import java.time.Duration;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExternalService<T> implements Callable<T>, Runnable, Supplier<T> {
	private static final Logger LOGGER = LoggerFactory.getLogger(ExternalService.class);
	private static final Duration DEFAULT_DURATION = Duration.ofSeconds(1);
	
	private final String name;
	private final T defaultResult;
	private final Duration duration;

	public ExternalService(String name, T result, Duration duration) {
		this.name = name;
		this.defaultResult = result;
		this.duration = duration;
	}
	
	public ExternalService(String name, T result) {
		this(name, result, DEFAULT_DURATION);
	}

	@Override
	public T call() throws Exception {
		LOGGER.info("[{}] call in 1s", name);
		Thread.sleep(duration.toMillis());
		return defaultResult;
	}

	@Override
	public void run() {
		LOGGER.info("[{}] run in 1s", name);
		try {
			Thread.sleep(duration.toMillis());
		} catch (InterruptedException e) {
			LOGGER.error(e.getMessage());
		}
	}

	@Override
	public T get() {
		LOGGER.info("[{}] get in 1s", name);
		try {
			Thread.sleep(duration.toMillis());
		} catch (InterruptedException e) {
			LOGGER.error(e.getMessage());
		}
		return defaultResult;
	}

}
