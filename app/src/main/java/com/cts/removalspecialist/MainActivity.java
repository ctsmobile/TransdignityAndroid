package com.cts.removalspecialist;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;

import com.cts.removalspecialist.fcm.FirebaseHelper;
import com.cts.removalspecialist.models.auth.LoginResponse;
import com.cts.removalspecialist.network.ApiClients;
import com.cts.removalspecialist.network.ApiInterface;
import com.cts.removalspecialist.utilities.GpsUtils;
import com.cts.removalspecialist.utilities.LoadingProgressDialog;
import com.cts.removalspecialist.utilities.MyConstants;
import com.cts.removalspecialist.utilities.PreferenceManager;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.cts.removalspecialist.utilities.CommonUtils;
import com.cts.removalspecialist.utilities.GlobalValues;
import com.cts.removalspecialist.fragments.ContactUsFragment;
import com.cts.removalspecialist.fragments.HomeFragment;
import com.cts.removalspecialist.fragments.PaymentFragment;
import com.cts.removalspecialist.fragments.SettingsFragment;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class MainActivity extends AppCompatActivity {
    boolean doubleBackToExitPressedOnce = false;
    String fragmtStatusTag = "";
    Fragment fragment;
    public BottomNavigationView navView;

    private FusedLocationProviderClient mFusedLocationClient;
    private double latitude = 0.0, longitude = 0.0;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    FirebaseHelper firebaseHelper;
    private StringBuilder stringBuilder;
    private boolean isGPS = false, isFatchLatong = true;
    LoginResponse loginResponse;
    String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navView.setItemIconTintList(null);

        loginResponse = (LoginResponse) GlobalValues.getPreferenceManager().getObject(PreferenceManager.LoginResponseKey, LoginResponse.class);
        if (loginResponse != null) {
            userId = loginResponse.getData().getId();
            firebaseHelper = new FirebaseHelper(userId);
        }

        fragment = new HomeFragment();
        fragmtStatusTag = fragment.getClass().getName();
        GlobalValues.getInstance().setFramgentTag(fragmtStatusTag);
        CommonUtils.loadFragment(this, fragment, fragmtStatusTag);
        isFatchLatong = true;
        createLocationCallback();
        createLocationUpdate();


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

                    return true;
                case R.id.navigation_payment:
                    fragment = new PaymentFragment();
                    fragmtStatusTag = fragment.getClass().getName();
                    GlobalValues.getInstance().setFramgentTag(fragmtStatusTag);
                    CommonUtils.loadFragment(MainActivity.this, fragment, fragmtStatusTag);

                    return true;
                case R.id.navigation_contactus:
                    fragment = new ContactUsFragment();
                    fragmtStatusTag = fragment.getClass().getName();
                    GlobalValues.getInstance().setFramgentTag(fragmtStatusTag);
                    CommonUtils.loadFragment(MainActivity.this, fragment, fragmtStatusTag);

                    return true;
                case R.id.navigaton_settings:
                    fragment = new SettingsFragment();
                    fragmtStatusTag = fragment.getClass().getName();
                    GlobalValues.getInstance().setFramgentTag(fragmtStatusTag);
                    CommonUtils.loadFragment(MainActivity.this, fragment, fragmtStatusTag);

                    return true;
            }
            return false;
        }
    };

    void createLocationUpdate() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(45 * 1000); // 10 seconds
        // locationRequest.setFastestInterval(3000); // 5 seconds

        new GpsUtils(this).turnGPSOn(new GpsUtils.onGpsListener() {
            @Override
            public void gpsStatus(boolean isGPSEnable) {
                // turn on GPS
                isGPS = isGPSEnable;
            }
        });
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    MyConstants.LOCATION_REQUEST);

        } else {
            //  if (isFatchLatong)
            mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
        }
    }

    private void createLocationCallback() {
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                if (locationResult == null) {
                    return;
                }
                String latlong = locationResult.getLastLocation().getLatitude() + ", " + locationResult.getLastLocation().getLongitude();
                Log.e("MainActivity", "onLocationResult: latlong>>>>> " + latlong);
                // Toast.makeText(MainActivity.this,""+latlong,Toast.LENGTH_LONG).show();
                if (GlobalValues.getPreferenceManager().getBoolean(PreferenceManager.ISONLINE)) {
                    // firebaseHelper.updateDriver(new LatLongModel(latlong, userId));
                    updateLatLongApi(String.valueOf(locationResult.getLastLocation().getLatitude()), String.valueOf(locationResult.getLastLocation().getLongitude()));
                }
            }
        };
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1000: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    createLocationCallback();
                       /* mFusedLocationClient.getLastLocation().addOnSuccessListener(MainActivity.this, location -> {
                            if (location != null) {
                               Double wayLatitude = location.getLatitude();
                               Double wayLongitude = location.getLongitude();
                               .setText(String.format(Locale.US, "%s - %s", wayLatitude, wayLongitude));
                            } else {
                                mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
                            }
                        });*/

                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == MyConstants.GPS_REQUEST) {
                isGPS = true; // flag maintain before get location
            }
        }
    }

    void updateLatLongApi(String lat, String lng) {


        ApiInterface apiInterface = ApiClients.getConnection(this);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("lat", lat);
        jsonObject.addProperty("lng", lng);
        Call<JsonObject> call = apiInterface.updateLatlongApi(loginResponse.getData().getToken(), userId, jsonObject);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                try {
                    if (response.code() >= 200 && response.code() <= 210) {
                        JSONObject object = new JSONObject(response.body().toString());
                        Log.e(TAG, "onResponse:Latlong Update>>  " + object.optString("success"));
                       /* if (object.optString("success").equalsIgnoreCase("true")) {

                        }*/
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
                                //CommonUtils.logoutSession(MainActivity.this);
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
                Log.e(TAG, "onResponse: " + t.getMessage());
            }
        });
    }


   /* private fun createLocationCallback() {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                super.onLocationResult(locationResult)
                if (locationResult!!.lastLocation == null) return
                        val latLng = LatLng(locationResult.lastLocation.latitude, locationResult.lastLocation.longitude)
                Log.e("Location", latLng.latitude.toString() + " , " + latLng.longitude)
                if (locationFlag) {
                    locationFlag = false
                    //animateCamera(latLng)
                }
                if (driverOnlineFlag) firebaseHelper.updateDriver(LatLongModel(lat = latLng.latitude, lng = latLng.longitude))
                showOrAnimateMarker(latLng)
            }
        }
    }*/

    /*public void loadFragment(Fragment fragment, String tag) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);

        transaction.commit();
    }*/

    @Override
    public void onBackPressed() {
        fragment = new HomeFragment();
        String currentTag = GlobalValues.getInstance().getFramgentTag();
        if (!currentTag.equalsIgnoreCase(fragment.getClass().getName())) {
            fragmtStatusTag = fragment.getClass().getName();
            GlobalValues.getInstance().setFramgentTag(fragmtStatusTag);
            CommonUtils.loadFragment(MainActivity.this, fragment, fragmtStatusTag);

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //isFatchLatong = false;
    }
}
