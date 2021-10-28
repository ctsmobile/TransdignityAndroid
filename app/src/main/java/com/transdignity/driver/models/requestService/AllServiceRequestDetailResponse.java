
package com.transdignity.driver.models.requestService;

import com.google.gson.annotations.SerializedName;


public class AllServiceRequestDetailResponse {

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

        @SerializedName("admin_charge")
        private String mAdminCharge;
        @SerializedName("admin_status")
        private String mAdminStatus;
        @SerializedName("cab_driver_assign_time")
        private String mCabDriverAssignTime;
        @SerializedName("cab_id")
        private String mCabId;
        @SerializedName("cab_name")
        private String mCabName;
        @SerializedName("cd_name")
        private String mCdName;
        @SerializedName("cd_phone")
        private String mCdPhone;
        @SerializedName("description")
        private String mDescription;
        @SerializedName("drop_location")
        private String mDropLocation;
        @SerializedName("drop_location_latlong")
        private String mDropLocationLatlong;
        @SerializedName("dsp_status")
        private String mDspStatus;
        @SerializedName("duration")
        private String mDuration;
        @SerializedName("first_name")
        private String mFirstName;
        @SerializedName("last_name")
        private String mLastName;
        @SerializedName("middle_name")
        private Object mMiddleName;
        @SerializedName("mobile_number")
        private String mMobileNumber;
        @SerializedName("pickup_location")
        private String mPickupLocation;
        @SerializedName("pickup_location_latlong")
        private String mPickupLocationLatlong;
        @SerializedName("request_created_by")
        private String mRequestCreatedBy;
        @SerializedName("rp_charge")
        private String mRpCharge;
        @SerializedName("rs_charge")
        private String mRsCharge;
        @SerializedName("service_id")
        private String mServiceId;
        @SerializedName("start_date")
        private String mStartDate;
        @SerializedName("start_time")
        private String mStartTime;
        @SerializedName("steps")
        private String mSteps;
        @SerializedName("total_charge")
        private String mTotalCharge;
        @SerializedName("total_distance")
        private String mTotalDistance;
        @SerializedName("trip_type")
        private String mTripType;
        @SerializedName("reciever_first_name")
        private String mRecieverFirstName;
        @SerializedName("reciever_last_name")
        private String mRecieverLastName;
        @SerializedName("reciever_mobile_number")
        private String mRecieverMobileNumber;

        public String getAdminCharge() {
            return mAdminCharge;
        }

        public void setAdminCharge(String adminCharge) {
            mAdminCharge = adminCharge;
        }

        public String getAdminStatus() {
            return mAdminStatus;
        }

        public void setAdminStatus(String adminStatus) {
            mAdminStatus = adminStatus;
        }

        public String getCabDriverAssignTime() {
            return mCabDriverAssignTime;
        }

        public void setCabDriverAssignTime(String cabDriverAssignTime) {
            mCabDriverAssignTime = cabDriverAssignTime;
        }

        public String getCabId() {
            return mCabId;
        }

        public void setCabId(String cabId) {
            mCabId = cabId;
        }

        public String getCabName() {
            return mCabName;
        }

        public void setCabName(String cabName) {
            mCabName = cabName;
        }

        public String getCdName() {
            return mCdName;
        }

        public void setCdName(String cdName) {
            mCdName = cdName;
        }

        public String getCdPhone() {
            return mCdPhone;
        }

        public void setCdPhone(String cdPhone) {
            mCdPhone = cdPhone;
        }

        public String getDescription() {
            return mDescription;
        }

        public void setDescription(String description) {
            mDescription = description;
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

        public String getDuration() {
            return mDuration;
        }

        public void setDuration(String duration) {
            mDuration = duration;
        }

        public String getFirstName() {
            return mFirstName;
        }

        public void setFirstName(String firstName) {
            mFirstName = firstName;
        }

        public String getLastName() {
            return mLastName;
        }

        public void setLastName(String lastName) {
            mLastName = lastName;
        }

        public Object getMiddleName() {
            return mMiddleName;
        }

        public void setMiddleName(Object middleName) {
            mMiddleName = middleName;
        }

        public String getMobileNumber() {
            return mMobileNumber;
        }

        public void setMobileNumber(String mobileNumber) {
            mMobileNumber = mobileNumber;
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

        public String getRequestCreatedBy() {
            return mRequestCreatedBy;
        }

        public void setRequestCreatedBy(String requestCreatedBy) {
            mRequestCreatedBy = requestCreatedBy;
        }

        public String getRpCharge() {
            return mRpCharge;
        }

        public void setRpCharge(String rpCharge) {
            mRpCharge = rpCharge;
        }

        public String getRsCharge() {
            return mRsCharge;
        }

        public void setRsCharge(String rsCharge) {
            mRsCharge = rsCharge;
        }

        public String getServiceId() {
            return mServiceId;
        }

        public void setServiceId(String serviceId) {
            mServiceId = serviceId;
        }

        public String getStartDate() {
            return mStartDate;
        }

        public void setStartDate(String startDate) {
            mStartDate = startDate;
        }

        public String getStartTime() {
            return mStartTime;
        }

        public void setStartTime(String startTime) {
            mStartTime = startTime;
        }

        public String getSteps() {
            return mSteps;
        }

        public void setSteps(String steps) {
            mSteps = steps;
        }

        public String getTotalCharge() {
            return mTotalCharge;
        }

        public void setTotalCharge(String totalCharge) {
            mTotalCharge = totalCharge;
        }

        public String getTotalDistance() {
            return mTotalDistance;
        }

        public void setTotalDistance(String totalDistance) {
            mTotalDistance = totalDistance;
        }

        public String getTripType() {
            return mTripType;
        }

        public void setTripType(String tripType) {
            mTripType = tripType;
        }

        public String getmRecieverFirstName() {
            return mRecieverFirstName;
        }

        public void setmRecieverFirstName(String mRecieverFirstName) {
            this.mRecieverFirstName = mRecieverFirstName;
        }

        public String getmRecieverLastName() {
            return mRecieverLastName;
        }

        public void setmRecieverLastName(String mRecieverLastName) {
            this.mRecieverLastName = mRecieverLastName;
        }

        public String getmRecieverMobileNumber() {
            return mRecieverMobileNumber;
        }

        public void setmRecieverMobileNumber(String mRecieverMobileNumber) {
            this.mRecieverMobileNumber = mRecieverMobileNumber;
        }
    }
    public class Error {


    }
}
