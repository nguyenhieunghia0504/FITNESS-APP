package com.example.fitnessapp.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessapp.R;
import com.example.fitnessapp.model.ChiTietBaiTap;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ChiTietCacBaiTapAdapter extends RecyclerView.Adapter<ChiTietCacBaiTapAdapter.ViewHolder> {
    ArrayList<ChiTietBaiTap> chiTietBaiTaps ;
    Context context;

    public ChiTietCacBaiTapAdapter(ArrayList<ChiTietBaiTap> chiTietBaiTaps, Context context) {
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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tenbaitap.setText(chiTietBaiTaps.get(position).getTenbaitap());
        holder.soday.setText(chiTietBaiTaps.get(position).getSoday()+" đẩy");
        holder.solan.setText(chiTietBaiTaps.get(position).getSolan()+" lần");
        Picasso.get().load(chiTietBaiTaps.get(position).getHinhanh()).into(holder.anh);
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
