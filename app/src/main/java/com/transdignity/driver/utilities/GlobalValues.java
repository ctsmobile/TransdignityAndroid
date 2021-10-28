package com.transdignity.driver.utilities;

import android.app.Application;

public class GlobalValues extends Application {
    private static GlobalValues mInstance;
    private String framgentTag;
    private static PreferenceManager preferenceManager;
    private  Double lattitude,longitude;
    private String requestStatus;
    public static synchronized GlobalValues getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        preferenceManager = new PreferenceManager(this);
    }
    public static PreferenceManager getPreferenceManager() {
        return preferenceManager;
    }

    public String getFramgentTag() {
        return framgentTag;
    }

    public void setFramgentTag(String framgentTag) {
        this.framgentTag = framgentTag;
    }

    public Double getLattitude() {
        return lattitude;
    }

    public void setLattitude(Double lattitude) {
        this.lattitude = lattitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
    }
}

