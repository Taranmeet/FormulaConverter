package com.tex.network;

import androidx.lifecycle.LiveData;

import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Callback;
import retrofit2.Response;

public class LiveDataCallAdapter<R> implements CallAdapter<R, LiveData<ApiResponse<R>>> {

    private boolean isSuccess = false;

    private Type mType;

    public LiveDataCallAdapter(Type iType){
        mType = iType;
    }
    @Override
    public Type responseType() {
        return mType;
    }

    @Override
    public LiveData<ApiResponse<R>> adapt(Call<R> call) {
        return new LiveData<ApiResponse<R>>() {

            @Override
            protected void onActive() {
                super.onActive();
                if(!isSuccess) enqueue();
            }

            @Override
            protected void onInactive() {
                super.onInactive();
                dequeue();
            }

            private void dequeue(){
                if(call.isExecuted()) call.cancel();
            }

            private void enqueue(){
                call.enqueue(new Callback<R>() {
                    @Override
                    public void onResponse(Call<R> call, Response<R> response) {
                        postValue(ApiResponse.create(response));
                        isSuccess = true;
                    }

                    @Override
                    public void onFailure(Call<R> call, Throwable t) {
                        postValue(ApiResponse.create(0, t));
                    }
                });
            }
        };
    }




}
