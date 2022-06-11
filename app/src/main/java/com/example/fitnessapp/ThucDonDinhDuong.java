package com.example.fitnessapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessapp.adpter.thucdonadapter;
import android.os.Bundle;
import android.widget.Toast;

import com.example.fitnessapp.model.ThucDon;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ThucDonDinhDuong extends AppCompatActivity {
    RecyclerView recyclerview;
    LinearLayoutManager layoutManager;
    thucdonadapter adapter;
    ArrayList<ThucDon> arrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thuc_don_dinh_duong);
        recyclerview = (RecyclerView) findViewById(R.id.rcl_thucdon);
        layoutManager = new LinearLayoutManager(this);
        recyclerview .setLayoutManager(layoutManager);

        arrayList = new ArrayList<>();
//
//        public void onDataChange(DataSnapshot dataSnapshot) {
//            arrayList.clear();
//            for(DataSnapshot data : dataSnapshot.getChildren()){
//                ChuongTrinh chuongTrinh = data.getValue(ChuongTrinh.class);
//
//                chuongTrinh.setId(data.getKey());
//
//                arrayList.add(chuongTrinh);
//            }
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Thucdon");
        myRef.addValueEventListener(new ValueEventListener() {

            public void onDataChange(DataSnapshot dataSnapshot) {
                arrayList.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    ThucDon thucDon = data.getValue(ThucDon.class);

                    thucDon.setId(data.getKey());
                    arrayList.add(thucDon);

                }
//                adpter = new ChuongTrinhAdpter(arrayList,getApplicationContext());
//                recyclerView.setAdapter(adpter);
                adapter = new thucdonadapter(arrayList,getApplicationContext());
                recyclerview.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ThucDonDinhDuong.this, ""+databaseError, Toast.LENGTH_SHORT).show();
            }
        });
    }
}