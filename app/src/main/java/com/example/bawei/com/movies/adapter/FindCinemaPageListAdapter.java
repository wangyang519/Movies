package com.example.bawei.com.movies.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.bawei.com.movies.R;
import com.example.bawei.com.movies.bean.FindCinemaPageListBean;
import com.example.bawei.com.movies.bean.FindMoviePageListBean;
import com.example.bawei.com.movies.util.DateUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FindCinemaPageListAdapter extends RecyclerView.Adapter<FindCinemaPageListAdapter.Holder> {

    private Context mContext;
    private List<FindCinemaPageListBean> mList;

    public FindCinemaPageListAdapter(Context context) {
        this.mContext = context;
        mList = new ArrayList<>();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_cinema_item, null);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int i) {

        Glide.with(mContext).load(mList.get(i).logo).into(holder.mViewImage);
        holder.mViewName.setText(mList.get(i).name);
        holder.mViewAddress.setText(mList.get(i).address);

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void addList(List<FindCinemaPageListBean> result) {
        if (result != null) {
            mList.addAll(result);
        }
    }

    public void clear() {
        mList.clear();
    }

    class Holder extends RecyclerView.ViewHolder {

        @BindView(R.id.image)
        ImageView mViewImage;
        @BindView(R.id.name)
        TextView mViewName;
        @BindView(R.id.address)
        TextView mViewAddress;

        public Holder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
