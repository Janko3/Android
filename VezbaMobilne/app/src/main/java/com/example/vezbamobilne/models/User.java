package com.example.vezbamobilne.models;

public class User {
    private String email;
    private String password;

    private String username;

    public User(String email, String password,String username) {
        this.email = email;
        this.password = password;
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
}
