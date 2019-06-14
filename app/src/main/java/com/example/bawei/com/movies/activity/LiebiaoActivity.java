package com.example.bawei.com.movies.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bawei.com.movies.R;
import com.example.bawei.com.movies.adapter.LeiBiaoadapter;
import com.example.bawei.com.movies.bean.LeibiaoBean;
import com.example.bawei.com.movies.bean.Result;
import com.example.bawei.com.movies.core.DataCall;
import com.example.bawei.com.movies.presenter.LieBiaoPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LiebiaoActivity extends AppCompatActivity {

    @BindView(R.id.liebiao_name)
    TextView liebiaoName;
    @BindView(R.id.leibiao_recy)
    RecyclerView leibiaoRecy;
    private LeiBiaoadapter leiBiaoadapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liebiao);
        ButterKnife.bind(this);


        EventBus.getDefault().register(this);

        Intent intent = getIntent();
        String  id = intent.getStringExtra("id");



        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        leibiaoRecy.setLayoutManager(linearLayoutManager);

        leiBiaoadapter = new LeiBiaoadapter(this);
        leibiaoRecy.setAdapter(leiBiaoadapter);

        LieBiaoPresenter lieBiaoPresenter = new LieBiaoPresenter(new Mylie());
        lieBiaoPresenter.requestData("6");
    }


    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(String name){
     liebiaoName.setText(name);
    }


    class Mylie implements DataCall<List<LeibiaoBean>>{

        @Override
        public void success(List<LeibiaoBean> result) {
             leiBiaoadapter.adddd(result);
             leiBiaoadapter.notifyDataSetChanged();
        }

        @Override
        public void error(Result result) {

        }
    }
}
