package com.tex;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.tex.viewmodel.FormulaViewModel;

public class FormulaActivity extends AppCompatActivity {

    private FormulaViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formula);
        viewModel = new FormulaViewModel();
        findViewById(R.id.tv_go).setOnClickListener(v -> {
            viewModel.onClick();
        });
    }
}