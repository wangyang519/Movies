package com.example.bawei.com.movies.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.bawei.com.movies.R;
import com.example.bawei.com.movies.activity.XiangQiangActivity;
import com.example.bawei.com.movies.activity.Zhangshi;
import com.example.bawei.com.movies.bean.reBean;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class MovieReadapter extends RecyclerView.Adapter<MovieReadapter.holder> {

    List<reBean> list;
    Context context;

    public MovieReadapter(Context context) {
         list=new ArrayList<>();
        this.context = context;
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=View.inflate(context,R.layout.movie_red_item,null);
        return new holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int i) {
       final reBean reBean = list.get(i);
         Glide.with(context).load(list.get(i).imageUrl).into(holder.imageView);
         holder.textView.setText(list.get(i).name);
         holder.text_jianjie.setText(list.get(i).summary);

         holder.itemView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                String id= reBean.id;
                 Intent intent=new Intent(context,XiangQiangActivity.class);
                 intent.putExtra("id",id);
                 context.startActivity(intent);
             }
         });
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
        private TextView textView,text_jianjie;
        public holder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.movie_imageview_view);
             textView=itemView.findViewById(R.id.movie_name);
             text_jianjie=itemView.findViewById(R.id.movie_jianjie);
        }
    }
}
