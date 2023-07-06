package com.example.cookieshaker;

public class Item {
    private int cps;
    private int price;


    public Item(int cps, int price) {
        this.cps = cps;
        this.price = price;
    }

    public int getCps() {
        return cps;
    }

    public void setCps(int cps) {
        this.cps = cps;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
