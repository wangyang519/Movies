package com.example.bawei.com.movies.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.example.bawei.com.movies.R;
import com.example.bawei.com.movies.adapter.ZhongAdapter;
import com.example.bawei.com.movies.fragment.fra.FujinFragment;
import com.example.bawei.com.movies.fragment.fra.TuijianFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class DIzhifrag extends Fragment {

    @BindView(R.id.zhong_group)
    RadioGroup zhongGroup;
    @BindView(R.id.view_pager_zhong)
    ViewPager viewPagerZhong;
    Unbinder unbinder;


    List<Fragment> list=new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dizhi, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        list.add(new TuijianFragment());
        list.add(new FujinFragment());

        ZhongAdapter zhongAdapter=new ZhongAdapter(getChildFragmentManager(),list);
        viewPagerZhong.setAdapter(zhongAdapter);

        viewPagerZhong.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                int id = zhongGroup.getChildAt(i).getId();
                zhongGroup.check(id);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        zhongGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId){
                    case R.id.btn_tton1:
                        viewPagerZhong.setCurrentItem(0);
                        break;

                    case R.id.btn_tton2:
                        viewPagerZhong.setCurrentItem(1);
                        break;
                }
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
