package guide.galaxy;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import guide.galaxy.parser.LineParser;

public class MerchantReader {

	private static void showUsage() {
		System.out.println("USAGE:\n>java guide.galaxy.MerchantReader <filename>");
	}

	public static void main(String[] args) {

		String filename;

		// Using input file provided as command line argument, or the default
		// input.txt if one is not provided
		if (args.length != 0)
			filename = args[0];
		else
			filename = "input.txt";

		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			String line = null;
			LineParser parser = new LineParser();
			while ((line = br.readLine()) != null)
				parser.parse(line);
		} catch (FileNotFoundException e) {
			System.out.println("The system cannot find the file specified.");
			showUsage();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
