package com.tex.network;

import okhttp3.Headers;

public class ApiResponse<T> {

    /**
     * Response from server.
     */
    protected T mResponse;

    /**
     * Boolean to check if call was successful.
     */
    private boolean mSuccess;

    /**
     * Headers from response.
     */
    private Headers mHeaders;

    /**
     * Method to check success.
     *
     * @return true if call was successful false otherwise.
     */
    public boolean isSuccess() {
        return mSuccess;
    }

    /**
     * Method to set success or failure flag.
     *
     * @param iSuccess true if call was successful false otherwise.
     */
    public void setSuccess(boolean iSuccess) {
        mSuccess = iSuccess;
    }

    /**
     * Method to get response from server.
     *
     * @return response from server.
     */
    public T getResponse() {
        return mResponse;
    }

    /**
     * Method to set response coming from server.
     *
     * @param iResponse server response.
     */
    public void setResponse(T iResponse) {
        this.mResponse = iResponse;
    }

    /**
     * Method to get response headers
     *
     * @return Headers from server.
     */
    public Headers getHeaders() {
        return mHeaders;
    }

    /**
     * Setter for headers
     *
     * @param iHeaders Headers from server.
     */
    public void setHeaders(Headers iHeaders) {
        this.mHeaders = iHeaders;
    }
}
