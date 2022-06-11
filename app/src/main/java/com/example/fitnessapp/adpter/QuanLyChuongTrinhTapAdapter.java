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
import com.example.fitnessapp.ChiTietCacBaiTap;
import com.example.fitnessapp.R;
import com.example.fitnessapp.model.ChuongTrinh;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class QuanLyChuongTrinhTapAdapter extends RecyclerView.Adapter<QuanLyChuongTrinhTapAdapter.ViewHolder> {
    ArrayList<ChuongTrinh> chuongTrinhs;
    Context context;

    public QuanLyChuongTrinhTapAdapter(ArrayList<ChuongTrinh> chuongTrinhs, Context context) {
        this.chuongTrinhs = chuongTrinhs;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.custom_rcl_danhmuc,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.tenchuongtrinh.setText(chuongTrinhs.get(position).getTenchuongtrinh());
        holder.sophut.setText(chuongTrinhs.get(position).getSophut()+" phút");
        holder.sotuan.setText(chuongTrinhs.get(position).getSotuan()+" tuần");
        Picasso.get().load(chuongTrinhs.get(position).getAnh()).into(holder.anh);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, QuanLyChiTietBaiTap.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("QUANLYDANHMUC",chuongTrinhs.get(position));
                context.startActivity(intent);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                PopupMenu popupMenu = new PopupMenu(context,holder.itemView);
                popupMenu.getMenuInflater().inflate(R.menu.menu_sua_xoa,popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                            if (menuItem.getItemId()==R.id.action_xoa){
                                xoachuongtrinh();
                                Toast.makeText(context, "Xóa", Toast.LENGTH_SHORT).show();
                            }
                        return false;
                    }
                });
                popupMenu.show();
                return false;
            }

            private void xoachuongtrinh() {
                DatabaseReference refchuongtrinh = FirebaseDatabase.getInstance().getReference("ChuongTrinh");
                refchuongtrinh.child(chuongTrinhs.get(position).getId()).removeValue();
                DatabaseReference refcacbaitap = FirebaseDatabase.getInstance().getReference("CacBaiTap");
                refcacbaitap.child(chuongTrinhs.get(position).getId()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(context, "Xóa chương trình tập thành công", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }

    @Override
    public int getItemCount() {
        return chuongTrinhs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView anh;
        TextView sotuan,sophut,tenchuongtrinh;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            anh = (ImageView) itemView.findViewById(R.id.anhchuongtrinh);
            sotuan = (TextView) itemView.findViewById(R.id.sotuan);
            sophut = (TextView) itemView.findViewById(R.id.sophut);
            tenchuongtrinh = (TextView) itemView.findViewById(R.id.tenchuongtrinh);
        }
    }
}
