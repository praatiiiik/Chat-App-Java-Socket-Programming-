package SocketProgramming.ChatApp;

import java.io.IOException;

public class UserSession {
    private final String username;
    private final ClientConnection connection;

    public UserSession(String username, ClientConnection connection) {
        this.username = username;
        this.connection = connection;
    }

    public String getUsername() {
        return username;
    }

    public void sendMessage(String msg) {
        connection.send(msg);
    }

    public String receiveMessage() throws IOException {
        return connection.readLine();
    }

    public void close() throws IOException {
        connection.close();
    }
}

