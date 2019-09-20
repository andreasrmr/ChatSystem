package com.company.threads;

import java.net.Socket;

public class Heartbeart implements Runnable {
    @Override
    public void run() {
        try{
            Thread.sleep(60000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        SendMessages.sendMSG("IMAV");
    }
}
