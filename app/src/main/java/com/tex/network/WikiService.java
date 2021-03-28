package com.tex.network;

import androidx.lifecycle.LiveData;

import com.tex.response.WikiResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface WikiService {

    @Multipart
    @Headers({
            "Accept: application/json",
            "Content-Type: multipart/form-data"
    })
    @POST("media/math/check/tex")
    public Call<WikiResponse> checkExpression(@Part("q") String exp);

}
