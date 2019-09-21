package com.company;

import com.company.threads.ReadMessages;
import com.company.threads.SendMessages;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    static String user_name;
    static String server_ip;
    static int server_port;

    public Client(){}

    public static void start() throws IOException {

        //Vent for join message
        waitForUserInput();

        //Convert String ip til InetAddress ip.
        InetAddress ip = InetAddress.getByName(server_ip);

        //opret forbindelse til server
        Socket socket = new Socket(ip, server_port);

        ReadMessages readMessages = new ReadMessages(socket);
        SendMessages sendMessages = new SendMessages(socket, user_name);

        Thread readMsg = new Thread(readMessages, "ReadMessage");
        Thread sendMsg = new Thread(sendMessages, "SendMessage");

        readMsg.start();
        sendMsg.start();

        Thread heartBeat = new Thread(() -> {
            while(true){
                try{
                    Thread.sleep(55000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
                SendMessages.sendMSG("IMAV");
            }
        });
        heartBeat.start();

        while(sendMsg.isAlive()){
        }

        }

        //Bruger får lov til at skrive input
        //TODO Hardcoded pt. brugeren skal selv lave input
        public static void waitForUserInput(){

            //Scanner bruges til input fra brugeren.
            Scanner scan = new Scanner(System.in);
            //scan.nextLine();
            server_port = 1234;
            System.out.println("Enter username");
            user_name = scan.nextLine();
            server_ip = "127.0.0.1";

            //Todo Verificer JOIN string på klient.
            //
            //if(connectStr.matches("(^join\\s\\w,\\s){1}(\\d\\.){3}\\d:(\\d){1,5}"))
            //Eksempel: Join John, 127.0.0.1:2001
        }


}
