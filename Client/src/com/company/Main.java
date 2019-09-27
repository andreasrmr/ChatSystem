package com.company;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    static String server_ip;
    static int server_port;
    static String user_name;
    public static boolean isRunning;

    public static void main(String[] args) throws IOException {

        isRunning = true;
        //Wait for Join msg
        System.out.println("Enter join message or QUIT");
        System.out.println("Ex: JOIN username, 127.0.0.1:1234");
        while(isRunning){

            Scanner scan = new Scanner(System.in);
            String joinMSG = scan.nextLine();
            if(chkUserInput(joinMSG)){
                Client client = new Client(server_ip, server_port, user_name);
            }


        }



    }

    //verificering af input
    public static boolean chkUserInput(String joinMSG){
        boolean msgOk = false;

            //verificering af join string.
            //Eksempel: JOIN John, 127.0.0.1:2001
            //pt må brugernavn kun være bogstaver og tal
            //TODO brugernavne må gerne indeholde nogle tegn
            msgOk = joinMSG.matches("^JOIN [A-Za-z0-9]{1,}, ([0-9]{1,3}[.]){3}[0-9]{1,3}:[0-9]{1,5}");

            if(joinMSG.equals("EXIT")){
                isRunning = false;
                return false;
            }

            else if(msgOk){
                //find username, server_ip + port
                //remove "JOIN " from joinMSG
                joinMSG = joinMSG.replace("JOIN ", "");
                String[] temp;

                //split på kommaet og find username på plads 0
                temp = joinMSG.split(",");
                user_name = temp[0];

                //split på kolon og find port på plads 1.
                temp = joinMSG.split(":");
                server_port = Integer.parseInt(temp[1]);

                //split på mellemrum og find server:port på plads 1
                temp = joinMSG.split(" ");
                joinMSG = temp[1];
                temp = joinMSG.split(":");
                server_ip = temp[0];
                return true;
            }
            else {
                System.out.println("Error in join message; example: JOIN John, 127.0.0.1:2001\nEXIT for exit");
                return false;
            }
    }
}
