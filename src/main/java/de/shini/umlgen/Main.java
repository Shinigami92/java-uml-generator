package de.shini.umlgen;

import de.shini.umlgen.util.UmlHelper;

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
//		System.out.println(new UmlClass(FileChannelImpl.class).generatePlantUML());
//		System.out.println(new UmlClass(FXMLLoader.class).generatePlantUML());
//		System.out.println(UmlHelper.generatePlantUML(Long.class.getPackage()));
//		System.out.println(UmlHelper.generatePlantUML(Main.class.getPackage()));
		System.out.println(UmlHelper.generatePlantUML("javafx.geometry"));
	}

}
