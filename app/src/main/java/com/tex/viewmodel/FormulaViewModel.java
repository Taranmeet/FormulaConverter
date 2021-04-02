package com.tex.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tex.network.ApiErrorResponse;
import com.tex.network.NetworkRepo;
import com.tex.network.WikiService;
import com.tex.response.WikiResponse;

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

public class FormulaViewModel extends ViewModel {

    public MutableLiveData<String> hash = new MutableLiveData<>();

    private boolean isCachingEnabled = false;

    public MutableLiveData<String> onClick(File iFile) {
        NetworkRepo.build(true).create(WikiService.class).checkExpression(RequestBody.create("a + b = c", MediaType.parse("multipart/form-data"))).enqueue(new Callback<WikiResponse>() {
            @Override
            public void onResponse(Call<WikiResponse> call, Response<WikiResponse> response) {
                if (response != null) {
                    if (response.isSuccessful()) {
                        Log.e("TARAN", "Success " + response.body());
                        String hashString = response.headers().get("x-resource-location");
//                        hash.postValue(hashString);
                        NetworkRepo.build(true).create(WikiService.class).downloadImage("https://en.wikipedia.org/api/rest_v1/media/math/render/png/" + hashString).enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if (response.isSuccessful()) {
                                    Log.e("TARAN", "Success " + response.body());
                                    boolean writeSuccess = writeResponseBodyToDisk(response.body(), iFile);
                                    if (writeSuccess) {
                                        Log.e("TARAN", "write Success " + response.body());
                                        hash.postValue(hashString);
                                    } else {
                                        Log.e("TARAN", "write failure " + response.body());
                                    }
                                } else {
                                    Log.e("TARAN", "response.success false for image " + response.body());
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Log.e("TARAN", "Failure could not load image" + response.code());
                            }
                        });

                    } else {
                        Log.e("TARAN", "Failure " + response.code());
                    }
                }
            }

            @Override
            public void onFailure(Call<WikiResponse> call, Throwable t) {
                Log.e("TARAN", "Fail");
            }
        });
        return hash;
    }

    public void onClick2(File iFile) {
        NetworkRepo.build(true).create(WikiService.class).checkExpressionLive(RequestBody.create("a + b = c", MediaType.parse("multipart/form-data"))).observeForever(wikiResponse -> {
            if (wikiResponse != null && wikiResponse.isSuccess()) {
                String hashString = wikiResponse.getHeaders().get("x-resource-location");
                NetworkRepo.build(false).create(WikiService.class).downloadImage("https://en.wikipedia.org/api/rest_v1/media/math/render/png/" + hashString).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Log.e("TARAN", "Image loaded Success");
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("TARAN", "Image loaded Failure");
                    }
                });/*observeForever(responseBody -> {
                    if (responseBody != null) {
                        Log.e("TARAN", "Image loaded Failure");
                        *//*boolean writeSuccess = writeResponseBodyToDisk(responseBody.getResponse(), iFile);
                        if (writeSuccess) {
                            hash.postValue(hashString);
                        } else {
                            Log.e("TARAN", "write failure " + responseBody.getResponse());
                        }*//*
                    } else {
                        Log.e("TARAN", "Image load Failure");
                    }
                });*/
            } else if (wikiResponse instanceof ApiErrorResponse) {
                Log.e("TARAN", "Failure " + ((ApiErrorResponse<WikiResponse>) wikiResponse).getErrorMsg());
            }
        });
    }

    private boolean writeResponseBodyToDisk(ResponseBody body, File file) {
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

    public boolean isCachingEnabled() {
        return isCachingEnabled;
    }

    public void setCachingEnabled(boolean cachingEnabled) {
        isCachingEnabled = cachingEnabled;
    }
}
