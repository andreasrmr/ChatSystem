package com.company;

import com.company.threads.Heartbeart;
import com.company.threads.ReadMessages;
import com.company.threads.SendMessages;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Client {


    static String user_name;
    static String server_ip;
    static int server_port;
    static Threads threads;

    public static void main(String[] args) throws UnknownHostException, IOException {


        //Vent for join message
        waitForUserInput();

        //Convert String ip til InetAddress ip.
        InetAddress ip = InetAddress.getByName(server_ip);

        //opret forbindelse til server
        Socket socket = new Socket(ip, server_port);

        //TODO får jeg brug for disse her? input / output
        //Opret InputStream - modtag fra server
        DataInputStream input = new DataInputStream(socket.getInputStream());

        //Opret OutputStream send til server
        DataOutputStream output = new DataOutputStream(socket.getOutputStream());

        ReadMessages readMessages = new ReadMessages(socket);
        SendMessages sendMessages = new SendMessages(socket, user_name);
        Heartbeart heartbeat = new Heartbeart();

        Thread readMsg = new Thread(readMessages, "ReadMessage");
        Thread sendMsg = new Thread(sendMessages, "SendMessage");
        //Thread heartBeat = new Thread(heartbeat, "Heartbeat");
        readMsg.setDaemon(true);
        sendMsg.setDaemon(true);
       // heartBeat.setDaemon(true);
        readMsg.start();
        sendMsg.start();
        //heartBeat.start();

        Thread heartBeat = new Thread(() -> {
            while(true){
                try{
                    Thread.sleep(60000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
                SendMessages.sendMSG("IMAV");
            }
        });
        heartBeat.setDaemon(true);
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
            user_name = "Johnny";
            server_ip = "127.0.0.1";

            //Todo Verificer JOIN string på klient.
            //
            //if(connectStr.matches("(^join\\s\\w,\\s){1}(\\d\\.){3}\\d:(\\d){1,5}"))
            //Eksempel: Join John, 127.0.0.1:2001
        }


}
