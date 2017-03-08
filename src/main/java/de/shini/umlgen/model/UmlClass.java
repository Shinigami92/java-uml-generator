package de.shini.umlgen.model;

import de.shini.umlgen.util.UmlHelper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
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
public class UmlClass {

	private final String name;
	private final String modifier;
	private final boolean _abstract;
	private final boolean _static;
	private final boolean _interface;
	private final boolean _enum;
	private final boolean _annotation;
	private UmlClass _extends;
	private final List<UmlClass> _implements;
	private final List<UmlConstructor> constructors;
	private final List<UmlField> fields;
	private final List<UmlMethod> methods;

	public UmlClass(Class<?> clazz) {
		this.name = clazz.getSimpleName();

		final int modifiers = clazz.getModifiers();
		this.modifier = UmlHelper.mapModifier(modifiers);
		this._abstract = Modifier.isAbstract(modifiers);
		this._static = Modifier.isStatic(modifiers);

		this._interface = clazz.isInterface();
		this._enum = clazz.isEnum();
		this._annotation = clazz.isAnnotation();

		this._implements = new ArrayList<>();
		final Class<?>[] interfaces = clazz.getInterfaces();
		for (Class<?> declaredInterface : interfaces) {
			this._implements.add(new UmlClass(declaredInterface));
		}

		Class<?> superclass = clazz.getSuperclass();
		if (superclass != null) {
			this._extends = new UmlClass(superclass);
		}

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

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		if (_implements != null) {
			for (UmlClass _implement : _implements) {
				builder.append(_implement.toString());
				builder.append("\n");
			}
		}
		if (_extends != null) {
//			builder.append(_extends.getName()).append(" <|-- ").append(name).append("\n");
			builder.append(_extends.toString());
			builder.append("\n");
		}

		if (_interface) {
			// interface
			builder.append("interface ").append(name);
			if (_implements != null && !_implements.isEmpty()) {
				builder.append(" extends ");
				builder.append(_implements.stream().map(UmlClass::getName).collect(Collectors.joining(", ")));
			}
			builder.append(" {\n");
		} else {
			// class
			builder.append(_abstract ? "abstract " : "");
			builder.append("class ").append(name);
			if (_extends != null) {
				builder.append(" extends ").append(_extends.getName());
			}
			if (_implements != null && !_implements.isEmpty()) {
				builder.append(" implements ");
				builder.append(_implements.stream().map(UmlClass::getName).collect(Collectors.joining(", ")));
			}
			builder.append(" {\n");
		}

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
