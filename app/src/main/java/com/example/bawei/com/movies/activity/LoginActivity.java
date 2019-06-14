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
    private String mPhone;
    private String mEncrypt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        mPresenter = new LoginPresenter(this);

        mDao = DaoMaster.newDevSession(this, UserInfoDao.TABLENAME).getUserInfoDao();
        List<UserInfo> list = mDao.queryBuilder().where(UserInfoDao.Properties.Status.eq(1)).list();

        if (list != null && list.size() > 0) {
            startActivity(new Intent(LoginActivity.this, ShowActivity.class));
        }

        //  跳转注册
        mViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

                mPhone = mTextPhone.getText().toString().trim();
                String pwd = mTextPwd.getText().toString().trim();

                mEncrypt = EncryptUtil.encrypt(pwd);

                Log.i(TAG, "onClick: --- " + mEncrypt);

                if (TextUtils.isEmpty(mPhone)) {
                    Toast.makeText(LoginActivity.this, "请输入正确的手机号", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(mEncrypt)) {
                    Toast.makeText(LoginActivity.this, "请输入密码", Toast.LENGTH_LONG).show();
                    return;
                }


                SharedPreferences.Editor editor = mConfig.edit();
                if (mBoxRemember.isChecked()) {
                    editor.putBoolean("key", true);
                    editor.putString("name", mPhone);
                    editor.putString("pass", pwd);
                }
                editor.commit();

                mPresenter.requestData(mPhone, mEncrypt);



                Log.i("encrypt", "onClick: --- " + mEncrypt);

            }
        });
    }

    @Override
    public void success(ResultBean result) {

        UserInfo userInfo = result.userInfo;
        String sessionId = result.sessionId;

        userInfo.setSessionId(sessionId);
        userInfo.setStatus(1);

        String toString = userInfo.toString();


        
        mDao.insertOrReplaceInTx(userInfo);
        Log.i("aaa", "success: " + mDao.loadAll());

        Toast.makeText(this, result.userId + "   " + result.sessionId + "   登陆成功！", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(LoginActivity.this, ShowActivity.class));
        finish();
    }

    @Override
    public void error(Result result) {
        Toast.makeText(this, result.status + "   " + result.message, Toast.LENGTH_SHORT).show();
    }
}
