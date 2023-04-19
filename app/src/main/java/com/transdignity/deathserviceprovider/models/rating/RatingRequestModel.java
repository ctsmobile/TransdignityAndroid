
package com.transdignity.deathserviceprovider.models.rating;

import com.google.gson.annotations.SerializedName;


public class RatingRequestModel {

    @SerializedName("comments")
    private String mComments;
    @SerializedName("ratings")
    private String mRatings;
    @SerializedName("request_id")
    private String mRequestId;
    @SerializedName("user_id")
    private String mUserId;

    public String getComments() {
        return mComments;
    }

    public void setComments(String comments) {
        mComments = comments;
    }

    public String getRatings() {
        return mRatings;
    }

    public void setRatings(String ratings) {
        mRatings = ratings;
    }

    public String getRequestId() {
        return mRequestId;
    }

    public void setRequestId(String requestId) {
        mRequestId = requestId;
    }

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String userId) {
        mUserId = userId;
    }

}
