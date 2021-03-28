package com.tex.network;

import com.google.gson.Gson;
import com.tex.utils.ProjectConstants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkRepo {

    public static Retrofit build(){
       return new Retrofit.Builder()
               .baseUrl(ProjectConstants.BASE_URL)
               .addConverterFactory(GsonConverterFactory.create())
//               .addCallAdapterFactory(new LiveDataCallAdapterFactory())
               .build();
    }
}
