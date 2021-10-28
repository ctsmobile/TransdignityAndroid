package com.transdignity.deathserviceprovider.models.requestDetails;

import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("transferred_to_address")
    private String transferredToAddress;

    @SerializedName("bill_to")
    private String billTo;

    @SerializedName("removed_from_address")
    private String removedFromAddress;

    @SerializedName("personal_effects")
    private String personalEffects;

    @SerializedName("physician_name")
    private String physicianName;

    @SerializedName("physician_phone")
    private String physicianPhone;

    @SerializedName("personal_effects_lists")
    private String personalEffectsLists;

    @SerializedName("requested_items")
    private String requestedItems;
    @SerializedName("requested_items_lists")
    private String requestedItemsLists;

    @SerializedName("date_of_death")
    private String dateOfDeath;

    @SerializedName("time_received")
    private String timeReceived;

    @SerializedName("decendant_first_name")
    private String decendantFirstName;
    @SerializedName("decendant_middle_name")
    private String decendantMiddleName;
    @SerializedName("decendant_last_name")
    private String decendantLastName;


    @SerializedName("admin_status")
    private String adminStatus;

    @SerializedName("rs_phone")
    private String rsPhone;

    @SerializedName("time_of_death")
    private String timeOfDeath;

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

    @SerializedName("cd_phone")
    private String cdPhone;

    @SerializedName("steps")
    private String steps;

    @SerializedName("hospital_type")
    private String hospitalType;

    @SerializedName("fomat_request_date")
    private String fomatRequestDate;

    @SerializedName("body_release")
    private String bodyRelease;

    @SerializedName("cd_name")
    private String cdName;

    @SerializedName("requested_by")
    private String requestedBy;

    @SerializedName("dob")
    private String dob;

    @SerializedName("request_date")
    private String requestDate;

    @SerializedName("rs_name")
    private String rsName;

    @SerializedName("next_of_kin_relationship")
    private String nextOfKinRelationship;

    @SerializedName("hospital_type_id")
    private String hospitalTypeId;

    @SerializedName("format_time_of_death")
    private String formatTimeOfDeath;

    @SerializedName("request_id")
    private String requestId;

    @SerializedName("request_created_by")
    private String requestCreatedBy;

    @SerializedName("age")
    private String age;

    public void setTransferredToAddress(String transferredToAddress) {
        this.transferredToAddress = transferredToAddress;
    }

    public String getTransferredToAddress() {
        return transferredToAddress;
    }

    public void setBillTo(String billTo) {
        this.billTo = billTo;
    }

    public String getBillTo() {
        return billTo;
    }

    public void setRemovedFromAddress(String removedFromAddress) {
        this.removedFromAddress = removedFromAddress;
    }

    public String getRemovedFromAddress() {
        return removedFromAddress;
    }

    public void setPersonalEffects(String personalEffects) {
        this.personalEffects = personalEffects;
    }

    public String getPersonalEffects() {
        return personalEffects;
    }

    public void setPhysicianName(String physicianName) {
        this.physicianName = physicianName;
    }

    public String getPhysicianName() {
        return physicianName;
    }

    public void setPhysicianPhone(String physicianPhone) {
        this.physicianPhone = physicianPhone;
    }

    public String getPhysicianPhone() {
        return physicianPhone;
    }

    public void setPersonalEffectsLists(String personalEffectsLists) {
        this.personalEffectsLists = personalEffectsLists;
    }

    public String getPersonalEffectsLists() {
        return personalEffectsLists;
    }

    public void setDateOfDeath(String dateOfDeath) {
        this.dateOfDeath = dateOfDeath;
    }

    public String getDateOfDeath() {
        return dateOfDeath;
    }

    public void setTimeReceived(String timeReceived) {
        this.timeReceived = timeReceived;
    }

    public String getTimeReceived() {
        return timeReceived;
    }

    public String getRequestedItems() {
        return requestedItems;
    }

    public void setRequestedItems(String requestedItems) {
        this.requestedItems = requestedItems;
    }

    public String getRequestedItemsLists() {
        return requestedItemsLists;
    }

    public void setRequestedItemsLists(String requestedItemsLists) {
        this.requestedItemsLists = requestedItemsLists;
    }

    public String getDecendantFirstName() {
        return decendantFirstName;
    }

    public void setDecendantFirstName(String decendantFirstName) {
        this.decendantFirstName = decendantFirstName;
    }

    public String getDecendantMiddleName() {
        return decendantMiddleName;
    }

    public void setDecendantMiddleName(String decendantMiddleName) {
        this.decendantMiddleName = decendantMiddleName;
    }

    public String getDecendantLastName() {
        return decendantLastName;
    }

    public void setDecendantLastName(String decendantLastName) {
        this.decendantLastName = decendantLastName;
    }

    public void setAdminStatus(String adminStatus) {
        this.adminStatus = adminStatus;
    }

    public String getAdminStatus() {
        return adminStatus;
    }

    public void setRsPhone(String rsPhone) {
        this.rsPhone = rsPhone;
    }

    public String getRsPhone() {
        return rsPhone;
    }

    public void setTimeOfDeath(String timeOfDeath) {
        this.timeOfDeath = timeOfDeath;
    }

    public String getTimeOfDeath() {
        return timeOfDeath;
    }

    public void setDspStatus(String dspStatus) {
        this.dspStatus = dspStatus;
    }

    public String getDspStatus() {
        return dspStatus;
    }

    public void setCabName(String cabName) {
        this.cabName = cabName;
    }

    public String getCabName() {
        return cabName;
    }

    public void setTimeCompleted(String timeCompleted) {
        this.timeCompleted = timeCompleted;
    }

    public String getTimeCompleted() {
        return timeCompleted;
    }

    public void setNextOfKinPhone(String nextOfKinPhone) {
        this.nextOfKinPhone = nextOfKinPhone;
    }

    public String getNextOfKinPhone() {
        return nextOfKinPhone;
    }

    public void setCabNo(String cabNo) {
        this.cabNo = cabNo;
    }

    public String getCabNo() {
        return cabNo;
    }

    public void setCdPhone(String cdPhone) {
        this.cdPhone = cdPhone;
    }

    public String getCdPhone() {
        return cdPhone;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

    public String getSteps() {
        return steps;
    }

    public void setHospitalType(String hospitalType) {
        this.hospitalType = hospitalType;
    }

    public String getHospitalType() {
        return hospitalType;
    }

    public void setFomatRequestDate(String fomatRequestDate) {
        this.fomatRequestDate = fomatRequestDate;
    }

    public String getFomatRequestDate() {
        return fomatRequestDate;
    }

    public void setBodyRelease(String bodyRelease) {
        this.bodyRelease = bodyRelease;
    }

    public String getBodyRelease() {
        return bodyRelease;
    }

    public void setCdName(String cdName) {
        this.cdName = cdName;
    }

    public String getCdName() {
        return cdName;
    }

    public void setRequestedBy(String requestedBy) {
        this.requestedBy = requestedBy;
    }

    public String getRequestedBy() {
        return requestedBy;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getDob() {
        return dob;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setRsName(String rsName) {
        this.rsName = rsName;
    }

    public String getRsName() {
        return rsName;
    }

    public void setNextOfKinRelationship(String nextOfKinRelationship) {
        this.nextOfKinRelationship = nextOfKinRelationship;
    }

    public String getNextOfKinRelationship() {
        return nextOfKinRelationship;
    }

    public void setHospitalTypeId(String hospitalTypeId) {
        this.hospitalTypeId = hospitalTypeId;
    }

    public String getHospitalTypeId() {
        return hospitalTypeId;
    }

    public void setFormatTimeOfDeath(String formatTimeOfDeath) {
        this.formatTimeOfDeath = formatTimeOfDeath;
    }

    public String getFormatTimeOfDeath() {
        return formatTimeOfDeath;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestCreatedBy(String requestCreatedBy) {
        this.requestCreatedBy = requestCreatedBy;
    }

    public String getRequestCreatedBy() {
        return requestCreatedBy;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAge() {
        return age;
    }
}