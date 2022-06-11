package com.example.fitnessapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.fitnessapp.adpter.ChiTietCacBaiTapAdapter;
import com.example.fitnessapp.model.ChiTietBaiTap;
import com.example.fitnessapp.model.ChuongTrinh;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChiTietCacBaiTap extends AppCompatActivity {
    ArrayList<ChiTietBaiTap> arrayList;
    ChiTietCacBaiTapAdapter adapter;
    LinearLayoutManager layoutManager;
    RecyclerView recyclerView;
    ChuongTrinh chuongTrinh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_cac_bai_tap);

        recyclerView = (RecyclerView) findViewById(R.id.rcl_chitietbaitap);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        arrayList = new ArrayList<>();

        Intent intent = getIntent();
        chuongTrinh = (ChuongTrinh) intent.getSerializableExtra("DANHMUC");
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
                adapter = new ChiTietCacBaiTapAdapter(arrayList,getApplicationContext());
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
