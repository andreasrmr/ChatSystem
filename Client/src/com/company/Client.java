package com.company;

import com.company.components.Heartbeat;
import com.company.components.Recieve;
import com.company.components.Send;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Client implements Runnable{

    static String user_name;
    static String server_ip;
    static int server_port;
    Socket socket;

    public Client() {
    }

    public Client(String user_name, String server_ip, int server_port){
        this.user_name = user_name;
        this.server_ip = server_ip;
        this.server_port = server_port;
    }

    public void run(){
        try {
            //Convert String ip til InetAddress ip.
            InetAddress ip = InetAddress.getByName(server_ip);

            //opret forbindelse til server
            this.socket = new Socket(ip, server_port);

            DataOutputStream output = new DataOutputStream(socket.getOutputStream());
            DataInputStream input = new DataInputStream(socket.getInputStream());

            //Send username som det første
            output.writeUTF(user_name);
            output.flush();

            //opret recieve thread.
            Thread threadRecieve = new Thread(new Recieve(input));

            //Opret send thread
            Thread threadSend = new Thread(new Send(output));

            //opret heartbeat thread
            Thread threadHeartBeat = new Thread(new Heartbeat());

            //Start threads.
            threadRecieve.start();
            threadSend.start();
            threadHeartBeat.start();


            while(Main.isRunning){

            }


        }catch (IOException e){
            e.printStackTrace();
            Main.isRunning = false;
        }




    }
}

