
package com.transdignity.deathserviceprovider.models.country;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class CountriesModel {

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

        @SerializedName("country_code")
        private String mCountryCode;
        @SerializedName("country_name")
        private String mCountryName;
        @SerializedName("id")
        private String mId;
        @SerializedName("isd_code")
        private String mIsdCode;

        public String getCountryCode() {
            return mCountryCode;
        }

        public void setCountryCode(String countryCode) {
            mCountryCode = countryCode;
        }

        public String getCountryName() {
            return mCountryName;
        }

        public void setCountryName(String countryName) {
            mCountryName = countryName;
        }

        public String getId() {
            return mId;
        }

        public void setId(String id) {
            mId = id;
        }

        public String getIsdCode() {
            return mIsdCode;
        }

        public void setIsdCode(String isdCode) {
            mIsdCode = isdCode;
        }

    }
    public class Error {


    }
}
