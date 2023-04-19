
package com.transdignity.deathserviceprovider.models.bankDetail;

import com.google.gson.annotations.SerializedName;


public class SendClientPaymentResponseRequestModel {

    @SerializedName("amount")
    private String mAmount;
    @SerializedName("gateway")
    private String mGateway;
    @SerializedName("payment_status")
    private String mPaymentStatus;
    @SerializedName("request_id")
    private String mRequestId;
    @SerializedName("transaction_id")
    private String mTransactionId;
    @SerializedName("user_group_id")
    private String mUserGroupId;
    @SerializedName("user_id")
    private String mUserId;
    @SerializedName("term")
    private String term;

    public String getAmount() {
        return mAmount;
    }

    public void setAmount(String amount) {
        mAmount = amount;
    }

    public String getGateway() {
        return mGateway;
    }

    public void setGateway(String gateway) {
        mGateway = gateway;
    }

    public String getPaymentStatus() {
        return mPaymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        mPaymentStatus = paymentStatus;
    }

    public String getRequestId() {
        return mRequestId;
    }

    public void setRequestId(String requestId) {
        mRequestId = requestId;
    }

    public String getTransactionId() {
        return mTransactionId;
    }

    public void setTransactionId(String transactionId) {
        mTransactionId = transactionId;
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

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }
}
