package com.company;

import java.net.InetAddress;

public class Client {
    String user_name;
    int port;
    InetAddress ip;
    int heartbeat;

    public Client(String user_name, int port, InetAddress ip, int heartbeat) {
        this.user_name = user_name;
        this.port = port;
        this.ip = ip;
        this.heartbeat = heartbeat;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public InetAddress getIp() {
        return ip;
    }

    public void setIp(InetAddress ip) {
        this.ip = ip;
    }

    public int getHeartbeat() {
        return heartbeat;
    }

    public void setHeartbeat(int heartbeat) {
        this.heartbeat = heartbeat;
    }
}
