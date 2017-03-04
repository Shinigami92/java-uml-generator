package de.shini.umlgen.util;

import java.lang.reflect.Modifier;

/**
 *
 * @author Christopher Quadflieg (chrissi92@hotmail.de)
 * @since 1.0.0
 * @version 1.0.0
 */
public class UmlHelper {

	public static String mapModifier(int mod) {
		if (Modifier.isPublic(mod)) {
			return "+";
		} else if (Modifier.isProtected(mod)) {
			return "#";
		} else if (Modifier.isPrivate(mod)) {
			return "-";
		}
		return "~";
	}

	private UmlHelper() {
	}
}
