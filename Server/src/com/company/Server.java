package com.company;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

    private static ServerSocket serverSocket;
    static Protocol protocol;


    public Server(int port_number) throws IOException {

        //Opret socket til server på port.
        serverSocket = new ServerSocket(port_number);

        //Opret protocol
        protocol = new Protocol();


        startServer();

    }
    public static void startServer(){
        Socket socket = new Socket();
            try {
                //afvent på klient forbinder.
                while (true) {

                    socket = serverSocket.accept();
                    System.out.println("New client request accepted" + socket);

                    //input / output
                    DataInputStream input = new DataInputStream(socket.getInputStream());
                    DataOutputStream output = new DataOutputStream(socket.getOutputStream());

                    //Modtag brugernavn.
                    String user_name = "";
                    while (user_name.equals("")) {
                        user_name = input.readUTF();
                    }
                    System.out.println("Username: " + user_name + " Received");

                    if(protocol.nameCheck(user_name)){
                        ClientHandler clientHandler = new ClientHandler(socket, input, output, user_name);
                        Thread clientThread = new Thread(clientHandler, user_name);
                        clientThread.start();
                        protocol.addThread(clientThread);
                        protocol.addClient(new Client(user_name, socket.getPort(), socket.getInetAddress(), 60));

                    }
                    else {
                        protocol.J_ERR("username not correct");
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();

                } catch (Exception e) {
                }
            }

        }
    }


