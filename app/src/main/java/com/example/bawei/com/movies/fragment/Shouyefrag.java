package com.example.bawei.com.movies.fragment;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bawei.com.movies.R;
import com.example.bawei.com.movies.adapter.BannerAdapter;
import com.example.bawei.com.movies.adapter.Readapter;
import com.example.bawei.com.movies.adapter.Yingadapter;
import com.example.bawei.com.movies.adapter.shangadapter;
import com.example.bawei.com.movies.bean.Result;
import com.example.bawei.com.movies.bean.ReyingBean;
import com.example.bawei.com.movies.bean.UserInfo;
import com.example.bawei.com.movies.bean.reBean;
import com.example.bawei.com.movies.bean.shangBean;
import com.example.bawei.com.movies.core.DataCall;
import com.example.bawei.com.movies.dao.DaoMaster;
import com.example.bawei.com.movies.dao.UserInfoDao;
import com.example.bawei.com.movies.presenter.RemenPresenter;
import com.example.bawei.com.movies.presenter.ReyingPresenter;
import com.example.bawei.com.movies.presenter.ShangPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import recycler.coverflow.RecyclerCoverFlow;

public class Shouyefrag extends Fragment {

    @BindView(R.id.miv)
    ImageView miv;
    @BindView(R.id.mdingwei)
    TextView mdingwei;
    @BindView(R.id.sou)
    ImageView sou;
    @BindView(R.id.et_sou)
    EditText etSou;
    @BindView(R.id.tv_sou)
    TextView tvSou;
    @BindView(R.id.mlinear)
    LinearLayout mlinear;
    @BindView(R.id.rmdy)
    TextView rmdy;
    @BindView(R.id.movie_image)
    ImageView movieImage;
    @BindView(R.id.ry)
    RelativeLayout ry;
    @BindView(R.id.home_recycler_hotmovie)
    RecyclerView homeRecyclerHotmovie;
    @BindView(R.id.zzry)
    TextView zzry;
    @BindView(R.id.movie_image1)
    ImageView movieImage1;
    @BindView(R.id.zz)
    RelativeLayout zz;
    @BindView(R.id.home_recycler_being)
    RecyclerView homeRecyclerBeing;
    @BindView(R.id.jjry)
    TextView jjry;
    @BindView(R.id.movie_image2)
    ImageView movieImage2;
    @BindView(R.id.jj)
    RelativeLayout jj;
    @BindView(R.id.home_recycler_soon)
    RecyclerView homeRecyclerSoon;
    Unbinder unbinder;
    @BindView(R.id.listt)
    RecyclerCoverFlow banner;
    private Readapter readapter;
    private Yingadapter yingadapter;
    private com.example.bawei.com.movies.adapter.shangadapter shangadapter;
    UserInfo userInfo;


    private int imags[]={R.drawable.a1,R.drawable.a2,R.drawable.a3,R.drawable.a4,R.drawable.a5};
    private BannerAdapter bannerAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shouye_fra, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        userInfo=DaoMaster.newDevSession(getContext(),UserInfoDao.TABLENAME).getUserInfoDao().loadAll().get(0);

        sou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.animation.ObjectAnimator translationX = android.animation.ObjectAnimator.ofFloat(mlinear, "translationX", 0, -550);
                translationX.setDuration(500);
                translationX.start();
            }
        });

        tvSou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ObjectAnimator translationX = ObjectAnimator.ofFloat(mlinear, "translationX", -550, 0);
                translationX.setDuration(500);
                translationX.start();
            }
        });

        bannerAdapter = new BannerAdapter(getContext(),imags);
        banner.setAdapter(bannerAdapter);
        banner.smoothScrollToPosition(imags.length/2);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        homeRecyclerHotmovie.setLayoutManager(linearLayoutManager);
        readapter = new Readapter(getContext());
        homeRecyclerHotmovie.setAdapter(readapter);
        RemenPresenter remenPresenter = new RemenPresenter(new Myre());
        remenPresenter.requestData(userInfo.getUserId(),userInfo.getSessionId(),1,20);

        /*Toast.makeText(getContext(),userInfo.userId+"",Toast.LENGTH_LONG).show();*/




        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getContext());
        linearLayoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        homeRecyclerBeing.setLayoutManager(linearLayoutManager1);
        yingadapter = new Yingadapter(getContext());
        homeRecyclerBeing.setAdapter(yingadapter);
        ReyingPresenter reyingPresenter = new ReyingPresenter(new Mying());
        reyingPresenter.requestData(userInfo.getUserId(),userInfo.getSessionId(),1, 10);


        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getContext());
        linearLayoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        homeRecyclerSoon.setLayoutManager(linearLayoutManager2);

        shangadapter = new shangadapter(getContext());
        homeRecyclerSoon.setAdapter(shangadapter);
        ShangPresenter shangPresenter = new ShangPresenter(new Myshan());
        shangPresenter.requestData(userInfo.getUserId(),userInfo.getSessionId(),1, 10);
    }

    //热门电影
    class Myre implements DataCall<List<reBean>> {

        @Override
        public void success(List<reBean> result) {

            readapter.addrea(result);
            readapter.notifyDataSetChanged();
        }
        @Override
        public void error(Result result) {

        }
    }

    //热映电影
    class Mying implements DataCall<List<ReyingBean>> {

        @Override
        public void success(List<ReyingBean> result) {
            yingadapter.addying(result);
            yingadapter.notifyDataSetChanged();
        }

        @Override
        public void error(Result result) {

        }
    }

    //上映

    class Myshan implements DataCall<List<shangBean>> {

        @Override
        public void success(List<shangBean> result) {
            shangadapter.addsss(result);
            shangadapter.notifyDataSetChanged();
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
