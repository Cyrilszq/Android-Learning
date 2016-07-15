package com.example.cyril.mvpdemo.core;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Cyril on 2016/7/15.
 */
public class RetrofitSington {
    private static Retrofit mRetrofit = null;
    private static Api apiService = null;

    public static void initRetrofit() {
        mRetrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(Api.HOST)
                .build();
        apiService = mRetrofit.create(Api.class);
    }

    public static Api getApiService() {
        if (apiService != null){
            return apiService;
        }
        initRetrofit();
        return getApiService();
    }
}
