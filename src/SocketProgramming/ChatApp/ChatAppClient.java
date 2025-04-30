package SocketProgramming.ChatApp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatAppClient {

    private static BufferedReader console, reader;
    private static PrintWriter writer;
    private static String user;


    public static void main(String[] args) {

        try {
            Socket socket = createSocket();
            updateMessengers(socket);
            getUser();
            startChat();
        } catch (IOException e) {
            Util.print("Client error: " + e.getMessage());
        }
    }

    private static void getUser() throws IOException{
        Util.print(Util.ENTER_NAME);
        user = console.readLine();
        writer.println(user);
    }

    private static void startChat(){
        ExecutorService exe = Executors.newFixedThreadPool(2);
        exe.execute(ChatAppClient::readIncomingMessage);
        exe.execute(ChatAppClient::handleConsoleInput);
    }

    private static Socket createSocket() throws IOException {
        return new Socket(Util.LOCAL_HOST, Util.PORT);
    }

    private static void updateMessengers(Socket socket) throws IOException {
        console = Util.getConsoleReader();
        writer = Util.getSocketWriter(socket);
        reader = Util.getSocketReader(socket);
    }

    private static void readIncomingMessage() {
        String serverResponse;
        try{
            while ((serverResponse = reader.readLine()) != null) {
                Util.print("From Server: " + serverResponse);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private static void handleConsoleInput(){
        try{
            String userInput;
            while ((userInput = console.readLine()) !=null){
                writer.println(userInput);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
