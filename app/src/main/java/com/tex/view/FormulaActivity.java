package com.tex.view;

import android.Manifest;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.tex.R;
import com.tex.base.BaseActivity;
import com.tex.databinding.FormulaActivityBinding;
import com.tex.viewmodel.FormulaViewModel;

public class FormulaActivity extends BaseActivity {

    /**
     * View model for formula activity.
     */
    private FormulaViewModel mViewModel;

    /**
     * Binding for formula UI.
     */
    private FormulaActivityBinding mBinding;

    /**
     * Observer called when image uri is fetched.
     */
    private final Observer<Uri> mCheckFormulaObserver = new Observer<Uri>() {
        @Override
        public void onChanged(Uri iUri) {
            Picasso.get().load(iUri).into(mBinding.ivGo, new Callback() {
                @Override
                public void onSuccess() {
                    Log.e("Picaso", "Success");
                    mBinding.ivGo.setVisibility(View.VISIBLE);
                }

                @Override
                public void onError(Exception e) {
                    Log.e("Picaso", "Fail " + e);
                    mBinding.ivGo.setVisibility(View.INVISIBLE);
                }
            });
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.formula_activity);
        mViewModel = new FormulaViewModel();

        checkPermissionGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE, () -> mViewModel.setCachingEnabled(true), () -> {
            mViewModel.setCachingEnabled(false);
        });

        mBinding.etGo.setOnClickListener(v -> mViewModel
                .checkFormula()
                .observe(this, mCheckFormulaObserver)
        );

        mBinding.ivShare.setOnClickListener(v -> startActivity(mViewModel.shareImage()));

    }
}