package test.galaxy.data;

import static org.junit.Assert.*;

import org.junit.Test;

import guide.galaxy.data.Products;

public class ProductsTest {

	@Test
	public void productsTest() {
		Products products = new Products();

		products.setCost(3, "Silver", 30);
		products.setCost(10, "Gold", 3000);
		products.setCost(1, "Dirt", 1);
		products.setCost(5, "Valyrian", 2000);
		products.setCost(7, "Iron", 35);
		
		assertEquals(400, products.getCost("Valyrian", 1), 0.01);
		assertEquals(10, products.getCost("Iron", 2), 0.01);
		assertEquals(10, products.getCost("Dirt", 10), 0.01);
		assertEquals(1500, products.getCost("Gold", 5), 0.01);
		assertEquals(500, products.getCost("Silver", 50), 0.01);
		

		assertNotEquals(401, products.getCost("Valyrian", 1), 0.01);
		assertNotEquals(10, products.getCost("Iron", 3), 0.01);
		assertNotEquals(10, products.getCost("Dirt", 11), 0.01);
		assertNotEquals(1500, products.getCost("Gold", 6), 0.01);
		assertNotEquals(600, products.getCost("Silver", 40), 0.01);
	}

}
