package com.example.bawei.com.movies.presenter;

import com.example.bawei.com.movies.core.DataCall;
import com.example.bawei.com.movies.model.IRequest;

import io.reactivex.Observable;

public class RegisterPresenter extends BasePresenter {

    public RegisterPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    Observable getModel(IRequest iRequest, Object... args) {
        return iRequest.registerUser((String)args[0],(String)args[1],(String)args[2],(String)args[3],(String)args[4],(String)args[5],(String)args[6]);
    }
}
