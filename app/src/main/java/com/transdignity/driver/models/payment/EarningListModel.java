
package com.transdignity.driver.models.payment;

import java.util.List;
import com.google.gson.annotations.SerializedName;


public class EarningListModel {

    @SerializedName("data")
    private List<Datum> mData;
    @SerializedName("error")
    private Error mError;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("success")
    private String mSuccess;
    @SerializedName("token_valid")
    private String mTokenValid;

    public List<Datum> getData() {
        return mData;
    }

    public void setData(List<Datum> data) {
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
    public class Datum {

        @SerializedName("cab_driver_id")
        private String mCabDriverId;
        @SerializedName("cd_status")
        private String mCdStatus;
        @SerializedName("date_time")
        private String mDateTime;
        @SerializedName("decendant_first_name")
        private String mDecendantFirstName;
        @SerializedName("decendant_last_name")
        private String mDecendantLastName;
        @SerializedName("drop_location")
        private String mDropLocation;
        @SerializedName("dsppayment_date")
        private String mDsppaymentDate;
        @SerializedName("earning")
        private String mEarning;
        @SerializedName("pickup_location")
        private String mPickupLocation;
        @SerializedName("removal_specialists_id")
        private String mRemovalSpecialistsId;
        @SerializedName("request_assigned_user_id")
        private String mRequestAssignedUserId;
        @SerializedName("request_date")
        private String mRequestDate;
        @SerializedName("request_id")
        private String mRequestId;
        @SerializedName("rs_status")
        private String mRsStatus;
        @SerializedName("service_id")
        private String mServiceId;
        @SerializedName("term")
        private String mTerm;
        @SerializedName("transfer_status")
        private String mTransferStatus;
        @SerializedName("transfered")
        private String mTransfered;

        public String getCabDriverId() {
            return mCabDriverId;
        }

        public void setCabDriverId(String cabDriverId) {
            mCabDriverId = cabDriverId;
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

        public String getDecendantFirstName() {
            return mDecendantFirstName;
        }

        public void setDecendantFirstName(String decendantFirstName) {
            mDecendantFirstName = decendantFirstName;
        }

        public String getDecendantLastName() {
            return mDecendantLastName;
        }

        public void setDecendantLastName(String decendantLastName) {
            mDecendantLastName = decendantLastName;
        }

        public String getDropLocation() {
            return mDropLocation;
        }

        public void setDropLocation(String dropLocation) {
            mDropLocation = dropLocation;
        }

        public String getDsppaymentDate() {
            return mDsppaymentDate;
        }

        public void setDsppaymentDate(String dsppaymentDate) {
            mDsppaymentDate = dsppaymentDate;
        }

        public String getEarning() {
            return mEarning;
        }

        public void setEarning(String earning) {
            mEarning = earning;
        }

        public String getPickupLocation() {
            return mPickupLocation;
        }

        public void setPickupLocation(String pickupLocation) {
            mPickupLocation = pickupLocation;
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

        public String getRsStatus() {
            return mRsStatus;
        }

        public void setRsStatus(String rsStatus) {
            mRsStatus = rsStatus;
        }

        public String getServiceId() {
            return mServiceId;
        }

        public void setServiceId(String serviceId) {
            mServiceId = serviceId;
        }

        public String getTerm() {
            return mTerm;
        }

        public void setTerm(String term) {
            mTerm = term;
        }

        public String getTransferStatus() {
            return mTransferStatus;
        }

        public void setTransferStatus(String transferStatus) {
            mTransferStatus = transferStatus;
        }

        public String getTransfered() {
            return mTransfered;
        }

        public void setTransfered(String transfered) {
            mTransfered = transfered;
        }

    }
    public class Error {


    }
}
