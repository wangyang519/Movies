package com.example.bawei.com.movies.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.bawei.com.movies.R;
import com.example.bawei.com.movies.adapter.Readapter;
import com.example.bawei.com.movies.bean.Result;
import com.example.bawei.com.movies.bean.reBean;
import com.example.bawei.com.movies.core.DataCall;
import com.example.bawei.com.movies.presenter.RemenPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShowActivity extends AppCompatActivity {

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
    private Readapter readapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        ButterKnife.bind(this);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        homeRecyclerHotmovie.setLayoutManager(linearLayoutManager);
        readapter = new Readapter(this);
        homeRecyclerHotmovie.setAdapter(readapter);
        RemenPresenter remenPresenter = new RemenPresenter(new Myre());
        remenPresenter.requestData(1, 10);


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


}
