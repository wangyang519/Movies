package com.example.bawei.com.movies.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.bawei.com.movies.R;
import com.example.bawei.com.movies.bean.UserInfo;
import com.example.bawei.com.movies.dao.DaoMaster;
import com.example.bawei.com.movies.dao.UserInfoDao;
import com.example.bawei.com.movies.util.DateUtils;
import com.example.bawei.com.movies.util.StatusBarUtil;

import java.text.ParseException;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.bawei.com.movies.util.DateUtils.DATE_PATTERN;


public class MessageActivity extends AppCompatActivity {

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

    private UserInfo mDao;
    private int sex = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        ButterKnife.bind(this);
        StatusBarUtil.setTransparent(this);

        //  返回
        mViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //  数据库
        mDao = DaoMaster.newDevSession(this, UserInfoDao.TABLENAME).getUserInfoDao().loadAll().get(0);

        long id = mDao.getId();
        String sessionId = mDao.getSessionId();

        long birthday = mDao.getBirthday();
        String nickName = mDao.getNickName();
        int sex = mDao.getSex();
        String phone = mDao.getPhone();
        String headPic = mDao.getHeadPic();

        try {
            mViewDate.setText(DateUtils.dateTransformer(birthday, DATE_PATTERN));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        mViewName.setText(nickName);
        if (sex == 1) {
            mViewSex.setText("男");
        }
        if (sex == 2) {
            mViewSex.setText("女");
        }
        mViewPhone.setText(phone);


        //  重置密码
        mRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MessageActivity.this, SetPasswordActivity.class));
            }
        });

    }

}
