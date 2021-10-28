
package com.transdignity.driver.models.requestService;

import com.google.gson.annotations.SerializedName;


public class PickupDriverResponse {

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

        @SerializedName("address")
        private String mAddress;
        @SerializedName("decendant_first_name")
        private String mDecendantFirstName;
        @SerializedName("decendant_last_name")
        private String mDecendantLastName;
        @SerializedName("decendant_middle_name")
        private Object mDecendantMiddleName;
        @SerializedName("drop_location")
        private String mDropLocation;
        @SerializedName("drop_location_latitude")
        private String mDropLocationLatitude;
        @SerializedName("drop_location_longitude")
        private String mDropLocationLongitude;
        @SerializedName("latitude")
        private String mLatitude;
        @SerializedName("longitude")
        private String mLongitude;
        @SerializedName("phone")
        private String mPhone;

        public String getAddress() {
            return mAddress;
        }

        public void setAddress(String address) {
            mAddress = address;
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

        public Object getDecendantMiddleName() {
            return mDecendantMiddleName;
        }

        public void setDecendantMiddleName(Object decendantMiddleName) {
            mDecendantMiddleName = decendantMiddleName;
        }

        public String getDropLocation() {
            return mDropLocation;
        }

        public void setDropLocation(String dropLocation) {
            mDropLocation = dropLocation;
        }

        public String getDropLocationLatitude() {
            return mDropLocationLatitude;
        }

        public void setDropLocationLatitude(String dropLocationLatitude) {
            mDropLocationLatitude = dropLocationLatitude;
        }

        public String getDropLocationLongitude() {
            return mDropLocationLongitude;
        }

        public void setDropLocationLongitude(String dropLocationLongitude) {
            mDropLocationLongitude = dropLocationLongitude;
        }

        public String getLatitude() {
            return mLatitude;
        }

        public void setLatitude(String latitude) {
            mLatitude = latitude;
        }

        public String getLongitude() {
            return mLongitude;
        }

        public void setLongitude(String longitude) {
            mLongitude = longitude;
        }

        public String getPhone() {
            return mPhone;
        }

        public void setPhone(String phone) {
            mPhone = phone;
        }

    }
    public class Error {


    }
}
