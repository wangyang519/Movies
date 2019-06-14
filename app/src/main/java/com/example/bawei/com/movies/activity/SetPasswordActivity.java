package com.example.bawei.com.movies.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bawei.com.movies.R;
import com.example.bawei.com.movies.bean.Result;
import com.example.bawei.com.movies.bean.UserInfo;
import com.example.bawei.com.movies.core.DataCall;
import com.example.bawei.com.movies.dao.DaoMaster;
import com.example.bawei.com.movies.dao.UserInfoDao;
import com.example.bawei.com.movies.presenter.SetPasswordPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SetPasswordActivity extends AppCompatActivity implements DataCall<Result> {

    @BindView(R.id.back)
    ImageView mViewBack;
    @BindView(R.id.pwd)
    TextView mViewPwd;

    private UserInfoDao mDao;
    private SetPasswordPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);

        ButterKnife.bind(this);

        mPresenter = new SetPasswordPresenter(this);

        //  返回
        mViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //  数据库
        mDao = DaoMaster.newDevSession(this, UserInfoDao.TABLENAME).getUserInfoDao();
        List<UserInfo> list = mDao.queryBuilder().where(UserInfoDao.Properties.Status.eq(1)).list();

        for (int i = 0; i < list.size(); i++) {
            long id = list.get(i).id;
            String sessionId = list.get(i).sessionId;

            Log.i("aaa", "onCreate: --- " + id + "  " +sessionId);
        }

    }

    @Override
    public void success(Result result) {

    }

    @Override
    public void error(Result result) {

    }
}
