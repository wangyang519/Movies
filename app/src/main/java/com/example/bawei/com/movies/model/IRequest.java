package com.example.bawei.com.movies.model;

import com.example.bawei.com.movies.bean.Result;
import com.example.bawei.com.movies.bean.ResultBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface IRequest {

    //  注册
    @FormUrlEncoded
    @POST("user/v1/registerUser")
    Observable<Result> registerUser(@Field("nickName") String nickName,
                                    @Field("sex") String sex,
                                    @Field("birthday") String birthday,
                                    @Field("phone") String phone,
                                    @Field("email") String email,
                                    @Field("pwd") String pwd,
                                    @Field("pwd2") String pwd2);

    //  登录
    @FormUrlEncoded
    @POST("user/v1/login")
    Observable<Result<ResultBean>> login(@Field("phone") String phone,
                                         @Field("pwd") String pwd);
}
