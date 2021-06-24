package com.udemy.demo.jwt;

public class JwtResponse {

    private final int userId;
    private final String userName;


    public JwtResponse(int id, String userName) {
        this.userId = id;
        this.userName = userName;
    }

    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }
}