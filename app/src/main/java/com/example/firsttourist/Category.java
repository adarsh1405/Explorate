package com.example.firsttourist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

public class Category extends AppCompatActivity {
    Animation bottomAnim;

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
    }
    public void open_falls(View view){
        Intent i1 = new Intent(this, ListWise.class);
        i1.putExtra("Type", 1);
        startActivity(i1);
    }
    public void open_temples(View view){
        Intent i1 = new Intent(this, ListWise.class);
        i1.putExtra("Type", 2);
        startActivity(i1);
    }
    public void open_wildlife(View view){
        Intent i1 = new Intent(this, ListWise.class);
        i1.putExtra("Type", 3);
        startActivity(i1);
    }
    public void open_beaches(View view){
        Intent i1 = new Intent(this, ListWise.class);
        i1.putExtra("Type", 4);
        startActivity(i1);
    }
    public void open_museums(View view){
        Intent i1 = new Intent(this, ListWise.class);
        i1.putExtra("Type", 5);
        startActivity(i1);
    }
    public void open_hills(View view){
        Intent i1 = new Intent(this, ListWise.class);
        i1.putExtra("Type", 6);
        startActivity(i1);
    }


}