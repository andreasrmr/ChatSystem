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

                    //Modtag brugernavn.
                    String user_name = "";
                    while (user_name.equals("")) {
                        user_name = input.readUTF();
                    }
                    System.out.println("Username: " + user_name + " Received");


                    //namecheck
                    //Tester at to ikke kan hedde det samme.
                    //TODO skal testes.
                    boolean nameCheck = false;
                    if(threadList.size() != 0){
                        for(Thread thread : threadList){
                            if(thread.getName().equals(user_name)){
                                //send error message to client.
                                System.out.println("Name check");
                            }
                            else {
                                nameCheck = true;
                            }
                        }
                    }
                    else {
                        nameCheck = true;
                    }
                    if(nameCheck = true){
                        output.writeUTF("J_OK");
                        Client client = new Client(socket, input, output);
                        Thread newClient = new Thread(client, user_name);
                        //newClient.setDaemon(true);
                        newClient.start();
                        threadList.add(newClient);
                    }
                    else {
                        output.writeUTF("error JOIN not ok");
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


