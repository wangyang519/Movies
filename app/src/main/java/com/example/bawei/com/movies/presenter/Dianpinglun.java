package com.example.bawei.com.movies.presenter;

import com.example.bawei.com.movies.core.DataCall;
import com.example.bawei.com.movies.model.IRequest;

import io.reactivex.Observable;

public class Dianpinglun extends BasePresenter {

    public Dianpinglun(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    Observable getModel(IRequest iRequest, Object... args) {
        return iRequest.dianpinglun((String)args[0],(int)args[1],(int)args[2]);
    }
}
