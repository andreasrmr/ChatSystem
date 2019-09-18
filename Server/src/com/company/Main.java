package com.company;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
	Client client = new Client("User1", "10.10.10.2", "24000", 61);
    Client client2 = new Client("User2", "10.10.10.2", "24000", 58);


    ClientList clientList = ClientList.fetchInstance();
    clientList.add(client);
    clientList.add(client2);
    clientList.printClients();
        System.out.println("check");
    clientList.checkForHeartbeat();
    clientList.printClients();



    }
}
