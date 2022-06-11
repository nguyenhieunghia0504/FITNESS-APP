package com.example.fitnessapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessapp.adpter.ChiTietThucDonAdapter;
import com.example.fitnessapp.model.Chitietthucdon;
import com.example.fitnessapp.model.ThucDon;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class XemChiTietThucDon extends AppCompatActivity {
    ArrayList<Chitietthucdon> arrayList;
    ChiTietThucDonAdapter adapter;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    ThucDon thucDon;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chitietthucdon);

        recyclerView = (RecyclerView) findViewById(R.id.rcl_chitietthucdon);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        arrayList = new ArrayList<>();
        Intent intent = getIntent();
        thucDon = (ThucDon) intent.getSerializableExtra("THUCDON");

        String idthucdon = thucDon.getId();
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("ChiTietThucDon").child(idthucdon);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        Chitietthucdon chitietthucdon = data.getValue(Chitietthucdon.class);

                        chitietthucdon.setId(data.getKey());

                        arrayList.add(chitietthucdon);
                    }

                    adapter = new ChiTietThucDonAdapter(arrayList, getApplicationContext());
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
