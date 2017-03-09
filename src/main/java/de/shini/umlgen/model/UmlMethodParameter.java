package de.shini.umlgen.model;

import java.lang.reflect.Parameter;

/**
 *
 * @author Christopher Quadflieg (chrissi92@hotmail.de)
 * @since 1.0.0
 * @version 1.0.0
 */
public class UmlMethodParameter {

	private final String name;
	private final String type;

	public UmlMethodParameter(Parameter parameter) {
		this.name = parameter.getName();
		this.type = parameter.getParameterizedType().getTypeName();
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	@Override
	public String toString() {
		return name + ": " + type;
	}

}
