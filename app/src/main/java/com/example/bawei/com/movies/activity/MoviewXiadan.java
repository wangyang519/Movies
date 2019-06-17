package com.example.bawei.com.movies.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.bawei.com.movies.R;
import com.example.bawei.com.movies.adapter.Mingadapter;
import com.example.bawei.com.movies.bean.Result;
import com.example.bawei.com.movies.bean.mingBean;
import com.example.bawei.com.movies.core.DataCall;
import com.example.bawei.com.movies.presenter.DianmingxiPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoviewXiadan extends AppCompatActivity {

    @BindView(R.id.xaidan_image_back)
    ImageView xaidanImageBack;
    @BindView(R.id.xiadan_name)
    TextView xiadanName;
    @BindView(R.id.xiadan_address)
    TextView xiadanAddress;
    @BindView(R.id.ticket_recy)
    RecyclerView ticketRecy;
    @BindView(R.id.ticket_sdv_image)
    ImageView ticketSdvImage;
    private Mingadapter mingadapter;
    private String image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moview_xiadan);
        ButterKnife.bind(this);

        EventBus.getDefault().register(this);
        xaidanImageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        Intent intent = getIntent();
       // image = intent.getStringExtra("image");
        //Toast.makeText(MoviewXiadan.this, image, Toast.LENGTH_LONG).show();


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        ticketRecy.setLayoutManager(linearLayoutManager);

        mingadapter = new Mingadapter(this);
        ticketRecy.setAdapter(mingadapter);


        DianmingxiPresenter dianmingxiPresenter = new DianmingxiPresenter(new Myming());
        dianmingxiPresenter.requestData("1", "1");

    }

    class Myming implements DataCall<List<mingBean>> {


        @Override
        public void success(List<mingBean> result) {
            Glide.with(MoviewXiadan.this).load(image).into(ticketSdvImage);
            mingadapter.addss(result);
            mingadapter.notifyDataSetChanged();
        }

        @Override
        public void error(Result result) {

        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void getData(String name) {
        xiadanName.setText(name);

    }
}
