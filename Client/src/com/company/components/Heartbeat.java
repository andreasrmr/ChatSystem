package com.company.components;

import com.company.Main;

import java.io.IOException;

public class Heartbeat extends Client implements Runnable {

    public Heartbeat() {
    }
    @Override
    public void run() {

           while(Main.isRunning == true) {
               try {
                   Thread.sleep(59000);
                   this.output.writeUTF("IMAV");
                   this.output.flush();
               } catch (InterruptedException e) {
                   e.printStackTrace();
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
    }
}
