package com.example.bawei.com.movies.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.bawei.com.movies.R;
import com.example.bawei.com.movies.bean.shangBean;

import java.util.ArrayList;
import java.util.List;

import retrofit2.http.POST;

public class BannerAdapter extends RecyclerView.Adapter<BannerAdapter.holder> {

    Context context;
    int imag[];

    public BannerAdapter(Context context, int[] imag) {
        this.context = context;
        this.imag = imag;
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=View.inflate(context, R.layout.banner_item,null);
        return new holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int i) {
        Glide.with(context).load(imag[i]).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return imag.length;
    }

    class holder extends RecyclerView.ViewHolder{

        private ImageView imageView;
        public holder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.banner_image);
        }
    }
}
