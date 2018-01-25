package guide.galaxy.language;

import java.util.HashMap;
import java.util.regex.Pattern;

public class Roman {
	
	// Since the Roman to English mapping is fixed, multiple instances aren't required.
	
	private static HashMap<String, Integer> dict = new HashMap<String, Integer>();
	
	static {
		dict.put("I", 1);
		dict.put("V", 5);
		dict.put("X", 10);
		dict.put("L", 50);
		dict.put("C", 100);
		dict.put("D", 500);
		dict.put("M", 1000);
	}
	
	// Checks if a given Roman numeral is valid
	public static boolean isValidNumeral(String symbol) {
		return dict.containsKey(symbol);
	}
	
	// Converts a single Roman numeral to its equivalent Arabic integer representation
	public static int convertSymbol(String symbol) {
		if (isValidNumeral(symbol))
			return dict.get(symbol);
		return 0;
	}
	
	// Uses regex to check whether the given Roman numeral value is a valid one
	private static boolean isValidRoman(String roman) {
		final String romanRegex = "M{0,3}?(CM|DC{0,3}|CD|C{0,3})?(XC|LX{0,3}|XL|X{0,3})?(IX|VI{0,3}|IV|I{0,3})";
		return Pattern.matches(romanRegex, roman);
	}
	
	public static int toEnglish(String roman) {
		
		if (!isValidRoman(roman)) {
			return 0;
		}
		
		int value = 0;

		// The Roman number is split into individual Roman numerals
		String[] symbols = roman.split("");
		int len = symbols.length;
		
		for (int i = 0; i < len; i++) {
			int c = convertSymbol(symbols[i]);
			
			if (i < (len - 1)) {
				// Checking the next symbol in case subtraction is required
				int next = convertSymbol(symbols[i + 1]);
				if (next > c) {
					c = next - c;
					i++;
				}
			}
			// Adding the value of the current numeral to the total
			value += c;
		}
		return value;
	}
	
}
