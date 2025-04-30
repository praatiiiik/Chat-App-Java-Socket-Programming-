package SocketProgramming.ChatApp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Util {

    static final String LOCAL_HOST = "localhost";
    static final String ENTER_NAME = "Enter Name";

    public static int PORT = 1234;

    public static BufferedReader getSocketReader(Socket socket) throws IOException {
        return new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public static PrintWriter getSocketWriter(Socket socket) throws IOException {
        return new PrintWriter(socket.getOutputStream(), true);
    }

    public static BufferedReader getConsoleReader(){
        return new BufferedReader(new InputStreamReader(System.in));
    }

    public static void print(String str){
        System.out.println(str);
    }
}

/**
 * cd C:\Users\...\src
 * javac SocketProgramming\ChatApp\*.java
 * java SocketProgramming.ChatApp.ChatAppClient
 */

/**
 * Functions — static or non-static — do NOT go into the heap.
 */