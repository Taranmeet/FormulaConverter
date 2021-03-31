package com.tex;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.tex.viewmodel.FormulaViewModel;

import java.io.File;

public class FormulaActivity extends AppCompatActivity {

    private FormulaViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formula);
        viewModel = new FormulaViewModel();
        File f = new File(getExternalFilesDir(null)+ File.separator + "Future Studio Icon.png");
        findViewById(R.id.tv_go).setOnClickListener(v -> {
            viewModel.onClick(f).observe(this, s -> {
                Picasso.get().load(f).into(findViewById(R.id.iv_go), new Callback() {
                    @Override
                    public void onSuccess() {
                        Log.e("TARAN", "Image loaded Success !!");
                    }

                    @Override
                    public void onError(Exception e) {
                        Log.e("TARAN", "Image loaded Failure !!");
                    }
                });
            });
        });
    }
}