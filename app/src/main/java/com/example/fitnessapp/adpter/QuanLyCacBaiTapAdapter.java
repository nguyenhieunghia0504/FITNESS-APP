package com.example.fitnessapp.adpter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessapp.Admin.QuanLyChiTietBaiTap;
import com.example.fitnessapp.CacBaiTap;
import com.example.fitnessapp.ChiTietCacBaiTap;
import com.example.fitnessapp.R;
import com.example.fitnessapp.model.ChiTietBaiTap;
import com.example.fitnessapp.model.ChuongTrinh;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class QuanLyCacBaiTapAdapter extends RecyclerView.Adapter<QuanLyCacBaiTapAdapter.ViewHolder> {
    ArrayList<ChiTietBaiTap> chiTietBaiTaps;
    Context context;
    ChuongTrinh chuongTrinh;
    ArrayList<ChuongTrinh> chuongTrinhs;
    public QuanLyCacBaiTapAdapter(ArrayList<ChiTietBaiTap> chiTietBaiTaps, Context context) {
        this.chiTietBaiTaps = chiTietBaiTaps;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.custom_chitietbaitap,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.tenbaitap.setText(chiTietBaiTaps.get(position).getTenbaitap());
        holder.soday.setText(chiTietBaiTaps.get(position).getSoday()+" đẩy");
        holder.solan.setText(chiTietBaiTaps.get(position).getSolan()+" lần");
        Picasso.get().load(chiTietBaiTaps.get(position).getHinhanh()).into(holder.anh);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                PopupMenu popupMenu = new PopupMenu(context,holder.itemView);
                popupMenu.getMenuInflater().inflate(R.menu.menu_sua_xoa,popupMenu.getMenu());
                
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if (menuItem.getItemId()==R.id.action_xoa){
                            xoabaitap();
                            Toast.makeText(context, "Xóa", Toast.LENGTH_SHORT).show();
                        }
                        return false;
                    }
                });
                popupMenu.show();
                return false;
            }

            private void xoabaitap() {
                final DatabaseReference refbaitap = FirebaseDatabase.getInstance().getReference("CacBaiTap");
                DatabaseReference refChuongtrinh = FirebaseDatabase.getInstance().getReference("ChuongTrinh");
                chuongTrinhs = new ArrayList<>();
                refChuongtrinh.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        chuongTrinhs.clear();
                        for (DataSnapshot data : dataSnapshot.getChildren()){
                            chuongTrinh = data.getValue(ChuongTrinh.class);

                            chuongTrinh.setId(data.getKey());
                            chuongTrinhs.add(chuongTrinh);
                        }
                        for (int i = 0 ;i<chuongTrinhs.size();i++){
                            final String iddanhmuc = chuongTrinhs.get(i).getId();
                            refbaitap.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot2) {
                                    if (dataSnapshot2.child(iddanhmuc).child(chiTietBaiTaps.get(position).getId()).exists()){
                                        refbaitap.child(iddanhmuc).child(chiTietBaiTaps.get(position).getId()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                Toast.makeText(context, "Xóa bài tập thành công", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });
    }

    @Override
    public int getItemCount() {
        return chiTietBaiTaps.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView anh;
        TextView soday,solan,tenbaitap;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            anh = (ImageView) itemView.findViewById(R.id.anhbaitap);
            soday = (TextView) itemView.findViewById(R.id.soday);
            solan = (TextView) itemView.findViewById(R.id.solan);
            tenbaitap = (TextView) itemView.findViewById(R.id.tenbaitap);

        }
    }
}
