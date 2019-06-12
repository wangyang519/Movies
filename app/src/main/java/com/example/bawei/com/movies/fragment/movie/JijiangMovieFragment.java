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
import com.example.bawei.com.movies.adapter.movieshangadapter;
import com.example.bawei.com.movies.adapter.shangadapter;
import com.example.bawei.com.movies.bean.Result;
import com.example.bawei.com.movies.bean.UserInfo;
import com.example.bawei.com.movies.bean.shangBean;
import com.example.bawei.com.movies.core.DataCall;
import com.example.bawei.com.movies.dao.DaoMaster;
import com.example.bawei.com.movies.dao.UserInfoDao;
import com.example.bawei.com.movies.fragment.Shouyefrag;
import com.example.bawei.com.movies.presenter.ShangPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class JijiangMovieFragment extends Fragment {

    @BindView(R.id.jijiang_list)
    RecyclerView jijiangList;
    Unbinder unbinder;

    UserInfo userInfo;
    private com.example.bawei.com.movies.adapter.movieshangadapter movieshangadapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.jijiang_movie_fra, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        userInfo=DaoMaster.newDevSession(getContext(),UserInfoDao.TABLENAME).getUserInfoDao().loadAll().get(0);

        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getContext());
        linearLayoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        jijiangList.setLayoutManager(linearLayoutManager2);

        movieshangadapter = new movieshangadapter(getContext());
        jijiangList.setAdapter(movieshangadapter);

        ShangPresenter shangPresenter = new ShangPresenter(new Myshan());
        shangPresenter.requestData(userInfo.getUserId(),userInfo.getSessionId(),1, 10);
    }

    class Myshan implements DataCall<List<shangBean>> {

        @Override
        public void success(List<shangBean> result) {
            movieshangadapter.addsss(result);
            movieshangadapter.notifyDataSetChanged();
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
