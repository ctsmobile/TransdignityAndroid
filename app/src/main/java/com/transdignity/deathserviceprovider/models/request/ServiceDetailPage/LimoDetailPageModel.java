
package com.transdignity.deathserviceprovider.models.request.ServiceDetailPage;

import com.google.gson.annotations.SerializedName;


public class LimoDetailPageModel {

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
        @SerializedName("cab_no")
        private String mCabNo;
        @SerializedName("cd_name")
        private String mCdName;
        @SerializedName("cd_phone")
        private String mCdPhone;
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
        @SerializedName("duration")
        private String mDuration;
        @SerializedName("endassigneddata")
        private Object mEndassigneddata;
        @SerializedName("endpaymentdetail")
        private Object mEndpaymentdetail;
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
        @SerializedName("request_id")
        private String mRequestId;
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
        @SerializedName("startassineddata")
        private Startassineddata mStartassineddata;
        @SerializedName("startpaymentdetail")
        private Startpaymentdetail mStartpaymentdetail;
        @SerializedName("steps")
        private String mSteps;
        @SerializedName("total_charge")
        private String mTotalCharge;
        @SerializedName("total_distance")
        private String mTotalDistance;
        @SerializedName("trip_type")
        private String mTripType;

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

        public String getCabNo() {
            return mCabNo;
        }

        public void setCabNo(String cabNo) {
            mCabNo = cabNo;
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

        public String getDuration() {
            return mDuration;
        }

        public void setDuration(String duration) {
            mDuration = duration;
        }

        public Object getEndassigneddata() {
            return mEndassigneddata;
        }

        public void setEndassigneddata(Object endassigneddata) {
            mEndassigneddata = endassigneddata;
        }

        public Object getEndpaymentdetail() {
            return mEndpaymentdetail;
        }

        public void setEndpaymentdetail(Object endpaymentdetail) {
            mEndpaymentdetail = endpaymentdetail;
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

        public String getRequestId() {
            return mRequestId;
        }

        public void setRequestId(String requestId) {
            mRequestId = requestId;
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

        public Startassineddata getStartassineddata() {
            return mStartassineddata;
        }

        public void setStartassineddata(Startassineddata startassineddata) {
            mStartassineddata = startassineddata;
        }

        public Startpaymentdetail getStartpaymentdetail() {
            return mStartpaymentdetail;
        }

        public void setStartpaymentdetail(Startpaymentdetail startpaymentdetail) {
            mStartpaymentdetail = startpaymentdetail;
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

    }
    public class Error {


    }
    public class Startassineddata {

        @SerializedName("cab_driver_id")
        private String mCabDriverId;
        @SerializedName("cab_id")
        private String mCabId;
        @SerializedName("cd_name")
        private String mCdName;
        @SerializedName("cd_phone")
        private String mCdPhone;
        @SerializedName("cd_status")
        private String mCdStatus;
        @SerializedName("removal_specialists_id")
        private Object mRemovalSpecialistsId;
        @SerializedName("request_assigned_user_id")
        private String mRequestAssignedUserId;
        @SerializedName("request_id")
        private String mRequestId;
        @SerializedName("rs_name")
        private Object mRsName;
        @SerializedName("rs_phone")
        private Object mRsPhone;
        @SerializedName("rs_status")
        private String mRsStatus;
        @SerializedName("term")
        private String mTerm;

        public String getCabDriverId() {
            return mCabDriverId;
        }

        public void setCabDriverId(String cabDriverId) {
            mCabDriverId = cabDriverId;
        }

        public String getCabId() {
            return mCabId;
        }

        public void setCabId(String cabId) {
            mCabId = cabId;
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

        public String getCdStatus() {
            return mCdStatus;
        }

        public void setCdStatus(String cdStatus) {
            mCdStatus = cdStatus;
        }

        public Object getRemovalSpecialistsId() {
            return mRemovalSpecialistsId;
        }

        public void setRemovalSpecialistsId(Object removalSpecialistsId) {
            mRemovalSpecialistsId = removalSpecialistsId;
        }

        public String getRequestAssignedUserId() {
            return mRequestAssignedUserId;
        }

        public void setRequestAssignedUserId(String requestAssignedUserId) {
            mRequestAssignedUserId = requestAssignedUserId;
        }

        public String getRequestId() {
            return mRequestId;
        }

        public void setRequestId(String requestId) {
            mRequestId = requestId;
        }

        public Object getRsName() {
            return mRsName;
        }

        public void setRsName(Object rsName) {
            mRsName = rsName;
        }

        public Object getRsPhone() {
            return mRsPhone;
        }

        public void setRsPhone(Object rsPhone) {
            mRsPhone = rsPhone;
        }

        public String getRsStatus() {
            return mRsStatus;
        }

        public void setRsStatus(String rsStatus) {
            mRsStatus = rsStatus;
        }

        public String getTerm() {
            return mTerm;
        }

        public void setTerm(String term) {
            mTerm = term;
        }

    }
    public class Startpaymentdetail {

        @SerializedName("admin_charge")
        private String mAdminCharge;
        @SerializedName("date")
        private String mDate;
        @SerializedName("gateway")
        private String mGateway;
        @SerializedName("id")
        private String mId;
        @SerializedName("mode_of_payment")
        private String mModeOfPayment;
        @SerializedName("payment_status")
        private String mPaymentStatus;
        @SerializedName("request_id")
        private String mRequestId;
        @SerializedName("rp_charge")
        private String mRpCharge;
        @SerializedName("rs_charge")
        private String mRsCharge;
        @SerializedName("term")
        private String mTerm;
        @SerializedName("total_charge")
        private String mTotalCharge;
        @SerializedName("total_distance")
        private String mTotalDistance;
        @SerializedName("transaction_id")
        private String mTransactionId;

        public String getAdminCharge() {
            return mAdminCharge;
        }

        public void setAdminCharge(String adminCharge) {
            mAdminCharge = adminCharge;
        }

        public String getDate() {
            return mDate;
        }

        public void setDate(String date) {
            mDate = date;
        }

        public String getGateway() {
            return mGateway;
        }

        public void setGateway(String gateway) {
            mGateway = gateway;
        }

        public String getId() {
            return mId;
        }

        public void setId(String id) {
            mId = id;
        }

        public String getModeOfPayment() {
            return mModeOfPayment;
        }

        public void setModeOfPayment(String modeOfPayment) {
            mModeOfPayment = modeOfPayment;
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

        public String getTerm() {
            return mTerm;
        }

        public void setTerm(String term) {
            mTerm = term;
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

        public String getTransactionId() {
            return mTransactionId;
        }

        public void setTransactionId(String transactionId) {
            mTransactionId = transactionId;
        }

    }

}
