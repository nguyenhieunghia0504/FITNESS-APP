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
import com.example.fitnessapp.model.Chitietthucdon;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ChiTietThucDonAdapter extends RecyclerView.Adapter<ChiTietThucDonAdapter.ViewHolder> {
    ArrayList<Chitietthucdon> chitietthucdons ;
    Context context;

    public ChiTietThucDonAdapter(ArrayList<Chitietthucdon> chitietthucdons, Context context) {
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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tenchitietthucdon.setText(chitietthucdons.get(position).getTenchitietthucdon());
        holder.calo.setText(chitietthucdons.get(position).getCalo()+" calo");
        holder.khoiluong.setText(chitietthucdons.get(position).getKhoiluong()+"gam");
        Picasso.get().load(chitietthucdons.get(position).getHinhanhthucdon()).into(holder.anh);
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
