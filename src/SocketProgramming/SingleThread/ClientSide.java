package SocketProgramming.SingleThread;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class ClientSide {

    public static void main(String[] args) throws IOException {
        InetAddress address = InetAddress.getByName("localhost");
        Socket socket = new Socket(address, 1234);

        OutputStream output = socket.getOutputStream();
        PrintWriter writer = new PrintWriter(output, true);

        writer.println("hello from client");

        InputStream input = socket.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));

        System.out.println("message from server is "+reader.readLine());

        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
        String text;

        System.out.println("Enter messages to send to server (type 'exit' to quit):");
        while (!(text = console.readLine()).equalsIgnoreCase("exit")) {
            writer.println(text);
            String response = reader.readLine();
            System.out.println("Server replied: " + response);
        }

        socket.close();
    }
}
