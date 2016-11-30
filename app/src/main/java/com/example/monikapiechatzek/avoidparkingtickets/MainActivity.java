package com.example.monikapiechatzek.avoidparkingtickets;

import android.*;
import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiActivity;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private Location mLastLocation;
    private GoogleApiClient mGoogleApiClient;
    private int PERMISSION_1 = 1;

    private TextView lblLocation;
    private Button ticketter_spotted, user_checkin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lblLocation = (TextView) findViewById(R.id.location);
        ticketter_spotted = (Button) findViewById(R.id.ticketter_spotted_btn);
        user_checkin = (Button) findViewById(R.id.check_in_btn);


        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
            Log.d("HALP", "goes into mGoogleapi");
        }

        ticketter_spotted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "TICKETTER CLICKED", Toast.LENGTH_SHORT).show();
//                displayLocation();
                Log.d("HALP", "goes into ticketter_spotted");
            }
        });

        user_checkin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "CHECKIN CLICKED", Toast.LENGTH_SHORT).show();
//                displayLocation();
            }
        });
    }

    @Override
    protected void onStart() {

        super.onStart();
        Log.d("HALP", "goes into onStart");
        mGoogleApiClient.connect();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_1) {
            if (grantResults.length > 1
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // success!

                Log.d("HALP", " onRequestPermissionsResult -> Permission granted");


                if (mLastLocation != null) {
                    String latitude = String.valueOf(mLastLocation.getLatitude());
                    String longtitude = String.valueOf(mLastLocation.getLongitude());
                    Log.d("HALP", "goes into onRequestPermissionsResult");
                    lblLocation.setText(latitude + ", " + longtitude);
                    Log.d("HALP","LAT , LONG = " + latitude + ", " + longtitude);



                } else {
                    // Permission was denied or request was cancelled

                    Log.d("HALP", "mLastLocation is null");

                }
            } else {
                Log.d("HALP", "onRequestPermissionsResult -> Permission denied");

            }
        } else {

            Log.d("HALP", "onRequestPermissionsResult -> Permission code doesn't match");

        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            if ((ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) || (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION))){

                Log.d("HALP", "No Permission granted - Needs explanation");
            } else {

                Log.d("HALP", "No Permission granted - No Need to explain");
                mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                        mGoogleApiClient);
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                        PERMISSION_1);

                Log.d("HALP", " Permission Requested - ++++++++++++++ ");


            }

        } else {

            Log.d("HALP", " Permission granted already");

            Log.d("HALP", "goes into onConnected");
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);
            if (mLastLocation != null) {
                String latitude = String.valueOf(mLastLocation.getLatitude());
                String longtitude = String.valueOf(mLastLocation.getLongitude());
                lblLocation.setText(latitude + ", " + longtitude);

                Log.d("HALP", "goes into onConnected inside mLastLocation");



        }


    }

        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);

    }



    @Override
    public void onConnectionSuspended(int i) {
        Log.d("HALP", "goes into onConnectionSuspended");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d("HALP", "goes into onConnectionFailed");



    }
    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }
}