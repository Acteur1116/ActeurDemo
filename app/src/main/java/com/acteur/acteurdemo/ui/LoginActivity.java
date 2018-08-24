package com.acteur.acteurdemo.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.acteur.acteurdemo.ui.BindView.LoginInterface;

/**
 * Created by ${Riven} on ${rabbit}.
 */
public class LoginActivity extends Activity implements LoginInterface {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {

    }
}
