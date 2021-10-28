
package com.transdignity.driver.models.requestService;

import com.google.gson.annotations.SerializedName;


public class ReachedDriverRequestModel {

    @SerializedName("request_id")
    private String mRequestId;
    @SerializedName("user_id")
    private String mUserId;

    public String getRequestId() {
        return mRequestId;
    }

    public void setRequestId(String requestId) {
        mRequestId = requestId;
    }

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String userId) {
        mUserId = userId;
    }

}
