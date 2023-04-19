package com.transdignity.deathserviceprovider.models.request.newRequest;

import com.google.gson.annotations.SerializedName;

public class NewRequestReq{

	@SerializedName("transferred_to_address")
	private String transferredToAddress;

	@SerializedName("bill_to")
	private String billTo;

	@SerializedName("removed_from_address")
	private String removedFromAddress;

	@SerializedName("personal_effects")
	private int personalEffects;

	@SerializedName("physician_name")
	private String physicianName;

	@SerializedName("requested_items_lists")
	private String requestedItemsLists;

	@SerializedName("physician_phone")
	private String physicianPhone;

	@SerializedName("personal_effects_lists")
	private String personalEffectsLists;

	@SerializedName("date_of_death")
	private String dateOfDeath;

	@SerializedName("time_received")
	private String timeReceived;

	@SerializedName("from_address_latlong")
	private String fromAddressLatlong;

	@SerializedName("time_of_death")
	private String timeOfDeath;

	@SerializedName("first_name")
	private String firstName;

	@SerializedName("device_id")
	private String deviceId;

	@SerializedName("time_completed")
	private String timeCompleted;

	@SerializedName("next_of_kin_phone")
	private String nextOfKinPhone;

	@SerializedName("fcm_key")
	private String fcmKey;

	@SerializedName("last_name")
	private String lastName;

	@SerializedName("middle_name")
	private String middleName;

	@SerializedName("body_release")
	private int bodyRelease;

	@SerializedName("requested_by")
	private String requestedBy;

	@SerializedName("dob")
	private String dob;

	@SerializedName("requested_items")
	private int requestedItems;

	@SerializedName("request_date")
	private String requestDate;

	@SerializedName("to_address_latlong")
	private String toAddressLatlong;

	@SerializedName("next_of_kin_relationship")
	private String nextOfKinRelationship;

	@SerializedName("hospital_type_id")
	private int hospitalTypeId;

	@SerializedName("device")
	private String device;

	@SerializedName("request_created_by")
	private int requestCreatedBy;

	@SerializedName("age")
	private int age;

	@SerializedName("service_id")
	private int serviceId;

	public int getServiceId() {
		return serviceId;
	}

	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}

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

	public void setRemovedFromAddress(String removedFromAddress){
		this.removedFromAddress = removedFromAddress;
	}

	public String getRemovedFromAddress(){
		return removedFromAddress;
	}

	public void setPersonalEffects(int personalEffects){
		this.personalEffects = personalEffects;
	}

	public int getPersonalEffects(){
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

	public void setFromAddressLatlong(String fromAddressLatlong){
		this.fromAddressLatlong = fromAddressLatlong;
	}

	public String getFromAddressLatlong(){
		return fromAddressLatlong;
	}

	public void setTimeOfDeath(String timeOfDeath){
		this.timeOfDeath = timeOfDeath;
	}

	public String getTimeOfDeath(){
		return timeOfDeath;
	}

	public void setFirstName(String firstName){
		this.firstName = firstName;
	}

	public String getFirstName(){
		return firstName;
	}

	public void setDeviceId(String deviceId){
		this.deviceId = deviceId;
	}

	public String getDeviceId(){
		return deviceId;
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

	public void setFcmKey(String fcmKey){
		this.fcmKey = fcmKey;
	}

	public String getFcmKey(){
		return fcmKey;
	}

	public void setLastName(String lastName){
		this.lastName = lastName;
	}

	public String getLastName(){
		return lastName;
	}

	public void setMiddleName(String middleName){
		this.middleName = middleName;
	}

	public String getMiddleName(){
		return middleName;
	}

	public void setBodyRelease(int bodyRelease){
		this.bodyRelease = bodyRelease;
	}

	public int getBodyRelease(){
		return bodyRelease;
	}

	public void setRequestedBy(String requestedBy){
		this.requestedBy = requestedBy;
	}

	public String getRequestedBy(){
		return requestedBy;
	}

	public void setDob(String dob){
		this.dob = dob;
	}

	public String getDob(){
		return dob;
	}

	public void setRequestedItems(int requestedItems){
		this.requestedItems = requestedItems;
	}

	public int getRequestedItems(){
		return requestedItems;
	}

	public void setRequestDate(String requestDate){
		this.requestDate = requestDate;
	}

	public String getRequestDate(){
		return requestDate;
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

	public void setHospitalTypeId(int hospitalTypeId){
		this.hospitalTypeId = hospitalTypeId;
	}

	public int getHospitalTypeId(){
		return hospitalTypeId;
	}

	public void setDevice(String device){
		this.device = device;
	}

	public String getDevice(){
		return device;
	}

	public void setRequestCreatedBy(int requestCreatedBy){
		this.requestCreatedBy = requestCreatedBy;
	}

	public int getRequestCreatedBy(){
		return requestCreatedBy;
	}

	public void setAge(int age){
		this.age = age;
	}

	public int getAge(){
		return age;
	}
}