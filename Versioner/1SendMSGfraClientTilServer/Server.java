package com.company;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static ServerSocket serverSocket;
    private static Socket socket;
    public static void main(String[] args) throws IOException {

        //Opret socket.
        serverSocket  = new ServerSocket(1234);
        try{


            //afvent på klient forbinder.
            while (true){

                socket = serverSocket.accept();
                System.out.println("New client request accepted" + socket);

                //input / output
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output = new DataOutputStream(socket.getOutputStream());

                try {

                    //Lyt på beskeder fra klient
                    while(true){
                        String receivedMsg = input.readUTF();
                        //Udskriv beskeder.
                        System.out.println(receivedMsg);
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                socket.close();
            }catch (Exception e){
            }
        }

    }

}
