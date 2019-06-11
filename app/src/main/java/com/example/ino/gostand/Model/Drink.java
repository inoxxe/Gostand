package com.example.ino.gostand.Model;

public class Drink {

    private String name,id,price,stand,gambar,count;

    public Drink() {
    }

    public Drink(String name, String id, String price, String stand, String gambar, String count) {
        this.name = name;
        this.id = id;
        this.price = price;
        this.stand = stand;
        this.gambar = gambar;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStand() {
        return stand;
    }

    public void setStand(String stand) {
        this.stand = stand;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
