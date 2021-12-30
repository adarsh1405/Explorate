package com.example.firsttourist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.arlib.floatingsearchview.FloatingSearchView;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.TreeMap;

public class ListWise extends AppCompatActivity {
    ListView lv;
    int ltype;
    String mTitle[];
    String mlocation[];
    String mDescription[];
    String images_url[];
    String mcity[];
    float mrating[];
    String mdist[];
    String dist_token = "pk.eyJ1IjoiYWRhcnNoMTQwNSIsImEiOiJja3hrYmRiZ3IxdnJmMnBucDl6aXlrZHdnIn0.uC5YXganbaH08SNcbzHNpA";
    double d_lat=0.0,d_lon=0.0,i_lat=0.0,i_lon=0.0;

    DecimalFormat df = new DecimalFormat("#.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_wise);
        lv = findViewById(R.id.listview1);
        Intent i1=getIntent();
        ltype=i1.getIntExtra("Type",1);

        i_lat = i1.getDoubleExtra("i_lat",1.0);
        i_lon = i1.getDoubleExtra("i_lon",1.0);

        String json;
        ArrayList<String> url = new ArrayList<String>();
        ArrayList<String> name = new ArrayList<String>();
        ArrayList<String> location =new ArrayList<String>();
        ArrayList<String> description = new ArrayList<String>();
        ArrayList<String> city = new ArrayList<String>();
        ArrayList<String> rating=new ArrayList<String>();
        ArrayList<String> dist = new ArrayList<String>();

        if(ltype==1)
        {
            try
            {
                InputStream is = getAssets().open("data.json");
                int size= is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                json = new String(buffer,"UTF-8");
                JSONArray jsonArray = new JSONArray(json);
                for(int i=0;i<jsonArray.length();i++)
                {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    if(obj.getString("category").equals("waterfall"))
                    {
                        url.add(obj.getString("link"));
                        name.add(obj.getString("name"));
                        description.add(obj.getString("description"));
                        location.add(obj.getString("location"));
                        city.add(obj.getString("city"));
                        rating.add(obj.getString("rating"));
                        d_lat = Double.parseDouble(obj.getString("latitude"));
                        d_lon = Double.parseDouble(obj.getString("longitude"));

                        double lon1 = Math.toRadians(i_lon);
                        double lon2 = Math.toRadians(d_lon);
                        double lat1 = Math.toRadians(i_lat);
                        double lat2 = Math.toRadians(d_lat);
                        // Haversine formula
                        double dlon = lon2 - lon1;
                        double dlat = lat2 - lat1;
                        double a = Math.pow(Math.sin(dlat / 2), 2)
                                + Math.cos(lat1) * Math.cos(lat2)
                                * Math.pow(Math.sin(dlon / 2),2);
                        double c = 2 * Math.asin(Math.sqrt(a));
                        double r = 6371;
                        double res=c*r;
                        dist.add(df.format(res));
                    }
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
            int len = name.size();
            mTitle= new String[len];
            mDescription = new String[len];
            mlocation = new String[len];
            images_url = new String[len];
            mcity = new String[len];
            mrating = new float[len];
            mdist = new String[len];


            //sort the array based on the nearest distance
            TreeMap<Double , ArrayList<String>> tm = new TreeMap<Double,ArrayList<String>>();

            for(int i=0;i<len;i++)
            {
                ArrayList<String> arr = new ArrayList<String>();
                    arr.add(name.get(i));
                    arr.add(location.get(i));
                    arr.add(url.get(i));
                    arr.add(rating.get(i));
                    arr.add(city.get(i));
                    arr.add(description.get(i));
                tm.put(Double.parseDouble(dist.get(i)),arr);
            }
            int count=0;
            for(Double key : tm.keySet()) {
                ArrayList<String> so =new ArrayList<String>(tm.get(key));
                mTitle[count] = so.get(0);
                mlocation[count]=so.get(1);
                images_url[count]=so.get(2);
                mrating[count]  = Float.parseFloat(so.get(3));
                mcity[count]=so.get(4);
                mDescription[count]=so.get(5);
                mdist[count] = Double.toString(key);
                count++;
            }

//            for(int i=0;i<name.size();i++)
//            {
//                mTitle[i] = name.get(i);
//                mlocation[i]=location.get(i);
//                mDescription[i]=description.get(i);
//                mcity[i]=city.get(i);
//                mrating[i]  = Float.parseFloat(rating.get(i));
//                images_url[i]=url.get(i);
//                mdist[i] = dist.get(i);
//            }



            MyAdapter adapter = new MyAdapter(this, mTitle,mlocation,images_url, mrating,mdist);
            lv.setAdapter(adapter);
            lv.setClickable(true);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(ListWise.this, Details.class);
                    intent.putExtra("Name", mTitle[position]);
                    intent.putExtra("Location",  mlocation[position]);
                    intent.putExtra("Description",  mDescription[position]);
                    intent.putExtra("Image", images_url[position]);
                    intent.putExtra("city", mcity[position]);
                    intent.putExtra("rating",Float.toString(mrating[position]));
                    intent.putExtra("dist",mdist[position]);
                    startActivity(intent);
                }
            });
        }
        else if(ltype==2)
        {
            try
            {
                InputStream is = getAssets().open("data.json");
                int size= is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                json = new String(buffer,"UTF-8");
                JSONArray jsonArray = new JSONArray(json);
                for(int i=0;i<jsonArray.length();i++)
                {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    if(obj.getString("category").equals("temple"))
                    {
                        url.add(obj.getString("link"));
                        name.add(obj.getString("name"));
                        description.add(obj.getString("description"));
                        location.add(obj.getString("location"));
                        city.add(obj.getString("city"));
                        rating.add(obj.getString("rating"));
                        d_lat = Double.parseDouble(obj.getString("latitude"));
                        d_lon = Double.parseDouble(obj.getString("longitude"));

                        double lon1 = Math.toRadians(i_lon);
                        double lon2 = Math.toRadians(d_lon);
                        double lat1 = Math.toRadians(i_lat);
                        double lat2 = Math.toRadians(d_lat);
                        // Haversine formula
                        double dlon = lon2 - lon1;
                        double dlat = lat2 - lat1;
                        double a = Math.pow(Math.sin(dlat / 2), 2)
                                + Math.cos(lat1) * Math.cos(lat2)
                                * Math.pow(Math.sin(dlon / 2),2);
                        double c = 2 * Math.asin(Math.sqrt(a));
                        double r = 6371;
                        double res=c*r;
                        dist.add(df.format(res));
                    }
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
            int len = name.size();
            mTitle= new String[len];
            mDescription = new String[len];
            mlocation = new String[len];
            images_url = new String[len];
            mcity = new String[len];
            mrating = new float[len];
            mdist = new String[len];

            //not done sorting
            for(int i=0;i<name.size();i++)
            {
                mTitle[i] = name.get(i);
                mlocation[i]=location.get(i);
                mDescription[i]=description.get(i);
                mcity[i]=city.get(i);
                mrating[i]  = Float.parseFloat(rating.get(i));
                images_url[i]=url.get(i);
                mdist[i] = dist.get(i);
            }
            MyAdapter adapter = new MyAdapter(this, mTitle,mlocation,images_url, mrating,mdist);
            lv.setAdapter(adapter);
            lv.setClickable(true);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(ListWise.this, Details.class);
                    intent.putExtra("Name", mTitle[position]);
                    intent.putExtra("Location",  mlocation[position]);
                    intent.putExtra("Description",  mDescription[position]);
                    intent.putExtra("Image", images_url[position]);
                    intent.putExtra("city", mcity[position]);
                    intent.putExtra("rating",Float.toString(mrating[position]));
                    intent.putExtra("dist",mdist[position]);
                    startActivity(intent);
                }
            });
        }
        else if(ltype==3)
        {
            try
            {
                InputStream is = getAssets().open("data.json");
                int size= is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                json = new String(buffer,"UTF-8");
                JSONArray jsonArray = new JSONArray(json);
                for(int i=0;i<jsonArray.length();i++)
                {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    if(obj.getString("category").equals("forest"))
                    {
                        url.add(obj.getString("link"));
                        name.add(obj.getString("name"));
                        description.add(obj.getString("description"));
                        location.add(obj.getString("location"));
                        city.add(obj.getString("city"));
                        rating.add(obj.getString("rating"));
                        d_lat = Double.parseDouble(obj.getString("latitude"));
                        d_lon = Double.parseDouble(obj.getString("longitude"));

                        double lon1 = Math.toRadians(i_lon);
                        double lon2 = Math.toRadians(d_lon);
                        double lat1 = Math.toRadians(i_lat);
                        double lat2 = Math.toRadians(d_lat);
                        // Haversine formula
                        double dlon = lon2 - lon1;
                        double dlat = lat2 - lat1;
                        double a = Math.pow(Math.sin(dlat / 2), 2)
                                + Math.cos(lat1) * Math.cos(lat2)
                                * Math.pow(Math.sin(dlon / 2),2);
                        double c = 2 * Math.asin(Math.sqrt(a));
                        double r = 6371;
                        double res=c*r;
                        dist.add(df.format(res));
                    }
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
            int len = name.size();
            mTitle= new String[len];
            mDescription = new String[len];
            mlocation = new String[len];
            images_url = new String[len];
            mcity = new String[len];
            mrating = new float[len];
            mdist = new String[len];

            //sort the array based on the nearest distance
            TreeMap<Double , ArrayList<String>> tm = new TreeMap<Double,ArrayList<String>>();

            for(int i=0;i<len;i++)
            {
                ArrayList<String> arr = new ArrayList<String>();
                arr.add(name.get(i));
                arr.add(location.get(i));
                arr.add(url.get(i));
                arr.add(rating.get(i));
                arr.add(city.get(i));
                arr.add(description.get(i));
                tm.put(Double.parseDouble(dist.get(i)),arr);
            }
            int count=0;
            for(Double key : tm.keySet()) {
                ArrayList<String> so =new ArrayList<String>(tm.get(key));
                mTitle[count] = so.get(0);
                mlocation[count]=so.get(1);
                images_url[count]=so.get(2);
                mrating[count]  = Float.parseFloat(so.get(3));
                mcity[count]=so.get(4);
                mDescription[count]=so.get(5);
                mdist[count] = Double.toString(key);
                count++;
            }
            MyAdapter adapter = new MyAdapter(this, mTitle,mlocation,images_url, mrating,mdist);
            lv.setAdapter(adapter);
            lv.setClickable(true);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(ListWise.this, Details.class);
                    intent.putExtra("Name", mTitle[position]);
                    intent.putExtra("Location",  mlocation[position]);
                    intent.putExtra("Description",  mDescription[position]);
                    intent.putExtra("Image", images_url[position]);
                    intent.putExtra("city", mcity[position]);
                    intent.putExtra("rating",Float.toString(mrating[position]));
                    intent.putExtra("dist",mdist[position]);
                    startActivity(intent);
                }
            });
        }
        else if(ltype==4)
        {
            try
            {
                InputStream is = getAssets().open("data.json");
                int size= is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                json = new String(buffer,"UTF-8");
                JSONArray jsonArray = new JSONArray(json);
                for(int i=0;i<jsonArray.length();i++)
                {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    if(obj.getString("category").equals("beach"))
                    {
                        url.add(obj.getString("link"));
                        name.add(obj.getString("name"));
                        description.add(obj.getString("description"));
                        location.add(obj.getString("location"));
                        city.add(obj.getString("city"));
                        rating.add(obj.getString("rating"));
                        d_lat = Double.parseDouble(obj.getString("latitude"));
                        d_lon = Double.parseDouble(obj.getString("longitude"));

                        double lon1 = Math.toRadians(i_lon);
                        double lon2 = Math.toRadians(d_lon);
                        double lat1 = Math.toRadians(i_lat);
                        double lat2 = Math.toRadians(d_lat);
                        // Haversine formula
                        double dlon = lon2 - lon1;
                        double dlat = lat2 - lat1;
                        double a = Math.pow(Math.sin(dlat / 2), 2)
                                + Math.cos(lat1) * Math.cos(lat2)
                                * Math.pow(Math.sin(dlon / 2),2);
                        double c = 2 * Math.asin(Math.sqrt(a));
                        double r = 6371;
                        double res=c*r;
                        dist.add(df.format(res));
                    }
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
            int len = name.size();
            mTitle= new String[len];
            mDescription = new String[len];
            mlocation = new String[len];
            images_url = new String[len];
            mcity = new String[len];
            mrating = new float[len];
            mdist = new String[len];

            //sort the array based on the nearest distance
            TreeMap<Double , ArrayList<String>> tm = new TreeMap<Double,ArrayList<String>>();

            for(int i=0;i<len;i++)
            {
                ArrayList<String> arr = new ArrayList<String>();
                arr.add(name.get(i));
                arr.add(location.get(i));
                arr.add(url.get(i));
                arr.add(rating.get(i));
                arr.add(city.get(i));
                arr.add(description.get(i));
                tm.put(Double.parseDouble(dist.get(i)),arr);
            }
            int count=0;
            for(Double key : tm.keySet()) {
                ArrayList<String> so =new ArrayList<String>(tm.get(key));
                mTitle[count] = so.get(0);
                mlocation[count]=so.get(1);
                images_url[count]=so.get(2);
                mrating[count]  = Float.parseFloat(so.get(3));
                mcity[count]=so.get(4);
                mDescription[count]=so.get(5);
                mdist[count] = Double.toString(key);
                count++;
            }
            MyAdapter adapter = new MyAdapter(this, mTitle,mlocation,images_url, mrating,mdist);
            lv.setAdapter(adapter);
            lv.setClickable(true);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(ListWise.this, Details.class);
                    intent.putExtra("Name", mTitle[position]);
                    intent.putExtra("Location",  mlocation[position]);
                    intent.putExtra("Description",  mDescription[position]);
                    intent.putExtra("Image", images_url[position]);
                    intent.putExtra("city", mcity[position]);
                    intent.putExtra("rating",Float.toString(mrating[position]));
                    intent.putExtra("dist",mdist[position]);
                    startActivity(intent);
                }
            });
        }
        else if(ltype==5)
        {
            try
            {
                InputStream is = getAssets().open("data.json");
                int size= is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                json = new String(buffer,"UTF-8");
                JSONArray jsonArray = new JSONArray(json);
                for(int i=0;i<jsonArray.length();i++)
                {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    if(obj.getString("category").equals("museums"))
                    {
                        url.add(obj.getString("link"));
                        name.add(obj.getString("name"));
                        description.add(obj.getString("description"));
                        location.add(obj.getString("location"));
                        city.add(obj.getString("city"));
                        rating.add(obj.getString("rating"));
                        d_lat = Double.parseDouble(obj.getString("latitude"));
                        d_lon = Double.parseDouble(obj.getString("longitude"));

                        double lon1 = Math.toRadians(i_lon);
                        double lon2 = Math.toRadians(d_lon);
                        double lat1 = Math.toRadians(i_lat);
                        double lat2 = Math.toRadians(d_lat);
                        // Haversine formula
                        double dlon = lon2 - lon1;
                        double dlat = lat2 - lat1;
                        double a = Math.pow(Math.sin(dlat / 2), 2)
                                + Math.cos(lat1) * Math.cos(lat2)
                                * Math.pow(Math.sin(dlon / 2),2);
                        double c = 2 * Math.asin(Math.sqrt(a));
                        double r = 6371;
                        double res=c*r;
                        dist.add(df.format(res));
                    }
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
            int len = name.size();
            mTitle= new String[len];
            mDescription = new String[len];
            mlocation = new String[len];
            images_url = new String[len];
            mcity = new String[len];
            mrating = new float[len];
            mdist = new String[len];

            //not done sorting
            for(int i=0;i<name.size();i++)
            {
                mTitle[i] = name.get(i);
                mlocation[i]=location.get(i);
                mDescription[i]=description.get(i);
                mcity[i]=city.get(i);
                mrating[i]  = Float.parseFloat(rating.get(i));
                images_url[i]=url.get(i);
                mdist[i] = dist.get(i);
            }
            MyAdapter adapter = new MyAdapter(this, mTitle,mlocation,images_url, mrating,mdist);
            lv.setAdapter(adapter);
            lv.setClickable(true);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(ListWise.this, Details.class);
                    intent.putExtra("Name", mTitle[position]);
                    intent.putExtra("Location",  mlocation[position]);
                    intent.putExtra("Description",  mDescription[position]);
                    intent.putExtra("Image", images_url[position]);
                    intent.putExtra("city", mcity[position]);
                    intent.putExtra("rating",Float.toString(mrating[position]));
                    intent.putExtra("dist",mdist[position]);
                    startActivity(intent);
                }
            });
        }
        else if(ltype==6)
        {
            try
            {
                InputStream is = getAssets().open("data.json");
                int size= is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                json = new String(buffer,"UTF-8");
                JSONArray jsonArray = new JSONArray(json);
                for(int i=0;i<jsonArray.length();i++)
                {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    if(obj.getString("category").equals("hill"))
                    {
                        url.add(obj.getString("link"));
                        name.add(obj.getString("name"));
                        description.add(obj.getString("description"));
                        location.add(obj.getString("location"));
                        city.add(obj.getString("city"));
                        rating.add(obj.getString("rating"));
                        d_lat = Double.parseDouble(obj.getString("latitude"));
                        d_lon = Double.parseDouble(obj.getString("longitude"));

                        double lon1 = Math.toRadians(i_lon);
                        double lon2 = Math.toRadians(d_lon);
                        double lat1 = Math.toRadians(i_lat);
                        double lat2 = Math.toRadians(d_lat);
                        // Haversine formula
                        double dlon = lon2 - lon1;
                        double dlat = lat2 - lat1;
                        double a = Math.pow(Math.sin(dlat / 2), 2)
                                + Math.cos(lat1) * Math.cos(lat2)
                                * Math.pow(Math.sin(dlon / 2),2);
                        double c = 2 * Math.asin(Math.sqrt(a));
                        double r = 6371;
                        double res=c*r;
                        dist.add(df.format(res));
                    }
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
            int len = name.size();
            mTitle= new String[len];
            mDescription = new String[len];
            mlocation = new String[len];
            images_url = new String[len];
            mcity = new String[len];
            mrating = new float[len];
            mdist = new String[len];

            //sort the array based on the nearest distance
            TreeMap<Double , ArrayList<String>> tm = new TreeMap<Double,ArrayList<String>>();

            for(int i=0;i<len;i++)
            {
                ArrayList<String> arr = new ArrayList<String>();
                arr.add(name.get(i));
                arr.add(location.get(i));
                arr.add(url.get(i));
                arr.add(rating.get(i));
                arr.add(city.get(i));
                arr.add(description.get(i));
                tm.put(Double.parseDouble(dist.get(i)),arr);
            }
            int count=0;
            for(Double key : tm.keySet()) {
                ArrayList<String> so =new ArrayList<String>(tm.get(key));
                mTitle[count] = so.get(0);
                mlocation[count]=so.get(1);
                images_url[count]=so.get(2);
                mrating[count]  = Float.parseFloat(so.get(3));
                mcity[count]=so.get(4);
                mDescription[count]=so.get(5);
                mdist[count] = Double.toString(key);
                count++;
            }
            MyAdapter adapter = new MyAdapter(this, mTitle,mlocation,images_url, mrating,mdist);
            lv.setAdapter(adapter);
            lv.setClickable(true);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(ListWise.this, Details.class);
                    intent.putExtra("Name", mTitle[position]);
                    intent.putExtra("Location",  mlocation[position]);
                    intent.putExtra("Description",  mDescription[position]);
                    intent.putExtra("Image", images_url[position]);
                    intent.putExtra("city", mcity[position]);
                    intent.putExtra("rating",Float.toString(mrating[position]));
                    intent.putExtra("dist",mdist[position]);
                    startActivity(intent);
                }
            });
        }
        else
        {
            try
            {
                String get_city = i1.getStringExtra("find_city");
                InputStream is = getAssets().open("data.json");
                int size= is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();

                json = new String(buffer,"UTF-8");
                JSONArray jsonArray = new JSONArray(json);

                for(int i=0;i<jsonArray.length();i++)
                {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    if(obj.getString("city").equalsIgnoreCase(get_city))
                    {
                        url.add(obj.getString("link"));
                        name.add(obj.getString("name"));
                        description.add(obj.getString("description"));
                        location.add(obj.getString("location"));
                        city.add(obj.getString("city"));
                        rating.add(obj.getString("rating"));

                        d_lat = Double.parseDouble(obj.getString("latitude"));
                        d_lon = Double.parseDouble(obj.getString("longitude"));

                        double lon1 = Math.toRadians(i_lon);
                        double lon2 = Math.toRadians(d_lon);
                        double lat1 = Math.toRadians(i_lat);
                        double lat2 = Math.toRadians(d_lat);
                        // Haversine formula
                        double dlon = lon2 - lon1;
                        double dlat = lat2 - lat1;
                        double a = Math.pow(Math.sin(dlat / 2), 2)
                                + Math.cos(lat1) * Math.cos(lat2)
                                * Math.pow(Math.sin(dlon / 2),2);
                        double c = 2 * Math.asin(Math.sqrt(a));
                        double r = 6371;
                        double res=c*r;
                        dist.add(df.format(res));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            int len = name.size();
            mTitle= new String[len];
            mDescription = new String[len];
            mlocation = new String[len];
            images_url = new String[len];
            mcity = new String[len];
            mrating = new float[len];
            mdist = new String[len];

            //sort the array based on the nearest distance
            TreeMap<Double , ArrayList<String>> tm = new TreeMap<Double,ArrayList<String>>();

            for(int i=0;i<len;i++)
            {
                ArrayList<String> arr = new ArrayList<String>();
                arr.add(name.get(i));
                arr.add(location.get(i));
                arr.add(url.get(i));
                arr.add(rating.get(i));
                arr.add(city.get(i));
                arr.add(description.get(i));
                tm.put(Double.parseDouble(dist.get(i)),arr);
            }
            int count=0;
            for(Double key : tm.keySet()) {
                ArrayList<String> so =new ArrayList<String>(tm.get(key));
                mTitle[count] = so.get(0);
                mlocation[count]=so.get(1);
                images_url[count]=so.get(2);
                mrating[count]  = Float.parseFloat(so.get(3));
                mcity[count]=so.get(4);
                mDescription[count]=so.get(5);
                mdist[count] = Double.toString(key);
                count++;
            }
            MyAdapter adapter = new MyAdapter(this, mTitle,mlocation,images_url, mrating,mdist);
            lv.setAdapter(adapter);
            lv.setClickable(true);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(ListWise.this, Details.class);
                    intent.putExtra("Name", mTitle[position]);
                    intent.putExtra("Location",  mlocation[position]);
                    intent.putExtra("Description",  mDescription[position]);
                    intent.putExtra("Image", images_url[position]);
                    intent.putExtra("city", mcity[position]);
                    intent.putExtra("rating",Float.toString(mrating[position]));
                    intent.putExtra("dist",mdist[position]);
                    startActivity(intent);
                }
            });
        }
    }


    class MyAdapter extends ArrayAdapter<String>{
        Context context;
        String rTitle[];
        String rLocation[];
        String rImgs[];
        float ra[];
        String rDist[];
        MyAdapter(Context c, String title[], String location[], String imgs[], float r[],String dist[]){
            super(c, R.layout.row, R.id.textview1, title);
            this.context = c;
            this.rTitle = title;
            this.rLocation=location;
            this.rImgs = imgs;
            this.ra=r;
            this.rDist=dist;
        }


        @NonNull
        @Override
        public View getView(int position, @NonNull View convertView, @NonNull ViewGroup parent){
            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.row, parent, false);
            ImageView images = row.findViewById(R.id.image);
            TextView myTitle = row.findViewById(R.id.textview1);
            TextView myDescription = row.findViewById(R.id.textview2);
            TextView mDistance = row.findViewById(R.id.dist);
            RatingBar rb=row.findViewById(R.id.ratingBar);




            Glide.with(row)
                    .load(rImgs[position])
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(images);
            myTitle.setText(rTitle[position]);
            myDescription.setText(rLocation[position]);
            if(Double.parseDouble(rDist[position])<=60)
                mDistance.setTextColor(Color.parseColor("#17ff2e"));
            else if(Double.parseDouble(rDist[position])>60 && Double.parseDouble(rDist[position])<=100)
                mDistance.setTextColor(Color.parseColor("#f7ff17"));
            else
                mDistance.setTextColor(Color.parseColor("#ff4917"));
            mDistance.setText(rDist[position]+"km");
            rb.setRating(ra[position]);
            return row;
        }
    }
}