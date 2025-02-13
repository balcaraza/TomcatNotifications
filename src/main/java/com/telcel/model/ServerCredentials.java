package com.telcel.model;

public class ServerCredentials {
    private String password;

    public ServerCredentials(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
