package com.example.fitnessapp.Admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.fitnessapp.R;
import com.example.fitnessapp.model.ChuongTrinh;
import com.example.fitnessapp.model.ThucDon;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class ThemChiTietThucDon extends AppCompatActivity {
    EditText edt_tenctthucdon,edt_khoiluong,edt_calo;
    ImageView img_add;
    Button trove,dongy;
    ThucDon thucDon;
    StorageReference storage;
    int GalleryPick = 1;
    Uri ImageUri;
    String tenctthucdon,khoiluong,calo,dowloadimageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_chi_tiet_thuc_don);

        Intent intent = getIntent();
        thucDon = (ThucDon) intent.getSerializableExtra("THEMCHITIETTHUCDON");
        edt_tenctthucdon = (EditText) findViewById(R.id.edt_add_tenchitietthucdon);
        edt_khoiluong = (EditText) findViewById(R.id.edt_add_khoiluong);
        edt_calo = (EditText) findViewById(R.id.edt_add_calo);

        img_add = (ImageView) findViewById(R.id.img_add_chitietthucdon);
        trove = (Button) findViewById(R.id.btn_trove_addctthucdon);
        dongy = (Button) findViewById(R.id.btn_dongy_addctthucdon);

        storage = FirebaseStorage.getInstance().getReference().child("HinhAnhChiTietThucDon");
        trove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        img_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenGalley();
            }
        });
        dongy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tenctthucdon = edt_tenctthucdon.getText().toString();
                khoiluong = edt_khoiluong.getText().toString();
                calo = edt_calo.getText().toString();
                if (ImageUri == null){
                    Toast.makeText(ThemChiTietThucDon.this, "Hình ảnh bắt buộc", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(tenctthucdon)){
                    Toast.makeText(ThemChiTietThucDon.this, "Vui lòng nhập tên món", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(khoiluong)){
                    Toast.makeText(ThemChiTietThucDon.this, "Vui lòng nhập số khối lượng", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(calo)){
                    Toast.makeText(ThemChiTietThucDon.this, "Vui lòng nhập số calo", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    themanhctthucdon();
                }
            }
        });
    }

    private void OpenGalley() {
        Intent galleryintent = new Intent();
        galleryintent.setAction(Intent.ACTION_GET_CONTENT);
        galleryintent.setType("image/*");
        startActivityForResult(galleryintent,GalleryPick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GalleryPick && resultCode == RESULT_OK && data!=null){
            ImageUri =data.getData();
            img_add.setImageURI(ImageUri);
        }
    }

    private void themanhctthucdon() {

        StorageReference firepath = storage.child(ImageUri.getLastPathSegment()+".jpg");
        UploadTask uploadTask = firepath.putFile(ImageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ThemChiTietThucDon.this, "Có lỗi", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isSuccessful());
                Uri dowloadUri = uriTask.getResult();
                if (uriTask.isSuccessful()){
                    dowloadimageUri = dowloadUri.toString();
                }
                Toast.makeText(ThemChiTietThucDon.this, "Hình ảnh được upload thành công", Toast.LENGTH_SHORT).show();
            }
        }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                Toast.makeText(ThemChiTietThucDon.this, "Thành công", Toast.LENGTH_SHORT).show();
                themctthucdon();
            }
        });
    }

    private void themctthucdon() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("ChiTietThucDon");
        HashMap<String,Object> ctthucdon = new HashMap<>();
        ctthucdon.put("tenchitietthucdon",tenctthucdon);
        ctthucdon.put("khoiluong",khoiluong);
        ctthucdon.put("calo",calo);
        ctthucdon.put("hinhanhthucdon",dowloadimageUri);

        String id = ref.push().getKey();

        String idchuongtrinh = thucDon.getId();

        ref.child(idchuongtrinh).child(id).updateChildren(ctthucdon).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(ThemChiTietThucDon.this, "Thêm bài tập thành công", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }


}
