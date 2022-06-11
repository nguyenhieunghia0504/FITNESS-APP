package com.example.fitnessapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.fitnessapp.adpter.ChuongTrinhAdpter;
import com.example.fitnessapp.model.ChuongTrinh;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CacBaiTap extends AppCompatActivity {
    RecyclerView recyclerView ;
    LinearLayoutManager layoutManager;
    ChuongTrinhAdpter adpter;
    ArrayList<ChuongTrinh> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cac_bai_tap);

        recyclerView = (RecyclerView) findViewById(R.id.rcl_danhmuc);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        arrayList = new ArrayList<>();

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("ChuongTrinh");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList.clear();
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    ChuongTrinh chuongTrinh = data.getValue(ChuongTrinh.class);

                    chuongTrinh.setId(data.getKey());


                    arrayList.add(chuongTrinh);
                }
                adpter = new ChuongTrinhAdpter(arrayList,getApplicationContext());
                recyclerView.setAdapter(adpter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(CacBaiTap.this, ""+databaseError, Toast.LENGTH_SHORT).show();
            }
        });

     }
}
