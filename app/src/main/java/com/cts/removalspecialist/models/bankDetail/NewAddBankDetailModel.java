
package com.cts.removalspecialist.models.bankDetail;

import com.google.gson.annotations.SerializedName;


public class NewAddBankDetailModel {

    @SerializedName("merchantNumber")
    private String mMerchantNumber;
    @SerializedName("status")
    private Boolean mStatus;

    public String getMerchantNumber() {
        return mMerchantNumber;
    }

    public void setMerchantNumber(String merchantNumber) {
        mMerchantNumber = merchantNumber;
    }

    public Boolean getStatus() {
        return mStatus;
    }

    public void setStatus(Boolean status) {
        mStatus = status;
    }

}
