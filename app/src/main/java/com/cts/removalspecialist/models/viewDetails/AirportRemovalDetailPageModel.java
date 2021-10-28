
package com.cts.removalspecialist.models.viewDetails;

import com.google.gson.annotations.SerializedName;


public class AirportRemovalDetailPageModel {

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
        private Object mAdminCharge;
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
        @SerializedName("decendent_dob")
        private String mDecendentDob;
        @SerializedName("decendent_name")
        private String mDecendentName;
        @SerializedName("decendent_pickedaddress_ofbody")
        private String mDecendentPickedaddressOfbody;
        @SerializedName("decendent_pickedaddress_ofbody_latlong")
        private String mDecendentPickedaddressOfbodyLatlong;
        @SerializedName("dob")
        private String mDob;
        @SerializedName("dsp_status")
        private String mDspStatus;
        @SerializedName("estimated_date_arrival_of_decendent")
        private String mEstimatedDateArrivalOfDecendent;
        @SerializedName("flightdetail")
        private Flightdetail mFlightdetail;
        @SerializedName("fomat_request_date")
        private String mFomatRequestDate;
        @SerializedName("format_time_of_death")
        private String mFormatTimeOfDeath;
        @SerializedName("place_of_death")
        private String mPlaceOfDeath;
        @SerializedName("request_created_by")
        private String mRequestCreatedBy;
        @SerializedName("request_date")
        private String mRequestDate;
        @SerializedName("request_id")
        private String mRequestId;
        @SerializedName("requested_by")
        private String mRequestedBy;
        @SerializedName("requestor_contact_number")
        private String mRequestorContactNumber;
        @SerializedName("rp_charge")
        private Object mRpCharge;
        @SerializedName("rs_charge")
        private Object mRsCharge;
        @SerializedName("service_id")
        private String mServiceId;
        @SerializedName("steps")
        private String mSteps;
        @SerializedName("time_of_death")
        private String mTimeOfDeath;
        @SerializedName("total_charge")
        private Object mTotalCharge;
        @SerializedName("total_distance")
        private Object mTotalDistance;
        @SerializedName("transferred_address")
        private String mTransferredAddress;
        @SerializedName("transferred_address_latlong")
        private String mTransferredAddressLatlong;

        public Object getAdminCharge() {
            return mAdminCharge;
        }

        public void setAdminCharge(Object adminCharge) {
            mAdminCharge = adminCharge;
        }

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

        public String getDecendentDob() {
            return mDecendentDob;
        }

        public void setDecendentDob(String decendentDob) {
            mDecendentDob = decendentDob;
        }

        public String getDecendentName() {
            return mDecendentName;
        }

        public void setDecendentName(String decendentName) {
            mDecendentName = decendentName;
        }

        public String getDecendentPickedaddressOfbody() {
            return mDecendentPickedaddressOfbody;
        }

        public void setDecendentPickedaddressOfbody(String decendentPickedaddressOfbody) {
            mDecendentPickedaddressOfbody = decendentPickedaddressOfbody;
        }

        public String getDecendentPickedaddressOfbodyLatlong() {
            return mDecendentPickedaddressOfbodyLatlong;
        }

        public void setDecendentPickedaddressOfbodyLatlong(String decendentPickedaddressOfbodyLatlong) {
            mDecendentPickedaddressOfbodyLatlong = decendentPickedaddressOfbodyLatlong;
        }

        public String getDob() {
            return mDob;
        }

        public void setDob(String dob) {
            mDob = dob;
        }

        public String getDspStatus() {
            return mDspStatus;
        }

        public void setDspStatus(String dspStatus) {
            mDspStatus = dspStatus;
        }

        public String getEstimatedDateArrivalOfDecendent() {
            return mEstimatedDateArrivalOfDecendent;
        }

        public void setEstimatedDateArrivalOfDecendent(String estimatedDateArrivalOfDecendent) {
            mEstimatedDateArrivalOfDecendent = estimatedDateArrivalOfDecendent;
        }

        public Flightdetail getFlightdetail() {
            return mFlightdetail;
        }

