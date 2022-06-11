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

import com.example.fitnessapp.Admin.QuanLyChiTietThucDon;
import com.example.fitnessapp.R;
import com.example.fitnessapp.XemChiTietThucDon;
import com.example.fitnessapp.model.ThucDon;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class QuanLyThucDonAdapter extends RecyclerView.Adapter<QuanLyThucDonAdapter.ViewHolder> {
    ArrayList<ThucDon> thucDons;
    Context context;

    public QuanLyThucDonAdapter(ArrayList<ThucDon> thucDons, Context context) {
        this.thucDons = thucDons;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.custom_rcl_thucdon,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.tenthucdon.setText(thucDons.get(position).getTenthucdon());

        Picasso.get().load(thucDons.get(position).getImgthucdon()).into(holder.imgthucdon);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, QuanLyChiTietThucDon.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("QUANLYTHUCDON",thucDons.get(position));
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
                            xoathucdon();
                            Toast.makeText(context, "Xóa", Toast.LENGTH_SHORT).show();
                        }
                        return false;
                    }
                });
                popupMenu.show();
                return false;
            }

            private void xoathucdon() {
                DatabaseReference refthucdon = FirebaseDatabase.getInstance().getReference("Thucdon");
                refthucdon.child(thucDons.get(position).getId()).removeValue();
                DatabaseReference refcacbaitap = FirebaseDatabase.getInstance().getReference("ChiTietThucDon");
                refcacbaitap.child(thucDons.get(position).getId()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(context, "Xóa thực đơn thành công", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return thucDons.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgthucdon;
        TextView tenthucdon;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgthucdon = (ImageView) itemView.findViewById(R.id.imgthucdon);

            tenthucdon = (TextView) itemView.findViewById(R.id.tenthucdon);
        }
    }
}
