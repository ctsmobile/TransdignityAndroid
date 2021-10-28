package com.transdignity.driver.models.auth.pofileEdit;

import com.google.gson.annotations.SerializedName;

public class EditProfileResponse{

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
}