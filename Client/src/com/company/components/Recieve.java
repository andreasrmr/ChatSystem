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
        String decryptedMsg = "";
        while(Main.isRunning == true){
            try{
                encryptedMsg = input.readUTF();
                decryptedMsg = AES.decrypt(encryptedMsg, AES.secretKeyDefined);

                switch (decryptedMsg){
                    case "QUIT":
                        Main.isRunning = false;
                        input.close();
                        break;
                    default:
                        System.out.println(decryptedMsg);
                        break;
                }
            }catch (IOException e){
                e.printStackTrace();
                Main.isRunning = false;
            }
        }
    }
}
