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
    private static Socket socket;
    private static List<Thread> threadList = new ArrayList<>();

    public Server(int port_number) throws IOException {

        //Opret socket til server på port.
        serverSocket = new ServerSocket(port_number);

        //start Server
        startServer();


    }
    public static void startServer(){

            try {
                //afvent på klient forbinder.
                while (true) {

                    socket = serverSocket.accept();
                    System.out.println("New client request accepted" + socket);


                    //input / output
                    DataInputStream input = new DataInputStream(socket.getInputStream());
                    DataOutputStream output = new DataOutputStream(socket.getOutputStream());

                    output.writeUTF("J_OK");

                    //Modtag brugernavn.
                    String user_name = "";
                    while (user_name.equals("")) {
                        user_name = input.readUTF();
                    }
                    System.out.println("Username: " + user_name + " Received");


                    Thread t = new Thread(() -> {
                        Client client = new Client(socket, input, output);
                        client.run();
                    });

                    t.setDaemon(true);
                    t.start();

                    threadList.add(t);


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


