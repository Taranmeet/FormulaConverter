package com.tex.network;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import retrofit2.CallAdapter;
import retrofit2.Retrofit;

public class LiveDataCallAdapterFactory extends CallAdapter.Factory{
    @Override
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        Type type = CallAdapter.Factory.getParameterUpperBound(0, (ParameterizedType) returnType);
        return new LiveDataCallAdapter(type);
    }
}
