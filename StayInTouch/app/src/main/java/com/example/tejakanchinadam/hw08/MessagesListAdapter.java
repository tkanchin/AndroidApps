package com.example.tejakanchinadam.hw08;

import android.content.Context;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tejakanchinadam on 4/17/16.
 */
public class MessagesListAdapter extends ArrayAdapter<Chat> {

    Firebase ref = new Firebase("https://glowing-heat-2951.firebaseio.com/");

    List<Chat> mData;

    Context mContext;

    int mResource;

    Chat chat;

    public MessagesListAdapter(Context context, int resource, List<Chat> objects) {
        super(context, resource, objects);

        this.mContext = context;

        this.mData = objects;

        this.mResource = resource;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(mResource, parent, false);

        }

        //ConversationListAdapter.redBubble = mData.get(position).getMessageRead();

        //ConversationListAdapter.redBubble = true;

        chat = mData.get(position);

        TextView chatName = (TextView) convertView.findViewById(R.id.chatName);

        TextView chatText = (TextView) convertView.findViewById(R.id.chatText);

        TextView chatTime = (TextView) convertView.findViewById(R.id.chatTime);

        Button deleteBtn = (Button) convertView.findViewById(R.id.deleteBtn);


        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Firebase usersRef = ref.child("messages");

                Firebase alanRef = usersRef.child(mData.get(position).getMessageID());

                Messages.messageIDList.remove(mData.get(position).getMessageID());

                Messages.myChatList.remove(mData.get(position));

                alanRef.setValue(null);

                Messages.adapter.notifyDataSetChanged();


            }
        });

        if((chat.getReceriver()).equals(Messages.receivingUser.getFullName()) ){

            convertView.setBackgroundResource(R.color.grey);

            chatName.setText(Messages.receivingUser.getFullName());

            chatText.setText(chat.getMessageText());

            chatTime.setText(chat.getTimeStamp());


        }else{

            chatName.setText(Messages.receivingUser.getFullName());

            chatText.setText(chat.getMessageText());

            chatTime.setText(chat.getTimeStamp());

            deleteBtn.setVisibility(View.INVISIBLE);

        }

        return convertView;
    }


}
