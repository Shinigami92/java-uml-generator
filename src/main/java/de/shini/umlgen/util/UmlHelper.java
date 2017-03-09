package de.shini.umlgen.util;

import de.shini.umlgen.model.UmlClass;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

/**
 *
 * @author Christopher Quadflieg (chrissi92@hotmail.de)
 * @since 1.0.0
 * @version 1.0.0
 */
public class UmlHelper {

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

	public static String generatePlantUML(Class<?> clazz) {
		return new UmlClass(clazz).generatePlantUML();
	}

	public static String generatePlantUML(String packageName) {
		final List<ClassLoader> classLoadersList = new LinkedList<>();
		classLoadersList.add(ClasspathHelper.contextClassLoader());
		classLoadersList.add(ClasspathHelper.staticClassLoader());

		final Reflections reflections = new Reflections(new ConfigurationBuilder()
				.setUrls(Stream.concat(ClasspathHelper.forJavaClassPath().stream(), ClasspathHelper.forClassLoader(classLoadersList.toArray(new ClassLoader[0])).stream()).collect(Collectors.toList()))
				.setScanners(new SubTypesScanner(false /* don't exclude Object.class */), new ResourcesScanner())
				//.filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix(pkg.getName()))));
				//.filterInputsBy(new FilterBuilder().includePackage(pkg.getName()))
				.filterInputsBy(new FilterBuilder().includePackage(packageName))
		);
//		reflections.getAllTypes().stream().forEach(System.out::println);
		final Set<Class<? extends Object>> classes = reflections.getSubTypesOf(Object.class);
		final List<String> generatedClasses = new ArrayList<>();
		final StringBuilder builder = new StringBuilder("@startuml\n");
		for (Class<? extends Object> clazz : classes) {
			UmlClass umlClass = new UmlClass(clazz);
			umlClass.generatePlantUML(generatedClasses, builder);
		}
		builder.append("@enduml\n");
		return builder.toString();
	}

	private UmlHelper() {
	}
}
