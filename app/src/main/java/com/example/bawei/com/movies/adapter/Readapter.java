package com.example.bawei.com.movies.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.bawei.com.movies.R;
import com.example.bawei.com.movies.bean.reBean;

import java.util.ArrayList;
import java.util.List;

public class Readapter extends RecyclerView.Adapter<Readapter.holder> {

    List<reBean> list;
    Context context;

    public Readapter( Context context) {
         list=new ArrayList<>();
        this.context = context;
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=View.inflate(context,R.layout.re_item,null);
        return new holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int i) {
        Glide.with(context).load(list.get(i).imageUrl).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addrea(List<reBean> result) {
        list.addAll(result);
    }

    class holder extends RecyclerView.ViewHolder{

        private ImageView imageView;
        public holder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.re_imagevieew);
        }
    }
}
