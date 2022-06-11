package com.example.fitnessapp.adpter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessapp.ChiTietCacBaiTap;
import com.example.fitnessapp.R;
import com.example.fitnessapp.model.ChuongTrinh;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ChuongTrinhAdpter extends RecyclerView.Adapter<ChuongTrinhAdpter.ViewHolder> {
    ArrayList<ChuongTrinh> chuongTrinhs;
    Context context;

    public ChuongTrinhAdpter(ArrayList<ChuongTrinh> chuongTrinhs, Context context) {
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
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
     holder.tenchuongtrinh.setText(chuongTrinhs.get(position).getTenchuongtrinh());
     holder.sophut.setText(chuongTrinhs.get(position).getSophut()+" phút");
     holder.sotuan.setText(chuongTrinhs.get(position).getSotuan()+" tuần");
     Picasso.get().load(chuongTrinhs.get(position).getAnh()).into(holder.anh);

     holder.itemView.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             Intent intent = new Intent(context, ChiTietCacBaiTap.class);
             intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
             intent.putExtra("DANHMUC",chuongTrinhs.get(position));
             context.startActivity(intent);
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
