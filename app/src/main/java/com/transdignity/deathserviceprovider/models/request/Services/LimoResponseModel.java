
package com.transdignity.deathserviceprovider.models.request.Services;

import com.google.gson.annotations.SerializedName;


public class LimoResponseModel {

    @SerializedName("data")
    private Data mData;
    @SerializedName("error")
    private Error mError;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("success")
    private String mSuccess;
    @SerializedName("token_valid")
    private String mTokenValid;

    public Data getData() {
        return mData;
    }

    public void setData(Data data) {
        mData = data;
    }

    public Error getError() {
        return mError;
    }

    public void setError(Error error) {
        mError = error;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public String getSuccess() {
        return mSuccess;
    }

    public void setSuccess(String success) {
        mSuccess = success;
    }

    public String getTokenValid() {
        return mTokenValid;
    }

    public void setTokenValid(String tokenValid) {
        mTokenValid = tokenValid;
    }
    public class Data {

        @SerializedName("request_id")
        private Long mRequestId;
        @SerializedName("request_sent_todriver")
        private String mRequestSentTodriver;

        public Long getRequestId() {
            return mRequestId;
        }

        public void setRequestId(Long requestId) {
            mRequestId = requestId;
        }

        public String getRequestSentTodriver() {
            return mRequestSentTodriver;
        }

        public void setRequestSentTodriver(String requestSentTodriver) {
            mRequestSentTodriver = requestSentTodriver;
        }

    }
    public class Error {
    }
}
