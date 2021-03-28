package com.tex.network;

public class ApiSuccessResponse<T> extends ApiResponse<T> {
    T mBody;
    public ApiSuccessResponse(T iBody){
        mBody = iBody;
    }
}
