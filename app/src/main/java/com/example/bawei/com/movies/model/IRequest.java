package com.example.bawei.com.movies.model;

import com.example.bawei.com.movies.bean.LeibiaoBean;
import com.example.bawei.com.movies.bean.Pinglun;
import com.example.bawei.com.movies.bean.MessageBean;
import com.example.bawei.com.movies.bean.Result;
import com.example.bawei.com.movies.bean.ResultBean;
import com.example.bawei.com.movies.bean.ReyingBean;
import com.example.bawei.com.movies.bean.SystemMessageBean;
import com.example.bawei.com.movies.bean.UserInfo;
import com.example.bawei.com.movies.bean.XiangBean;
import com.example.bawei.com.movies.bean.chaBean;
import com.example.bawei.com.movies.bean.fujinBean;
import com.example.bawei.com.movies.bean.mingBean;
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

    //  查询用户信息
    @GET("user/v1/verify/getUserInfoByUserId")
    Observable<Result<UserInfo>>  getUserInfoByUserId(@Header("userId") String userId,
                                                      @Header("sessionId") String sessionId);


    //根据电影ID查询电影信息
    @GET("movie/v1/findMoviesById")
    Observable<Result<chaBean>> findMoviesByIdcha(
            @Header("userId")long userId,@Header("sessionId")String sessionId,
            @Query("movieId")String movieId);

    //电影 详情
    @GET("movie/v1/findMoviesDetail")
    Observable<Result<XiangBean>> findMoviesDetail(@Query("movieId")String movieId);


    //查询影片评论
    @GET("movie/v1/findAllMovieComment")
    Observable<Result<List<Pinglun>>> dianpinglun(@Query("movieId")String movieId,
                                                  @Query("page") int page,
                                                  @Query("count") int count
                                   );

    //根据电影ID查询当前排片该电影的影院列表
    @GET("movie/v1/findCinemasListByMovieId")
    Observable<Result<List<LeibiaoBean>>> findcinemaLeibiao(@Query("movieId")String cinemaId);



    //根据电影ID和影院ID查询电影排期列表
     @GET("movie/v1/findMovieScheduleList")
    Observable<Result<List<mingBean>>> finmingxi(@Query("cinemasId")int cinemasId,
                                 @Query("movieId")int movieId
                                 );




    //  重置密码
    @GET("user/v1/verify/modifyUserPwd")
    Observable<Result> modifyUserPwd(@Header("userId") String userId,
                                     @Header("sessionId") String sessionId,
                                     @Query("oldPwd") String oldPwd,
                                     @Query("newPwd") String newPwd,
                                     @Query("newPwd2") String newPwd2);

    //  签到
    @GET("user/v1/verify/userSignIn")
    Observable<Result> userSignIn(@Header("userId") String userId,
                                  @Header("sessionId") String sessionId);

    //  消息列表
    @GET("tool/v1/verify/findAllSysMsgList?count=10")
    Observable<Result<List<SystemMessageBean>>> findAllSysMsgList(@Header("userId") String userId,
                                                                  @Header("sessionId") String sessionId,
                                                                  @Query("page") String page);

    //  系统消息读取状态修改
    @GET("tool/v1/verify/changeSysMsgStatus")
    Observable<Result> changeSysMsgStatus(@Header("userId") String userId,
                                          @Header("sessionId") String sessionId,
                                          @Query("id") String id);

    //  查询用户当前未读消息数量
    @GET("tool/v1/verify/findUnreadMessageCount")
    Observable<Result> findUnreadMessageCount(@Header("userId") String userId,
                                              @Header("sessionId") String sessionId);
}
