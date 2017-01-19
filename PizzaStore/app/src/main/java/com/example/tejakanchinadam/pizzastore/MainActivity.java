package com.example.tejakanchinadam.pizzastore;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.preference.DialogPreference;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Toppings myToppings = new Toppings();

    final CharSequence[] items = myToppings.mytoppingsArray;

    int sum;

    final String[] myString = null;

    // final int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setIcon(R.drawable.app_icon);

        // TableLayout table = new TableLayout(this);

        TableLayout table = (TableLayout) findViewById(R.id.tableLay);

        final TableRow.LayoutParams t1 = new TableRow.LayoutParams(150, 150);


        final TableRow row1 = (TableRow) findViewById(R.id.row1);

        final TableRow row2 = (TableRow) findViewById(R.id.row2);

        //row1.setLayoutParams(t1);

        final ProgressBar myProgressBar = (ProgressBar) findViewById(R.id.progressBar);


        final  ImageView img1 = new ImageView(MainActivity.this);

        img1.setLayoutParams(t1);



        // row1.addView(img)


        sum = 1;


        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Choose a Topping")
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        img1.setImageResource(myToppings.toppingImage(which));


                        img1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {


                                if (sum <= 5) {

                                    //  Log.d("Demo", items[which].toString());
                                    row1.removeView(img1);

                                    myProgressBar.setProgress(--sum);

                                    StringBuilder builder = new StringBuilder();


                                } else {

                                    //  Log.d("Demo", which.toString());
                                    row2.removeView(img1);

                                    myProgressBar.setProgress(--sum);


                                }


                            }
                        });

                        if (sum <= 10) {

                            Log.d("Demo", items[which].toString());


                            if (sum <= 5) {

                                //  Log.d("Demo", items[which].toString());
                                row1.addView(img1);

                                myProgressBar.setProgress(sum);

                            } else {

                                //  Log.d("Demo", which.toString());
                                row2.addView(img1);

                                myProgressBar.setProgress(sum);


                            }


                        } else {

                            Toast.makeText(getApplication(), "Maximum numer of Toppings limit reached", Toast.LENGTH_LONG).show();

                        }


                        sum++;


                    }
                }).setCancelable(false);







        final AlertDialog alert = builder.create();

        findViewById(R.id.btnTopping).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alert.show();

            }
        });


        findViewById(R.id.btnClear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                row1.removeAllViews();

                row2.removeAllViews();


            }
        });

 Intent intent = new Intent(this,OrderActivity.class);
        intent.putExtra("TOPPINGS",myString );


    }
}