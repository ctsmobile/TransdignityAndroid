
package com.transdignity.deathserviceprovider.models.checkDriver;

import com.google.gson.annotations.SerializedName;


public class CheckDriverRequestModel {

    @SerializedName("assign_user_id")
    private String mAssignUserId;
    @SerializedName("request_id")
    private String mRequestId;
    @SerializedName("times")
    private String mTimes;

    public String getAssignUserId() {
        return mAssignUserId;
    }

    public void setAssignUserId(String assignUserId) {
        mAssignUserId = assignUserId;
    }

    public String getRequestId() {
        return mRequestId;
    }

    public void setRequestId(String requestId) {
        mRequestId = requestId;
    }

    public String getTimes() {
        return mTimes;
    }

    public void setTimes(String times) {
        mTimes = times;
    }

}
