package com.company;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLOutput;
import java.util.Scanner;

public class Threads {

    static DataOutputStream output;
    static DataInputStream input;
    Socket socket;
    static Scanner scan;
    public static Thread sendMessage;
    static String user_name;


    public Threads(Socket socket, String user_name) {
        this.socket = socket;
        this.user_name = user_name;

    }
    private static class ConnectionThread implements Runnable {
        public ConnectionThread(){

        }
        @Override
        public void run(){

        }
    }
    private static class SendMessages extends ConnectionThread{

    }
    private static class ReadMessages extends ConnectionThread{

    }
}
/*
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



    public static void startSendMessage(){

    }
*/

