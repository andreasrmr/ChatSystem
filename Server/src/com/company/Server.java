package com.company;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Vector;

public class Server {

    private static ServerSocket serverSocket;
    static Protocol protocol;
    static boolean isRunning = true;

    public Server(int port_number) throws IOException {

        //Opret socket til server på port.
        serverSocket = new ServerSocket(port_number);

        //Opret protocol
        protocol = new Protocol();

        //Opret liste til clienter
        //activeClients = new Vector<ClientHandler>();



        Thread waitForClients = new Thread(() -> {
            startServer();
        });

        Thread cleanActiveClientsList = new Thread(() -> {
            while(isRunning){
                ClientList cList = ClientList.getInstance();

                for(int i = 0; i < cList.getActiveClients().size(); i++){
                    if(cList.getActiveClients().get(i).getIsRunning() == false){
                        System.out.println("client removed from list: " + cList.getActiveClients().get(i).getUser_name());
                        cList.getActiveClients().remove(i);
                        i--;
                    }
                }
            }
        });
        //TODO: work this
        Thread checkHeartbeat = new Thread(() -> {
            while(isRunning){
                ClientList cList = ClientList.getInstance();
                try{
                Thread.sleep(1000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                for(int i = 0; i < cList.getActiveClients().size(); i++){

                        int heartbeat = cList.getActiveClients().get(i).getHeartbeat();
                        if(heartbeat <= 0){
                            cList.getActiveClients().remove(i);
                            i--;
                        }
                        else {
                            cList.getActiveClients().get(i).setHeartbeat(heartbeat - 1);
                            //System.out.println(activeClients.get(i).getUser_name() + ": heartbeart set: " + (heartbeat - 1));
                        }

                }
            }
        });
        checkHeartbeat.setName("Thread-HeartBeatchecker");
        checkHeartbeat.start();
        cleanActiveClientsList.setName("Thread-cleanActiveClientsList");
        cleanActiveClientsList.start();
        waitForClients.setName("Thread-waitForClients");
        waitForClients.start();

        }



    public void startServer(){
        Socket socket;
        while(isRunning){

            try {
                //afvent på klient forbinder.
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
                output.writeUTF("From server: " + user_name + " was added");
                ClientHandler clientHandler = new ClientHandler(socket, input, output, user_name);
                Thread clientThread = new Thread(clientHandler);
                clientThread.start();
                ClientList cList = ClientList.getInstance();
                cList.addActiveClient(clientHandler);


            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //TODO: CHeck Logger klasse
        //

    }
}


