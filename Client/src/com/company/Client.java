package com.company;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    static String user_name;
    static String server_ip;
    static int server_port;
    Socket socket;

    public Client(String user_name, String server_ip, int server_port){
        this.user_name = user_name;
        this.server_ip = server_ip;
        this.server_port = server_port;

        try {
            start();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void start() throws IOException {

        //List<Thread> threads = new ArrayList<>();

        //Convert String ip til InetAddress ip.
        InetAddress ip = InetAddress.getByName(server_ip);

        //opret forbindelse til server
        this.socket = new Socket(ip, server_port);

        DataOutputStream output = new DataOutputStream(socket.getOutputStream());
        DataInputStream input = new DataInputStream(socket.getInputStream());

        //Send username som det første
        output.writeUTF(user_name);




        //Scanner
        Scanner scanner = new Scanner(System.in);

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
                }
            }
        });

        Thread heartbeat = new Thread(() -> {
           while(Main.isRunning == true){
               try{
                   Thread.sleep(59000   );
                   output.writeUTF("IMAV");
               }catch (InterruptedException e){
                   e.printStackTrace();
               }catch (IOException e){
                   e.printStackTrace();
               }
           }

        });

        recieve.start();
        heartbeat.start();
        String msg = "";

        //Send messages
        while(Main.isRunning == true){
            msg = scanner.nextLine();
            switch (msg){
                //close prorgam
                case "QUIT":
                    output.writeUTF("QUIT");
                    output.flush();
                    output.close();
                    heartbeat.interrupt();
                    recieve.interrupt();
                    socket.close();
                    Main.isRunning = false;
                    break;
                //DATA message
                default:
                    //if(msg.matches("^DATA [A-Za-z0-9]{1,12}:.*")){
                    if(InputVerification.chkDataMSG(msg)){
                        String[] temp = msg.split(":");
                        output.writeUTF(temp[1]);
                    }
                    else{
                        System.out.println("Error write QUIT or DATA username:message");
                    }
                    break;
            }


        }
    }
}

