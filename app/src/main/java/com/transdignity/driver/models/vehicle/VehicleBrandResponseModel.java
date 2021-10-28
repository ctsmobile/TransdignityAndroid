
package com.transdignity.driver.models.vehicle;

import java.util.List;
import com.google.gson.annotations.SerializedName;


public class VehicleBrandResponseModel {

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

        @SerializedName("vehicle_brand")
        private String mVehicleBrand;
        @SerializedName("vehicle_brand_id")
        private String mVehicleBrandId;

        public String getVehicleBrand() {
            return mVehicleBrand;
        }

        public void setVehicleBrand(String vehicleBrand) {
            mVehicleBrand = vehicleBrand;
        }

        public String getVehicleBrandId() {
            return mVehicleBrandId;
        }

        public void setVehicleBrandId(String vehicleBrandId) {
            mVehicleBrandId = vehicleBrandId;
        }

    }
    public class Error {


    }
}
