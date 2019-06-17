package com.example.bawei.com.movies.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.bawei.com.movies.R;
import com.example.bawei.com.movies.activity.XiangQiangActivity;
import com.example.bawei.com.movies.bean.Result;
import com.example.bawei.com.movies.bean.UserInfo;
import com.example.bawei.com.movies.bean.reBean;
import com.example.bawei.com.movies.core.DataCall;
import com.example.bawei.com.movies.dao.DaoMaster;
import com.example.bawei.com.movies.dao.UserInfoDao;
import com.example.bawei.com.movies.presenter.CancelFollowMoviePresenter;
import com.example.bawei.com.movies.presenter.FollowMoviePresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.greendao.annotation.Id;

import java.util.ArrayList;
import java.util.List;

public class MovieReadapter extends RecyclerView.Adapter<MovieReadapter.holder> {

    List<reBean> list;
    Context context;
    boolean aa;
    private FollowMoviePresenter mPresenter;
    private CancelFollowMoviePresenter mCancelFollowMoviePresenter;
    private UserInfoDao mDao;
    private UserInfo mUnique;
    private String mId;

    public MovieReadapter(Context context) {
        list = new ArrayList<>();
        this.context = context;
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.movie_red_item, null);
        return new holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final holder holder, final int i) {

        mDao = DaoMaster.newDevSession(context, UserInfoDao.TABLENAME).getUserInfoDao();
        mUnique = mDao.queryBuilder().where(UserInfoDao.Properties.Status.eq(1)).unique();

        final reBean reBean = list.get(i);
        Glide.with(context).load(list.get(i).imageUrl).into(holder.imageView);
        holder.textView.setText(list.get(i).name);
        holder.text_jianjie.setText(list.get(i).summary);

        int followMovie = reBean.followMovie;
        if (followMovie == 2) {
            aa = false;
        } else {
            aa = true;
        }

        holder.mCheckBox.setChecked(aa);
        holder.mCheckBox.setTag(reBean);
        holder.mCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final reBean reBean1 = (com.example.bawei.com.movies.bean.reBean) view.getTag();
                String id = reBean1.id;
                CheckBox checkBox = (CheckBox) view;
                boolean checked = checkBox.isChecked();
                if (checked) {
                    reBean1.followMovie = 1;
                    mPresenter = new FollowMoviePresenter(new DataCall<Result>() {
                        @Override
                        public void success(Result result) {
                            Toast.makeText(context, "关注成功！", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void error(Result result) {
                            Toast.makeText(context, result.message, Toast.LENGTH_SHORT).show();
                        }
                    });

                    mPresenter.requestData(String.valueOf(mUnique.getId()), mUnique.getSessionId(), id);
                    Toast.makeText(context, "" + id, Toast.LENGTH_SHORT).show();
                } else {
                    reBean1.followMovie = 2;
                    mCancelFollowMoviePresenter = new CancelFollowMoviePresenter(new DataCall<Result>() {
                        @Override
                        public void success(Result result) {
                            Toast.makeText(context, "取消关注成功!", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void error(Result result) {
                            Toast.makeText(context, result.message, Toast.LENGTH_SHORT).show();
                        }
                    });

                    mCancelFollowMoviePresenter.requestData(String.valueOf(mUnique.getId()), mUnique.getSessionId(), id);
                }
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mId = reBean.id;
                String name = reBean.name;
                Intent intent = new Intent(context, XiangQiangActivity.class);
                intent.putExtra("id", mId);
                EventBus.getDefault().postSticky(name);
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

    public List<reBean> getList() {
        return list;
    }


    class holder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView textView, text_jianjie;
        private CheckBox mCheckBox;

        public holder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.movie_imageview_view);
            textView = itemView.findViewById(R.id.movie_name);
            text_jianjie = itemView.findViewById(R.id.movie_jianjie);
            mCheckBox = itemView.findViewById(R.id.checkbox);
        }
    }
}
