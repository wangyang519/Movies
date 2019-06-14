package com.example.bawei.com.movies.activity;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.bawei.com.movies.R;
import com.example.bawei.com.movies.adapter.AttentionAdapter;
import com.example.bawei.com.movies.adapter.RccordAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RccordActivity extends AppCompatActivity {

    @BindView(R.id.back)
    ImageView mViewBack;
    @BindView(R.id.obligation)
    Button mButtonObligation;
    @BindView(R.id.finish)
    Button mButtonFinish;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;

    private RccordAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rccord);

        ButterKnife.bind(this);

        mAdapter = new RccordAdapter(getSupportFragmentManager());
        mAdapter.addFragment();
        mAdapter.notifyDataSetChanged();
        mViewPager.setAdapter(mAdapter);


        //  返回
        mViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mButtonObligation.setBackgroundColor(R.drawable.attention_style);

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

                List<Fragment> list = mAdapter.getList();
                for (int j = 0; j < list.size(); j++) {
                    if (j != i) {
                        mButtonObligation.setBackgroundColor(R.drawable.attention_style);
                        mButtonFinish.setBackgroundColor(Color.WHITE);
                    } else {
                        mButtonFinish.setBackgroundColor(R.drawable.attention_style);
                        mButtonObligation.setBackgroundColor(Color.WHITE);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        mButtonObligation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewPager.setCurrentItem(0);
            }
        });

        mButtonFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewPager.setCurrentItem(1);
            }
        });

    }
}
