package com.example.bawei.com.movies.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bawei.com.movies.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GoupiaoActivity extends AppCompatActivity {


    @BindView(R.id.mSelect2)
    SeatTable mSelect2;
    @BindView(R.id.mPayMoney_tv)
    TextView mPayMoneyTv;
     private  int s;
    private String price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goupiao);
        ButterKnife.bind(this);

       // EventBus.getDefault().register(this);

        Intent intent=getIntent();
        price = intent.getStringExtra("price");
        Toast.makeText(GoupiaoActivity.this, price, Toast.LENGTH_LONG).show();
        mSelect2.setSeatChecker(new SeatTable.SeatChecker() {
            @Override
            public boolean isValidSeat(int row, int column) {
                if (row == 4) {
                    return false;
                }
                return true;
            }

            @Override
            public boolean isSold(int row, int column) {
                if (row == 6 && column == 6) {
                    return true;
                }
                return false;
            }

            @Override
            public void checked(int row, int column) {
                //Log.e("tag",row+"----"+column);
               s++;
             mPayMoneyTv.setText(price);
            }

            @Override
            public void unCheck(int row, int column) {
                //Log.e("tag",row+"****"+column);
               s--;
              Log.e("tag","选中票数----"+s);
            mPayMoneyTv.setText(price);
            }

            @Override
            public String[] checkedSeatTxt(int row, int column) {
                return null;
            }

        });
        mSelect2.setMaxSelected(4);
        mSelect2.setData(10, 10);

    }
/*
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void getad(String price) {
        mPayMoneyTv.setText(price);

    }*/
}
