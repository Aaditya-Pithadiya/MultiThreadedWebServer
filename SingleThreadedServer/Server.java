package SingleThreadedServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * A single-threaded TCP server that accepts client connections,
 * reads their input, and sends a personalized response.
 */
public class Server {

    private final int port = 8080;

    public void run() {
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(5000);  // Optional: 5 sec timeout for accepting
            System.out.println("Server started on port " + port);

            while (true) {
                System.out.println("Waiting for client on port " + port + "...");

                Socket clientSocket = null;

                // Only wrap accept() in the SocketTimeoutException-catching block
                try {
                    clientSocket = serverSocket.accept();  
                } catch (SocketTimeoutException e) {
                    System.out.println("No client connected within timeout period.");
                    continue;  // continue waiting for clients
                } catch (IOException e) {
                    System.err.println("Error accepting connection: " + e.getMessage());
                    e.printStackTrace();
                    continue;
                }

                // Proceed with communication if a client was accepted
                try (
                        Socket socket = clientSocket;
                        PrintWriter toClient = new PrintWriter(socket.getOutputStream(), true);
                        BufferedReader fromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()))
                ) {
                    System.out.println("Connection accepted from " +
                            socket.getInetAddress() + ":" + socket.getPort());

                    String clientMessage = fromClient.readLine();
                    if (clientMessage != null) {
                        System.out.println("Client says: " + clientMessage);
                        toClient.println("Hello from the server! You said: " + clientMessage);
                    } else {
                        System.out.println("Client sent no data.");
                        toClient.println("No input received from client.");
                    }

                } catch (IOException e) {
                    System.err.println("Error during client communication: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.err.println("Could not start server on port " + port + ": " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (serverSocket != null && !serverSocket.isClosed()) {
                try {
                    serverSocket.close();
                    System.out.println("Server socket closed.");
                } catch (IOException e) {
                    System.err.println("Failed to close server socket: " + e.getMessage());
                }
            }
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        try {
            server.run();
        } catch (Exception e) {
            System.err.println("An error occurred while running the server: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
