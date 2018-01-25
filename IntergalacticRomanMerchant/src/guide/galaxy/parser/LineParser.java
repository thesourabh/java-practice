package guide.galaxy.parser;

import guide.galaxy.data.Credits;
import guide.galaxy.data.Products;
import guide.galaxy.language.Alien;
import guide.galaxy.language.Roman;

public class LineParser {

	// Object creation is allowed in case of multiple instances requiring
	// separate alien language dictionaries
	private Alien alienLang;
	private Products products;

	public LineParser() {
		alienLang = new Alien();
		products = new Products();
	}

	public void parse(String line) {
		String[] symbols = line.split(" ");
		if (symbols.length == 3 && symbols[1].equalsIgnoreCase("is")) {
			if (!assignValue(symbols[0], symbols[2]))
				handleInvalidQuery();
		} else if (symbols[0].equalsIgnoreCase("how")) {
			handleQuestions(symbols);
		} else if (symbols[symbols.length - 1].equalsIgnoreCase("Credits")) {
			setProductCost(symbols);
		} else {
			handleInvalidQuery();
		}
	}

	// In case the Roman value is not a valid numeral, return false to call
	// the invalid query handler.
	private boolean assignValue(String alienWord, String romanWord) {
		boolean isValid = Roman.isValidNumeral(romanWord);
		if (isValid)
			alienLang.learn(alienWord, romanWord);
		return isValid;
	}

	// Sets the cost of a product in the product catalogue.
	private void setProductCost(String[] symbols) {
		int len = symbols.length;
		int cost = Integer.parseInt(symbols[len - 2]);
		String product = symbols[len - 4];
		String alienQuantity = alienQueryBuilder(symbols, 0, len - 4);
		int quantity = Roman.toEnglish(alienLang.toRoman(alienQuantity));
		if (alienQuantity != null)
			products.setCost(quantity, product, cost);
	}

	private void handleQuestions(String[] symbols) {
		int len = symbols.length;
		if (!symbols[len - 1].equals("?")) {
			handleInvalidQuery();
		} else if (symbols[1].equalsIgnoreCase("much") && symbols[2].equalsIgnoreCase("is")) {
			handleHowMuch(symbols);
		} else if (symbols[1].equalsIgnoreCase("many") && symbols[2].equalsIgnoreCase("Credits")
				&& symbols[3].equalsIgnoreCase("is")) {
			handleHowMany(symbols, 4);
		} else {
			handleInvalidQuery();
		}
	}

	// If the query is valid, this method returns the cost of some quantity of a
	// product.
	private void handleHowMany(String[] symbols, int start) {
		int len = symbols.length;
		String product = symbols[len - 2];
		String alien = alienQueryBuilder(symbols, start, len - 2);
		if (alien != null) {
			double cost = products.getCost(product, Roman.toEnglish(alienLang.toRoman(alien)));
			System.out.println(alien + " " + product + " is " + Credits.format(cost) + " Credits");
		} else {
			handleInvalidQuery();
		}
	}

	// This method converts alien numbers to English
	private void handleHowMuch(String[] symbols) {
		String alienValue = alienQueryBuilder(symbols, 3, symbols.length - 1);
		if (alienValue != null)
			System.out.println(alienValue + " is " + Roman.toEnglish(alienLang.toRoman(alienValue)));
	}

	// This method uses a beginning and ending index to concatenate only alien
	// language symbols into a single string.
	private String alienQueryBuilder(String[] symbols, int start, int end) {
		String alienValue = "";
		for (int i = start; i < end; i++) {
			if (alienLang.isAlien(symbols[i])) {
				alienValue += symbols[i] + " ";
			} else {
				handleInvalidQuery();
				return null;
			}
		}
		alienValue = alienValue.substring(0, alienValue.length() - 1);
		return alienValue;
	}

	private void handleInvalidQuery() {
		System.out.println("I have no idea what you are talking about");
	}

}
