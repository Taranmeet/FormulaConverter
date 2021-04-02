package com.tex;

import android.Manifest;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

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
            viewModel.onClick(f).observe(this, s -> {
                Picasso.get().load(f).into((ImageView) findViewById(R.id.iv_go));
            });
        });

    }
}