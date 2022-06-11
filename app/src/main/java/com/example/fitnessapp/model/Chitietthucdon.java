package com.example.fitnessapp.model;

public class Chitietthucdon {
    String id;
    String hinhanhthucdon;
    String calo;
    String khoiluong;
    String tenchitietthucdon;

    public Chitietthucdon(String id, String hinhanhthucdon, String calo, String khoiluong, String tenchitietthucdon) {
        this.id = id;
        this.hinhanhthucdon = hinhanhthucdon;
        this.calo = calo;
        this.khoiluong = khoiluong;
        this.tenchitietthucdon = tenchitietthucdon;
    }

    public Chitietthucdon() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHinhanhthucdon() {
        return hinhanhthucdon;
    }

    public void setHinhanhthucdon(String hinhanhthucdon) {
        this.hinhanhthucdon = hinhanhthucdon;
    }

    public String getCalo() {
        return calo;
    }

    public void setCalo(String calo) {
        this.calo = calo;
    }

    public String getKhoiluong() {
        return khoiluong;
    }

    public void setKhoiluong(String khoiluong) {
        this.khoiluong = khoiluong;
    }

    public String getTenchitietthucdon() {
        return tenchitietthucdon;
    }

    public void setTenchitietthucdon(String tenchitietthucdon) {
        this.tenchitietthucdon = tenchitietthucdon;
    }
}
