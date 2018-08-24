package com.acteur.acteurdemo.ui.BindView;

import com.acteur.acteurdemo.modle.Bean.DataBean;

import java.util.ArrayList;

/**
 * Created by ${Riven} on ${rabbit}.
 */
public interface LoginInterface {
    void showProgressBar();

    void hidProgressBar();

    void loadData();

    void getDataSuccess(ArrayList<DataBean> stories);

    void getDataFail(String errCode, String errMsg);
}
