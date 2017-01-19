package com.example.tejakanchinadam.inclass12;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.client.Firebase;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ExpenseDetailsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class ExpenseDetailsFragment extends Fragment {

    TextView tvName,tvDate,tvCategory,tvAmount;
    static String expId;
    Firebase fbExpDet;

    private OnFragmentInteractionListener mListener;

    public ExpenseDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_expense_details, container, false);
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
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Firebase.setAndroidContext(getActivity());
        fbExpDet = new Firebase("https://inclass12-5180.firebaseio.com/");
        //Expense detExp = (Expense) getIntent().getExtras().getSerializable("racha");

        tvName = (TextView) getActivity().findViewById(R.id.tvNameValue);
        tvCategory = (TextView) getActivity().findViewById(R.id.tVCategoryValue);
        tvAmount = (TextView) getActivity().findViewById(R.id.tVAmountValue);
        tvDate = (TextView) getActivity().findViewById(R.id.tVDateValue);

        //tvName.setText(detExp.getName());
        //tvCategory.setText(detExp.getCategory());
        //tvAmount.setText("$ "+detExp.getAmount());
        //tvDate.setText(detExp.getDate());
        //Log.d("Demo", detExp.getId());
        //expId=detExp.getId();

    }

    public void details(Expense expense){

        tvName = (TextView) getActivity().findViewById(R.id.tvNameValue);
        tvCategory = (TextView) getActivity().findViewById(R.id.tVCategoryValue);
        tvAmount = (TextView) getActivity().findViewById(R.id.tVAmountValue);
        tvDate = (TextView) getActivity().findViewById(R.id.tVDateValue);

        tvName.setText(expense.getName());
        tvCategory.setText(expense.getCategory());
        tvAmount.setText("$ "+expense.getAmount());
        tvDate.setText(expense.getDate());
        //Log.d("Demo", detExp.getId())



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
        void onExpenseDetails();
    }
}
