
package com.transdignity.driver;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.os.Bundle;
import android.os.Handler;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.transdignity.driver.fragments.EarningFragment;
import com.transdignity.driver.fragments.HomeFragment;
import com.transdignity.driver.fragments.InfoFragment;
import com.transdignity.driver.fragments.OrderPickedFragment;
import com.transdignity.driver.fragments.PickupDriverFragment;
import com.transdignity.driver.fragments.SettingsFragment;
import com.transdignity.driver.fragments.VehicleDetailsFragment;
import com.transdignity.driver.fragments.rating.RatingFragment;
import com.transdignity.driver.models.auth.LoginResponse;
import com.transdignity.driver.models.trackingDriverStatus.TrackingDriver;
import com.transdignity.driver.models.vehicle.VehicleDetailsResponseModel;
import com.transdignity.driver.network.ApiClients;
import com.transdignity.driver.network.ApiInterface;
import com.transdignity.driver.utilities.CommonUtils;
import com.transdignity.driver.utilities.GlobalValues;
import com.transdignity.driver.utilities.LoadingProgressDialog;
import com.transdignity.driver.utilities.MyConstants;
import com.transdignity.driver.utilities.PreferenceManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    boolean doubleBackToExitPressedOnce = false;
    String fragmtStatusTag = "";
    Fragment fragmentHome,fragmentEarn,fragmentRating,fragmentSetting,fragmentProfile;
    public BottomNavigationView navView;
    SwitchCompat driverStatusSwitch;
    TextView tvOnlineStatus;
    LoginResponse loginResponse;
    String userId,request_id;
    Ringtone r;
    MediaPlayer ring;
    Boolean isRingTonePlay = false;
    Fragment fragment;
    String vehicle_details="";
    String vehicle_type="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // FirebaseCrash.report(new Exception("My  first android no fatel error!!"));
        //   FirebaseCrash.log("log main firebase");
        initView();
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navView.setItemIconTintList(null);
        fragmentHome = new HomeFragment();
        fragmtStatusTag = fragmentHome.getClass().getName();
        GlobalValues.getInstance().setFramgentTag(fragmtStatusTag);
        CommonUtils.loadFragment(this, fragmentHome, fragmtStatusTag);
       // reStoreRequestApi();
        //trackStatus();
        currentRideApi();
        getVehicleDetailsApi();
        trackDriverStatus();

        //trackDriverStatus();
    }

    void initView() {
        loginResponse = (LoginResponse) GlobalValues.getPreferenceManager().getObject(PreferenceManager.LoginResponseKey, LoginResponse.class);
        if (loginResponse != null) {
            userId = loginResponse.getData().getId();
            request_id = GlobalValues.getPreferenceManager().getString(PreferenceManager.REQUESTID);

        }

        navView = findViewById(R.id.nav_view);
        driverStatusSwitch = findViewById(R.id.driverStatusSwitch);
        tvOnlineStatus = findViewById(R.id.tv_online_status);

        fragmentHome =  HomeFragment.newInstance();
        fragmentEarn =  EarningFragment.newInstance();
        //fragmentRating = new Ra
        fragmentSetting =  SettingsFragment.newInstance();
        onlineAvailabilityStatus();
        setOnlineOfflineStatus();
        switchCopact();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragment = new HomeFragment();
                    fragmtStatusTag = fragment.getClass().getName();
                    GlobalValues.getInstance().setFramgentTag(fragmtStatusTag);
                    CommonUtils.loadFragment(MainActivity.this, fragment, fragmtStatusTag);
                    //trackStatus();
                    trackDriverStatus();
                    // trackDriverStatus();
                    return true;


              /*  case R.id.navigation_home:
                   // reStoreRequestApi();
                  *//*  if (GlobalValues.getInstance().getRequestStatus() != null) {
                        if (GlobalValues.getInstance().getRequestStatus().equalsIgnoreCase("accept")) {
                            Fragment fragment = OrderPickedFragment.newInstance();
                            String tagfragment = fragment.getClass().getName();
                            //selectedNavItem(R.id.navigation_home);
                            GlobalValues.getInstance().setFramgentTag(tagfragment);
                            CommonUtils.loadFragment(MainActivity.this, fragment, tagfragment);
                        } else if (GlobalValues.getInstance().getRequestStatus().equalsIgnoreCase("reached_on_rs_location")) {
                            Fragment fragment = InfoFragment.newInstance(GlobalValues.getInstance().getRequestStatus());
                            String tagfragment = fragment.getClass().getName();
                            //selectedNavItem(R.id.navigation_home);
                            GlobalValues.getInstance().setFramgentTag(tagfragment);
                            CommonUtils.loadFragment(MainActivity.this, fragment, tagfragment);

                        } else if (GlobalValues.getInstance().getRequestStatus().equalsIgnoreCase("pickup_rs")) {
                            Fragment fragment = InfoFragment.newInstance(GlobalValues.getInstance().getRequestStatus());
                            String tagfragment = fragment.getClass().getName();
                            //selectedNavItem(R.id.navigation_home);
                            GlobalValues.getInstance().setFramgentTag(tagfragment);
                            CommonUtils.loadFragment(MainActivity.this, fragment, tagfragment);
                        } else if (GlobalValues.getInstance().getRequestStatus().equalsIgnoreCase("reached_on_decendant_location")) {
                            Fragment fragment = InfoFragment.newInstance(GlobalValues.getInstance().getRequestStatus());
                            String tagfragment = fragment.getClass().getName();
                            // selectedNavItem(R.id.navigation_home);
                            GlobalValues.getInstance().setFramgentTag(tagfragment);
                            CommonUtils.loadFragment(MainActivity.this, fragment, tagfragment);
                        }else if (GlobalValues.getInstance().getRequestStatus().equalsIgnoreCase("reached_on_pickup_location")) {
                            Fragment fragment = PickupDriverFragment.newInstance(GlobalValues.getInstance().getRequestStatus());
                            String tagfragment = fragment.getClass().getName();
                            // selectedNavItem(R.id.navigation_home);
                            GlobalValues.getInstance().setFramgentTag(tagfragment);
                            CommonUtils.loadFragment(MainActivity.this, fragment, tagfragment);
                        }
                        else {
                           // fragmentHome = new HomeFragment();
                            fragmtStatusTag = fragmentHome.getClass().getName();
                            GlobalValues.getInstance().setFramgentTag(fragmtStatusTag);
                            CommonUtils.loadFragment(MainActivity.this, fragmentHome, fragmtStatusTag);
                        }
                    } else {*//*
                      //  fragment = new HomeFragment();
                        fragmtStatusTag = fragmentHome.getClass().getName();
                        GlobalValues.getInstance().setFramgentTag(fragmtStatusTag);
                        CommonUtils.loadFragment(MainActivity.this, fragmentHome, fragmtStatusTag);

                   // }
                    break;*/
                case R.id.navigation_earning:
                    fragment = new EarningFragment();
                    fragmtStatusTag = fragment.getClass().getName();
                    GlobalValues.getInstance().setFramgentTag(fragmtStatusTag);
                    CommonUtils.loadFragment(MainActivity.this, fragment, fragmtStatusTag);

                    return true;

                case R.id.navigation_rating:

                    fragment = new RatingFragment();
                    fragmtStatusTag = fragment.getClass().getName();
                    GlobalValues.getInstance().setFramgentTag(fragmtStatusTag);
                    CommonUtils.loadFragment(MainActivity.this, fragment, fragmtStatusTag);

                    return true;
                case R.id.navigaton_account:
                    fragment = new SettingsFragment();
                    fragmtStatusTag = fragmentSetting.getClass().getName();
                    GlobalValues.getInstance().setFramgentTag(fragmtStatusTag);
                    CommonUtils.loadFragment(MainActivity.this, fragment, fragmtStatusTag);

                    return true;
            }
            return false;
        }
    };

    void switchCopact() {
        driverStatusSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean check) {
                if (check) {
                    getVehicleDetailsApi();
                    if(vehicle_details=="true"){
                        updateOnlineStatus("1");
                    }else if(vehicle_details=="false"){
                        driverStatusSwitch.setChecked(false);
                        showAlert();
                    }
                } else {
                    updateOnlineStatus("0");
                }
            }
        });
    }

    void onlineAvailabilityStatus() {
        final LoadingProgressDialog dialog = new LoadingProgressDialog(this, "please Wait!");
        dialog.setCancelable(false);
        dialog.show();
        ApiInterface apiInterface = ApiClients.getConnection(this);
        Call<JsonObject> call = apiInterface.onlineAvailablityStatusApi(loginResponse.getData().getToken());
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                try {
                    dialog.dismiss();
                    if (response.code() >= 200 && response.code() <= 210) {
                        JSONObject jsonObj = new JSONObject(response.body().toString());
                        if (jsonObj.optString("success").equalsIgnoreCase("true")) {
                            if (jsonObj.getJSONObject("data") != null) {
                                String status = jsonObj.getJSONObject("data").optString("online");
                                if (status.equalsIgnoreCase("1")) {
                                    GlobalValues.getPreferenceManager().setBoolean(PreferenceManager.ISONLINE, true);
                                } else {
                                    GlobalValues.getPreferenceManager().setBoolean(PreferenceManager.ISONLINE, false);
                                }
                                setOnlineOfflineStatus();
                            }
                        }
                    } else {
                        BufferedReader reader = null;
                        StringBuilder sb = new StringBuilder();
                        try {
                            reader = new BufferedReader(new InputStreamReader(response.errorBody().byteStream()));
                            String line;
                            try {
                                while ((line = reader.readLine()) != null) {
                                    sb.append(line);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            String finallyError = sb.toString();
                            JSONObject jsonObjectError = new JSONObject(finallyError);
                            String message = jsonObjectError.optString("message");
                            //    Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
                            if (jsonObjectError.optString("token_valid").equalsIgnoreCase("false")) {
                                CommonUtils.logoutSession(MainActivity.this);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                dialog.dismiss();
            }
        });
    }

    void reStoreRequestApi() {

        final LoadingProgressDialog dialog = new LoadingProgressDialog(this, "please Wait!");
        dialog.setCancelable(false);
        dialog.show();
        ApiInterface apiInterface = ApiClients.getConnection(this);
        Call<JsonObject> call = apiInterface.reStoreRequestApi(loginResponse.getData().getToken(), userId);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                try {
                    dialog.dismiss();
                    if (response.code() >= 200 && response.code() <= 210) {
                        JSONObject object = new JSONObject(response.body().toString());
                         Toast.makeText(MainActivity.this, object.optString("message"), Toast.LENGTH_SHORT).show();
                        if (object.optString("success").equalsIgnoreCase("true")) {
                            if (object.getJSONObject("data") != null) {
                                GlobalValues.getInstance().setRequestStatus(object.getJSONObject("data").optString("status"));
                                GlobalValues.getPreferenceManager().setString(PreferenceManager.REQUESTID, object.getJSONObject("data").optString("request_id"));
                                if (object.getJSONObject("data").optString("status").equalsIgnoreCase("accept")) {
                                    Fragment fragment = OrderPickedFragment.newInstance();
                                    String tagfragment = fragment.getClass().getName();
                                   // selectedNavItem(R.id.navigation_home);
                                    GlobalValues.getInstance().setFramgentTag(tagfragment);
                                    CommonUtils.loadFragment(MainActivity.this, fragment, tagfragment);
                                } else if (object.getJSONObject("data").optString("status").equalsIgnoreCase("reached_on_rs_location")) {
                                    Fragment fragment = InfoFragment.newInstance(object.getJSONObject("data").optString("status"));
                                    String tagfragment = fragment.getClass().getName();
                                   // selectedNavItem(R.id.navigation_home);
                                    GlobalValues.getInstance().setFramgentTag(tagfragment);
                                    CommonUtils.loadFragment(MainActivity.this, fragment, tagfragment);
                                } else if (object.getJSONObject("data").optString("status").equalsIgnoreCase("pickup_rs")) {
                                    Fragment fragment = InfoFragment.newInstance(object.getJSONObject("data").optString("status"));
                                    String tagfragment = fragment.getClass().getName();
                                   // selectedNavItem(R.id.navigation_home);
                                    GlobalValues.getInstance().setFramgentTag(tagfragment);
                                    CommonUtils.loadFragment(MainActivity.this, fragment, tagfragment);
                                } else if (object.getJSONObject("data").optString("status").equalsIgnoreCase("reached_on_decendant_location")) {
                                    Fragment fragment = InfoFragment.newInstance(object.getJSONObject("data").optString("status"));
                                    String tagfragment = fragment.getClass().getName();
                                   // selectedNavItem(R.id.navigation_home);
                                    GlobalValues.getInstance().setFramgentTag(tagfragment);
                                    CommonUtils.loadFragment(MainActivity.this, fragment, tagfragment);
                                }

                            }
                            /*Fragment fragment = HomeFragment.newInstance();
                            String tagfragment = fragment.getClass().getName();
                            GlobalValues.getInstance().setFramgentTag(tagfragment);
                            CommonUtils.loadFragment(MainActivity.this, fragment, tagfragment);*/
                        }
                    } else {
                        BufferedReader reader = null;
                        StringBuilder sb = new StringBuilder();
                        try {
                            reader = new BufferedReader(new InputStreamReader(response.errorBody().byteStream()));
                            String line;
                            try {
                                while ((line = reader.readLine()) != null) {
                                    sb.append(line);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            String finallyError = sb.toString();
                            JSONObject jsonObjectError = new JSONObject(finallyError);
                            String message = jsonObjectError.optString("message");
                            //  Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
                            if (jsonObjectError.optString("token_valid").equalsIgnoreCase("false")) {
                                CommonUtils.logoutSession(MainActivity.this);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                dialog.dismiss();
            }
        });
    }

    void updateOnlineStatus(String status) {
        final LoadingProgressDialog dialog = new LoadingProgressDialog(this, "please Wait!");
        dialog.setCancelable(false);
        dialog.show();
        ApiInterface apiInterface = ApiClients.getConnection(this);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("user_id", userId);
        jsonObject.addProperty("user_group_id", MyConstants.GROUP_ID);
        jsonObject.addProperty("is_online", status);
        Call<JsonObject> call = apiInterface.updateOfflineStatusApi(loginResponse.getData().getToken(), jsonObject);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                try {
                    dialog.dismiss();
                    if (response.code() >= 200 && response.code() <= 210) {
                        JSONObject jsonObj = new JSONObject(response.body().toString());
                        if (jsonObj.optString("success").equalsIgnoreCase("true")) {
                            if (jsonObj.getJSONObject("data") != null) {
                                String status = jsonObj.getJSONObject("data").optString("online");
                                if (status.equalsIgnoreCase("1")) {
                                    GlobalValues.getPreferenceManager().setBoolean(PreferenceManager.ISONLINE, true);
                                } else {
                                    GlobalValues.getPreferenceManager().setBoolean(PreferenceManager.ISONLINE, false);
                                }
                                setOnlineOfflineStatus();
                            }
                        }
                    } else {
                        BufferedReader reader = null;
                        StringBuilder sb = new StringBuilder();
                        try {
                            reader = new BufferedReader(new InputStreamReader(response.errorBody().byteStream()));
                            String line;
                            try {
                                while ((line = reader.readLine()) != null) {
                                    sb.append(line);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            String finallyError = sb.toString();
                            JSONObject jsonObjectError = new JSONObject(finallyError);
                            String message = jsonObjectError.optString("message");
                            Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
                            if (jsonObjectError.optString("token_valid").equalsIgnoreCase("false")) {
                                CommonUtils.logoutSession(MainActivity.this);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                dialog.dismiss();
            }
        });
    }

    void setOnlineOfflineStatus() {
        Boolean status = GlobalValues.getPreferenceManager().getBoolean(PreferenceManager.ISONLINE);
        if (status) {
            tvOnlineStatus.setText("Online");
            driverStatusSwitch.setChecked(true);
        } else {
            tvOnlineStatus.setText("Offline");
            driverStatusSwitch.setChecked(false);
        }
    }



    @Override
    public void onBackPressed() {
        //fragment = new HomeFragment();
        String currentTag = GlobalValues.getInstance().getFramgentTag();
        if (!currentTag.equalsIgnoreCase(fragmentHome.getClass().getName())) {
            fragmtStatusTag = fragmentHome.getClass().getName();
            GlobalValues.getInstance().setFramgentTag(fragmtStatusTag);
            CommonUtils.loadFragment(MainActivity.this, fragmentHome, fragmtStatusTag);

            selectedNavItem(R.id.navigation_home);
            return;
        } else if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK PRESS again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    public void selectedNavItem(int item) {
        navView.setSelectedItemId(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    // tracking driver status
    void trackStatus() {
        ApiInterface apiInterface = ApiClients.getConnection(this);
        Call<TrackingDriver> call = apiInterface.trackStatusApi(loginResponse.getData().getToken(), request_id);
        call.enqueue(new Callback<TrackingDriver>() {
            @Override
            public void onResponse(Call<TrackingDriver> call, Response<TrackingDriver> response) {
                try {

                    if (response.code() >= 200 && response.code() <= 210) {
                        if (response.body().getSuccess().equalsIgnoreCase("true")) {
                            String rq_accept = response.body().getData().getRequestAcceptedTitme();
                            String rq_pickupLocation = response.body().getData().getReachedOnDecendantPickupLocationTime();
                            String rq_pickup = response.body().getData().getmPickupDecendentTime();
                            String rq_drop = response.body().getData().getDropDecendantTime();

                            String service_id = response.body().getData().getServiceId();
                            if(service_id.equals("1")||service_id.equals("5")||service_id.equals("8")||service_id.equals("7")||service_id.equals("9")){
                                reStoreRequestApi();
                            }else {
                                if(rq_accept!=null){

                                    if(rq_pickupLocation!=null){

                                        if(rq_pickup!=null){

                                            if(rq_drop!=null){
                                                fragmtStatusTag = fragmentHome.getClass().getName();
                                                GlobalValues.getInstance().setFramgentTag(fragmtStatusTag);
                                                CommonUtils.loadFragment(MainActivity.this, fragmentHome, fragmtStatusTag);
                                            }else {
                                                Fragment fragment2 = PickupDriverFragment.newInstance("navigate");
                                                String tagfragment2 = fragment2.getClass().getName();
                                                // selectedNavItem(R.id.navigation_home);
                                                GlobalValues.getInstance().setFramgentTag(tagfragment2);
                                                CommonUtils.loadFragment(MainActivity.this, fragment2, tagfragment2);
                                            }
                                        }else {
                                            Fragment fragment1 = PickupDriverFragment.newInstance("pickup");
                                            String tagfragment1 = fragment1.getClass().getName();
                                            // selectedNavItem(R.id.navigation_home);
                                            GlobalValues.getInstance().setFramgentTag(tagfragment1);
                                            CommonUtils.loadFragment(MainActivity.this, fragment1, tagfragment1);
                                        }
                                    }else {
                                        Fragment fragment = OrderPickedFragment.newInstance();
                                        String tagfragment = fragment.getClass().getName();
                                        //selectedNavItem(R.id.navigation_home);
                                        GlobalValues.getInstance().setFramgentTag(tagfragment);
                                        CommonUtils.loadFragment(MainActivity.this, fragment, tagfragment);
                                    }
                                }else {
                                    fragmtStatusTag = fragmentHome.getClass().getName();
                                    GlobalValues.getInstance().setFramgentTag(fragmtStatusTag);
                                    CommonUtils.loadFragment(MainActivity.this, fragmentHome, fragmtStatusTag);
                                }
                            }

                        } else {
                        }

                    } else {
                        BufferedReader reader = null;
                        StringBuilder sb = new StringBuilder();
                        try {
                            reader = new BufferedReader(new InputStreamReader(response.errorBody().byteStream()));
                            String line;
                            try {
                                while ((line = reader.readLine()) != null) {
                                    sb.append(line);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            String finallyError = sb.toString();
                            JSONObject jsonObjectError = new JSONObject(finallyError);
                            String message = jsonObjectError.optString("message");
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                            if (jsonObjectError.optString("token_valid").equalsIgnoreCase("false")) {
                                CommonUtils.logoutSession(MainActivity.this);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<TrackingDriver> call, Throwable t) {


            }
        });
    }

    void trackDriverStatus() {

        ApiInterface apiInterface = ApiClients.getConnection(this);
        Call<JsonObject> call = apiInterface.getDriverTrackApi(loginResponse.getData().getToken(),userId, request_id);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                try {

                    if (response.code() >= 200 && response.code() <= 210) {

                        JSONObject jsonObject=new JSONObject(response.body().toString());
                         String success=jsonObject.getString("success");
                         if(success.equals("true")){
                             JSONObject jsonObject1= jsonObject.getJSONObject("data");
                             String service_id=jsonObject1.getString("service_id");

                             if(service_id.equals("1")){
                                 String removal_specialists_assign_time=jsonObject1.getString("removal_specialists_assign_time");
                                 String cab_driver_assign_time=jsonObject1.getString("cab_driver_assign_time");
                                 String reached_on_rs_location_time=jsonObject1.getString("reached_on_rs_location_time");
                                 String pickup_rs_time=jsonObject1.getString("pickup_rs_time");
                                 String reached_on_decendant_pickup_location_time=jsonObject1.getString("reached_on_decendant_pickup_location_time");
                                 String pickup_decendent_time=jsonObject1.getString("pickup_decendent_time");
                                 String drop_decendant_time=jsonObject1.getString("drop_decendant_time");
                                 if(cab_driver_assign_time!="null"){
                                     if(reached_on_rs_location_time!="null"){
                                         if(pickup_rs_time!="null"){
                                             if(reached_on_decendant_pickup_location_time!="null"){
                                                if(pickup_decendent_time!="null"){
                                                    if(drop_decendant_time!="null"){
                                                        fragmtStatusTag = fragmentHome.getClass().getName();
                                                        GlobalValues.getInstance().setFramgentTag(fragmtStatusTag);
                                                        CommonUtils.loadFragment(MainActivity.this, fragmentHome, fragmtStatusTag);

                                                    }else {
                                                        Fragment fragment = PickupDriverFragment.newInstance("descendant2");
                                                        String tagfragment = fragment.getClass().getName();
                                                        // selectedNavItem(R.id.navigation_home);
                                                        GlobalValues.getInstance().setFramgentTag(tagfragment);
                                                        CommonUtils.loadFragment(MainActivity.this, fragment, tagfragment);
                                                    }
                                                }else {
                                                    Fragment fragment = PickupDriverFragment.newInstance("descendant1");
                                                    String tagfragment = fragment.getClass().getName();
                                                    // selectedNavItem(R.id.navigation_home);
                                                    GlobalValues.getInstance().setFramgentTag(tagfragment);
                                                    CommonUtils.loadFragment(MainActivity.this, fragment, tagfragment);
                                                }
                                             }else {
                                                 Fragment fragment = PickupDriverFragment.newInstance("descendant");
                                                 String tagfragment = fragment.getClass().getName();
                                                 // selectedNavItem(R.id.navigation_home);
                                                 GlobalValues.getInstance().setFramgentTag(tagfragment);
                                                 CommonUtils.loadFragment(MainActivity.this, fragment, tagfragment);

                                             }
                                         }else {
                                             Fragment fragment = PickupDriverFragment.newInstance("removal");
                                             String tagfragment = fragment.getClass().getName();
                                             // selectedNavItem(R.id.navigation_home);
                                             GlobalValues.getInstance().setFramgentTag(tagfragment);
                                             CommonUtils.loadFragment(MainActivity.this, fragment, tagfragment);

                                         }
                                     }else {
                                         Fragment fragment = OrderPickedFragment.newInstance();
                                         String tagfragment = fragment.getClass().getName();
                                         GlobalValues.getInstance().setFramgentTag(tagfragment);
                                         CommonUtils.loadFragment(MainActivity.this, fragment, tagfragment);
                                     }
                                 }else {
                                     fragmtStatusTag = fragmentHome.getClass().getName();
                                     GlobalValues.getInstance().setFramgentTag(fragmtStatusTag);
                                     CommonUtils.loadFragment(MainActivity.this, fragmentHome, fragmtStatusTag);

                                 }
                             }
                             else if(service_id.equals("2")){
                                 String request_accepted_titme=jsonObject1.getString("request_accepted_titme");
                                 String reached_on_decendant_pickup_location_time=jsonObject1.getString("reached_on_decendant_pickup_location_time");
                                 String pickup_decendent_time=jsonObject1.getString("pickup_decendent_time");
                                 String drop_decendant_time=jsonObject1.getString("drop_decendant_time");
                                 if(request_accepted_titme!="null"){
                                     if(reached_on_decendant_pickup_location_time!="null"){
                                         if(pickup_decendent_time!="null"){
                                             if(drop_decendant_time!="null"){
                                                 fragmtStatusTag = fragmentHome.getClass().getName();
                                                 GlobalValues.getInstance().setFramgentTag(fragmtStatusTag);
                                                 CommonUtils.loadFragment(MainActivity.this, fragmentHome, fragmtStatusTag);

                                             }else {
                                                 Fragment fragment = PickupDriverFragment.newInstance("descendant1");
                                                 String tagfragment = fragment.getClass().getName();
                                                 // selectedNavItem(R.id.navigation_home);
                                                 GlobalValues.getInstance().setFramgentTag(tagfragment);
                                                 CommonUtils.loadFragment(MainActivity.this, fragment, tagfragment);

                                             }

                                         }else {
                                             Fragment fragment = PickupDriverFragment.newInstance("descendant");
                                             String tagfragment = fragment.getClass().getName();
                                             // selectedNavItem(R.id.navigation_home);
                                             GlobalValues.getInstance().setFramgentTag(tagfragment);
                                             CommonUtils.loadFragment(MainActivity.this, fragment, tagfragment);

                                         }

                                     }else {
                                         Fragment fragment = OrderPickedFragment.newInstance();
                                         String tagfragment = fragment.getClass().getName();
                                         GlobalValues.getInstance().setFramgentTag(tagfragment);
                                         CommonUtils.loadFragment(MainActivity.this, fragment, tagfragment);

                                     }
                                 }else {
                                     fragmtStatusTag = fragmentHome.getClass().getName();
                                     GlobalValues.getInstance().setFramgentTag(fragmtStatusTag);
                                     CommonUtils.loadFragment(MainActivity.this, fragmentHome, fragmtStatusTag);

                                 }
                             }
                             else if(service_id.equals("3")){
                                 String request_accepted_titme=jsonObject1.getString("request_accepted_titme");
                                 String reached_on_decendant_pickup_location_time=jsonObject1.getString("reached_on_decendant_pickup_location_time");
                                 String pickup_decendent_time=jsonObject1.getString("pickup_decendent_time");
                                 String drop_decendant_time=jsonObject1.getString("drop_decendant_time");

                                 if(request_accepted_titme!="null"){
                                     if(reached_on_decendant_pickup_location_time!="null"){
                                         if(pickup_decendent_time!="null"){
                                             if(drop_decendant_time!="null"){
                                                 fragmtStatusTag = fragmentHome.getClass().getName();
                                                 GlobalValues.getInstance().setFramgentTag(fragmtStatusTag);
                                                 CommonUtils.loadFragment(MainActivity.this, fragmentHome, fragmtStatusTag);

                                             }else {
                                                 Fragment fragment = PickupDriverFragment.newInstance("descendant1");
                                                 String tagfragment = fragment.getClass().getName();
                                                 // selectedNavItem(R.id.navigation_home);
                                                 GlobalValues.getInstance().setFramgentTag(tagfragment);
                                                 CommonUtils.loadFragment(MainActivity.this, fragment, tagfragment);

                                             }

                                         }else {
                                             Fragment fragment = PickupDriverFragment.newInstance("descendant");
                                             String tagfragment = fragment.getClass().getName();
                                             // selectedNavItem(R.id.navigation_home);
                                             GlobalValues.getInstance().setFramgentTag(tagfragment);
                                             CommonUtils.loadFragment(MainActivity.this, fragment, tagfragment);

                                         }

                                     }else {
                                         Fragment fragment = OrderPickedFragment.newInstance();
                                         String tagfragment = fragment.getClass().getName();
                                         GlobalValues.getInstance().setFramgentTag(tagfragment);
                                         CommonUtils.loadFragment(MainActivity.this, fragment, tagfragment);

                                     }
                                 }else {
                                     fragmtStatusTag = fragmentHome.getClass().getName();
                                     GlobalValues.getInstance().setFramgentTag(fragmtStatusTag);
                                     CommonUtils.loadFragment(MainActivity.this, fragmentHome, fragmtStatusTag);

                                 }
                             }
                             else if(service_id.equals("4")){
                                 String request_accepted_titme=jsonObject1.getString("request_accepted_titme");
                                 String reached_on_decendant_pickup_location_time=jsonObject1.getString("reached_on_decendant_pickup_location_time");
                                 String pickup_decendent_time=jsonObject1.getString("pickup_decendent_time");
                                 String drop_decendant_time=jsonObject1.getString("drop_decendant_time");

                                 if(request_accepted_titme!="null"){
                                     if(reached_on_decendant_pickup_location_time!="null"){
                                         if(pickup_decendent_time!="null"){
                                             if(drop_decendant_time!="null"){
                                                 fragmtStatusTag = fragmentHome.getClass().getName();
                                                 GlobalValues.getInstance().setFramgentTag(fragmtStatusTag);
                                                 CommonUtils.loadFragment(MainActivity.this, fragmentHome, fragmtStatusTag);

                                             }else {
                                                 Fragment fragment = PickupDriverFragment.newInstance("descendant1");
                                                 String tagfragment = fragment.getClass().getName();
                                                 // selectedNavItem(R.id.navigation_home);
                                                 GlobalValues.getInstance().setFramgentTag(tagfragment);
                                                 CommonUtils.loadFragment(MainActivity.this, fragment, tagfragment);

                                             }

                                         }else {
                                             Fragment fragment = PickupDriverFragment.newInstance("descendant");
                                             String tagfragment = fragment.getClass().getName();
                                             // selectedNavItem(R.id.navigation_home);
                                             GlobalValues.getInstance().setFramgentTag(tagfragment);
                                             CommonUtils.loadFragment(MainActivity.this, fragment, tagfragment);

                                         }

                                     }else {
                                         Fragment fragment = OrderPickedFragment.newInstance();
                                         String tagfragment = fragment.getClass().getName();
                                         GlobalValues.getInstance().setFramgentTag(tagfragment);
                                         CommonUtils.loadFragment(MainActivity.this, fragment, tagfragment);

                                     }
                                 }else {
                                     fragmtStatusTag = fragmentHome.getClass().getName();
                                     GlobalValues.getInstance().setFramgentTag(fragmtStatusTag);
                                     CommonUtils.loadFragment(MainActivity.this, fragmentHome, fragmtStatusTag);

                                 }


                             }
                             else if(service_id.equals("5")){
                                 String removal_specialists_assign_time=jsonObject1.getString("removal_specialists_assign_time");
                                 String cab_driver_assign_time=jsonObject1.getString("cab_driver_assign_time");
                                 String reached_on_rs_location_time=jsonObject1.getString("reached_on_rs_location_time");
                                 String pickup_rs_time=jsonObject1.getString("pickup_rs_time");
                                 String reached_on_decendant_pickup_location_time=jsonObject1.getString("reached_on_decendant_pickup_location_time");
                                 String pickup_decendent_time=jsonObject1.getString("pickup_decendent_time");
                                 String drop_decendant_time=jsonObject1.getString("drop_decendant_time");

                                 if(cab_driver_assign_time!="null"){
                                     if(reached_on_rs_location_time!="null"){
                                         if(pickup_rs_time!="null"){
                                             if(reached_on_decendant_pickup_location_time!="null"){
                                                 if(pickup_decendent_time!="null"){
                                                     if(drop_decendant_time!="null"){
                                                         fragmtStatusTag = fragmentHome.getClass().getName();
                                                         GlobalValues.getInstance().setFramgentTag(fragmtStatusTag);
                                                         CommonUtils.loadFragment(MainActivity.this, fragmentHome, fragmtStatusTag);

                                                     }else {
                                                         Fragment fragment = PickupDriverFragment.newInstance("descendant2");
                                                         String tagfragment = fragment.getClass().getName();
                                                         // selectedNavItem(R.id.navigation_home);
                                                         GlobalValues.getInstance().setFramgentTag(tagfragment);
                                                         CommonUtils.loadFragment(MainActivity.this, fragment, tagfragment);
                                                     }
                                                 }else {
                                                     Fragment fragment = PickupDriverFragment.newInstance("descendant1");
                                                     String tagfragment = fragment.getClass().getName();
                                                     // selectedNavItem(R.id.navigation_home);
                                                     GlobalValues.getInstance().setFramgentTag(tagfragment);
                                                     CommonUtils.loadFragment(MainActivity.this, fragment, tagfragment);
                                                 }
                                             }else {
                                                 Fragment fragment = PickupDriverFragment.newInstance("descendant");
                                                 String tagfragment = fragment.getClass().getName();
                                                 // selectedNavItem(R.id.navigation_home);
                                                 GlobalValues.getInstance().setFramgentTag(tagfragment);
                                                 CommonUtils.loadFragment(MainActivity.this, fragment, tagfragment);

                                             }
                                         }else {
                                             Fragment fragment = PickupDriverFragment.newInstance("removal");
                                             String tagfragment = fragment.getClass().getName();
                                             // selectedNavItem(R.id.navigation_home);
                                             GlobalValues.getInstance().setFramgentTag(tagfragment);
                                             CommonUtils.loadFragment(MainActivity.this, fragment, tagfragment);

                                         }
                                     }else {

                                         Fragment fragment = OrderPickedFragment.newInstance();
                                         String tagfragment = fragment.getClass().getName();
                                         GlobalValues.getInstance().setFramgentTag(tagfragment);
                                         CommonUtils.loadFragment(MainActivity.this, fragment, tagfragment);
                                     }
                                 }else {
                                     fragmtStatusTag = fragmentHome.getClass().getName();
                                     GlobalValues.getInstance().setFramgentTag(fragmtStatusTag);
                                     CommonUtils.loadFragment(MainActivity.this, fragmentHome, fragmtStatusTag);

                                 }

                             }
                             else if(service_id.equals("6")){
                                 String request_accepted_titme=jsonObject1.getString("request_accepted_titme");
                                 String reached_on_decendant_pickup_location_time=jsonObject1.getString("reached_on_decendant_pickup_location_time");
                                 String pickup_decendent_time=jsonObject1.getString("pickup_decendent_time");
                                 String drop_decendant_time=jsonObject1.getString("drop_decendant_time");
                                 if(request_accepted_titme!="null"){
                                     if(reached_on_decendant_pickup_location_time!="null"){
                                         if(pickup_decendent_time!="null"){
                                             if(drop_decendant_time!="null"){
                                                 fragmtStatusTag = fragmentHome.getClass().getName();
                                                 GlobalValues.getInstance().setFramgentTag(fragmtStatusTag);
                                                 CommonUtils.loadFragment(MainActivity.this, fragmentHome, fragmtStatusTag);

                                             }else {
                                                 Fragment fragment = PickupDriverFragment.newInstance("descendant1");
                                                 String tagfragment = fragment.getClass().getName();
                                                 // selectedNavItem(R.id.navigation_home);
                                                 GlobalValues.getInstance().setFramgentTag(tagfragment);
                                                 CommonUtils.loadFragment(MainActivity.this, fragment, tagfragment);

                                             }

                                         }else {
                                             Fragment fragment = PickupDriverFragment.newInstance("descendant");
                                             String tagfragment = fragment.getClass().getName();
                                             // selectedNavItem(R.id.navigation_home);
                                             GlobalValues.getInstance().setFramgentTag(tagfragment);
                                             CommonUtils.loadFragment(MainActivity.this, fragment, tagfragment);

                                         }

                                     }else {
                                         Fragment fragment = OrderPickedFragment.newInstance();
                                         String tagfragment = fragment.getClass().getName();
                                         GlobalValues.getInstance().setFramgentTag(tagfragment);
                                         CommonUtils.loadFragment(MainActivity.this, fragment, tagfragment);

                                     }
                                 }else {
                                     fragmtStatusTag = fragmentHome.getClass().getName();
                                     GlobalValues.getInstance().setFramgentTag(fragmtStatusTag);
                                     CommonUtils.loadFragment(MainActivity.this, fragmentHome, fragmtStatusTag);

                                 }


                             }
                             else if(service_id.equals("7")){
                                 String removal_specialists_assign_time=jsonObject1.getString("removal_specialists_assign_time");
                                 String cab_driver_assign_time=jsonObject1.getString("cab_driver_assign_time");
                                 String reached_on_rs_location_time=jsonObject1.getString("reached_on_rs_location_time");
                                 String pickup_rs_time=jsonObject1.getString("pickup_rs_time");
                                 String reached_on_decendant_pickup_location_time=jsonObject1.getString("reached_on_decendant_pickup_location_time");
                                 String pickup_decendent_time=jsonObject1.getString("pickup_decendent_time");
                                 String drop_decendant_time=jsonObject1.getString("drop_decendant_time");
                                 if(cab_driver_assign_time!="null"){
                                     if(reached_on_rs_location_time!="null"){
                                         if(pickup_rs_time!="null"){
                                             if(reached_on_decendant_pickup_location_time!="null"){
                                                 if(pickup_decendent_time!="null"){
                                                     if(drop_decendant_time!="null"){
                                                         fragmtStatusTag = fragmentHome.getClass().getName();
                                                         GlobalValues.getInstance().setFramgentTag(fragmtStatusTag);
                                                         CommonUtils.loadFragment(MainActivity.this, fragmentHome, fragmtStatusTag);

                                                     }else {
                                                         Fragment fragment = PickupDriverFragment.newInstance("descendant2");
                                                         String tagfragment = fragment.getClass().getName();
                                                         // selectedNavItem(R.id.navigation_home);
                                                         GlobalValues.getInstance().setFramgentTag(tagfragment);
                                                         CommonUtils.loadFragment(MainActivity.this, fragment, tagfragment);
                                                     }
                                                 }else {
                                                     Fragment fragment = PickupDriverFragment.newInstance("descendant1");
                                                     String tagfragment = fragment.getClass().getName();
                                                     // selectedNavItem(R.id.navigation_home);
                                                     GlobalValues.getInstance().setFramgentTag(tagfragment);
                                                     CommonUtils.loadFragment(MainActivity.this, fragment, tagfragment);
                                                 }
                                             }else {
                                                 Fragment fragment = PickupDriverFragment.newInstance("descendant");
                                                 String tagfragment = fragment.getClass().getName();
                                                 // selectedNavItem(R.id.navigation_home);
                                                 GlobalValues.getInstance().setFramgentTag(tagfragment);
                                                 CommonUtils.loadFragment(MainActivity.this, fragment, tagfragment);

                                             }
                                         }else {
                                             Fragment fragment = PickupDriverFragment.newInstance("removal");
                                             String tagfragment = fragment.getClass().getName();
                                             // selectedNavItem(R.id.navigation_home);
                                             GlobalValues.getInstance().setFramgentTag(tagfragment);
                                             CommonUtils.loadFragment(MainActivity.this, fragment, tagfragment);

                                         }
                                     }else {
                                         Fragment fragment = OrderPickedFragment.newInstance();
                                         String tagfragment = fragment.getClass().getName();
                                         GlobalValues.getInstance().setFramgentTag(tagfragment);
                                         CommonUtils.loadFragment(MainActivity.this, fragment, tagfragment);
                                     }
                                 }else {
                                     fragmtStatusTag = fragmentHome.getClass().getName();
                                     GlobalValues.getInstance().setFramgentTag(fragmtStatusTag);
                                     CommonUtils.loadFragment(MainActivity.this, fragmentHome, fragmtStatusTag);

                                 }

                             }
                             else if(service_id.equals("8")){

                                 String removal_specialists_assign_time=jsonObject1.getString("removal_specialists_assign_time");
                                 String cab_driver_assign_time=jsonObject1.getString("cab_driver_assign_time");
                                 String reached_on_rs_location_time=jsonObject1.getString("reached_on_rs_location_time");
                                 String pickup_rs_time=jsonObject1.getString("pickup_rs_time");
                                 String reached_on_decendant_pickup_location_time=jsonObject1.getString("reached_on_decendant_pickup_location_time");
                                 String pickup_decendent_time=jsonObject1.getString("pickup_decendent_time");
                                 String drop_airport_time=jsonObject1.getString("drop_decendant_time");
                                 if(cab_driver_assign_time!="null"){
                                     if(reached_on_rs_location_time!="null"){
                                         if(pickup_rs_time!="null"){
                                             if(reached_on_decendant_pickup_location_time!="null"){
                                                 if(pickup_decendent_time!="null"){
                                                     if(drop_airport_time!="null"){
                                                        // Toast.makeText(getApplicationContext(), "hlo", Toast.LENGTH_SHORT).show();

                                                         fragmtStatusTag = fragmentHome.getClass().getName();
                                                         GlobalValues.getInstance().setFramgentTag(fragmtStatusTag);
                                                         CommonUtils.loadFragment(MainActivity.this, fragmentHome, fragmtStatusTag);

                                                     }else {
                                                         Fragment fragment = PickupDriverFragment.newInstance("descendant2");
                                                         String tagfragment = fragment.getClass().getName();
                                                         // selectedNavItem(R.id.navigation_home);
                                                         GlobalValues.getInstance().setFramgentTag(tagfragment);
                                                         CommonUtils.loadFragment(MainActivity.this, fragment, tagfragment);
                                                     }
                                                 }else {
                                                    // Toast.makeText(getApplicationContext(), "hlo", Toast.LENGTH_SHORT).show();
                                                     Fragment fragment = PickupDriverFragment.newInstance("descendant1");
                                                     String tagfragment = fragment.getClass().getName();
                                                     // selectedNavItem(R.id.navigation_home);
                                                     GlobalValues.getInstance().setFramgentTag(tagfragment);
                                                     CommonUtils.loadFragment(MainActivity.this, fragment, tagfragment);
                                                 }
                                             }else {
                                                 Fragment fragment = PickupDriverFragment.newInstance("descendant");
                                                 String tagfragment = fragment.getClass().getName();
                                                 // selectedNavItem(R.id.navigation_home);
                                                 GlobalValues.getInstance().setFramgentTag(tagfragment);
                                                 CommonUtils.loadFragment(MainActivity.this, fragment, tagfragment);

                                             }
                                         }else {
                                             Fragment fragment = PickupDriverFragment.newInstance("removal");
                                             String tagfragment = fragment.getClass().getName();
                                             // selectedNavItem(R.id.navigation_home);
                                             GlobalValues.getInstance().setFramgentTag(tagfragment);
                                             CommonUtils.loadFragment(MainActivity.this, fragment, tagfragment);

                                         }
                                     }else {

                                         Fragment fragment = OrderPickedFragment.newInstance();
                                         String tagfragment = fragment.getClass().getName();
                                         GlobalValues.getInstance().setFramgentTag(tagfragment);
                                         CommonUtils.loadFragment(MainActivity.this, fragment, tagfragment);
                                     }
                                 }else {

                                     fragmtStatusTag = fragmentHome.getClass().getName();
                                     GlobalValues.getInstance().setFramgentTag(fragmtStatusTag);
                                     CommonUtils.loadFragment(MainActivity.this, fragmentHome, fragmtStatusTag);

                                 }


                                /* String removal_assign_time_airpoert=jsonObject1.getString("removal_assign_time_airpoert");
                                 String cab_driver_assign_time_airpoert=jsonObject1.getString("cab_driver_assign_time_airpoert");
                                 String reached_on_rs_location_time_airport=jsonObject1.getString("reached_on_rs_location_time_airport");
                                 String pickup_rs_time_airport=jsonObject1.getString("pickup_rs_time_airport");
                                 String reached_on_airport_pickup_location_time=jsonObject1.getString("reached_on_airport_pickup_location_time");
                                 String pickup_decendent_time_airport=jsonObject1.getString("pickup_decendent_time_airport");
                                 String drop_decendant_time=jsonObject1.getString("drop_decendant_time");
*/
                             }
                             else if(service_id.equals("9")){

                                 if(vehicle_type.equals("6")){
                                     // boat driver
                                     String cab_driver_assign_time_boat=jsonObject1.getString("cab_driver_assign_time");
                                     String reached_on_decendant_pickup_location_time_boat=jsonObject1.getString("reached_on_decendant_pickup_location_time");
                                     String pickup_decendent_time_boat=jsonObject1.getString("pickup_decendent_time");
                                     String burial_time_insea=jsonObject1.getString("burial_time_insea");
                                     String drop_decendant_time_boat=jsonObject1.getString("drop_decendant_time");
                                     if(cab_driver_assign_time_boat!="null"){
                                         if(reached_on_decendant_pickup_location_time_boat!="null"){
                                             if(pickup_decendent_time_boat!="null"){
                                                 if(burial_time_insea!="null"){
                                                     if(drop_decendant_time_boat!="null"){
                                                         fragmtStatusTag = fragmentHome.getClass().getName();
                                                         GlobalValues.getInstance().setFramgentTag(fragmtStatusTag);
                                                         CommonUtils.loadFragment(MainActivity.this, fragmentHome, fragmtStatusTag);

                                                     }else {
                                                         Fragment fragment = PickupDriverFragment.newInstance("boat2");
                                                         String tagfragment = fragment.getClass().getName();
                                                          //selectedNavItem(R.id.navigation_home);
                                                         GlobalValues.getInstance().setFramgentTag(tagfragment);
                                                         CommonUtils.loadFragment(MainActivity.this, fragment, tagfragment);

                                                     }

                                                 }else {
                                                     Fragment fragment = PickupDriverFragment.newInstance("boat1");
                                                     String tagfragment = fragment.getClass().getName();
                                                     // selectedNavItem(R.id.navigation_home);
                                                     GlobalValues.getInstance().setFramgentTag(tagfragment);
                                                     CommonUtils.loadFragment(MainActivity.this, fragment, tagfragment);

                                                 }

                                             }else {
                                                 Fragment fragment = PickupDriverFragment.newInstance("boat0");
                                                 String tagfragment = fragment.getClass().getName();
                                                 // selectedNavItem(R.id.navigation_home);
                                                 GlobalValues.getInstance().setFramgentTag(tagfragment);
                                                 CommonUtils.loadFragment(MainActivity.this, fragment, tagfragment);

                                             }

                                         }else {
                                             Fragment fragment = PickupDriverFragment.newInstance("boat");
                                             String tagfragment = fragment.getClass().getName();
                                             // selectedNavItem(R.id.navigation_home);
                                             GlobalValues.getInstance().setFramgentTag(tagfragment);
                                             CommonUtils.loadFragment(MainActivity.this, fragment, tagfragment);

                                         }
                                     }else {
                                         fragmtStatusTag = fragmentHome.getClass().getName();
                                         GlobalValues.getInstance().setFramgentTag(fragmtStatusTag);
                                         CommonUtils.loadFragment(MainActivity.this, fragmentHome, fragmtStatusTag);

                                     }

                                 }else {
                                     String removal_specialists_assign_time=jsonObject1.getString("removal_specialists_assign_time");
                                     String cab_driver_assign_time=jsonObject1.getString("cab_driver_assign_time");
                                     String reached_on_rs_location_time=jsonObject1.getString("reached_on_rs_location_time");
                                     String pickup_rs_time=jsonObject1.getString("pickup_rs_time");
                                     String reached_on_decendant_pickup_location_time=jsonObject1.getString("reached_on_decendant_pickup_location_time");
                                     String pickup_decendent_time=jsonObject1.getString("pickup_decendent_time");
                                     String drop_decendant_time=jsonObject1.getString("drop_decendant_time");
                                     if(cab_driver_assign_time!="null"){
                                         if(reached_on_rs_location_time!="null"){
                                             if(pickup_rs_time!="null"){
                                                 if(reached_on_decendant_pickup_location_time!="null"){
                                                     if(pickup_decendent_time!="null"){
                                                         if(drop_decendant_time!="null"){
                                                             fragmtStatusTag = fragmentHome.getClass().getName();
                                                             GlobalValues.getInstance().setFramgentTag(fragmtStatusTag);
                                                             CommonUtils.loadFragment(MainActivity.this, fragmentHome, fragmtStatusTag);

                                                         }else {
                                                             Fragment fragment = PickupDriverFragment.newInstance("descendant2");
                                                             String tagfragment = fragment.getClass().getName();
                                                             // selectedNavItem(R.id.navigation_home);
                                                             GlobalValues.getInstance().setFramgentTag(tagfragment);
                                                             CommonUtils.loadFragment(MainActivity.this, fragment, tagfragment);
                                                         }
                                                     }else {
                                                         Fragment fragment = PickupDriverFragment.newInstance("descendant1");
                                                         String tagfragment = fragment.getClass().getName();
                                                         // selectedNavItem(R.id.navigation_home);
                                                         GlobalValues.getInstance().setFramgentTag(tagfragment);
                                                         CommonUtils.loadFragment(MainActivity.this, fragment, tagfragment);
                                                     }
                                                 }else {
                                                     Fragment fragment = PickupDriverFragment.newInstance("descendant");
                                                     String tagfragment = fragment.getClass().getName();
                                                     // selectedNavItem(R.id.navigation_home);
                                                     GlobalValues.getInstance().setFramgentTag(tagfragment);
                                                     CommonUtils.loadFragment(MainActivity.this, fragment, tagfragment);

                                                 }
                                             }else {
                                                 Fragment fragment = PickupDriverFragment.newInstance("removal");
                                                 String tagfragment = fragment.getClass().getName();
                                                 // selectedNavItem(R.id.navigation_home);
                                                 GlobalValues.getInstance().setFramgentTag(tagfragment);
                                                 CommonUtils.loadFragment(MainActivity.this, fragment, tagfragment);

                                             }
                                         }else {
                                             Fragment fragment = OrderPickedFragment.newInstance();
                                             String tagfragment = fragment.getClass().getName();
                                             GlobalValues.getInstance().setFramgentTag(tagfragment);
                                             CommonUtils.loadFragment(MainActivity.this, fragment, tagfragment);
                                         }
                                     }else {
                                         fragmtStatusTag = fragmentHome.getClass().getName();
                                         GlobalValues.getInstance().setFramgentTag(fragmtStatusTag);
                                         CommonUtils.loadFragment(MainActivity.this, fragmentHome, fragmtStatusTag);

                                     }
                                 }


                             }
                         }else {
                             Toast.makeText(getApplicationContext(), "false", Toast.LENGTH_LONG).show();

                         }
                    } else {
                        BufferedReader reader = null;
                        StringBuilder sb = new StringBuilder();
                        try {
                            reader = new BufferedReader(new InputStreamReader(response.errorBody().byteStream()));
                            String line;
                            try {
                                while ((line = reader.readLine()) != null) {
                                    sb.append(line);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            String finallyError = sb.toString();
                            JSONObject jsonObjectError = new JSONObject(finallyError);
                            String message = jsonObjectError.optString("message");
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                            if (jsonObjectError.optString("token_valid").equalsIgnoreCase("false")) {
                                CommonUtils.logoutSession(MainActivity.this);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
               // Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();


            }
        });
    }

    void getVehicleDetailsApi() {

       /* final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Loading.....");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(true);
        progressDialog.show();*/


        ApiInterface apiInterface = ApiClients.getClient(getApplicationContext()).create(ApiInterface.class);
        Call<VehicleDetailsResponseModel> call = null;

        call = apiInterface.vehicleDetailList(loginResponse.getData().getToken(),userId);

        call.enqueue(new Callback<VehicleDetailsResponseModel>() {
            @Override
            public void onResponse(Call<VehicleDetailsResponseModel> call, Response<VehicleDetailsResponseModel> response) {
                try {
                    //progressDialog.dismiss();
                    if (response.code() >= 200 && response.code() <= 210) {
                        vehicle_details="true";
                        //Toast.makeText(getApplicationContext(), "true", Toast.LENGTH_SHORT).show();

                        String success=response.body().getSuccess().toString();
                        vehicle_type =response.body().getData().getVehicleTypeId().toString();

                    }else if(response.code() >= 400){
                        vehicle_details="false";
                       // Toast.makeText(getApplicationContext(), "false", Toast.LENGTH_SHORT).show();



                    } else {




                        //GlobalValues.getPreferenceManager().setloginPref(false);
                        BufferedReader reader = null;
                        StringBuilder sb = new StringBuilder();
                        try {
                            reader = new BufferedReader(new InputStreamReader(response.errorBody().byteStream()));
                            String line;
                            try {
                                while ((line = reader.readLine()) != null) {
                                    sb.append(line);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            String finallyError = sb.toString();
                            JSONObject jsonObjectError = new JSONObject(finallyError);
                            String message = jsonObjectError.optString("message");
                            JSONObject jsonArray = jsonObjectError.getJSONObject("data");
                            String error =jsonArray.optString("error");
                            Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    //progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<VehicleDetailsResponseModel> call, Throwable t) {
                //progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();


            }
        });

    }
    public void showAlert(){
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.add_vehicel_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));



        LinearLayout bt_ok = dialog.findViewById(R.id.btn_add);
        bt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment;
                fragment = new VehicleDetailsFragment();
                String fragmtStatusTag = fragment.getClass().getName();
                GlobalValues.getInstance().setFramgentTag(fragmtStatusTag);
                ((MainActivity) MainActivity.this).selectedNavItem(R.id.navigation_home);
                CommonUtils.loadFragment(MainActivity.this, fragment, fragmtStatusTag);
                dialog.setCancelable(true);
                dialog.dismiss();

            }
        });

        dialog.show();

    }
    void currentRideApi() {

        final LoadingProgressDialog dialog = new LoadingProgressDialog(this, "please Wait!");
        dialog.setCancelable(false);
        dialog.show();
        ApiInterface apiInterface = ApiClients.getConnection(this);
        Call<JsonObject> call = apiInterface.currentRideApi(loginResponse.getData().getToken(), userId);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                try {
                    dialog.dismiss();
                    if (response.code() >= 200 && response.code() <= 210) {
                        JSONObject object = new JSONObject(response.body().toString());
                       // Toast.makeText(MainActivity.this, object.optString("message"), Toast.LENGTH_SHORT).show();
                        if (object.optString("success").equalsIgnoreCase("true")) {
                            if (object.getJSONObject("data") != null) {
                                GlobalValues.getInstance().setRequestStatus(object.getJSONObject("data").optString("status"));
                                GlobalValues.getPreferenceManager().setString(PreferenceManager.REQUESTID, object.getJSONObject("data").optString("request_id"));

                            }
                            /*Fragment fragment = HomeFragment.newInstance();
                            String tagfragment = fragment.getClass().getName();
                            GlobalValues.getInstance().setFramgentTag(tagfragment);
                            CommonUtils.loadFragment(MainActivity.this, fragment, tagfragment);*/
                        }
                    } else {
                        BufferedReader reader = null;
                        StringBuilder sb = new StringBuilder();
                        try {
                            reader = new BufferedReader(new InputStreamReader(response.errorBody().byteStream()));
                            String line;
                            try {
                                while ((line = reader.readLine()) != null) {
                                    sb.append(line);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            String finallyError = sb.toString();
                            JSONObject jsonObjectError = new JSONObject(finallyError);
                            String message = jsonObjectError.optString("message");
                            //  Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
                            if (jsonObjectError.optString("token_valid").equalsIgnoreCase("false")) {
                                CommonUtils.logoutSession(MainActivity.this);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                dialog.dismiss();
            }
        });
    }

}
