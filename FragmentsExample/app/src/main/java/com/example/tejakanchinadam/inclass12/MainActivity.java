package com.example.tejakanchinadam.inclass12;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.firebase.client.Firebase;

public class MainActivity extends AppCompatActivity implements LoginFragment.OnFragmentInteractionListener, SignUpFragment.OnFragmentInteractionListener,
ExpenseListFragment.OnFragmentInteractionListener, AddExpenseFragment.OnFragmentInteractionListener, ExpenseDetailsFragment.OnFragmentInteractionListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getFragmentManager().beginTransaction().add(R.id.container, new LoginFragment(), "LoginFrag").commit();

    }


    @Override
    public void goToExpenseList() {

        getFragmentManager().beginTransaction().replace(R.id.container, new ExpenseListFragment(), "ExpenseListFrag").commit();

    }

    @Override
    public void goToSingUpActivity() {


        getFragmentManager().beginTransaction().replace(R.id.container, new SignUpFragment(), "SignUpFrag").commit();

    }


    @Override
    public void returnToLogin() {

        getFragmentManager().beginTransaction().replace(R.id.container, new LoginFragment(), "LoginFrag").commit();

    }

    @Override
    public void expenseListInterface() {

    }

    @Override
    public void addExpenseInterface() {

        getFragmentManager().beginTransaction().replace(R.id.container, new AddExpenseFragment(), "ExpenseFrag").commit();

    }

    @Override
    public void Expensedetails(Expense ex) {

        ExpenseDetailsFragment fragment = (ExpenseDetailsFragment) getFragmentManager().findFragmentByTag("expenseDetails");

        fragment.details(ex);

    }


    @Override
    public void onFragmentInteraction() {

        getFragmentManager().beginTransaction().replace(R.id.container, new ExpenseListFragment(), "ExpenseList").commit();

    }

    @Override
    public void onExpenseDetails() {

        getFragmentManager().beginTransaction().replace(R.id.container, new ExpenseDetailsFragment(), "expenseDetails").commit();

    }
}
