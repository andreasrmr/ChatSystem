package com.company;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    static String server_ip;
    static int server_port;
    static String user_name;

    public static void main(String[] args) throws IOException {


    //Wait for Join msg
    waitForUserInput();


    //Initial configuration hardcoded
    server_ip = "127.0.0.1";
    server_port = 1234;


    System.out.println("Enter username");
    //input
    //user_name = scan.nextLine();

    //start client.
    Client client = new Client(server_ip, server_port, user_name);

    }
    //Bruger får lov til at skrive input
    //TODO Hardcoded pt. brugeren skal selv lave input
    public static void waitForUserInput(){

        //Scanner bruges til input fra brugeren.
        Scanner scan = new Scanner(System.in);
        boolean msgOk = false;
        while(!msgOk){
            String joinMessage = scan.nextLine();

            String[] joinMessageSplit = joinMessage.split(" ");
            if(joinMessageSplit[0].equals("JOIN") && joinMessageSplit.length == 3){

            }
            else {
                System.out.println("join message not ok.");
            }
        }


        //Todo Verificer JOIN string på klient.

        //Eksempel: Join John, 127.0.0.1:2001
    }
}
