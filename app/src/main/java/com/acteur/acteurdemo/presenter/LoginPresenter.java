package com.acteur.acteurdemo.presenter;

import com.acteur.acteurdemo.Constants;
import com.acteur.acteurdemo.MyApplication;
import com.acteur.acteurdemo.disklrucache.DiskCacheManager;
import com.acteur.acteurdemo.event.OnEventLister;
import com.acteur.acteurdemo.modle.Api.ApiManager;
import com.acteur.acteurdemo.modle.Bean.DataBean;
import com.acteur.acteurdemo.modle.Bean.DataBeanH;
import com.acteur.acteurdemo.modle.biz.ZhihuDailyBiz;
import com.acteur.acteurdemo.ui.BindView.LoginInterface;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ${Riven} on ${rabbit}.
 */
public class LoginPresenter extends BasePresenter{
    private ZhihuDailyBiz mDailyBiz;
    private LoginInterface loginActivity;

    public LoginPresenter(LoginInterface loginActivity,ZhihuDailyBiz mDailyBiz) {
        this.mDailyBiz=mDailyBiz;
        this.loginActivity = loginActivity;
    }

    //普通封装okhttp进行网络请求
    public void loadData() {
        loginActivity.showProgressBar();
        mDailyBiz.getStoryData("news/latest", new OnEventLister<ArrayList<DataBean>>() {
            @Override
            public void onSuccess(ArrayList<DataBean> response) {
                loginActivity.hidProgressBar();
                loginActivity.getDataSuccess(response);
            }

            @Override
            public void onFail(String errCode, String errMsg) {
                loginActivity.hidProgressBar();
                loginActivity.getDataFail(errCode, errMsg);
            }
        });
    }

    //单独只用Retrofit进行网络请求
    public void loadDataByRetrofit() {
        loginActivity.showProgressBar();
        mDailyBiz.getStoryDataByRetrofit(new OnEventLister<ArrayList<DataBean>>() {
            @Override
            public void onSuccess(ArrayList<DataBean> response) {
                loginActivity.hidProgressBar();
                loginActivity.getDataSuccess(response);
            }

            @Override
            public void onFail(String errCode, String errMsg) {
                loginActivity.hidProgressBar();
                loginActivity.getDataFail(errCode, errMsg);
            }
        });
    }

    //使用rxandroid+retrofit进行请求
    public void loadDataByRxandroidRetrofit() {
        loginActivity.showProgressBar();
        ApiManager.getInstence().getDataService()
                .getDataBean()
                .map(new Function<DataBeanH, ArrayList<DataBean> >() {
                    @Override
                    public ArrayList<DataBean> apply(@NonNull DataBeanH zhiHuDaily) throws Exception {
                        ArrayList<DataBean> stories = zhiHuDaily.getStories();
                        if (stories != null) {
                            //加载成功后将数据缓存倒本地(demo 中只有一页，实际使用时根据需求选择是否进行缓存)
                            makeCache(zhiHuDaily.getStories());
                        }
                        return stories;
                    }
                })
                //设置事件触发在非主线程
                .subscribeOn(Schedulers.io())
                //设置事件接受在UI线程以达到UI显示的目的
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<DataBean>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        //绑定观察对象，注意在界面的ondestory或者onpouse方法中调用presenter.unsubcription();
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(@NonNull ArrayList<DataBean> zhihuStories) {
                        loginActivity.getDataSuccess(zhihuStories);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        loginActivity.getDataFail("", e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        loginActivity.hidProgressBar();
                    }
                });
    }

    private void makeCache(ArrayList<DataBean> stories) {
        DiskCacheManager manager = new DiskCacheManager(MyApplication.getContext(), Constants.ZHIHUCACHE);
        manager.put(Constants.ZHIHUSTORY_KEY, stories);
    }

    public void loadCache() {
        DiskCacheManager manager = new DiskCacheManager(MyApplication.getContext(), Constants.ZHIHUCACHE);
        ArrayList<DataBean> stories = manager.getSerializable(Constants.ZHIHUSTORY_KEY);
        if (stories != null) {
            loginActivity.getDataSuccess(stories);
        } else {
            loginActivity.getDataFail("", "读取缓存失败");
        }
    }
}
