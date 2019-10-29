package com.company;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static com.company.Server.clientHandlers;

public class ClientHandler implements Runnable {

    private DataInputStream input;
    private DataOutputStream output;
    private Socket socket;
    private String user_name;
    boolean isRunning = true;
    int heartbeat;

    public ClientHandler(){}

    public ClientHandler(Socket socket, DataInputStream input, DataOutputStream output, String user_name) {


        this.socket = socket;
        this.input = input;
        this.output = output;
        this.user_name = user_name;
        this.heartbeat = 60;

        singlecast(user_name + " was added");
        singlecast("J_OK");
    }

    //lyt på beskeder og fra klienter.
    @Override
    public void run() {
        String encryptedMsg;
        try {
            //Lyt på beskeder fra klient
            while(isRunning){

                encryptedMsg = input.readUTF();
                final String secretKey = "WorrisomePurveyorGlorify3";
                String decryptedString = AES.decrypt(encryptedMsg, secretKey);

                switch (decryptedString){
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
                        msgAll(decryptedString);
                        break;
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }

    }
    public void msgAll(String msg) {
        Server.broadcast(msg, this.socket.getPort());
    }
    //metode bruges til at sende besked til én client
    public void singlecast(String msg) {
        try {
            this.output.writeUTF("Server: " + msg);
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
