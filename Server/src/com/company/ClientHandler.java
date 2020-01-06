package com.company;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static com.company.Server.*;

public class ClientHandler implements Runnable {

    private DataInputStream input;
    private DataOutputStream output;
    private Socket socket;
    private String user_name;
    boolean isRunning = true;
    int heartbeat = 59;

    public ClientHandler(){}

    public ClientHandler(Socket socket, DataInputStream input, DataOutputStream output, String user_name) {


        this.socket = socket;
        this.input = input;
        this.output = output;
        this.user_name = user_name;
        singlecast("J_OK", "");

        Thread heartbeat = new Thread(() -> {
            while(Server.isRunning){
                try{
                    Thread.sleep(1000);
                    this.heartbeat--;
                    if(this.heartbeat < 0){
                        Server.removeUserFromUserList(socket.getPort());
                    }
                }catch (InterruptedException e){
                    e.printStackTrace();
                }

            }
        });
        heartbeat.start();

    }

    //lyt på beskeder og fra klienter.
    @Override
    public void run() {

        String encryptedMsg = "";
        try {
            //Lyt på beskeder fra klient
            while(isRunning){

                encryptedMsg = input.readUTF();
                String decryptedString = AES.decrypt(encryptedMsg, AES.secretKeyDefined);

                switch (decryptedString){
                    case "QUIT":
                        singlecast(decryptedString, this.user_name);
                        input.close();
                        output.close();
                        socket.close();
                        isRunning = false;
                        System.out.println(user_name + " quitted");
                        Server.removeUserFromUserList(socket.getPort());
                        break;
                    case "LIST":
                        singlecast(getActiveUserList(), "");
                        break;
                    case "IMAV":
                        System.out.println("IMAV recieved from: " + socket);
                        this.heartbeat = 60;
                        break;
                    default:
                        clientSend(decryptedString);
                        break;
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }

    }
    //Bruges til at sende fra en bruger til alle andre
    public void clientSend(String msg) {
        String encryptedMsg = AES.encrypt((user_name + ": " + msg), AES.secretKeyDefined);
        Server.clientSend(encryptedMsg, this.socket.getPort());
    }
    //metode bruges til at sende besked til én client
    public void singlecast(String msg, String user_name) {
        try {
            String encryptedMsg = AES.encrypt((user_name + ":" + msg), AES.secretKeyDefined);
            this.output.writeUTF(encryptedMsg);
            this.output.flush();
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

}
