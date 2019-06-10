package com.example.bawei.com.movies.activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.bawei.com.movies.R;
import com.example.bawei.com.movies.adapter.GuidePageAdapter;
import com.example.bawei.com.movies.util.SPUtil;

import java.util.ArrayList;
import java.util.List;

public class GuidePageActivity extends AppCompatActivity {

    private List<ImageView> list = new ArrayList<>();
    private static final String key = "isFirst";
    private ViewPager viewPager;
    private Button bt1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_page);

//判断是不是第一次加载
        boolean b = SPUtil.getBoolean(this, key);
        if (b) {
            //
            viewPager = findViewById(R.id.viewPager);
            bt1 = findViewById(R.id.bt1);
            //数据
            initDatas();
            //设置适配器
            GuidePageAdapter myAdapter1 = new GuidePageAdapter(list);
            viewPager.setAdapter(myAdapter1);
            //点击事件
            bt1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //将是否是第一次进入的值
                    SPUtil.saveBoolean(GuidePageActivity.this, key, false);
                    startActivity(new Intent(GuidePageActivity.this, LoginActivity.class));
                }
            });
            //viewpager监听
            viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    if (position == list.size() - 1) {
                        bt1.setVisibility(View.VISIBLE);
                    } else {
                        bt1.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onPageSelected(int position) {

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

        } else {
            startActivity(new Intent(GuidePageActivity.this, LoginActivity.class));
        }
    }

    private void initDatas() {
        ImageView img1 = new ImageView(this);
        img1.setImageResource(R.mipmap.one);

        ImageView img2 = new ImageView(this);
        img2.setImageResource(R.mipmap.two);

        ImageView img3 = new ImageView(this);
        img3.setImageResource(R.mipmap.three);

        ImageView img4 = new ImageView(this);
        img4.setImageResource(R.mipmap.four);

        list.add(img1);
        list.add(img2);
        list.add(img3);
        list.add(img4);
    }
}
