package com.example.tejakanchinadam.hw08;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ServerValue;
import com.firebase.client.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Messages extends AppCompatActivity {

    Firebase myFirebaseRef;

    Chat chat;

    static ListView listView;

    static ArrayList<Chat> myChatList;

    static MessagesListAdapter adapter;

    EditText sendMessage;

    Button sendBtn;

    static User receivingUser;

    static User primaryUser;

    static ArrayList<String> messageIDList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        myChatList = new ArrayList<Chat>();

        messageIDList = new ArrayList<String>();

        chat = new Chat();

        listView = (ListView) findViewById(R.id.listView2);

        adapter = new MessagesListAdapter(Messages.this, R.layout.chats, myChatList);

        adapter.setNotifyOnChange(true);

        listView.setAdapter(adapter);

        Firebase.setAndroidContext(this);

        sendMessage = (EditText) findViewById(R.id.sendMessage);

        sendBtn = (Button) findViewById(R.id.sendBtn);

        receivingUser = (User) getIntent().getExtras().getSerializable("currentUser");

        primaryUser = (User) getIntent().getExtras().getSerializable("primaryUser");

        myFirebaseRef = new Firebase("https://glowing-heat-2951.firebaseio.com/");

        myFirebaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                    if (((postSnapshot.getKey()).equals("messages"))) {

                        for (DataSnapshot postPostSnaps : postSnapshot.getChildren()) {

                            Chat chats = postPostSnaps.getValue(Chat.class);

                            if(!messageIDList.contains(chats.getMessageID())) {

                                if (((chats.getSender()).equals(primaryUser.getFullName())) && ((chats.getReceriver()).equals(receivingUser.getFullName()))) {

                                   // if (!messageIDList.contains(chats.getMessageID())) {

                                        myChatList.add(chats);

                                        messageIDList.add(chats.getMessageID());

                                        adapter.notifyDataSetChanged();

                                    //}

                                } else if (((chats.getSender()).equals(receivingUser.getFullName())) && ((chats.getReceriver()).equals(primaryUser.getFullName()))) {


                                    //if (!messageIDList.contains(chats.getMessageID())) {

                                        myChatList.add(chats);

                                        messageIDList.add(chats.getMessageID());

                                        adapter.notifyDataSetChanged();
                                    //}

                                }

                            }
                        }

                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendMessage(receivingUser, primaryUser);

            }
        });


    }

    private void sendMessage(User receivingUser, User primaryUser) {
        String input = sendMessage.getText().toString();
        //Map timestamp = ServerValue.TIMESTAMP;

        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
        String timeStamp = df.format(Calendar.getInstance().getTime());

       // Log.d("Date", date);

        Chat chat;

        if (!input.equals("")) {
            Boolean messageRead = false;

            Firebase postRef = myFirebaseRef.child("messages");

            Firebase newPostRef = postRef.push();

            chat = new Chat(messageRead,input, receivingUser.getFullName(), primaryUser.getFullName(), timeStamp, newPostRef.getKey());

            newPostRef.setValue(chat);

            sendMessage.setText("");

            recreate();

        }

        Toast.makeText(getApplicationContext(), "Please enter Text", Toast.LENGTH_LONG).show();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.view_contact_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.viewContact:
                Intent i = new Intent(Messages.this, ViewContact.class);
                //i.putExtra("userDetails", primaryUser);
                startActivity(i);
                return true;
            case R.id.callContact:
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + receivingUser.getPhoneNumber()));
                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (ActivityCompat.checkSelfPermission(Messages.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return true;
                }
                startActivity(callIntent);

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void refresh(){

        recreate();
    }


}
