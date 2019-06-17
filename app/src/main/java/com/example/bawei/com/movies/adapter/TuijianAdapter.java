package com.example.bawei.com.movies.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.bawei.com.movies.R;
import com.example.bawei.com.movies.activity.YingyuanMingxiActivity;
import com.example.bawei.com.movies.bean.Result;
import com.example.bawei.com.movies.bean.UserInfo;
import com.example.bawei.com.movies.bean.tuijianBean;
import com.example.bawei.com.movies.core.DataCall;
import com.example.bawei.com.movies.dao.DaoMaster;
import com.example.bawei.com.movies.dao.UserInfoDao;
import com.example.bawei.com.movies.presenter.CancelFollowCinemaPresenter;
import com.example.bawei.com.movies.presenter.FollowCinemaPresenter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class TuijianAdapter extends RecyclerView.Adapter<TuijianAdapter.holder> {

    List<tuijianBean> list;
    Context context;
    boolean aa;
    private FollowCinemaPresenter mFollowCinemaPresenter;
    private CancelFollowCinemaPresenter mCancelFollowCinemaPresenter;
    private UserInfo mUnique;

    public TuijianAdapter(Context context) {
        list = new ArrayList<>();
        this.context = context;
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.tuijina_item, null);
        return new holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final holder holder, int i) {

        UserInfoDao userInfoDao = DaoMaster.newDevSession(context, UserInfoDao.TABLENAME).getUserInfoDao();
        mUnique = userInfoDao.queryBuilder().where(UserInfoDao.Properties.Status.eq(1)).unique();

        final tuijianBean tuijianBean = list.get(i);
        holder.textView_name.setText(list.get(i).name);
        Glide.with(context).load(list.get(i).logo).into(holder.imageView);
        holder.text_Add.setText(list.get(i).address);
        holder.text_qian.setText(list.get(i).commentTotal + "千米");

        int followCinema = tuijianBean.followCinema;
        if (followCinema == 2) {
            aa = false;
        } else {
            aa = true;
        }
        holder.mCheckBox.setChecked(aa);
        holder.mCheckBox.setTag(tuijianBean);

        holder.mCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                com.example.bawei.com.movies.bean.tuijianBean tuijianBean1 = (com.example.bawei.com.movies.bean.tuijianBean) view.getTag();
                String id = tuijianBean1.id;
                CheckBox checkBox = (CheckBox) view;
                boolean checked = checkBox.isChecked();
                if (checked) {
                    tuijianBean1.followCinema = 1;
                    mFollowCinemaPresenter = new FollowCinemaPresenter(new DataCall<Result>() {
                        @Override
                        public void success(Result result) {
                            Toast.makeText(context, "关注成功！", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void error(Result result) {
                            Toast.makeText(context, result.message, Toast.LENGTH_SHORT).show();
                        }
                    });

                    mFollowCinemaPresenter.requestData(String.valueOf(mUnique.getId()), mUnique.getSessionId(), id);

                } else {
                    tuijianBean1.followCinema = 2;
                    mCancelFollowCinemaPresenter = new CancelFollowCinemaPresenter(new DataCall() {
                        @Override
                        public void success(Object result) {
                            Toast.makeText(context, "取消关注成功！", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void error(Result result) {
                            Toast.makeText(context, result.message, Toast.LENGTH_SHORT).show();
                        }
                    });
                    mCancelFollowCinemaPresenter.requestData(String.valueOf(mUnique.getId()), mUnique.getSessionId(), id);
                }
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = tuijianBean.id;
                Intent intent = new Intent(context, YingyuanMingxiActivity.class);
                intent.putExtra("id", id);
                EventBus.getDefault().postSticky(tuijianBean);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addd(List<tuijianBean> result) {
        list.addAll(result);
    }

    class holder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView textView_name, text_Add, text_qian;
        private CheckBox mCheckBox;

        public holder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.tuijina_imagview);
            textView_name = itemView.findViewById(R.id.tuijian_Name);
            text_Add = itemView.findViewById(R.id.tuijian_Adress);
            text_qian = itemView.findViewById(R.id.tuijina_qianmi);
            mCheckBox = itemView.findViewById(R.id.checkBox);
        }
    }
}
