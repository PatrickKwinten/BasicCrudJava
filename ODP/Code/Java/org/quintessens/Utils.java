package org.quintessens;

import lotus.domino.Base;

public class Utils {

	public static String strLeft(String input, String delimiter) {
		if (input == null) {
			return "";
		}
		return input.indexOf(delimiter) == -1 ? "" : input.substring(0, input.indexOf(delimiter));
	}
	
	public static String strRight(String input, String delimiter) {
		if (input == null) {
			return "";
		}
		return input.indexOf(delimiter) == -1 ? "" : input.substring(input.indexOf(delimiter) + delimiter.length());
	}
	
	public static String strLeftBack(String input, String delimiter) {
		if (input == null) {
			return "";
		}
		return input.indexOf(delimiter) == -1 ? "" : input.substring(0, input.lastIndexOf(delimiter));
	}
	
	public static String strRightBack(String input, String delimiter) {
		if (input == null) {
			return "";
		}
		return input.indexOf(delimiter) == -1 ? "" : input.substring(input.lastIndexOf(delimiter) + delimiter.length());
	}
	
	public static void recycleDominoObjects(Object... dominoObjects) {
		// http://stackoverflow.com/questions/11159444/what-is-the-best-way-to-recycle-domino-objects-in-java-beans/11160925#11160925
		// (Tim Tripcony)
		for (Object dominoObject : dominoObjects) {
			if (null != dominoObject) {
				if (dominoObject instanceof Base) {
					try {
						((Base) dominoObject).recycle();
					} catch (Exception ignore) {
					}
				}
			}
		}
	}
	
}
