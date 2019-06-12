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
import com.example.bawei.com.movies.adapter.MovieAdapter;
import com.example.bawei.com.movies.adapter.MovieReadapter;
import com.example.bawei.com.movies.adapter.Readapter;
import com.example.bawei.com.movies.bean.Result;
import com.example.bawei.com.movies.bean.UserInfo;
import com.example.bawei.com.movies.bean.reBean;
import com.example.bawei.com.movies.core.DataCall;
import com.example.bawei.com.movies.dao.DaoMaster;
import com.example.bawei.com.movies.dao.UserInfoDao;
import com.example.bawei.com.movies.fragment.Shouyefrag;
import com.example.bawei.com.movies.presenter.RemenPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class RemenMovieFragment extends Fragment {

    @BindView(R.id.remen_recy)
    RecyclerView remenRecy;
    Unbinder unbinder;

    UserInfo userInfo;
    private MovieReadapter movieReadapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.re_movie_fra, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        userInfo=DaoMaster.newDevSession(getContext(),UserInfoDao.TABLENAME).getUserInfoDao().loadAll().get(0);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        remenRecy.setLayoutManager(linearLayoutManager);


        movieReadapter = new MovieReadapter(getContext());
        remenRecy.setAdapter(movieReadapter);
        RemenPresenter remenPresenter = new RemenPresenter(new Myre());
        remenPresenter.requestData(userInfo.getUserId(),userInfo.getSessionId(),1,20);
    }

    //热门电影
    class Myre implements DataCall<List<reBean>> {

        @Override
        public void success(List<reBean> result) {

            movieReadapter.addrea(result);
            movieReadapter.notifyDataSetChanged();
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
