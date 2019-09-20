package com.company.threads;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class SendMessages implements Runnable {

    static DataOutputStream output;
    static Socket socket;
    static Scanner scan = new Scanner(System.in);

    public SendMessages(Socket socket){
        this.socket = socket;
        try {
            this.output = new DataOutputStream(socket.getOutputStream());
        }catch (IOException e){
            e.printStackTrace();
        }
        run();
    }

    @Override
    public void run() {
        Thread sendMessage = new Thread (() -> {
            System.out.println("Client ready to send messages");
            try {
                String msgToSend = "";
                while (!msgToSend.equals("logout")){
                    //Send besked til server.
                    msgToSend = scan.nextLine();
                    output.writeUTF(msgToSend);
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        });
        sendMessage.setDaemon(true);
        sendMessage.start();
    }
}
