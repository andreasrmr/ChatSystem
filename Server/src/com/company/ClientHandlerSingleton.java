package com.company;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ClientHandlerSingleton {
    private static ClientHandlerSingleton uniqueInstance;

    public static List<ClientHandler> clientHandlers;

    private ClientHandlerSingleton(){
        clientHandlers = new ArrayList<>();
    }

    public synchronized static ClientHandlerSingleton getInstance(){
        if(uniqueInstance == null){
            uniqueInstance = new ClientHandlerSingleton();
        }
        return uniqueInstance;
    }

    public static List<ClientHandler> getClientHandlers() {
        return clientHandlers;
    }

}
