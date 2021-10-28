
package com.transdignity.deathserviceprovider.models.tracking;

import com.google.gson.annotations.SerializedName;


public class AshesTrackingModel {

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

        @SerializedName("cab_driver_assign_time")
        private String mCabDriverAssignTime;
        @SerializedName("decendant_name")
        private String mDecendantName;
        @SerializedName("drop_decendant_time")
        private String mDropDecendantTime;
        @SerializedName("pickup_decendent_time")
        private String mPickupDecendentTime;
        @SerializedName("pickup_rs_time")
        private String mPickupRsTime;
        @SerializedName("reached_on_decendant_pickup_location_time")
        private String mReachedOnDecendantPickupLocationTime;
        @SerializedName("reached_on_rs_location_time")
        private String mReachedOnRsLocationTime;
        @SerializedName("removal_specialists_assign_time")
        private String mRemovalSpecialistsAssignTime;
        @SerializedName("removed_from_address")
        private String mRemovedFromAddress;
        @SerializedName("request_id")
        private String mRequestId;
        @SerializedName("service_id")
        private String mServiceId;
        @SerializedName("steps")
        private String mSteps;
        @SerializedName("transferred_to_address")
        private String mTransferredToAddress;

        public String getCabDriverAssignTime() {
            return mCabDriverAssignTime;
        }

        public void setCabDriverAssignTime(String cabDriverAssignTime) {
            mCabDriverAssignTime = cabDriverAssignTime;
        }

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

        public String getPickupDecendentTime() {
            return mPickupDecendentTime;
        }

        public void setPickupDecendentTime(String pickupDecendentTime) {
            mPickupDecendentTime = pickupDecendentTime;
        }

        public String getPickupRsTime() {
            return mPickupRsTime;
        }

        public void setPickupRsTime(String pickupRsTime) {
            mPickupRsTime = pickupRsTime;
        }

        public String getReachedOnDecendantPickupLocationTime() {
            return mReachedOnDecendantPickupLocationTime;
        }

        public void setReachedOnDecendantPickupLocationTime(String reachedOnDecendantPickupLocationTime) {
            mReachedOnDecendantPickupLocationTime = reachedOnDecendantPickupLocationTime;
        }

        public String getReachedOnRsLocationTime() {
            return mReachedOnRsLocationTime;
        }

        public void setReachedOnRsLocationTime(String reachedOnRsLocationTime) {
            mReachedOnRsLocationTime = reachedOnRsLocationTime;
        }

        public String getRemovalSpecialistsAssignTime() {
            return mRemovalSpecialistsAssignTime;
        }

        public void setRemovalSpecialistsAssignTime(String removalSpecialistsAssignTime) {
            mRemovalSpecialistsAssignTime = removalSpecialistsAssignTime;
        }

        public String getRemovedFromAddress() {
            return mRemovedFromAddress;
        }

        public void setRemovedFromAddress(String removedFromAddress) {
            mRemovedFromAddress = removedFromAddress;
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
