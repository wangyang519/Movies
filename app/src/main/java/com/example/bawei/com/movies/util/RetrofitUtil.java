package com.example.bawei.com.movies.util;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

//  网络工具类
public class RetrofitUtil {

    private Retrofit mRetrofit;

    private static final RetrofitUtil ourInstance = new RetrofitUtil();

    public static RetrofitUtil getInstance() {
        return ourInstance;
    }

    private RetrofitUtil() {

        // okHttp拦截器，读取时间、响应时间
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();

        //  retrofit二次封装
        mRetrofit = new Retrofit.Builder()
                .baseUrl("http://172.17.8.100/movieApi/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public Retrofit getRetrofit() {
        return mRetrofit;
    }
}
