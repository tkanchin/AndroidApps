package com.example.tejakanchinadam.inclass13;

import android.Manifest;
import android.animation.AnimatorSet;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.RemoteException;
import android.service.carrier.CarrierMessagingService;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap mMap;

    String[] items = {"place type", "airport", "atm", "bank", "cafe", "parking", "food", "school"};

    ArrayList<String> placesList;

    AlertDialog ad;

    GoogleMapOptions options;

    private GoogleApiClient mGoogleApiClient;

    PendingResult<PlaceLikelihoodBuffer> result;

    ArrayList<LatLng> latLngs;

    LatLng latLngAirport, latLngAtm, latLngBank, latLngCafe, latLngParking, latLngFood, latLngSchool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        latLngs = new ArrayList<>();

        options = new GoogleMapOptions();

        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();


        placesList = new ArrayList<String>();

        final LayoutInflater inflater = getLayoutInflater();
        final LinearLayout dialoglayout = (LinearLayout) inflater.inflate(R.layout.custom_linear_layout, null);

        //spinner

        Spinner dropdown = (Spinner) dialoglayout.findViewById(R.id.spinner);

        Button goButton = (Button) dialoglayout.findViewById(R.id.button);

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(spinnerArrayAdapter);

        //spinner

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        ad = builder.create();

        builder.setTitle("Select the place type");

        builder.setView(dialoglayout);
        builder.show();

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //Log.d("Demo1", items[position]);

                if (!items[position].equals("place type")) {

                    placesList.add(items[position]);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                options.mapType(GoogleMap.MAP_TYPE_SATELLITE)
                        .compassEnabled(false)
                        .rotateGesturesEnabled(false)
                        .tiltGesturesEnabled(false);

                setContentView(R.layout.activity_maps);

                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);
                mapFragment.getMapAsync(MapsActivity.this);

                dialoglayout.setAlpha(0);

                ad.cancel();


                Toast.makeText(getApplicationContext(), "Click anywhere on the screen to dismiss this box", Toast.LENGTH_LONG).show();


                if (ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                result = Places.PlaceDetectionApi
                        .getCurrentPlace(mGoogleApiClient, null);
                result.setResultCallback(new ResultCallback<PlaceLikelihoodBuffer>() {
                    public void onResult(PlaceLikelihoodBuffer likelyPlaces) {
                        for (PlaceLikelihood placeLikelihood : likelyPlaces) {
                            Log.i("Demo", String.format("Place '%s' has likelihood: %g",
                                    placeLikelihood.getPlace().getName(),
                                    placeLikelihood.getLikelihood()));
                            latLngs.add(placeLikelihood.getPlace().getLatLng());

                        }
                        likelyPlaces.release();
                    }
                });



            }
        });

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        for (LatLng latLng : latLngs) {
            mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
            mMap.addMarker(new MarkerOptions().position(latLng));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
        }

        for (String s : placesList) {

            switch (s) {
                case "airport":
                    mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                    mMap.addMarker(new MarkerOptions().position(latLngAirport).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngAirport, 16));
                    break;
                case "atm":
                    mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                    mMap.addMarker(new MarkerOptions().position(latLngAtm).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngAtm, 16));
                    break;
                case "bank":
                    mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                    mMap.addMarker(new MarkerOptions().position(latLngBank).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngBank, 16));
                    break;
                case "cafe":
                    mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                    mMap.addMarker(new MarkerOptions().position(latLngCafe).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngCafe, 16));
                    break;
                case "parking":
                    mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                    mMap.addMarker(new MarkerOptions().position(latLngParking).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngParking, 16));
                    break;
                case "food":
                    mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                    mMap.addMarker(new MarkerOptions().position(latLngFood).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngFood, 16));
                    break;
                case "school":
                    mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                    mMap.addMarker(new MarkerOptions().position(latLngSchool).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngSchool, 16));
                    break;
            }
        }

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}
