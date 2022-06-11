package com.example.fitnessapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DangNhapAdmin extends AppCompatActivity {

    Button trove,dangnhap;
    EditText edt_tendn,edt_mk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap_admin);

        dangnhap = (Button) findViewById(R.id.btn_dangnhap_admin);
        trove = (Button) findViewById(R.id.trove_dnadmin);

        edt_tendn = (EditText) findViewById(R.id.tendn_admin);
        edt_mk = (EditText) findViewById(R.id.mkdn_admin);

        trove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        dangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Admin");
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(TextUtils.isEmpty(edt_tendn.getText().toString())){
                            Toast.makeText(DangNhapAdmin.this, "Vui lòng điền tên đăng nhập", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if(TextUtils.isEmpty(edt_mk.getText().toString())){
                            Toast.makeText(DangNhapAdmin.this, "Vui lòng điền mật khẩu", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (dataSnapshot.child("id1").exists()) {
                            if (dataSnapshot.child("id1").child("ten").getValue().equals(edt_tendn.getText().toString())) {
                                if (dataSnapshot.child("id1").child("matkhau").getValue().equals(edt_mk.getText().toString())) {
                                    Intent intent = new Intent(DangNhapAdmin.this,GiaoDienAdmin.class);
                                    startActivity(intent);
                                    Toast.makeText(DangNhapAdmin.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(DangNhapAdmin.this, "Thất bại", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

        });
    }
}
