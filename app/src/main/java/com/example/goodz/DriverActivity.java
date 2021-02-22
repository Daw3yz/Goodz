package com.example.goodz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.goodz.databaseClasses.Driver;
import com.example.goodz.databaseClasses.Order;
import com.google.firebase.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DriverActivity extends AppCompatActivity {
    ArrayAdapter<String> adapter;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);
        ListView ordersListView = (ListView) findViewById(R.id.ordersListDriver);

        ArrayList<Order> ordersList = new ArrayList<Order>();
        ArrayList<String> ordersKeyList = new ArrayList<String>();
        Query orders = ref.child("orders").orderByKey();
        orders.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                        Order order = childSnapshot.getValue(Order.class);
                        ordersList.add(order);
                        ordersKeyList.add(childSnapshot.getKey());
                    }
                }
                OrderAdapter orderAdapter = new OrderAdapter(DriverActivity.this, ordersList, ordersKeyList);
                ordersListView.setAdapter(orderAdapter);
            }

            public void onCancelled(DatabaseError databaseError) {
                ;
            }
        });

        Button acceptButton = (Button) findViewById(R.id.acceptOrder);
        acceptButton.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Order order =(Order) parent.getItemAtPosition(position);
            }
        });
    }
}