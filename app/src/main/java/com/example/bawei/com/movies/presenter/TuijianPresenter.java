package com.example.bawei.com.movies.presenter;

import com.example.bawei.com.movies.core.DataCall;
import com.example.bawei.com.movies.model.IRequest;

import io.reactivex.Observable;

public class TuijianPresenter extends BasePresenter {

    public TuijianPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    Observable getModel(IRequest iRequest, Object... args) {
        return iRequest.findRecommendCinema((long)args[0],(String)args[1],(int)args[2],(int)args[3]);
    }
}
