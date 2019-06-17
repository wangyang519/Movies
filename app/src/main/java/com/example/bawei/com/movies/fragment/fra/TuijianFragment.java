package com.example.bawei.com.movies.fragment.fra;

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
import com.example.bawei.com.movies.adapter.TuijianAdapter;
import com.example.bawei.com.movies.bean.Result;
import com.example.bawei.com.movies.bean.UserInfo;
import com.example.bawei.com.movies.bean.tuijianBean;
import com.example.bawei.com.movies.core.DataCall;
import com.example.bawei.com.movies.dao.DaoMaster;
import com.example.bawei.com.movies.dao.UserInfoDao;
import com.example.bawei.com.movies.presenter.TuijianPresenter;
import com.example.bawei.com.movies.util.StatusBarUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class TuijianFragment extends Fragment {

    @BindView(R.id.tuijina_list)
    RecyclerView tuijinaList;
    Unbinder unbinder;
    private TuijianAdapter tuijianAdapter;

    UserInfoDao mDao;
    private UserInfo mUnique;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tuijian, container, false);
        unbinder = ButterKnife.bind(this, view);

        StatusBarUtil.setTransparent(getActivity());
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mDao = DaoMaster.newDevSession(getContext(), UserInfoDao.TABLENAME).getUserInfoDao();
        mUnique = mDao.queryBuilder().where(UserInfoDao.Properties.Status.eq(1)).unique();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        tuijinaList.setLayoutManager(linearLayoutManager);

        tuijianAdapter = new TuijianAdapter(getContext());
        tuijinaList.setAdapter(tuijianAdapter);

        TuijianPresenter tuijianPresenter = new TuijianPresenter(new Mytui());
        tuijianPresenter.requestData(mUnique.getId(), mUnique.getSessionId(), 1, 20);
    }

    class Mytui implements DataCall<List<tuijianBean>> {

        @Override
        public void success(List<tuijianBean> result) {
            tuijianAdapter.addd(result);
            tuijianAdapter.notifyDataSetChanged();
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
