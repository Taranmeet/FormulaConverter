package com.tex.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.tex.R;
import com.tex.base.BaseActivity;
import com.tex.databinding.SplashActivityBinding;
import com.tex.viewmodel.SplashViewModel;

public class SplashActivity extends BaseActivity {

    /**
     * Binding for splash ui.
     */
    private SplashActivityBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideActionBar();
        mBinding = DataBindingUtil.setContentView(this, R.layout.splash_activity);
        SplashViewModel viewModel = new ViewModelProvider(this).get(SplashViewModel.class);

        viewModel.startCountDown().observe(this, aVoid -> {
            // start next activity
            Intent intent = new Intent(this, FormulaActivity.class);
            startActivity(intent);
            // end current activity
            finish();
        });

    }
}
