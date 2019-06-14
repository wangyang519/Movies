package com.example.bawei.com.movies.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bawei.com.movies.R;
import com.example.bawei.com.movies.adapter.SystemMessageAdapter;
import com.example.bawei.com.movies.bean.Result;
import com.example.bawei.com.movies.bean.SystemMessageBean;
import com.example.bawei.com.movies.bean.UserInfo;
import com.example.bawei.com.movies.core.DataCall;
import com.example.bawei.com.movies.dao.DaoMaster;
import com.example.bawei.com.movies.dao.UserInfoDao;
import com.example.bawei.com.movies.presenter.ChangeSystemMessagePresenter;
import com.example.bawei.com.movies.presenter.SystemMessageCountPresenter;
import com.example.bawei.com.movies.presenter.SystemMessagePresenter;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SystemMessageActivity extends AppCompatActivity {

    @BindView(R.id.XRecyclerView)
    XRecyclerView mXRecyclerView;
    @BindView(R.id.back)
    ImageView mViewBack;
    @BindView(R.id.title)
    TextView mViewTitle;

    private SystemMessageAdapter mAdapter;
    private SystemMessagePresenter mPresenter;
    private UserInfo mUserInfo;
    private int state = 0;
    private ChangeSystemMessagePresenter mChangeSystemMessagePresenter;
    private SystemMessageCountPresenter mSystemMessageCountPresenter;
    private int mId;
    int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_message);

        ButterKnife.bind(this);

        //  返回
        mViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mUserInfo = DaoMaster.newDevSession(this, UserInfoDao.TABLENAME).getUserInfoDao().loadAll().get(0);

        mAdapter = new SystemMessageAdapter(this);
        mPresenter = new SystemMessagePresenter(new DataCall<List<SystemMessageBean>>() {

            @Override
            public void success(List<SystemMessageBean> result) {
                if (mPresenter.getPage() == 1) {
                    mAdapter.clear();
                }
                mAdapter.addList(result);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void error(Result result) {
                Toast.makeText(SystemMessageActivity.this, result.message, Toast.LENGTH_LONG).show();
            }
        });

        mXRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mXRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mXRecyclerView.loadMoreComplete();
                mXRecyclerView.refreshComplete();
                mPresenter.requestData(true, String.valueOf(mUserInfo.getId()), mUserInfo.getSessionId());
            }

            @Override
            public void onLoadMore() {
                mXRecyclerView.loadMoreComplete();
                mXRecyclerView.refreshComplete();
                mPresenter.requestData(false, String.valueOf(mUserInfo.getId()), mUserInfo.getSessionId());
            }
        });
        mXRecyclerView.setAdapter(mAdapter);
        mXRecyclerView.refresh();

        mChangeSystemMessagePresenter = new ChangeSystemMessagePresenter(new DataCall<Result>() {
            @Override
            public void success(Result result) {
                Toast.makeText(SystemMessageActivity.this, "状态改变成功！", Toast.LENGTH_LONG).show();
            }

            @Override
            public void error(Result result) {
                Toast.makeText(SystemMessageActivity.this, result.message, Toast.LENGTH_LONG).show();
            }
        });

        //  点击改变消息状态
        mAdapter.setChangeStatus(new SystemMessageAdapter.setChangeStatus() {
            @Override
            public void setChange(View view) {
                List<SystemMessageBean> list = mAdapter.getList();
                for (int i = 0; i < list.size(); i++) {
                    mId = list.get(i).id;
                    Log.i("id", "setChange: --- " + mId);
                    mChangeSystemMessagePresenter.requestData(String.valueOf(mUserInfo.getId()), mUserInfo.getSessionId(), String.valueOf(mId));
                }
            }
        });

        mSystemMessageCountPresenter = new SystemMessageCountPresenter(new DataCall<Result>() {
            @Override
            public void success(Result result) {
                Toast.makeText(SystemMessageActivity.this, "查询成功", Toast.LENGTH_LONG).show();

                if (count == 0){
                    mViewTitle.setText("系统消息(没有未读消息)");
                }else{
                    mViewTitle.setText("系统消息(有"+ count +"未读消息)");
                }
            }

            @Override
            public void error(Result result) {
                Toast.makeText(SystemMessageActivity.this, result.message, Toast.LENGTH_LONG).show();
            }
        });

        mSystemMessageCountPresenter.requestData(String.valueOf(mUserInfo.getId()), mUserInfo.getSessionId());

    }
}
