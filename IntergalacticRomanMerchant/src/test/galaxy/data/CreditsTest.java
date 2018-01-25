package test.galaxy.data;

import static org.junit.Assert.*;

import org.junit.Test;

import guide.galaxy.data.Credits;

public class CreditsTest {

	@Test
	public void testCredits() {

		assertEquals("32", Credits.format(32));
		assertEquals("6453", Credits.format(6453));
		
		assertEquals("32.67", Credits.format(32.67));
		assertEquals("32.67", Credits.format(32.672));
		assertEquals("32.67", Credits.format(32.6723));
		assertEquals("32.67", Credits.format(32.67235));
		
		assertNotEquals("6453", Credits.format(6453.35));
	}

}
