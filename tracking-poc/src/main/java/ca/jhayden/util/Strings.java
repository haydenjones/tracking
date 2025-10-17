package ca.jhayden.util;

public class Strings {
	private Strings() {
		super();
	}

	public static String trim(Object o) {
		return (o == null) ? "" : o.toString().trim();
	}

	public static String nullIfEmpty(Object o) {
		String s = trim(o);
		if (!s.isEmpty()) {
			return s;
		}
		return null;
	}

	public static String notEmpty(Object value, String context) {
		final var s = trim(value);
		if (s.isEmpty()) {
			throw new IllegalArgumentException(context);
		}
		return s;
	}
}
