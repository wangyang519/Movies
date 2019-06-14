package com.example.bawei.com.movies.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bawei.com.movies.R;
import com.example.bawei.com.movies.bean.Result;
import com.example.bawei.com.movies.bean.ResultBean;
import com.example.bawei.com.movies.bean.UserInfo;
import com.example.bawei.com.movies.core.DataCall;

import com.example.bawei.com.movies.dao.DaoMaster;
import com.example.bawei.com.movies.dao.UserInfoDao;
import com.example.bawei.com.movies.presenter.LoginPresenter;
import com.example.bawei.com.movies.util.EncryptUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements DataCall<ResultBean> {

    private static final String TAG = "LoginActivity";

    @BindView(R.id.register)
    TextView mViewRegister;
    @BindView(R.id.phone)
    EditText mTextPhone;
    @BindView(R.id.pwd)
    EditText mTextPwd;
    @BindView(R.id.login)
    Button mButtonLogin;
    @BindView(R.id.remember)
    CheckBox mBoxRemember;

    private LoginPresenter mPresenter;
    private UserInfoDao mDao;
    private SharedPreferences mConfig;
    private UserInfo mInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        mPresenter = new LoginPresenter(this);
        mInfo = new UserInfo();

        mDao = DaoMaster.newDevSession(this, UserInfoDao.TABLENAME).getUserInfoDao();
       // List<UserInfo> list = mDao.queryBuilder().where(UserInfoDao.Properties.Status.eq(1)).list();

/*
        if (list != null && list.size() > 0) {
            startActivity(new Intent(LoginActivity.this, ShowActivity.class));
        }
        Log.i(TAG, "onCreate: --- " + list);*/


        //  跳转注册
        mViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(LoginActivity.this,"aaa",Toast.LENGTH_LONG).show();
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        //  记住密码
        mConfig = getSharedPreferences("config", MODE_PRIVATE);
        boolean key = mConfig.getBoolean("key", false);
        mBoxRemember.setChecked(key);
        if (key) {
            String name = mConfig.getString("name", "");
            String pass = mConfig.getString("pass", "");
            mTextPhone.setText(name);
            mTextPwd.setText(pass);
        }

        //  点击登录
        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String phone = mTextPhone.getText().toString().trim();
                String pwd = mTextPwd.getText().toString().trim();

                String encrypt = EncryptUtil.encrypt(pwd);

              //  Log.i(TAG, "onClick: --- " + encrypt);

                if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(LoginActivity.this, "请输入正确的手机号", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(encrypt)) {
                    Toast.makeText(LoginActivity.this, "请输入密码", Toast.LENGTH_LONG).show();
                    return;
                }
                mPresenter.requestData(phone, encrypt);

                SharedPreferences.Editor editor = mConfig.edit();
                if (mBoxRemember.isChecked()) {
                    editor.putBoolean("key", true);
                    editor.putString("name", phone);
                    editor.putString("pass", pwd);
                }
                editor.commit();
                Toast.makeText(LoginActivity.this,"点击",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void success(ResultBean result) {
        startActivity(new Intent(LoginActivity.this, ShowActivity.class));
        UserInfo userInfo = result.userInfo;
        String sessionId = result.sessionId;

        userInfo.setSessionId(sessionId);

        String s = userInfo.toString();

        Log.i(TAG, "success: --- " + s);

        mDao.insertOrReplaceInTx(userInfo);

        Toast.makeText(this, result.userId + "   " + result.sessionId + "   登陆成功！", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void error(Result result) {
        Toast.makeText(this, result.status + "   " + result.message, Toast.LENGTH_SHORT).show();
    }
}
