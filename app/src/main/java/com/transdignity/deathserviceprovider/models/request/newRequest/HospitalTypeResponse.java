package com.transdignity.deathserviceprovider.models.request.newRequest;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class HospitalTypeResponse{

	@SerializedName("data")
	private List<DataItem> data;

	@SerializedName("success")
	private String success;

	@SerializedName("title")
	private String title;

	@SerializedName("message")
	private String message;

	@SerializedName("error")
	private Error error;

	public void setData(List<DataItem> data){
		this.data = data;
	}

	public List<DataItem> getData(){
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

	public class DataItem{

		@SerializedName("hospital_type_id")
		private String hospitalTypeId;

		@SerializedName("hospital_type")
		private String hospitalType;

		@SerializedName("status")
		private String status;

		public void setHospitalTypeId(String hospitalTypeId){
			this.hospitalTypeId = hospitalTypeId;
		}

		public String getHospitalTypeId(){
			return hospitalTypeId;
		}

		public void setHospitalType(String hospitalType){
			this.hospitalType = hospitalType;
		}

		public String getHospitalType(){
			return hospitalType;
		}

		public void setStatus(String status){
			this.status = status;
		}

		public String getStatus(){
			return status;
		}
	}
}