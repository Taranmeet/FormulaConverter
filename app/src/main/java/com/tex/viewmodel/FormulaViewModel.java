package com.tex.viewmodel;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tex.repo.DataRepo;
import com.tex.repo.localrepo.DBRepoFactory;
import com.tex.utils.Utils;

import java.io.File;

public class FormulaViewModel extends ViewModel {

    public MutableLiveData<Uri> mImageLiveData = new MutableLiveData<>();

    private boolean isCachingEnabled = false;

    private Uri mImageUri = null;

    public MutableLiveData<Uri> checkFormula() {
        DataRepo.checkFormula(Utils.trimWhiteSpaces("a + b = c")).observeForever(formulaModel -> {
            if (formulaModel != null && formulaModel.mSuccess) {
                String hashString = formulaModel.mFormulaHash;
                DataRepo.downloadImage(hashString).observeForever(s -> {
                    if (formulaModel.mSuccess) {
                        DBRepoFactory.getInstance().saveFormulaToDb(formulaModel);
                    }
                    File file = new File(s);
                    mImageUri = Uri.fromFile(file);
                    mImageLiveData.postValue(mImageUri);
                });
            } else {
                Log.e("TARAN", "TAG false");
            }
        });
        return mImageLiveData;
    }

    public boolean isCachingEnabled() {
        return isCachingEnabled;
    }

    public void setCachingEnabled(boolean cachingEnabled) {
        isCachingEnabled = cachingEnabled;
    }

    /**
     * Method to create share image intent.
     *
     * @return Intent created for share image.
     */
    public Intent shareImage() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_STREAM, mImageUri);
        sendIntent.setType("image/png");
        return Intent.createChooser(sendIntent, "Send your image");
    }
}
