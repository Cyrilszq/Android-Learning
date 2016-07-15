package com.example.cyril.mvpdemo.core;

import com.example.cyril.mvpdemo.data.Article;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Cyril on 2016/7/15.
 */
public interface Api {
    String HOST = "http://gank.io/api/";
    //num第几页
    @GET("data/{category}/15/{num}")
    Observable<Article> getArticle(@Path("category")String category, @Path("num")int num);
}
