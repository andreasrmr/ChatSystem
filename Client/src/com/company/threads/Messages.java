package com.company.threads;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Messages {

    public static DataOutputStream output;
    public static DataInputStream input;
    public static Socket socket;
    public Messages(){}

    public Messages(Socket socket){
        try {
            this.socket = socket;
            this.output = new DataOutputStream(socket.getOutputStream());
            this.input = new DataInputStream(socket.getInputStream());
        }catch (IOException e){
            e.printStackTrace();
        }


    }

    public void send(String msg){
        try{
            this.output.writeUTF(msg);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public String receieve(){
        String msg = "";
        try{
            msg = this.input.readUTF();
        }catch (IOException e){
            e.printStackTrace();
        }
        return msg;
    }

    public DataOutputStream getOutput() {
        return output;
    }

    public void setOutput(DataOutputStream output) {
        this.output = output;
    }

    public DataInputStream getInput() {
        return input;
    }

    public void setInput(DataInputStream input) {
        this.input = input;
    }
}
