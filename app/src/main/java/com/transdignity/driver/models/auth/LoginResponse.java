package com.transdignity.driver.models.auth;

import com.google.gson.annotations.SerializedName;

public class LoginResponse{

	@SerializedName("data")
	private Data data;

	@SerializedName("success")
	private String success;

	@SerializedName("token_valid")
	private String token_valid;

	@SerializedName("message")
	private String message;

	@SerializedName("error")
	private Error error;

	public void setData(Data data){
		this.data = data;
	}

	public Data getData(){
		return data;
	}

	public void setSuccess(String success){
		this.success = success;
	}

	public String getSuccess(){
		return success;
	}

	public void setTokenValid(String token_valid){
		this.token_valid = token_valid;
	}

	public String getTokenValid(){
		return token_valid;
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
	public class Error{

	}
	public class Data{

		@SerializedName("address")
		private String address;

		@SerializedName("otp")
		private String otp;

		@SerializedName("id")
		private String id;

		@SerializedName("user_group_id")
		private String userGroupId;

		@SerializedName("email")
		private String email;

		@SerializedName("username")
		private String username;

		@SerializedName("status")
		private String status;

		@SerializedName("token")
		private String token;

		public void setAddress(String address){
			this.address = address;
		}

		public String getAddress(){
			return address;
		}

		public void setOtp(String otp){
			this.otp = otp;
		}

		public String getOtp(){
			return otp;
		}

		public void setId(String id){
			this.id = id;
		}

		public String getId(){
			return id;
		}

		public void setUserGroupId(String userGroupId){
			this.userGroupId = userGroupId;
		}

		public String getUserGroupId(){
			return userGroupId;
		}

		public void setEmail(String email){
			this.email = email;
		}

		public String getEmail(){
			return email;
		}

		public void setUsername(String username){
			this.username = username;
		}

		public String getUsername(){
			return username;
		}

		public void setStatus(String status){
			this.status = status;
		}

		public String getStatus(){
			return status;
		}

		public void setToken(String token){
			this.token = token;
		}

		public String getToken(){
			return token;
		}
	}
}