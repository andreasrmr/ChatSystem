package com.company.components;

import com.company.Main;

public class Heartbeat implements Runnable {
    public Heartbeat() {}
    @Override
    public void run() {

        while (Main.isRunning == true) {
            try {
                Thread.sleep(59000);
                Send.sendEncryptedMsg("IMAV");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
