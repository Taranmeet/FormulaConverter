package com.tex.view;

import android.Manifest;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.tex.R;
import com.tex.base.BaseActivity;
import com.tex.databinding.FormulaActivityBinding;
import com.tex.viewmodel.FormulaViewModel;

import java.io.File;

public class FormulaActivity extends BaseActivity {

    /**
     * View model for formula activity.
     */
    private FormulaViewModel mViewModel;

    /**
     * Binding for formula UI.
     */
    private FormulaActivityBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.formula_activity);
        mViewModel = new FormulaViewModel();

        checkPermissionGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE, () -> {
            mViewModel.setCachingEnabled(true);
        }, () -> {
            mViewModel.setCachingEnabled(true);
            Toast.makeText(this, "Caching of images will not work indefinitely", Toast.LENGTH_SHORT).show();
        });

        mBinding.tvGo.setOnClickListener(v -> {
            mViewModel.checkFormula().observe(this, checkFormulaObserver);
        }
        );

    }

    private final Observer<String> checkFormulaObserver = new Observer<String>() {
        @Override
        public void onChanged(String s) {
            File file = new File(s);
            Picasso.get().load(Uri.fromFile(file)).into(mBinding.ivGo, new Callback() {
                @Override
                public void onSuccess() {
                    Log.e("Picaso", "Success");
                }

                @Override
                public void onError(Exception e) {
                    Log.e("Picaso", "Fail " + e);
                }
            });
        }
    };
}