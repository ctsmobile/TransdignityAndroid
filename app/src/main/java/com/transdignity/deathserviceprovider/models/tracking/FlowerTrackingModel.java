
package com.transdignity.deathserviceprovider.models.tracking;

import com.google.gson.annotations.SerializedName;


public class FlowerTrackingModel {

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

        @SerializedName("decendant_name")
        private String mDecendantName;
        @SerializedName("drop_decendant_time")
        private String mDropDecendantTime;
        @SerializedName("flower_drop_time")
        private Object mFlowerDropTime;
        @SerializedName("flower_pickup_time")
        private Object mFlowerPickupTime;
        @SerializedName("reached_on_decendant_pickup_location_time")
        private String mReachedOnDecendantPickupLocationTime;
        @SerializedName("pickup_decendent_time")
        private String mPickupDecendentTime;
        @SerializedName("request_accepted_titme")
        private String mRequestAcceptedTitme;
        @SerializedName("request_id")
        private String mRequestId;
        @SerializedName("service_id")
        private String mServiceId;
        @SerializedName("steps")
        private String mSteps;
        @SerializedName("transferred_to_address")
        private String mTransferredToAddress;

        public String getDecendantName() {
            return mDecendantName;
        }

        public void setDecendantName(String decendantName) {
            mDecendantName = decendantName;
        }

        public String getDropDecendantTime() {
            return mDropDecendantTime;
        }

        public void setDropDecendantTime(String dropDecendantTime) {
            mDropDecendantTime = dropDecendantTime;
        }

        public Object getFlowerDropTime() {
            return mFlowerDropTime;
        }

        public void setFlowerDropTime(Object flowerDropTime) {
            mFlowerDropTime = flowerDropTime;
        }

        public Object getFlowerPickupTime() {
            return mFlowerPickupTime;
        }

        public void setFlowerPickupTime(Object flowerPickupTime) {
            mFlowerPickupTime = flowerPickupTime;
        }

        public String getRequestAcceptedTitme() {
            return mRequestAcceptedTitme;
        }

        public void setRequestAcceptedTitme(String requestAcceptedTitme) {
            mRequestAcceptedTitme = requestAcceptedTitme;
        }

        public String getRequestId() {
            return mRequestId;
        }

        public void setRequestId(String requestId) {
            mRequestId = requestId;
        }

        public String getServiceId() {
            return mServiceId;
        }

        public void setServiceId(String serviceId) {
            mServiceId = serviceId;
        }

        public String getSteps() {
            return mSteps;
        }

        public void setSteps(String steps) {
            mSteps = steps;
        }

        public String getTransferredToAddress() {
            return mTransferredToAddress;
        }

        public void setTransferredToAddress(String transferredToAddress) {
            mTransferredToAddress = transferredToAddress;
        }

        public String getmReachedOnDecendantPickupLocationTime() {
            return mReachedOnDecendantPickupLocationTime;
        }

        public void setmReachedOnDecendantPickupLocationTime(String mReachedOnDecendantPickupLocationTime) {
            this.mReachedOnDecendantPickupLocationTime = mReachedOnDecendantPickupLocationTime;
        }

        public String getmPickupDecendentTime() {
            return mPickupDecendentTime;
        }

        public void setmPickupDecendentTime(String mPickupDecendentTime) {
            this.mPickupDecendentTime = mPickupDecendentTime;
        }
    }
    public class Error {


    }
}
