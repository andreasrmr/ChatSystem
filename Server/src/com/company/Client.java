package com.company;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client implements Runnable {

    Scanner scn = new Scanner(System.in);

    final DataInputStream input;
    final DataOutputStream output;
    Socket s;


    public Client(Socket s, DataInputStream input, DataOutputStream output) {
        this.s = s;
        this.input = input;
        this.output = output;

    }

    @Override
    public void run() {
        String received;

        while(true)
        {

        }

    }
}
