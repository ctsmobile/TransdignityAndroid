
package com.transdignity.driver.models.rideDetail;

import com.google.gson.annotations.SerializedName;


public class RideDetailResponseModel {

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

        @SerializedName("admin_status")
        private String mAdminStatus;
        @SerializedName("decendant_mobile_number")
        private String mDecendantMobileNumber;
        @SerializedName("decendent_first_name")
        private String mDecendentFirstName;
        @SerializedName("decendent_last_name")
        private String mDecendentLastName;
        @SerializedName("decendent_middle_name")
        private String mDecendentMiddleName;
        @SerializedName("drop_location")
        private String mDropLocation;
        @SerializedName("drop_location_latlong")
        private String mDropLocationLatlong;
        @SerializedName("dsp_status")
        private String mDspStatus;
        @SerializedName("pickup_location")
        private String mPickupLocation;
        @SerializedName("pickup_location_latlong")
        private String mPickupLocationLatlong;
        @SerializedName("reciever_first_name")
        private String mRecieverFirstName;
        @SerializedName("reciever_last_name")
        private String mRecieverLastName;
        @SerializedName("reciever_mobile_number")
        private String mRecieverMobileNumber;
        @SerializedName("request_created_by")
        private String mRequestCreatedBy;
        @SerializedName("request_id")
        private String mRequestId;
        @SerializedName("rs_name")
        private String mRsName;
        @SerializedName("rs_phone")
        private String mRsPhone;
        @SerializedName("service_id")
        private String mServiceId;
        @SerializedName("total_distance")
        private String mTotalDistance;
        @SerializedName("removal_specialist_pickup_location")
        private String mRemovalSpecialistPickupLocation;
        @SerializedName("rs_latitude")
        private String mRsLatitude;
        @SerializedName("rs_longitude")
        private String mRsLongitude;

        public String getAdminStatus() {
            return mAdminStatus;
        }

        public void setAdminStatus(String adminStatus) {
            mAdminStatus = adminStatus;
        }

        public String getDecendantMobileNumber() {
            return mDecendantMobileNumber;
        }

        public void setDecendantMobileNumber(String decendantMobileNumber) {
            mDecendantMobileNumber = decendantMobileNumber;
        }

        public String getDecendentFirstName() {
            return mDecendentFirstName;
        }

        public void setDecendentFirstName(String decendentFirstName) {
            mDecendentFirstName = decendentFirstName;
        }

        public String getDecendentLastName() {
            return mDecendentLastName;
        }

        public void setDecendentLastName(String decendentLastName) {
            mDecendentLastName = decendentLastName;
        }

        public String getDecendentMiddleName() {
            return mDecendentMiddleName;
        }

        public void setDecendentMiddleName(String decendentMiddleName) {
            mDecendentMiddleName = decendentMiddleName;
        }

        public String getDropLocation() {
            return mDropLocation;
        }

        public void setDropLocation(String dropLocation) {
            mDropLocation = dropLocation;
        }

        public String getDropLocationLatlong() {
            return mDropLocationLatlong;
        }

        public void setDropLocationLatlong(String dropLocationLatlong) {
            mDropLocationLatlong = dropLocationLatlong;
        }

        public String getDspStatus() {
            return mDspStatus;
        }

        public void setDspStatus(String dspStatus) {
            mDspStatus = dspStatus;
        }

        public String getPickupLocation() {
            return mPickupLocation;
        }

        public void setPickupLocation(String pickupLocation) {
            mPickupLocation = pickupLocation;
        }

        public String getPickupLocationLatlong() {
            return mPickupLocationLatlong;
        }

        public void setPickupLocationLatlong(String pickupLocationLatlong) {
            mPickupLocationLatlong = pickupLocationLatlong;
        }

        public String getRecieverFirstName() {
            return mRecieverFirstName;
        }

        public void setRecieverFirstName(String recieverFirstName) {
            mRecieverFirstName = recieverFirstName;
        }

        public String getRecieverLastName() {
            return mRecieverLastName;
        }

        public void setRecieverLastName(String recieverLastName) {
            mRecieverLastName = recieverLastName;
        }

        public String getRecieverMobileNumber() {
            return mRecieverMobileNumber;
        }

        public void setRecieverMobileNumber(String recieverMobileNumber) {
            mRecieverMobileNumber = recieverMobileNumber;
        }

        public String getRequestCreatedBy() {
            return mRequestCreatedBy;
        }

        public void setRequestCreatedBy(String requestCreatedBy) {
            mRequestCreatedBy = requestCreatedBy;
        }

        public String getRequestId() {
            return mRequestId;
        }

        public void setRequestId(String requestId) {
            mRequestId = requestId;
        }

        public String getRsName() {
            return mRsName;
        }

        public void setRsName(String rsName) {
            mRsName = rsName;
        }

        public String getRsPhone() {
            return mRsPhone;
        }

        public void setRsPhone(String rsPhone) {
            mRsPhone = rsPhone;
        }

        public String getServiceId() {
            return mServiceId;
        }

        public void setServiceId(String serviceId) {
            mServiceId = serviceId;
        }

        public String getmTotalDistance() {
            return mTotalDistance;
        }

        public void setmTotalDistance(String mTotalDistance) {
            this.mTotalDistance = mTotalDistance;
        }

        public String getRemovalSpecialistPickupLocation() {
            return mRemovalSpecialistPickupLocation;
        }

        public void setRemovalSpecialistPickupLocation(String RemovalSpecialistPickupLocation) {
            this.mRemovalSpecialistPickupLocation = RemovalSpecialistPickupLocation;
        }

        public String getRsLatitude() {
            return mRsLatitude;
        }

        public void setRsLatitude(String RsLatitude) {
            this.mRsLatitude = RsLatitude;
        }

        public String getRsLongitude() {
            return mRsLongitude;
        }

        public void setRsLongitude(String RsLongitude) {
            this.mRsLongitude = RsLongitude;
        }
    }
    public class Error {


    }
}
