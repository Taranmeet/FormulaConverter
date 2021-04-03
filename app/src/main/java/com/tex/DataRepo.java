package com.tex;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.tex.network.ApiResponse;
import com.tex.network.ApiResponseFactory;
import com.tex.network.NetworkRepo;
import com.tex.network.WikiService;
import com.tex.response.WikiResponse;
import com.tex.utils.ProjectConstants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataRepo {

    public static LiveData<ApiResponse<WikiResponse>> checkFormula(String iFormula) {
        MutableLiveData<ApiResponse<WikiResponse>> liveData = new MutableLiveData<>();
        NetworkRepo
                .build()
                .checkExpression(RequestBody.create(iFormula, MediaType.parse("multipart/form-data")))
                .enqueue(new Callback<WikiResponse>() {
                    @Override
                    public void onResponse(Call<WikiResponse> call, Response<WikiResponse> response) {
                        if (response != null && response.isSuccessful()) {
                            liveData.postValue(ApiResponseFactory.create(response));
                        } else if (response != null) {
                            Log.e("TARAN", "Failure");
                            liveData.postValue(ApiResponseFactory.create(response));
                        } else {
                            liveData.postValue(ApiResponseFactory.create(0, null));
                        }
                    }

                    @Override
                    public void onFailure(Call<WikiResponse> call, Throwable t) {
                        Log.e("TARAN", "Failure");
                        liveData.postValue(ApiResponseFactory.create(0, t));
                    }
                });
        return liveData;
    }

    public static LiveData<String> downloadImage(String iHash) {
        MutableLiveData<String> imageData = new MutableLiveData<>();
        NetworkRepo
                .build()
                .downloadImage(ProjectConstants.BASE_IMAGE_URL + iHash)
        .enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Log.e("TARAN", "Image Success " + response.body());
                    File file = new File(FormulaApplication.mContext.getExternalFilesDir(null) + File.separator + iHash+".png");
                    boolean writeSuccess = writeResponseBodyToDisk(response.body(), file);
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
        return imageData;
    }

    private static boolean writeResponseBodyToDisk(ResponseBody body, File file) {
        try {
            // todo change the file location/name according to your needs

            if (!file.exists()) {
                boolean created = file.createNewFile();
                Log.e("Taran", "created " + created);
            }

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(file);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                    Log.d("TAG", "file download: " + fileSizeDownloaded + " of " + fileSize);
                }

                outputStream.flush();

                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }


}
