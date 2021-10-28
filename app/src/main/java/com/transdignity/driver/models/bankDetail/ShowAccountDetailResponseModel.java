
package com.transdignity.driver.models.bankDetail;

import com.google.gson.annotations.SerializedName;


public class ShowAccountDetailResponseModel {

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

        @SerializedName("account_holder_name")
        private String mAccountHolderName;
        @SerializedName("account_number")
        private String mAccountNumber;
        @SerializedName("bank_name")
        private String mBankName;
        @SerializedName("date_added")
        private String mDateAdded;
        @SerializedName("id")
        private String mId;
        @SerializedName("ifsc_code")
        private String mIfscCode;
        @SerializedName("mobile_number")
        private String mMobileNumber;
        @SerializedName("user_group_id")
        private String mUserGroupId;
        @SerializedName("user_id")
        private String mUserId;

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

        public String getDateAdded() {
            return mDateAdded;
        }

        public void setDateAdded(String dateAdded) {
            mDateAdded = dateAdded;
        }

        public String getId() {
            return mId;
        }

        public void setId(String id) {
            mId = id;
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
    public class Error {


    }

}
