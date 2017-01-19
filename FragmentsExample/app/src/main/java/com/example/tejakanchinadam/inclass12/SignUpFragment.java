package com.example.tejakanchinadam.inclass12;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SignUpFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class SignUpFragment extends Fragment {

    Firebase myFirebaseRef;

    EditText name, email, password;

    private OnFragmentInteractionListener mListener;



    public SignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Firebase.setAndroidContext(getActivity());

        myFirebaseRef = new Firebase("https://inclass12-5180.firebaseio.com/");

         name = (EditText)getActivity().findViewById(R.id.signUpName);

         email = (EditText)getActivity().findViewById(R.id.signUpEmail);

         password = (EditText)getActivity().findViewById(R.id.signUpPassword);


        getActivity().findViewById(R.id.signUpCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mListener.returnToLogin();

            }
        });


        getActivity().findViewById(R.id.singUpButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myFirebaseRef.createUser(email.getText().toString(), password.getText().toString(), new Firebase.ValueResultHandler<Map<String, Object>>() {
                    @Override
                    public void onSuccess(Map<String, Object> result) {

                        User user = new User(email.getText().toString(), name.getText().toString(), password.getText().toString());

                        Firebase userRef = myFirebaseRef.child("users").child("" + result.get("uid"));
                        userRef.setValue(user);

                        mListener.returnToLogin();

                    }

                    @Override
                    public void onError(FirebaseError firebaseError) {

                    }
                });

            }
        });


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void returnToLogin();
    }




}
