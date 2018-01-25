package guide.galaxy.data;

import java.util.HashMap;

public class Products {
	
	// This is a catalogue of products that maps a product with its price

	private HashMap<String, Double> map;
	
	public Products() {
		map = new HashMap<String, Double>();
	}

	public void setCost(int quantity, String product, double totalCost) {
		map.put(product, totalCost / quantity);
	}

	public double getCost(String product, int quantity) {
		if (!map.containsKey(product))
			return 0;
		double cost = map.get(product) * quantity;
		return cost;
	}
}
