package com.example.cookieshaker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Button;

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

    MyService s;
    boolean b = false;

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(connection);
        b = false;
    }
    @Override
    protected void onStart() {
        super.onStart();
        // Bind to LocalService.
        Intent intent = new Intent(this, MyService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    /** Defines callbacks for service binding, passed to bindService(). */
    private ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance.
            MyService.LocalBinder binder = (MyService.LocalBinder) service;
            s = (MyService) binder.getService();
            b = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            b = false;
        }
    };






}