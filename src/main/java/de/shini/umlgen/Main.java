package de.shini.umlgen;

import de.shini.umlgen.model.UmlClass;

import java.awt.Color;
import java.math.BigInteger;

import sun.nio.ch.FileChannelImpl;

/**
 *
 * @author Christopher Quadflieg (chrissi92@hotmail.de)
 * @since 1.0.0
 * @version 1.0.0
 */
public class Main {

	public static void main(String[] args) throws Exception {
//		System.out.println(new UmlClass(Long.class).generatePlantUML());
//		System.out.println(new UmlClass(Process.class).generatePlantUML());
//		System.out.println(new UmlClass(BigInteger.class).generatePlantUML());
//		System.out.println(new UmlClass(Color.class).generatePlantUML());
		System.out.println(new UmlClass(FileChannelImpl.class).generatePlantUML());
	}

}