        public void setFlightdetail(Flightdetail flightdetail) {
            mFlightdetail = flightdetail;
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

        public String getPlaceOfDeath() {
            return mPlaceOfDeath;
        }

        public void setPlaceOfDeath(String placeOfDeath) {
            mPlaceOfDeath = placeOfDeath;
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

        public String getRequestedBy() {
            return mRequestedBy;
        }

        public void setRequestedBy(String requestedBy) {
            mRequestedBy = requestedBy;
        }

        public String getRequestorContactNumber() {
            return mRequestorContactNumber;
        }

        public void setRequestorContactNumber(String requestorContactNumber) {
            mRequestorContactNumber = requestorContactNumber;
        }

        public Object getRpCharge() {
            return mRpCharge;
        }

        public void setRpCharge(Object rpCharge) {
            mRpCharge = rpCharge;
        }

        public Object getRsCharge() {
            return mRsCharge;
        }

        public void setRsCharge(Object rsCharge) {
            mRsCharge = rsCharge;
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

        public String getTimeOfDeath() {
            return mTimeOfDeath;
        }

        public void setTimeOfDeath(String timeOfDeath) {
            mTimeOfDeath = timeOfDeath;
        }

        public Object getTotalCharge() {
            return mTotalCharge;
        }

        public void setTotalCharge(Object totalCharge) {
            mTotalCharge = totalCharge;
        }

        public Object getTotalDistance() {
            return mTotalDistance;
        }

        public void setTotalDistance(Object totalDistance) {
            mTotalDistance = totalDistance;
        }

        public String getTransferredAddress() {
            return mTransferredAddress;
        }

        public void setTransferredAddress(String transferredAddress) {
            mTransferredAddress = transferredAddress;
        }

        public String getTransferredAddressLatlong() {
            return mTransferredAddressLatlong;
        }

        public void setTransferredAddressLatlong(String transferredAddressLatlong) {
            mTransferredAddressLatlong = transferredAddressLatlong;
        }

    }


    public class Error {


    }

    public class Flightdetail {

        @SerializedName("date_of_journey")
        private String mDateOfJourney;
        @SerializedName("flight_name")
        private String mFlightName;
        @SerializedName("flight_number")
        private String mFlightNumber;
        @SerializedName("flight_pnr")
        private String mFlightPnr;
        @SerializedName("from_address")
        private String mFromAddress;
        @SerializedName("from_address_latlong")
        private String mFromAddressLatlong;
        @SerializedName("time_of_arrival")
        private String mTimeOfArrival;
        @SerializedName("time_of_departure")
        private String mTimeOfDeparture;
        @SerializedName("to_address")
        private String mToAddress;
        @SerializedName("to_address_latlong")
        private String mToAddressLatlong;

        public String getDateOfJourney() {
            return mDateOfJourney;
        }

        public void setDateOfJourney(String dateOfJourney) {
            mDateOfJourney = dateOfJourney;
        }

        public String getFlightName() {
            return mFlightName;
        }

        public void setFlightName(String flightName) {
            mFlightName = flightName;
        }

        public String getFlightNumber() {
            return mFlightNumber;
        }

        public void setFlightNumber(String flightNumber) {
            mFlightNumber = flightNumber;
        }

        public String getFlightPnr() {
            return mFlightPnr;
        }

        public void setFlightPnr(String flightPnr) {
            mFlightPnr = flightPnr;
        }

        public String getFromAddress() {
            return mFromAddress;
        }

        public void setFromAddress(String fromAddress) {
            mFromAddress = fromAddress;
        }

        public String getFromAddressLatlong() {
            return mFromAddressLatlong;
        }

        public void setFromAddressLatlong(String fromAddressLatlong) {
            mFromAddressLatlong = fromAddressLatlong;
        }

        public String getTimeOfArrival() {
            return mTimeOfArrival;
        }

        public void setTimeOfArrival(String timeOfArrival) {
            mTimeOfArrival = timeOfArrival;
        }

        public String getTimeOfDeparture() {
            return mTimeOfDeparture;
        }

        public void setTimeOfDeparture(String timeOfDeparture) {
            mTimeOfDeparture = timeOfDeparture;
        }

        public String getToAddress() {
            return mToAddress;
        }

        public void setToAddress(String toAddress) {
            mToAddress = toAddress;
        }

        public String getToAddressLatlong() {
            return mToAddressLatlong;
        }

        public void setToAddressLatlong(String toAddressLatlong) {
            mToAddressLatlong = toAddressLatlong;
        }

    }

}
