package com.tex.network;

import com.tex.utils.ProjectConstants;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkRepo {

    public static Retrofit build() {
        // interceptor to start logging requests.
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        return new Retrofit.Builder()
                .baseUrl(ProjectConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
//               .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .build();
    }
}
