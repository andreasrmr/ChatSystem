package com.company;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Server implements Runnable{

    private static ServerSocket serverSocket;
    static boolean isRunning = true;
    List<Thread> threads;
    public static List<ClientHandler> clientHandlers;

    public Server(int port_number) throws IOException {

        //Opret socket til server på port.
        serverSocket = new ServerSocket(port_number);
        threads = new ArrayList<>();

        //Concurrent lists - fundet her pt. 13 https://www.codejava.net/java-core/collections/java-list-collection-tutorial-and-examples
        clientHandlers = Collections.synchronizedList(new ArrayList<>());

    }

    public void run(){
            Socket socket;
            while(isRunning){
                try {
                    //afvent på klient forbinder.
                    System.out.println("waiting for clients");
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

                    ClientHandler clientHandler = new ClientHandler(socket, input, output, user_name);
                    Thread clientThread = new Thread(clientHandler);
                    clientThread.start();

                    threads.add(clientThread);
                    clientHandlers.add(clientHandler);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        //Metode sender besked ud til alle klienter. (undtagen sig selv)
        public static void broadcast(String msg, int port){
            for(ClientHandler c : clientHandlers){
                if(c.getSocket().getPort() != port){
                    try{
                        c.getOutput().writeUTF(c.getUser_name() + ": " + msg);
                        c.getOutput().flush();
                    }catch (IOException e){
                        e.printStackTrace();
                    }

                }
            }
        }


        /*
        //ryd op i aktive klient liste. (Virker ikke efter jeg lavede en separat synchronized klasse der indeholdt listen)
        Thread cleanActiveClientsList = new Thread(() -> {
            while(isRunning){
                //check hvert 5 sekund.
                try{
                    Thread.sleep(5000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }

                List<ClientHandler> activeClients = ClientList.getInstance().getActiveClients();

                for(int i = 0; i < activeClients.size(); i++){
                    if(activeClients.get(i).getIsRunning() == false){
                        System.out.println("client removed from list: " + activeClients.get(i).getUser_name());
                        activeClients.remove(i);
                        i--;
                    }
                }
            }
        });
        //Thread der sover et sekund og derefter fjerner den 1 fra heartbeat.
        Thread checkHeartbeat = new Thread(() -> {
            while(isRunning){
                try{
                    Thread.sleep(1000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                List<ClientHandler> activeClients = ClientList.getInstance().getActiveClients();
                for(int i = 0; i < activeClients.size(); i++){
                        int heartbeat = activeClients.get(i).getHeartbeat();
                        if(heartbeat <= 0){
                            activeClients.remove(i);
                            i--;
                        }
                        else {
                            activeClients.get(i).setHeartbeat(heartbeat - 1);
                            //System.out.println(activeClients.get(i).getUser_name() + ": heartbeart set: " + (heartbeat - 1));
                        }

                }
            }
        });

        //navngiv og start threads
        checkHeartbeat.setName("Thread-HeartBeatchecker");
        checkHeartbeat.start();
        cleanActiveClientsList.setName("Thread-cleanActiveClientsList");
        cleanActiveClientsList.start();

        */




}


