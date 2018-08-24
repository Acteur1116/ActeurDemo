package com.acteur.acteurdemo.modle.Api;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Response;
import okhttp3.ResponseBody;


import static android.content.ContentValues.TAG;

/**
 * Created by ${Riven} on ${rabbit}.
 */
public class OkhttpInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());

        Log.d(TAG, "code     =  : " + response.code());
        Log.d(TAG, "message  =  : " + response.message());
        Log.d(TAG, "protocol =  : " + response.protocol());

        if (response.body() != null && response.body().contentType() != null) {
            MediaType mediaType = response.body().contentType();
            String string = response.body().string();
            Log.d(TAG, "mediaType =  :  " + mediaType.toString());
            Log.d(TAG, "string    =  : " + string);
            ResponseBody responseBody = ResponseBody.create(mediaType, string);
            return response.newBuilder().body(responseBody).build();
        } else {
            return response;
        }
    }
}
