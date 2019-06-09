package com.example.bawei.com.movies.core;

import com.example.bawei.com.movies.bean.Result;

public interface DataCall<T> {

    void success(T result);
    void error(Result result);
}
