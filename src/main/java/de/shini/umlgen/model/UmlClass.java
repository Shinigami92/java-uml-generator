package de.shini.umlgen.model;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Christopher Quadflieg (chrissi92@hotmail.de)
 * @since 1.0.0
 * @version 1.0.0
 */
public class UmlClass {

	private final String name;
	private String modifier; //TODO: set modifier
	private boolean _abstract; //TODO: set _abstract
	private boolean _static; //TODO: set _static
	private UmlClass _extends; //TODO: set _extends
	private List<UmlClass> _implements; //TODO: set _implements
	private final List<UmlConstructor> constructors;
	private final List<UmlField> fields;
	private final List<UmlMethod> methods;

	public UmlClass(Class<?> clazz) {
		this.name = clazz.getSimpleName();

		// Fields
		this.fields = new ArrayList<>();
		final List<Field> declaredFields = Arrays.asList(clazz.getDeclaredFields());
		for (Field declaredField : declaredFields) {
			this.fields.add(new UmlField(declaredField));
		}

		// Constructors
		this.constructors = new ArrayList<>();
		final List<Constructor<?>> declaredConstructors = Arrays.asList(clazz.getDeclaredConstructors());
		for (Constructor<?> declaredConstructor : declaredConstructors) {
			this.constructors.add(new UmlConstructor(declaredConstructor));
		}

		// Methods
		this.methods = new ArrayList<>();
		final List<Method> declaredMethods = Arrays.asList(clazz.getDeclaredMethods());
		for (Method declaredMethod : declaredMethods) {
			this.methods.add(new UmlMethod(declaredMethod));
		}
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("class ").append(name).append(" {\n");
		for (UmlField field : fields) {
			builder.append(field).append("\n");
		}
		for (UmlConstructor constructor : constructors) {
			builder.append(constructor).append("\n");
		}
		for (UmlMethod method : methods) {
			builder.append(method).append("\n");
		}
		builder.append("}\n");
		return builder.toString();
	}
}
