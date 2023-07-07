package com.example.cookieshaker;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class MyService extends Service {


    private final IBinder binder = new LocalBinder();


    public class LocalBinder extends Binder {
        Service getService() {
            // Return this instance of LocalService so clients can call public methods.
            return MyService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
       return binder;
    }

    public int calcCPS(Item sunglasses, Item cookieee, Item slippers){
        return (sunglasses.getCps()*sunglasses.getAmount())+(cookieee.getCps()*cookieee.getAmount())+(slippers.getCps()*slippers.getAmount());
    }

    public long buy(Item item, long cookieCTR){
        return cookieCTR-item.getPrice();
    }

    public int amountUp(Item item){
        return item.getAmount()+1;
    }
}