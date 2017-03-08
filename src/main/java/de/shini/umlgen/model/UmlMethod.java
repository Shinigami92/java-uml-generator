package de.shini.umlgen.model;

import de.shini.umlgen.util.UmlHelper;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Christopher Quadflieg (chrissi92@hotmail.de)
 * @since 1.0.0
 * @version 1.0.0
 */
public class UmlMethod {

	private final String name;
	private final String modifier;
	private final boolean _abstract;
	private final boolean _static;
	private final String returnType;
	private final List<UmlMethodParameter> parameters;

	public UmlMethod(Method declaredMethod) {
		this.name = declaredMethod.getName();
		final int modifiers = declaredMethod.getModifiers();
		this.modifier = UmlHelper.mapModifier(modifiers);
		this._abstract = Modifier.isAbstract(modifiers);
		this._static = Modifier.isStatic(modifiers);
		this.returnType = declaredMethod.getGenericReturnType().getTypeName();
		this.parameters = new ArrayList<>();
		final List<Parameter> methodParameters = Arrays.asList(declaredMethod.getParameters());
		for (Parameter methodParameter : methodParameters) {
			this.parameters.add(new UmlMethodParameter(methodParameter));
		}
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append((_abstract ? "{abstract}" : ""));
		builder.append((_static ? "{static}" : ""));
		builder.append(modifier);
		builder.append(name);
		builder.append("(");
		builder.append(parameters.stream().map(UmlMethodParameter::toString).collect(Collectors.joining(", ")));
		builder.append(")");
		builder.append(!"void".equals(returnType) ? ": " + returnType : "");
		return builder.toString();
	}

}
