package com.transdignity.driver.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Context;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.JsonObject;
import com.transdignity.driver.MainActivity;
import com.transdignity.driver.R;
import com.transdignity.driver.activities.LoginActivity;
import com.transdignity.driver.activities.ProfileActivity;
import com.transdignity.driver.databinding.FragmentOrderPickedBinding;
import com.transdignity.driver.models.auth.LoginResponse;
import com.transdignity.driver.models.auth.pofileEdit.EditProfileRequest;
import com.transdignity.driver.models.auth.pofileEdit.EditProfileResponse;
import com.transdignity.driver.models.requestService.AllServiceRequestDetailResponse;
import com.transdignity.driver.models.requestService.ReachedDriverRequestModel;
import com.transdignity.driver.models.requestService.ReachedDriverResponseModel;
import com.transdignity.driver.models.requestService.RemovalSplDetialsResponse;
import com.transdignity.driver.models.rideDetail.RideDetailResponseModel;
import com.transdignity.driver.network.ApiClients;
import com.transdignity.driver.network.ApiInterface;
import com.transdignity.driver.utilities.CommonUtils;
import com.transdignity.driver.utilities.FloatingViewService;
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
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static androidx.constraintlayout.widget.Constraints.TAG;

public class OrderPickedFragment extends Fragment implements View.OnClickListener, OnMapReadyCallback {

