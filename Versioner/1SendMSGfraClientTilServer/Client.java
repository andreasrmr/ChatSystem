package com.company;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {

    //porten som server lytter på.
    final static int serverPort = 1234;

    public static void main(String[] args) throws UnknownHostException, IOException {

        //Scanner bruges til input fra brugeren.
        Scanner scan = new Scanner(System.in);

        //localhost = 127.0.0.1 os selv.
        InetAddress ip = InetAddress.getByName("localhost");

        //opret forbindelse til server
        Socket s = new Socket(ip, serverPort);

        //modtag besked fra server. //ikke i brug her.
        DataInputStream dis = new DataInputStream(s.getInputStream());

        //Send besked til server
        DataOutputStream dos = new DataOutputStream(s.getOutputStream());


        while(true){
            //Skriv besked til
            String msg = scan.nextLine();

            //Send besked til server.
            dos.writeUTF(msg);
        }
    }

}
