package com.example.tejakanchinadam.inclass12;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.zip.Inflater;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ExpenseListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class ExpenseListFragment extends Fragment {

    static ArrayList<Expense> expenseList ;

    Expense posts;

    ListView listView;

    ExpenseListAdapter adapter;

    ArrayList<String> dummyAl;


    private OnFragmentInteractionListener mListener;

    public ExpenseListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_expense_list, container, false);
        return view;
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


    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.menu_values_logout, menu);
        //return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menuExpense:
                mListener.addExpenseInterface();
                return true;
            case R.id.menuLogOut:
                //clearAll();
                Firebase fbLogout = new Firebase("https://inclass12-5180.firebaseio.com/");
                fbLogout.unauth();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



        listView = (ListView) getActivity().findViewById(R.id.listView);
        Firebase.setAndroidContext(getActivity());
        final Firebase fbExpen = new Firebase("https://inclass12-5180.firebaseio.com/Expenses");
        expenseList=new ArrayList<Expense>();
       loadList(expenseList);


//        adapter.setNotifyOnChange(true);



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                posts = expenseList.get(position);
                //Intent intent = new Intent(ExpensesList.this, ExpenseDetails.class);
                //intent.putExtra("racha", posts);
                //startActivity(intent);
                mListener.onExpenseDetails();
                mListener.Expensedetails(posts);


            }
        });


        fbExpen.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    posts = postSnapshot.getValue(Expense.class);
                            //Log.d("Posts", posts.getName());
                            //Log.d("EMail", fbExpen.getAuth().getProviderData().get("email").toString());



                            if ((posts.getUser()).equals(fbExpen.getAuth().getProviderData().get("email"))) {

                               // if(!dummyAl.contains(posts.getCategory())){
                                    expenseList.add(posts);
                                    adapter.notifyDataSetChanged();
                                   // dummyAl.add(posts.getCategory());
                                //}
                        }
                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed " + firebaseError.getMessage());

            }
        });




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
        void expenseListInterface();
        void addExpenseInterface();
        void onExpenseDetails();
        void Expensedetails(Expense ex);
    }

    public void menus(){



    }


    public void loadList(ArrayList<Expense> expenseList){
        adapter = new ExpenseListAdapter(getActivity(), R.layout.expenses_listview, expenseList);
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);
    }


}
