package com.transdignity.deathserviceprovider.models.auth;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ForgetRequest{
	public ForgetRequest(String email,String userGroupId) {
		this.email = email;
		this.userGroupId = userGroupId;
	}
	@SerializedName("user_group_id")
	@Expose
	private String userGroupId;
	@SerializedName("email")
	private String email;

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	public String getUserGroupId() {
		return userGroupId;
	}

	public void setUserGroupId(String userGroupId) {
		this.userGroupId = userGroupId;
	}
}