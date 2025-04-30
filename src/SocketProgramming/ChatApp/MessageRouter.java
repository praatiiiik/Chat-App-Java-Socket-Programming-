package SocketProgramming.ChatApp;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

public class MessageRouter {
    static final Map<String, BlockingQueue<String>> threadQueues = new ConcurrentHashMap<>();

    static void addUser(String name){
        threadQueues.putIfAbsent(name, new LinkedBlockingQueue<>());
        System.out.println("adduser :" + threadQueues.entrySet());
    }

    static void sendMessage(String user, String message){
        try{
            threadQueues.get(user).put(message);
            System.out.println("sendMessage :" + threadQueues.entrySet());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    static String readMessage(String user) {
        System.out.println("readMessage :" + threadQueues.entrySet());
        try{
            return threadQueues.get(user).take();
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }
}
