package com.cts.removalspecialist.models.auth;

import com.google.gson.annotations.SerializedName;

public class ChangePasswordReq{

	@SerializedName("old_password")
	private String oldPassword;

	@SerializedName("device_id")
	private String deviceId;

	@SerializedName("user_id")
	private String userId;

	@SerializedName("fcm_key")
	private String fcmKey;

	@SerializedName("device")
	private String device;

	@SerializedName("password")
	private String password;

	@SerializedName("user_group_id")
	private String userGroupId;

	public void setOldPassword(String oldPassword){
		this.oldPassword = oldPassword;
	}

	public String getOldPassword(){
		return oldPassword;
	}

	public void setDeviceId(String deviceId){
		this.deviceId = deviceId;
	}

	public String getDeviceId(){
		return deviceId;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
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

	public void setpassword(String password){
		this.password = password;
	}

	public String getpassword(){
		return password;
	}

	public String getUserGroupId() {
		return userGroupId;
	}

	public void setUserGroupId(String userGroupId) {
		this.userGroupId = userGroupId;
	}
}