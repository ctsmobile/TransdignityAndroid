
package com.transdignity.deathserviceprovider.models.request.Services;

import com.google.gson.annotations.SerializedName;


public class AirportArrivalDepartureRequestModel {

    @SerializedName("date_of_request")
    private String mDateOfRequest;
    @SerializedName("decendent_dob")
    private String mDecendentDob;
    @SerializedName("decendent_dod")
    private String mDecendentDod;
    @SerializedName("decendent_name")
    private String mDecendentName;
    @SerializedName("decendent_pickedaddress_ofbody")
    private String mDecendentPickedaddressOfbody;
    @SerializedName("decendent_pickedaddress_ofbody_latlong")
    private String mDecendentPickedaddressOfbodyLatlong;
    @SerializedName("decendent_pod")
    private String mDecendentPod;
    @SerializedName("decendent_tod")
    private String mDecendentTod;
    @SerializedName("estimated_date_arrival_of_decendent")
    private String mEstimatedDateArrivalOfDecendent;
    @SerializedName("flightdetail")
    private Flightdetail mFlightdetail;
    @SerializedName("request_created_by")
    private String mRequestCreatedBy;
    @SerializedName("requested_by")
    private String mRequestedBy;
    @SerializedName("requestor_contact_number")
    private String mRequestorContactNumber;
    @SerializedName("service_id")
    private String mServiceId;
    @SerializedName("transferred_address")
    private String mTransferredAddress;
    @SerializedName("transferred_address_latlong")
    private String mTransferredAddressLatlong;

    public String getDateOfRequest() {
        return mDateOfRequest;
    }

    public void setDateOfRequest(String dateOfRequest) {
        mDateOfRequest = dateOfRequest;
    }

    public String getDecendentDob() {
        return mDecendentDob;
    }

    public void setDecendentDob(String decendentDob) {
        mDecendentDob = decendentDob;
    }

    public String getDecendentDod() {
        return mDecendentDod;
    }

    public void setDecendentDod(String decendentDod) {
        mDecendentDod = decendentDod;
    }

    public String getDecendentName() {
        return mDecendentName;
    }

    public void setDecendentName(String decendentName) {
        mDecendentName = decendentName;
    }

    public String getDecendentPickedaddressOfbody() {
        return mDecendentPickedaddressOfbody;
    }

    public void setDecendentPickedaddressOfbody(String decendentPickedaddressOfbody) {
        mDecendentPickedaddressOfbody = decendentPickedaddressOfbody;
    }

    public String getDecendentPickedaddressOfbodyLatlong() {
        return mDecendentPickedaddressOfbodyLatlong;
    }

    public void setDecendentPickedaddressOfbodyLatlong(String decendentPickedaddressOfbodyLatlong) {
        mDecendentPickedaddressOfbodyLatlong = decendentPickedaddressOfbodyLatlong;
    }

    public String getDecendentPod() {
        return mDecendentPod;
    }

    public void setDecendentPod(String decendentPod) {
        mDecendentPod = decendentPod;
    }

    public String getDecendentTod() {
        return mDecendentTod;
    }

    public void setDecendentTod(String decendentTod) {
        mDecendentTod = decendentTod;
    }

    public String getEstimatedDateArrivalOfDecendent() {
        return mEstimatedDateArrivalOfDecendent;
    }

    public void setEstimatedDateArrivalOfDecendent(String estimatedDateArrivalOfDecendent) {
        mEstimatedDateArrivalOfDecendent = estimatedDateArrivalOfDecendent;
    }

    public Flightdetail getFlightdetail() {
        return mFlightdetail;
    }

    public void setFlightdetail(Flightdetail flightdetail) {
        mFlightdetail = flightdetail;
    }

    public String getRequestCreatedBy() {
        return mRequestCreatedBy;
    }

    public void setRequestCreatedBy(String requestCreatedBy) {
        mRequestCreatedBy = requestCreatedBy;
    }

    public String getRequestedBy() {
        return mRequestedBy;
    }

    public void setRequestedBy(String requestedBy) {
        mRequestedBy = requestedBy;
    }

    public String getRequestorContactNumber() {
        return mRequestorContactNumber;
    }

    public void setRequestorContactNumber(String requestorContactNumber) {
        mRequestorContactNumber = requestorContactNumber;
    }

    public String getServiceId() {
        return mServiceId;
    }

    public void setServiceId(String serviceId) {
        mServiceId = serviceId;
    }

    public String getTransferredAddress() {
        return mTransferredAddress;
    }

    public void setTransferredAddress(String transferredAddress) {
        mTransferredAddress = transferredAddress;
    }

    public String getTransferredAddressLatlong() {
        return mTransferredAddressLatlong;
    }

    public void setTransferredAddressLatlong(String transferredAddressLatlong) {
        mTransferredAddressLatlong = transferredAddressLatlong;
    }
    public class Flightdetail {

        @SerializedName("date_of_journey")
        private String mDateOfJourney;
        @SerializedName("flight_name")
        private String mFlightName;
        @SerializedName("flight_number")
        private String mFlightNumber;
        @SerializedName("flight_pnr")
        private String mFlightPnr;
        @SerializedName("from_address")
        private String mFromAddress;
        @SerializedName("from_address_latlong")
        private String mFromAddressLatlong;
        @SerializedName("time_of_arrival")
        private String mTimeOfArrival;
        @SerializedName("time_of_departure")
        private String mTimeOfDeparture;
        @SerializedName("to_address")
        private String mToAddress;
        @SerializedName("to_address_latlong")
        private String mToAddressLatlong;

        public String getDateOfJourney() {
            return mDateOfJourney;
        }

        public void setDateOfJourney(String dateOfJourney) {
            mDateOfJourney = dateOfJourney;
        }

        public String getFlightName() {
            return mFlightName;
        }

        public void setFlightName(String flightName) {
            mFlightName = flightName;
        }

        public String getFlightNumber() {
            return mFlightNumber;
        }

        public void setFlightNumber(String flightNumber) {
            mFlightNumber = flightNumber;
        }

        public String getFlightPnr() {
            return mFlightPnr;
        }

        public void setFlightPnr(String flightPnr) {
            mFlightPnr = flightPnr;
        }

        public String getFromAddress() {
            return mFromAddress;
        }

        public void setFromAddress(String fromAddress) {
            mFromAddress = fromAddress;
        }

        public String getFromAddressLatlong() {
            return mFromAddressLatlong;
        }

        public void setFromAddressLatlong(String fromAddressLatlong) {
            mFromAddressLatlong = fromAddressLatlong;
        }

        public String getTimeOfArrival() {
            return mTimeOfArrival;
        }

        public void setTimeOfArrival(String timeOfArrival) {
            mTimeOfArrival = timeOfArrival;
        }

        public String getTimeOfDeparture() {
            return mTimeOfDeparture;
        }

        public void setTimeOfDeparture(String timeOfDeparture) {
            mTimeOfDeparture = timeOfDeparture;
        }

        public String getToAddress() {
            return mToAddress;
        }

        public void setToAddress(String toAddress) {
            mToAddress = toAddress;
        }

        public String getToAddressLatlong() {
            return mToAddressLatlong;
        }

        public void setToAddressLatlong(String toAddressLatlong) {
            mToAddressLatlong = toAddressLatlong;
        }

    }

}
