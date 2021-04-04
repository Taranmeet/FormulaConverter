package com.tex.view;

import android.Manifest;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

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
        setSupportActionBar(mBinding.toolbar);
        mViewModel = new FormulaViewModel();

        checkPermissionGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                () -> mViewModel.setCachingEnabled(true),
                () -> mViewModel.setCachingEnabled(false));

        mBinding.ivShare.setOnClickListener(v -> startActivity(mViewModel.shareImage()));

        mViewModel.mErrorModel.observe(this, model -> {
            if (model.isBadRequest) {
                Toast.makeText(this, "Entered string is not in Tex format", Toast.LENGTH_SHORT).show();
            } else if (model.isInternetIssue) {
                Toast.makeText(this, "Internet Issue!! Check your Network", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        checkIntent(intent);
    }

    private void checkIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);

            mViewModel
                    .checkFormula(query)
                    .observe(this, mCheckFormulaObserver);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setIconified(false);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.setSubmitButtonEnabled(true);
        searchView.setQueryRefinementEnabled(true);
        return true;
    }
}