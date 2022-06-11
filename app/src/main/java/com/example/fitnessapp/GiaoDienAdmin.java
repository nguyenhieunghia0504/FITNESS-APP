package com.example.fitnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.fitnessapp.Admin.QuanLyChuongTrinhTap;
import com.example.fitnessapp.Admin.QuanLyThucDon;

public class GiaoDienAdmin extends AppCompatActivity {

    Button btn_chuongtrinhtap,btn_thucdon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giao_dien_admin);
        btn_chuongtrinhtap = (Button) findViewById(R.id.btn_quanly_bt);
        btn_thucdon = (Button) findViewById(R.id.btn_quanly_dinhduong);

        btn_chuongtrinhtap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GiaoDienAdmin.this, QuanLyChuongTrinhTap.class);
                startActivity(intent);
            }
        });
        btn_thucdon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GiaoDienAdmin.this, QuanLyThucDon.class);
                startActivity(intent);
            }
        });
    }
}
