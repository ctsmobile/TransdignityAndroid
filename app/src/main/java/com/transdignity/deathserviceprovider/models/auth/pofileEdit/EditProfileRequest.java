package com.transdignity.deathserviceprovider.models.auth.pofileEdit;

import com.google.gson.annotations.SerializedName;

public class EditProfileRequest{

	@SerializedName("address")
	private String address;

	@SerializedName("device_id")
	private String deviceId;

	@SerializedName("user_id")
	private int userId;

	@SerializedName("name_of_business")
	private String nameOfBusiness;

	@SerializedName("fcm_key")
	private String fcmKey;

	@SerializedName("device")
	private String device;

	@SerializedName("primary_personal_phone")
	private String primaryPersonalPhone;

	@SerializedName("user_group_id")
	private int userGroupId;

	public void setAddress(String address){
		this.address = address;
	}

	public String getAddress(){
		return address;
	}

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

	public void setNameOfBusiness(String nameOfBusiness){
		this.nameOfBusiness = nameOfBusiness;
	}

	public String getNameOfBusiness(){
		return nameOfBusiness;
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

	public void setPrimaryPersonalPhone(String primaryPersonalPhone){
		this.primaryPersonalPhone = primaryPersonalPhone;
	}

	public String getPrimaryPersonalPhone(){
		return primaryPersonalPhone;
	}

	public void setUserGroupId(int userGroupId){
		this.userGroupId = userGroupId;
	}

	public int getUserGroupId(){
		return userGroupId;
	}
}