package de.sgnosti.playground.util;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@Builder
@Getter
@ToString
@EqualsAndHashCode
public class User {

	private @NonNull String name;
	private long timestamp;
	private @NonNull String text;
	
}
