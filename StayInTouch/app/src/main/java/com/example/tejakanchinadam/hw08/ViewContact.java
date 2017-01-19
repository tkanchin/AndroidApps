package com.example.tejakanchinadam.hw08;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewContact extends AppCompatActivity {

    TextView viewName, viewName2, viewEmail, viewNumber;

    ImageView viewProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_contact);

        viewEmail = (TextView) findViewById(R.id.viewEmail);

        viewName = (TextView) findViewById(R.id.viewName);

        viewName2 = (TextView) findViewById(R.id.viewName2);

        viewNumber = (TextView) findViewById(R.id.viewNumber);

        viewProfile = (ImageView) findViewById(R.id.viewPicture);

        User user = Messages.receivingUser;

        viewEmail.setText("Email : " + user.getEmail());

        viewName2.setText("Username : " + user.getFullName());

        viewName.setText(user.getFullName());

        viewNumber.setText("Phone # : " + user.getPhoneNumber());

        Bitmap profileImage = ConversationListAdapter.base64ToBitmap(user.getPicture());

        viewProfile.setImageBitmap(profileImage);


    }
}
