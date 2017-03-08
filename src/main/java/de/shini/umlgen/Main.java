package de.shini.umlgen;

import de.shini.umlgen.model.UmlClass;

import java.awt.Color;
import java.math.BigInteger;

/**
 *
 * @author Christopher Quadflieg (chrissi92@hotmail.de)
 * @since 1.0.0
 * @version 1.0.0
 */
public class Main {

	public static void main(String[] args) throws Exception {
		System.out.println(new UmlClass(Long.class));
//		System.out.println(new UmlClass(Process.class));
//		System.out.println();
//		System.out.println(new UmlClass(BigInteger.class));
//		System.out.println();
//		System.out.println(new UmlClass(Color.class));
	}

}
