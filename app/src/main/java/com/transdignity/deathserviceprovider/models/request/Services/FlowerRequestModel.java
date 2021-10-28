
package com.transdignity.deathserviceprovider.models.request.Services;

import com.google.gson.annotations.SerializedName;


public class FlowerRequestModel {

    @SerializedName("address")
    private String mAddress;
    @SerializedName("address_latlong")
    private String mAddressLatlong;
    @SerializedName("description")
    private String mDescription;
    @SerializedName("first_name")
    private String mFirstName;
    @SerializedName("flower_name")
    private String mFlowerName;
    @SerializedName("last_name")
    private String mLastName;
    @SerializedName("middle_name")
    private String mMiddleName;
    @SerializedName("mobile_number")
    private String mMobileNumber;
    @SerializedName("quantity")
    private String mQuantity;
    @SerializedName("request_created_by")
    private int mRequestCreatedBy;
    @SerializedName("service_id")
    private int mServiceId;
    @SerializedName("device_id")
    private String deviceId;
    @SerializedName("fcm_key")
    private String fcmKey;
    @SerializedName("device")
    private String device;
    @SerializedName("request_id")
    private String requestid;
    @SerializedName("request_date")
    private String requestDate;

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public String getAddressLatlong() {
        return mAddressLatlong;
    }

    public void setAddressLatlong(String addressLatlong) {
        mAddressLatlong = addressLatlong;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        mFirstName = firstName;
    }

    public String getFlowerName() {
        return mFlowerName;
    }

    public void setFlowerName(String flowerName) {
        mFlowerName = flowerName;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String lastName) {
        mLastName = lastName;
    }

    public String getMiddleName() {
        return mMiddleName;
    }

    public void setMiddleName(String middleName) {
        mMiddleName = middleName;
    }

    public String getMobileNumber() {
        return mMobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        mMobileNumber = mobileNumber;
    }

    public String getQuantity() {
        return mQuantity;
    }

    public void setQuantity(String quantity) {
        mQuantity = quantity;
    }

    public int getRequestCreatedBy() {
        return mRequestCreatedBy;
    }

    public void setRequestCreatedBy(int requestCreatedBy) {
        mRequestCreatedBy = requestCreatedBy;
    }

    public int getServiceId() {
        return mServiceId;
    }

    public void setServiceId(int serviceId) {
        mServiceId = serviceId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getFcmKey() {
        return fcmKey;
    }

    public void setFcmKey(String fcmKey) {
        this.fcmKey = fcmKey;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getRequestid() {
        return requestid;
    }

    public void setRequestid(String requestid) {
        this.requestid = requestid;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }
}
