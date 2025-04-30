package SocketProgramming.MultiThread;

import java.io.*;
import java.net.*;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try (
                InputStream input = clientSocket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));

                OutputStream output = clientSocket.getOutputStream();
                PrintWriter writer = new PrintWriter(output, true);
        ) {
            String message;
            writer.println("Welcome! Type 'exit' to quit.");

            while ((message = reader.readLine()) != null) {
                System.out.println("Received from " + clientSocket.getInetAddress() + ": " + message);
                if (message.equalsIgnoreCase("exit")) {
                    writer.println("Goodbye!");
                    break;
                }
                writer.println("Echo: " + message + Thread.currentThread().getName());
            }

        } catch (IOException ex) {
            System.out.println("Error with client " + clientSocket.getInetAddress() + ": " + ex.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException ex) {
                System.out.println("Error closing socket: " + ex.getMessage());
            }
        }
    }
}

