import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	public static void main(String args[]) throws IOException {
		Socket clientSock = null;
		try {
			clientSock = new Socket("localhost", 12345);
		} catch (UnknownHostException uhe) {
			System.out.println("Host unknown.");
			System.exit(1);
		} catch (IOException ie) {
			System.out
					.println("Could not connect to target computer.\nThe program may not be running on the target computer.");
			System.exit(1);
		}
		PrintWriter pw = new PrintWriter(clientSock.getOutputStream());
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String line = "";
		pw.println("YO");
		BufferedReader br1 = new BufferedReader(new InputStreamReader(clientSock.getInputStream()));
		System.out.println("bbbbbb" + br1.readLine());
		br1.close();
		pw.close();
		clientSock.close();
	}
}