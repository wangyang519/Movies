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
import com.example.bawei.com.movies.bean.fujinBean;
import com.example.bawei.com.movies.bean.tuijianBean;

import java.util.ArrayList;
import java.util.List;

public class FujinAdapter extends RecyclerView.Adapter<FujinAdapter.holder> {

    List<fujinBean> list;
    Context context;

    public FujinAdapter(Context context) {
        list=new ArrayList<>();
        this.context = context;
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=View.inflate(context,R.layout.fujin_item,null);
        return new holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int i) {
        final fujinBean fujinBean = list.get(i);
        holder.textView_name.setText(list.get(i).name);
        Glide.with(context).load(list.get(i).logo).into(holder.imageView);
        holder.text_Add.setText(list.get(i).address);
        holder.text_qian.setText(list.get(i).commentTotal+"千米");


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addd(List<fujinBean> result) {
        list.addAll(result);
    }


    class holder extends RecyclerView.ViewHolder{


        private ImageView imageView;
        private TextView textView_name,text_Add,text_qian;
        public holder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.fujin_imagview);
            textView_name=itemView.findViewById(R.id.fujin_Name);
            text_Add=itemView.findViewById(R.id.fujin_Adress);
            text_qian=itemView.findViewById(R.id.fujin_qianmi);
        }
    }
}
