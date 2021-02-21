package com.example.goodz.databaseClasses;

public class User {
    public String id;
    public String email;
    public String role;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String email, String role) {
        this.email = email;
        this.role = role;
    }
}
