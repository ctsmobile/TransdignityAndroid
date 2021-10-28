
package com.cts.removalspecialist.models.rating;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class RatignLIstResponseModel {

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

        @SerializedName("comments")
        private String mComments;
        @SerializedName("date_time")
        private String mDateTime;
        @SerializedName("decendant_first_name")
        private String mDecendantFirstName;
        @SerializedName("decendant_last_name")
        private String mDecendantLastName;
        @SerializedName("ratings")
        private String mRatings;
        @SerializedName("request_id")
        private String mRequestId;

        public String getComments() {
            return mComments;
        }

        public void setComments(String comments) {
            mComments = comments;
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

    }
    public class Error {


    }
}
