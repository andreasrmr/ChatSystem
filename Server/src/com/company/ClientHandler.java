package com.company;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static com.company.Server.clientHandlers;

public class ClientHandler implements Runnable {

    private DataInputStream input;
    private DataOutputStream output;
    private Socket socket;
    private String user_name;
    boolean isRunning = true;

    public ClientHandler(){}

    public ClientHandler(Socket socket, DataInputStream input, DataOutputStream output, String user_name) {


        this.socket = socket;
        this.input = input;
        this.output = output;
        this.user_name = user_name;
        singlecast(user_name + " was added");
        singlecast("J_OK");


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
                        singlecast(decryptedString);
                        input.close();
                        output.close();
                        socket.close();
                        isRunning = false;
                        System.out.println(user_name + " quitted");
                        Server.removeUserFromUserList(socket.getPort());
                        break;
                    case "IMAV":
                        //TODO: ikke funtionelt
                        System.out.println("IMAV recieved from: " + user_name);
                        List<Thread> threadPool = new ArrayList<>();
                        Thread t = new Thread(() -> {
                            try{
                                Thread.sleep(59);
                            }catch (InterruptedException e){
                                e.printStackTrace();
                            }
                        });
                        t.start();


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
    public void clientSend(String msg) {
        String encryptedMsg = AES.encrypt((user_name + ": " + msg), AES.secretKeyDefined);
        Server.clientSend(encryptedMsg, this.socket.getPort());
    }
    //metode bruges til at sende besked til én client
    public void singlecast(String msg) {
        try {
            String encryptedMsg = AES.encrypt((user_name + ": " + msg), AES.secretKeyDefined);
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
