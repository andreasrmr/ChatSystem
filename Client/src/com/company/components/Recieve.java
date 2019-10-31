package com.company.components;

import com.company.Main;

import java.io.DataInputStream;
import java.io.IOException;

public class Recieve implements Runnable {

    DataInputStream input;

    public Recieve(DataInputStream input) {
        this.input = input;
    }
    @Override
    public void run() {
        String encryptedMsg = "";
        String msg = "";
        while(Main.isRunning == true){
            try{
                encryptedMsg = input.readUTF();

                msg = AES.decrypt(encryptedMsg, AES.secretKeyDefined);

                switch (msg){
                    case "QUIT":
                        Main.isRunning = false;
                        input.close();
                        break;
                    default:
                        System.out.println(msg);
                        break;
                }
            }catch (IOException e){
                e.printStackTrace();
                Main.isRunning = false;
            }
        }
    }
}
