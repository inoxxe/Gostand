package com.example.ino.gostand.Model;

public class Stand {
    private String name,number,id;
    private int gambar;

    public Stand(String name, String number, String id, int gambar) {
        this.name = name;
        this.number = number;
        this.id = id;
        this.gambar = gambar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getGambar() {
        return gambar;
    }

    public void setGambar(int gambar) {
        this.gambar = gambar;
    }
}
