package com.example.bawei.com.movies.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.bawei.com.movies.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SetPasswordActivity extends AppCompatActivity {

    @BindView(R.id.back)
    ImageView mViewBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);

        ButterKnife.bind(this);

        mViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
