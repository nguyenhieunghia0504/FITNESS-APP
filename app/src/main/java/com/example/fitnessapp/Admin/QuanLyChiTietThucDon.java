package com.example.fitnessapp.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.fitnessapp.R;
import com.example.fitnessapp.adpter.ChiTietThucDonAdapter;
import com.example.fitnessapp.adpter.QuanLyChiTietThucDonAdapter;
import com.example.fitnessapp.model.Chitietthucdon;
import com.example.fitnessapp.model.ThucDon;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class QuanLyChiTietThucDon extends AppCompatActivity {

    ArrayList<Chitietthucdon> arrayList;
    QuanLyChiTietThucDonAdapter adapter;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    ThucDon thucDon;
    FloatingActionButton fab;
    Chitietthucdon chitietthucdon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_chi_tiet_thuc_don);

        recyclerView = (RecyclerView) findViewById(R.id.rcl_quanlychitietthucdon);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        fab = (FloatingActionButton) findViewById(R.id.fab_quanly_chitietthucdon);
        arrayList = new ArrayList<>();
        Intent intent = getIntent();
        thucDon = (ThucDon) intent.getSerializableExtra("QUANLYTHUCDON");

        String idthucdon = thucDon.getId();
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("ChiTietThucDon").child(idthucdon);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        chitietthucdon = data.getValue(Chitietthucdon.class);

                        chitietthucdon.setId(data.getKey());

                        arrayList.add(chitietthucdon);
                    }

                    adapter = new QuanLyChiTietThucDonAdapter(arrayList, getApplicationContext());
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent1 = new Intent(QuanLyChiTietThucDon.this,ThemChiTietThucDon.class);
                intent1.putExtra("THEMCHITIETTHUCDON",thucDon);
                startActivity(intent1);
            }
        });
    }
}
