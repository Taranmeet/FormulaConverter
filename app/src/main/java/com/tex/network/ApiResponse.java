package com.tex.network;

import retrofit2.Response;

public class ApiResponse<T> {

    public static <T> ApiResponse<T> create(Response<T> response){
        if(response.isSuccessful()){
            T body = response.body();
            if (body == null || response.code() == 204) {
                new ApiEmptyResponse();
            } else {
                new ApiSuccessResponse(body);
            }
        } else {
            if(response.errorBody() != null) {
                return new ApiErrorResponse(response.code(), response.errorBody().toString());
            } else {
                return new ApiErrorResponse(response.code(), response.message());
            }
        }
        return null;
    }

    public static <T> ApiErrorResponse<T> create(int errorCode, Throwable error){
        if(error != null) {
            return new ApiErrorResponse(errorCode, error.getMessage());
        } else {
            return new ApiErrorResponse(errorCode, "Unknown Error");
        }
    }


}
