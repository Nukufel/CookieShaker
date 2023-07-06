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
import android.widget.TextView;

import java.util.List;

public class ShopActivity extends AppCompatActivity {

    private Item suglasses;
    private Item cookieee;
    private Item slipper;
    private Item controler;
    private static long cookieCTRS;
    private static int cpsS;
    private TextView cookieCTRShop;
    private TextView cpsShop;
    private SharedPreferences sharedPref;
    private Button backB;
    private Button buySunglasses;
    private Button buyCookie;
    private Button buySlipper;
    private Button buyControler;
    private Button resetB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        sharedPref  = getSharedPreferences("temp",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        Bundle mainIntent = getIntent().getExtras();


        cpsS = mainIntent.getInt("cps");
        cookieCTRS = mainIntent.getLong("cookieCTR");

        suglasses = new Item(1,sharedPref.getInt("sunglassP", 10), sharedPref.getInt("sunglasses",0));
        cookieee = new Item(10, sharedPref.getInt("cookieeeP", 100), sharedPref.getInt("cookieee",0));
        slipper = new Item(100, sharedPref.getInt("slipperP", 1000), sharedPref.getInt("slipper",0));
        controler = new Item(1, sharedPref.getInt("controllerP", 10), sharedPref.getInt("controller",0));


        backB = findViewById(R.id.BackButton);
        buySunglasses = findViewById(R.id.SunglassButton);
        buyCookie = findViewById(R.id.CookieButton);
        buySlipper = findViewById(R.id.SlipersButton);
        buyControler = findViewById(R.id.ControlerButton);
        cookieCTRShop = findViewById(R.id.CookieCounterShop);
        cpsShop = findViewById(R.id.cpsShop);
        cookieCTRShop.setText(String.valueOf((int) cookieCTRS));
        cpsShop.setText(String.valueOf((int) cpsS));

        resetB = findViewById(R.id.resetB);
        resetB.setOnClickListener(e -> {
            reset(editor);
            cookieCTRShop.setText("0");
            cpsShop.setText("0");
        });

        backB.setOnClickListener(e -> {
            finish();
        });

        buySunglasses.setOnClickListener(e ->{
            if (cookieCTRS>=suglasses.getPrice()) {
                cookieCTRS = s.buy(suglasses, cookieCTRS);
                editor.putLong("cookieCTR", cookieCTRS);
                afterBuy(suglasses, editor);
            }
        });

        buyCookie.setOnClickListener(e ->{
            if (cookieCTRS>=cookieee.getPrice()) {
                cookieCTRS = s.buy(cookieee, cookieCTRS);
                editor.putLong("cookieCTR", cookieCTRS);
                afterBuy(cookieee, editor);;
            }
        });

        buySlipper.setOnClickListener(e ->{
            if (cookieCTRS>=slipper.getPrice()) {
                cookieCTRS = s.buy(slipper, cookieCTRS);
                editor.putLong("cookieCTR", cookieCTRS);
                afterBuy(slipper, editor);
            }
        });

        buyControler.setOnClickListener(e ->{
            if (cookieCTRS>=controler.getPrice()) {
                cookieCTRS = s.buy(controler, cookieCTRS);
                editor.putLong("cookieCTR", cookieCTRS);
                afterBuy(controler, editor);
            }
        });
    }

    private void afterBuy(Item item, SharedPreferences.Editor editor){
        item.setAmount(s.amountUp(item));
        cpsS = s.calcCPS(suglasses,cookieee,slipper);
        cookieCTRShop.setText(String.valueOf((int) cookieCTRS));
        cpsShop.setText(String.valueOf((int) cpsS));
        save(editor);
    }

    private void save(SharedPreferences.Editor editor){
        editor.putInt("sunglasses", suglasses.getAmount());
        editor.putInt("controller", controler.getAmount());
        editor.putInt("slipper", slipper.getAmount());
        editor.putInt("cookieee", cookieee.getAmount());
        editor.putLong("cookieCTR", cookieCTRS);
        editor.putInt("cps", cpsS);
        editor.apply();
    }

    private void reset(SharedPreferences.Editor editor){
            editor.putInt("sunglasses", 0);
            editor.putInt("controller", 0);
            editor.putInt("slipper", 0);
            editor.putInt("cookieee", 0);
            editor.putLong("cookieCTR", 0);
            editor.putInt("cps", 0);
            editor.apply();
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
        public void onServiceConnected(ComponentName className, IBinder service) {
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