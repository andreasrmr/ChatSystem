package com.company;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class Protocol {
    public static List<Thread> threadList = new ArrayList<>();
    public static List<Client> clientList = new ArrayList<>();

    public Protocol() {
    }
    public static void J_OK(Socket socket, DataInputStream input, DataOutputStream output) {
        try {
            output.writeUTF("J_OK");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void J_ERR(String error_code){
        System.out.println("TODO: error code needs to be sent to client name not ok etc.");
    }
    public static void IMAV(String user_name){
        for(int i = 0; i < clientList.size(); i++){
            if(clientList.get(i).getUser_name().equals(user_name)){
                clientList.get(i).setHeartbeat(60);
                System.out.println("IMAV set for user: " + user_name);
            }
        }
    }
    

    public static void QUIT(Socket socket, String user_name) {
        try {
            socket.close();
            removeThread(user_name);
            removeClient(user_name);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addThread(Thread thread) {
        threadList.add(thread);
    }
    public void addClient(Client client){
        clientList.add(client);
    }
    public static void removeThread(String user_name){
        for(int i = 0; i < threadList.size(); i++){
            if(threadList.get(i).getName().equals(user_name)){
                threadList.get(i).interrupt();
                threadList.remove(i);
                System.out.println("Thread was removed");
            }
        }
    }
    public static void removeClient(String user_name){
        for(int i = 0; i < clientList.size(); i++){
            if(clientList.get(i).getUser_name().equals(user_name)){
                clientList.remove(i);
                System.out.println("client was removed");
            }
        }
    }

    public boolean nameCheck(String user_name) {
        //TODO skal testes + der skal sættes flere constraints op, se krav spec.
        if (threadList.size() != 0) {
            for (Thread thread : threadList) {
                if (thread.getName().equals(user_name)) {
                    //send error message to client.
                    return false;
                } else {
                    return true;
                }
            }
        }
        //hvis threadList.size() = 0 - er brugernavn ok.
        return true;
    }

}