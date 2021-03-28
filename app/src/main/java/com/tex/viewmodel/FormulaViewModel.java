package com.tex.viewmodel;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.tex.network.NetworkRepo;
import com.tex.network.WikiService;
import com.tex.response.WikiResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormulaViewModel extends ViewModel {

    public void onClick(){
        NetworkRepo.build().create(WikiService.class).checkExpression("a + b = c").enqueue(new Callback<WikiResponse>() {
            @Override
            public void onResponse(Call<WikiResponse> call, Response<WikiResponse> response) {
                if (response != null){
                    if(response.isSuccessful()){
                        Log.e("TARAN", "Success " + response.body());
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
    }

}
