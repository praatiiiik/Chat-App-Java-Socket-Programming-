package SocketProgramming.ChatApp;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MessageHandler implements Runnable {
    private final Socket clientSocket;
    private volatile boolean running = true;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public MessageHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            ClientConnection connection = new ClientConnection(clientSocket);
            connection.send("Hello");
            String username = connection.readLine();

            UserSession session = new UserSession(username, connection);
            MessageRouter.addUser(username);

            handleIncomingMessages(session);
            listenAndForwardMessages(session);

        } catch (IOException e) {
            System.err.println("Error in MessageHandler: " + e.getMessage());
        }
    }

    private void handleIncomingMessages(UserSession session) {
        executor.submit(() -> {
            try {
                while (running) {
                    String msg = MessageRouter.readMessage(session.getUsername());
                    session.sendMessage(msg);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void listenAndForwardMessages(UserSession session) {
        try {
            String input;
            while ((input = session.receiveMessage()) != null) {
                if ("break".equalsIgnoreCase(input)) {
                    running = false;
                    break;
                }
                String[] parts = input.split(":", 2);
                if (parts.length == 2) {
                    String receiver = parts[0];
                    String message = parts[1];
                    MessageRouter.sendMessage(receiver, message);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
