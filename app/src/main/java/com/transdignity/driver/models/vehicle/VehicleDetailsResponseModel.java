
package com.transdignity.driver.models.vehicle;

import com.google.gson.annotations.SerializedName;


public class VehicleDetailsResponseModel {

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

        @SerializedName("basrform")
        private String mBasrform;
        @SerializedName("basrform_doc")
        private String mBasrformDoc;
        @SerializedName("cab_driver_company_id")
        private String mCabDriverCompanyId;
        @SerializedName("cab_id")
        private String mCabId;
        @SerializedName("cab_name")
        private String mCabName;
        @SerializedName("cab_no")
        private String mCabNo;
        @SerializedName("captain_name")
        private String mCaptainName;
        @SerializedName("insurance_no")
        private Object mInsuranceNo;
        @SerializedName("location")
        private String mLocation;
        @SerializedName("owner_name")
        private String mOwnerName;
        @SerializedName("pollution_doc")
        private String mPollutionDoc;
        @SerializedName("registration_doc")
        private String mRegistrationDoc;
        @SerializedName("registration_no")
        private String mRegistrationNo;
        @SerializedName("status")
        private String mStatus;
        @SerializedName("vehicle_brand_id")
        private String mVehicleBrandId;
        @SerializedName("vehicle_color_id")
        private String mVehicleColorId;
        @SerializedName("vehicle_model_id")
        private String mVehicleModelId;
        @SerializedName("vehicle_type_id")
        private String mVehicleTypeId;

        public String getBasrform() {
            return mBasrform;
        }

        public void setBasrform(String basrform) {
            mBasrform = basrform;
        }

        public String getBasrformDoc() {
            return mBasrformDoc;
        }

        public void setBasrformDoc(String basrformDoc) {
            mBasrformDoc = basrformDoc;
        }

        public String getCabDriverCompanyId() {
            return mCabDriverCompanyId;
        }

        public void setCabDriverCompanyId(String cabDriverCompanyId) {
            mCabDriverCompanyId = cabDriverCompanyId;
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

        public String getCaptainName() {
            return mCaptainName;
        }

        public void setCaptainName(String captainName) {
            mCaptainName = captainName;
        }

        public Object getInsuranceNo() {
            return mInsuranceNo;
        }

        public void setInsuranceNo(Object insuranceNo) {
            mInsuranceNo = insuranceNo;
        }

        public String getLocation() {
            return mLocation;
        }

        public void setLocation(String location) {
            mLocation = location;
        }

        public String getOwnerName() {
            return mOwnerName;
        }

        public void setOwnerName(String ownerName) {
            mOwnerName = ownerName;
        }

        public String getPollutionDoc() {
            return mPollutionDoc;
        }

        public void setPollutionDoc(String pollutionDoc) {
            mPollutionDoc = pollutionDoc;
        }

        public String getRegistrationDoc() {
            return mRegistrationDoc;
        }

        public void setRegistrationDoc(String registrationDoc) {
            mRegistrationDoc = registrationDoc;
        }

        public String getRegistrationNo() {
            return mRegistrationNo;
        }

        public void setRegistrationNo(String registrationNo) {
            mRegistrationNo = registrationNo;
        }

        public String getStatus() {
            return mStatus;
        }

        public void setStatus(String status) {
            mStatus = status;
        }

        public String getVehicleBrandId() {
            return mVehicleBrandId;
        }

        public void setVehicleBrandId(String vehicleBrandId) {
            mVehicleBrandId = vehicleBrandId;
        }

        public String getVehicleColorId() {
            return mVehicleColorId;
        }

        public void setVehicleColorId(String vehicleColorId) {
            mVehicleColorId = vehicleColorId;
        }

        public String getVehicleModelId() {
            return mVehicleModelId;
        }

        public void setVehicleModelId(String vehicleModelId) {
            mVehicleModelId = vehicleModelId;
        }

        public String getVehicleTypeId() {
            return mVehicleTypeId;
        }

        public void setVehicleTypeId(String vehicleTypeId) {
            mVehicleTypeId = vehicleTypeId;
        }

    }
    public class Error {


    }
}
