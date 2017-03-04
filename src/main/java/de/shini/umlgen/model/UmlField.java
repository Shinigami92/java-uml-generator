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
	private final String type;

	public UmlField(Field field) {
		this.name = field.getName();
		this.type = field.getGenericType().getTypeName();
		final int modifiers = field.getModifiers();
		this.modifier = UmlHelper.mapModifier(modifiers);
		this._static = Modifier.isStatic(modifiers);
	}

	@Override
	public String toString() {
		return (_static ? "{static}" : "") + modifier + name + ": " + type;
	}

}
