package test.galaxy.parser;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.junit.Test;

import guide.galaxy.parser.LineParser;

// So that the method calls are done in the required order
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LineParserTest {
	
	private String[] lines;
	private LineParser parser;
	
	@BeforeClass
	public void setUp() {
		parser = new LineParser();
	}

	@Test
	public void assignTest() {
		String assignString = "glob is I\nprok is V\npish is X\ntegj is L";
		lines = assignString.split("\n");
		for (int i = 0; i < lines.length; i++)
			parser.parse(lines[i]);
	}
	
	@Test
	public void giveValueTest() {
		String valueString = "glob glob Silver is 34 Credits\nglob prok Gold is 57800 Credits\npish pish Iron is 3910 Credits";
		lines = valueString.split("\n");		
		for (int i = 0; i < lines.length; i++)
			parser.parse(lines[i]);
	}
	
	@Test
	public void questionTest() throws IOException {
		String questionString = "how much is pish tegj glob glob ?\nhow many Credits is glob prok Silver ?\nhow many Credits is glob prok Gold ?\nhow many Credits is glob prok Iron ?\nhow much wood could a woodchuck chuck if a woodchuck could chuck wood ?";
		lines = questionString.split("\n");
		
		/* Testing the output printed to System.out by diverting it to a ByteArrayOutputStream.
		 * Need to add carriage return and newline to testing strings since the output stream
		 * captures and stores it too.
		 */
		PrintStream original = System.out;
	    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	    System.setOut(new PrintStream(outputStream));
	    
		parser.parse(lines[0]);
		assertEquals(outputStream.toString(), "pish tegj glob glob is 42\r\n");
		
		resetAndParse(lines[1], outputStream);
		assertEquals(outputStream.toString().substring(0,  32), "glob prok Silver is 68 Credits\r\n");
		
		resetAndParse(lines[2], outputStream);
		assertEquals(outputStream.toString(), "glob prok Gold is 57800 Credits\r\n");
		
		resetAndParse(lines[3], outputStream);
		assertEquals(outputStream.toString(), "glob prok Iron is 782 Credits\r\n");
		
		resetAndParse(lines[4], outputStream);
		assertEquals(outputStream.toString(), "I have no idea what you are talking about\r\n");

		resetAndParse("how much is tonk ?", outputStream);
		assertEquals(outputStream.toString(), "I have no idea what you are talking about\r\n");

		resetAndParse("The Iron Throne is mine by right.", outputStream);
		assertEquals(outputStream.toString(), "I have no idea what you are talking about\r\n");
	    
		// Setting system output stream back to default
		System.setOut(original);
	}
	
	// Separate method because of multiple calls to reset
	private void resetAndParse(String s, ByteArrayOutputStream p) {
		p.reset(); // This clears the previous data in the output stream
		parser.parse(s);
	}





}
