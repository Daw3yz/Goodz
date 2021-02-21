package com.example.goodz.databaseClasses;

public class Order {
    public String customerName;
    public String customerPhoneNo;
    public String address;
    public String postalCode;
    public String deliveryDate;
    public String pickupTime;

    public Order(){

    }

    public Order(String customerName, String customerPhoneNo, String address, String postalCode, String deliveryDate, String pickupTime){
        this.customerName = customerName;
        this.customerPhoneNo = customerPhoneNo;
        this.address = address;
        this.postalCode = postalCode;
        this.deliveryDate = deliveryDate;
        this.pickupTime = pickupTime;
    }
}
