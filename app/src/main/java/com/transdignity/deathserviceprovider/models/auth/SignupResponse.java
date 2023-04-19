package com.transdignity.deathserviceprovider.models.auth;

import com.google.gson.annotations.SerializedName;

public class SignupResponse{

	@SerializedName("data")
	private Data data;

	@SerializedName("success")
	private String success;

	@SerializedName("title")
	private String title;

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
	public class Data{

	}
	public class Error{

	}
}