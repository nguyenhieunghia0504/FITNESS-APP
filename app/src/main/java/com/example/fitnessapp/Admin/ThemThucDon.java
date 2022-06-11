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

public class ThemThucDon extends AppCompatActivity {

    EditText edt_tenthucdon,edt_calo;
    ImageView img_add;
    Button trove,dongy;
    StorageReference storage;
    int GalleryPick = 1;
    Uri ImageUri;
    String tenthucdon,calo,dowloadimageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_thuc_don);

        edt_tenthucdon = (EditText) findViewById(R.id.edt_add_tenthucdon);
        edt_calo = (EditText) findViewById(R.id.edt_add_calo);

        img_add = (ImageView) findViewById(R.id.img_add_thucdon);
        trove = (Button) findViewById(R.id.btn_trove_addthucdon);
        dongy = (Button) findViewById(R.id.btn_dongy_addthucdon);

        storage = FirebaseStorage.getInstance().getReference().child("HinhAnhThucDon");
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
                tenthucdon = edt_tenthucdon.getText().toString();
                calo = edt_calo.getText().toString();
                if (ImageUri == null) {
                    Toast.makeText(ThemThucDon.this, "Hình ảnh bắt buộc", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(tenthucdon)) {
                    Toast.makeText(ThemThucDon.this, "Vui lòng nhập tên thực đơn", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(calo)) {
                    Toast.makeText(ThemThucDon.this, "Vui lòng nhập tổng calo", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    themanhthucdon();
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
    private void themanhthucdon() {

        StorageReference firepath = storage.child(ImageUri.getLastPathSegment()+".jpg");
        UploadTask uploadTask = firepath.putFile(ImageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ThemThucDon.this, "Có lỗi", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(ThemThucDon.this, "Hình ảnh được upload thành công", Toast.LENGTH_SHORT).show();
            }
        }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                Toast.makeText(ThemThucDon.this, "Thành công", Toast.LENGTH_SHORT).show();
                themthucdon();
            }
        });
    }

    private void themthucdon() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Thucdon");
        HashMap<String,Object> thucdon = new HashMap<>();
        thucdon.put("tenthucdon",tenthucdon);
        thucdon.put("calo",calo);

        thucdon.put("imgthucdon",dowloadimageUri);

        String id = ref.push().getKey();

        ref.child(id).updateChildren(thucdon).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(ThemThucDon.this, "Thêm chương trình thành công", Toast.LENGTH_SHORT).show();
                finish();
            }
        });


    }

}
