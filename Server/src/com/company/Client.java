package com.company;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client implements Runnable {

    final DataInputStream input;
    final DataOutputStream output;
    Socket socket;
    String user_name;


    public Client(Socket socket, DataInputStream input, DataOutputStream output) {
        this.socket = socket;
        this.input = input;
        this.output = output;

    }

    //lyt på beskeder og udskriv.
    @Override
    public void run() {
        String receivedMsg;

        try {
            //Lyt på beskeder fra klient
            while(true){
                receivedMsg = input.readUTF();
                //Udskriv beskeder.
                if(receivedMsg.equals("logout")){
                    socket.close();
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
