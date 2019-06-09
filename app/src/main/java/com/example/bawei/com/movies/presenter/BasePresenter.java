package com.example.bawei.com.movies.presenter;

import com.example.bawei.com.movies.bean.Result;
import com.example.bawei.com.movies.core.DataCall;
import com.example.bawei.com.movies.model.IRequest;
import com.example.bawei.com.movies.util.RetrofitUtil;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public abstract class BasePresenter {

    private DataCall mDataCall;

    public BasePresenter(DataCall dataCall) {
        this.mDataCall = dataCall;
    }

    //  请求数据
    public void requestData(Object... args) {

        IRequest iRequest = RetrofitUtil.getInstance().getRetrofit().create(IRequest.class);
        getModel(iRequest, args)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Result>() {
                    @Override
                    public void accept(Result result) throws Exception {
                        if (result.status.equals("0000")) {
                            mDataCall.success(result.result);
                        } else {
                            mDataCall.error(result);
                        }
                    }
                });
    }

    abstract Observable getModel(IRequest iRequest, Object... args);

    //  内存泄漏
    public void unBind() {
        if (mDataCall != null) {
            mDataCall = null;
        }
    }
}
