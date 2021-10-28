package com.transdignity.driver.models.auth.pofileEdit;

import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("username")
    private String businessName;

    @SerializedName("address")
    private String address;

    @SerializedName("personal_phone")
    private String personalPhone;

    @SerializedName("years_in_business")
    private String yearsInBusiness;

    @SerializedName("id")
    private String id;

    @SerializedName("user_group_id")
    private String userGroupId;

    @SerializedName("email")
    private String email;

    @SerializedName("license_number")
    private String licenseNumber;

    @SerializedName("status")
    private String status;

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setPersonalPhone(String personalPhone) {
        this.personalPhone = personalPhone;
    }

    public String getPersonalPhone() {
        return personalPhone;
    }

    public void setYearsInBusiness(String yearsInBusiness) {
        this.yearsInBusiness = yearsInBusiness;
    }

    public String getYearsInBusiness() {
        return yearsInBusiness;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setUserGroupId(String userGroupId) {
        this.userGroupId = userGroupId;
    }

    public String getUserGroupId() {
        return userGroupId;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}