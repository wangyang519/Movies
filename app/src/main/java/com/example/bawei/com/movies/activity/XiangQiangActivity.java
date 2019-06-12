package com.example.bawei.com.movies.activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.bawei.com.movies.R;
import com.example.bawei.com.movies.bean.Result;
import com.example.bawei.com.movies.bean.UserInfo;
import com.example.bawei.com.movies.bean.chaBean;
import com.example.bawei.com.movies.core.DataCall;
import com.example.bawei.com.movies.dao.DaoMaster;
import com.example.bawei.com.movies.dao.UserInfoDao;
import com.example.bawei.com.movies.presenter.DianyingChaPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    ImageView PopDown;

    PopupWindow popup_Detail;
    PopupWindow popup_Yugao;
    PopupWindow popup_Juzhao;
    PopupWindow popup_YP;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xiang_qiang);
        ButterKnife.bind(this);

        userInfo = DaoMaster.newDevSession(this, UserInfoDao.TABLENAME).getUserInfoDao().loadAll().get(0);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");

        // Toast.makeText(this, id, Toast.LENGTH_LONG).show();
        DianyingChaPresenter dianyingChaPresenter = new DianyingChaPresenter(new Myxing());
        dianyingChaPresenter.requestData(id);




        xiangqingFanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
                break;
            case R.id.btn_juzhao:
                break;
            case R.id.btn_yingping:
                break;
        }
    }

    private void popup_XiangQing(View view_pop_detail) {


        PopDown = view_pop_detail.findViewById(R.id.Pop_down);


        //关闭 详情
        PopDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup_Detail.dismiss();
            }
        });

    }


    class Myxing implements DataCall<chaBean> {

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
