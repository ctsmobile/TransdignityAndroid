
package com.transdignity.deathserviceprovider.models.payment;

import java.util.List;
import com.google.gson.annotations.SerializedName;


public class PaymentHistoryResponseModel {

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

        @SerializedName("decendant_first_name")
        private String mDecendantFirstName;
        @SerializedName("decendant_last_name")
        private String mDecendantLastName;
        @SerializedName("drop_location")
        private String mDropLocation;
        @SerializedName("dsppayment_date")
        private String mDsppaymentDate;
        @SerializedName("payment_status")
        private String mPaymentStatus;
        @SerializedName("pickup_location")
        private String mPickupLocation;
        @SerializedName("ratings")
        private String mRatings;
        @SerializedName("request_id")
        private String mRequestId;
        @SerializedName("term")
        private String mTerm;
        @SerializedName("total_charge")
        private String mTotalCharge;
        @SerializedName("transaction_id")
        private String mTransactionId;
        @SerializedName("service_id")
        private String ServiceId;

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

        public String getPaymentStatus() {
            return mPaymentStatus;
        }

        public void setPaymentStatus(String paymentStatus) {
            mPaymentStatus = paymentStatus;
        }

        public String getPickupLocation() {
            return mPickupLocation;
        }

        public void setPickupLocation(String pickupLocation) {
            mPickupLocation = pickupLocation;
        }

        public String getRatings() {
            return mRatings;
        }

        public void setRatings(String ratings) {
            mRatings = ratings;
        }

        public String getRequestId() {
            return mRequestId;
        }

        public void setRequestId(String requestId) {
            mRequestId = requestId;
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

        public String getTransactionId() {
            return mTransactionId;
        }

        public void setTransactionId(String transactionId) {
            mTransactionId = transactionId;
        }

        public String getServiceId() {
            return ServiceId;
        }

        public void setServiceId(String serviceId) {
            ServiceId = serviceId;
        }
    }
    public class Error {


    }
}
