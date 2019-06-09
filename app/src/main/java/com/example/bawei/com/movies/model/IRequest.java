package com.example.bawei.com.movies.model;

import com.example.bawei.com.movies.bean.Result;
import com.example.bawei.com.movies.bean.reBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IRequest {


    // 热门 电影
    @GET("v1/findHotMovieList")
    Observable<Result<List<reBean>>> findHotMovieList(@Query("page")int page, @Query("count")int count);

    //正在 热映
    //@GET("")

}
