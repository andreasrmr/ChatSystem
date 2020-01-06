package com.company;

public class InputVerification {

    //Eksempel: JOIN John_2, 127.0.0.1:1234
    //Altid "JOIN " først
    //Brugernavn mellem 1-12 chars (chars, tal og - og _)
    //komma + mellemrum immellem brugernavn + ip
    //ip verificeres / localhost som ip tillades
    public static boolean chkJoinMSG(String command){
        if(command.matches("^JOIN [A-Za-z0-9_-]{1,12}, (([0-9]{1,3}[.]){3}[0-9]{1,3}|(localhost)):[0-9]{1,5}")){
            return true;
        }
        return false;
    }

    //Eksempel: DATA John_2:Besked her lala
    //Altid "DATA " først
    //Brugernavn mellem 1-12 chars (chars, tal og - og _)
    //kolon imellem brugernavn og besked
    //Besked uendelig lang alle tegn tilladt.
    //Besked kan max være 255 tegn.
    public static boolean chkDataMSG(String command){
        if(command.matches("^DATA:.{0,255}")){
            return true;
        }
        return false;
    }

    //checker command er chkExitMSG.
    public static boolean chkExitMSG(String command){
        if(command.matches("QUIT")){
            return true;
        }
        return false;
    }
}
