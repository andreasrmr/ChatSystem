package com.company;

public class Client {
    //TODO data typer korrekte?

    String username;
    String ip;
    String port;
    int heartbeat;

    public Client(String username, String ip, String port, int heartbeat) {
        this.username = username;
        this.ip = ip;
        this.port = port;
        this.heartbeat = heartbeat;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public int getHeartbeat() {
        return heartbeat;
    }

    public void setHeartbeat(int heartbeat) {
        this.heartbeat = heartbeat;
    }

    @Override
    public String toString() {
        return "Client{" +
                "username='" + username + '\'' +
                ", ip='" + ip + '\'' +
                ", port='" + port + '\'' +
                ", heartbeat=" + heartbeat +
                '}';
    }
}
