package test.galaxy;

import org.junit.Test;

import guide.galaxy.MerchantReader;

public class MerchantReaderTest {

	@Test
	public void testMain() {
		MerchantReader.main(new String[]{"input.txt"});
		MerchantReader.main(new String[]{"no-input.txt"});
	}

}
