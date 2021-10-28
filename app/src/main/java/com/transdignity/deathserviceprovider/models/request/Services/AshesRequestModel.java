
package com.transdignity.deathserviceprovider.models.request.Services;

import com.google.gson.annotations.SerializedName;


public class AshesRequestModel {

    @SerializedName("description")
    private String mDescription;
    @SerializedName("drop_location")
    private String mDropLocation;
    @SerializedName("drop_location_latlong")
    private String mDropLocationLatlong;
    @SerializedName("first_name")
    private String mFirstName;
    @SerializedName("last_name")
    private String mLastName;
    @SerializedName("middle_name")
    private String mMiddleName;
    @SerializedName("mobile_number")
    private String mMobileNumber;
    @SerializedName("pickup_location")
    private String mPickupLocation;
    @SerializedName("pickup_location_latlong")
    private String mPickupLocationLatlong;
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
    @SerializedName("request_date")
    private String requestDate;
    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getDropLocation() {
        return mDropLocation;
    }

    public void setDropLocation(String dropLocation) {
        mDropLocation = dropLocation;
    }

    public String getDropLocationLatlong() {
        return mDropLocationLatlong;
    }

    public void setDropLocationLatlong(String dropLocationLatlong) {
        mDropLocationLatlong = dropLocationLatlong;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        mFirstName = firstName;
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

    public String getPickupLocation() {
        return mPickupLocation;
    }

    public void setPickupLocation(String pickupLocation) {
        mPickupLocation = pickupLocation;
    }

    public String getPickupLocationLatlong() {
        return mPickupLocationLatlong;
    }

    public void setPickupLocationLatlong(String pickupLocationLatlong) {
        mPickupLocationLatlong = pickupLocationLatlong;
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

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }
}
