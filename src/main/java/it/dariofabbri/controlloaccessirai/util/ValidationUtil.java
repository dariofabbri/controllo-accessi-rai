package it.dariofabbri.controlloaccessirai.util;

import java.util.regex.Pattern;

public class ValidationUtil {

	private static Pattern integerPattern = Pattern.compile("^\\d*$");

	public static boolean isEmpty(String s) {
		
		return s == null || s.trim().length() == 0;
	}
	
	public static boolean isInteger(String s) {
	
		return integerPattern.matcher(s.trim()).matches();
	}
}
