package com.transdignity.deathserviceprovider.models.auth;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse{

	@SerializedName("data")
	private Datum data;

	@SerializedName("success")
	private String success;

	@SerializedName("title")
	private String title;

	@SerializedName("message")
	private String message;

	@SerializedName("error")
	private Error error;

	public void setData(Datum data){
		this.data = data;
	}

	public Datum getData(){
		return data;
	}

	public void setSuccess(String success){
		this.success = success;
	}

	public String getSuccess(){
		return success;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setError(Error error){
		this.error = error;
	}

	public Error getError(){
		return error;
	}

	public class Datum{
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("business_name")
        @Expose
        private String businessName;
        @SerializedName("user_group_id")
        @Expose
        private String userGroupId;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("years_in_business")
        @Expose
        private String yearsInBusiness;
        @SerializedName("license_number")
        @Expose
        private String licenseNumber;
        @SerializedName("token")
        @Expose
        private String token;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getBusinessName() {
            return businessName;
        }

        public void setBusinessName(String businessName) {
            this.businessName = businessName;
        }

        public String getUserGroupId() {
            return userGroupId;
        }

        public void setUserGroupId(String userGroupId) {
            this.userGroupId = userGroupId;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getYearsInBusiness() {
            return yearsInBusiness;
        }

        public void setYearsInBusiness(String yearsInBusiness) {
            this.yearsInBusiness = yearsInBusiness;
        }

        public String getLicenseNumber() {
            return licenseNumber;
        }

        public void setLicenseNumber(String licenseNumber) {
            this.licenseNumber = licenseNumber;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }

}