package com.example.bawei.com.movies.model;

import com.example.bawei.com.movies.bean.Result;
import com.example.bawei.com.movies.bean.ResultBean;
import com.example.bawei.com.movies.bean.ReyingBean;
import com.example.bawei.com.movies.bean.fujinBean;
import com.example.bawei.com.movies.bean.reBean;
import com.example.bawei.com.movies.bean.shangBean;
import com.example.bawei.com.movies.bean.tuijianBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

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

    // 热门 电影
    @GET("movie/v1/findHotMovieList")
    Observable<Result<List<reBean>>> findHotMovieList(
            @Header("userId")long userId,@Header("sessionId")String sessionId,
            @Query("page")int page, @Query("count")int count);

    //正在 热映
    @GET("movie/v1/findReleaseMovieList")
    Observable<Result<List<ReyingBean>>> findReleaseMovieList(
            @Header("userId")long userId,@Header("sessionId")String sessionId,
            @Query("page")int page, @Query("count")int count);

    //即将上演
    @GET("movie/v1/findComingSoonMovieList")
    Observable<Result<List<shangBean>>> findComingSoonMovieLis(
            @Header("userId")long userId,@Header("sessionId")String sessionId,
            @Query("page")int page, @Query("count")int count);


//1.查询推荐影院信息
    @GET("cinema/v1/findRecommendCinemas")
    Observable<Result<List<tuijianBean>>> findRecommendCinema(
            @Header("userId")long userId,@Header("sessionId")String sessionId,
            @Query("page")int page, @Query("count")int count);


    //附近 影院信息
    @GET("cinema/v1/findNearbyCinemas")
    Observable<Result<List<fujinBean>>> findNearbyCinemas(
            @Header("userId")long userId,@Header("sessionId")String sessionId,
            @Query("page")int page, @Query("count")int count);

}
