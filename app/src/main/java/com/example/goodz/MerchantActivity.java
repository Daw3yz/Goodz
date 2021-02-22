package com.example.goodz;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.goodz.databaseClasses.Order;
import com.example.goodz.databaseClasses.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MerchantActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference();

    ListView ordersListView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        String userID = getIntent().getStringExtra("userID");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant);
        ArrayList<String> ordersList = new ArrayList<String>();
        ordersListView  = (ListView) findViewById(R.id.ordersListMerchant);

        Query orders = ref.child("orders").orderByChild("merchantUserID").equalTo(userID);
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
                        MerchantActivity.this,
                        android.R.layout.simple_list_item_1,
                        ordersList);
                ordersListView.setAdapter(adapter);
            }

            public void onCancelled(DatabaseError databaseError) {
                ;
            }

        });

        ordersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object o = ordersListView.getItemAtPosition(position);
                String str = (String)o; //As you are using Default String Adapter
                Toast.makeText(getBaseContext(),str,Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void addOrder(View view){
        Intent intent = new Intent(MerchantActivity.this, CreateOrderActivity.class);
        intent.putExtra("userID", getIntent().getStringExtra("userID"));
        MerchantActivity.this.startActivity(intent);
    }


}