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

public class ThemChiTietBaiTap extends AppCompatActivity {
    EditText edt_tenbaitap,edt_soday,edt_solan;
    ImageView img_add;
    Button trove,dongy;
    ChuongTrinh chuongTrinh;
    StorageReference storage;
    int GalleryPick = 1;
    Uri ImageUri;
    String tenbaitap,soday,solan,dowloadimageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_chi_tiet_bai_tap);

        Intent intent = getIntent();
        chuongTrinh = (ChuongTrinh) intent.getSerializableExtra("THEMBAITAP");
        edt_tenbaitap = (EditText) findViewById(R.id.edt_add_baitap);
        edt_soday = (EditText) findViewById(R.id.edt_add_soday);
        edt_solan = (EditText) findViewById(R.id.edt_add_solan);


        img_add = (ImageView) findViewById(R.id.img_add_baitap);
        trove = (Button) findViewById(R.id.btn_trove_addbaitap);
        dongy = (Button) findViewById(R.id.btn_dongy_addbaitap);

        storage = FirebaseStorage.getInstance().getReference().child("HinhAnhBaiTap");
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
                tenbaitap = edt_tenbaitap.getText().toString();
                soday = edt_soday.getText().toString();
                solan = edt_solan.getText().toString();
                if (ImageUri == null){
                    Toast.makeText(ThemChiTietBaiTap.this, "Hình ảnh bắt buộc", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(tenbaitap)){
                    Toast.makeText(ThemChiTietBaiTap.this, "Vui lòng nhập tên bài tập", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(soday)){
                    Toast.makeText(ThemChiTietBaiTap.this, "Vui lòng nhập số đẩy tập", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(solan)){
                    Toast.makeText(ThemChiTietBaiTap.this, "Vui lòng nhập số lần tập", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    themanhbaitap();
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
    private void themanhbaitap() {
        StorageReference firepath = storage.child(ImageUri.getLastPathSegment()+".jpg");
        UploadTask uploadTask = firepath.putFile(ImageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ThemChiTietBaiTap.this, "Có lỗi", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(ThemChiTietBaiTap.this, "Hình ảnh được upload thành công", Toast.LENGTH_SHORT).show();
            }
        }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                Toast.makeText(ThemChiTietBaiTap.this, "Thành công", Toast.LENGTH_SHORT).show();
                thembaitap();
            }
        });
    }

    private void thembaitap() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("CacBaiTap");
        HashMap<String,Object> baitap = new HashMap<>();
        baitap.put("tenbaitap",tenbaitap);
        baitap.put("soday",soday);
        baitap.put("solan",solan);
        baitap.put("hinhanh",dowloadimageUri);

        String id = ref.push().getKey();

        String idchuongtrinh = chuongTrinh.getId();

        ref.child(idchuongtrinh).child(id).updateChildren(baitap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(ThemChiTietBaiTap.this, "Thêm bài tập thành công", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }


}
