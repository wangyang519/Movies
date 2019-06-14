package com.example.bawei.com.movies.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.bawei.com.movies.R;
import com.example.bawei.com.movies.bean.Pinglun;

import java.util.ArrayList;
import java.util.List;

public class PinglunadapterEr extends RecyclerView.Adapter<PinglunadapterEr.holder> {


    List<Pinglun> list;
    Context context;

    public PinglunadapterEr(Context context) {
        list=new ArrayList<>();
        this.context = context;
    }
    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=View.inflate(context, R.layout.yingpinger_item_item,null);
        return new holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int i) {

        // holder.textView.setText(list.get(i).commentUserName);
         Glide.with(context).load(list.get(i).commentHeadPic).into(holder.imageView);
        holder.text_2.setText(list.get(i).greatNum);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void aaaa(List<Pinglun> result) {
        list.addAll(result);
    }

    class holder extends RecyclerView.ViewHolder{

        private TextView textView,text_1,text_2;
        private ImageView imageView;
        public holder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.pingluner_text);
            imageView=itemView.findViewById(R.id.comment_img);
            text_2=itemView.findViewById(R.id.commenter_2);
        }
    }
}
