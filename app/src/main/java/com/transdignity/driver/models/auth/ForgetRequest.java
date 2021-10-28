package com.transdignity.driver.models.auth;

import com.google.gson.annotations.SerializedName;

public class ForgetRequest{
	private String email;
	@SerializedName("user_group_id")
	private String userGroupId;

	public ForgetRequest(String email, String userGroupId) {
		this.email = email;
		this.userGroupId = userGroupId;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	public void setUserGroupId(String userGroupId){
		this.userGroupId = userGroupId;
	}

	public String getUserGroupId(){
		return userGroupId;
	}
}
