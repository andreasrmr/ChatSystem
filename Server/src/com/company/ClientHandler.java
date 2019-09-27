package com.company;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private DataInputStream input;
    private DataOutputStream output;
    private Socket socket;
    private String user_name;
    boolean isRunning = true;
    int heartbeat;

    public ClientHandler(){}

    public ClientHandler(Socket socket, DataInputStream input, DataOutputStream output, String user_name) {
        try{
            output.writeUTF("J_OK");
        }catch (IOException e){
            e.printStackTrace();
        }

        this.socket = socket;
        this.input = input;
        this.output = output;
        this.user_name = user_name;
        this.heartbeat = 60;
    }

    //lyt på beskeder og fra klienter.
    @Override
    public void run() {
        String receivedMsg;
        try {
            //Lyt på beskeder fra klient
            while(isRunning){
                receivedMsg = input.readUTF();
                ClientList cList = ClientList.getInstance();
                switch (receivedMsg){
                    case "QUIT":
                        output.writeUTF("QUIT");
                        output.flush();
                        input.close();
                        socket.close();
                        isRunning = false;
                        System.out.println(user_name + " quitted");
                        break;
                    case "IMAV":
                        System.out.println("IMAV recieved from: " + user_name);
                        this.heartbeat = 60;
                        break;
                    default:
                        for(ClientHandler c : cList.getActiveClients()){
                            if(!c.getUser_name().equals(this.user_name)){
                                c.getOutput().writeUTF(this.user_name + ": " + receivedMsg);
                            }
                        }
                        //udskriv til server
                        System.out.println(this.user_name + ": " + receivedMsg);
                        break;
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }

    }
    public void stopRunning(){
        isRunning = false;
    }

    public DataInputStream getInput() {
        return input;
    }

    public DataOutputStream getOutput() {
        return output;
    }

    public Socket getSocket() {
        return socket;
    }

    public String getUser_name() {
        return user_name;
    }

    public boolean getIsRunning() {
        return isRunning;
    }

    public void setIsRunning(boolean flag) {
        this.isRunning = flag;
    }

    public int getHeartbeat() {
        return heartbeat;
    }

    public void setHeartbeat(int heartbeat) {
        this.heartbeat = heartbeat;
    }
}
