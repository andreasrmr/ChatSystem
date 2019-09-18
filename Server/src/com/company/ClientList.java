package com.company;

import java.util.ArrayList;


//Overvej 1 singleton her.
public class ClientList {

    private static  ClientList instance;
    ArrayList<Client> clientList;

    private ClientList() {
        clientList = new ArrayList<>();
    }

    //TODO Singleton:  skal threadsikres?! Kig i min bog
    public static ClientList fetchInstance(){
        if(instance == null){
            instance = new ClientList();
        }
        return instance;
    }

    //Kører clientlisten igennem og checker om det er over 60 sekunder siden vi har hørt fra en klient.
    public void checkForHeartbeat(){
        for(int i = 0; i < clientList.size(); i++ ){
            if(clientList.get(i).heartbeat > 60){
                clientList.remove(i);
                i--;
            }
        }
    }

    //TODO Remove metode skal laves om. Skal bruges når en Klient quitter.
    public void remove(Client c) {
        clientList.remove(c);
    }

    //Tilføj klient til liste
    public void add(Client c) {
        clientList.add(c);
    }

    //udskriv clienter.
    public void printClients() {
        for(Client c : clientList){
            System.out.println(c.toString());
        }
    }
}
