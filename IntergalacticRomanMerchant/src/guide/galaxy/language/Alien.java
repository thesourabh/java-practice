package guide.galaxy.language;

import java.util.HashMap;

public class Alien {

	// Alien allows object creation because there can be multiple alien language
	// mappings unlike Roman-to-English

	private HashMap<String, String> map;

	public Alien() {
		map = new HashMap<String, String>();
	}

	public void learn(String alien, String roman) {
		map.put(alien, roman);
	}

	public boolean isAlien(String alien) {
		return map.containsKey(alien);
	}

	private String convertSymbol(String alien) {
		if (map.containsKey(alien)) {
			return map.get(alien);
		}
		return "0";
	}

	public String toRoman(String alien) {
		String roman = "";
		String[] symbols = alien.split(" ");
		for (String s : symbols) {
			roman += convertSymbol(s);
		}
		return roman;
	}
}
