package SingleThreadedServer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * A simple TCP client that connects to the server,
 * sends a message, and prints the response.
 */
public class Client
{
    private final String hostname = "localhost";
    private final int port = 8080;

    public void run() {
        try {
            InetAddress serverAddress = InetAddress.getByName(hostname);
            connectAndCommunicate(serverAddress, port);
        } catch (UnknownHostException e) {
            System.err.println("Could not resolve host: " + hostname);
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("I/O error occurred while communicating with the server.");
            e.printStackTrace();
        }
    }

    private void connectAndCommunicate(InetAddress address, int port) throws IOException {
        try (
                Socket socket = new Socket(address, port);
                PrintWriter toServer = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()))
        ) {
            sendMessage(toServer, "hello from the client!");

            String response = receiveMessage(fromServer);
            if (response != null) {
                System.out.println("Server response: " + response);
            } else {
                System.err.println("No response received from the server.");
            }
        }
    }

    private void sendMessage(PrintWriter out, String message) {
        out.println(message);
    }

    private String receiveMessage(BufferedReader in) throws IOException {
        return in.readLine();
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.run();
    }
}
