package com.company;

import java.util.Scanner;

public class InputHandler{

    public static Scanner scanner = new Scanner(System.in);

    public InputHandler(){}

    /**
     * Læs en tekststreng
     * @return Metoden returnere en String som bruger indtaster
     * @see String
     **/
    public static String readString(){
        String str = scanner.nextLine();
        return str;
    }

    public static String[] splitJoinMSG(String joinMSG){
        //find username, server_ip + port
        String user_name;
        String server_ip;
        String server_port;
        String[] temp;

        //find username, server_ip + port
        //remove "JOIN " from joinMSG
        joinMSG = joinMSG.replace("JOIN ", "");

        //split på kommaet og find username på plads 0
        temp = joinMSG.split(",");
        user_name = temp[0];

        //split på kolon og find port på plads 1.
        temp = joinMSG.split(":");
        server_port = temp[1];

        //split på mellemrum og find server:port på plads 1
        temp = joinMSG.split(" ");
        joinMSG = temp[1];
        temp = joinMSG.split(":");
        server_ip = temp[0];


        //returner værdier.
        String[] allInfo = new String[3];
        allInfo[0] = user_name;
        allInfo[1] = server_ip;
        allInfo[2] = server_port;

        return allInfo;
    }

}