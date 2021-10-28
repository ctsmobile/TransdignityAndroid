package com.cts.removalspecialist.models.auth;

import com.google.gson.annotations.SerializedName;
public class LoginRequest{

	@SerializedName("password")
	private String password;

	@SerializedName("device_id")
	private String deviceId;

	@SerializedName("fcm_key")
	private String fcmKey;

	@SerializedName("device")
	private String device;

	@SerializedName("email")
	private String email;

	@SerializedName("user_group_id")
	private String userGroupId;

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return password;
	}

	public void setDeviceId(String deviceId){
		this.deviceId = deviceId;
	}

	public String getDeviceId(){
		return deviceId;
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

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	public void setUserGroupId(String userGroupId){
		this.userGroupId = userGroupId;
	}

	public String getUserGroupId(){
		return userGroupId;
	}
}