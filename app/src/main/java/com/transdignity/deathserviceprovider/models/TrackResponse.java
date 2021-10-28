package com.transdignity.deathserviceprovider.models;

import com.google.gson.annotations.SerializedName;

public class TrackResponse{

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

	public class Error{

	}
	public class Data{

		@SerializedName("reached_on_rs_location_time")
		private String reachedOnRsLocationTime;

		@SerializedName("removed_from_address")
		private String removedFromAddress;

		@SerializedName("drop_decendant_time")
		private String dropDecendantTime;

		@SerializedName("decendant_name")
		private String decendantName;

		@SerializedName("transferred_to_address")
		private String transferedToAddress;

		@SerializedName("removal_specialists_assign_time")
		private String removalSpecialistsAssignTime;

		@SerializedName("steps")
		private Integer steps;

		@SerializedName("pickup_rs_time")
		private String pickupRsTime;

		@SerializedName("cab_driver_assign_time")
		private String cabDriverAssignTime;

		@SerializedName("reached_on_decendant_pickup_location_time")
		private String reachedOnDecendantPickupLocationTime;

		public void setReachedOnRsLocationTime(String reachedOnRsLocationTime){
			this.reachedOnRsLocationTime = reachedOnRsLocationTime;
		}

		public String getReachedOnRsLocationTime(){
			return reachedOnRsLocationTime;
		}

		public void setRemovedFromAddress(String removedFromAddress){
			this.removedFromAddress = removedFromAddress;
		}

		public String getRemovedFromAddress(){
			return removedFromAddress;
		}

		public void setDropDecendantTime(String dropDecendantTime){
			this.dropDecendantTime = dropDecendantTime;
		}

		public String getDropDecendantTime(){
			return dropDecendantTime;
		}

		public void setDecendantName(String decendantName){
			this.decendantName = decendantName;
		}

		public String getDecendantName(){
			return decendantName;
		}

		public void setTransferedToAddress(String transferedFromAddress){
			this.transferedToAddress = transferedFromAddress;
		}

		public String getTransferedToAddress(){
			return transferedToAddress;
		}

		public void setRemovalSpecialistsAssignTime(String removalSpecialistsAssignTime){
			this.removalSpecialistsAssignTime = removalSpecialistsAssignTime;
		}

		public String getRemovalSpecialistsAssignTime(){
			return removalSpecialistsAssignTime;
		}

		public void setSteps(Integer steps){
			this.steps = steps;
		}

		public Integer getSteps(){
			return steps;
		}

		public void setPickupRsTime(String pickupRsTime){
			this.pickupRsTime = pickupRsTime;
		}

		public String getPickupRsTime(){
			return pickupRsTime;
		}

		public void setCabDriverAssignTime(String cabDriverAssignTime){
			this.cabDriverAssignTime = cabDriverAssignTime;
		}

		public String getCabDriverAssignTime(){
			return cabDriverAssignTime;
		}

		public void setReachedOnDecendantPickupLocationTime(String reachedOnDecendantPickupLocationTime){
			this.reachedOnDecendantPickupLocationTime = reachedOnDecendantPickupLocationTime;
		}

		public String getReachedOnDecendantPickupLocationTime(){
			return reachedOnDecendantPickupLocationTime;
		}
	}
}