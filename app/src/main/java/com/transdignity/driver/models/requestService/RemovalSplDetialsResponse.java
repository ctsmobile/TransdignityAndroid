package com.transdignity.driver.models.requestService;

import com.google.gson.annotations.SerializedName;

public class RemovalSplDetialsResponse {

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
	public static class Data{

		@SerializedName("address")
		private String address;

		@SerializedName("distance")
		private String distance;

		@SerializedName("latitude")
		private String latitude;

		@SerializedName("name")
		private String name;

		@SerializedName("phone_number")
		private String phoneNumber;

		@SerializedName("time")
		private String time;

		@SerializedName("request_id")
		private String requestId;

		@SerializedName("longitude")
		private String longitude;

		public void setAddress(String address){
			this.address = address;
		}

		public String getAddress(){
			return address;
		}

		public void setDistance(String distance){
			this.distance = distance;
		}

		public String getDistance(){
			return distance;
		}

		public void setLatitude(String latitude){
			this.latitude = latitude;
		}

		public String getLatitude(){
			return latitude;
		}

		public void setName(String name){
			this.name = name;
		}

		public String getName(){
			return name;
		}

		public void setPhoneNumber(String phoneNumber){
			this.phoneNumber = phoneNumber;
		}

		public String getPhoneNumber(){
			return phoneNumber;
		}

		public void setTime(String time){
			this.time = time;
		}

		public String getTime(){
			return time;
		}

		public void setRequestId(String requestId){
			this.requestId = requestId;
		}

		public String getRequestId(){
			return requestId;
		}

		public void setLongitude(String longitude){
			this.longitude = longitude;
		}

		public String getLongitude(){
			return longitude;
		}
	}
	public class Error{
	}
}
