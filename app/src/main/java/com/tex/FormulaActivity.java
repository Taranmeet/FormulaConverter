package com.tex;

import android.Manifest;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.lifecycle.Observer;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.tex.base.BaseActivity;
import com.tex.viewmodel.FormulaViewModel;

import java.io.File;

public class FormulaActivity extends BaseActivity {

    private FormulaViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formula);
        viewModel = new FormulaViewModel();

        checkPermissionGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE, () -> {
            viewModel.setCachingEnabled(true);
        }, () -> {
            viewModel.setCachingEnabled(true);
            Toast.makeText(this, "Caching of images will not work indefinitely", Toast.LENGTH_SHORT).show();
        });

        File f = new File(getExternalFilesDir(null) + File.separator + "Future Studio Icon.png");
        findViewById(R.id.tv_go).setOnClickListener(v -> {
            viewModel.checkFormula().observe(this, checkFormulaObserver);
        }
        );

    }

    Observer<String> checkFormulaObserver = new Observer<String>() {
        @Override
        public void onChanged(String s) {
            File file = new File(s);
            Picasso.get().load(Uri.fromFile(file)).into(findViewById(R.id.iv_go), new Callback() {
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