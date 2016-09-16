package de.sgnosti.playground.util;

import java.util.concurrent.Callable;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExternalService<T> implements Callable<T>, Runnable, Supplier<T> {
	private Logger LOGGER = LoggerFactory.getLogger(ExternalService.class);
	
	private final String name;
	private final T defaultResult;

	public ExternalService(String name, T result) {
		this.name = name;
		this.defaultResult = result;
	}

	@Override
	public T call() throws Exception {
		LOGGER.info("[{}] call in 1s", name);
		Thread.sleep(1000);
		return defaultResult;
	}

	@Override
	public void run() {
		LOGGER.info("[{}] run in 1s", name);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			LOGGER.error(e.getMessage());
		}
	}

	@Override
	public T get() {
		LOGGER.info("[{}] get in 1s", name);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			LOGGER.error(e.getMessage());
		}
		return defaultResult;
	}

}
