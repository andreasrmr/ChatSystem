package com.company.threads;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ReadMessages implements Runnable {

    static DataInputStream input;
    static Socket socket;
    static Scanner scan = new Scanner(System.in);

    public ReadMessages(Socket socket) {
        this.socket = socket;
        try {
            this.input = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
            //Thread der afventer på indkommende beskeder
            System.out.println("Client ready to receive messages");
            try {
                while (true) {
                    System.out.println(input.readUTF());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

    }
}