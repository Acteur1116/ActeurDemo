package com.acteur.acteurdemo.modle.biz;

import android.os.Handler;

import com.acteur.acteurdemo.event.OnEventLister;
import com.acteur.acteurdemo.modle.Api.ApiManager;
import com.acteur.acteurdemo.modle.Api.HttpServiceManager;
import com.acteur.acteurdemo.modle.Bean.DataBean;
import com.acteur.acteurdemo.modle.Bean.DataBeanH;
import com.google.gson.Gson;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by PandaQ on 2016/10/19.
 * email : 767807368@qq.com
 */

public class ZhihuDailyBiz {

    public void getStoryData(final String url, final OnEventLister<ArrayList<DataBean>> eventLister) {
        final Handler handler = new Handler();
        new Thread() {
            public void run() {
                try {
                    String result = HttpServiceManager.httpGet(url);
                    Gson gson = new Gson();
                    DataBeanH daily = gson.fromJson(result, DataBeanH.class);
                    final ArrayList<DataBean> stories = daily.getStories();
                    if (stories != null) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                eventLister.onSuccess(stories);
                            }
                        });
                    } else {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                eventLister.onFail("-100", "获取日报失败！");
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            eventLister.onFail("-100", "获取日报失败！");
                        }
                    });
                }
            }
        }.start();
    }

    public void getStoryDataByRetrofit(final OnEventLister<ArrayList<DataBean>> eventLister) {
        ApiManager apiManager = ApiManager.getInstence();
        Call<DataBeanH> call = apiManager.getDataService().getDataBeanRetrofitOnly();
        //发送异步请求
        call.enqueue(new Callback<DataBeanH>() {
            @Override
            public void onResponse(Call<DataBeanH> call, Response<DataBeanH> response) {
                eventLister.onSuccess(response.body().getStories());
            }

            @Override
            public void onFailure(Call<DataBeanH> call, Throwable t) {
                eventLister.onFail(t.getMessage(), "");
            }
        });
    }
}
