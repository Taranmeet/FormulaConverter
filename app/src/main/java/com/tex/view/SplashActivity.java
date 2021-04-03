package com.tex.view;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.tex.R;
import com.tex.base.BaseActivity;
import com.tex.databinding.SplashActivityBinding;

public class SplashActivity extends BaseActivity {

    private SplashActivityBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        mBinding = DataBindingUtil.setContentView(this, R.layout.splash_activity);

    }
}
