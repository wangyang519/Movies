package com.example.bawei.com.movies.activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.bawei.com.movies.R;
import com.example.bawei.com.movies.adapter.Pinglunadapter;
import com.example.bawei.com.movies.bean.MyshortFilmList;
import com.example.bawei.com.movies.bean.Pinglun;
import com.example.bawei.com.movies.bean.Result;
import com.example.bawei.com.movies.bean.UserInfo;
import com.example.bawei.com.movies.bean.XiangBean;
import com.example.bawei.com.movies.bean.chaBean;
import com.example.bawei.com.movies.core.DataCall;
import com.example.bawei.com.movies.dao.DaoMaster;
import com.example.bawei.com.movies.dao.UserInfoDao;
import com.example.bawei.com.movies.presenter.Dianpinglun;
import com.example.bawei.com.movies.presenter.DianyingChaPresenter;
import com.example.bawei.com.movies.presenter.XiangQingPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

public class XiangQiangActivity extends AppCompatActivity {

    @BindView(R.id.xiangqing_name)
    TextView xiangqingName;
    @BindView(R.id.xiangqing_imageview)
    ImageView xiangqingImageview;
    @BindView(R.id.xiangqing_fanhui)
    Button xiangqingFanhui;
    @BindView(R.id.xiangqing_goupiao)
    Button xiangqingGoupiao;

    UserInfo userInfo;
    @BindView(R.id.btn_xaingqing)
    Button btnXaingqing;
    @BindView(R.id.btn_yugao)
    Button btnYugao;
    @BindView(R.id.btn_juzhao)
    Button btnJuzhao;
    @BindView(R.id.btn_yingping)
    Button btnYingping;
    ImageView PopDown ,Yudaodoawn,juzhaodown,yingpingdown;

    PopupWindow popup_Detail;
    PopupWindow popup_Yugao;
    PopupWindow popup_Juzhao;
    PopupWindow popup_YP;
    RecyclerView recyclerView_yingping;

    TextView text_xiang,text_jianjie,text_daoyan,text_shi,text_chan;

    ImageView imageView_xiangqing;
    private String id;
    private Pinglunadapter pinglunadapter;


