package SocketProgramming.SingleThread;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSide {
    public static void main(String[] args) throws IOException {

        /*
        the server is opening a door (socket) on port 1234.
        Clients will connect to this port.
         */
        ServerSocket serverSocket = new ServerSocket(1234);

        System.out.println("Server is listening on port 1234...");

        Socket acceptedConnection = serverSocket.accept(); // waits for a client
        System.out.println("Connection accepted from client "+ acceptedConnection.getRemoteSocketAddress());

        /*
         * .getInputStream() returns an InputStream object from the socket.
         * This stream is used to receive bytes from the client.
         *  Think of it as a pipe from the client to the server.
         */
        InputStream input = acceptedConnection.getInputStream();

        /*
         * We can’t easily read raw bytes from the InputStream,
         * so we wrap it to read text more conveniently.
         */
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));

        OutputStream output = acceptedConnection.getOutputStream();
        PrintWriter writer = new PrintWriter(output, true);

        writer.println("From Server");

        String text;
        /*
        reader.readLine() is a blocking call, it waits (blocks) until:
        1)The client sends a line and presses Enter (or sends \n) or
        2)The connection is closed (EOF)
         */
        while ((text = reader.readLine()) != null) {
            System.out.println("Received: " + text);
            writer.println("Echo: " + text); // echo back to client
        }

        acceptedConnection.close();
        serverSocket.close();
    }
}
