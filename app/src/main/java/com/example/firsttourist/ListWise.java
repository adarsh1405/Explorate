package com.example.firsttourist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
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

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ListWise extends AppCompatActivity {
    ListView lv;
    int ltype;
    String mTitle[];
    String mlocation[];
    String mDescription[];
    String images_url[];
    float mrating[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_wise);
        lv = findViewById(R.id.listview1);
        Intent i1=getIntent();
        ltype=i1.getIntExtra("Type",1);
        String json;
        ArrayList<String> url = new ArrayList<String>();
        ArrayList<String> name = new ArrayList<String>();
        ArrayList<String> location =new ArrayList<String>();
        ArrayList<String> description = new ArrayList<String>();
        ArrayList<String> rating=new ArrayList<String>();
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
                        rating.add(obj.getString("rating"));
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
            mrating = new float[len];

            for(int i=0;i<name.size();i++)
            {
                mTitle[i] = name.get(i);
                mlocation[i]=location.get(i);
                mDescription[i]=description.get(i);
                mrating[i]  = Float.parseFloat(rating.get(i));
                images_url[i]=url.get(i);
            }

            MyAdapter adapter = new MyAdapter(this, mTitle,mlocation,images_url, mrating);
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
                    intent.putExtra("rating",Float.toString(mrating[position]));
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
                        rating.add(obj.getString("rating"));
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
            mrating = new float[len];

            for(int i=0;i<name.size();i++)
            {
                mTitle[i] = name.get(i);
                mlocation[i]=location.get(i);
                mDescription[i]=description.get(i);
                mrating[i]  = Float.parseFloat(rating.get(i));
                images_url[i]=url.get(i);
            }

            MyAdapter adapter = new MyAdapter(this, mTitle,mlocation,images_url, mrating);
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
                    intent.putExtra("rating",Float.toString(mrating[position]));
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
                        rating.add(obj.getString("rating"));
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
            mrating = new float[len];

            for(int i=0;i<name.size();i++)
            {
                mTitle[i] = name.get(i);
                mlocation[i]=location.get(i);
                mDescription[i]=description.get(i);
                mrating[i]  = Float.parseFloat(rating.get(i));
                images_url[i]=url.get(i);
            }

            MyAdapter adapter = new MyAdapter(this, mTitle,mlocation,images_url, mrating);
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
                    intent.putExtra("rating",Float.toString(mrating[position]));
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
                        rating.add(obj.getString("rating"));
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
            mrating = new float[len];

            for(int i=0;i<name.size();i++)
            {
                mTitle[i] = name.get(i);
                mlocation[i]=location.get(i);
                mDescription[i]=description.get(i);
                mrating[i]  = Float.parseFloat(rating.get(i));
                images_url[i]=url.get(i);
            }

            MyAdapter adapter = new MyAdapter(this, mTitle,mlocation,images_url, mrating);
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
                    intent.putExtra("rating",Float.toString(mrating[position]));
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
                        rating.add(obj.getString("rating"));
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
            mrating = new float[len];

            for(int i=0;i<name.size();i++)
            {
                mTitle[i] = name.get(i);
                mlocation[i]=location.get(i);
                mDescription[i]=description.get(i);
                mrating[i]  = Float.parseFloat(rating.get(i));
                images_url[i]=url.get(i);
            }

            MyAdapter adapter = new MyAdapter(this, mTitle,mlocation,images_url, mrating);
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
                    intent.putExtra("Rating",Float.toString(mrating[position]));
                    startActivity(intent);


                }
            });
        }
        else
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
                        rating.add(obj.getString("rating"));
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
            mrating = new float[len];

            for(int i=0;i<name.size();i++)
            {
                mTitle[i] = name.get(i);
                mlocation[i]=location.get(i);
                mDescription[i]=description.get(i);

                mrating[i]  = Float.parseFloat(rating.get(i));
                images_url[i]=url.get(i);
            }

            MyAdapter adapter = new MyAdapter(this, mTitle,mlocation,images_url, mrating);
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
                    intent.putExtra("rating",Float.toString(mrating[position]));
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
        MyAdapter(Context c, String title[], String location[], String imgs[], float r[]){
            super(c, R.layout.row, R.id.textview1, title);
            this.context = c;
            this.rTitle = title;
            this.rLocation=location;
            this.rImgs = imgs;
            this.ra=r;
        }
        @NonNull
        @Override
        public View getView(int position, @NonNull View convertView, @NonNull ViewGroup parent){
            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.row, parent, false);
            ImageView images = row.findViewById(R.id.image);
            TextView myTitle = row.findViewById(R.id.textview1);
            TextView myDescription = row.findViewById(R.id.textview2);
            RatingBar rb=row.findViewById(R.id.ratingBar);
            Glide.with(row)
                    .load(rImgs[position])
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(images);
            myTitle.setText(rTitle[position]);
            myDescription.setText(rLocation[position]);
            rb.setRating(ra[position]);
            return row;
        }
    }
}