
package com.transdignity.driver.models.bankDetail;

import com.google.gson.annotations.SerializedName;

public class AddAccountDetailRequestModel {

    @SerializedName("account_holder_name")
    private String mAccountHolderName;
    @SerializedName("account_number")
    private String mAccountNumber;
    @SerializedName("bank_name")
    private String mBankName;
    @SerializedName("ifsc_code")
    private String mIfscCode;
    @SerializedName("mobile_number")
    private String mMobileNumber;
    @SerializedName("user_group_id")
    private int mUserGroupId;
    @SerializedName("user_id")
    private int mUserId;

    public String getAccountHolderName() {
        return mAccountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        mAccountHolderName = accountHolderName;
    }

    public String getAccountNumber() {
        return mAccountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        mAccountNumber = accountNumber;
    }

    public String getBankName() {
        return mBankName;
    }

    public void setBankName(String bankName) {
        mBankName = bankName;
    }

    public String getIfscCode() {
        return mIfscCode;
    }

    public void setIfscCode(String ifscCode) {
        mIfscCode = ifscCode;
    }

    public String getMobileNumber() {
        return mMobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        mMobileNumber = mobileNumber;
    }

    public int getUserGroupId() {
        return mUserGroupId;
    }

    public void setUserGroupId(int userGroupId) {
        mUserGroupId = userGroupId;
    }

    public int getUserId() {
        return mUserId;
    }

    public void setUserId(int userId) {
        mUserId = userId;
    }

}
