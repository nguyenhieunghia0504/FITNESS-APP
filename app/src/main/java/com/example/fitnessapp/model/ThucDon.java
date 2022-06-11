package com.example.fitnessapp.model;

import java.io.Serializable;

public class ThucDon implements Serializable {
    String id;
    String tenthucdon;
    String imgthucdon;
    String calo;

    public ThucDon(String id, String tenthucdon, String imgthucdon, String calo) {
        this.id = id;
        this.tenthucdon = tenthucdon;
        this.imgthucdon = imgthucdon;
        this.calo = calo;
    }
    public ThucDon() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTenthucdon() {
        return tenthucdon;
    }

    public void setTenthucdon(String tenthucdon) {
        this.tenthucdon = tenthucdon;
    }

    public String getImgthucdon() {
        return imgthucdon;
    }

    public void setImgthucdon(String imgthucdon) {
        this.imgthucdon = imgthucdon;
    }

    public String getCalo() {
        return calo;
    }

    public void setCalo(String calo) {
        this.calo = calo;
    }




}
