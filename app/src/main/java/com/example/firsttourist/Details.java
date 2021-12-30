package com.example.firsttourist;

import static android.location.LocationManager.*;
import static java.lang.Thread.sleep;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.Objects;

public class Details extends AppCompatActivity {


    private final String weather_url="http://api.openweathermap.org/data/2.5/weather";
    private final String appid="4f180633048c290fbcb29a36742d179a";
    DecimalFormat df = new DecimalFormat("#.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        TextView t2 = (TextView) findViewById(R.id.text1);
        ImageView im = findViewById(R.id.image);
        RatingBar rb1 = findViewById(R.id.detail_rating);
        Toolbar toolbar = findViewById(R.id.toolbar);


        TextView weather_desc = findViewById(R.id.weather_description);
        TextView weather_temp = findViewById(R.id.weather_temp);
        TextView text_feelslike = findViewById(R.id.text_feelslike);
        TextView text_pressure = findViewById(R.id.text_pressure);
        TextView text_wind = findViewById(R.id.text_wind);
        TextView text_humidity = findViewById(R.id.text_humidity);

        ImageView weather_icon = findViewById(R.id.weather_icon);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String name = intent.getStringExtra("Name");
        String description = intent.getStringExtra("Description");
        String image_url = intent.getStringExtra("Image");
        String rating = intent.getStringExtra("rating");
        String city = intent.getStringExtra("city");
        String location = intent.getStringExtra("location");


        Button map = findViewById(R.id.map);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String url = "geo:0,0?q=" + name + "," + city;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                i.setPackage("com.google.android.apps.maps");
                Intent chooser = Intent.createChooser(i,"Map");
                startActivity(chooser);

            }
        });

        if (intent != null)
        {
            t2.setText(description);
            Glide.with(this)
                    .load(image_url)
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(im);
            rb1.setRating(Float.parseFloat(rating));
            im.setContentDescription(name);
            this.setTitle(name);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            String tempUrl = weather_url + "?q=" + city + "&appid=" + appid;
            StringRequest stringRequest = new StringRequest(Request.Method.POST, tempUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {

                        JSONObject jsonResponse = new JSONObject(response);
                        JSONArray jsonArray = jsonResponse.getJSONArray("weather");
                        JSONObject jsonObjectWeather = jsonArray.getJSONObject(0);
                        String description = jsonObjectWeather.getString("description");
                        JSONObject jsonObjectMain = jsonResponse.getJSONObject("main");
                        double temp = jsonObjectMain.getDouble("temp") - 273.15;
                        double feelsLike = jsonObjectMain.getDouble("feels_like") - 273.15;
                        float pressure = jsonObjectMain.getInt("pressure");
                        int humidity = jsonObjectMain.getInt("humidity");
                        JSONObject jsonObjectWind = jsonResponse.getJSONObject("wind");
                        String wind = jsonObjectWind.getString("speed");
                        JSONObject jsonObjectClouds = jsonResponse.getJSONObject("clouds");
                        String clouds = jsonObjectClouds.getString("all");
                        JSONObject jsonObjectSys = jsonResponse.getJSONObject("sys");
                        String countryName = jsonObjectSys.getString("country");
                        String cityName = jsonResponse.getString("name");

                        weather_desc.setText(description);
                        weather_temp.setText(df.format(temp) + "°C");
                        text_feelslike.setText(df.format(feelsLike) + "°C");
                        text_pressure.setText(pressure + "\nhPa");
                        text_wind.setText(wind + "\nm/sec");
                        text_humidity.setText(humidity + "%");
                        if(description.equals("haze"))
                            weather_icon.setImageResource(R.drawable.cloudy);
                        else if(description.equals("rain"))
                            weather_icon.setImageResource(R.drawable.storm);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener(){

                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_LONG).show();
                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);

        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}