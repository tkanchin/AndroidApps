package com.example.tejakanchinadam.hw08;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Map;

public class signUp extends AppCompatActivity {

    EditText signName, signEmail, signPhone, signPassword, signConPassword;

    Button signUp, signUpCancel;

    Firebase myFirebaseRef;
    String encodedImage;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            switch (requestCode) {
                case 1:
                    if (resultCode == RESULT_OK) {
                        Uri selectedImage = data.getData();
                        InputStream inputStream = getContentResolver().openInputStream(selectedImage);
                        Bitmap picture = BitmapFactory.decodeStream(inputStream);
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        picture.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        byte [] b = baos.toByteArray();
                        encodedImage = Base64.encodeToString(b, Base64.DEFAULT);



                    } else
                        Toast.makeText(getApplicationContext(), "Pick an Image from gallery", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(),"Something is wrong",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Firebase.setAndroidContext(this);

        myFirebaseRef = new Firebase("https://glowing-heat-2951.firebaseio.com/");

        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent,1);
        signName = (EditText) findViewById(R.id.signName);

        signEmail = (EditText) findViewById(R.id.signEmail);

        signPhone = (EditText) findViewById(R.id.signNumber);

        signPassword = (EditText) findViewById(R.id.signPassword);

        signConPassword = (EditText) findViewById(R.id.conPassword);

        signUp = (Button) findViewById(R.id.signUpBtn);

        signUpCancel = (Button) findViewById(R.id.signCancel);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = signName.getText().toString();

                final String email = signEmail.getText().toString();

                final String password = signPassword.getText().toString();

                String conPassword = signConPassword.getText().toString();

                final String number = signPhone.getText().toString();

                String message = null;

                if(name.equals("") || email.equals("") || password.equals("") || conPassword.equals("") || number.equals("")){

                    message = "Please enter all the values";

                }else {

                /*

                myFirebaseRef.child("message").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        System.out.println(snapshot.getValue());  //prints "Do you have data? You'll love Firebase."
                    }
                    @Override public void onCancelled(FirebaseError error) { }
                }); */

                    if (password.equals(conPassword)) {

                        myFirebaseRef.createUser(email, password, new Firebase.ValueResultHandler<Map<String, Object>>() {
                            @Override
                            public void onSuccess(Map<String, Object> result) {
                                //System.out.println("Successfully created user account with uid: " + result.get("uid"));

                                //Log.d("Demo", result.get("uid").toString());

                                Bitmap bitmapImage = BitmapFactory.decodeResource(getResources(), R.drawable.placeholder);

                                String picture = bitmapToBase64(bitmapImage);

                                User user = new User(email, name, password, number, encodedImage );

                                Firebase userRef = myFirebaseRef.child("users").child("" + result.get("uid"));
                                //User alan = new User("Alan Turing", 1912);
                                userRef.setValue(user);

                                finish();

                            }

                            @Override
                            public void onError(FirebaseError firebaseError) {
                                // there was an error
                            }
                        });

                    } else {

                        message = "Passwords do not match";

                    }

                }

                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            }


        });

        signUpCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

               // myFirebaseRef.child("message").setValue("Do you have data? You'll love Firebase.");
            }
        });

    }

    public static String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }


}
