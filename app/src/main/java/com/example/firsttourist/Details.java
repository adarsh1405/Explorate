package com.example.firsttourist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import java.util.Objects;

public class Details extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        TextView t2 = (TextView)findViewById(R.id.text1);
        ImageView im = findViewById(R.id.image);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

            Intent intent = getIntent();
            if(intent!=null)
            {
                String name = intent.getStringExtra("Name");
                String description = intent.getStringExtra("Description");
                String image_url = intent.getStringExtra("Image");
                t2.setText(description);
                Glide.with(this)
                        .load(image_url)
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .into(im);


                im.setContentDescription(name);
                this.setTitle(name);

            }

    }
}