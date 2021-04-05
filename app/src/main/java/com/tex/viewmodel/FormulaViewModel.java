package com.tex.viewmodel;

import android.content.Intent;
import android.net.Uri;
import android.provider.SearchRecentSuggestions;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tex.contentprovider.SuggestionContentProvider;
import com.tex.repo.DataRepo;
import com.tex.repo.localrepo.DBRepoFactory;
import com.tex.repo.localrepo.models.FormulaModel;
import com.tex.utils.FormulaApplication;
import com.tex.utils.Utils;

import java.io.File;

public class FormulaViewModel extends ViewModel {

    public MutableLiveData<Uri> mImageLiveData = new MutableLiveData<>();

    public MutableLiveData<FormulaModel> mErrorModel = new MutableLiveData<>();

    private boolean isCachingEnabled = false;

    private Uri mImageUri = null;

    public MutableLiveData<Uri> checkFormula(String iQuery) {
            DataRepo.checkFormula(Utils.trimWhiteSpaces(iQuery)).observeForever(formulaModel -> {
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
                } else if (formulaModel != null && !formulaModel.isInternetIssue) {
                    formulaModel.isBadRequest = true;
                    mErrorModel.postValue(formulaModel);
                } else if (formulaModel != null && formulaModel.isInternetIssue) {
                    mErrorModel.postValue(formulaModel);
                } else{
                    FormulaModel failModel = new FormulaModel();
                    failModel.mFormula = Utils.trimWhiteSpaces(iQuery);
                    failModel.isBadRequest = true;
                    mErrorModel.postValue(failModel);
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

    /**
     * Method to save recent query for future suggestions.
     *
     * @param query Query to be saved.
     */
    public void saveSuggestion(String query) {
        SearchRecentSuggestions suggestions = new SearchRecentSuggestions(FormulaApplication.mContext,
                SuggestionContentProvider.AUTHORITY, SuggestionContentProvider.MODE);
        suggestions.saveRecentQuery(query, null);
    }
}
