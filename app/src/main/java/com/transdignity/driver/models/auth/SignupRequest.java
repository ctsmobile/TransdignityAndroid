package com.transdignity.driver.models.auth;

import com.google.gson.annotations.SerializedName;

public class 	SignupRequest {

	@SerializedName("secondary_ofc_phone")
	private String secondaryOfcPhone;

	@SerializedName("secondary_title")
	private String secondaryTitle;

	@SerializedName("address")
	private String address;

	@SerializedName("device_id")
	private String deviceId;

	@SerializedName("name_of_business")
	private String nameOfBusiness;

	@SerializedName("fcm_key")
	private String fcmKey;

	@SerializedName("primary_contact_name")
	private String primaryContactName;

	@SerializedName("primary_title")
	private String primaryTitle;

	@SerializedName("primary_ofc_phone")
	private String primaryOfcPhone;

	@SerializedName("secondary_email")
	private String secondaryEmail;

	@SerializedName("license_number")
	private String licenseNumber;

	@SerializedName("confirm_password")
	private String confirmPassword;

	@SerializedName("password")
	private String password;

	@SerializedName("secondary_personal_phone")
	private String secondaryPersonalPhone;

	@SerializedName("secondary_contact_name")
	private String secondaryContactName;

	@SerializedName("years_in_business")
	private int yearsInBusiness;

	@SerializedName("primary_email")
	private String primaryEmail;

	@SerializedName("device")
	private String device;

	@SerializedName("primary_personal_phone")
	private String primaryPersonalPhone;

	@SerializedName("user_group_id")
	private int userGroupId;

	public void setSecondaryOfcPhone(String secondaryOfcPhone){
		this.secondaryOfcPhone = secondaryOfcPhone;
	}

	public String getSecondaryOfcPhone(){
		return secondaryOfcPhone;
	}

	public void setSecondaryTitle(String secondaryTitle){
		this.secondaryTitle = secondaryTitle;
	}

	public String getSecondaryTitle(){
		return secondaryTitle;
	}

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

	public void setPrimaryContactName(String primaryContactName){
		this.primaryContactName = primaryContactName;
	}

	public String getPrimaryContactName(){
		return primaryContactName;
	}

	public void setPrimaryTitle(String primaryTitle){
		this.primaryTitle = primaryTitle;
	}

	public String getPrimaryTitle(){
		return primaryTitle;
	}

	public void setPrimaryOfcPhone(String primaryOfcPhone){
		this.primaryOfcPhone = primaryOfcPhone;
	}

	public String getPrimaryOfcPhone(){
		return primaryOfcPhone;
	}

	public void setSecondaryEmail(String secondaryEmail){
		this.secondaryEmail = secondaryEmail;
	}

	public String getSecondaryEmail(){
		return secondaryEmail;
	}

	public void setLicenseNumber(String licenseNumber){
		this.licenseNumber = licenseNumber;
	}

	public String getLicenseNumber(){
		return licenseNumber;
	}

	public void setConfirmPassword(String confirmPassword){
		this.confirmPassword = confirmPassword;
	}

	public String getConfirmPassword(){
		return confirmPassword;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return password;
	}

	public void setSecondaryPersonalPhone(String secondaryPersonalPhone){
		this.secondaryPersonalPhone = secondaryPersonalPhone;
	}

	public String getSecondaryPersonalPhone(){
		return secondaryPersonalPhone;
	}

	public void setSecondaryContactName(String secondaryContactName){
		this.secondaryContactName = secondaryContactName;
	}

	public String getSecondaryContactName(){
		return secondaryContactName;
	}

	public void setYearsInBusiness(int yearsInBusiness){
		this.yearsInBusiness = yearsInBusiness;
	}

	public int getYearsInBusiness(){
		return yearsInBusiness;
	}

	public void setPrimaryEmail(String primaryEmail){
		this.primaryEmail = primaryEmail;
	}

	public String getPrimaryEmail(){
		return primaryEmail;
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