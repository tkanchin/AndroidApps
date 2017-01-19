package com.example.tejakanchinadam.inclass12;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.firebase.client.Firebase;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddExpenseFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class AddExpenseFragment extends Fragment {

    Firebase ref;

    EditText Name, Amount, dateEdit;

    String email;

    private OnFragmentInteractionListener mListener;

    public AddExpenseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_expense, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final Spinner dropdown = (Spinner) getActivity().findViewById(R.id.spinner);
        String[] items = new String[]{"Groceries", "Invoice", "Transportation", "Shopping", "Rent",
                "Trips", "Utilities", "Other"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items);

        dropdown.setAdapter(adapter);

        Firebase.setAndroidContext(getActivity());



        ref = new Firebase("https://inclass12-5180.firebaseio.com/");

        final Firebase fbExpense = ref.child("Expenses");


         email = (String) ref.getAuth().getProviderData().get("email");

        Name = (EditText) getActivity().findViewById(R.id.expenseName);

        Amount = (EditText) getActivity().findViewById(R.id.expenseAmount);

        dateEdit = (EditText) getActivity().findViewById(R.id.expenseDate);

        getActivity().findViewById(R.id.expenseButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = Name.getText().toString();
                String amount = Amount.getText().toString();
                String date = dateEdit.getText().toString();
                String category = dropdown.getSelectedItem().toString();


                //Firebase postRef = ref.child("Expenses");

                //Firebase newPostRef = postRef.push();

                Expense expense = new Expense();

                expense.setName(name);

                expense.setCategory(category);


                expense.setDate(date);

                expense.setAmount(amount);

                Map<String, String> post2 = new HashMap<String, String>();
                post2.put("name", name);
                post2.put("amount", amount);
                post2.put("category", category);
                post2.put("user", ref.getAuth().getProviderData().get("email").toString());
                post2.put("date", date);
                fbExpense.push().setValue(post2);

                //ExpensesList.expenseList.add(expense);
                //newPostRef.setValue(expense);

                //sendMessage.setText("");

                mListener.onFragmentInteraction();


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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction();
    }
}
