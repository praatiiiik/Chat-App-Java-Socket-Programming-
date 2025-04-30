package SocketProgramming.MultiThread;

import java.io.*;
import java.net.*;

public class MClientSide {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 1234);
             BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))
        ) {
            String serverResponse;
            while ((serverResponse = reader.readLine()) != null) {
                System.out.println("Server: " + serverResponse);
                if (serverResponse.contains("Goodbye")) break;

                System.out.print("You: ");
                String userInput = console.readLine();
                writer.println(userInput);
                if ("exit".equalsIgnoreCase(userInput)) break;
            }

        } catch (IOException e) {
            System.out.println("Client error: " + e.getMessage());
        }

    }
}



