package com.example.bawei.com.movies.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bawei.com.movies.R;
import com.example.bawei.com.movies.bean.Result;
import com.example.bawei.com.movies.bean.ResultBean;
import com.example.bawei.com.movies.bean.UserInfo;
import com.example.bawei.com.movies.core.DataCall;
import com.example.bawei.com.movies.dao.DaoMaster;
import com.example.bawei.com.movies.dao.UserInfoDao;
import com.example.bawei.com.movies.presenter.MessagePresenter;
import com.example.bawei.com.movies.util.DateUtils;
import com.example.bawei.com.movies.util.StatusBarUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.bawei.com.movies.util.DateUtils.DATE_PATTERN;


public class MessageActivity extends AppCompatActivity implements DataCall<UserInfo> {

    private static final String TAG = "MessageActivity";

    @BindView(R.id.back)
    ImageView mViewBack;
    @BindView(R.id.name)
    TextView mViewName;
    @BindView(R.id.sex)
    TextView mViewSex;
    @BindView(R.id.date)
    TextView mViewDate;
    @BindView(R.id.phone)
    TextView mViewPhone;
    @BindView(R.id.r3)
    RelativeLayout mRelativeLayout;

    private UserInfo mInfo;
    private MessagePresenter mPresenter;
    private int sex = 1;
    private String mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        ButterKnife.bind(this);
        StatusBarUtil.setTransparent(this);

        mPresenter = new MessagePresenter(this);

        mViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mInfo = DaoMaster.newDevSession(this, UserInfoDao.TABLENAME).getUserInfoDao().loadAll().get(0);

        Log.i(TAG, "onCreate: --- " + mInfo.toString());

        String nickName = mInfo.nickName;
        sex = mInfo.sex;
        long birthday = mInfo.birthday;
        try {
            mData = DateUtils.dateTransformer(birthday, DATE_PATTERN);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String phone = mInfo.phone;

        Log.i(TAG, "onCreate: --- " + nickName + "   " + birthday);

        mViewName.setText(nickName);
        if (sex == 1) {
            mViewSex.setText("男");
        }
        if (sex == 2) {
            mViewSex.setText("女");
        }

        mViewDate.setText(mData);
        mViewPhone.setText(phone);

        ResultBean resultBean = new ResultBean();


        mPresenter.requestData(String.valueOf(resultBean.userId), resultBean.sessionId);

        mRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MessageActivity.this, SetPasswordActivity.class));
            }
        });

    }


    @Override
    public void success(UserInfo result) {

        String nickName = result.nickName;

        Toast.makeText(this, nickName + "\r\n" + result.sessionId + "\r\n" + result.id, Toast.LENGTH_LONG).show();
    }

    @Override
    public void error(Result result) {
        Toast.makeText(this, result.message, Toast.LENGTH_LONG).show();
    }
}
