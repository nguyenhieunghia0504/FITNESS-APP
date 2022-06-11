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

public class ThemChuongTrinhTap extends AppCompatActivity {
    EditText edt_tenchuongtrinh,edt_sotuan,edt_sophut;
    ImageView img_add;
    Button trove,dongy;
    StorageReference storage;
    int GalleryPick = 1;
    Uri ImageUri;
    String tenchuongtrinh,sotuan,sophut,dowloadimageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_chuong_trinh_tap);

        edt_tenchuongtrinh = (EditText) findViewById(R.id.edt_add_tenchuongtrinh);
        edt_sotuan = (EditText) findViewById(R.id.edt_add_sotuan);
        edt_sophut = (EditText) findViewById(R.id.edt_add_sophut);

        img_add = (ImageView) findViewById(R.id.img_add_chuongtrinh);
        trove = (Button) findViewById(R.id.btn_trove_addchuongtrinh);
        dongy = (Button) findViewById(R.id.btn_dongy_addchuongtrinh);

        storage = FirebaseStorage.getInstance().getReference().child("HinhAnhChuongTrinh");
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
                tenchuongtrinh = edt_tenchuongtrinh.getText().toString();
                sotuan = edt_sotuan.getText().toString();
                sophut = edt_sophut.getText().toString();
                if (ImageUri == null){
                    Toast.makeText(ThemChuongTrinhTap.this, "Hình ảnh bắt buộc", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(tenchuongtrinh)){
                    Toast.makeText(ThemChuongTrinhTap.this, "Vui lòng nhập tên chương trình", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(sotuan)){
                    Toast.makeText(ThemChuongTrinhTap.this, "Vui lòng nhập số tuần tập", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(sophut)){
                    Toast.makeText(ThemChuongTrinhTap.this, "Vui lòng nhập số phút tập", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    themanhchuongtrinh();
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

    private void themanhchuongtrinh() {
        StorageReference firepath = storage.child(ImageUri.getLastPathSegment()+".jpg");
        UploadTask uploadTask = firepath.putFile(ImageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ThemChuongTrinhTap.this, "Có lỗi", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(ThemChuongTrinhTap.this, "Hình ảnh được upload thành công", Toast.LENGTH_SHORT).show();
            }
        }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                Toast.makeText(ThemChuongTrinhTap.this, "Thành công", Toast.LENGTH_SHORT).show();
                themchuongtrinh();
            }
        });
    }

    private void themchuongtrinh() {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("ChuongTrinh");
        HashMap<String,Object> chuongtrinh = new HashMap<>();
        chuongtrinh.put("tenchuongtrinh",tenchuongtrinh);
        chuongtrinh.put("sotuan",sotuan);
        chuongtrinh.put("sophut",sophut);
        chuongtrinh.put("anh",dowloadimageUri);

        String id = ref.push().getKey();

        ref.child(id).updateChildren(chuongtrinh).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(ThemChuongTrinhTap.this, "Thêm chương trình thành công", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }
}
