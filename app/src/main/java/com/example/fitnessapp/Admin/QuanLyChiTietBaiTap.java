package com.example.fitnessapp.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.fitnessapp.R;
import com.example.fitnessapp.adpter.ChiTietCacBaiTapAdapter;
import com.example.fitnessapp.adpter.QuanLyCacBaiTapAdapter;
import com.example.fitnessapp.model.ChiTietBaiTap;
import com.example.fitnessapp.model.ChuongTrinh;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class QuanLyChiTietBaiTap extends AppCompatActivity {

    ArrayList<ChiTietBaiTap> arrayList;
    QuanLyCacBaiTapAdapter adapter;
    LinearLayoutManager layoutManager;
    RecyclerView recyclerView;
    ChuongTrinh chuongTrinh;
    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_chi_tiet_bai_tap);

        recyclerView = (RecyclerView) findViewById(R.id.rcl_quanly_chitietbaitap);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        fab = (FloatingActionButton) findViewById(R.id.fab_quanly_chitietbaitap);

        arrayList = new ArrayList<>();
        Intent intent = getIntent();

        chuongTrinh = (ChuongTrinh) intent.getSerializableExtra("QUANLYDANHMUC");
        String iddanhmuc = chuongTrinh.getId();

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("CacBaiTap").child(iddanhmuc);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        ChiTietBaiTap chiTietBaiTap = data.getValue(ChiTietBaiTap.class);

                        chiTietBaiTap.setId(data.getKey());

                        arrayList.add(chiTietBaiTap);
                    }
                }
                adapter = new QuanLyCacBaiTapAdapter(arrayList,getApplicationContext());
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(QuanLyChiTietBaiTap.this,ThemChiTietBaiTap.class);
                intent1.putExtra("THEMBAITAP",chuongTrinh);
                startActivity(intent1);
            }
        });
    }
}
