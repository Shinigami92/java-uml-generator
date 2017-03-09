package de.shini.umlgen.model;

import de.shini.umlgen.util.UmlHelper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 *
 * @author Christopher Quadflieg (chrissi92@hotmail.de)
 * @since 1.0.0
 * @version 1.0.0
 */
public class UmlClass {

	private final String fullname;
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

	private static <T> Predicate<T> not(Predicate<T> t) {
		return t.negate();
	}

	public UmlClass(Class<?> clazz) {
		this.fullname = clazz.getName();
		this.name = clazz.getSimpleName();

		final int modifiers = clazz.getModifiers();
		this.modifier = UmlHelper.mapModifier(modifiers);
		this._abstract = Modifier.isAbstract(modifiers);
		this._static = Modifier.isStatic(modifiers);

		this._interface = clazz.isInterface();
		this._enum = clazz.isEnum();
		this._annotation = clazz.isAnnotation();

		this._implements = new ArrayList<>();
		for (Class<?> declaredInterface : clazz.getInterfaces()) {
			this._implements.add(new UmlClass(declaredInterface));
		}

		Class<?> superclass = clazz.getSuperclass();
		if (superclass != null) {
			this._extends = new UmlClass(superclass);
		}

		// Fields
		this.fields = new ArrayList<>();
		for (Field declaredField : clazz.getDeclaredFields()) {
			this.fields.add(new UmlField(declaredField));
		}

		// Constructors
		this.constructors = new ArrayList<>();
		for (Constructor<?> declaredConstructor : clazz.getDeclaredConstructors()) {
			this.constructors.add(new UmlConstructor(declaredConstructor));
		}

		// Methods
		this.methods = new ArrayList<>();
		for (Method declaredMethod : clazz.getDeclaredMethods()) {
			this.methods.add(new UmlMethod(declaredMethod));
		}
	}

	public String getFullname() {
		return fullname;
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

	public boolean isInterface() {
		return _interface;
	}

	public boolean isEnum() {
		return _enum;
	}

	public boolean isAnnotation() {
		return _annotation;
	}

	public UmlClass getExtends() {
		return _extends;
	}

	public List<UmlClass> getImplements() {
		return Collections.unmodifiableList(_implements);
	}

	public List<UmlConstructor> getConstructors() {
		return Collections.unmodifiableList(constructors);
	}

	public List<UmlField> getFields() {
		return Collections.unmodifiableList(fields);
	}

	public List<UmlMethod> getMethods() {
		return Collections.unmodifiableList(methods);
	}

	public String generatePlantUML() {
		StringBuilder builder = new StringBuilder("@startuml\n");
		generatePlantUML(new ArrayList<>(), builder);
		return builder.append("@enduml\n").toString();
	}

	protected void generatePlantUML(final Collection<String> generatedClasses, final StringBuilder builder) {
		if (_implements != null) {
			_implements.stream()
					.filter(_implement -> !generatedClasses.contains(_implement.getFullname()))
					.map(_implement -> {
						_implement.generatePlantUML(generatedClasses, builder);
						return _implement;
					})
					.forEachOrdered(_item -> builder.append("\n"));
		}
		if (_extends != null && !generatedClasses.contains(_extends.getFullname())) {
			_extends.generatePlantUML(generatedClasses, builder);
			builder.append("\n");
		}

		if (_interface) {
			// interface
			builder.append("interface ").append(fullname);
			if (_implements != null && !_implements.isEmpty()) {
				builder.append(" extends ");
				builder.append(_implements.stream().map(UmlClass::getFullname).collect(Collectors.joining(", ")));
			}
			builder.append(" {\n");
		} else {
			// class
			builder.append(_abstract ? "abstract " : "");
			builder.append("class ").append(fullname);
			if (_extends != null) {
				builder.append(" extends ").append(_extends.getFullname());
			}
			if (_implements != null && !_implements.isEmpty()) {
				builder.append(" implements ");
				builder.append(_implements.stream().map(UmlClass::getFullname).collect(Collectors.joining(", ")));
			}
			builder.append(" {\n");
		}

		fields.stream().filter(not(UmlField::isSynthetic)).forEach(field -> builder.append(field).append("\n"));
		constructors.forEach(constructor -> builder.append(constructor).append("\n"));
		methods.stream().filter(not(UmlMethod::isSynthetic)).forEach(method -> builder.append(method).append("\n"));
		builder.append("}\n");
		generatedClasses.add(fullname);
	}

	@Override
	public String toString() {
		return generatePlantUML();
	}
}
