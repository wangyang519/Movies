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
import com.example.bawei.com.movies.activity.MoviewXiadan;
import com.example.bawei.com.movies.bean.LeibiaoBean;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class LeiBiaoadapter extends RecyclerView.Adapter<LeiBiaoadapter.holder> {

    List<LeibiaoBean> list;
    Context context;

    public LeiBiaoadapter( Context context) {
        list=new ArrayList<>();
        this.context = context;
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=View.inflate(context,R.layout.liebiao_item,null);
        return new holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, final int i) {
        final LeibiaoBean leibiaoBean = list.get(i);
          holder.textView_name.setText(list.get(i).name);
        Glide.with(context).load(list.get(i).logo).into(holder.imageView);
        holder.text_add.setText(list.get(i).address);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = leibiaoBean.name;
                String address = leibiaoBean.address;
                String image=leibiaoBean.logo;
                Intent intent=new Intent(context,MoviewXiadan.class);
                EventBus.getDefault().postSticky(name);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void adddd(List<LeibiaoBean> result) {
        list.addAll(result);
    }

    class holder extends RecyclerView.ViewHolder{

        private TextView textView_name,text_add,text_shi;
        private ImageView imageView;
        public holder(@NonNull View itemView) {
            super(itemView);
            textView_name=itemView.findViewById(R.id.leibiao_name_item);
            imageView=itemView.findViewById(R.id.leibiao_imagview);
            text_add=itemView.findViewById(R.id.leibiao_Adress);
        }
    }
}