    private JZVideoPlayerStandard videoplayer;
    private SensorManager sensorManager;
    private JZVideoPlayer.JZAutoFullscreenListener jzAutoFullscreenListener;
    private MyshortFilmList myshortFilmList;
    private RecyclerView juzhao_recyview;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xiang_qiang);
        ButterKnife.bind(this);
        xiangqingFanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        xiangqingGoupiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(XiangQiangActivity.this,LiebiaoActivity.class);
                startActivity(intent);
            }
        });

        userInfo = DaoMaster.newDevSession(this, UserInfoDao.TABLENAME).getUserInfoDao().loadAll().get(0);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");

        DianyingChaPresenter dianyingChaPresenter = new DianyingChaPresenter(new Mycha());
        dianyingChaPresenter.requestData(userInfo.getId(),userInfo.getSessionId(), id);

    }

    //详情
    class  Myxiang implements DataCall<XiangBean>{
        @Override
        public void success(XiangBean result) {
            myshortFilmList = result.shortFilmList.get(0);

            text_jianjie.setText(result.summary);
           text_xiang.setText(result.movieTypes);
           text_daoyan.setText(result.director);
           text_shi.setText(result.duration);
           text_chan.setText(result.placeOrigin);
           Glide.with(XiangQiangActivity.this).load(result.imageUrl).into(imageView_xiangqing);



        }

        @Override
        public void error(Result result) {

        }
    }
    @OnClick({R.id.btn_xaingqing, R.id.btn_yugao, R.id.btn_juzhao, R.id.btn_yingping})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_xaingqing:
                View view_pop_detail = View.inflate(XiangQiangActivity.this, R.layout.xiangqing_item, null);
                popup_XiangQing(view_pop_detail);
                popup_Detail = new PopupWindow(view_pop_detail, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                popup_Detail.setBackgroundDrawable(new BitmapDrawable());
                popup_Detail.setOutsideTouchable(true);
                popup_Detail.setFocusable(true);
                popup_Detail.showAtLocation(view_pop_detail, Gravity.CENTER_HORIZONTAL, 0, 100);
                break;
            case R.id.btn_yugao:
                //获取到预告
                View view_pop_yg = View.inflate(XiangQiangActivity.this, R.layout.yugao_item, null);
                popup_YuGao(view_pop_yg);
                popup_Yugao = new PopupWindow(view_pop_yg, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                popup_Yugao.setBackgroundDrawable(new BitmapDrawable());
                popup_Yugao.setOutsideTouchable(true);
                popup_Yugao.setFocusable(true);
                popup_Yugao.showAtLocation(view_pop_yg, Gravity.CENTER_HORIZONTAL, 0, 100);
                break;
            case R.id.btn_juzhao:
                View view_pop_jz = View.inflate(this, R.layout.juzhao_item, null);
                popup_JZ(view_pop_jz);
                popup_Juzhao = new PopupWindow(view_pop_jz, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                popup_Juzhao.setBackgroundDrawable(new BitmapDrawable());
                popup_Juzhao.setOutsideTouchable(true);
                popup_Juzhao.setFocusable(true);
                popup_Juzhao.showAtLocation(view_pop_jz, Gravity.CENTER_HORIZONTAL, 0, 100);
                break;
            case R.id.btn_yingping:
                View view_pop_yp = View.inflate(this, R.layout.yingping_item, null);
                popup_yp(view_pop_yp);
                popup_YP = new PopupWindow(view_pop_yp, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                popup_YP.setBackgroundDrawable(new BitmapDrawable());
                popup_YP.setOutsideTouchable(true);
                popup_YP.setFocusable(true);
                popup_YP.showAtLocation(view_pop_yp, Gravity.CENTER_HORIZONTAL, 0, 100);
                break;
        }
    }


    //影评
    private void popup_yp(View view_pop_yp) {

        recyclerView_yingping=view_pop_yp.findViewById(R.id.yingpingleft_recy);
        yingpingdown=view_pop_yp.findViewById(R.id.yingping_finish);
        Dianpinglun   dianpinglun = new Dianpinglun(new Mylun());
        dianpinglun.requestData("1",1,20);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView_yingping.setLayoutManager(linearLayoutManager);

        pinglunadapter = new Pinglunadapter(this);
        recyclerView_yingping.setAdapter(pinglunadapter);



        yingpingdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup_YP.dismiss();
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

    //剧照
    private void popup_JZ(View view_pop_jz) {

        juzhaodown=view_pop_jz.findViewById(R.id.juzhao_down);
        juzhao_recyview=view_pop_jz.findViewById(R.id.popw_juzhao_RecyclerView);


        juzhaodown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup_Juzhao.dismiss();
            }
        });
    }

    // 预告 点击事件
    private void popup_YuGao(View view_pop_yg) {

        Yudaodoawn=view_pop_yg.findViewById(R.id.yugao_dowm);
        videoplayer=view_pop_yg.findViewById(R.id.yudao_video);

        //用于实现重力感应下切换横竖屏
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        jzAutoFullscreenListener = new JZVideoPlayer.JZAutoFullscreenListener();

        //将缩略图的scaleType设置为FIT_XY（图片全屏）
        videoplayer.thumbImageView.setScaleType(ImageView.ScaleType.FIT_XY);

        //设置容器内播放器高,解决黑边（视频全屏）
        JZVideoPlayer.setVideoImageDisplayType(JZVideoPlayer.VIDEO_IMAGE_DISPLAY_TYPE_FILL_PARENT);

/*
        videoplayer.setUp("http://172.17.8.100/video/movie/jhen/jhen1.mp4"
                , JZVideoPlayerStandard.SCROLL_AXIS_HORIZONTAL);*/


        Yudaodoawn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              popup_Yugao.dismiss();
            }
        });

    }

    //详情 点击事件
    private void popup_XiangQing(View view_pop_detail) {
        XiangQingPresenter xiangQingPresenter = new XiangQingPresenter(new Myxiang());
        xiangQingPresenter.requestData(id);

        PopDown = view_pop_detail.findViewById(R.id.Pop_down);
        text_xiang=view_pop_detail.findViewById(R.id.xiangqing_leixing);
        imageView_xiangqing=view_pop_detail.findViewById(R.id.dialog_detalis_sdvone);
        text_jianjie=view_pop_detail.findViewById(R.id.xiangqing_plot);
        text_daoyan=view_pop_detail.findViewById(R.id.xaingqing_daoyan);
        text_shi=view_pop_detail.findViewById(R.id.dialog_detalis_shichang);
        text_chan=view_pop_detail.findViewById(R.id.dialog_detalis_chandi);
        //关闭 详情
        PopDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup_Detail.dismiss();
            }
        });

    }


    class Mycha implements DataCall<chaBean> {

        @Override
        public void success(chaBean result) {
            xiangqingName.setText(result.name);
            Glide.with(XiangQiangActivity.this).load(result.imageUrl).into(xiangqingImageview);
        }

        @Override
        public void error(Result result) {

        }
    }

}
