package SocketProgramming.ChatApp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatAppServer {

    public static void main(String[] args) {
        usingExecutor(Util.PORT);
    }

    private static void  usingExecutor(int port){
        ExecutorService exe = Executors.newFixedThreadPool(5);
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started. Listening on port " + port + "...");
            while (true) {
                Socket clientSocket = serverSocket.accept(); // wait for new client
                System.out.println("New client connected: " + clientSocket.getInetAddress());
                MessageHandler handler = new MessageHandler(clientSocket);
                exe.execute(handler);
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


}
