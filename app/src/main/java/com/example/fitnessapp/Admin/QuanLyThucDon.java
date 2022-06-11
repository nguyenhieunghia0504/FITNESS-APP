package com.example.fitnessapp.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.fitnessapp.R;
import com.example.fitnessapp.ThucDonDinhDuong;
import com.example.fitnessapp.adpter.QuanLyThucDonAdapter;
import com.example.fitnessapp.adpter.thucdonadapter;
import com.example.fitnessapp.model.ThucDon;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class QuanLyThucDon extends AppCompatActivity {
    RecyclerView recyclerview;
    LinearLayoutManager layoutManager;
    QuanLyThucDonAdapter adapter;
    ArrayList<ThucDon> arrayList;
    FloatingActionButton fab;
    ThucDon thucDon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_thuc_don);
        recyclerview = (RecyclerView) findViewById(R.id.rcl_quanly_thucdon);
        layoutManager = new LinearLayoutManager(this);
        recyclerview .setLayoutManager(layoutManager);

        fab = (FloatingActionButton) findViewById(R.id.fab_quanly_thucdon);
        arrayList = new ArrayList<>();
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Thucdon");
        myRef.addValueEventListener(new ValueEventListener() {

            public void onDataChange(DataSnapshot dataSnapshot) {
                arrayList.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    thucDon = data.getValue(ThucDon.class);

                    thucDon.setId(data.getKey());
                    arrayList.add(thucDon);

                }
//                adpter = new ChuongTrinhAdpter(arrayList,getApplicationContext());
//                recyclerView.setAdapter(adpter);
                adapter = new QuanLyThucDonAdapter(arrayList,getApplicationContext());
                recyclerview.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(QuanLyThucDon.this, ""+databaseError, Toast.LENGTH_SHORT).show();
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(QuanLyThucDon.this,ThemThucDon.class);
                startActivity(intent);
            }
        });
    }
}
