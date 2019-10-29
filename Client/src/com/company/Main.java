package com.company;

import com.company.components.Client;

public class Main {

    public static boolean isRunning;

    public static void main(String[] args) {

        isRunning = true;
        //Wait for Join msg
        System.out.println("Enter join message or QUIT");
        System.out.println("Example: JOIN username, 127.0.0.1:1234");
        while(isRunning){

            //TODO: er hardcoded for hurtigere test afkommenter så bruger kan lave input
            //input fra bruger join msg / exit
            //String userInput = InputHandler.readString();
            String userInput = "JOIN Hans, 127.0.0.1:1234";

            if(InputVerification.chkExitMSG(userInput)){
                isRunning = false;
            }
            else if(InputVerification.chkJoinMSG(userInput)){
                String[] info = InputHandler.splitJoinMSG(userInput);
                String user_name = info[0];
                String server_ip = info[1];
                int server_port = Integer.parseInt(info[2]);
                Client client = new Client(user_name, server_ip, server_port);
                Thread clientThread = new Thread(client);
                clientThread.run();
            }
            else {
                System.out.println("Error in join message; example: JOIN John, 127.0.0.1:2001\nQUIT for quit");
                System.out.println("Note username can only be 12 characters long");
            }

        }

    }
}
