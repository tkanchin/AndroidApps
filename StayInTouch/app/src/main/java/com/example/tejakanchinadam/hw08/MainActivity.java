package com.example.tejakanchinadam.hw08;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText loginEmail, loginPassword;

    Button loginBtn, loginCreateNew;

    Firebase myFirebaseRef;

    static String emailOfUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Long tsLong = System.currentTimeMillis()/1000;
        //String ts = tsLong.toString();

        //Log.d("Time", ts);

        Firebase.setAndroidContext(this);

        myFirebaseRef = new Firebase("https://glowing-heat-2951.firebaseio.com/");

        loginEmail = (EditText) findViewById(R.id.loginEmail);

        loginPassword = (EditText) findViewById(R.id.loginPassword);

        loginBtn = (Button) findViewById(R.id.LoginBtn);

        loginCreateNew = (Button) findViewById(R.id.LoginNewAcc);

        myFirebaseRef.addAuthStateListener(new Firebase.AuthStateListener() {
            @Override
            public void onAuthStateChanged(AuthData authData) {
                if (authData != null) {
                    Intent i = new Intent(MainActivity.this, Conversations.class);

                    emailOfUser = (String) authData.getProviderData().get("email");

                    i.putExtra("uid", authData.getUid());

                    startActivity(i);
                } else {
                    loginCreateNew.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(MainActivity.this, signUp.class);


                            startActivity(i);
                        }
                    });

                    loginBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            emailOfUser = loginEmail.getText().toString();

                            String password = loginPassword.getText().toString();

                            if (emailOfUser.equals("") || password.equals("")) {

                                Toast.makeText(getApplicationContext(), "Please enter all the Values", Toast.LENGTH_LONG).show();
                            } else {

                                myFirebaseRef.authWithPassword(emailOfUser, password, new Firebase.AuthResultHandler() {
                                    @Override
                                    public void onAuthenticated(AuthData authData) {

                                        Log.d("Demo", authData.getProviderData().toString());

                                        Log.d("Demo", authData.getAuth().toString());

                                        //Log.d("Demo", authData.getProviderData().toString());

                                        Intent i = new Intent(MainActivity.this, Conversations.class);

                                        i.putExtra("uid",authData.getUid());

                                        Toast.makeText(getApplicationContext(), "You are Logged in", Toast.LENGTH_LONG).show();

                                        startActivity(i);

                                    }

                                    @Override
                                    public void onAuthenticationError(FirebaseError firebaseError) {

                                        switch (firebaseError.getCode()) {
                                            case FirebaseError.USER_DOES_NOT_EXIST:

                                                Toast.makeText(getApplicationContext(), "User does not Exist", Toast.LENGTH_LONG).show();
                                                break;
                                            case FirebaseError.INVALID_PASSWORD:
                                                Toast.makeText(getApplicationContext(), "Invalid Password", Toast.LENGTH_LONG).show();
                                                break;
                                            default:
                                                // handle other errors
                                                break;
                                        }

                                    }
                                });
                            }


                        }

                    });

                }
            }
        });


     }
}
