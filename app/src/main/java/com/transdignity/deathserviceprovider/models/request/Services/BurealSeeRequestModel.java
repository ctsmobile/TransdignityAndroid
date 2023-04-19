
package com.transdignity.deathserviceprovider.models.request.Services;

import com.google.gson.annotations.SerializedName;


public class BurealSeeRequestModel {

    @SerializedName("date_of_death")
    private String mDateOfDeath;
    @SerializedName("decendant_mobile_number")
    private String mDecendantMobileNumber;
    @SerializedName("dob")
    private String mDob;
    @SerializedName("estimated_date")
    private String mEstimatedDate;
    @SerializedName("first_name")
    private String mFirstName;
    @SerializedName("from_address_latlong")
    private String mFromAddressLatlong;
    @SerializedName("place_of_death")
    private String mPlaceOfDeath;
    @SerializedName("removed_from_address")
    private String mRemovedFromAddress;
    @SerializedName("request_created_by")
    private String mRequestCreatedBy;
    @SerializedName("request_date")
    private String mRequestDate;
    @SerializedName("requested_by")
    private String mRequestedBy;
    @SerializedName("service_id")
    private String mServiceId;
    @SerializedName("time_of_death")
    private String mTimeOfDeath;
    @SerializedName("to_address_latlong")
    private String mToAddressLatlong;
    @SerializedName("transferred_to_address")
    private String mTransferredToAddress;

    public String getDateOfDeath() {
        return mDateOfDeath;
    }

    public void setDateOfDeath(String dateOfDeath) {
        mDateOfDeath = dateOfDeath;
    }

    public String getDecendantMobileNumber() {
        return mDecendantMobileNumber;
    }

    public void setDecendantMobileNumber(String decendantMobileNumber) {
        mDecendantMobileNumber = decendantMobileNumber;
    }

    public String getDob() {
        return mDob;
    }

    public void setDob(String dob) {
        mDob = dob;
    }

    public String getEstimatedDate() {
        return mEstimatedDate;
    }

    public void setEstimatedDate(String estimatedDate) {
        mEstimatedDate = estimatedDate;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        mFirstName = firstName;
    }

    public String getFromAddressLatlong() {
        return mFromAddressLatlong;
    }

    public void setFromAddressLatlong(String fromAddressLatlong) {
        mFromAddressLatlong = fromAddressLatlong;
    }

    public String getPlaceOfDeath() {
        return mPlaceOfDeath;
    }

    public void setPlaceOfDeath(String placeOfDeath) {
        mPlaceOfDeath = placeOfDeath;
    }

    public String getRemovedFromAddress() {
        return mRemovedFromAddress;
    }

    public void setRemovedFromAddress(String removedFromAddress) {
        mRemovedFromAddress = removedFromAddress;
    }

    public String getRequestCreatedBy() {
        return mRequestCreatedBy;
    }

    public void setRequestCreatedBy(String requestCreatedBy) {
        mRequestCreatedBy = requestCreatedBy;
    }

    public String getRequestDate() {
        return mRequestDate;
    }

    public void setRequestDate(String requestDate) {
        mRequestDate = requestDate;
    }

    public String getRequestedBy() {
        return mRequestedBy;
    }

    public void setRequestedBy(String requestedBy) {
        mRequestedBy = requestedBy;
    }

    public String getServiceId() {
        return mServiceId;
    }

    public void setServiceId(String serviceId) {
        mServiceId = serviceId;
    }

    public String getTimeOfDeath() {
        return mTimeOfDeath;
    }

    public void setTimeOfDeath(String timeOfDeath) {
        mTimeOfDeath = timeOfDeath;
    }

    public String getToAddressLatlong() {
        return mToAddressLatlong;
    }

    public void setToAddressLatlong(String toAddressLatlong) {
        mToAddressLatlong = toAddressLatlong;
    }

    public String getTransferredToAddress() {
        return mTransferredToAddress;
    }

    public void setTransferredToAddress(String transferredToAddress) {
        mTransferredToAddress = transferredToAddress;
    }

}
