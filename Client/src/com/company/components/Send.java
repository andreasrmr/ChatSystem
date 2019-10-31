package com.company.components;

import com.company.InputHandler;
import com.company.InputVerification;
import com.company.Main;
import java.io.DataOutputStream;
import java.io.IOException;


public class Send implements Runnable {
    static DataOutputStream output;
    
    public Send(DataOutputStream output) {
        this.output = output;
        Thread threadHeartbeat = new Thread(new Heartbeat());
        threadHeartbeat.start();
    }

    @Override
    public void run() {
        String msg = "";
        while(Main.isRunning == true){
            msg = InputHandler.readString();

            switch (msg){
                //close prorgam
                case "QUIT":
                    sendEncryptedMsg(msg);
                    Main.isRunning = false;
                    break;
                //DATA message
                default:
                    if(InputVerification.chkDataMSG(msg)){
                        String[] temp = msg.split(":");
                        sendEncryptedMsg(temp[1]);
                    }
                    else{
                        System.out.println("Error write QUIT or DATA:message");
                    }
                    break;
            }
        }
    }
    public static void sendEncryptedMsg(String msg){
        String encryptedString = AES.encrypt(msg, AES.secretKeyDefined);
        try{
            output.writeUTF(encryptedString);
            output.flush();
        }catch (IOException e){
            e.printStackTrace();
            Main.isRunning = false;
        }

    }
}
