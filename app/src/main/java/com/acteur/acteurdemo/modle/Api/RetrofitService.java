package com.acteur.acteurdemo.modle.Api;

import com.acteur.acteurdemo.modle.Bean.DataBean;
import com.acteur.acteurdemo.modle.Bean.DataBeanH;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by ${Riven} on ${rabbit}.
 */
public interface RetrofitService {
    //使用retrofit接口定义
    @GET("news/latest")
    Call<DataBeanH> getDataBeanRetrofitOnly();

    //使用retrofit+RxAndroid的接口定义
    @GET("news/latest")
    Observable<DataBeanH> getDataBean();

    @GET("/")
    Observable<ResponseBody> getLoginTest();

}
