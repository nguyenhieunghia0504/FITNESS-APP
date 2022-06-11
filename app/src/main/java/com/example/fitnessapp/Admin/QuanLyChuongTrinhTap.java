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
import com.example.fitnessapp.adpter.ChuongTrinhAdpter;
import com.example.fitnessapp.adpter.QuanLyChuongTrinhTapAdapter;
import com.example.fitnessapp.model.ChuongTrinh;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class QuanLyChuongTrinhTap extends AppCompatActivity {
    RecyclerView recyclerView ;
    LinearLayoutManager layoutManager;
    QuanLyChuongTrinhTapAdapter adpter;
    ArrayList<ChuongTrinh> arrayList;
    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_chuong_trinh_tap);

        recyclerView = (RecyclerView) findViewById(R.id.rcl_quanly_chuongtrinhtap);

        fab = (FloatingActionButton) findViewById(R.id.fab);
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
                adpter = new QuanLyChuongTrinhTapAdapter(arrayList,getApplicationContext());
                recyclerView.setAdapter(adpter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(QuanLyChuongTrinhTap.this, ""+databaseError, Toast.LENGTH_SHORT).show();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuanLyChuongTrinhTap.this,ThemChuongTrinhTap.class);
                startActivity(intent);
            }
        });
    }
}
