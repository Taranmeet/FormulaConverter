package com.tex.network;

import com.tex.response.WikiResponse;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;

public interface WikiService {

    @Multipart
    @Headers({
            "Authority: en.wikipedia.org",
            "Method: POST",
            "Path: /api/rest_v1/media/math/check/tex",
            "Scheme: https",
            "Accept: application/json",
            "Accept-Encoding: gzip, deflate, br"
    })
    @POST("media/math/check/tex")
    Call<WikiResponse> checkExpression(@Part("q") RequestBody exp);

    @Headers({
            "Authority: en.wikipedia.org",
            "Method: POST",
            "Path: /api/rest_v1/media/math/check/tex",
            "Scheme: https",
            "Accept: image/png",
            "Accept-Encoding: gzip, deflate, br"
    })
    @GET
    Call<ResponseBody> downloadImage(@Url String path);

}
