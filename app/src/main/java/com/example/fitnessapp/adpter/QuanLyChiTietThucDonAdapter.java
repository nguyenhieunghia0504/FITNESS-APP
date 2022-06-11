package com.example.fitnessapp.adpter;

import android.content.Context;
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

import com.example.fitnessapp.R;
import com.example.fitnessapp.model.Chitietthucdon;
import com.example.fitnessapp.model.ChuongTrinh;
import com.example.fitnessapp.model.ThucDon;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class QuanLyChiTietThucDonAdapter extends RecyclerView.Adapter<QuanLyChiTietThucDonAdapter.ViewHolder> {
    ArrayList<Chitietthucdon> chitietthucdons;
    Context context ;
    ArrayList<ThucDon> thucDons;
    ThucDon thucDon;
    public QuanLyChiTietThucDonAdapter(ArrayList<Chitietthucdon> chitietthucdons, Context context) {
        this.chitietthucdons = chitietthucdons;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.custom_chitietthucdon,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.tenchitietthucdon.setText(chitietthucdons.get(position).getTenchitietthucdon());
        holder.calo.setText(chitietthucdons.get(position).getCalo()+" calo");
        holder.khoiluong.setText(chitietthucdons.get(position).getKhoiluong()+"gam");
        Picasso.get().load(chitietthucdons.get(position).getHinhanhthucdon()).into(holder.anh);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                PopupMenu popupMenu = new PopupMenu(context,holder.itemView);
                popupMenu.getMenuInflater().inflate(R.menu.menu_sua_xoa,popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        if (menuItem.getItemId()==R.id.action_xoa){
                            xoachitietthucdon();
                            Toast.makeText(context, "Xóa", Toast.LENGTH_SHORT).show();
                        }
                        return false;
                    }
                });
                popupMenu.show();
                return false;
            }

            private void xoachitietthucdon() {

                final DatabaseReference refctthucdon = FirebaseDatabase.getInstance().getReference("ChiTietThucDon");
                DatabaseReference refThucDon = FirebaseDatabase.getInstance().getReference("Thucdon");
                thucDons = new ArrayList<>();
                refThucDon.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        thucDons.clear();
                        for (DataSnapshot data : dataSnapshot.getChildren()){
                            thucDon = data.getValue(ThucDon.class);

                            thucDon.setId(data.getKey());
                            thucDons.add(thucDon);
                        }
                        for (int i = 0 ;i<thucDons.size();i++){
                            final String iddanhmuc = thucDons.get(i).getId();
                            refctthucdon.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot2) {
                                    if (dataSnapshot2.child(iddanhmuc).child(chitietthucdons.get(position).getId()).exists()){
                                        refctthucdon.child(iddanhmuc).child(chitietthucdons.get(position).getId()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                Toast.makeText(context, "Xóa chi tiết thực đơn thành công", Toast.LENGTH_SHORT).show();
                                                notifyDataSetChanged();
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
        return chitietthucdons.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView anh;
        TextView calo,khoiluong,tenchitietthucdon;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            anh = (ImageView) itemView.findViewById(R.id.anhchitietthucdon);
            calo = (TextView) itemView.findViewById(R.id.calo);
            khoiluong= (TextView) itemView.findViewById(R.id.khoiluong);
            tenchitietthucdon = (TextView) itemView.findViewById(R.id.tenchitietthucdon);
        }
    }
}
