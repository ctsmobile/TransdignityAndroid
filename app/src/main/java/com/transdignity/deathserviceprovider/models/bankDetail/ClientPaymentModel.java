
package com.transdignity.deathserviceprovider.models.bankDetail;

import com.google.gson.annotations.SerializedName;


public class ClientPaymentModel {

    @SerializedName("msg")
    private String mMsg;
    @SerializedName("ref")
    private String mRef;
    @SerializedName("status")
    private String mStatus;

    public String getMsg() {
        return mMsg;
    }

    public void setMsg(String msg) {
        mMsg = msg;
    }

    public String getRef() {
        return mRef;
    }

    public void setRef(String ref) {
        mRef = ref;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

}
