package test.galaxy.language;

import static org.junit.Assert.*;

import org.junit.Test;

import guide.galaxy.language.Alien;

public class AlienTest {

	@Test
	public void totalAlienTest() {
		Alien alien = new Alien();
		alien.learn("glob", "I");
		alien.learn("prok", "V");
		alien.learn("pish", "X");
		alien.learn("tegj", "L");

		assertTrue(alien.isAlien("glob"));
		assertTrue(alien.isAlien("prok"));
		assertFalse(alien.isAlien("glok"));
		assertFalse(alien.isAlien("posh"));
		assertTrue(alien.isAlien("pish"));
		assertTrue(alien.isAlien("tegj"));

		assertEquals(alien.toRoman("glob glob"), "II");
		assertEquals(alien.toRoman("prok glob glob glob"), "VIII");
		assertEquals(alien.toRoman("pish tegj"), "XL");
		assertEquals(alien.toRoman("tegj pish pish"), "LXX");
		assertEquals(alien.toRoman("glob pish"), "IX");
	}

}
