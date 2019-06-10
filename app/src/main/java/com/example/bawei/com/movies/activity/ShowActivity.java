package com.example.bawei.com.movies.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioGroup;

import com.example.bawei.com.movies.R;
import com.example.bawei.com.movies.adapter.Myadapter;
import com.example.bawei.com.movies.fragment.DIzhifrag;
import com.example.bawei.com.movies.fragment.Shouyefrag;
import com.example.bawei.com.movies.fragment.Wodefrag;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ShowActivity extends AppCompatActivity {

    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.radio_group)
    RadioGroup radioGroup;

    List<Fragment> list=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        ButterKnife.bind(this);

        list.add(new Shouyefrag());
        list.add(new DIzhifrag());
        list.add(new Wodefrag());

        Myadapter myadapter=new Myadapter(getSupportFragmentManager(),list);
        viewPager.setAdapter(myadapter);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                int id = radioGroup.getChildAt(i).getId();
                radioGroup.check(id);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.btn_1:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.btn_2:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.btn_3:
                        viewPager.setCurrentItem(2);
                        break;
                }
            }
        });

    }


}
