package com.cts.removalspecialist.models.requestList;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class RequestListResponse{

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

	public class Error{

	}
	public class DataItem{

		@SerializedName("transferred_to_address")
		private String transferredToAddress;

		@SerializedName("bill_to")
		private String billTo;

		@SerializedName("reached_on_rs_location_time")
		private String reachedOnRsLocationTime;

		@SerializedName("removed_from_address")
		private String removedFromAddress;

		@SerializedName("cab_id")
		private String cabId;

		@SerializedName("personal_effects")
		private String personalEffects;

		@SerializedName("physician_name")
		private String physicianName;

		@SerializedName("requested_items_lists")
		private String requestedItemsLists;

		@SerializedName("physician_phone")
		private String physicianPhone;

		@SerializedName("personal_effects_lists")
		private String personalEffectsLists;

		@SerializedName("reached_on_decendant_pickup_location_time")
		private String reachedOnDecendantPickupLocationTime;

		@SerializedName("date_of_death")
		private String dateOfDeath;

		@SerializedName("time_received")
		private String timeReceived;

		@SerializedName("drop_decendant_time")
		private String dropDecendantTime;

		@SerializedName("decendant_name")
		private String decendantName;

		@SerializedName("from_address_latlong")
		private String fromAddressLatlong;

		@SerializedName("admin_status")
		private String adminStatus;

		@SerializedName("rs_phone")
		private String rsPhone;

		@SerializedName("time_of_death")
		private String timeOfDeath;

		@SerializedName("decendant_last_name")
		private String decendantLastName;

		@SerializedName("pickup_rs_time")
		private String pickupRsTime;

		@SerializedName("cab_driver_assign_time")
		private String cabDriverAssignTime;

		@SerializedName("dsp_status")
		private String dspStatus;

		@SerializedName("cab_name")
		private String cabName;

		@SerializedName("time_completed")
		private String timeCompleted;

		@SerializedName("next_of_kin_phone")
		private String nextOfKinPhone;

		@SerializedName("cab_no")
		private String cabNo;

		@SerializedName("is_btn")
		private int isBtn;

		@SerializedName("cd_phone")
		private String cdPhone;

		@SerializedName("steps")
		private String steps;

		@SerializedName("hospital_type")
		private String hospitalType;

		@SerializedName("body_release")
		private String bodyRelease;

		@SerializedName("rs_status")
		private String rsStatus;

		@SerializedName("cd_name")
		private String cdName;

		@SerializedName("decendant_middle_name")
		private String decendantMiddleName;

		@SerializedName("requested_by")
		private String requestedBy;

		@SerializedName("decendant_first_name")
		private String decendantFirstName;

		@SerializedName("dob")
		private String dob;

		@SerializedName("requested_items")
		private String requestedItems;

		@SerializedName("request_date")
		private String requestDate;

		@SerializedName("rs_name")
		private String rsName;

		@SerializedName("to_address_latlong")
		private String toAddressLatlong;

		@SerializedName("next_of_kin_relationship")
		private String nextOfKinRelationship;

		@SerializedName("removal_specialists_assign_time")
		private String removalSpecialistsAssignTime;

		@SerializedName("hospital_type_id")
		private String hospitalTypeId;

		@SerializedName("request_id")
		private String requestId;

		@SerializedName("request_created_by")
		private String requestCreatedBy;

		@SerializedName("age")
		private String age;

		@SerializedName("service_id")
		private String mServiceId;

		public void setTransferredToAddress(String transferredToAddress){
			this.transferredToAddress = transferredToAddress;
		}

		public String getTransferredToAddress(){
			return transferredToAddress;
		}

		public void setBillTo(String billTo){
			this.billTo = billTo;
		}

		public String getBillTo(){
			return billTo;
		}

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

		public void setCabId(String cabId){
			this.cabId = cabId;
		}

		public String getCabId(){
			return cabId;
		}

		public void setPersonalEffects(String personalEffects){
			this.personalEffects = personalEffects;
		}

		public String getPersonalEffects(){
			return personalEffects;
		}

		public void setPhysicianName(String physicianName){
			this.physicianName = physicianName;
		}

		public String getPhysicianName(){
			return physicianName;
		}

		public void setRequestedItemsLists(String requestedItemsLists){
			this.requestedItemsLists = requestedItemsLists;
		}

		public String getRequestedItemsLists(){
			return requestedItemsLists;
		}

		public void setPhysicianPhone(String physicianPhone){
			this.physicianPhone = physicianPhone;
		}

		public String getPhysicianPhone(){
			return physicianPhone;
		}

		public void setPersonalEffectsLists(String personalEffectsLists){
			this.personalEffectsLists = personalEffectsLists;
		}

		public String getPersonalEffectsLists(){
			return personalEffectsLists;
		}

		public void setReachedOnDecendantPickupLocationTime(String reachedOnDecendantPickupLocationTime){
			this.reachedOnDecendantPickupLocationTime = reachedOnDecendantPickupLocationTime;
		}

		public String getReachedOnDecendantPickupLocationTime(){
			return reachedOnDecendantPickupLocationTime;
		}

		public void setDateOfDeath(String dateOfDeath){
			this.dateOfDeath = dateOfDeath;
		}

		public String getDateOfDeath(){
			return dateOfDeath;
		}

		public void setTimeReceived(String timeReceived){
			this.timeReceived = timeReceived;
		}

		public String getTimeReceived(){
			return timeReceived;
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

		public void setFromAddressLatlong(String fromAddressLatlong){
			this.fromAddressLatlong = fromAddressLatlong;
		}

		public String getFromAddressLatlong(){
			return fromAddressLatlong;
		}

		public void setAdminStatus(String adminStatus){
			this.adminStatus = adminStatus;
		}

		public String getAdminStatus(){
			return adminStatus;
		}

		public void setRsPhone(String rsPhone){
			this.rsPhone = rsPhone;
		}

		public String getRsPhone(){
			return rsPhone;
		}

		public void setTimeOfDeath(String timeOfDeath){
			this.timeOfDeath = timeOfDeath;
		}

		public String getTimeOfDeath(){
			return timeOfDeath;
		}

		public void setDecendantLastName(String decendantLastName){
			this.decendantLastName = decendantLastName;
		}

		public String getDecendantLastName(){
			return decendantLastName;
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

		public void setDspStatus(String dspStatus){
			this.dspStatus = dspStatus;
		}

		public String getDspStatus(){
			return dspStatus;
		}

		public void setCabName(String cabName){
			this.cabName = cabName;
		}

		public String getCabName(){
			return cabName;
		}

		public void setTimeCompleted(String timeCompleted){
			this.timeCompleted = timeCompleted;
		}

		public String getTimeCompleted(){
			return timeCompleted;
		}

		public void setNextOfKinPhone(String nextOfKinPhone){
			this.nextOfKinPhone = nextOfKinPhone;
		}

		public String getNextOfKinPhone(){
			return nextOfKinPhone;
		}

		public void setCabNo(String cabNo){
			this.cabNo = cabNo;
		}

		public String getCabNo(){
			return cabNo;
		}

		public void setIsBtn(int isBtn){
			this.isBtn = isBtn;
		}

		public int getIsBtn(){
			return isBtn;
		}

		public void setCdPhone(String cdPhone){
			this.cdPhone = cdPhone;
		}

		public String getCdPhone(){
			return cdPhone;
		}

		public void setSteps(String steps){
			this.steps = steps;
		}

		public String getSteps(){
			return steps;
		}

		public void setHospitalType(String hospitalType){
			this.hospitalType = hospitalType;
		}

		public String getHospitalType(){
			return hospitalType;
		}

		public void setBodyRelease(String bodyRelease){
			this.bodyRelease = bodyRelease;
		}

		public String getBodyRelease(){
			return bodyRelease;
		}

		public void setRsStatus(String rsStatus){
			this.rsStatus = rsStatus;
		}

		public String getRsStatus(){
			return rsStatus;
		}

		public void setCdName(String cdName){
			this.cdName = cdName;
		}

		public String getCdName(){
			return cdName;
		}

		public void setDecendantMiddleName(String decendantMiddleName){
			this.decendantMiddleName = decendantMiddleName;
		}

		public String getDecendantMiddleName(){
			return decendantMiddleName;
		}

		public void setRequestedBy(String requestedBy){
			this.requestedBy = requestedBy;
		}

		public String getRequestedBy(){
			return requestedBy;
		}

		public void setDecendantFirstName(String decendantFirstName){
			this.decendantFirstName = decendantFirstName;
		}

		public String getDecendantFirstName(){
			return decendantFirstName;
		}

		public void setDob(String dob){
			this.dob = dob;
		}

		public String getDob(){
			return dob;
		}

		public void setRequestedItems(String requestedItems){
			this.requestedItems = requestedItems;
		}

		public String getRequestedItems(){
			return requestedItems;
		}

		public void setRequestDate(String requestDate){
			this.requestDate = requestDate;
		}

		public String getRequestDate(){
			return requestDate;
		}

		public void setRsName(String rsName){
			this.rsName = rsName;
		}

		public String getRsName(){
			return rsName;
		}

		public void setToAddressLatlong(String toAddressLatlong){
			this.toAddressLatlong = toAddressLatlong;
		}

		public String getToAddressLatlong(){
			return toAddressLatlong;
		}

		public void setNextOfKinRelationship(String nextOfKinRelationship){
			this.nextOfKinRelationship = nextOfKinRelationship;
		}

		public String getNextOfKinRelationship(){
			return nextOfKinRelationship;
		}

		public void setRemovalSpecialistsAssignTime(String removalSpecialistsAssignTime){
			this.removalSpecialistsAssignTime = removalSpecialistsAssignTime;
		}

		public String getRemovalSpecialistsAssignTime(){
			return removalSpecialistsAssignTime;
		}

		public void setHospitalTypeId(String hospitalTypeId){
			this.hospitalTypeId = hospitalTypeId;
		}

		public String getHospitalTypeId(){
			return hospitalTypeId;
		}

		public void setRequestId(String requestId){
			this.requestId = requestId;
		}

		public String getRequestId(){
			return requestId;
		}

		public void setRequestCreatedBy(String requestCreatedBy){
			this.requestCreatedBy = requestCreatedBy;
		}

		public String getRequestCreatedBy(){
			return requestCreatedBy;
		}

		public void setAge(String age){
			this.age = age;
		}

		public String getAge(){
			return age;
		}

		public String getServiceId() {
			return mServiceId;
		}

		public void setServiceId(String mServiceId) {
			this.mServiceId = mServiceId;
		}
	}
}