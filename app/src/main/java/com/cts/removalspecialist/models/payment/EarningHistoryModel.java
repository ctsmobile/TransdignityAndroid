
package com.cts.removalspecialist.models.payment;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class EarningHistoryModel {

    @SerializedName("data")
    private List<Datum> mData;
    @SerializedName("error")
    private Error mError;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("success")
    private String mSuccess;
    @SerializedName("token_valid")
    private String mTokenValid;

    public List<Datum> getData() {
        return mData;
    }

    public void setData(List<Datum> data) {
        mData = data;
    }

    public Error getError() {
        return mError;
    }

    public void setError(Error error) {
        mError = error;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public String getSuccess() {
        return mSuccess;
    }

    public void setSuccess(String success) {
        mSuccess = success;
    }

    public String getTokenValid() {
        return mTokenValid;
    }

    public void setTokenValid(String tokenValid) {
        mTokenValid = tokenValid;
    }

    public class Datum {

        @SerializedName("admin_status")
        private String mAdminStatus;
        @SerializedName("age")
        private String mAge;
        @SerializedName("bill_to")
        private String mBillTo;
        @SerializedName("body_release")
        private String mBodyRelease;
        @SerializedName("cab_driver_assign_time")
        private String mCabDriverAssignTime;
        @SerializedName("cab_driver_id")
        private String mCabDriverId;
        @SerializedName("cd_status")
        private String mCdStatus;
        @SerializedName("DBrequest_datetime")
        private String mDBrequestDatetime;
        @SerializedName("date_completed")
        private String mDateCompleted;
        @SerializedName("date_of_death")
        private String mDateOfDeath;
        @SerializedName("decendant_first_name")
        private String mDecendantFirstName;
        @SerializedName("decendant_last_name")
        private String mDecendantLastName;
        @SerializedName("decendant_middle_name")
        private Object mDecendantMiddleName;
        @SerializedName("decendant_mobile_number")
        private String mDecendantMobileNumber;
        @SerializedName("description")
        private String mDescription;
        @SerializedName("dob")
        private String mDob;
        @SerializedName("drop_decendant_time")
        private String mDropDecendantTime;
        @SerializedName("dsp_status")
        private String mDspStatus;
        @SerializedName("duration")
        private String mDuration;
        @SerializedName("earning")
        private String mEarning;
        @SerializedName("flightdetail")
        private String mFlightdetail;
        @SerializedName("flower_name")
        private String mFlowerName;
        @SerializedName("from_address_latlong")
        private String mFromAddressLatlong;
        @SerializedName("hospital_type_id")
        private String mHospitalTypeId;
        @SerializedName("instructuion_to_carryitem")
        private String mInstructuionToCarryitem;
        @SerializedName("item_name")
        private String mItemName;
        @SerializedName("next_of_kin_phone")
        private String mNextOfKinPhone;
        @SerializedName("next_of_kin_relationship")
        private String mNextOfKinRelationship;
        @SerializedName("personal_effects")
        private String mPersonalEffects;
        @SerializedName("personal_effects_lists")
        private Object mPersonalEffectsLists;
        @SerializedName("physician_name")
        private String mPhysicianName;
        @SerializedName("physician_phone")
        private String mPhysicianPhone;
        @SerializedName("pickup_decendent_time")
        private Object mPickupDecendentTime;
        @SerializedName("pickup_rs_time")
        private String mPickupRsTime;
        @SerializedName("place_of_death")
        private String mPlaceOfDeath;
        @SerializedName("purchased_flower_time")
        private String mPurchasedFlowerTime;
        @SerializedName("quantity")
        private String mQuantity;
        @SerializedName("reached_on_decendant_pickup_location_time")
        private String mReachedOnDecendantPickupLocationTime;
        @SerializedName("reached_on_rs_location_time")
        private String mReachedOnRsLocationTime;
        @SerializedName("reciever_first_name")
        private String mRecieverFirstName;
        @SerializedName("reciever_last_name")
        private String mRecieverLastName;
        @SerializedName("reciever_mobile_number")
        private String mRecieverMobileNumber;
        @SerializedName("removal_specialist_pickup_location")
        private String mRemovalSpecialistPickupLocation;
        @SerializedName("removal_specialists_assign_time")
        private String mRemovalSpecialistsAssignTime;
        @SerializedName("removal_specialists_id")
        private String mRemovalSpecialistsId;
        @SerializedName("removed_from_address")
        private String mRemovedFromAddress;
        @SerializedName("request_assigned_user_id")
        private String mRequestAssignedUserId;
        @SerializedName("request_created_by")
        private String mRequestCreatedBy;
        @SerializedName("request_date")
        private String mRequestDate;
        @SerializedName("request_id")
        private String mRequestId;
        @SerializedName("requested_by")
        private String mRequestedBy;
        @SerializedName("rs_distance")
        private String mRsDistance;
        @SerializedName("rs_min_away")
        private String mRsMinAway;
        @SerializedName("rs_status")
        private String mRsStatus;
        @SerializedName("service_id")
        private String mServiceId;
        @SerializedName("start_date")
        private String mStartDate;
        @SerializedName("start_time")
        private String mStartTime;
        @SerializedName("steps")
        private String mSteps;
        @SerializedName("time_completed")
        private String mTimeCompleted;
        @SerializedName("time_of_death")
        private String mTimeOfDeath;
        @SerializedName("time_received")
        private String mTimeReceived;
        @SerializedName("to_address_latlong")
        private String mToAddressLatlong;
        @SerializedName("transferred_to_address")
        private String mTransferredToAddress;
        @SerializedName("trip_type")
        private String mTripType;

        public String getAdminStatus() {
            return mAdminStatus;
        }

        public void setAdminStatus(String adminStatus) {
            mAdminStatus = adminStatus;
        }

        public String getAge() {
            return mAge;
        }

        public void setAge(String age) {
            mAge = age;
        }

        public String getBillTo() {
            return mBillTo;
        }

        public void setBillTo(String billTo) {
            mBillTo = billTo;
        }

        public String getBodyRelease() {
            return mBodyRelease;
        }

        public void setBodyRelease(String bodyRelease) {
            mBodyRelease = bodyRelease;
        }

        public String getCabDriverAssignTime() {
            return mCabDriverAssignTime;
        }

        public void setCabDriverAssignTime(String cabDriverAssignTime) {
            mCabDriverAssignTime = cabDriverAssignTime;
        }

        public String getCabDriverId() {
            return mCabDriverId;
        }

        public void setCabDriverId(String cabDriverId) {
            mCabDriverId = cabDriverId;
        }

        public String getCdStatus() {
            return mCdStatus;
        }

        public void setCdStatus(String cdStatus) {
            mCdStatus = cdStatus;
        }

        public String getDBrequestDatetime() {
            return mDBrequestDatetime;
        }

        public void setDBrequestDatetime(String dBrequestDatetime) {
            mDBrequestDatetime = dBrequestDatetime;
        }

        public String getDateCompleted() {
            return mDateCompleted;
        }

        public void setDateCompleted(String dateCompleted) {
            mDateCompleted = dateCompleted;
        }

        public String getDateOfDeath() {
            return mDateOfDeath;
        }

        public void setDateOfDeath(String dateOfDeath) {
            mDateOfDeath = dateOfDeath;
        }

        public String getDecendantFirstName() {
            return mDecendantFirstName;
        }

        public void setDecendantFirstName(String decendantFirstName) {
            mDecendantFirstName = decendantFirstName;
        }

        public String getDecendantLastName() {
            return mDecendantLastName;
        }

        public void setDecendantLastName(String decendantLastName) {
            mDecendantLastName = decendantLastName;
        }

        public Object getDecendantMiddleName() {
            return mDecendantMiddleName;
        }

        public void setDecendantMiddleName(Object decendantMiddleName) {
            mDecendantMiddleName = decendantMiddleName;
        }

        public String getDecendantMobileNumber() {
            return mDecendantMobileNumber;
        }

        public void setDecendantMobileNumber(String decendantMobileNumber) {
            mDecendantMobileNumber = decendantMobileNumber;
        }

        public String getDescription() {
            return mDescription;
        }

        public void setDescription(String description) {
            mDescription = description;
        }

        public String getDob() {
            return mDob;
        }

        public void setDob(String dob) {
            mDob = dob;
        }

        public String getDropDecendantTime() {
            return mDropDecendantTime;
        }

        public void setDropDecendantTime(String dropDecendantTime) {
            mDropDecendantTime = dropDecendantTime;
        }

        public String getDspStatus() {
            return mDspStatus;
        }

        public void setDspStatus(String dspStatus) {
            mDspStatus = dspStatus;
        }

        public String getDuration() {
            return mDuration;
        }

        public void setDuration(String duration) {
            mDuration = duration;
        }

        public String getEarning() {
            return mEarning;
        }

        public void setEarning(String earning) {
            mEarning = earning;
        }

        public String getFlightdetail() {
            return mFlightdetail;
        }

        public void setFlightdetail(String flightdetail) {
            mFlightdetail = flightdetail;
        }

        public String getFlowerName() {
            return mFlowerName;
        }

        public void setFlowerName(String flowerName) {
            mFlowerName = flowerName;
        }

        public String getFromAddressLatlong() {
            return mFromAddressLatlong;
        }

        public void setFromAddressLatlong(String fromAddressLatlong) {
            mFromAddressLatlong = fromAddressLatlong;
        }

        public String getHospitalTypeId() {
            return mHospitalTypeId;
        }

        public void setHospitalTypeId(String hospitalTypeId) {
            mHospitalTypeId = hospitalTypeId;
        }

        public String getInstructuionToCarryitem() {
            return mInstructuionToCarryitem;
        }

        public void setInstructuionToCarryitem(String instructuionToCarryitem) {
            mInstructuionToCarryitem = instructuionToCarryitem;
        }

        public String getItemName() {
            return mItemName;
        }

        public void setItemName(String itemName) {
            mItemName = itemName;
        }

        public String getNextOfKinPhone() {
            return mNextOfKinPhone;
        }

        public void setNextOfKinPhone(String nextOfKinPhone) {
            mNextOfKinPhone = nextOfKinPhone;
        }

        public String getNextOfKinRelationship() {
            return mNextOfKinRelationship;
        }

        public void setNextOfKinRelationship(String nextOfKinRelationship) {
            mNextOfKinRelationship = nextOfKinRelationship;
        }

        public String getPersonalEffects() {
            return mPersonalEffects;
        }

        public void setPersonalEffects(String personalEffects) {
            mPersonalEffects = personalEffects;
        }

        public Object getPersonalEffectsLists() {
            return mPersonalEffectsLists;
        }

        public void setPersonalEffectsLists(Object personalEffectsLists) {
            mPersonalEffectsLists = personalEffectsLists;
        }

        public String getPhysicianName() {
            return mPhysicianName;
        }

        public void setPhysicianName(String physicianName) {
            mPhysicianName = physicianName;
        }

        public String getPhysicianPhone() {
            return mPhysicianPhone;
        }

        public void setPhysicianPhone(String physicianPhone) {
            mPhysicianPhone = physicianPhone;
        }

        public Object getPickupDecendentTime() {
            return mPickupDecendentTime;
        }

        public void setPickupDecendentTime(Object pickupDecendentTime) {
            mPickupDecendentTime = pickupDecendentTime;
        }

        public String getPickupRsTime() {
            return mPickupRsTime;
        }

        public void setPickupRsTime(String pickupRsTime) {
            mPickupRsTime = pickupRsTime;
        }

        public String getPlaceOfDeath() {
            return mPlaceOfDeath;
        }

        public void setPlaceOfDeath(String placeOfDeath) {
            mPlaceOfDeath = placeOfDeath;
        }

        public String getPurchasedFlowerTime() {
            return mPurchasedFlowerTime;
        }

        public void setPurchasedFlowerTime(String purchasedFlowerTime) {
            mPurchasedFlowerTime = purchasedFlowerTime;
        }

        public String getQuantity() {
            return mQuantity;
        }

        public void setQuantity(String quantity) {
            mQuantity = quantity;
        }

        public String getReachedOnDecendantPickupLocationTime() {
            return mReachedOnDecendantPickupLocationTime;
        }

        public void setReachedOnDecendantPickupLocationTime(String reachedOnDecendantPickupLocationTime) {
            mReachedOnDecendantPickupLocationTime = reachedOnDecendantPickupLocationTime;
        }

        public String getReachedOnRsLocationTime() {
            return mReachedOnRsLocationTime;
        }

        public void setReachedOnRsLocationTime(String reachedOnRsLocationTime) {
            mReachedOnRsLocationTime = reachedOnRsLocationTime;
        }

        public String getRecieverFirstName() {
            return mRecieverFirstName;
        }

        public void setRecieverFirstName(String recieverFirstName) {
            mRecieverFirstName = recieverFirstName;
        }

        public String getRecieverLastName() {
            return mRecieverLastName;
        }

        public void setRecieverLastName(String recieverLastName) {
            mRecieverLastName = recieverLastName;
        }

        public String getRecieverMobileNumber() {
            return mRecieverMobileNumber;
        }

        public void setRecieverMobileNumber(String recieverMobileNumber) {
            mRecieverMobileNumber = recieverMobileNumber;
        }

        public String getRemovalSpecialistPickupLocation() {
            return mRemovalSpecialistPickupLocation;
        }

        public void setRemovalSpecialistPickupLocation(String removalSpecialistPickupLocation) {
            mRemovalSpecialistPickupLocation = removalSpecialistPickupLocation;
        }

        public String getRemovalSpecialistsAssignTime() {
            return mRemovalSpecialistsAssignTime;
        }

        public void setRemovalSpecialistsAssignTime(String removalSpecialistsAssignTime) {
            mRemovalSpecialistsAssignTime = removalSpecialistsAssignTime;
        }

        public String getRemovalSpecialistsId() {
            return mRemovalSpecialistsId;
        }

        public void setRemovalSpecialistsId(String removalSpecialistsId) {
            mRemovalSpecialistsId = removalSpecialistsId;
        }

        public String getRemovedFromAddress() {
            return mRemovedFromAddress;
        }

        public void setRemovedFromAddress(String removedFromAddress) {
            mRemovedFromAddress = removedFromAddress;
        }

        public String getRequestAssignedUserId() {
            return mRequestAssignedUserId;
        }

        public void setRequestAssignedUserId(String requestAssignedUserId) {
            mRequestAssignedUserId = requestAssignedUserId;
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

        public String getRequestId() {
            return mRequestId;
        }

        public void setRequestId(String requestId) {
            mRequestId = requestId;
        }

        public String getRequestedBy() {
            return mRequestedBy;
        }

        public void setRequestedBy(String requestedBy) {
            mRequestedBy = requestedBy;
        }

        public String getRsDistance() {
            return mRsDistance;
        }

        public void setRsDistance(String rsDistance) {
            mRsDistance = rsDistance;
        }

        public String getRsMinAway() {
            return mRsMinAway;
        }

        public void setRsMinAway(String rsMinAway) {
            mRsMinAway = rsMinAway;
        }

        public String getRsStatus() {
            return mRsStatus;
        }

        public void setRsStatus(String rsStatus) {
            mRsStatus = rsStatus;
        }

        public String getServiceId() {
            return mServiceId;
        }

        public void setServiceId(String serviceId) {
            mServiceId = serviceId;
        }

        public String getStartDate() {
            return mStartDate;
        }

        public void setStartDate(String startDate) {
            mStartDate = startDate;
        }

        public String getStartTime() {
            return mStartTime;
        }

        public void setStartTime(String startTime) {
            mStartTime = startTime;
        }

        public String getSteps() {
            return mSteps;
        }

        public void setSteps(String steps) {
            mSteps = steps;
        }

        public String getTimeCompleted() {
            return mTimeCompleted;
        }

        public void setTimeCompleted(String timeCompleted) {
            mTimeCompleted = timeCompleted;
        }

        public String getTimeOfDeath() {
            return mTimeOfDeath;
        }

        public void setTimeOfDeath(String timeOfDeath) {
            mTimeOfDeath = timeOfDeath;
        }

        public String getTimeReceived() {
            return mTimeReceived;
        }

        public void setTimeReceived(String timeReceived) {
            mTimeReceived = timeReceived;
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

        public String getTripType() {
            return mTripType;
        }

        public void setTripType(String tripType) {
            mTripType = tripType;
        }

    }
    public class Error {


    }
}
