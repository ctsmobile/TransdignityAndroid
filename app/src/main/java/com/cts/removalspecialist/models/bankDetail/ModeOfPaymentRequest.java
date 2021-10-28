
package com.cts.removalspecialist.models.bankDetail;

import com.google.gson.annotations.SerializedName;


public class ModeOfPaymentRequest {

    @SerializedName("mode_of_payment")
    private String mModeOfPayment;
    @SerializedName("user_group_id")
    private String mUserGroupId;
    @SerializedName("user_id")
    private String mUserId;

    public String getModeOfPayment() {
        return mModeOfPayment;
    }

    public void setModeOfPayment(String modeOfPayment) {
        mModeOfPayment = modeOfPayment;
    }

    public String getUserGroupId() {
        return mUserGroupId;
    }

    public void setUserGroupId(String userGroupId) {
        mUserGroupId = userGroupId;
    }

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String userId) {
        mUserId = userId;
    }

}
