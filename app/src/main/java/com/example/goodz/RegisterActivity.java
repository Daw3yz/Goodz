package com.example.goodz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.goodz.databaseClasses.Driver;
import com.example.goodz.databaseClasses.Merchant;
import com.example.goodz.databaseClasses.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference();
    String userID;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        email = getIntent().getStringExtra("email");
        super.onCreate(savedInstanceState);

        //Check if user has chosen a role
        Query userData = ref.child("users").orderByChild("email").equalTo(email);
        userData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                        User userSnapshot = childSnapshot.getValue(User.class);
                        userID = childSnapshot.getKey();
                        Query extraData;
                        switch (userSnapshot.role){
                            case "merchant":
                                extraData = ref.child("merchants").orderByChild("userID").equalTo(userID);
                                extraData.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot2) {
                                        if (dataSnapshot2.exists()){
                                            Intent intent = new Intent(RegisterActivity.this, MerchantActivity.class);
                                            intent.putExtra("userID", userID);
                                            RegisterActivity.this.startActivity(intent);
                                        }else{
                                            setContentView(R.layout.activity_register_merchant);
                                        }
                                    }public void onCancelled(DatabaseError databaseError) {
                                        ;
                                    }
                                });
                                break;
                            case "driver":
                                extraData = ref.child("drivers").orderByChild("userID").equalTo(userID);
                                extraData.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot2) {
                                        if (dataSnapshot2.exists()){
                                            Intent intent = new Intent(RegisterActivity.this, DriverActivity.class);
                                            intent.putExtra("userID", userID);
                                            RegisterActivity.this.startActivity(intent);
                                        }else{
                                            setContentView(R.layout.activity_register_driver);
                                        }
                                    }public void onCancelled(DatabaseError databaseError) {
                                        ;
                                    }
                                });
                                break;
                        }
                    }
                }else{
                    setContentView(R.layout.activity_register);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void chooseRole(View view){
        switch (view.getId()){
            case R.id.merchantButton:
                writeNewUser(email,"merchant");
                Toast.makeText(RegisterActivity.this, "Registering as merchant", Toast.LENGTH_SHORT).show();
                setContentView(R.layout.activity_register_merchant);
                break;
            case R.id.driverButton:
                writeNewUser(email, "driver");
                setContentView(R.layout.activity_register_driver);
                Toast.makeText(RegisterActivity.this, "Registering as driver", Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(RegisterActivity.this, "Sumthin went wrong", Toast.LENGTH_SHORT).show();
                break;

        }
    }

    public void addMerchantDetails(View view){
        EditText merchantAddressEditText = (EditText) findViewById(R.id.merchantAddress);
        EditText openingTimeEditText = (EditText) findViewById(R.id.openingTime);
        EditText closingTimeEditText = (EditText) findViewById(R.id.closingTime);

        String merchantAddress = merchantAddressEditText.getText().toString();
        String openingTime = openingTimeEditText.getText().toString();
        String closingTime = closingTimeEditText.getText().toString();

        writeNewMerchant(merchantAddress, openingTime, closingTime);

        Intent intent = new Intent(RegisterActivity.this, MerchantActivity.class);
        intent.putExtra("userID", userID);
        RegisterActivity.this.startActivity(intent);
    }
    public void addDriverDetails(View view){
        EditText driverAgeEditText = (EditText) findViewById(R.id.driverAge);
        RadioGroup genderRadioGroup = (RadioGroup) findViewById(R.id.genderRadioGroup);
        int genderResult = genderRadioGroup.getCheckedRadioButtonId();
        RadioButton genderRadioButton = (RadioButton) findViewById(genderResult);

        String driverAge = driverAgeEditText.getText().toString();
        String gender = genderRadioButton.getText().toString();

        writeNewDriver(driverAge, gender);

        Intent intent = new Intent(RegisterActivity.this, DriverActivity.class);
        intent.putExtra("userID", userID);
        RegisterActivity.this.startActivity(intent);
    }


    public void writeNewUser(String email, String role) {
        User user = new User(email, role);
        String userID = ref.child("users").push().getKey();
        this.userID = userID;
        ref.child("users").child(userID).setValue(user);
    }
    public void writeNewMerchant(String address, String openTime, String closeTime){
        Merchant merchant = new Merchant(address, openTime, closeTime, userID);
        String merchantID = ref.child("merchants").push().getKey();
        ref.child("merchants").child(merchantID).setValue(merchant);
    }
    public void writeNewDriver(String age, String gender){
        Driver driver = new Driver(age, gender, userID);
        String driverID = ref.child("drivers").push().getKey();
        ref.child("drivers").child(driverID).setValue(driver);
    }

}