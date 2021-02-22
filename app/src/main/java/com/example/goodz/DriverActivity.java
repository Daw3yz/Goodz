package com.example.goodz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
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

        try {
            ListView ordersListView = (ListView) findViewById(R.id.ordersListDriver);
            ArrayList<String> ordersList = new ArrayList<String>();
            Query orders = ref.child("orders").orderByKey();
            orders.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                            Order order = childSnapshot.getValue(Order.class);
                            ordersList.add(childSnapshot.getKey());
                        }
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(
                            DriverActivity.this,
                            android.R.layout.simple_list_item_1,
                            ordersList);
                    ordersListView.setAdapter(adapter);
                }

                public void onCancelled(DatabaseError databaseError) {
                    ;
                }
            });
        }catch (Exception e){
            Toast.makeText(DriverActivity.this, e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }
}