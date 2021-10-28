package com.transdignity.deathserviceprovider.utilities;

import android.app.Application;

public class GlobalValues extends Application {
    private static GlobalValues mInstance;
    private String framgentTag;
    private static PreferenceManager preferenceManager;

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
}
