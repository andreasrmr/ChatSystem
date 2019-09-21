package com.company.threads;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class SendMessages implements Runnable {

    static DataOutputStream output;
    static Socket socket;
    static Scanner scan = new Scanner(System.in);
    static String user_name;

    public SendMessages(){}
    public SendMessages(Socket socket, String user_name){
        this.socket = socket;
        this.user_name = user_name;
        try {
            this.output = new DataOutputStream(socket.getOutputStream());
            //Send username til server
            output.writeUTF(user_name);
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        String msgToSend = "";
        System.out.println("Client ready to send messages");
        while(!msgToSend.equals("QUIT")){
            msgToSend = scan.nextLine();
            try{
                output.writeUTF(msgToSend);
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }
    public static void sendMSG(String msg){
        try{
            output.writeUTF(msg);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
