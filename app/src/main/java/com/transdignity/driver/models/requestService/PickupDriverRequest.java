
package com.transdignity.driver.models.requestService;

import com.google.gson.annotations.SerializedName;


public class PickupDriverRequest {

    @SerializedName("request_id")
    private String mRequestId;
    @SerializedName("status")
    private String mStatus;
    @SerializedName("user_id")
    private String mUserId;

    public String getRequestId() {
        return mRequestId;
    }

    public void setRequestId(String requestId) {
        mRequestId = requestId;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String userId) {
        mUserId = userId;
    }

}
