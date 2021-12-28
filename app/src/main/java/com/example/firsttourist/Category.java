package com.example.firsttourist;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class Category extends AppCompatActivity {
    Animation bottomAnim;

    private LocationRequest locationRequest;
//    private Button googleMap;
    Double latitude=0.0, longitude=0.0;
    String res="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.buttom_animation);
        RelativeLayout r1 = findViewById(R.id.relative1);
        r1.setAnimation(bottomAnim);
        RelativeLayout r2 = findViewById(R.id.relative2);
        r2.setAnimation(bottomAnim);
        RelativeLayout r3 = findViewById(R.id.relative3);
        r3.setAnimation(bottomAnim);
        RelativeLayout r4 = findViewById(R.id.relative4);
        r4.setAnimation(bottomAnim);
        RelativeLayout r5 = findViewById(R.id.relative5);
        r5.setAnimation(bottomAnim);
        RelativeLayout r6 = findViewById(R.id.relative6);
        r6.setAnimation(bottomAnim);

//        googleMap = findViewById(R.id.map);

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(2000);

        getCurrentLocation();


//        googleMap.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v)
//            {
//                String res = "latitude : "+Double.toString(latitude)+"\n longitude: "+Double.toString(longitude);
//                Toast.makeText(getApplicationContext(),res,Toast.LENGTH_SHORT).show();
//            }
//        });

    }



    public void open_falls(View view){
        Intent i1 = new Intent(this, ListWise.class);
        getCurrentLocation();
//        res = "latitude : "+Double.toString(latitude)+"\n longitude: "+Double.toString(longitude);
//        Toast.makeText(getApplicationContext(),res,Toast.LENGTH_LONG).show();
        i1.putExtra("Type", 1);
        i1.putExtra("i_lat",latitude);
        i1.putExtra("i_lon",longitude);

        startActivity(i1);
    }
    public void open_temples(View view){
        Intent i1 = new Intent(this, ListWise.class);
        getCurrentLocation();
        i1.putExtra("Type", 2);
        i1.putExtra("i_lat",latitude);
        i1.putExtra("i_lon",longitude);
        startActivity(i1);
    }
    public void open_wildlife(View view){
        Intent i1 = new Intent(this, ListWise.class);
        getCurrentLocation();
        i1.putExtra("Type", 3);
        i1.putExtra("i_lat",latitude);
        i1.putExtra("i_lon",longitude);
        startActivity(i1);
    }
    public void open_beaches(View view){
        Intent i1 = new Intent(this, ListWise.class);
        getCurrentLocation();
        i1.putExtra("Type", 4);
        i1.putExtra("i_lat",latitude);
        i1.putExtra("i_lon",longitude);
        startActivity(i1);
    }
    public void open_museums(View view){
        Intent i1 = new Intent(this, ListWise.class);
        getCurrentLocation();
        i1.putExtra("Type", 5);
        i1.putExtra("i_lat",latitude);
        i1.putExtra("i_lon",longitude);
        startActivity(i1);
    }
    public void open_hills(View view){
        Intent i1 = new Intent(this, ListWise.class);
        getCurrentLocation();
        i1.putExtra("Type", 6);
        i1.putExtra("i_lat",latitude);
        i1.putExtra("i_lon",longitude);
        startActivity(i1);
    }
    public void open_city(View view){
        Intent i1 = new Intent(this, ListWise.class);
        getCurrentLocation();
        i1.putExtra("Type", 7);
        EditText findcity = findViewById(R.id.get_city);
        String str=findcity.getText().toString();
        i1.putExtra("find_city",str);
        i1.putExtra("i_lat",latitude);
        i1.putExtra("i_lon",longitude);
        startActivity(i1);
    }




                //get user current location
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                if (isGPSEnabled()) {

                    getCurrentLocation();

                } else {

                    turnOnGPS();
                }
            }
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {

                getCurrentLocation();
            }
        }
    }

    private void getCurrentLocation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(Category.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                if (isGPSEnabled()) {

                    LocationServices.getFusedLocationProviderClient(Category.this)
                            .requestLocationUpdates(locationRequest, new LocationCallback() {
                                @Override
                                public void onLocationResult(@NonNull LocationResult locationResult) {
                                    super.onLocationResult(locationResult);

                                    LocationServices.getFusedLocationProviderClient(Category.this)
                                            .removeLocationUpdates(this);

                                    if (locationResult != null && locationResult.getLocations().size() > 0) {

                                        int index = locationResult.getLocations().size() - 1;
                                        latitude = locationResult.getLocations().get(index).getLatitude();
                                        longitude = locationResult.getLocations().get(index).getLongitude();
//                                         AddressText.setText("Latitude: "+ latitude + "\n" + "Longitude: "+ longitude);
                                    }
                                }
                            }, Looper.getMainLooper());

                } else {
                    turnOnGPS();
                }

            } else {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }
    }

    private void turnOnGPS() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(getApplicationContext())
                .checkLocationSettings(builder.build());

        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {

                try {
                    LocationSettingsResponse response = task.getResult(ApiException.class);
                    Toast.makeText(Category.this, "GPS is already tured on", Toast.LENGTH_SHORT).show();

                } catch (ApiException e) {

                    switch (e.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:

                            try {
                                ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                                resolvableApiException.startResolutionForResult(Category.this, 2);
                            } catch (IntentSender.SendIntentException ex) {
                                ex.printStackTrace();
                            }
                            break;

                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            //Device does not have location
                            break;
                    }
                }
            }
        });

    }

    private boolean isGPSEnabled() {
        LocationManager locationManager = null;
        boolean isEnabled = false;

        if (locationManager == null) {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        }

        isEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return isEnabled;

    }


}