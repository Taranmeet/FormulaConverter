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
import com.tex.utils.ProjectConstants;
import com.tex.utils.Utils;

import java.io.File;

public class FormulaViewModel extends ViewModel {

    /**
     * Live data to observe check formula responses.
     */
    public MutableLiveData<Uri> mImageLiveData = new MutableLiveData<>();

    /**
     * Live data to observe when cache permission is not granted and we directly call server using picasso.
     */
    public MutableLiveData<String> mImageDirectAccessLiveData = new MutableLiveData<>();

    /**
     * Live data to observe for error scenarios
     */
    public MutableLiveData<FormulaModel> mErrorModel = new MutableLiveData<>();

    /**
     * Boolean providing situational awareness for cache related permissions.
     */
    private boolean isCachingEnabled = false;

    /**
     * Uri of latest loaded image.
     */
    private Uri mImageUri = null;

    /**
     * Url of latest loaded image available when cache is not present.
     */
    private String mImageURL = null;

    /**
     * Method to check if formula is in tex format then download its image from servers.
     *
     * @param iQuery Formula to be checked.
     * @return Live data to be monitored for server response.
     */
    public MutableLiveData<Uri> checkFormula(String iQuery) {
        DataRepo.checkFormula(Utils.trimWhiteSpaces(iQuery)).observeForever(formulaModel -> {
            if (formulaModel != null && formulaModel.mSuccess) {
                String hashString = formulaModel.mFormulaHash;
                if (isCachingEnabled) {
                    DataRepo.downloadImage(hashString).observeForever(s -> {
                        if (formulaModel.mSuccess) {
                            DBRepoFactory.getInstance().saveFormulaToDb(formulaModel);
                        }
                        File file = new File(s);
                        mImageUri = Uri.fromFile(file);
                        mImageLiveData.postValue(mImageUri);
                    });
                } else {
                    mImageURL = ProjectConstants.BASE_IMAGE_URL + hashString;
                    mImageDirectAccessLiveData.postValue(mImageURL);
                }
            } else if (formulaModel != null && !formulaModel.isInternetIssue) {
                formulaModel.isBadRequest = true;
                mErrorModel.postValue(formulaModel);
            } else if (formulaModel != null && formulaModel.isInternetIssue) {
                mErrorModel.postValue(formulaModel);
            } else {
                FormulaModel failModel = new FormulaModel();
                failModel.mFormula = Utils.trimWhiteSpaces(iQuery);
                failModel.isBadRequest = true;
                mErrorModel.postValue(failModel);
            }
        });
        return mImageLiveData;
    }

    /**
     * Method to update view model if cache can be enabled.
     *
     * @param cachingEnabled true when permission is granted
     */
    public void setCachingEnabled(boolean cachingEnabled) {
        isCachingEnabled = cachingEnabled;
    }

    /**
     * Method to create share image intent.
     *
     * @return Intent created for share image.
     */
    public Intent shareImage() {
        if (isCachingEnabled) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_STREAM, mImageUri);
            sendIntent.setType("image/png");
            return Intent.createChooser(sendIntent, "Send your image");
        } else {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, mImageURL);
            sendIntent.setType("image/png");
            return Intent.createChooser(sendIntent, "Send your image");
        }
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
