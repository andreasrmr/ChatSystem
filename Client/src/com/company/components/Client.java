package com.company.components;

import com.company.InputHandler;
import com.company.InputVerification;
import com.company.Main;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;


public class Client implements Runnable{

    static String user_name;
    static String server_ip;
    static int server_port;
    Socket socket;
    DataOutputStream output;
    DataInputStream input;

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

            //Opret Separat Thread til at recieve messages.
            Thread recieve = new Thread(() -> {
                String msg;
                while(Main.isRunning == true){
                    try{
                        msg = input.readUTF();
                        switch (msg){
                            case "QUIT":
                                Main.isRunning = false;
                                input.close();
                                socket.close();
                                break;
                            default:
                                System.out.println(msg);
                                break;
                        }
                    }catch (IOException e){
                        e.printStackTrace();
                        Main.isRunning = false;
                    }
                }
            });

            recieve.start();
            String msg = "";

            //Send messages
            while(Main.isRunning == true){
                msg = InputHandler.readString();
                switch (msg){
                    //close prorgam
                    case "QUIT":
                        output.writeUTF("QUIT");
                        output.flush();
                        output.close();
                        socket.close();
                        Main.isRunning = false;
                        break;

                    //DATA message
                    default:
                        if(InputVerification.chkDataMSG(msg)){
                            String[] temp = msg.split(":");
                            output.writeUTF(temp[1]);
                            output.flush();
                        }
                        else{
                            System.out.println("Error write QUIT or DATA:message");
                        }
                        break;
                }
            }

        }catch (IOException e){
            e.printStackTrace();
            Main.isRunning = false;
        }




    }
}

