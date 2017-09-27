import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

public final class EchoServer {

	public static void main(String[] args) throws Exception {
		try (ServerSocket serverSocket = new ServerSocket(22222)) {
			System.out.println("Waiting for connection...");
			while (true) {
				Socket socket = serverSocket.accept();
				Runnable server = () -> {
					try {
						String address = socket.getInetAddress().getHostAddress();
						System.out.printf("Client connected: %s%n", address);
						OutputStream os;
						os = socket.getOutputStream();
						PrintStream out = new PrintStream(os, true, "UTF-8");
						out.printf("Hi %s, thanks for connecting!%n", address);
						InputStream is = socket.getInputStream();
						InputStreamReader isr = new InputStreamReader(is, "UTF-8");
						BufferedReader br = new BufferedReader(isr);

						while ( true ){
							String str = br.readLine();
							if ( str == null ){
								System.out.printf("Client disconnected: %s%n", address);
								break;
							} else {
								out.printf("%s%n", str);
							}
							Thread.sleep(10);
						}
						socket.close();
					} catch (Exception e) {
						System.out.println("Something happened lol.");
						e.printStackTrace();
					}
				};
				Thread serverThread = new Thread(server);
				serverThread.start();
			}
		} catch ( BindException e ){
			System.out.println( "A server is already running, terminating...");
		}
	}
}
