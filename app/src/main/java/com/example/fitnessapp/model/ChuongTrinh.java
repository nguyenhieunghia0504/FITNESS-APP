package com.example.fitnessapp.model;

import java.io.Serializable;

public class ChuongTrinh implements Serializable {
    String id;
    String anh;
    String tenchuongtrinh;
    String sotuan;
    String sophut;

    public ChuongTrinh(String id, String anh, String tenchuongtrinh, String sotuan, String sophut) {
        this.id = id;
        this.anh = anh;
        this.tenchuongtrinh = tenchuongtrinh;
        this.sotuan = sotuan;
        this.sophut = sophut;
    }

    public ChuongTrinh() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public String getTenchuongtrinh() {
        return tenchuongtrinh;
    }

    public void setTenchuongtrinh(String tenchuongtrinh) {
        this.tenchuongtrinh = tenchuongtrinh;
    }

    public String getSotuan() {
        return sotuan;
    }

    public void setSotuan(String sotuan) {
        this.sotuan = sotuan;
    }

    public String getSophut() {
        return sophut;
    }

    public void setSophut(String sophut) {
        this.sophut = sophut;
    }
}
