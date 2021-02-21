package com.example.goodz.databaseClasses;

public class Driver {
    public String age;
    public String gender;
    public String userID;

    public Driver() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Driver(String age, String gender, String userID) {
        this.age = age;
        this.gender = gender;
        this.userID = userID;
    }
}
