package com.example.tejakanchinadam.hw08;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

public class Conversations extends AppCompatActivity {

    Firebase myFirebaseRef;

    User user;

    ListView listView;

    private ArrayList<User> list;

    String uid;

    User primaryUser;

    String emailOfUser;

    ConversationListAdapter adapter;

    static String UID;

    ArrayList<String> namesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversations);

            list = new ArrayList<User>();

            namesList = new ArrayList<String>();

            user = new User();

            listView = (ListView) findViewById(R.id.listView);

            adapter = new ConversationListAdapter(Conversations.this, R.layout.conversation_listview, list);

            listView.setAdapter(adapter);

            Firebase.setAndroidContext(this);

            myFirebaseRef = new Firebase("https://glowing-heat-2951.firebaseio.com/");

            UID = getIntent().getExtras().getString("uid");

            myFirebaseRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                        if (!((postSnapshot.getKey()).equals("messages"))) {

                            for (DataSnapshot postPostSnaps : postSnapshot.getChildren()) {

                                User users = postPostSnaps.getValue(User.class);

                                if (!((postPostSnaps.getKey()).equals(UID))) {

                                    if (!namesList.contains(users.getFullName())) {

                                        list.add(users);

                                        namesList.add(users.getFullName());

                                        adapter.notifyDataSetChanged();
                                    }


                                } else {

                                    primaryUser = users;

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

        Log.d("Size", list.size() + "");

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    user = list.get(position);
                    ImageView bubbleRed = (ImageView) view.findViewById(R.id.redBubble);
                    bubbleRed.setVisibility(View.INVISIBLE);

                    Intent i = new Intent(Conversations.this, Messages.class);

                    i.putExtra("currentUser", user);

                    i.putExtra("primaryUser", primaryUser);

                    startActivity(i);

                }
            });

        }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.EditProfile:
                Intent i = new Intent(Conversations.this, EditProfile.class);
                i.putExtra("userDetails", primaryUser);
                startActivity(i);
                return true;
            case R.id.Logout:
                myFirebaseRef.unauth();
                Intent in = new Intent(Conversations.this, MainActivity.class);
                startActivity(in);

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }



}


