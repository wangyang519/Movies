package com.example.bawei.com.movies.model;

import com.example.bawei.com.movies.bean.Result;
import com.example.bawei.com.movies.bean.ReyingBean;
import com.example.bawei.com.movies.bean.reBean;
import com.example.bawei.com.movies.bean.shangBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IRequest {


    // 热门 电影
    @GET("v1/findHotMovieList")
    Observable<Result<List<reBean>>> findHotMovieList(@Query("page")int page, @Query("count")int count);

    //正在 热映
    @GET("v1/findReleaseMovieList")
    Observable<Result<List<ReyingBean>>> findReleaseMovieList(@Query("page")int page, @Query("count")int count);

    //即将上演
    @GET("v1/findComingSoonMovieList")
    Observable<Result<List<shangBean>>> findComingSoonMovieLis(@Query("page")int page, @Query("count")int count);



}
