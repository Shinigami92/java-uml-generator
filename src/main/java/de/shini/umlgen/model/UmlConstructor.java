package de.shini.umlgen.model;

import de.shini.umlgen.util.UmlHelper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Christopher Quadflieg (chrissi92@hotmail.de)
 * @since 1.0.0
 * @version 1.0.0
 */
public class UmlConstructor {

	private final String name;
	private final String modifier;
	private final List<UmlMethodParameter> parameters;

	public UmlConstructor(Constructor<?> constructor) {
		this.name = constructor.getDeclaringClass().getSimpleName();
		this.modifier = UmlHelper.mapModifier(constructor.getModifiers());
		this.parameters = new ArrayList<>();
		for (Parameter parameter : constructor.getParameters()) {
			this.parameters.add(new UmlMethodParameter(parameter));
		}
	}

	public String getName() {
		return name;
	}

	public String getModifier() {
		return modifier;
	}

	public List<UmlMethodParameter> getParameters() {
		return Collections.unmodifiableList(parameters);
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append(modifier);
		builder.append(name);
		builder.append("(");
		builder.append(parameters.stream().map(UmlMethodParameter::toString).collect(Collectors.joining(", ")));
		builder.append(")");
		return builder.toString();
	}

}
