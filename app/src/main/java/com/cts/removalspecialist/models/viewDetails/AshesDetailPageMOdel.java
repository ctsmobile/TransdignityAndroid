
package com.cts.removalspecialist.models.viewDetails;

import com.google.gson.annotations.SerializedName;


public class AshesDetailPageMOdel {

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
    public class Error {


    }
    public class Data {

        @SerializedName("admin_status")
        private String mAdminStatus;
        @SerializedName("cab_driver_assign_time")
        private Object mCabDriverAssignTime;
        @SerializedName("cab_id")
        private Object mCabId;
        @SerializedName("cab_name")
        private Object mCabName;
        @SerializedName("cab_no")
        private Object mCabNo;
        @SerializedName("cd_name")
        private Object mCdName;
        @SerializedName("cd_phone")
        private Object mCdPhone;
        @SerializedName("date_of_death")
        private String mDateOfDeath;
        @SerializedName("description")
        private String mDescription;
        @SerializedName("dob")
        private String mDob;
        @SerializedName("drop_location")
        private String mDropLocation;
        @SerializedName("drop_location_latlong")
        private String mDropLocationLatlong;
        @SerializedName("dsp_status")
        private String mDspStatus;
        @SerializedName("first_name")
        private String mFirstName;
        @SerializedName("fomat_request_date")
        private String mFomatRequestDate;
        @SerializedName("format_time_of_death")
        private String mFormatTimeOfDeath;
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
        @SerializedName("request_date")
        private String mRequestDate;
        @SerializedName("service_id")
        private String mServiceId;
        @SerializedName("steps")
        private String mSteps;
        @SerializedName("total_charge")
        private String mTotalCharge;
        public String getAdminStatus() {
            return mAdminStatus;
        }

        public void setAdminStatus(String adminStatus) {
            mAdminStatus = adminStatus;
        }

        public Object getCabDriverAssignTime() {
            return mCabDriverAssignTime;
        }

        public void setCabDriverAssignTime(Object cabDriverAssignTime) {
            mCabDriverAssignTime = cabDriverAssignTime;
        }

        public Object getCabId() {
            return mCabId;
        }

        public void setCabId(Object cabId) {
            mCabId = cabId;
        }

        public Object getCabName() {
            return mCabName;
        }

        public void setCabName(Object cabName) {
            mCabName = cabName;
        }

        public Object getCabNo() {
            return mCabNo;
        }

        public void setCabNo(Object cabNo) {
            mCabNo = cabNo;
        }

        public Object getCdName() {
            return mCdName;
        }

        public void setCdName(Object cdName) {
            mCdName = cdName;
        }

        public Object getCdPhone() {
            return mCdPhone;
        }

        public void setCdPhone(Object cdPhone) {
            mCdPhone = cdPhone;
        }

        public String getDateOfDeath() {
            return mDateOfDeath;
        }

        public void setDateOfDeath(String dateOfDeath) {
            mDateOfDeath = dateOfDeath;
        }

        public String getDescription() {
            return mDescription;
        }

        public void setDescription(String description) {
            mDescription = description;
        }

        public String getDob() {
            return mDob;
        }

        public void setDob(String dob) {
            mDob = dob;
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

        public String getFirstName() {
            return mFirstName;
        }

        public void setFirstName(String firstName) {
            mFirstName = firstName;
        }

        public String getFomatRequestDate() {
            return mFomatRequestDate;
        }

        public void setFomatRequestDate(String fomatRequestDate) {
            mFomatRequestDate = fomatRequestDate;
        }

        public String getFormatTimeOfDeath() {
            return mFormatTimeOfDeath;
        }

        public void setFormatTimeOfDeath(String formatTimeOfDeath) {
            mFormatTimeOfDeath = formatTimeOfDeath;
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

        public String getRequestDate() {
            return mRequestDate;
        }

        public void setRequestDate(String requestDate) {
            mRequestDate = requestDate;
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

        public String getmTotalCharge() {
            return mTotalCharge;
        }

        public void setmTotalCharge(String mTotalCharge) {
            this.mTotalCharge = mTotalCharge;
        }
    }

}
