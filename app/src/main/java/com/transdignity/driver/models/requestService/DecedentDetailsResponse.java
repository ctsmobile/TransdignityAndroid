package com.transdignity.driver.models.requestService;

import com.google.gson.annotations.SerializedName;

public class DecedentDetailsResponse {

    @SerializedName("data")
    private Data data;

    @SerializedName("success")
    private String success;

    @SerializedName("token_valid")
    private String token_valid;

    @SerializedName("message")
    private String message;

    @SerializedName("error")
    private Error error;

    public void setData(Data data) {
        this.data = data;
    }

    public Data getData() {
        return data;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getSuccess() {
        return success;
    }

    public void setTokenValid(String token_valid) {
        this.token_valid = token_valid;
    }

    public String getTokenValid() {
        return token_valid;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setError(Error error) {
        this.error = error;
    }

    public Error getError() {
        return error;
    }

    public static class Data {

        @SerializedName("decendant_middle_name")
        private String decendantMiddleName;

        @SerializedName("address")
        private String address;

        @SerializedName("phone")
        private String phone;

        @SerializedName("decendant_first_name")
        private String decendantFirstName;

        @SerializedName("requested_items")
        private String requestedItems;

        @SerializedName("drop_location")
        private String dropLocation;

        @SerializedName("decendant_last_name")
        private String decendantLastName;

        @SerializedName("latitude")
        private String lattitude;

        @SerializedName("longitude")
        private String longitude;
        @SerializedName("drop_location_latitude")
        private String dropLocationLatitude;
        @SerializedName("drop_location_longitude")
        private String dropLocationLongitude;

        public void setDecendantMiddleName(String decendantMiddleName) {
            this.decendantMiddleName = decendantMiddleName;
        }

        public String getDecendantMiddleName() {
            return decendantMiddleName;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getAddress() {
            return address;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPhone() {
            return phone;
        }

        public void setDecendantFirstName(String decendantFirstName) {
            this.decendantFirstName = decendantFirstName;
        }

        public String getDecendantFirstName() {
            return decendantFirstName;
        }

        public void setRequestedItems(String requestedItems) {
            this.requestedItems = requestedItems;
        }

        public String getRequestedItems() {
            return requestedItems;
        }

        public void setDropLocation(String dropLocation) {
            this.dropLocation = dropLocation;
        }

        public String getDropLocation() {
            return dropLocation;
        }

        public void setDecendantLastName(String decendantLastName) {
            this.decendantLastName = decendantLastName;
        }

        public String getDecendantLastName() {
            return decendantLastName;
        }

        public String getLattitude() {
            return lattitude;
        }

        public void setLattitude(String lattitude) {
            this.lattitude = lattitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getDropLocationLatitude() {
            return dropLocationLatitude;
        }

        public void setDropLocationLatitude(String dropLocationLatitude) {
            this.dropLocationLatitude = dropLocationLatitude;
        }

        public String getDropLocationLongitude() {
            return dropLocationLongitude;
        }

        public void setDropLocationLongitude(String dropLocationLongitude) {
            this.dropLocationLongitude = dropLocationLongitude;
        }
    }

    public class Error {

    }
}