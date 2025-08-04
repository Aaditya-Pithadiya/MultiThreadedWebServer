package MultiThreaded;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;


public class Client {

    private final String hostname = "localhost";
    private final int port = 8010;

     /*
       Returns a Runnable task that connects to the server and communicates once.
     */
    public Runnable getRunnable() {
        return () -> {
            try {
                InetAddress address = InetAddress.getByName(hostname);
                try (
                        Socket socket = new Socket(address, port);
                        PrintWriter toSocket = new PrintWriter(socket.getOutputStream(), true);
                        BufferedReader fromSocket = new BufferedReader(new InputStreamReader(socket.getInputStream()))
                ) {
                    // Send message to server
                    toSocket.println("Hello from Client " + socket.getLocalSocketAddress());

                    // Read response from server
                    String response = fromSocket.readLine();
                    if (response != null) {
                        System.out.println("Response from Server: " + response);
                    } else {
                        System.err.println("No response from server.");
                    }

                }
            } catch (IOException e) {
                System.err.println("Client connection failed: " + e.getMessage());
            }
        };
    }

    public static void main(String[] args) {
        Client client = new Client();

        for (int i = 0; i < 100; i++) {
            Thread thread = new Thread(client.getRunnable());
            thread.start();
        }
    }
}
