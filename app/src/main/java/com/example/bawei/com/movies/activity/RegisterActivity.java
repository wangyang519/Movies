package com.example.bawei.com.movies.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bawei.com.movies.R;
import com.example.bawei.com.movies.bean.Result;
import com.example.bawei.com.movies.core.DataCall;
import com.example.bawei.com.movies.presenter.RegisterPresenter;
import com.example.bawei.com.movies.util.EncryptUtil;
import com.example.bawei.com.movies.util.MD5Util;
import com.example.bawei.com.movies.util.StringUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends AppCompatActivity implements DataCall<Result> {

    @BindView(R.id.name)
    EditText mTextName;
    @BindView(R.id.sex)
    EditText mTextSex;
    @BindView(R.id.date)
    EditText mTextDate;
    @BindView(R.id.phone)
    EditText mTextPhone;
    @BindView(R.id.email)
    EditText mTextMail;
    @BindView(R.id.pwd)
    EditText mTextPwd;
    @BindView(R.id.pwd2)
    EditText mTextPwd2;
    @BindView(R.id.register)
    Button mButtonRegister;

    private RegisterPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ButterKnife.bind(this);
        mPresenter = new RegisterPresenter(this);

        //  点击注册
        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = mTextName.getText().toString().trim();
                String sex = mTextSex.getText().toString().trim();
                String date = mTextDate.getText().toString().trim();
                String phone = mTextPhone.getText().toString().trim();
                String email = mTextMail.getText().toString().trim();
                String pwd = mTextPwd.getText().toString().trim();
                String pwd2 = mTextPwd2.getText().toString().trim();

                String encrypt = EncryptUtil.encrypt(pwd);
                String encrypt2 = EncryptUtil.encrypt(pwd2);

                if (name.isEmpty() || phone.isEmpty() || pwd.isEmpty() || sex.isEmpty() || date.isEmpty() || email.isEmpty() || pwd2.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "注册失败！输入框不能为空", Toast.LENGTH_LONG).show();
                    return;
                }

                if (!StringUtil.isPhone(phone)){
                    Toast.makeText(RegisterActivity.this,"注册失败！请输入正确的手机号。",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (encrypt.length() < 6 && encrypt != encrypt2){
                    Toast.makeText(RegisterActivity.this,"注册失败！密码不能少于6位",Toast.LENGTH_SHORT).show();
                    return;
                }

                mPresenter.requestData(name,sex,date,phone,email,encrypt,encrypt2);
            }
        });
    }

    @Override
    public void success(Result result) {
        Toast.makeText(this,"你很棒棒呦，注册成功啦！",Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void error(Result result) {
        Toast.makeText(this,result.message + "   " + result.status,Toast.LENGTH_SHORT).show();
    }
}
