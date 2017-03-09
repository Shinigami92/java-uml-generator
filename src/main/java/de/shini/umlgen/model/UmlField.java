package de.shini.umlgen.model;

import de.shini.umlgen.util.UmlHelper;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 *
 * @author Christopher Quadflieg (chrissi92@hotmail.de)
 * @since 1.0.0
 * @version 1.0.0
 */
public class UmlField {

	private final String name;
	private final String modifier;
	private final boolean _static;
	private final boolean synthetic;
	private final String type;

	public UmlField(Field field) {
		this.name = field.getName();
		this.type = field.getGenericType().getTypeName();
		final int modifiers = field.getModifiers();
		this.modifier = UmlHelper.mapModifier(modifiers);
		this._static = Modifier.isStatic(modifiers);
		this.synthetic = field.isSynthetic();
	}

	public String getName() {
		return name;
	}

	public String getModifier() {
		return modifier;
	}

	public boolean isStatic() {
		return _static;
	}

	public boolean isSynthetic() {
		return synthetic;
	}

	public String getType() {
		return type;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		if (_static) {
			builder.append("{static}");
		}
		builder.append(modifier).append(name).append(": ").append(type);
		if (synthetic) {
			builder.append(" __synthetic__");
		}
		return builder.toString();
	}

}
