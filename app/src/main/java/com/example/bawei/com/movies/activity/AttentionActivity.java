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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AttentionActivity extends AppCompatActivity {

    @BindView(R.id.back)
    ImageView mViewBack;
    @BindView(R.id.movie)
    Button mButtonMovie;
    @BindView(R.id.cinema)
    Button mButtonCinema;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;

    private AttentionAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attention);

        ButterKnife.bind(this);

        mAdapter = new AttentionAdapter(getSupportFragmentManager());
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

        mButtonMovie.setBackgroundColor(R.drawable.attention_style);

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

                List<Fragment> list = mAdapter.getList();
                for (int j = 0; j < list.size() ; j++) {
                    if (j != i){
                        mButtonMovie.setBackgroundColor(R.drawable.attention_style);
                        mButtonCinema.setBackgroundColor(Color.WHITE);
                    }else {
                        mButtonCinema.setBackgroundColor(R.drawable.attention_style);
                        mButtonMovie.setBackgroundColor(Color.WHITE);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        mButtonMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewPager.setCurrentItem(0);
            }
        });

        mButtonCinema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewPager.setCurrentItem(1);
            }
        });

    }
}
