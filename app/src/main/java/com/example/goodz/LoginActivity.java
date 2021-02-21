package com.example.goodz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.goodz.databaseClasses.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference db = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_YES);
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_login);
    }
    public void loginClicked(View view){
        EditText emailEditText = (EditText) findViewById(R.id.emailEditText);
        EditText passwordEditText = (EditText) findViewById(R.id.passwordEditText);

        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        try{

            //Sign up function, upload to firebase
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                     .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> signUpTask) {
                            if (signUpTask.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "Signup success, signing in",
                                        Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                                intent.putExtra("email", email);
                                LoginActivity.this.startActivity(intent);

                            } else {
                                Exception e = signUpTask.getException();
                                if (e.getMessage().contains("The email address is already in use by another account.")){

                                    //Sign in if the email exists in the database------------------------------
                                    firebaseAuth.signInWithEmailAndPassword(email, password)
                                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                                public void onComplete(@NonNull Task<AuthResult> loginTask) {
                                                    if (loginTask.isSuccessful()) {
                                                        Intent intent = null;
                                                        intent = new Intent(LoginActivity.this, RegisterActivity.class);
                                                        intent.putExtra("email", email);
                                                        LoginActivity.this.startActivity(intent);
                                                    } else {
                                                        Exception e = loginTask.getException();
                                                        Toast.makeText(LoginActivity.this, e.getMessage(),
                                                                Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                    //-------------------------------------------------------------------------

                                //Display error as toast if register failed
                                }else{
                                    Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
        }
        catch (Exception e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG);
        }
    }
}