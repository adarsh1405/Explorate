package com.example.firsttourist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static int splash_time=3000;
    //Variables
    Animation topAnim, bottomAnim;
    TextView t1, t2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.buttom_animation);
        t1 = findViewById(R.id.text1);
        t2 = findViewById(R.id.text2);
        t1.setAnimation(topAnim);
        t2.setAnimation(bottomAnim);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i1= new Intent(MainActivity.this,Category.class);
                startActivity(i1);
                finish();
            }
        },splash_time);
    }
}