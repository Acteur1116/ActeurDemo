package com.acteur.acteurdemo.modle.Api;

import android.content.Context;

import com.acteur.acteurdemo.URLConfig;
import com.acteur.acteurdemo.enums.ClientType;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ${Riven} on ${rabbit}.
 */
public class ApiManager {

    private RetrofitService mDailyApi;
    private static ApiManager sApiManager;

    public static ApiManager getInstence() {
        if (sApiManager == null) {
            synchronized (ApiManager.class) {
                if (sApiManager == null) {
                    sApiManager = new ApiManager();
                }
            }
        }
        return sApiManager;
    }

    /**
     * 封装网络请求
     */
    public RetrofitService getDataService() {
        OkHttpClient client = new OkHttpClient.Builder()
                //添加应用拦截器
                .addInterceptor(new HttpInterceptor())
                //添加网络拦截器
                .addNetworkInterceptor(new OkhttpInterceptor())
                .build();
        if (mDailyApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(URLConfig.BaseUrl)
                    //将client与retrofit关联
                    .client(client)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            mDailyApi = retrofit.create(RetrofitService.class);
        }
        return mDailyApi;
    }
}