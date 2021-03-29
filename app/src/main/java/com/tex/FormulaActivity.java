package com.tex;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.tex.viewmodel.FormulaViewModel;

public class FormulaActivity extends AppCompatActivity {

    private FormulaViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formula);
        viewModel = new FormulaViewModel();
        findViewById(R.id.tv_go).setOnClickListener(v -> {
            viewModel.onClick().observe(this, s -> {
                Picasso.get().load("https://en.wikipedia.org/api/rest_v1/media/math/render/png/" + s).into(findViewById(R.id.iv_go), new Callback() {
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