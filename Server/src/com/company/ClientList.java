package com.company;

import java.util.List;
import java.util.Vector;

public class ClientList {

    private static ClientList instance;
    private static List<ClientHandler> activeClients;

    private ClientList(){
        activeClients = new Vector<ClientHandler>();
    }

    synchronized public static ClientList getInstance(){
        if(instance == null){
            instance = new ClientList();
        }
        return instance;
    }

    public static List<ClientHandler> getActiveClients() {
        return activeClients;
    }
    public static void addActiveClient(ClientHandler c) {
        activeClients.add(c);
    }
}
