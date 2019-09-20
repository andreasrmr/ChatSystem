package com.company;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Threads implements Runnable  {

    static DataOutputStream output;
    static DataInputStream input;
    Socket socket;
    static Scanner scan;
    public static Thread sendMessage;
    static String user_name;


    public Threads(Socket socket, String user_name){
        this.socket = socket;
        this.user_name = user_name;
        scan = new Scanner(System.in);
        try {
            output = new DataOutputStream(socket.getOutputStream());
            input = new DataInputStream(socket.getInputStream());
        }catch (IOException e){
            e.printStackTrace();
        }
        run();
    }

    @Override
    public void run() {
        connectionProtocol();
    }

    public static void connectionProtocol() {

        //Modtag besked fra server at der er connected
        String connOk = "";
        try {
            connOk = input.readUTF();
            if (connOk.equals("J_OK")) {
                System.out.println("Client connected successfully");
                startReadMessage();
                startSendMessage();

                //Send brugernavn som første besked.
                output.writeUTF(user_name);

            } else {
                System.out.println("Error message");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void startReadMessage(){
        //Thread der afventer på indkommende beskeder
        Thread readMessage = new Thread();
            System.out.println("Client ready to receive messages");
            try {
                while(true){
                    System.out.println(input.readUTF());
                }
            }catch(IOException e){
                e.printStackTrace();
            }
    }

    public static void startSendMessage(){
        sendMessage = new Thread();

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

        sendMessage.setDaemon(true);
        sendMessage.run();
    }

}
