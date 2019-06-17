package com.example.bawei.com.movies.activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.bawei.com.movies.R;
import com.example.bawei.com.movies.adapter.BannerAdapter;
import com.example.bawei.com.movies.adapter.Mingadapter;
import com.example.bawei.com.movies.adapter.Pinglunadapter;
import com.example.bawei.com.movies.adapter.PinglunadapterEr;
import com.example.bawei.com.movies.bean.Pinglun;
import com.example.bawei.com.movies.bean.Result;
import com.example.bawei.com.movies.bean.mingBean;
import com.example.bawei.com.movies.bean.tuijianBean;
import com.example.bawei.com.movies.core.DataCall;
import com.example.bawei.com.movies.presenter.DianmingxiPresenter;
import com.example.bawei.com.movies.presenter.Dianpinglun;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import recycler.coverflow.RecyclerCoverFlow;

public class YingyuanMingxiActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.mingxi_banner)
    RecyclerCoverFlow mingxiBanner;
    @BindView(R.id.mingxi_list)
    RecyclerView mingxiList;
    @BindView(R.id.mingxi_imageview)
    ImageView mingxiImageview;
    @BindView(R.id.ming_name)
    TextView mingName;
    @BindView(R.id.ming_dizhi)
    TextView mingDizhi;

    private RecyclerView recyclerView_ming;

    PopupWindow popup_tiao;
    private ImageView imageView_down;

    private int imags[] = {R.drawable.a1, R.drawable.a2, R.drawable.a3, R.drawable.a4, R.drawable.a5};
    private BannerAdapter bannerAdapter;
    private Mingadapter mingadapter;

    private PinglunadapterEr pinglunadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yingyuan_mingxi);
        ButterKnife.bind(this);

        bannerAdapter = new BannerAdapter(this, imags);
        mingxiBanner.setAdapter(bannerAdapter);
        mingxiBanner.smoothScrollToPosition(imags.length / 2);

        EventBus.getDefault().register(this);
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");




        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mingxiList.setLayoutManager(linearLayoutManager);

        mingadapter = new Mingadapter(this);
        mingxiList.setAdapter(mingadapter);


        DianmingxiPresenter dianmingxiPresenter = new DianmingxiPresenter(new Myming());
        dianmingxiPresenter.requestData("1","1");


        mingxiImageview.setOnClickListener(this);

    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(tuijianBean tuijianBean){
          mingName.setText(tuijianBean.name);
          Glide.with(this).load(tuijianBean.logo).into(mingxiImageview);
          mingDizhi.setText(tuijianBean.address);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.mingxi_imageview:
                View view_pop_detail = View.inflate(this, R.layout.mingxi_item, null);
                popup_XiangQing(view_pop_detail);
                popup_tiao = new PopupWindow(view_pop_detail, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                popup_tiao.setBackgroundDrawable(new BitmapDrawable());
                popup_tiao.setOutsideTouchable(true);
                popup_tiao.setFocusable(true);
                popup_tiao.showAtLocation(view_pop_detail, Gravity.CENTER_HORIZONTAL, 0, 100);
                break;
        }
    }

    //找 子布局 控件
    private void popup_XiangQing(View view_pop_detail) {
        imageView_down=view_pop_detail.findViewById(R.id.mingzi_finish);
        recyclerView_ming=view_pop_detail.findViewById(R.id.mingxileft_recy);


        Dianpinglun dianpinglun = new Dianpinglun(new Mylun());
        dianpinglun.requestData("1",1,20);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView_ming.setLayoutManager(linearLayoutManager);

        pinglunadapter = new PinglunadapterEr(this);
        recyclerView_ming.setAdapter(pinglunadapter);
        imageView_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup_tiao.dismiss();
            }
        });
    }
    // 影评
    class Mylun implements DataCall<List<Pinglun>>{

        @Override
        public void success(List<Pinglun> result) {
            pinglunadapter.aaaa(result);
            pinglunadapter.notifyDataSetChanged();
        }

        @Override
        public void error(Result result) {

        }
    }


    class Myming implements DataCall<List<mingBean>>{


        @Override
        public void success(List<mingBean> result) {
            mingadapter.addss(result);
            mingadapter.notifyDataSetChanged();
        }

        @Override
        public void error(Result result) {

        }
    }

}
