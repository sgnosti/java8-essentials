package de.sgnosti.playground.java8;

import static org.junit.Assert.*;

import org.junit.Test;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

public class LombokTest {

	@Builder
	@Getter
	@ToString
	@EqualsAndHashCode
	public class User {

		private @NonNull String name;
		private @NonNull String email;
		
	}
	
	@Test
	public void testConstructor() {
		User user = new User ();
		System.out.println(user);
		assertNotNull(user);
	}
	
	@Test
	public void testBuilder() {

	}
}
