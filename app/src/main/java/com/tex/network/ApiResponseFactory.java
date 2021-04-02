package com.tex.network;

import retrofit2.Response;

public class ApiResponseFactory {

    /**
     * private constructor.
     */
    private ApiResponseFactory(){

    }

    /**
     * Method to create response when response can be either successful or failure.
     *
     * @param response response from server.
     * @param <T>      Type of response.
     * @return response from server.
     */
    public static <T> ApiResponse<T> create(Response<T> response) {
        ApiResponse<T> apiResponse = null;
        if (response.isSuccessful()) {
            // success case
            T body = response.body();
            if (body == null || response.code() == 204) {
                apiResponse = new ApiEmptyResponse<T>();
            } else {
                apiResponse = new ApiSuccessResponse<T>();
            }
            apiResponse.setSuccess(true);
            apiResponse.setResponse(body);
        } else {
            // failure case
            if (response.errorBody() != null) {
                apiResponse = new ApiErrorResponse<T>(response.code(), response.errorBody().toString());
            } else {
                apiResponse = new ApiErrorResponse<T>(response.code(), response.message());
            }
            apiResponse.setSuccess(false);
        }
        apiResponse.setHeaders(response.headers());
        return apiResponse;
    }

    /**
     * Method to create error response.
     *
     * @param errorCode Error code.
     * @param error     Throwable for error.
     * @param <T>       Type of response expected.
     * @return Error Response
     */
    public static <T> ApiErrorResponse<T> create(int errorCode, Throwable error) {
        ApiErrorResponse<T> apiResponse = null;
        if (error != null) {
            apiResponse = new ApiErrorResponse<T>(errorCode, error.getMessage());
        } else {
            apiResponse = new ApiErrorResponse<T>(errorCode, "Unknown Error");
        }
        apiResponse.setSuccess(false);
        return apiResponse;
    }
}
