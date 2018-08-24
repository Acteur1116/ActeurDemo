package com.acteur.acteurdemo.modle.Api;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ${Riven} on ${rabbit}.
 */
public class HttpInterceptor implements Interceptor {

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();
        //打印请求链接
        String TAG_REQUEST = "request";
        Log.e(TAG_REQUEST, request.url().toString());
        Response response = chain.proceed(request);
        //打印返回的message
        return response;
    }
}
