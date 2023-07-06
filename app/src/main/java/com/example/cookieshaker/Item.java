package com.example.cookieshaker;

public class Item {
    private int cps;
    private int price;

    private int amount;

    public int getAmount() {
        return amount;
    }

    public Item(int cps, int price, int amount) {
        this.cps = cps;
        this.price = price;
        this.amount = amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
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
