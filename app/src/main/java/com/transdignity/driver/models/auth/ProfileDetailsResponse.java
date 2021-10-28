package com.transdignity.driver.models.auth;

import com.google.gson.annotations.SerializedName;

public class ProfileDetailsResponse {

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

		@SerializedName("full_name")
		private String fullName;

		@SerializedName("address")
		private String address;

		@SerializedName("phone_number")
		private String phoneNumber;

		@SerializedName("email")
		private String email;

		@SerializedName("mode_of_payment")
		private String modeOfPayment;

		public void setFullName(String fullName){
			this.fullName = fullName;
		}

		public String getFullName(){
			return fullName;
		}

		public void setAddress(String address){
			this.address = address;
		}

		public String getAddress(){
			return address;
		}

		public void setPhoneNumber(String phoneNumber){
			this.phoneNumber = phoneNumber;
		}

		public String getPhoneNumber(){
			return phoneNumber;
		}

		public void setEmail(String email){
			this.email = email;
		}

		public String getEmail(){
			return email;
		}

		public String getModeOfPayment() {
			return modeOfPayment;
		}

		public void setModeOfPayment(String modeOfPayment) {
			this.modeOfPayment = modeOfPayment;
		}
	}
}