    Context context;
    Activity activity;
    FragmentOrderPickedBinding binding;
    private GoogleMap mMap;
    LoginResponse loginResponse;
    String userId;
    private static final int SYSTEM_ALERT_WINDOW_PERMISSION = 2084;
    private boolean isGPS = false, isFatchLatong = false;
    String rs_mobile_number = "", rs_lat, rs_long, time = "", request_id;
    String rs_address = "";
    String tvremovalspecialist = "";
    boolean mLocationPermissionGranted = false;
    RemovalSplDetialsResponse removalSplDetialsResponse;
    AllServiceRequestDetailResponse allServiceRequestDetailResponse;
    LocationCallback locationCallback;
    private FusedLocationProviderClient mFusedLocationClient;
    ImageView iv_navigator;
    public OrderPickedFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static OrderPickedFragment newInstance() {
        OrderPickedFragment fragment = new OrderPickedFragment();
        // fragment.removalSplDetialsResponse = removalSplDetialsResponse;
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_order_picked, container, false);
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
            request_id = GlobalValues.getPreferenceManager().getString(PreferenceManager.REQUESTID);
        }
        if (request_id != null)
           // getRemovalSplDetailsApi();
       // getAllServiceRequestDetailApi();
            iv_navigator=view.findViewById(R.id.iv_navigator);
        binding.currentLocationPicker.setOnClickListener(this);
        //CommonUtils.setInterpolation(context, binding.llCall);
        binding.llCall.setOnClickListener(this);
        binding.llReached.setOnClickListener(this);
        binding.llStartNavigation.setOnClickListener(this);
        binding.llOtherService.setOnClickListener(this);
        binding.llCancel.setOnClickListener(this);
        binding.llCancelride.setOnClickListener(this);
        iv_navigator.setOnClickListener(this);
        getLocationPermission();
        openGPS();
        getDriverAllServiceRequestDetailApi();

       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(context)) {
            askPermission();
        }*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.current_location_picker:
                try {
                    if (mLocationPermissionGranted) {
                        if (isGPS) {
                            //createLocationUpdate();
                            if (GlobalValues.getInstance().getLattitude() != null && GlobalValues.getInstance().getLongitude() != null && mMap != null) {
                                //markerDisplayOnMap(GlobalValues.getInstance().getLattitude(), GlobalValues.getInstance().getLongitude());
                                LatLng latLong = new LatLng(GlobalValues.getInstance().getLattitude(), GlobalValues.getInstance().getLongitude());
                                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLong, 15.0f));
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
            case R.id.ll_call:
                //CommonUtils.setInterpolation(context, binding.llCall);
                try {
                    if (rs_mobile_number != null) {
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:" + rs_mobile_number));
                        startActivity(callIntent);
                    } else {
                        Toast.makeText(context, "Invalid Mobile Number", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                }
                break;
            case R.id.ll_reached:
                reachedToRemovalSplApi();
                Fragment fragment = InfoFragment.newInstance("reached_on_rs_location");
                String tagfragment = fragment.getClass().getName();
                GlobalValues.getInstance().setFramgentTag(tagfragment);
                CommonUtils.loadFragment(context, fragment, tagfragment);
                GlobalValues.getInstance().setRequestStatus("reached_on_rs_location");
                break;
            case R.id.ll_other_service:
                reachedPickupLocationApi();
                /*reachedToRemovalSplApi();
                Fragment fragment = InfoFragment.newInstance("reached_on_rs_location");
                String tagfragment = fragment.getClass().getName();
                GlobalValues.getInstance().setFramgentTag(tagfragment);
                CommonUtils.loadFragment(context, fragment, tagfragment);
                GlobalValues.getInstance().setRequestStatus("reached_on_rs_location");*/
                break;
            case R.id.ll_cancelride:
                cancelRideApi();
                break;
            case R.id.ll_cancel:
                cancelRideApi();
                break;
                case R.id.iv_navigator:
                    CommonUtils.navigateToGMap(context,rs_lat, rs_long);
                break;
            case R.id.ll_start_navigation:
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                    context.startService(new Intent(context, FloatingViewService.class));
                } else if (Settings.canDrawOverlays(context)) {
                    context.startService(new Intent(context, FloatingViewService.class));
                } else {
                    askPermission();
                    Toast.makeText(context, "You need System Alert Window Permission to do this", Toast.LENGTH_SHORT).show();
                }
                if (rs_lat != null && rs_long != null)
                    CommonUtils.navigateToGMap(context,rs_lat, rs_long);
                break;
        }
    }

    void getRemovalSplDetailsApi() {
        final LoadingProgressDialog dialog = new LoadingProgressDialog(context, "please Wait!");
        dialog.setCancelable(false);
        dialog.show();
        ApiInterface apiInterface = ApiClients.getConnection(context);
        Call<RemovalSplDetialsResponse> call = apiInterface.removalsplDetailsApi(loginResponse.getData().getToken(), request_id);
        call.enqueue(new Callback<RemovalSplDetialsResponse>() {
            @Override
            public void onResponse(Call<RemovalSplDetialsResponse> call, Response<RemovalSplDetialsResponse> response) {
                try {
                    dialog.dismiss();
                    if (response.code() >= 200 && response.code() <= 210) {
                        if (response.body().getSuccess().equalsIgnoreCase("true")) {
                            removalSplDetialsResponse = response.body();
                            time = removalSplDetialsResponse.getData().getTime();
                            binding.time.setText(time);
                            binding.tvKm.setText(removalSplDetialsResponse.getData().getDistance());
                            rs_mobile_number = removalSplDetialsResponse.getData().getPhoneNumber();
                            rs_lat = removalSplDetialsResponse.getData().getLatitude();
                            rs_long = removalSplDetialsResponse.getData().getLongitude();
                            rs_address = removalSplDetialsResponse.getData().getAddress();
                            if (rs_lat != null && rs_long != null) {
                                markerDisplayOnMap(Double.valueOf(rs_lat), Double.valueOf(rs_long));
                            }
                        } else {
                            Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
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
                dialog.dismiss();
            }
        });
    }

    void getAllServiceRequestDetailApi() {
        final LoadingProgressDialog dialog = new LoadingProgressDialog(context, "please Wait!");
        dialog.setCancelable(false);
        dialog.show();
        ApiInterface apiInterface = ApiClients.getConnection(context);
        Call<AllServiceRequestDetailResponse> call = apiInterface.getRequestDetailApi(loginResponse.getData().getToken(),userId, request_id);
        call.enqueue(new Callback<AllServiceRequestDetailResponse>() {
            @Override
            public void onResponse(Call<AllServiceRequestDetailResponse> call, Response<AllServiceRequestDetailResponse> response) {
                try {
                    dialog.dismiss();
                    if (response.code() >= 200 && response.code() <= 210) {
                        if (response.body().getSuccess().equalsIgnoreCase("true")) {
                            allServiceRequestDetailResponse = response.body();
                            //time = allServiceRequestDetailResponse.getData().getTime();
                           // binding.time.setText(time);
                            binding.time.setText("Away "+allServiceRequestDetailResponse.getData().getTotalDistance()+"  miles");
                            rs_mobile_number = allServiceRequestDetailResponse.getData().getMobileNumber();
                            String decendante_name = allServiceRequestDetailResponse.getData().getFirstName();
                            String pickup_lat_long=allServiceRequestDetailResponse.getData().getPickupLocationLatlong();
                            String drop_lat_long=allServiceRequestDetailResponse.getData().getDropLocationLatlong();
                            String[] pick_up_lat_lng = pickup_lat_long.split(",");
                            String[] drop_lat_lng = drop_lat_long.split(",");
                            rs_lat = pick_up_lat_lng [0];
                            rs_long = pick_up_lat_lng [1];
                            markerDisplayOnMap(Double.valueOf(rs_lat), Double.valueOf(rs_long));
                            tvremovalspecialist= allServiceRequestDetailResponse.getData().getFirstName()+" "+allServiceRequestDetailResponse.getData().getLastName();
                            String str_reciver_name= allServiceRequestDetailResponse.getData().getmRecieverFirstName()+" "+allServiceRequestDetailResponse.getData().getmRecieverLastName();
                            if(decendante_name==null){
                                binding.tvRemovalSpecialist.setText(str_reciver_name);
                            }else {
                                binding.tvRemovalSpecialist.setText(tvremovalspecialist);

                            }
                            String service_id = allServiceRequestDetailResponse.getData().getServiceId();
                            if(service_id.equals("2")||service_id.equals("3")||service_id.equals("4")||service_id.equals("6")){
                                binding.llOtherService.setVisibility(View.VISIBLE);
                                binding.llForDescendantService.setVisibility(View.GONE);
                                binding.tvReached.setText("Reached ");
                            }else if(service_id.equals("1")||service_id.equals("5")||service_id.equals("8")){
                                binding.llForDescendantService.setVisibility(View.VISIBLE);
                                binding.llOtherService.setVisibility(View.GONE);

                            }else {
                                binding.llForDescendantService.setVisibility(View.VISIBLE);
                                binding.llOtherService.setVisibility(View.VISIBLE);

                            }
                            /* rs_lat = removalSplDetialsResponse.getData().getLatitude();
                            rs_long = removalSplDetialsResponse.getData().getLongitude();*/
                           // String drop_lat_long=allServiceRequestDetailResponse.getData().getDropLocationLatlong();
                           // rs_address = removalSplDetialsResponse.getData().getAddress();
//                            if (rs_lat != null && rs_long != null) {
//                                markerDisplayOnMap(Double.valueOf(rs_lat), Double.valueOf(rs_long));
//                            }
                        } else {
                            Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<AllServiceRequestDetailResponse> call, Throwable t) {
                dialog.dismiss();
            }
        });
    }


    void reachedToRemovalSplApi() {
        final LoadingProgressDialog dialog = new LoadingProgressDialog(context, "please Wait!");
        dialog.setCancelable(false);
        dialog.show();
        ApiInterface apiInterface = ApiClients.getConnection(context);
        Call<RemovalSplDetialsResponse> call = apiInterface.reachedToRsApi(loginResponse.getData().getToken(), request_id);
        call.enqueue(new Callback<RemovalSplDetialsResponse>() {
            @Override
            public void onResponse(Call<RemovalSplDetialsResponse> call, Response<RemovalSplDetialsResponse> response) {
                try {
                    dialog.dismiss();
                    if (response.code() >= 200 && response.code() <= 210) {
                        if (response.body().getSuccess().equalsIgnoreCase("true")) {
                            Fragment fragment = InfoFragment.newInstance("reached_on_rs_location");
                            GlobalValues.getInstance().setRequestStatus("reached_on_rs_location");
                            String tagfragment = fragment.getClass().getName();
                            ((MainActivity) activity).selectedNavItem(R.id.navigation_home);
                            GlobalValues.getInstance().setFramgentTag(tagfragment);
                            CommonUtils.loadFragment(context, fragment, tagfragment);
                        } else {
                            Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
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
                dialog.dismiss();
            }
        });
    }

    void reachedPickupLocationApi() {


        final LoadingProgressDialog dialog = new LoadingProgressDialog(getActivity(), "Please wait...");
        dialog.setCancelable(false);
        dialog.show();

        ApiInterface apiInterface = ApiClients.getConnection(getActivity());
        ReachedDriverRequestModel request = new ReachedDriverRequestModel();
        request.setUserId(userId);
        request.setRequestId(request_id);



        Call<ReachedDriverResponseModel> call = apiInterface.reachedDriverApi(loginResponse.getData().getToken(), request);
        call.enqueue(new Callback<ReachedDriverResponseModel>() {
            @Override
            public void onResponse(Call<ReachedDriverResponseModel> call, Response<ReachedDriverResponseModel> response) {
                try {
                    dialog.dismiss();
                    if (response.code() >= 200 && response.code() <= 210) {
                        if (response.body().getSuccess().equalsIgnoreCase("true")) {
                            Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            Fragment fragment = PickupDriverFragment.newInstance("descendant");
                            String tagfragment = fragment.getClass().getName();
                            GlobalValues.getInstance().setFramgentTag(tagfragment);
                            CommonUtils.loadFragment(context, fragment, tagfragment);
                           // GlobalValues.getInstance().setRequestStatus("reached_on_pickup_location");

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
                            Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ReachedDriverResponseModel> call, Throwable t) {
                dialog.dismiss();
                Log.e(TAG, "onResponse: " + t.getMessage());
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    void cancelRideApi() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("user_id", userId);
        jsonObject.addProperty("request_id", request_id);
        final LoadingProgressDialog dialog = new LoadingProgressDialog(context, "please Wait!");
        dialog.setCancelable(false);
        dialog.show();
        ApiInterface apiInterface = ApiClients.getConnection(context);
        Call<JsonObject> call = apiInterface.cancelRideApi(loginResponse.getData().getToken(), jsonObject);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                try {
                    dialog.dismiss();
                    if (response.code() >= 200 && response.code() <= 210) {
                        JSONObject object = new JSONObject(response.body().toString());
                        Toast.makeText(context, object.optString("message"), Toast.LENGTH_SHORT).show();
                        if (object.optString("success").equalsIgnoreCase("true")) {
                            Fragment fragment = HomeFragment.newInstance();
                            String tagfragment = fragment.getClass().getName();
                            GlobalValues.getInstance().setFramgentTag(tagfragment);
                            CommonUtils.loadFragment(context, fragment, tagfragment);
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
            public void onFailure(Call<JsonObject> call, Throwable t) {
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        updateLocationUI();
        if (rs_lat != null && rs_long != null) {
            markerDisplayOnMap(Double.valueOf(rs_lat), Double.valueOf(rs_long));
        }
       /* if (GlobalValues.getInstance().getLattitude() != null && GlobalValues.getInstance().getLongitude() != null) {
            markerDisplayOnMap(GlobalValues.getInstance().getLattitude(), GlobalValues.getInstance().getLongitude());
        }*/

    }

    private void getLocationPermission() {

        if (ContextCompat.checkSelfPermission(context,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(activity,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.CALL_PHONE},
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
                // createLocationCallback();
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

    private void markerDisplayOnMap(double lat, double longit) {
        LatLng latLong = new LatLng(lat, longit);
        try {

            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            mMap.addMarker(
                    new MarkerOptions()
                            .position(latLong)
                            .title(rs_address)
                            .icon(BitmapDescriptorFactory.fromBitmap(createCustomMarker(context, time, "MIN")))
                            .zIndex(9)
            );
            // mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney);
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLong, 15.0f));
        } catch (Exception e) {

        }
    }

    private void openGPS() {
        new GpsUtils(context).turnGPSOn(new GpsUtils.onGpsListener() {
            @Override
            public void gpsStatus(boolean isGPSEnable) {
                // turn on GPS
                isGPS = isGPSEnable;
                //createLocationCallback();
                if (GlobalValues.getInstance().getLattitude() != null && GlobalValues.getInstance().getLongitude() != null && mMap != null) {
                    LatLng latLong = new LatLng(GlobalValues.getInstance().getLattitude(), GlobalValues.getInstance().getLongitude());
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLong, 15.0f));
                }

            }
        });
    }

    private Bitmap customMarker() {

        Bitmap bmp = null;

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
        Bitmap resized = Bitmap.createScaledBitmap(imageBitmap, 120, 320, true);
        canvas1.drawBitmap(resized, 40, 40, color);
        canvas1.drawText("Lee", 30, 40, color);
        return bmp;
    }

    public static Bitmap createCustomMarker(Context context, String time, String text) {

        View marker = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker_layout, null);

        //  TextView txt_name = (TextView)marker.findViewById(R.id.time);
        // TextView txt_text = (TextView)marker.findViewById(R.id.time_min);
        // txt_name.setText(time);
        //txt_text.setText(text);

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

    private void askPermission() {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:" + context.getPackageName()));
        startActivityForResult(intent, SYSTEM_ALERT_WINDOW_PERMISSION);
    }



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


    void getDriverAllServiceRequestDetailApi() {
        final LoadingProgressDialog dialog = new LoadingProgressDialog(context, "please Wait!");
        dialog.setCancelable(false);
        dialog.show();
        ApiInterface apiInterface = ApiClients.getConnection(context);
        Call<RideDetailResponseModel> call = apiInterface.getDriverRequestDetailApi(loginResponse.getData().getToken(),userId, request_id);
        call.enqueue(new Callback<RideDetailResponseModel>() {
            @Override
            public void onResponse(Call<RideDetailResponseModel> call, Response<RideDetailResponseModel> response) {
                try {
                    dialog.dismiss();
                    if (response.code() >= 200 && response.code() <= 210) {
                        String success=response.body().getSuccess();
                        if(success.equals("true")) {
                            String service_id = response.body().getData().getServiceId();
                            if(service_id.equals("1")){
                                binding.llOtherService.setVisibility(View.GONE);

                                String decendent_name=response.body().getData().getDecendentFirstName();
                                String rs_name=response.body().getData().getRsName();
                                String reciver_name=response.body().getData().getRecieverFirstName();
                                rs_mobile_number=response.body().getData().getRsPhone();
                                String from_address_latlong=response.body().getData().getPickupLocationLatlong();
                                String to_address_latlong=response.body().getData().getDropLocationLatlong();
                                String removed_from_address=response.body().getData().getPickupLocation();
                                String transferred_to_address=response.body().getData().getDropLocation();
                                String total_distance=response.body().getData().getmTotalDistance();
                                binding.time.setText("Away "+total_distance);
                                binding.tvRemovalSpecialist.setText(rs_name);

                                String[] pick_up_lat_lng = from_address_latlong.split(",");
                               /* rs_lat = pick_up_lat_lng [0];
                                rs_long = pick_up_lat_lng [1];*/
                                rs_lat = response.body().getData().getRsLatitude();
                                rs_long = response.body().getData().getRsLongitude();
                                markerDisplayOnMap(Double.valueOf(rs_lat), Double.valueOf(rs_long));

                            }
                            else if(service_id.equals("2")){
                                binding.llOtherService.setVisibility(View.VISIBLE);
                                binding.llForDescendantService.setVisibility(View.GONE);

                                String decendent_name=response.body().getData().getDecendentFirstName();
                                String rs_name=response.body().getData().getRsName();
                                String reciver_name=response.body().getData().getRecieverFirstName();
                                rs_mobile_number=response.body().getData().getDecendantMobileNumber();
                                String from_address_latlong=response.body().getData().getPickupLocationLatlong();
                                String to_address_latlong=response.body().getData().getDropLocationLatlong();
                                String removed_from_address=response.body().getData().getPickupLocation();
                                String transferred_to_address=response.body().getData().getDropLocation();
                                String total_distance=response.body().getData().getmTotalDistance();
                                binding.time.setText("Away "+total_distance);                                binding.tvRemovalSpecialist.setText(decendent_name);

                                String[] pick_up_lat_lng = from_address_latlong.split(",");
                                rs_lat = pick_up_lat_lng [0];
                                rs_long = pick_up_lat_lng [1];
                                markerDisplayOnMap(Double.valueOf(rs_lat), Double.valueOf(rs_long));

                            }
                            else if(service_id.equals("3")){
                                binding.llOtherService.setVisibility(View.VISIBLE);
                                binding.llForDescendantService.setVisibility(View.GONE);
                                String decendent_name=response.body().getData().getDecendentFirstName();
                                String rs_name=response.body().getData().getRsName();
                                String reciver_name=response.body().getData().getRecieverFirstName();
                                rs_mobile_number=response.body().getData().getDecendantMobileNumber();
                                String from_address_latlong=response.body().getData().getPickupLocationLatlong();
                                String to_address_latlong=response.body().getData().getDropLocationLatlong();
                                String removed_from_address=response.body().getData().getPickupLocation();
                                String transferred_to_address=response.body().getData().getDropLocation();
                                String total_distance=response.body().getData().getmTotalDistance();
                                binding.time.setText("Away "+total_distance);                                binding.tvRemovalSpecialist.setText(decendent_name);

                                String[] pick_up_lat_lng = from_address_latlong.split(",");
                                rs_lat = pick_up_lat_lng [0];
                                rs_long = pick_up_lat_lng [1];
                                markerDisplayOnMap(Double.valueOf(rs_lat), Double.valueOf(rs_long));



                            }
                            else if(service_id.equals("4")){
                                binding.llOtherService.setVisibility(View.VISIBLE);
                                binding.llForDescendantService.setVisibility(View.GONE);
                                String decendent_name=response.body().getData().getDecendentFirstName();
                                String rs_name=response.body().getData().getRsName();
                                String reciver_name=response.body().getData().getRecieverFirstName();
                                rs_mobile_number=response.body().getData().getRsPhone();
                                String from_address_latlong=response.body().getData().getPickupLocationLatlong();
                                String to_address_latlong=response.body().getData().getDropLocationLatlong();
                                String removed_from_address=response.body().getData().getPickupLocation();
                                String transferred_to_address=response.body().getData().getDropLocation();
                                String total_distance=response.body().getData().getmTotalDistance();
                                binding.time.setText("Away "+total_distance+"  miles");                                binding.tvRemovalSpecialist.setText(decendent_name);

                                String[] pick_up_lat_lng = from_address_latlong.split(",");
                                rs_lat = pick_up_lat_lng [0];
                                rs_long = pick_up_lat_lng [1];
                                markerDisplayOnMap(Double.valueOf(rs_lat), Double.valueOf(rs_long));

                            }
                            else if(service_id.equals("5")){
                                binding.llOtherService.setVisibility(View.GONE);
                                binding.llForDescendantService.setVisibility(View.VISIBLE);
                                String decendent_name=response.body().getData().getDecendentFirstName();
                                String rs_name=response.body().getData().getRsName();
                                String reciver_name=response.body().getData().getRecieverFirstName();
                                rs_mobile_number=response.body().getData().getRsPhone();
                                String from_address_latlong=response.body().getData().getPickupLocationLatlong();
                                String to_address_latlong=response.body().getData().getDropLocationLatlong();
                                String removed_from_address=response.body().getData().getPickupLocation();
                                String transferred_to_address=response.body().getData().getDropLocation();
                                String total_distance=response.body().getData().getmTotalDistance();
                                binding.time.setText("Away "+total_distance);
                                binding.tvRemovalSpecialist.setText(rs_name);

                                String[] pick_up_lat_lng = from_address_latlong.split(",");
                               /* rs_lat = pick_up_lat_lng [0];
                                rs_long = pick_up_lat_lng [1];*/
                                rs_lat = response.body().getData().getRsLatitude();
                                rs_long = response.body().getData().getRsLongitude();
                                markerDisplayOnMap(Double.valueOf(rs_lat), Double.valueOf(rs_long));

                            }
                            else if(service_id.equals("6")){
                                binding.llOtherService.setVisibility(View.VISIBLE);
                                binding.llForDescendantService.setVisibility(View.GONE);

                                String decendent_name=response.body().getData().getDecendentFirstName();
                                String rs_name=response.body().getData().getRsName();
                                String reciver_name=response.body().getData().getRecieverFirstName();
                                rs_mobile_number=response.body().getData().getDecendantMobileNumber();
                                String from_address_latlong=response.body().getData().getPickupLocationLatlong();
                                String to_address_latlong=response.body().getData().getDropLocationLatlong();
                                String removed_from_address=response.body().getData().getPickupLocation();
                                String transferred_to_address=response.body().getData().getDropLocation();
                                String total_distance=response.body().getData().getmTotalDistance();
                                binding.time.setText("Away "+total_distance);                                binding.tvRemovalSpecialist.setText(decendent_name);

                                String[] pick_up_lat_lng = from_address_latlong.split(",");
                                rs_lat = pick_up_lat_lng [0];
                                rs_long = pick_up_lat_lng [1];
                                markerDisplayOnMap(Double.valueOf(rs_lat), Double.valueOf(rs_long));

                            }
                            else if(service_id.equals("7")){
                                binding.llOtherService.setVisibility(View.GONE);

                                String decendent_name=response.body().getData().getDecendentFirstName();
                                String rs_name=response.body().getData().getRsName();
                                String reciver_name=response.body().getData().getRecieverFirstName();
                                rs_mobile_number=response.body().getData().getRsPhone();
                                String from_address_latlong=response.body().getData().getPickupLocationLatlong();
                                String to_address_latlong=response.body().getData().getDropLocationLatlong();
                                String removed_from_address=response.body().getData().getPickupLocation();
                                String transferred_to_address=response.body().getData().getDropLocation();
                                String total_distance=response.body().getData().getmTotalDistance();
                                binding.time.setText("Away "+total_distance);
                                binding.tvRemovalSpecialist.setText(rs_name);

                                String[] pick_up_lat_lng = from_address_latlong.split(",");
                               /* rs_lat = pick_up_lat_lng [0];
                                rs_long = pick_up_lat_lng [1];*/
                                rs_lat = response.body().getData().getRsLatitude();
                                rs_long = response.body().getData().getRsLongitude();
                                markerDisplayOnMap(Double.valueOf(rs_lat), Double.valueOf(rs_long));

                            }
                            else if(service_id.equals("8")){
                                binding.llOtherService.setVisibility(View.GONE);

                                String decendent_name=response.body().getData().getDecendentFirstName();
                                String rs_name=response.body().getData().getRsName();
                                String reciver_name=response.body().getData().getRecieverFirstName();
                                rs_mobile_number=response.body().getData().getRsPhone();
                                String from_address_latlong=response.body().getData().getPickupLocationLatlong();
                                String to_address_latlong=response.body().getData().getDropLocationLatlong();
                                String removed_from_address=response.body().getData().getPickupLocation();
                                String transferred_to_address=response.body().getData().getDropLocation();
                                String total_distance=response.body().getData().getmTotalDistance();
                                binding.time.setText("Away "+total_distance);
                                binding.tvRemovalSpecialist.setText(rs_name);

                                String[] pick_up_lat_lng = from_address_latlong.split(",");
                               /* rs_lat = pick_up_lat_lng [0];
                                rs_long = pick_up_lat_lng [1];*/
                                rs_lat = response.body().getData().getRsLatitude();
                                rs_long = response.body().getData().getRsLongitude();
                                markerDisplayOnMap(Double.valueOf(rs_lat), Double.valueOf(rs_long));

                            }
                            else if(service_id.equals("9")){
                                binding.llOtherService.setVisibility(View.GONE);

                                String decendent_name=response.body().getData().getDecendentFirstName();
                                String rs_name=response.body().getData().getRsName();
                                String reciver_name=response.body().getData().getRecieverFirstName();
                                rs_mobile_number=response.body().getData().getRsPhone();
                                String from_address_latlong=response.body().getData().getPickupLocationLatlong();
                                String to_address_latlong=response.body().getData().getDropLocationLatlong();
                                String removed_from_address=response.body().getData().getPickupLocation();
                                String transferred_to_address=response.body().getData().getDropLocation();
                                String total_distance=response.body().getData().getmTotalDistance();
                                if(rs_name==null||rs_name.equals("")||rs_name=="null"){
                                    binding.tvRemovalSpecialist.setText(decendent_name);
                                    binding.time.setVisibility(View.GONE);
                                    String[] pick_up_lat_lng = from_address_latlong.split(",");
                                    rs_lat = pick_up_lat_lng [0];
                                    rs_long = pick_up_lat_lng [1];
                                    markerDisplayOnMap(Double.valueOf(rs_lat), Double.valueOf(rs_long));
                                } else {
                                    binding.tvRemovalSpecialist.setText(rs_name);
                                    binding.time.setText("Away "+total_distance);
                                    rs_lat = response.body().getData().getRsLatitude();
                                    rs_long = response.body().getData().getRsLongitude();
                                    markerDisplayOnMap(Double.valueOf(rs_lat), Double.valueOf(rs_long));
                                }





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
            public void onFailure(Call<RideDetailResponseModel> call, Throwable t) {
                dialog.dismiss();
            }
        });
    }




}
