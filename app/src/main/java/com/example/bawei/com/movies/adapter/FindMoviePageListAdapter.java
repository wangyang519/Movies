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
import com.example.bawei.com.movies.bean.FindMoviePageListBean;
import com.example.bawei.com.movies.util.DateUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FindMoviePageListAdapter extends RecyclerView.Adapter<FindMoviePageListAdapter.Holder> {

    private Context mContext;
    private List<FindMoviePageListBean> mList;

    public FindMoviePageListAdapter(Context context) {
        this.mContext = context;
        mList = new ArrayList<>();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_movie_item, null);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int i) {

        Glide.with(mContext).load(mList.get(i).imageUrl).into(holder.mViewImage);
        holder.mViewName.setText(mList.get(i).name);
        holder.mViewSummary.setText(mList.get(i).summary);
        try {
            holder.mViewDate.setText(DateUtils.dateTransformer(mList.get(i).releaseTime, DateUtils.DATE_PATTERN));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void addList(List<FindMoviePageListBean> result) {
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
        @BindView(R.id.summary)
        TextView mViewSummary;
        @BindView(R.id.date)
        TextView mViewDate;

        public Holder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
