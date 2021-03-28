package com.tex.network;

public class ApiErrorResponse<T> extends ApiResponse<T> {
    int errorCode;

    String errorMsg;

    public ApiErrorResponse(int iErrorCode, String iErrorMsg){
        errorCode = iErrorCode;
        errorMsg = iErrorMsg;
    }
}
