package guide.galaxy.data;

public class Credits {
	
	private float value;
	
	public Credits(float value) {
		this.value = value;
	}
	
	public String toString() {
		return format(value);
	}
	
	// A way to display two decimal places iff there is a non-zero decimal part
	
	public static String format(double f) {
		String cost = String.format("%.2f", f);
		if (cost.endsWith(".00"))
			return cost.split("\\.")[0];
		return cost;
	}
}
