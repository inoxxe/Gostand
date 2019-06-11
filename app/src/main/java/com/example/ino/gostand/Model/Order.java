package com.example.ino.gostand.Model;

import java.util.Date;

public class Order {

    private String id,name,status,date,time,itemname,itemcount,totalprice,price;
    public Order() {
    }

    public Order(String id, String name, String status, String date, String time, String itemname, String itemcount, String totalprice, String price) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.date = date;
        this.time = time;
        this.itemname = itemname;
        this.itemcount = itemcount;
        this.totalprice = totalprice;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public String getItemcount() {
        return itemcount;
    }

    public void setItemcount(String itemcount) {
        this.itemcount = itemcount;
    }

    public String getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(String totalprice) {
        this.totalprice = totalprice;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
