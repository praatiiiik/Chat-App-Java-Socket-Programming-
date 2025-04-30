package SocketProgramming.MultiThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MServerSide {

    public static void main(String[] args) {
        int port = 1234;
        usingExecutor(port);
    }

    private static void  usingExecutor(int port){
        ExecutorService exe = Executors.newFixedThreadPool(2);
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started. Listening on port " + port + "...");

            while (true) {
                Socket clientSocket = serverSocket.accept(); // wait for new client
                System.out.println("New client connected: " + clientSocket.getInetAddress());
                ClientHandler handler = new ClientHandler(clientSocket);
                exe.execute(handler);
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    private static void usingThread(int port){
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started. Listening on port " + port + "...");

            while (true) {
                Socket clientSocket = serverSocket.accept(); // wait for new client
                System.out.println("New client connected: " + clientSocket.getInetAddress());

                // Create and start new thread for each client
                ClientHandler handler = new ClientHandler(clientSocket);
                new Thread(handler).start();
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
