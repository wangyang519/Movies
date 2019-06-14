package com.example.bawei.com.movies.presenter;

import com.example.bawei.com.movies.core.DataCall;
import com.example.bawei.com.movies.model.IRequest;

import io.reactivex.Observable;

public class SystemMessageCountPresenter extends BasePresenter {

    public SystemMessageCountPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    Observable getModel(IRequest iRequest, Object... args) {

        return iRequest.findUnreadMessageCount((String) args[0], (String) args[1]);
    }

}
