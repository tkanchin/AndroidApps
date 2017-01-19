package com.example.tejakanchinadam.hw08;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URI;
import java.util.Map;

import it.sephiroth.android.library.picasso.Picasso;

public class EditProfile extends AppCompatActivity {

    EditText editName, editPassword, editNumber, editEmail;

    ImageView editProfilePic;

    TextView editFUllName;

    Firebase myFirebaseRef;

    Button updateBtn, editCancelBtn;

    String picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        Firebase.setAndroidContext(this);

        myFirebaseRef = new Firebase("https://glowing-heat-2951.firebaseio.com/");

        User mainUser = (User) getIntent().getExtras().getSerializable("userDetails");

        editName = (EditText) findViewById(R.id.editName);

        editName.setText(mainUser.getFullName());

        editPassword = (EditText) findViewById(R.id.editPassword);

        editPassword.setText(mainUser.getPassword());

        editEmail = (EditText) findViewById(R.id.editEmail);

        editEmail.setText(mainUser.getEmail());

        editNumber = (EditText) findViewById(R.id.editNumber);

        editNumber.setText(mainUser.getPhoneNumber());

        editProfilePic = (ImageView) findViewById(R.id.editProfilePicture);

        editProfilePic.setImageBitmap(ConversationListAdapter.base64ToBitmap(mainUser.getPicture()));

        editProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
            }
        });

        editFUllName = (TextView) findViewById(R.id.editTextView);

        picture = mainUser.getPicture();

        editFUllName.setText(mainUser.getFullName());

        updateBtn = (Button) findViewById(R.id.editUpdateBtn);

        editCancelBtn = (Button) findViewById(R.id.editCancelBtn);

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        String email = editEmail.getText().toString();

                        String name = editName.getText().toString();

                        String password = editPassword.getText().toString();

                        String number = editNumber.getText().toString();

                        User user = new User(email, name, password, number, picture);

                        Firebase userRef = myFirebaseRef.child("users").child(Conversations.UID);

                        userRef.setValue(user);

                        Toast.makeText(getApplicationContext(), "Saved Successfully !", Toast.LENGTH_LONG).show();

                        finish();


            }
        });

        editCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                //Display an error
                return;
            }else{

                final Uri imageUri = data.getData();
                final InputStream imageStream;
                try {
                    imageStream = getContentResolver().openInputStream(imageUri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);

                    picture = signUp.bitmapToBase64(selectedImage);

                    editProfilePic.setImageBitmap(selectedImage);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                //Picasso.with(EditProfile.this).load(data).into(editProfilePic);

                //Log.d("Demo", data.toString());

            }
            //InputStream inputStream = context.getContentResolver().openInputStream(data.getData());


        }
    }

}
