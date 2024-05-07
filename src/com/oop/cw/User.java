package com.oop.cw;

public class User {
    private String username;
    private String password;

    //Constructor for the User class
    public User(String username,String password){
        this.username=username;
        this.password=password;
    }

    //Getter and setter methods for the User class

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
