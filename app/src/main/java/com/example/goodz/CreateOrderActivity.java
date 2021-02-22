package com.example.goodz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.goodz.databaseClasses.Merchant;
import com.example.goodz.databaseClasses.Order;
import com.example.goodz.databaseClasses.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class CreateOrderActivity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference();

    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_order);
        userID = getIntent().getStringExtra("userID");
    }
    public void createOrder(View view){
        EditText customerNameEditText = (EditText) findViewById(R.id.customerName);
        EditText customerPhoneNoEditText = (EditText) findViewById(R.id.customerPhoneNo);
        EditText customerAddressEditText = (EditText) findViewById(R.id.customerAddress);
        EditText customerPostalCodeEditText = (EditText) findViewById(R.id.customerPostalCode);
        EditText deliveryDateEditText = (EditText) findViewById(R.id.deliveryDate);
        EditText deliveryTimeEditText = (EditText) findViewById(R.id.deliveryTime);

        String customerName = customerNameEditText.getText().toString();
        String customerPhoneNo = customerPhoneNoEditText.getText().toString();
        String customerAddress = customerAddressEditText.getText().toString();
        String customerPostalCode = customerPostalCodeEditText.getText().toString();
        String deliveryData = deliveryDateEditText.toString();
        String deliveryTime = deliveryTimeEditText.toString();


        writeNewOrder(customerName, customerPhoneNo, customerAddress, customerPostalCode, deliveryData, deliveryTime, userID);
        Intent intent = new Intent(CreateOrderActivity.this, MerchantActivity.class);
        intent.putExtra("userID", userID);
        CreateOrderActivity.this.startActivity(intent);
    }

    public void writeNewOrder(String customerName, String customerPhoneNo, String address,
                              String postalCode, String deliveryOrder, String deliveryTime, String merchantID) {
        Order order = new Order(customerName, customerPhoneNo, address, postalCode, deliveryOrder, deliveryTime, userID);
        String orderID = ref.child("orders").push().getKey();
        ref.child("orders").child(orderID).setValue(order);
    }
}