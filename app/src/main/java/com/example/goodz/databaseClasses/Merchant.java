package com.example.goodz.databaseClasses;

public class Merchant {
    public String address;
    public String openTime;
    public String closeTime;
    public String userID;

    public Merchant() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Merchant(String address, String openTime, String closeTime, String userID) {
        this.address = address;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.userID = userID;
    }
}
