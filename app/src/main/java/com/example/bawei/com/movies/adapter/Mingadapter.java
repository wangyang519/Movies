package com.example.bawei.com.movies.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bawei.com.movies.R;
import com.example.bawei.com.movies.activity.GoupiaoActivity;
import com.example.bawei.com.movies.bean.mingBean;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class Mingadapter extends RecyclerView.Adapter<Mingadapter.holder> {


    List<mingBean> list;
    Context context;

    public Mingadapter( Context context) {
        list=new ArrayList<>();
        this.context = context;
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=View.inflate(context,R.layout.mingzi_item,null);
        return new holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int i) {

        final mingBean mingBean = list.get(i);
        holder.text_mon.setText(list.get(i).price);
         holder.text_view.setText(list.get(i).screeningHall);
         holder.text_shi.setText(list.get(i).beginTime);
         holder.text_guan.setText(list.get(i).endTime);

         holder.itemView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                String price =mingBean.price;
                 Intent intent=new Intent(context,GoupiaoActivity.class);
                 intent.putExtra("price",price);
                 context.startActivity(intent);
             }
         });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addss(List<mingBean> result) {
        list.addAll(result);
    }


    class holder extends RecyclerView.ViewHolder{

        private TextView text_view,text_shi,text_mon,text_guan;
        public holder(@NonNull View itemView) {
            super(itemView);
            text_view=itemView.findViewById(R.id.mingxi_name);
            text_shi=itemView.findViewById(R.id.cinema_start);
            text_mon=itemView.findViewById(R.id.cinema_mongey);
            text_guan=itemView.findViewById(R.id.cinema_guanbi);
        }
    }
}
