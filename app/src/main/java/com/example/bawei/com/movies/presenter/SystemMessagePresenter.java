package com.example.bawei.com.movies.presenter;

import com.example.bawei.com.movies.core.DataCall;
import com.example.bawei.com.movies.model.IRequest;

import io.reactivex.Observable;

public class SystemMessagePresenter extends BasePresenter {

    private int page = 1;

    public SystemMessagePresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    Observable getModel(IRequest iRequest, Object... args) {
        boolean isRefresh = (boolean) args[0];
        if (isRefresh) {
            page = 1;
        } else {
            page++;
        }
        return iRequest.findAllSysMsgList((String) args[1], (String) args[2], String.valueOf(page));
    }

    public int getPage() {
        return page;
    }
}
