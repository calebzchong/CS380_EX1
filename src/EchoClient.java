import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ConnectException;
import java.net.Socket;
import java.util.Scanner;

public final class EchoClient {

    public static void main(String[] args) throws Exception {
        try (Socket socket = new Socket("localhost", 22222)) {
            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is, "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            Scanner kb = new Scanner(System.in);
            String input;
            OutputStream os = socket.getOutputStream();
            PrintStream out = new PrintStream(os, true, "UTF-8");
            System.out.println("Server> " + br.readLine());
       		System.out.print("Client> ");
            input = kb.nextLine();
            while( !input.equals("exit") ) {
                out.printf("%s%n", input);
                while ( !br.ready() ){
                	Thread.sleep(5);
                }
        		System.out.println("Server> " + br.readLine());
           		System.out.print("Client> ");
                input = kb.nextLine();
                if ( input.equals("exit") ){
                	kb.close();
                	out.close();
                }
        	}
        } catch (ConnectException e ){
        	System.out.println("Could not connect to server. Terminating...");
        }
    }
    
}















