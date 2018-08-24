package com.acteur.acteurdemo.event;

/**
 * Created by ${Riven} on ${rabbit}.
 */
public interface OnEventLister <T>{

    void onSuccess(T response);

    void onFail(String errCode, String errMsg);
}
