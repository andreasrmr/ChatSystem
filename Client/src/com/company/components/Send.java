package com.company.components;

import com.company.InputHandler;
import com.company.InputVerification;
import com.company.Main;

import java.io.DataOutputStream;
import java.io.IOException;

public class Send implements Runnable {
    DataOutputStream output;

    public Send(DataOutputStream output) {
        this.output = output;

    }

    @Override
    public void run() {
        String msg = "";
        while(Main.isRunning == true){
            msg = InputHandler.readString();

            try{
                switch (msg){
                    //close prorgam
                    case "QUIT":
                        output.writeUTF("QUIT");
                        output.flush();
                        output.close();
                        Main.isRunning = false;
                        break;

                    //DATA message
                    default:
                        if(InputVerification.chkDataMSG(msg)){
                            String[] temp = msg.split(":");

                            final String secretKey = "WorrisomePurveyorGlorify3";
                            String encryptedString = AES.encrypt(temp[1], secretKey);
                            output.writeUTF(encryptedString);
                            output.flush();
                        }
                        else{
                            System.out.println("Error write QUIT or DATA:message");
                        }
                        break;
                }
            }catch (IOException e){
                e.printStackTrace();
                Main.isRunning = false;
            }
        }
    }
}
