
package com.transdignity.driver.models.trackingDriverStatus;

import com.google.gson.annotations.SerializedName;


public class TrackingDriver {

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
        @SerializedName("reached_on_decendant_pickup_location_time")
        private String mReachedOnDecendantPickupLocationTime;
        @SerializedName("pickup_decendent_time")
        private String mPickupDecendentTime;
        @SerializedName("removed_from_address")
        private String mRemovedFromAddress;
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
        @SerializedName("removal_specialists_assign_time")
        private String mRemovalSpecialistsAssignTime;
        @SerializedName("cab_driver_assign_time")
        private String mCabDriverAssignTime;
        @SerializedName("reached_on_rs_location_time")
        private String mReachedOnRsLocationTime;
        @SerializedName("pickup_rs_time")
        private String mPickupRsTime;


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

        public String getReachedOnDecendantPickupLocationTime() {
            return mReachedOnDecendantPickupLocationTime;
        }

        public void setReachedOnDecendantPickupLocationTime(String reachedOnDecendantPickupLocationTime) {
            mReachedOnDecendantPickupLocationTime = reachedOnDecendantPickupLocationTime;
        }

        public String getmPickupDecendentTime() {
            return mPickupDecendentTime;
        }

        public void setmPickupDecendentTime(String mPickupDecendentTime) {
            this.mPickupDecendentTime = mPickupDecendentTime;
        }

        public String getRemovedFromAddress() {
            return mRemovedFromAddress;
        }

        public void setRemovedFromAddress(String removedFromAddress) {
            mRemovedFromAddress = removedFromAddress;
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

    }
    public class Error {


    }
}
