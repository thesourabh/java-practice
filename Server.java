import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Server {
	public static void main(String args[]) {
		ServerSocket serv = null;
		Socket client = null;
try {
		serv = new ServerSocket(12345);
		client = serv.accept();

		InputStream is = null;
		OutputStream os = null;
		BufferedReader br = null;

		is = client.getInputStream();
		os = client.getOutputStream();
		br = new BufferedReader(new InputStreamReader(is));
					System.out.println("aaaaaa" + br.readLine());

		PrintWriter pw = new PrintWriter(os, true);
					pw.println("Hello");
					pw.close();

				br.close();
				is.close();
				os.close();
				client.close();
				serv.close();
			}catch (IOException ioe) {}
	}
}