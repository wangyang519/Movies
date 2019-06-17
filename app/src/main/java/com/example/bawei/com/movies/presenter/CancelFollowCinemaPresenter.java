package com.example.bawei.com.movies.presenter;

import com.example.bawei.com.movies.core.DataCall;
import com.example.bawei.com.movies.model.IRequest;

import io.reactivex.Observable;

public class CancelFollowCinemaPresenter extends BasePresenter {

    public CancelFollowCinemaPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    Observable getModel(IRequest iRequest, Object... args) {
        return iRequest.cancelFollowCinema((String) args[0], (String) args[1], (String) args[2]);
    }
}
