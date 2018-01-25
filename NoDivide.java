
public class NoDivide {
	public static void main(String[] args) {
		int[] arr = {1, 2, 3, 4, 5};
		int len = arr.length, prod;
		for (int i = 0; i < len; i++) {
			prod = 1;
			for (int j = 0; j < len; j++) {
				if (j == i) continue;
				prod *= arr[j];
			}
			System.out.println(prod);
		}
	}
}