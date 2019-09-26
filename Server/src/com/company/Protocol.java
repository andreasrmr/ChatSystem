package com.company;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.*;


public class Protocol {

    public static List<ClientHandler> clientHandler;



    public Protocol() {
        //https://www.geeksforgeeks.org/java-singleton-design-pattern-practices-examples/
        clientHandler = new Vector<>();
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
    /*
    public static void IMAV(String user_name){
        for(int i = 0; i < clientList.size(); i++){
            if(clientList.get(i).getUser_name().equals(user_name)){
                clientList.get(i).setHeartbeat(60);
                System.out.println("IMAV set for user: " + user_name);
            }
        }
    }
*/

    public void removeFromList(String user_name){
        for(int i = 0; i < clientHandler.size(); i++){
            if(clientHandler.get(i).getUser_name().equals(user_name)){
                clientHandler.remove(i);
            }
        }
    }


    public void waitForMSG(){

    }




    public void addClient(ClientHandler clientHandler){
        this.clientHandler.add(clientHandler);
    }
    /*
    public void addThread(Thread thread) {
        threadList.add(thread);
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
    */

    public boolean nameCheck(String user_name) {
        //TODO skal testes + der skal sættes flere constraints op, se krav spec.
        if (this.clientHandler.size() != 0) {
            for (ClientHandler client : clientHandler) {
                if (client.getUser_name().equals(user_name)) {
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

    /*
    public void multiCast(Socket socket, DataOutputStream output, String msg){

        for(Client client : clientList){
            try{

                output.writeUTF(msg);
            }catch (IOException e){
                e.printStackTrace();
            }

        }
    }

     */

}