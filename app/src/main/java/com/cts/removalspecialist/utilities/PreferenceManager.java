package com.cts.removalspecialist.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

public class PreferenceManager {
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    public Context mContext;


    public static final String SHARED_PREF_NAME = "REMOVALSPECIALIST";

    public static final String introSlideHideKEY = "introSlideHideKEY";
    public static final String loginKEY = "loginKEY";
    public static final String LoginResponseKey = "LoginRespKey";
    public static final String FCMKEY = "fcm";
    public static final String REQUESTID = "request_id";
    public static final String ISONLINE = "isonline";



    public PreferenceManager(Context mContext) {
        this.mContext = mContext;
    }

    public void initPreferences() {
        sharedpreferences = mContext.getSharedPreferences(introSlideHideKEY, mContext.MODE_PRIVATE);
        sharedpreferences = mContext.getSharedPreferences(loginKEY, mContext.MODE_PRIVATE);

    }

    private static SharedPreferences getUserSharedPreferences(Context context) {
        return context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }

    public void setloginPref(Boolean login) {
        initPreferences();
        editor = sharedpreferences.edit();
        editor.putBoolean(loginKEY, login);
        editor.commit();
        editor.apply();
    }

    public Boolean isloginPref() {
        initPreferences();
        Boolean loginkey = sharedpreferences.getBoolean(loginKEY, false);
        return loginkey;
    }

    public void setFirstTimeLaunch(String isFirstTime) {
        initPreferences();
        editor = sharedpreferences.edit();
        editor.putString(introSlideHideKEY, isFirstTime);
        editor.commit();
        editor.apply();
    }

    public String isFirstTimeLaunch() {
        initPreferences();
        return sharedpreferences.getString(introSlideHideKEY, null);
    }

    public void setString(String key, String value) {
        SharedPreferences.Editor editor = getUserSharedPreferences(mContext).edit();
        editor.putString(key, value);
        editor.commit();
    }

    public String getString(String key) {
        return getUserSharedPreferences(mContext).getString(key, null);
    }



    public void setObject(String key, String value) {
        SharedPreferences.Editor editor = getUserSharedPreferences(mContext).edit();
//        editor.putString(key, new Gson().toJson(value));
        editor.putString(key, value);
        editor.commit();
    }

    public Object getObject(String key, final Class<?> aClass) {

        return new Gson().fromJson(getString(key), aClass);
    }

    public void setBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = getUserSharedPreferences(mContext).edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public boolean getBoolean(String key) {
        return getUserSharedPreferences(mContext).getBoolean(key, false);
    }


    public void setFcmString(String key, String value) {
        SharedPreferences.Editor editor = getUserSharedPreferences(mContext).edit();
        editor.putString(key, value);
        editor.commit();
    }

    public String getFcmString(String key) {
        return getUserSharedPreferences(mContext).getString(key, null);
    }
  /*  public void setLoginResponseKey(LoginResponse resp) {
        initPreferences();
        editor = sharedpreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(resp);
        editor.putString(LoginResponseKey, json);
        editor.commit();
        editor.apply();
    }
    public LoginResponse getLoginResponseKey() {
        initPreferences();
        val = (GlobalValues) mContext;
        Gson gson = new Gson();
        String json = sharedpreferences.getString(LoginResponseKey, "");
        LoginResponse objResp = gson.fromJson(json, LoginResponse.class);
        //val.setLoginUserMobile(objResp.getHostDescription().getMobile());
        ///val.setLoginResponse(objResp);
        val.setLoginResponse(objResp);
        return objResp;
    }*/


    public void logout() {
        initPreferences();
        //String lonch = isFirstTimeLaunch();
        editor = sharedpreferences.edit();
        editor.clear();
        editor.apply();
        //mContext.startActivity(mContext, LoginActivity.class);
        //setFirstTimeLaunch(lonch);

    }
}
