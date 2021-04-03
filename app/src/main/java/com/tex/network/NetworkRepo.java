package com.tex.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tex.utils.ProjectConstants;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkRepo {

    private static final Gson gson = new GsonBuilder()
            .create();

    public static WikiService build() {
        // interceptor to start logging requests.
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        return new Retrofit.Builder()
                .baseUrl(ProjectConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build()
                .create(WikiService.class);

    }
}
