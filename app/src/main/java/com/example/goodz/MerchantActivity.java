package com.example.goodz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MerchantActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant);
    }

    public void addOrder(){
        Intent intent = new Intent(MerchantActivity.this, CreateOrderActivity.class);
        intent.putExtra("userID", getIntent().getStringExtra("userID"));
        MerchantActivity.this.startActivity(intent);
    }


}