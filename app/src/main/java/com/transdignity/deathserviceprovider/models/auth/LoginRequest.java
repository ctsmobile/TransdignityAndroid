package com.transdignity.deathserviceprovider.models.auth;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginRequest {

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("device_id")
    @Expose
    private String deviceId;

    @SerializedName("fcm_key")
    @Expose
    private String fcmKey;

    @SerializedName("device")
    @Expose
    private String device;

    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("user_group_id")
    @Expose
    private String userGroupId;

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getUserGroupId() {
        return userGroupId;
    }

    public void setUserGroupId(String userGroupId) {
        this.userGroupId = userGroupId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setFcmKey(String fcmKey) {
        this.fcmKey = fcmKey;
    }

    public String getFcmKey() {
        return fcmKey;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getDevice() {
        return device;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}