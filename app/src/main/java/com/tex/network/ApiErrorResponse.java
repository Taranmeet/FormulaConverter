package com.tex.network;

public class ApiErrorResponse<T> extends ApiResponse<T> {
    private int errorCode;

    private String errorMsg;

    public ApiErrorResponse(int iErrorCode, String iErrorMsg){
        errorCode = iErrorCode;
        errorMsg = iErrorMsg;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
