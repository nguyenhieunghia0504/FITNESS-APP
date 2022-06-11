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

import com.example.fitnessapp.R;
import com.example.fitnessapp.XemChiTietThucDon;
import com.example.fitnessapp.model.ThucDon;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class thucdonadapter extends RecyclerView.Adapter<thucdonadapter.ViewHolder> {
    ArrayList<ThucDon> thucdons;
    Context context;

    public thucdonadapter(ArrayList<ThucDon> thucdons, Context context) {
        this.thucdons = thucdons;
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
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.tenthucdon.setText(thucdons.get(position).getTenthucdon());
   
        Picasso.get().load(thucdons.get(position).getImgthucdon()).into(holder.imgthucdon);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, XemChiTietThucDon.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("THUCDON",thucdons.get(position));
                context.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return thucdons.size();
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
