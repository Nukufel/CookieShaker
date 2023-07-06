package com.example.cookieshaker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ShopActivity extends AppCompatActivity {

    private Item suglasses;
    private Item cookieee;
    private Item slipper;
    private Item controler;

    private SharedPreferences sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        sharedPref  = getPreferences(Context.MODE_PRIVATE);

        suglasses = new Item(1,10, sharedPref.getInt("sunglasses",0));
        cookieee = new Item(10, 100, sharedPref.getInt("cookieee",0));
        slipper = new Item(100, 1000, sharedPref.getInt("slipper",0));
        controler = new Item(1, 10, sharedPref.getInt("controler",0));

        Button backB = findViewById(R.id.BackButton);
        Button buySunglasses = findViewById(R.id.SunglassButton);
        Button buyCookie = findViewById(R.id.CookieButton);
        Button buySlipper = findViewById(R.id.SlipersButton);
        Button buyControler = findViewById(R.id.ControlerButton);

        backB.setOnClickListener(e -> {
            Intent intent = new Intent(ShopActivity.this, MainActivity.class);
            startActivity(intent);
        });

        buySunglasses.setOnClickListener(e ->{

        });

        buyCookie.setOnClickListener(e ->{

        });

        buySlipper.setOnClickListener(e ->{

        });

        buyControler.setOnClickListener(e ->{

        });
    }










}