package com.transdignity.deathserviceprovider.models.request;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class DataItem implements Parcelable {

	@SerializedName("transferred_to_address")
	private String transferredToAddress;

	@SerializedName("bill_to")
	private String billTo;

	@SerializedName("removed_from_address")
	private String removedFromAddress;

	@SerializedName("personal_effects")
	private String personalEffects;

	@SerializedName("time_completed")
	private String timeCompleted;

	@SerializedName("next_of_kin_phone")
	private String nextOfKinPhone;

	@SerializedName("physician_phone")
	private String physicianPhone;

	@SerializedName("body_release")
	private String bodyRelease;

	@SerializedName("call_to")
	private String callTo;

	@SerializedName("type_of_hospital")
	private String typeOfHospital;

	@SerializedName("requested_by")
	private String requestedBy;

	@SerializedName("time_received")
	private String timeReceived;

	@SerializedName("dob")
	private String dob;

	@SerializedName("decendant_name")
	private String decendantName;

	@SerializedName("request_date")
	private String requestDate;

	@SerializedName("next_of_kin_relationship")
	private String nextOfKinRelationship;

	@SerializedName("time_of_death")
	private String timeOfDeath;

	@SerializedName("request_id")
	private String requestId;

	@SerializedName("request_created_by")
	private String requestCreatedBy;

	@SerializedName("age")
	private String age;

	@SerializedName("dsp_status")
	private String status;

	@SerializedName("service_id")
	private String mServiceId;

	@SerializedName("DBrequest_datetime")
	private String mDBrequestDatetime;

	protected DataItem(Parcel in) {
		transferredToAddress = in.readString();
		billTo = in.readString();
		removedFromAddress = in.readString();
		personalEffects = in.readString();
		timeCompleted = in.readString();
		nextOfKinPhone = in.readString();
		physicianPhone = in.readString();
		bodyRelease = in.readString();
		callTo = in.readString();
		typeOfHospital = in.readString();
		requestedBy = in.readString();
		timeReceived = in.readString();
		dob = in.readString();
		decendantName = in.readString();
		requestDate = in.readString();
		nextOfKinRelationship = in.readString();
		timeOfDeath = in.readString();
		requestId = in.readString();
		requestCreatedBy = in.readString();
		age = in.readString();
		status = in.readString();
		mServiceId = in.readString();
		mDBrequestDatetime = in.readString();
	}

	public static final Creator<DataItem> CREATOR = new Creator<DataItem>() {
		@Override
		public DataItem createFromParcel(Parcel in) {
			return new DataItem(in);
		}

		@Override
		public DataItem[] newArray(int size) {
			return new DataItem[size];
		}
	};

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

	public void setPersonalEffects(String personalEffects){
		this.personalEffects = personalEffects;
	}

	public String getPersonalEffects(){
		return personalEffects;
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

	public void setPhysicianPhone(String physicianPhone){
		this.physicianPhone = physicianPhone;
	}

	public String getPhysicianPhone(){
		return physicianPhone;
	}

	public void setBodyRelease(String bodyRelease){
		this.bodyRelease = bodyRelease;
	}

	public String getBodyRelease(){
		return bodyRelease;
	}

	public void setCallTo(String callTo){
		this.callTo = callTo;
	}

	public String getCallTo(){
		return callTo;
	}

	public void setTypeOfHospital(String typeOfHospital){
		this.typeOfHospital = typeOfHospital;
	}

	public String getTypeOfHospital(){
		return typeOfHospital;
	}

	public void setRequestedBy(String requestedBy){
		this.requestedBy = requestedBy;
	}

	public String getRequestedBy(){
		return requestedBy;
	}

	public void setTimeReceived(String timeReceived){
		this.timeReceived = timeReceived;
	}

	public String getTimeReceived(){
		return timeReceived;
	}

	public void setDob(String dob){
		this.dob = dob;
	}

	public String getDob(){
		return dob;
	}

	public void setDecendantName(String decendantName){
		this.decendantName = decendantName;
	}

	public String getDecendantName(){
		return decendantName;
	}

	public void setRequestDate(String requestDate){
		this.requestDate = requestDate;
	}

	public String getRequestDate(){
		return requestDate;
	}

	public void setNextOfKinRelationship(String nextOfKinRelationship){
		this.nextOfKinRelationship = nextOfKinRelationship;
	}

	public String getNextOfKinRelationship(){
		return nextOfKinRelationship;
	}

	public void setTimeOfDeath(String timeOfDeath){
		this.timeOfDeath = timeOfDeath;
	}

	public String getTimeOfDeath(){
		return timeOfDeath;
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

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	public String getmServiceId() {
		return mServiceId;
	}

	public void setmServiceId(String mServiceId) {
		this.mServiceId = mServiceId;
	}

	public String getDBrequestDatetime() {
		return mDBrequestDatetime;
	}

	public void setDBrequestDatetime(String mDBrequestDatetime) {
		this.mDBrequestDatetime = mDBrequestDatetime;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeString(transferredToAddress);
		parcel.writeString(billTo);
		parcel.writeString(removedFromAddress);
		parcel.writeString(personalEffects);
		parcel.writeString(timeCompleted);
		parcel.writeString(nextOfKinPhone);
		parcel.writeString(physicianPhone);
		parcel.writeString(bodyRelease);
		parcel.writeString(callTo);
		parcel.writeString(typeOfHospital);
		parcel.writeString(requestedBy);
		parcel.writeString(timeReceived);
		parcel.writeString(dob);
		parcel.writeString(decendantName);
		parcel.writeString(requestDate);
		parcel.writeString(nextOfKinRelationship);
		parcel.writeString(timeOfDeath);
		parcel.writeString(requestId);
		parcel.writeString(requestCreatedBy);
		parcel.writeString(age);
		parcel.writeString(status);
		parcel.writeString(mServiceId);
		parcel.writeString(mDBrequestDatetime);
	}
}