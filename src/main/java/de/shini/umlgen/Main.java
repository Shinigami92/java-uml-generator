package de.shini.umlgen;

import java.awt.Color;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Christopher Quadflieg (chrissi92@hotmail.de)
 * @since 1.0.0
 * @version 1.0.0
 */
public class Main {

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

	public static void printUML(Class<?> clazz) {
		System.out.println(clazz.getSimpleName());
		System.out.println("------------------------------------------------------------------------");

		List<Field> declaredFields = Arrays.asList(clazz.getDeclaredFields());
		declaredFields.sort((o1, o2) -> Comparator
				.comparingInt(Field::getModifiers)
				.thenComparing(Field::getName)
				.compare(o1, o2));
		for (Field declaredField : declaredFields) {
			System.out.print(mapModifier(declaredField.getModifiers()));
//			System.out.println(declaredField.getName() + ": " + declaredField.getType().getSimpleName());
			System.out.println(declaredField.getName() + ": " + declaredField.getGenericType().getTypeName() + "   [" + Modifier.toString(declaredField.getModifiers()) + "]");
		}

		System.out.println("------------------------------------------------------------------------");
		Constructor<?>[] constructors = clazz.getDeclaredConstructors();
		for (Constructor<?> constructor : constructors) {
			System.out.print(mapModifier(constructor.getModifiers()));
			System.out.print(clazz.getSimpleName());
			System.out.println(Arrays.stream(constructor.getParameters())
					.map(p -> p.getName() + ":" + p.getType().getSimpleName())
					.collect(Collectors.joining(", ", "(", ")"))
			);
		}

		System.out.println("------------------------------------------------------------------------");
		List<Method> methods = Arrays.asList(clazz.getDeclaredMethods());
		methods.sort((o1, o2) -> Comparator
				.comparingInt(Method::getModifiers)
				.thenComparing(Method::getName)
				.thenComparingInt(Method::getParameterCount)
				.compare(o1, o2));
		for (Method method : methods) {
			System.out.print(mapModifier(method.getModifiers()));
			System.out.print(method.getName());
			System.out.print(Arrays.stream(method.getParameters())
//					.map(p -> p.getName() + ":" + p.getType().getSimpleName())
					.map(p -> p.getName() + ":" + p.getParameterizedType().getTypeName())
					.collect(Collectors.joining(", ", "(", ")"))
			);
			String returnTypeName = method.getGenericReturnType().getTypeName();
			if (!returnTypeName.equals("void")) {
				System.out.print(": " + returnTypeName);
			}
			System.out.println("   [" + Modifier.toString(method.getModifiers()) + "]");
		}
	}

	public static void main(String[] args) {
		printUML(Long.class);
		System.out.println();
		System.out.println();
		printUML(Color.class);
		System.out.println();
		System.out.println();
		printUML(BigInteger.class);
	}

}
