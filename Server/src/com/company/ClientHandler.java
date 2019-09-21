package com.company;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


public class ClientHandler extends Protocol implements Runnable {

    final DataInputStream input;
    final DataOutputStream output;
    Socket socket;
    String user_name;

    public ClientHandler(Socket socket, DataInputStream input, DataOutputStream output, String user_name) {
        super.J_OK(socket, input, output);
        this.socket = socket;
        this.input = input;
        this.output = output;
        this.user_name = user_name;
    }

    //lyt på beskeder og fra klienter.
    @Override
    public void run() {
        String receivedMsg;
        try {
            //Lyt på beskeder fra klient
            while(true){
                receivedMsg = input.readUTF();
                //Udskriv beskeder.
                if(receivedMsg.equals("QUIT")){
                    super.QUIT(socket, user_name);
                }
                else if(receivedMsg.equals("IMAV")){
                    super.IMAV(user_name);
                }
                else {
                    System.out.println(receivedMsg);
                }

            }
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
