package com.tex.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tex.repo.DataRepo;

public class FormulaViewModel extends ViewModel {

    public MutableLiveData<String> hash = new MutableLiveData<>();

    private boolean isCachingEnabled = false;

    public MutableLiveData<String> checkFormula() {
        DataRepo.checkFormula("a + b = c").observeForever(wikiResponse -> {
            if (wikiResponse != null) {
                String hashString = wikiResponse.getHeaders().get("x-resource-location");
                DataRepo.downloadImage(hashString).observeForever(s -> {
                    hash.postValue(s);
                });
            } else {
                Log.e("TARAN", "TAG false");
            }
        });
        return hash;
    }

    public boolean isCachingEnabled() {
        return isCachingEnabled;
    }

    public void setCachingEnabled(boolean cachingEnabled) {
        isCachingEnabled = cachingEnabled;
    }
}
