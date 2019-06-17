package com.example.bawei.com.movies.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bawei.com.movies.R;
import com.example.bawei.com.movies.adapter.FindMoviePageListAdapter;
import com.example.bawei.com.movies.bean.FindMoviePageListBean;
import com.example.bawei.com.movies.bean.Result;
import com.example.bawei.com.movies.bean.UserInfo;
import com.example.bawei.com.movies.core.DataCall;
import com.example.bawei.com.movies.dao.DaoMaster;
import com.example.bawei.com.movies.dao.UserInfoDao;
import com.example.bawei.com.movies.presenter.FindMoviePageListPresenter;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieFragment extends Fragment implements DataCall<List<FindMoviePageListBean>> {

    @BindView(R.id.XRecyclerView)
    XRecyclerView mXRecyclerView;

    private FindMoviePageListAdapter mAdapter;
    private FindMoviePageListPresenter mPresenter;
    private UserInfo mUnique;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_movie, null);
        ButterKnife.bind(this, view);

        UserInfoDao userInfoDao = DaoMaster.newDevSession(getContext(), UserInfoDao.TABLENAME).getUserInfoDao();
        mUnique = userInfoDao.queryBuilder().where(UserInfoDao.Properties.Status.eq(1)).unique();

        mAdapter = new FindMoviePageListAdapter(getContext());
        mPresenter = new FindMoviePageListPresenter(this);

        mXRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mXRecyclerView.setAdapter(mAdapter);
        mXRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mXRecyclerView.refreshComplete();
                mXRecyclerView.loadMoreComplete();
                mPresenter.requestData(true, String.valueOf(mUnique.getId()), mUnique.getSessionId());
            }

            @Override
            public void onLoadMore() {
                mXRecyclerView.refreshComplete();
                mXRecyclerView.loadMoreComplete();
                mPresenter.requestData(false, String.valueOf(mUnique.getId()), mUnique.getSessionId());
            }
        });

        mXRecyclerView.refresh();

        return view;
    }

    @Override
    public void success(List<FindMoviePageListBean> result) {
        if (mPresenter.getPage() == 1) {
            mAdapter.clear();
        }
        mAdapter.addList(result);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void error(Result result) {
        Toast.makeText(getContext(), "" + result.message, Toast.LENGTH_SHORT).show();
    }
}
