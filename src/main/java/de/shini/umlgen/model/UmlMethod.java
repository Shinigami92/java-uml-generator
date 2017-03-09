package de.shini.umlgen.model;

import de.shini.umlgen.util.UmlHelper;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
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
public class UmlMethod {

	private final String name;
	private final String modifier;
	private final boolean _abstract;
	private final boolean _static;
	private final boolean bridge;
	private final boolean synthetic;
	private final String returnType;
	private final List<UmlMethodParameter> parameters;

	public UmlMethod(Method declaredMethod) {
		this.name = declaredMethod.getName();
		final int modifiers = declaredMethod.getModifiers();
		this.modifier = UmlHelper.mapModifier(modifiers);
		this._abstract = Modifier.isAbstract(modifiers);
		this._static = Modifier.isStatic(modifiers);
		this.returnType = declaredMethod.getGenericReturnType().getTypeName();
		this.bridge = declaredMethod.isBridge();
		this.synthetic = declaredMethod.isSynthetic();
		this.parameters = new ArrayList<>();
		for (Parameter parameter : declaredMethod.getParameters()) {
			this.parameters.add(new UmlMethodParameter(parameter));
		}
	}

	public String getName() {
		return name;
	}

	public String getModifier() {
		return modifier;
	}

	public boolean isAbstract() {
		return _abstract;
	}

	public boolean isStatic() {
		return _static;
	}

	public boolean isBridge() {
		return bridge;
	}

	public boolean isSynthetic() {
		return synthetic;
	}

	public String getReturnType() {
		return returnType;
	}

	public List<UmlMethodParameter> getParameters() {
		return Collections.unmodifiableList(parameters);
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		if (_abstract) {
			builder.append("{abstract}");
		} else if (_static) {
			builder.append("{static}");
		}
		builder.append(modifier);
		builder.append(name);
		builder.append("(");
		builder.append(parameters.stream().map(UmlMethodParameter::toString).collect(Collectors.joining(", ")));
		builder.append(")");
		if (!"void".equals(returnType)) {
			builder.append(": ").append(returnType);
		}
		if (synthetic) {
			builder.append(" __synthetic__");
		}
		if (bridge) {
			builder.append(" __bridge__");
		}
		return builder.toString();
	}

}
