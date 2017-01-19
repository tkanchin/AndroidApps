package com.example.tejakanchinadam.photogallery;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    int counter;
    HashMap<String, ArrayList<String>> myHashMap = new HashMap<String,ArrayList<String>>();
    List<String> getURL = new ArrayList<String>();
    int listSize;
    ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (isConnectedOnline()){

           // new GetDataWithParams().execute("http://dev.theappsdr.com/apis/spring_2016/inclass5/URLs.txt");

            RequestParams params = new RequestParams("GET", "http://dev.theappsdr.com/apis/spring_2016/inclass5/URLs.txt");

            new GetDataWithParams().execute(params);


            //printMap(myHashMap);

        } else {

            Toast.makeText(getApplicationContext(),"Please connect to the Internet", Toast.LENGTH_LONG).show();


        }

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                printMap(myHashMap);

            }
        });


    }




    private class GetDataWithParams extends AsyncTask<RequestParams , Void, String> {


        @Override
        protected String doInBackground(RequestParams... params) {


            BufferedReader reader = null;

            try {
                HttpURLConnection con = params[0].starsConnection();
                reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                StringBuilder sb = new StringBuilder();

                String line = "";

                while((line = reader.readLine()) != null){

                    sb.append(line + "\n");

                }

                return sb.toString();



            } catch (IOException e) {
                e.printStackTrace();
            }


            finally {

                try {

                    if(reader != null) {

                        reader.close();

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }



            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            // super.onPostExecute(s);


            //myHashMap
            if (s != null)
            {
                String[] wholeLine = s.split(";");

                ArrayList<String> temp = new ArrayList<String>();
                ArrayList<String> tempList1= new ArrayList<String>();;
                ArrayList<String> tempList2= new ArrayList<String>();;
                ArrayList<String> tempList3= new ArrayList<String>();;ArrayList<String> tempList5= new ArrayList<String>();;
                ArrayList<String> tempList4= new ArrayList<String>();;


                myHashMap.put("UNCC", tempList1);
                myHashMap.put("Android",tempList2);
                myHashMap.put("aurora borealis",tempList3);
                myHashMap.put("winter",tempList4);
                myHashMap.put("wonders",tempList5);



                for (String line: wholeLine)
                {




                    String[] lineSplit = line.split(",");


                    if(lineSplit[0].equals("UNCC"))
                    {
                        tempList1 = new ArrayList<String>();
                        tempList1 = myHashMap.get(lineSplit[0]);
                        tempList1.add(lineSplit[1]);
                        myHashMap.put(lineSplit[0], tempList1);
                    }

                    else if(lineSplit[0].equals("Android"))
                    {
                        tempList2 = new ArrayList<String>();
                        tempList2 = myHashMap.get(lineSplit[0]);
                        tempList2.add(lineSplit[1]);
                        myHashMap.put(lineSplit[0],tempList2);
                    }
                    else if(lineSplit[0].equals("aurora borealis"))
                    {
                        tempList3 = new ArrayList<String>();
                        tempList3 = myHashMap.get(lineSplit[0]);
                        tempList3.add(lineSplit[1]);
                        myHashMap.put(lineSplit[0],tempList3);
                    }
                    else if(lineSplit[0].equals("wonders"))
                    {
                        tempList4 = new ArrayList<String>();
                        tempList4 = myHashMap.get(lineSplit[0]);
                        tempList4.add(lineSplit[1]);
                        myHashMap.put(lineSplit[0],tempList4);
                    }
                    else if(lineSplit[0].equals("winter"))
                    {
                        tempList5 = new ArrayList<String>();
                        tempList5 = myHashMap.get(lineSplit[0]);
                        tempList5.add(lineSplit[1]);
                        myHashMap.put(lineSplit[0],tempList5);
                    }



                }

//                Log.d("demo",
//                myHashMap.get("UNCC").size() +
//                myHashMap.get("Android").size() +
//                myHashMap.get("aurora borealis").size() +
//                myHashMap.get("winter").size()+
//                myHashMap.get("wonders").size() +"");

                Log.d("demo",
                        myHashMap.get("aurora borealis").size() + "");


            }

            else {

                Log.d("Demo", "Error");

            }





            Button button = (Button) findViewById(R.id.button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditText searchText = (EditText) findViewById(R.id.searchText);
                    String searchTextString = searchText.getText().toString();

                    getURL = myHashMap.get(searchTextString);

                    if(searchTextString.equals(null))
                    {
                        Toast.makeText(MainActivity.this, "Empty", Toast.LENGTH_SHORT).show();
                    }
                    else if(getURL.isEmpty())
                    {
                        Toast.makeText(MainActivity.this, "No Results found", Toast.LENGTH_SHORT).show();

                    }
                    else
                    {


                        counter = 0;

                        final ImageView myImageView = (ImageView) findViewById(R.id.myImageView);
                        Picasso.with(MainActivity.this).load(getURL.get(0)).error(R.drawable.ic_action_next_item).into(myImageView);

                        pDialog = new ProgressDialog(MainActivity.this);
                        pDialog.setCancelable(true);
                        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        pDialog.setTitle("Loading...");
                        pDialog.setMessage("Loading Photo");


                        listSize = getURL.size();
                        ImageButton next = (ImageButton) findViewById(R.id.next);
                        next.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                pDialog.show();

                                if(counter == listSize - 1)
                                {
                                    counter = 0;
                                }
                                else
                                {
                                    counter++;
                                }

                                Picasso.with(MainActivity.this).load(getURL.get(counter)).error(R.drawable.ic_action_next_item).into(myImageView);
                                pDialog.dismiss();

                            }
                        });


                        ImageButton previous = (ImageButton) findViewById(R.id.previous);
                        previous.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pDialog.show();
                                if(counter == 0)
                                {
                                    counter = listSize - 1;
                                }
                                else
                                {
                                    counter--;
                                }

                                Picasso.with(MainActivity.this).load(getURL.get(counter)).error(R.drawable.ic_action_next_item).into(myImageView);

                                pDialog.dismiss();
                            }
                        });
                    }



                }
            });

        }
    }





    private boolean isConnectedOnline(){

        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo =  cm.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {

            return true;

        } else {

            return false;
        }

    }

    public static void printMap(HashMap myHashMap) {
        Iterator it = myHashMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            System.out.println(pair.getKey() + " = " + pair.getValue());
            it.remove(); // avoids a ConcurrentModificationException
        }
    }


}
