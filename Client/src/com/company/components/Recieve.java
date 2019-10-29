package com.company.components;

import com.company.Main;

import java.io.IOException;

public class Recieve extends Client implements Runnable {

    public Recieve() {
    }
    @Override
    public void run() {
        String msg;
        while(Main.isRunning == true){
            try{
                msg = input.readUTF();
                switch (msg){
                    case "QUIT":
                        Main.isRunning = false;
                        input.close();
                        socket.close();
                        break;
                    default:
                        System.out.println(msg);
                        break;
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
