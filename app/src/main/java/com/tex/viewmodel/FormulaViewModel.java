package com.tex.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tex.network.NetworkRepo;
import com.tex.network.WikiService;
import com.tex.response.WikiResponse;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormulaViewModel extends ViewModel {

    public MutableLiveData<String> hash = new MutableLiveData<>();

    public MutableLiveData<String> onClick() {
        NetworkRepo.build().create(WikiService.class).checkExpression(RequestBody.create("a + b = c", MediaType.parse("multipart/form-data"))).enqueue(new Callback<WikiResponse>() {
            @Override
            public void onResponse(Call<WikiResponse> call, Response<WikiResponse> response) {
                if (response != null) {
                    if (response.isSuccessful()) {
                        Log.e("TARAN", "Success " + response.body());
                        String hashString = response.headers().get("x-resource-location");
                        hash.postValue(hashString);
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

}
