package com.example.ino.gostand.Model;

public class Stand {
    private String name,number,id;
    private String gambar;

    public Stand() {

    }

    public Stand(String name, String number, String id, String gambar) {
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

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }
}
