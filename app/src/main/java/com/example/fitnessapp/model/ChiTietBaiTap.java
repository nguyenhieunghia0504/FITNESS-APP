package com.example.fitnessapp.model;

public class ChiTietBaiTap {
    String id;
    String hinhanh;
    String tenbaitap;
    String soday;
    String solan;

    public ChiTietBaiTap(String id, String hinhanh, String tenbaitap, String soday, String solan) {
        this.id = id;
        this.hinhanh = hinhanh;
        this.tenbaitap = tenbaitap;
        this.soday = soday;
        this.solan = solan;
    }

    public ChiTietBaiTap() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public String getTenbaitap() {
        return tenbaitap;
    }

    public void setTenbaitap(String tenbaitap) {
        this.tenbaitap = tenbaitap;
    }

    public String getSoday() {
        return soday;
    }

    public void setSoday(String soday) {
        this.soday = soday;
    }

    public String getSolan() {
        return solan;
    }

    public void setSolan(String solan) {
        this.solan = solan;
    }
}
