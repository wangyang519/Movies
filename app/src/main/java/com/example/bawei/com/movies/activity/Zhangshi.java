package com.example.bawei.com.movies.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.example.bawei.com.movies.R;
import com.example.bawei.com.movies.adapter.MovieAdapter;
import com.example.bawei.com.movies.fragment.movie.JijiangMovieFragment;
import com.example.bawei.com.movies.fragment.movie.RemenMovieFragment;
import com.example.bawei.com.movies.fragment.movie.ZhengMovieFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Zhangshi extends AppCompatActivity {

    @BindView(R.id.moive_radiogroup)
    RadioGroup moiveRadiogroup;
    @BindView(R.id.moive_viewpager)
    ViewPager moiveViewpager;
    @BindView(R.id.moive_return)
    ImageView moiveReturn;

    List<Fragment> list=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhangshi);
        ButterKnife.bind(this);

        list.add(new RemenMovieFragment());
        list.add(new ZhengMovieFragment());
        list.add(new JijiangMovieFragment());

        MovieAdapter movieAdapter=new MovieAdapter(getSupportFragmentManager(),list);
        moiveViewpager.setAdapter(movieAdapter);

        moiveViewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                int id = moiveRadiogroup.getChildAt(i).getId();
                moiveRadiogroup.check(id);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        moiveRadiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.btnnnn_1:
                        moiveViewpager.setCurrentItem(0);
                        break;
                    case R.id.btnnnn_2:
                        moiveViewpager.setCurrentItem(1);
                        break;
                    case R.id.btnnnn_3:
                        moiveViewpager.setCurrentItem(2);
                        break;
                }
            }
        });


        moiveReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
