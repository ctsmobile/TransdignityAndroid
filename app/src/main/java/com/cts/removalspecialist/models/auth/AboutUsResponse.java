package com.cts.removalspecialist.models.auth;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AboutUsResponse {
    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("error")
    @Expose
    private Error error;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public class Datum {

        @SerializedName("page_id")
        @Expose
        private String pageId;
        @SerializedName("page_name")
        @Expose
        private String pageName;
        @SerializedName("page_desc")
        @Expose
        private String pageDesc;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("page_url")
        @Expose
        private String pageUrl;

        public String getPageId() {
            return pageId;
        }

        public void setPageId(String pageId) {
            this.pageId = pageId;
        }

        public String getPageName() {
            return pageName;
        }

        public void setPageName(String pageName) {
            this.pageName = pageName;
        }

        public String getPageDesc() {
            return pageDesc;
        }

        public void setPageDesc(String pageDesc) {
            this.pageDesc = pageDesc;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getPageUrl() {
            return pageUrl;
        }

        public void setPageUrl(String pageUrl) {
            this.pageUrl = pageUrl;
        }

    }
    public class Error {


    }
}
