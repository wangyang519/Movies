package com.example.bawei.com.movies.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.bawei.com.movies.R;
import com.example.bawei.com.movies.activity.ShowActivity;
import com.example.bawei.com.movies.adapter.Readapter;
import com.example.bawei.com.movies.adapter.Yingadapter;
import com.example.bawei.com.movies.adapter.shangadapter;
import com.example.bawei.com.movies.bean.Result;
import com.example.bawei.com.movies.bean.ReyingBean;
import com.example.bawei.com.movies.bean.reBean;
import com.example.bawei.com.movies.bean.shangBean;
import com.example.bawei.com.movies.core.DataCall;
import com.example.bawei.com.movies.presenter.RemenPresenter;
import com.example.bawei.com.movies.presenter.ReyingPresenter;
import com.example.bawei.com.movies.presenter.ShangPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

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
    @BindView(R.id.listt)
    RecyclerView listt;
    @BindView(R.id.home_radio_1)
    RadioButton homeRadio1;
    @BindView(R.id.home_radio_2)
    RadioButton homeRadio2;
    @BindView(R.id.home_radio_3)
    RadioButton homeRadio3;
    @BindView(R.id.home_radio_4)
    RadioButton homeRadio4;
    @BindView(R.id.home_radio_5)
    RadioButton homeRadio5;
    @BindView(R.id.home_radio_6)
    RadioButton homeRadio6;
    @BindView(R.id.home_radio_group)
    RadioGroup homeRadioGroup;
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
    private Readapter readapter;
    private Yingadapter yingadapter;
    private com.example.bawei.com.movies.adapter.shangadapter shangadapter;

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

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        homeRecyclerHotmovie.setLayoutManager(linearLayoutManager);
        readapter = new Readapter(getContext());
        homeRecyclerHotmovie.setAdapter(readapter);
        RemenPresenter remenPresenter = new RemenPresenter(new Myre());
        remenPresenter.requestData(1, 10);


        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getContext());
        linearLayoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        homeRecyclerBeing.setLayoutManager(linearLayoutManager1);
        yingadapter = new Yingadapter(getContext());
        homeRecyclerBeing.setAdapter(yingadapter);
        ReyingPresenter reyingPresenter = new ReyingPresenter(new Mying());
        reyingPresenter.requestData(1,10);


        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getContext());
        linearLayoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        homeRecyclerSoon.setLayoutManager(linearLayoutManager2);

        shangadapter = new shangadapter(getContext());
        homeRecyclerSoon.setAdapter(shangadapter);
        ShangPresenter shangPresenter = new ShangPresenter(new Myshan());
        shangPresenter.requestData(1,10);
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
    class  Mying implements DataCall<List<ReyingBean>>{

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

    class Myshan implements DataCall<List<shangBean>>{

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
