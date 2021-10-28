
package com.cts.removalspecialist.fragments.bankDetailsFragments;

import com.google.gson.annotations.SerializedName;


public class ShowBankDetailResponseModel {

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

        @SerializedName("accountnumber")
        private String mAccountnumber;
        @SerializedName("accountroutingnumber")
        private String mAccountroutingnumber;
        @SerializedName("date_added")
        private String mDateAdded;
        @SerializedName("id")
        private String mId;
        @SerializedName("merchantAddress")
        private String mMerchantAddress;
        @SerializedName("merchantCity")
        private String mMerchantCity;
        @SerializedName("merchantEmail")
        private String mMerchantEmail;
        @SerializedName("merchantName")
        private String mMerchantName;
        @SerializedName("merchantNumber")
        private String mMerchantNumber;
        @SerializedName("merchantNumberGateway")
        private String mMerchantNumberGateway;
        @SerializedName("merchantState")
        private String mMerchantState;
        @SerializedName("merchantcontactNumber")
        private String mMerchantcontactNumber;
        @SerializedName("merchantzipNumber")
        private String mMerchantzipNumber;
        @SerializedName("refnumber")
        private String mRefnumber;
        @SerializedName("user_group_id")
        private String mUserGroupId;
        @SerializedName("user_id")
        private String mUserId;

        public String getAccountnumber() {
            return mAccountnumber;
        }

        public void setAccountnumber(String accountnumber) {
            mAccountnumber = accountnumber;
        }

        public String getAccountroutingnumber() {
            return mAccountroutingnumber;
        }

        public void setAccountroutingnumber(String accountroutingnumber) {
            mAccountroutingnumber = accountroutingnumber;
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

        public String getMerchantAddress() {
            return mMerchantAddress;
        }

        public void setMerchantAddress(String merchantAddress) {
            mMerchantAddress = merchantAddress;
        }

        public String getMerchantCity() {
            return mMerchantCity;
        }

        public void setMerchantCity(String merchantCity) {
            mMerchantCity = merchantCity;
        }

        public String getMerchantEmail() {
            return mMerchantEmail;
        }

        public void setMerchantEmail(String merchantEmail) {
            mMerchantEmail = merchantEmail;
        }

        public String getMerchantName() {
            return mMerchantName;
        }

        public void setMerchantName(String merchantName) {
            mMerchantName = merchantName;
        }

        public String getMerchantNumber() {
            return mMerchantNumber;
        }

        public void setMerchantNumber(String merchantNumber) {
            mMerchantNumber = merchantNumber;
        }

        public String getMerchantNumberGateway() {
            return mMerchantNumberGateway;
        }

        public void setMerchantNumberGateway(String merchantNumberGateway) {
            mMerchantNumberGateway = merchantNumberGateway;
        }

        public String getMerchantState() {
            return mMerchantState;
        }

        public void setMerchantState(String merchantState) {
            mMerchantState = merchantState;
        }

        public String getMerchantcontactNumber() {
            return mMerchantcontactNumber;
        }

        public void setMerchantcontactNumber(String merchantcontactNumber) {
            mMerchantcontactNumber = merchantcontactNumber;
        }

        public String getMerchantzipNumber() {
            return mMerchantzipNumber;
        }

        public void setMerchantzipNumber(String merchantzipNumber) {
            mMerchantzipNumber = merchantzipNumber;
        }

        public String getRefnumber() {
            return mRefnumber;
        }

        public void setRefnumber(String refnumber) {
            mRefnumber = refnumber;
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
