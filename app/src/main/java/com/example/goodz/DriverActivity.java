package com.example.goodz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
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
import com.example.goodz.databaseClasses.User;
import com.google.firebase.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class DriverActivity extends AppCompatActivity {

    public SensorManager mSensorManager;
    public float mAccel;
    public float mAccelCurrent;
    public float mAccelLast;
    public int crashCount = 0;

    ArrayAdapter<String> adapter;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Objects.requireNonNull(mSensorManager).registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
        mAccel = 10f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;

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
    }


    private final SensorEventListener mSensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            mAccelLast = mAccelCurrent;
            mAccelCurrent = (float) Math.sqrt((double) (x * x + y * y + z * z));
            float delta = mAccelCurrent - mAccelLast;
            mAccel = mAccel * 0.9f + delta;
            if (mAccel > 30) {
                crashCount +=1;
                if (crashCount == 10){
                    Query orders = ref.child("drivers").orderByChild("userID").equalTo(getIntent().getStringExtra("userID"));
                    orders.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                                    int totalCrash = childSnapshot.getValue(Driver.class).numberOfCrashes;
                                    totalCrash += 10;
                                    ref.child("drivers").child(childSnapshot.getKey()).child("numberOfCrashes").setValue(totalCrash);
                                    Toast.makeText(DriverActivity.this, "Congratulations, You have crashed " + totalCrash + " times!!!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                        public void onCancelled(DatabaseError databaseError) {
                            ;
                        }
                    });
                    crashCount = 0;
                }
            }
        }
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };


}