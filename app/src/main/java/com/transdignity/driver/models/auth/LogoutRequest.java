package com.transdignity.driver.models.auth;

import com.google.gson.annotations.SerializedName;

public class LogoutRequest{

	@SerializedName("device_id")
	private String deviceId;

	@SerializedName("user_id")
	private int userId;

	@SerializedName("fcm_key")
	private String fcmKey;

	@SerializedName("device")
	private String device;

	public void setDeviceId(String deviceId){
		this.deviceId = deviceId;
	}

	public String getDeviceId(){
		return deviceId;
	}

	public void setUserId(int userId){
		this.userId = userId;
	}

	public int getUserId(){
		return userId;
	}

	public void setFcmKey(String fcmKey){
		this.fcmKey = fcmKey;
	}

	public String getFcmKey(){
		return fcmKey;
	}

	public void setDevice(String device){
		this.device = device;
	}

	public String getDevice(){
		return device;
	}
}