package com.example.bawei.com.movies.fragment.movie;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bawei.com.movies.R;
import com.example.bawei.com.movies.adapter.MovieYingadapter;
import com.example.bawei.com.movies.adapter.Yingadapter;
import com.example.bawei.com.movies.bean.Result;
import com.example.bawei.com.movies.bean.ReyingBean;
import com.example.bawei.com.movies.bean.UserInfo;
import com.example.bawei.com.movies.core.DataCall;
import com.example.bawei.com.movies.dao.DaoMaster;
import com.example.bawei.com.movies.dao.UserInfoDao;
import com.example.bawei.com.movies.fragment.Shouyefrag;
import com.example.bawei.com.movies.presenter.ReyingPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ZhengMovieFragment extends Fragment {

    @BindView(R.id.zheng_moview_list)
    RecyclerView zhengMoviewList;
    Unbinder unbinder;

    UserInfo userInfo;
    private MovieYingadapter movieYingadapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.zheng_movie_fra, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        userInfo=DaoMaster.newDevSession(getContext(),UserInfoDao.TABLENAME).getUserInfoDao().loadAll().get(0);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getContext());
        linearLayoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
        zhengMoviewList.setLayoutManager(linearLayoutManager1);
        movieYingadapter = new MovieYingadapter(getContext());
        zhengMoviewList.setAdapter(movieYingadapter);
        ReyingPresenter reyingPresenter = new ReyingPresenter(new Mying());
        reyingPresenter.requestData(userInfo.getUserId(),userInfo.getSessionId(),1, 10);
    }
    class Mying implements DataCall<List<ReyingBean>> {

        @Override
        public void success(List<ReyingBean> result) {
            movieYingadapter.addying(result);
            movieYingadapter.notifyDataSetChanged();
        }

        @Override
        public void error(Result result) {

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
