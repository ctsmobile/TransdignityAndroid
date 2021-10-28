package com.transdignity.driver.fragments;

import android.Manifest;
import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.JsonObject;
import com.transdignity.driver.MainActivity;
import com.transdignity.driver.R;
import com.transdignity.driver.databinding.FragmentHomeBinding;
import com.transdignity.driver.models.auth.LoginResponse;
import com.transdignity.driver.models.requestService.RemovalSplDetialsResponse;
import com.transdignity.driver.models.requestService.DecedentDetailsResponse;
import com.transdignity.driver.network.ApiClients;
import com.transdignity.driver.network.ApiInterface;
import com.transdignity.driver.utilities.CommonUtils;
import com.transdignity.driver.utilities.GlobalValues;
import com.transdignity.driver.utilities.GpsUtils;
import com.transdignity.driver.utilities.LoadingProgressDialog;
import com.transdignity.driver.utilities.MyConstants;
import com.transdignity.driver.utilities.PreferenceManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Locale;
import java.util.Timer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements View.OnClickListener, OnMapReadyCallback {

    Context context;
    Activity activity;
    FragmentHomeBinding binding;
    private GoogleMap mMap;
    boolean mLocationPermissionGranted = false;
    String TAG = "HomeFragment";
    private boolean isGPS = false, isFatchLatong = false;
    private FusedLocationProviderClient mFusedLocationClient;
    private double latitude = 0.0, longitude = 0.0;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    LoginResponse loginResponse;
    String userId;
    Ringtone r;
    MediaPlayer ring;
    Boolean isRingTonePlay = false;
    String latlong;
    String address;
    String strLat,strLong;
    public HomeFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView toolbar = activity.findViewById(R.id.tv_toolbar);
        toolbar.setText("Home");
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        loginResponse = (LoginResponse) GlobalValues.getPreferenceManager().getObject(PreferenceManager.LoginResponseKey, LoginResponse.class);
        if (loginResponse != null) {
            userId = loginResponse.getData().getId();
            String group_id = loginResponse.getData().getUserGroupId();
           // Toast.makeText(context, ""+userId+"\n"+group_id, Toast.LENGTH_SHORT).show();

        }
        binding.currentLocationPicker.setOnClickListener(this);
        currentRideApi();
        // setOnlineOfflineStatus();
        //switchCopact();
        getLocationPermission();
        openGPS();
        createLocationCallback();
        createLocationUpdate();
    }

    @Override
    public void onResume() {
        super.onResume();
        manageNotification();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.current_location_picker:
                //AcceptRejectPopup(activity);
                try {
                    if (mLocationPermissionGranted) {
                        if (isGPS) {
                            //createLocationUpdate();
                            if (GlobalValues.getInstance().getLattitude() != null && GlobalValues.getInstance().getLongitude() != null) {
                                markerDisplayOnMap(GlobalValues.getInstance().getLattitude(), GlobalValues.getInstance().getLongitude());
                            }
                        } else {
                            openGPS();
                        }
                    } else {
                        getLocationPermission();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
           /* case R.id.notification:
                Fragment fragmentnotf = new NotificationFragment();
                String tagFragmentnotf = fragmentnotf.getClass().getName();
                GlobalValues.getInstance().setFramgentTag(tagFragmentnotf);
                CommonUtils.loadFragment(context, fragmentnotf, tagFragmentnotf);
                break;
            case R.id.filter:
                break;*/
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        updateLocationUI();
        createLocationCallback();
        // Add a marker in Sydney, Australia, and move the camera.

    }

    private void markerDisplayOnMap(double lat, double longit) {
        LatLng latLong = new LatLng(lat, longit);
        try {

            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
       /* mMap.addMarker(
                new MarkerOptions()
                        .position(latLong)
                        .title("Marker in Noida")
                        .icon(BitmapDescriptorFactory.fromBitmap(createCustomMarker(context, "5", "MIN")))
                        .zIndex(9)
        );*/
            // mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney);
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLong, 18.0f));
        } catch (Exception e) {

        }
    }

    private void getLocationPermission() {

        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED

        ) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(activity,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.CALL_PHONE, Manifest.permission.ANSWER_PHONE_CALLS, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MyConstants.LOCATION_REQUEST);
            // mLocationPermissionGranted = true;
        }
    }

    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        try {
            if (mLocationPermissionGranted) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                createLocationCallback();
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                // mLastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
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
                latlong = locationResult.getLastLocation().getLatitude() + "," + locationResult.getLastLocation().getLongitude();
                Log.e("MainActivity", "onLocationResult: latlong>>>>> " + latlong);
               // Toast.makeText(context,""+latlong,Toast.LENGTH_LONG).show();

                // address=locationResult.getLocations().toString();
                GlobalValues.getInstance().setLattitude(locationResult.getLastLocation().getLatitude());
                GlobalValues.getInstance().setLongitude(locationResult.getLastLocation().getLongitude());

                try {
                    Geocoder geo = new Geocoder(getActivity().getApplicationContext(), Locale.getDefault());
                    List<Address> addresses = geo.getFromLocation(locationResult.getLastLocation().getLatitude(), locationResult.getLastLocation().getLongitude(), 1);
                    if (addresses.isEmpty()) {
                        // yourtextfieldname.setText("Waiting for Location");
                    }
                    else {
                        if (addresses.size() > 0) {
                            // yourtextfieldname.setText(addresses.get(0).getFeatureName() + ", " + addresses.get(0).getLocality() +", " + addresses.get(0).getAdminArea() + ", " + addresses.get(0).getCountryName());
                            address=addresses.get(0).getFeatureName() + ", " + addresses.get(0).getLocality() +", " + addresses.get(0).getAdminArea() + ", " + addresses.get(0).getCountryName();

                          //  Toast.makeText(getActivity(), "Address:- " + address, Toast.LENGTH_LONG).show();
                        }
                    }
                }
                catch (Exception e) {
                    e.printStackTrace(); // getFromLocation() may sometimes fail
                }



                markerDisplayOnMap(locationResult.getLastLocation().getLatitude(), locationResult.getLastLocation().getLongitude());
                if (GlobalValues.getPreferenceManager().getBoolean(PreferenceManager.ISONLINE)) {
                    //firebaseHelper.updateDriver(new LatLongModel(latlong, userId));
                    updateLatLongApi(String.valueOf(locationResult.getLastLocation().getLatitude()), String.valueOf(locationResult.getLastLocation().getLongitude()));
                }
            }
        };
    }

    void createLocationUpdate() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5 * 1000); // 10 seconds
        //locationRequest.setFastestInterval(5000); // 5 seconds
        try {
            if (mLocationPermissionGranted) {
                if (!isGPS) {
                    openGPS();
                }
            } else {
                getLocationPermission();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (mLocationPermissionGranted) {
            mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());

            /*if (GlobalValues.getPreferenceManager().getBoolean(PreferenceManager.ISONLINE))
                mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());*/
        } else {
            getLocationPermission();
        }


    }

    private void openGPS() {
        new GpsUtils(context).turnGPSOn(new GpsUtils.onGpsListener() {
            @Override
            public void gpsStatus(boolean isGPSEnable) {
                // turn on GPS
                isGPS = isGPSEnable;
                createLocationCallback();
            }
        });
    }

    /*void switchCopact() {
        binding.driverStatusSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean check) {
                if (check) {
                    updateOnlineStatus("1");
                } else {
                    updateOnlineStatus("0");
                }
            }
        });
    }*/

   /* void updateOnlineStatus(String status) {
        final LoadingProgressDialog dialog = new LoadingProgressDialog(context, "please Wait!");
        dialog.setCancelable(false);
        dialog.show();
        ApiInterface apiInterface = ApiClients.getConnection(context);
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
    }*/

    void manageNotification() {
        MyConstants.isMainActivityPageOpen = true;
        if (MyConstants.isNewTask) {
            String requestId = GlobalValues.getPreferenceManager().getString(PreferenceManager.REQUESTID);
            String pickuplocation = GlobalValues.getPreferenceManager().getString(PreferenceManager.PICKUPLOCATION);
            if(pickuplocation.equals("")||pickuplocation.equals(null)){

            }
            try {

                if (requestId != null) {
                    MyConstants.isNewTask = false;
                    AcceptRejectPopup(activity, requestId, pickuplocation);
                    clearNotifications(getActivity());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    void updateLatLongApi(String lat, String lng) {
        ApiInterface apiInterface = ApiClients.getConnection(context);
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
                        Log.e(TAG, "onResponse: loatlogUpdate Api" + object.optString("success"));
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
                            if (jsonObjectError.optString("token_valid").equalsIgnoreCase("false")) {
                                CommonUtils.logoutSession(activity);
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

    @Override
    public void onStop() {
        super.onStop();
        //  MyConstants.isMainActivityPageOpen = false;
    }

    private void clearNotifications(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

    void acceptRejectRequestApi(String userId, final String status, final String requestId) {






        final LoadingProgressDialog dialog = new LoadingProgressDialog(context, "please Wait!");
        dialog.setCancelable(false);
        dialog.show();
        ApiInterface apiInterface = ApiClients.getConnection(context);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("user_id", userId);
        jsonObject.addProperty("status", status);
        jsonObject.addProperty("request_id", requestId);
        jsonObject.addProperty("current_location", address);
        jsonObject.addProperty("current_latlng", latlong);
        Call<RemovalSplDetialsResponse> call = apiInterface.acceptRejectApi(loginResponse.getData().getToken(), jsonObject);
        call.enqueue(new Callback<RemovalSplDetialsResponse>() {
            @Override
            public void onResponse(Call<RemovalSplDetialsResponse> call, Response<RemovalSplDetialsResponse> response) {
                dialog.dismiss();
                try {
                    if (response.code() >= 200 && response.code() <= 210) {
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        if (status.equalsIgnoreCase(MyConstants.ACCEPT)) {
                            GlobalValues.getInstance().setRequestStatus(MyConstants.ACCEPT);
                            Fragment fragment;
                            fragment = OrderPickedFragment.newInstance();
                            String fragmtStatusTag = fragment.getClass().getName();
                            GlobalValues.getInstance().setFramgentTag(fragmtStatusTag);
                            ((MainActivity) getActivity()).selectedNavItem(R.id.navigation_home);
                            CommonUtils.loadFragment(getActivity(), fragment, fragmtStatusTag);
                        } else {
                            Toast.makeText(context, "Rejected", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                            if (jsonObjectError.optString("token_valid").equalsIgnoreCase("false")) {
                                CommonUtils.logoutSession(activity);
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
            public void onFailure(Call<RemovalSplDetialsResponse> call, Throwable t) {
                Toast.makeText(context, "something went wrong"+t, Toast.LENGTH_LONG).show();

                dialog.dismiss();
            }
        });
    }



    /*void setOnlineOfflineStatus() {
        Boolean status = GlobalValues.getPreferenceManager().getBoolean(PreferenceManager.ISONLINE);
        if (status) {
            binding.tvOnlineStatus.setText("Online");
            binding.driverStatusSwitch.setChecked(true);
        } else {
            binding.tvOnlineStatus.setText("Offline");
            binding.driverStatusSwitch.setChecked(false);
        }
    }*/

    @Override

    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        // mLocationPermissionGranted = false;
        switch (requestCode) {
            case MyConstants.LOCATION_REQUEST: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                    if (!isGPS) {
                        openGPS();
                    }
                }
                updateLocationUI();
            }
        }

    }

    private boolean customMarker(Context context, @DrawableRes int resource, LatLng currentLocation) {
        boolean imageCreated = false;
        Marker currentLocationMarker = null;
        Bitmap bmp = null;
        if (!imageCreated) {
            imageCreated = true;
            Bitmap.Config conf = Bitmap.Config.ARGB_8888;
            bmp = Bitmap.createBitmap(400, 400, conf);
            Canvas canvas1 = new Canvas(bmp);

            Paint color = new Paint();
            color.setTextSize(30);
            color.setColor(Color.WHITE);

            BitmapFactory.Options opt = new BitmapFactory.Options();
            opt.inMutable = true;

            Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(),
                    R.drawable.ic_taxi_flat, opt);
            Bitmap resized = Bitmap.createScaledBitmap(imageBitmap, 320, 320, true);
            canvas1.drawBitmap(resized, 40, 40, color);

            canvas1.drawText("Lee", 30, 40, color);

            currentLocationMarker = mMap.addMarker(new MarkerOptions().position(currentLocation)
                    .icon(BitmapDescriptorFactory.fromBitmap(bmp))
                    // Specifies the anchor to be at a particular point in the marker image.
                    .anchor(0.5f, 1));
        } else {
            currentLocationMarker.setPosition(currentLocation);
        }
        return true;
    }

    public static Bitmap createCustomMarker(Context context, String time, String text) {

        View marker = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker_layout, null);

       /* TextView txt_name = (TextView)marker.findViewById(R.id.time);
        TextView txt_text = (TextView)marker.findViewById(R.id.time_min);
         txt_name.setText(time);
        txt_text.setText(text);*/

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        marker.setLayoutParams(new ViewGroup.LayoutParams(20, ViewGroup.LayoutParams.WRAP_CONTENT));
        marker.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        marker.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        marker.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(marker.getMeasuredWidth(), marker.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        marker.draw(canvas);

        return bitmap;
    }

    public static Bitmap createCustomMarker(Context context, @DrawableRes int resource, String _name) {

        View marker = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker_layout, null);

        // CircleImageView markerImage = (CircleImageView) marker.findViewById(R.id.user_dp);
        // markerImage.setImageResource(resource);
        //  TextView txt_name = (TextView)marker.findViewById(R.id.name);
        // txt_name.setText(_name);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        marker.setLayoutParams(new ViewGroup.LayoutParams(60, ViewGroup.LayoutParams.WRAP_CONTENT));
        marker.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        marker.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        marker.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(marker.getMeasuredWidth(), marker.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        marker.draw(canvas);

        return bitmap;
    }

    public void AcceptRejectPopup(final Activity activity, final String requesID, final String pickup_location) {

        LayoutInflater inflater = LayoutInflater.from(activity);
        View viewCustom = inflater.inflate(R.layout.new_job_request_dialog, null);
        final AlertDialog dialog = new AlertDialog.Builder(activity).create();
        dialog.setView(viewCustom);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView pickupLocation, cost;
        pickupLocation = viewCustom.findViewById(R.id.tv_pickup_location);
        // cost = viewCustom.findViewById(R.id.tv_ride_cost);
        pickupLocation.setText(pickup_location);
        LinearLayout accept = viewCustom.findViewById(R.id.accept);
        LinearLayout reject = viewCustom.findViewById(R.id.reject);
        dialog.setCancelable(false);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                stopRingTone();
                acceptRejectRequestApi(userId, MyConstants.ACCEPT, requesID);
               /* Fragment fragment;
                fragment = new OrderPickedFragment();
                String fragmtStatusTag = fragment.getClass().getName();
                GlobalValues.getInstance().setFramgentTag(fragmtStatusTag);
                ((MainActivity) getActivity()).selectedNavItem(R.id.navigation_home);
                CommonUtils.loadFragment(getActivity(), fragment, fragmtStatusTag);*/
            }
        });
        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                stopRingTone();
                acceptRejectRequestApi(userId, MyConstants.REJECT, requesID);

            }
        });
        dialog.show();
        startTimer(dialog);
        setAlarmRingtone();
    }

    private void startTimer(final AlertDialog dialog) {
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                    stopRingTone();
                }
            }
        };
        handler.postDelayed(runnable, 90 * 1000);
    }

    void playaudio() {
        ring = MediaPlayer.create(context, R.raw.sound);
        ring.setAudioStreamType(AudioManager.STREAM_MUSIC);

        ring.start();

        ring.setLooping(true);
    }

    void setAlarmRingtone() {
        try {
            /*//  Uri path = Uri.parse("android.resource://" + getPackageName() + R.raw.sound);
            Uri path = Uri.parse("android.resource://" + getPackageName() + "/raw/sound.mp3");
            //File ring = new File("android.resource://" + getPackageName() + "/raw/sound.mp3");
            // Uri path1 = MediaStore.Audio.Media.getContentUriForPath(ring.getAbsolutePath());

            // The line below will set it as a default ring tone replace
            // RingtoneManager.TYPE_RINGTONE with RingtoneManager.TYPE_NOTIFICATION
            // to set it as a notification tone
            RingtoneManager.setActualDefaultRingtoneUri(
                    this, RingtoneManager.TYPE_RINGTONE, path);*/
            Uri currentRintoneUri = RingtoneManager.getActualDefaultRingtoneUri(context, RingtoneManager.TYPE_ALARM);
            r = RingtoneManager.getRingtone(context, currentRintoneUri);
            r.play();
            r.setLooping(true);
            isRingTonePlay = true;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void stopRingTone() {
        if (r != null && r.isPlaying()) {
            r.stop();
        }
        /*if (ring.isPlaying()) {
            ring.stop();
        }*/
        isRingTonePlay = false;
    }
    void currentRideApi() {

        final LoadingProgressDialog dialog = new LoadingProgressDialog(getActivity(), "please Wait!");
        dialog.setCancelable(false);
        dialog.show();
        ApiInterface apiInterface = ApiClients.getConnection(getActivity());
        Call<JsonObject> call = apiInterface.currentRideApi(loginResponse.getData().getToken(), userId);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                try {
                    dialog.dismiss();
                    if (response.code() >= 200 && response.code() <= 210) {
                        JSONObject object = new JSONObject(response.body().toString());
                        Toast.makeText(getActivity(), object.optString("message"), Toast.LENGTH_SHORT).show();
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
                                CommonUtils.logoutSession(getActivity());
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
