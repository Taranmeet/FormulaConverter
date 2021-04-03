package com.tex.repo;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.tex.utils.FormulaApplication;
import com.tex.network.ApiResponseFactory;
import com.tex.repo.localrepo.DBRepoFactory;
import com.tex.repo.localrepo.models.FormulaModel;
import com.tex.repo.netwokrepo.NetworkRepo;
import com.tex.response.WikiResponseModel;
import com.tex.utils.LiveDataUtil;
import com.tex.utils.ProjectConstants;
import com.tex.utils.Utils;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataRepo {

    public static LiveData<FormulaModel> checkFormula(String iFormula) {
        MutableLiveData<FormulaModel> formulaModelLiveData = new MutableLiveData<>();
        LiveDataUtil.observeOnce(DBRepoFactory.getInstance().getFormula(iFormula), formulaModel -> {
            if (formulaModel != null) {
                Log.e("TARAN", "Data Already exists");
                formulaModelLiveData.postValue(formulaModel);
            } else {
                Log.e("TARAN", "Data Not available get from network");
                NetworkRepo
                        .build()
                        .checkExpression(RequestBody.create(iFormula, MediaType.parse("multipart/form-data")))
                        .enqueue(new Callback<WikiResponseModel>() {
                            @Override
                            public void onResponse(Call<WikiResponseModel> call, Response<WikiResponseModel> response) {
                                FormulaModel model = null;
                                if (response != null && response.isSuccessful()) {
                                    model = FormulaModel
                                            .convertFromNetworkModel(ApiResponseFactory
                                                    .create(response));
                                } else if (response != null) {
                                    model = FormulaModel
                                            .convertFromNetworkModel(ApiResponseFactory
                                                    .create(response));
                                } else {
                                    model = FormulaModel
                                            .convertFromNetworkModel(ApiResponseFactory
                                                    .create(0, null));

                                }
                                formulaModelLiveData.postValue(model);
                            }

                            @Override
                            public void onFailure(Call<WikiResponseModel> call, Throwable t) {
                                FormulaModel model = FormulaModel
                                        .convertFromNetworkModel(ApiResponseFactory
                                                .create(0, t));
                                formulaModelLiveData.postValue(model);
                            }
                        });
            }
        });

        return formulaModelLiveData;
    }

    public static LiveData<String> downloadImage(String iHash) {
        MutableLiveData<String> imageData = new MutableLiveData<>();
        if (Utils.isImageCached(iHash)) {
            imageData.postValue(Utils.getImagePath(iHash));
        } else {
            NetworkRepo
                    .build()
                    .downloadImage(ProjectConstants.BASE_IMAGE_URL + iHash)
                    .enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()) {
                                Log.e("TARAN", "Image Success " + response.body());
                                File file = new File(FormulaApplication.mContext.getExternalFilesDir(null) + File.separator + iHash + ".png");
                                boolean writeSuccess = Utils.writeResponseBodyToDisk(response.body(), file);
                                if (writeSuccess) {
                                    Log.e("TARAN", "write Success " + response.body());
                                    imageData.postValue(file.getAbsolutePath());

                                } else {
                                    Log.e("TARAN", "write failure " + response.body());
                                    imageData.postValue(null);
                                }
                            } else {
                                Log.e("TARAN", "response.success false for image " + response.body());
                                imageData.postValue(null);
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Log.e("TARAN", "Failure could not load image");
                            imageData.postValue(null);
                        }
                    });
        }

        return imageData;
    }


}
