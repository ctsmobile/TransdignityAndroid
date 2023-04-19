
package com.transdignity.deathserviceprovider.models.request.Services;

import com.google.gson.annotations.SerializedName;


public class CourierRequestModel {
    @SerializedName("date_time")
    private String mDateTime;
    @SerializedName("description")
    private String mDescription;
    @SerializedName("drop_location")
    private String mDropLocation;
    @SerializedName("drop_location_latlong")
    private String mDropLocationLatlong;
    @SerializedName("instructuion_to_carryitem")
    private String mInstructuionToCarryitem;
    @SerializedName("item_name")
    private String mItemName;
    @SerializedName("middle_name")
    private String mMiddleName;
    @SerializedName("pickup_location")
    private String mPickupLocation;
    @SerializedName("pickup_location_latlong")
    private String mPickupLocationLatlong;
    @SerializedName("reciever_first_name")
    private String mRecieverFirstName;
    @SerializedName("reciever_last_name")
    private String mRecieverLastName;
    @SerializedName("reciever_mobile_number")
    private String mRecieverMobileNumber;
    @SerializedName("request_created_by")
    private int mRequestCreatedBy;
    @SerializedName("sender_first_name")
    private String mSenderFirstName;
    @SerializedName("sender_last_name")
    private String mSenderLastName;
    @SerializedName("sender_mobile_number")
    private String mSenderMobileNumber;
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
    public String getDateTime() {
        return mDateTime;
    }

    public void setDateTime(String dateTime) {
        mDateTime = dateTime;
    }
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

    public String getInstructuionToCarryitem() {
        return mInstructuionToCarryitem;
    }

    public void setInstructuionToCarryitem(String instructuionToCarryitem) {
        mInstructuionToCarryitem = instructuionToCarryitem;
    }

    public String getItemName() {
        return mItemName;
    }

    public void setItemName(String itemName) {
        mItemName = itemName;
    }

    public String getMiddleName() {
        return mMiddleName;
    }

    public void setMiddleName(String middleName) {
        mMiddleName = middleName;
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

    public String getRecieverFirstName() {
        return mRecieverFirstName;
    }

    public void setRecieverFirstName(String recieverFirstName) {
        mRecieverFirstName = recieverFirstName;
    }

    public String getRecieverLastName() {
        return mRecieverLastName;
    }

    public void setRecieverLastName(String recieverLastName) {
        mRecieverLastName = recieverLastName;
    }

    public String getRecieverMobileNumber() {
        return mRecieverMobileNumber;
    }

    public void setRecieverMobileNumber(String recieverMobileNumber) {
        mRecieverMobileNumber = recieverMobileNumber;
    }

    public int getRequestCreatedBy() {
        return mRequestCreatedBy;
    }

    public void setRequestCreatedBy(int requestCreatedBy) {
        mRequestCreatedBy = requestCreatedBy;
    }

    public String getSenderFirstName() {
        return mSenderFirstName;
    }

    public void setSenderFirstName(String senderFirstName) {
        mSenderFirstName = senderFirstName;
    }

    public String getSenderLastName() {
        return mSenderLastName;
    }

    public void setSenderLastName(String senderLastName) {
        mSenderLastName = senderLastName;
    }

    public String getSenderMobileNumber() {
        return mSenderMobileNumber;
    }

    public void setSenderMobileNumber(String senderMobileNumber) {
        mSenderMobileNumber = senderMobileNumber;
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
