package test.galaxy.language;

import static org.junit.Assert.*;

import org.junit.Test;

import guide.galaxy.language.Roman;

public class RomanTest {

	@Test
	public void toEnglishTest() {
		assertNotEquals(2, Roman.toEnglish("IVI"));
		assertEquals(3, Roman.toEnglish("III"));
		assertEquals(4, Roman.toEnglish("IV"));
		assertEquals(5, Roman.toEnglish("V"));
		assertEquals(6, Roman.toEnglish("VI"));
		assertEquals(7, Roman.toEnglish("VII"));
		assertEquals(8, Roman.toEnglish("VIII"));
		assertEquals(9, Roman.toEnglish("IX"));
		assertEquals(1904, Roman.toEnglish("MCMIV"));
		assertNotEquals(1904, Roman.toEnglish("MCMV"));
		assertEquals(1954, Roman.toEnglish("MCMLIV"));
		assertNotEquals(1953, Roman.toEnglish("MCMLIV"));
		assertEquals(1990, Roman.toEnglish("MCMXC"));
		assertNotEquals(1991, Roman.toEnglish("MCMXC"));
		assertEquals(2014, Roman.toEnglish("MMXIV"));
		assertNotEquals(2016, Roman.toEnglish("MMXIV"));
		assertEquals(22, Roman.toEnglish("XXII"));
		assertNotEquals(21, Roman.toEnglish("XXII"));
	}

	@Test
	public void convertSymbolTest() {
		assertEquals(1, Roman.convertSymbol("I"));
		assertEquals(5, Roman.convertSymbol("V"));
		assertEquals(10, Roman.convertSymbol("X"));
		assertEquals(50, Roman.convertSymbol("L"));
		assertEquals(100, Roman.convertSymbol("C"));
		assertEquals(500, Roman.convertSymbol("D"));
		assertEquals(1000, Roman.convertSymbol("M"));
		assertEquals(1, Roman.convertSymbol("I"));
		assertEquals(1, Roman.convertSymbol("I"));
		assertEquals(1, Roman.convertSymbol("I"));
	}
}
