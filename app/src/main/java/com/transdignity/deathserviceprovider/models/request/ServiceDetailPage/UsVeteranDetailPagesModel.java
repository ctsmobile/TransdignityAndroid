
package com.transdignity.deathserviceprovider.models.request.ServiceDetailPage;

import com.google.gson.annotations.SerializedName;


public class UsVeteranDetailPagesModel {

    @SerializedName("data")
    private Data mData;
    @SerializedName("error")
    private Error mError;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("success")
    private String mSuccess;
    @SerializedName("token_valid")
    private String mTokenValid;

    public Data getData() {
        return mData;
    }

    public void setData(Data data) {
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

    public class Data {

        @SerializedName("admin_charge")
        private Object mAdminCharge;
        @SerializedName("admin_status")
        private String mAdminStatus;
        @SerializedName("age")
        private String mAge;
        @SerializedName("bill_to")
        private String mBillTo;
        @SerializedName("body_release")
        private String mBodyRelease;
        @SerializedName("branch_ofmilitary")
        private String mBranchOfmilitary;
        @SerializedName("cab_driver_assign_time")
        private Object mCabDriverAssignTime;
        @SerializedName("cab_driver_id")
        private Object mCabDriverId;
        @SerializedName("cab_id")
        private Object mCabId;
        @SerializedName("cab_name")
        private Object mCabName;
        @SerializedName("cab_no")
        private Object mCabNo;
        @SerializedName("cd_name")
        private Object mCdName;
        @SerializedName("cd_phone")
        private Object mCdPhone;
        @SerializedName("country")
        private String mCountry;
        @SerializedName("DBrequest_datetime")
        private String mDBrequestDatetime;
        @SerializedName("date_completed")
        private Object mDateCompleted;
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
        private Object mDropDecendantTime;
        @SerializedName("dsp_status")
        private String mDspStatus;
        @SerializedName("duration")
        private String mDuration;
        @SerializedName("flightdetail")
        private String mFlightdetail;
        @SerializedName("flower_name")
        private String mFlowerName;
        @SerializedName("fomat_request_date")
        private String mFomatRequestDate;
        @SerializedName("format_time_of_death")
        private String mFormatTimeOfDeath;
        @SerializedName("from_address_latlong")
        private String mFromAddressLatlong;
        @SerializedName("hospital_type")
        private Object mHospitalType;
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
        private String mPersonalEffectsLists;
        @SerializedName("physician_name")
        private String mPhysicianName;
        @SerializedName("physician_phone")
        private String mPhysicianPhone;
        @SerializedName("pickup_decendent_time")
        private Object mPickupDecendentTime;
        @SerializedName("pickup_rs_time")
        private Object mPickupRsTime;
        @SerializedName("place_of_death")
        private String mPlaceOfDeath;
        @SerializedName("purchased_flower_time")
        private String mPurchasedFlowerTime;
        @SerializedName("quantity")
        private String mQuantity;
        @SerializedName("reached_on_decendant_pickup_location_time")
        private Object mReachedOnDecendantPickupLocationTime;
        @SerializedName("reached_on_rs_location_time")
        private Object mReachedOnRsLocationTime;
        @SerializedName("reciever_first_name")
        private String mRecieverFirstName;
        @SerializedName("reciever_last_name")
        private String mRecieverLastName;
        @SerializedName("reciever_mobile_number")
        private String mRecieverMobileNumber;
        @SerializedName("removal_specialist_pickup_location")
        private Object mRemovalSpecialistPickupLocation;
        @SerializedName("removal_specialists_assign_time")
        private Object mRemovalSpecialistsAssignTime;
        @SerializedName("removed_from_address")
        private String mRemovedFromAddress;
        @SerializedName("request_created_by")
        private String mRequestCreatedBy;
        @SerializedName("request_date")
        private String mRequestDate;
        @SerializedName("request_id")
        private String mRequestId;
        @SerializedName("requested_by")
        private String mRequestedBy;
        @SerializedName("requested_items")
        private String mRequestedItems;
        @SerializedName("requested_items_lists")
        private String mRequestedItemsLists;
        @SerializedName("responder_type")
        private String mResponderType;
        @SerializedName("rp_charge")
        private Object mRpCharge;
        @SerializedName("rs_charge")
        private Object mRsCharge;
        @SerializedName("rs_distance")
        private Object mRsDistance;
        @SerializedName("rs_id")
        private Object mRsId;
        @SerializedName("rs_min_away")
        private Object mRsMinAway;
        @SerializedName("rs_name")
        private Object mRsName;
        @SerializedName("rs_phone")
        private Object mRsPhone;
        @SerializedName("service_id")
        private String mServiceId;
        @SerializedName("service_type")
        private String mServiceType;
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
        @SerializedName("total_charge")
        private String mTotalCharge;
        @SerializedName("total_distance")
        private Object mTotalDistance;
        @SerializedName("transferred_to_address")
        private String mTransferredToAddress;
        @SerializedName("trip_type")
        private String mTripType;
        @SerializedName("endassigneddata")
        private Object mEndassigneddata;
        @SerializedName("endpaymentdetail")
        private Object mEndpaymentdetail;
        @SerializedName("startassineddata")
        private Startassineddata mStartassineddata;
        @SerializedName("startpaymentdetail")
        private Startpaymentdetail mStartpaymentdetail;
        public Object getAdminCharge() {
            return mAdminCharge;
        }

        public void setAdminCharge(Object adminCharge) {
            mAdminCharge = adminCharge;
        }

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

        public String getBranchOfmilitary() {
            return mBranchOfmilitary;
        }

        public void setBranchOfmilitary(String branchOfmilitary) {
            mBranchOfmilitary = branchOfmilitary;
        }

        public Object getCabDriverAssignTime() {
            return mCabDriverAssignTime;
        }

        public void setCabDriverAssignTime(Object cabDriverAssignTime) {
            mCabDriverAssignTime = cabDriverAssignTime;
        }

        public Object getCabDriverId() {
            return mCabDriverId;
        }

        public void setCabDriverId(Object cabDriverId) {
            mCabDriverId = cabDriverId;
        }

        public Object getCabId() {
            return mCabId;
        }

        public void setCabId(Object cabId) {
            mCabId = cabId;
        }

        public Object getCabName() {
            return mCabName;
        }

        public void setCabName(Object cabName) {
            mCabName = cabName;
        }

        public Object getCabNo() {
            return mCabNo;
        }

        public void setCabNo(Object cabNo) {
            mCabNo = cabNo;
        }

        public Object getCdName() {
            return mCdName;
        }

        public void setCdName(Object cdName) {
            mCdName = cdName;
        }

        public Object getCdPhone() {
            return mCdPhone;
        }

        public void setCdPhone(Object cdPhone) {
            mCdPhone = cdPhone;
        }

        public String getCountry() {
            return mCountry;
        }

        public void setCountry(String country) {
            mCountry = country;
        }

        public String getDBrequestDatetime() {
            return mDBrequestDatetime;
        }

        public void setDBrequestDatetime(String dBrequestDatetime) {
            mDBrequestDatetime = dBrequestDatetime;
        }

        public Object getDateCompleted() {
            return mDateCompleted;
        }

        public void setDateCompleted(Object dateCompleted) {
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

        public Object getDropDecendantTime() {
            return mDropDecendantTime;
        }

        public void setDropDecendantTime(Object dropDecendantTime) {
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

        public String getFomatRequestDate() {
            return mFomatRequestDate;
        }

        public void setFomatRequestDate(String fomatRequestDate) {
            mFomatRequestDate = fomatRequestDate;
        }

        public String getFormatTimeOfDeath() {
            return mFormatTimeOfDeath;
        }

        public void setFormatTimeOfDeath(String formatTimeOfDeath) {
            mFormatTimeOfDeath = formatTimeOfDeath;
        }

        public String getFromAddressLatlong() {
            return mFromAddressLatlong;
        }

        public void setFromAddressLatlong(String fromAddressLatlong) {
            mFromAddressLatlong = fromAddressLatlong;
        }

        public Object getHospitalType() {
            return mHospitalType;
        }

        public void setHospitalType(Object hospitalType) {
            mHospitalType = hospitalType;
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

        public String getPersonalEffectsLists() {
            return mPersonalEffectsLists;
        }

        public void setPersonalEffectsLists(String personalEffectsLists) {
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

        public Object getPickupRsTime() {
            return mPickupRsTime;
        }

        public void setPickupRsTime(Object pickupRsTime) {
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

        public Object getReachedOnDecendantPickupLocationTime() {
            return mReachedOnDecendantPickupLocationTime;
        }

        public void setReachedOnDecendantPickupLocationTime(Object reachedOnDecendantPickupLocationTime) {
            mReachedOnDecendantPickupLocationTime = reachedOnDecendantPickupLocationTime;
        }

        public Object getReachedOnRsLocationTime() {
            return mReachedOnRsLocationTime;
        }

        public void setReachedOnRsLocationTime(Object reachedOnRsLocationTime) {
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

        public Object getRemovalSpecialistPickupLocation() {
            return mRemovalSpecialistPickupLocation;
        }

        public void setRemovalSpecialistPickupLocation(Object removalSpecialistPickupLocation) {
            mRemovalSpecialistPickupLocation = removalSpecialistPickupLocation;
        }

        public Object getRemovalSpecialistsAssignTime() {
            return mRemovalSpecialistsAssignTime;
        }

        public void setRemovalSpecialistsAssignTime(Object removalSpecialistsAssignTime) {
            mRemovalSpecialistsAssignTime = removalSpecialistsAssignTime;
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

        public String getRequestedItems() {
            return mRequestedItems;
        }

        public void setRequestedItems(String requestedItems) {
            mRequestedItems = requestedItems;
        }

        public String getRequestedItemsLists() {
            return mRequestedItemsLists;
        }

        public void setRequestedItemsLists(String requestedItemsLists) {
            mRequestedItemsLists = requestedItemsLists;
        }

        public String getResponderType() {
            return mResponderType;
        }

        public void setResponderType(String responderType) {
            mResponderType = responderType;
        }

        public Object getRpCharge() {
            return mRpCharge;
        }

        public void setRpCharge(Object rpCharge) {
            mRpCharge = rpCharge;
        }

        public Object getRsCharge() {
            return mRsCharge;
        }

        public void setRsCharge(Object rsCharge) {
            mRsCharge = rsCharge;
        }

        public Object getRsDistance() {
            return mRsDistance;
        }

        public void setRsDistance(Object rsDistance) {
            mRsDistance = rsDistance;
        }

        public Object getRsId() {
            return mRsId;
        }

        public void setRsId(Object rsId) {
            mRsId = rsId;
        }

        public Object getRsMinAway() {
            return mRsMinAway;
        }

        public void setRsMinAway(Object rsMinAway) {
            mRsMinAway = rsMinAway;
        }

        public Object getRsName() {
            return mRsName;
        }

        public void setRsName(Object rsName) {
            mRsName = rsName;
        }

        public Object getRsPhone() {
            return mRsPhone;
        }

        public void setRsPhone(Object rsPhone) {
            mRsPhone = rsPhone;
        }

        public String getServiceId() {
            return mServiceId;
        }

        public void setServiceId(String serviceId) {
            mServiceId = serviceId;
        }

        public String getServiceType() {
            return mServiceType;
        }

        public void setServiceType(String serviceType) {
            mServiceType = serviceType;
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

        public String getTotalCharge() {
            return mTotalCharge;
        }

        public void setTotalCharge(String totalCharge) {
            mTotalCharge = totalCharge;
        }

        public Object getTotalDistance() {
            return mTotalDistance;
        }

        public void setTotalDistance(Object totalDistance) {
            mTotalDistance = totalDistance;
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

        public Object getEndassigneddata() {
            return mEndassigneddata;
        }

        public void setEndassigneddata(Object endassigneddata) {
            mEndassigneddata = endassigneddata;
        }

        public Object getEndpaymentdetail() {
            return mEndpaymentdetail;
        }

        public void setEndpaymentdetail(Object endpaymentdetail) {
            mEndpaymentdetail = endpaymentdetail;
        }


        public Startassineddata getStartassineddata() {
            return mStartassineddata;
        }

        public void setStartassineddata(Startassineddata startassineddata) {
            mStartassineddata = startassineddata;
        }

        public Startpaymentdetail getStartpaymentdetail() {
            return mStartpaymentdetail;
        }

        public void setStartpaymentdetail(Startpaymentdetail startpaymentdetail) {
            mStartpaymentdetail = startpaymentdetail;
        }

    }
    public class Error {


    }
    public class Startassineddata {

        @SerializedName("cab_driver_id")
        private String mCabDriverId;
        @SerializedName("cab_id")
        private String mCabId;
        @SerializedName("cd_name")
        private String mCdName;
        @SerializedName("cd_phone")
        private String mCdPhone;
        @SerializedName("cd_status")
        private String mCdStatus;
        @SerializedName("removal_specialists_id")
        private Object mRemovalSpecialistsId;
        @SerializedName("request_assigned_user_id")
        private String mRequestAssignedUserId;
        @SerializedName("request_id")
        private String mRequestId;
        @SerializedName("rs_name")
        private Object mRsName;
        @SerializedName("rs_phone")
        private Object mRsPhone;
        @SerializedName("rs_status")
        private String mRsStatus;
        @SerializedName("term")
        private String mTerm;

        public String getCabDriverId() {
            return mCabDriverId;
        }

        public void setCabDriverId(String cabDriverId) {
            mCabDriverId = cabDriverId;
        }

        public String getCabId() {
            return mCabId;
        }

        public void setCabId(String cabId) {
            mCabId = cabId;
        }

        public String getCdName() {
            return mCdName;
        }

        public void setCdName(String cdName) {
            mCdName = cdName;
        }

        public String getCdPhone() {
            return mCdPhone;
        }

        public void setCdPhone(String cdPhone) {
            mCdPhone = cdPhone;
        }

        public String getCdStatus() {
            return mCdStatus;
        }

        public void setCdStatus(String cdStatus) {
            mCdStatus = cdStatus;
        }

        public Object getRemovalSpecialistsId() {
            return mRemovalSpecialistsId;
        }

        public void setRemovalSpecialistsId(Object removalSpecialistsId) {
            mRemovalSpecialistsId = removalSpecialistsId;
        }

        public String getRequestAssignedUserId() {
            return mRequestAssignedUserId;
        }

        public void setRequestAssignedUserId(String requestAssignedUserId) {
            mRequestAssignedUserId = requestAssignedUserId;
        }

        public String getRequestId() {
            return mRequestId;
        }

        public void setRequestId(String requestId) {
            mRequestId = requestId;
        }

        public Object getRsName() {
            return mRsName;
        }

        public void setRsName(Object rsName) {
            mRsName = rsName;
        }

        public Object getRsPhone() {
            return mRsPhone;
        }

        public void setRsPhone(Object rsPhone) {
            mRsPhone = rsPhone;
        }

        public String getRsStatus() {
            return mRsStatus;
        }

        public void setRsStatus(String rsStatus) {
            mRsStatus = rsStatus;
        }

        public String getTerm() {
            return mTerm;
        }

        public void setTerm(String term) {
            mTerm = term;
        }

    }
    public class Startpaymentdetail {

        @SerializedName("admin_charge")
        private String mAdminCharge;
        @SerializedName("date")
        private String mDate;
        @SerializedName("gateway")
        private String mGateway;
        @SerializedName("id")
        private String mId;
        @SerializedName("mode_of_payment")
        private String mModeOfPayment;
        @SerializedName("payment_status")
        private String mPaymentStatus;
        @SerializedName("request_id")
        private String mRequestId;
        @SerializedName("rp_charge")
        private String mRpCharge;
        @SerializedName("rs_charge")
        private String mRsCharge;
        @SerializedName("term")
        private String mTerm;
        @SerializedName("total_charge")
        private String mTotalCharge;
        @SerializedName("total_distance")
        private String mTotalDistance;
        @SerializedName("transaction_id")
        private String mTransactionId;

        public String getAdminCharge() {
            return mAdminCharge;
        }

        public void setAdminCharge(String adminCharge) {
            mAdminCharge = adminCharge;
        }

        public String getDate() {
            return mDate;
        }

        public void setDate(String date) {
            mDate = date;
        }

        public String getGateway() {
            return mGateway;
        }

        public void setGateway(String gateway) {
            mGateway = gateway;
        }

        public String getId() {
            return mId;
        }

        public void setId(String id) {
            mId = id;
        }

        public String getModeOfPayment() {
            return mModeOfPayment;
        }

        public void setModeOfPayment(String modeOfPayment) {
            mModeOfPayment = modeOfPayment;
        }

        public String getPaymentStatus() {
            return mPaymentStatus;
        }

        public void setPaymentStatus(String paymentStatus) {
            mPaymentStatus = paymentStatus;
        }

        public String getRequestId() {
            return mRequestId;
        }

        public void setRequestId(String requestId) {
            mRequestId = requestId;
        }

        public String getRpCharge() {
            return mRpCharge;
        }

        public void setRpCharge(String rpCharge) {
            mRpCharge = rpCharge;
        }

        public String getRsCharge() {
            return mRsCharge;
        }

        public void setRsCharge(String rsCharge) {
            mRsCharge = rsCharge;
        }

        public String getTerm() {
            return mTerm;
        }

        public void setTerm(String term) {
            mTerm = term;
        }

        public String getTotalCharge() {
            return mTotalCharge;
        }

        public void setTotalCharge(String totalCharge) {
            mTotalCharge = totalCharge;
        }

        public String getTotalDistance() {
            return mTotalDistance;
        }

        public void setTotalDistance(String totalDistance) {
            mTotalDistance = totalDistance;
        }

        public String getTransactionId() {
            return mTransactionId;
        }

        public void setTransactionId(String transactionId) {
            mTransactionId = transactionId;
        }

    }
}
