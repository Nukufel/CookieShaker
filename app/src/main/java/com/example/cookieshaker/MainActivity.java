package com.example.cookieshaker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity{

    private SensorManager sensorManager;
    private float acceleration;
    private float currentAcceleration;
    private float lastAcceleration;

    private TextView cookieCTRLabel;
    private static long cookieCTR;

    private TextView cpsLabel;
    private static int cps;
    private SensorEventListener sensorListener;

    private SharedPreferences sharedPref;

    private  Thread thread;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPref = getPreferences(Context.MODE_PRIVATE);

        updateForCPS();

        sensorListener = new SensorEventListener() {
            SharedPreferences.Editor editor = sharedPref.edit();
            @Override
            public void onSensorChanged(SensorEvent event) {
                cookiesForShake(event, editor);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {}
        };

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        sensorManager.registerListener(sensorListener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);

        acceleration = 0;
        currentAcceleration = SensorManager.GRAVITY_EARTH;
        lastAcceleration = SensorManager.GRAVITY_EARTH;


        Button shopB = findViewById(R.id.ShopButton);

        shopB.setOnClickListener(e -> {
            Intent intent = new Intent(MainActivity.this, ShopActivity.class);
            startActivity(intent);
        });

        cookieCTR = sharedPref.getLong("cookieCTR", 0);
        cookieCTRLabel = findViewById(R.id.CookieCounterMain);
        cookieCTRLabel.setText(String.valueOf(cookieCTR));

        cps = sharedPref.getInt("cps", 10);
        cpsLabel = findViewById(R.id.cpsMain);
        cpsLabel.setText(String.valueOf(cps));
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(sensorListener,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        sensorManager.unregisterListener(sensorListener);
        super.onPause();
    }

    private void cookiesForShake(SensorEvent event, SharedPreferences.Editor editor){
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];
        lastAcceleration = currentAcceleration;

        currentAcceleration = (float) Math.sqrt(x * x + y * y + z * z);
        float delta = currentAcceleration - lastAcceleration;
        acceleration = acceleration * 0.9f + delta;

        if (acceleration > 1) {
            cookieCTR++;
            cookieCTRLabel.setText(String.valueOf(cookieCTR));
            editor.putLong("cookieCTR", cookieCTR);
            editor.apply();
        }
    }

    private void updateForCPS(){
        thread = new Thread() {
            @Override
            public void run() {
                try {
                    while (!thread.isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                cookieCTR = cookieCTR+cps;
                                cookieCTRLabel.setText(String.valueOf(cookieCTR));
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };

        thread.start();
    }

}