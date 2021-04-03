package com.tex.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tex.repo.DataRepo;
import com.tex.repo.localrepo.DBRepoFactory;
import com.tex.utils.Utils;

public class FormulaViewModel extends ViewModel {

    public MutableLiveData<String> hash = new MutableLiveData<>();

    private boolean isCachingEnabled = false;

    public MutableLiveData<String> checkFormula() {
        DataRepo.checkFormula(Utils.trimWhiteSpaces("a + b = c")).observeForever(formulaModel -> {
            if (formulaModel != null && formulaModel.mSuccess) {
                String hashString = formulaModel.mFormulaHash;
                DataRepo.downloadImage(hashString).observeForever(s -> {
                    if (formulaModel.mSuccess) {
                        DBRepoFactory.getInstance().saveFormulaToDb(formulaModel);
                    }
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
