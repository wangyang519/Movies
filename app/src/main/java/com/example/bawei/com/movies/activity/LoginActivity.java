package com.example.bawei.com.movies.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

    private LoginPresenter mPresenter;
    private UserInfoDao mDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        mPresenter = new LoginPresenter(this);

        mDao = DaoMaster.newDevSession(this, UserInfoDao.TABLENAME).getUserInfoDao();

        //  跳转注册
        mViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        //  点击登录
        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String phone = mTextPhone.getText().toString().trim();
                String pwd = mTextPwd.getText().toString().trim();

                String encrypt = EncryptUtil.encrypt(pwd);

                if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(LoginActivity.this, "请输入正确的手机号", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(encrypt)) {
                    Toast.makeText(LoginActivity.this, "请输入密码", Toast.LENGTH_LONG).show();
                    return;
                }
                mPresenter.requestData(phone, encrypt);
            }
        });
    }

    @Override
    public void success(ResultBean result) {

        UserInfo userInfo = result.userInfo;

        Log.i(TAG, "success: --- " + userInfo);
        mDao.insertOrReplaceInTx(userInfo);
        Toast.makeText(this, result.userId + "   " + result.sessionId + "   登陆成功！", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void error(Result result) {
        Toast.makeText(this, result.status + "   " + result.message, Toast.LENGTH_SHORT).show();
    }
}
