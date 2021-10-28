
package com.transdignity.deathserviceprovider.models.request.ServiceDetailPage;

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

        @SerializedName("admin_status")
        private String mAdminStatus;
        @SerializedName("cab_name")
        private String mCabName;
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
        @SerializedName("endassigneddata")
        private Endassigneddata mEndassigneddata;
        @SerializedName("endpaymentdetail")
        private Endpaymentdetail mEndpaymentdetail;
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
        @SerializedName("service_id")
        private String mServiceId;
        @SerializedName("startassineddata")
        private Startassineddata mStartassineddata;
        @SerializedName("startpaymentdetail")
        private Startpaymentdetail mStartpaymentdetail;
        @SerializedName("time_of_death")
        private String mTimeOfDeath;
        @SerializedName("transferred_address")
        private String mTransferredAddress;
        @SerializedName("transferred_address_latlong")
        private String mTransferredAddressLatlong;

        public String getAdminStatus() {
            return mAdminStatus;
        }

        public void setAdminStatus(String adminStatus) {
            mAdminStatus = adminStatus;
        }

        public String getCabName() {
            return mCabName;
        }

        public void setCabName(String cabName) {
            mCabName = cabName;
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

        public Endassigneddata getEndassigneddata() {
            return mEndassigneddata;
        }

        public void setEndassigneddata(Endassigneddata endassigneddata) {
            mEndassigneddata = endassigneddata;
        }

        public Endpaymentdetail getEndpaymentdetail() {
            return mEndpaymentdetail;
        }

        public void setEndpaymentdetail(Endpaymentdetail endpaymentdetail) {
            mEndpaymentdetail = endpaymentdetail;
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

        public String getServiceId() {
            return mServiceId;
        }

        public void setServiceId(String serviceId) {
            mServiceId = serviceId;
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

        public String getTimeOfDeath() {
            return mTimeOfDeath;
        }

        public void setTimeOfDeath(String timeOfDeath) {
            mTimeOfDeath = timeOfDeath;
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
        @SerializedName("date_time")
        private String mDateTime;
        @SerializedName("removal_specialists_id")
        private String mRemovalSpecialistsId;
        @SerializedName("request_assigned_user_id")
        private String mRequestAssignedUserId;
        @SerializedName("request_id")
        private String mRequestId;
        @SerializedName("rs_name")
        private String mRsName;
        @SerializedName("rs_phone")
        private String mRsPhone;
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

        public String getDateTime() {
            return mDateTime;
        }

        public void setDateTime(String dateTime) {
            mDateTime = dateTime;
        }

        public String getRemovalSpecialistsId() {
            return mRemovalSpecialistsId;
        }

        public void setRemovalSpecialistsId(String removalSpecialistsId) {
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
    public class Endpaymentdetail {

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
    public class Endassigneddata {

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
        @SerializedName("date_time")
        private String mDateTime;
        @SerializedName("removal_specialists_id")
        private String mRemovalSpecialistsId;
        @SerializedName("request_assigned_user_id")
        private String mRequestAssignedUserId;
        @SerializedName("request_id")
        private String mRequestId;
        @SerializedName("rs_name")
        private String mRsName;
        @SerializedName("rs_phone")
        private String mRsPhone;
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

        public String getDateTime() {
            return mDateTime;
        }

        public void setDateTime(String dateTime) {
            mDateTime = dateTime;
        }

        public String getRemovalSpecialistsId() {
            return mRemovalSpecialistsId;
        }

        public void setRemovalSpecialistsId(String removalSpecialistsId) {
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
