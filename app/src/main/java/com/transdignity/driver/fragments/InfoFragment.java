package com.transdignity.driver.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Context;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.transdignity.driver.databinding.FragmentInfoBinding;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfoFragment extends Fragment implements View.OnClickListener, OnMapReadyCallback {
    Context context;
    Activity activity;
    private GoogleMap mMap;
    FragmentInfoBinding binding;
    RemovalSplDetialsResponse removalSplDetialsResponse;
    DecedentDetailsResponse decedentDetailsResponse;
    String rs_number = "", rs_lat, rs_long, time = "", request_id;
    String rs_address = "", status;
    LoginResponse loginResponse;
    boolean mLocationPermissionGranted = false;
    String userId;
    private boolean isGPS = false;
    TextView tvToolbar;

    // TODO: Rename and change types and number of parameters
    public static InfoFragment newInstance(String status) {
        InfoFragment fragment = new InfoFragment();
        fragment.status = status;
        //   fragment.removalSplDetialsResponse = removalSplDetialsResponse;
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_info, container, false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvToolbar = activity.findViewById(R.id.tv_toolbar);
        tvToolbar.setText("Information");
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        loginResponse = (LoginResponse) GlobalValues.getPreferenceManager().getObject(PreferenceManager.LoginResponseKey, LoginResponse.class);
        if (loginResponse != null) {
            userId = loginResponse.getData().getId();
            request_id = GlobalValues.getPreferenceManager().getString(PreferenceManager.REQUESTID);
        }
        if (request_id != null) {
            if (status.equalsIgnoreCase("reached_on_rs_location")) {
                getRemovalSplDetailsApi();
            }
            getDecedentDetailsApi();
        }
        binding.llNavigate.setOnClickListener(this);
        binding.llReached.setOnClickListener(this);
        binding.llDropped.setOnClickListener(this);
        binding.llPicked.setOnClickListener(this);
        binding.llCancel.setOnClickListener(this);
        getLocationPermission();
        managePage();
       /* decedentDetailsResponse = new DecedentDetailsResponse();
        DecedentDetailsResponse.Data data = new DecedentDetailsResponse.Data();
        data.setDecendantFirstName("jawed");
        data.setDecendantLastName("Akhtar");
        data.setAddress("Noida Satka");
        data.setPhone("9122641694");
        data.setDropLocation("New Delhi");
        data.setLattitude("28.621743");
        data.setLongitude("77.368841");
        decedentDetailsResponse.setData(data);*/
     /*   if (status.equalsIgnoreCase("reached_on_rs_location")) {
            setremovalSpecialistData();
        } else if (status.equalsIgnoreCase("pickup_rs")) {
            setDecendentDetailsReachedData();
        } else if (status.equalsIgnoreCase("reached_on_decendant_location")) {
            setDecendentDetailsDroppedData();
        }*/
    }

    void managePage() {
        if (status.equalsIgnoreCase("reached_on_rs_location")) {
            setremovalSpecialistData();
        } else if (status.equalsIgnoreCase("pickup_rs")) {
            setDecendentDetailsReachedData();
        } else if (status.equalsIgnoreCase("reached_on_decendant_location")) {
            setDecendentDetailsDroppedData();
        }
    }

    void setremovalSpecialistData() {

        tvToolbar.setText("Information");
        binding.llRemovalSpecialist.setVisibility(View.VISIBLE);
        binding.llDecendentInfo.setVisibility(View.GONE);

        if (removalSplDetialsResponse != null) {
            binding.tvName.setText(removalSplDetialsResponse.getData().getName());
            binding.tvPhoneNo.setText(removalSplDetialsResponse.getData().getPhoneNumber());
            binding.tvAddress.setText(removalSplDetialsResponse.getData().getAddress());
            rs_number = removalSplDetialsResponse.getData().getPhoneNumber();
            rs_lat = removalSplDetialsResponse.getData().getLatitude();
            rs_long = removalSplDetialsResponse.getData().getLongitude();
            rs_address = removalSplDetialsResponse.getData().getAddress();
            if (rs_lat != null && rs_long != null)
              //  mMap.clear();
                markerDisplayOnMap(Double.valueOf(rs_lat), Double.valueOf(rs_long));
        }
    }

    void setDecendentDetailsReachedData() {
        tvToolbar.setText("Information");
        binding.llRemovalSpecialist.setVisibility(View.GONE);
        binding.llDecendentInfo.setVisibility(View.VISIBLE);
        binding.llDropped.setVisibility(View.GONE);
        binding.llDropLocation.setVisibility(View.GONE);
        binding.llNavigateview.setVisibility(View.VISIBLE);
        if (decedentDetailsResponse != null) {
            rs_lat = decedentDetailsResponse.getData().getLattitude();
            rs_long = decedentDetailsResponse.getData().getLongitude();
            String name = "";
            if (decedentDetailsResponse.getData().getDecendantFirstName() != null) {
                name += decedentDetailsResponse.getData().getDecendantFirstName();
            }
            if (decedentDetailsResponse.getData().getDecendantMiddleName() != null) {
                name += decedentDetailsResponse.getData().getDecendantLastName();
            }
            if (decedentDetailsResponse.getData().getDecendantLastName() != null) {
                name += decedentDetailsResponse.getData().getDecendantLastName();
            }
            binding.tvDecName.setText(name);
            binding.tvDecPhoneNo.setText(decedentDetailsResponse.getData().getPhone());
            binding.tvDecAddress.setText(decedentDetailsResponse.getData().getAddress());
            binding.tvDropLocation.setText(decedentDetailsResponse.getData().getDropLocation());
            binding.tvFlowers.setText(decedentDetailsResponse.getData().getRequestedItems());
            if (rs_lat != null && rs_long != null)
                markerDisplayOnMap(Double.valueOf(rs_lat), Double.valueOf(rs_long));
        }
    }

    void setDecendentDetailsDroppedData() {
        tvToolbar.setText("Information");
        binding.llRemovalSpecialist.setVisibility(View.GONE);
        binding.llDecendentInfo.setVisibility(View.VISIBLE);
        binding.llDropped.setVisibility(View.VISIBLE);
        binding.llDropLocation.setVisibility(View.VISIBLE);
        binding.llNavigateview.setVisibility(View.GONE);
        if (decedentDetailsResponse != null) {
            rs_lat = decedentDetailsResponse.getData().getDropLocationLatitude();
            rs_long = decedentDetailsResponse.getData().getDropLocationLongitude();
            String name = "";
            if (decedentDetailsResponse.getData().getDecendantFirstName() != null) {
                name += decedentDetailsResponse.getData().getDecendantFirstName();
            }
            if (decedentDetailsResponse.getData().getDecendantMiddleName() != null) {
                name += decedentDetailsResponse.getData().getDecendantLastName();
            }
            if (decedentDetailsResponse.getData().getDecendantLastName() != null) {
                name += decedentDetailsResponse.getData().getDecendantLastName();
            }
            binding.tvDecName.setText(name);
            binding.tvDecPhoneNo.setText(decedentDetailsResponse.getData().getPhone());
            binding.tvDecAddress.setText(decedentDetailsResponse.getData().getAddress());
            binding.tvDropLocation.setText(decedentDetailsResponse.getData().getDropLocation());
            binding.tvFlowers.setText(decedentDetailsResponse.getData().getRequestedItems());
            if (rs_lat != null && rs_long != null)
                markerDisplayOnMap(Double.valueOf(rs_lat), Double.valueOf(rs_long));
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        updateLocationUI();
        if (rs_lat != null && rs_long != null)
            markerDisplayOnMap(Double.valueOf(rs_lat), Double.valueOf(rs_long));
        // Add a marker in Sydney, Australia, and move the camera.
       /* LatLng sydney = new LatLng(28.535517, 77.391029);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.addMarker(
                new MarkerOptions()
                        .position(sydney)
                        .title("Marker in Noida")
                        .icon(BitmapDescriptorFactory.fromBitmap(createCustomMarker(context, "5", "MIN")))
                        .zIndex(9)
        );
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,15.0f));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15.0f));*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_picked:
                if (userId != null && request_id != null) {
                    GlobalValues.getInstance().setRequestStatus("pickup_rs");
                    pickedRemovalSplApi(userId, request_id);
                }

                //setDecendentDetailsReachedData();
                break;
            case R.id.ll_cancel:
                GlobalValues.getInstance().setRequestStatus(null);
                cancelRideApi();
                break;
            case R.id.ll_navigate:
                if (rs_lat != null && rs_long != null)
                    CommonUtils.navigateToGMap(context,rs_lat, rs_long);
                break;
            case R.id.ll_reached:
                GlobalValues.getInstance().setRequestStatus("reached_on_decendant_location");
                reachedToDecedentApi();
                /*GlobalValues.getInstance().setRequestStatus("reached_on_decendant_location");
                setDecendentDetailsDroppedData();*/
                break;
            case R.id.ll_dropped:
                GlobalValues.getInstance().setRequestStatus(null);
                dropRequestApi(userId, MyConstants.COMPLETE, request_id);
                break;
        }
    }

    void pickedRemovalSplApi(String userId, String requestId) {
        final LoadingProgressDialog dialog = new LoadingProgressDialog(context, "please Wait!");
        dialog.setCancelable(false);
        dialog.show();
        ApiInterface apiInterface = ApiClients.getConnection(context);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("user_id", userId);
        jsonObject.addProperty("request_id", requestId);
        jsonObject.addProperty("status", "Ongoing");
        Call<DecedentDetailsResponse> call = apiInterface.pickedRsApi(loginResponse.getData().getToken(), jsonObject);
        call.enqueue(new Callback<DecedentDetailsResponse>() {
            @Override
            public void onResponse(Call<DecedentDetailsResponse> call, Response<DecedentDetailsResponse> response) {
                try {
                    dialog.dismiss();
                    if (response.code() >= 200 && response.code() <= 210) {
                        if (response.body().getSuccess().equalsIgnoreCase("true")){
                            setDecendentDetailsReachedData();
                        }else {
                            Toast.makeText(context,response.body().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }else {
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
            public void onFailure(Call<DecedentDetailsResponse> call, Throwable t) {
                dialog.dismiss();
            }
        });
    }


    void getDecedentDetailsApi() {
        final LoadingProgressDialog dialog = new LoadingProgressDialog(context, "please Wait!");
        dialog.setCancelable(false);
        dialog.show();
        ApiInterface apiInterface = ApiClients.getConnection(context);
        Call<DecedentDetailsResponse> call = apiInterface.decedentDetailsApi(loginResponse.getData().getToken(), request_id);
        call.enqueue(new Callback<DecedentDetailsResponse>() {
            @Override
            public void onResponse(Call<DecedentDetailsResponse> call, Response<DecedentDetailsResponse> response) {
                try {
                    dialog.dismiss();
                    if (response.code() >= 200 && response.code() <= 210) {
                        if (response.body().getSuccess().equalsIgnoreCase("true")) {
                            decedentDetailsResponse = response.body();
                            managePage();
                            /*rs_lat = decedentDetailsResponse.getData().getLattitude();
                            rs_long = decedentDetailsResponse.getData().getLongitude();
                            rs_address = decedentDetailsResponse.getData().getAddress();*/
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
            public void onFailure(Call<DecedentDetailsResponse> call, Throwable t) {
                dialog.dismiss();
            }
        });
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
                            rs_lat = removalSplDetialsResponse.getData().getLatitude();
                            rs_long = removalSplDetialsResponse.getData().getLongitude();
                            rs_address = removalSplDetialsResponse.getData().getAddress();
                            managePage();
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

    void reachedToDecedentApi() {
        final LoadingProgressDialog dialog = new LoadingProgressDialog(context, "please Wait!");
        dialog.setCancelable(false);
        dialog.show();
        ApiInterface apiInterface = ApiClients.getConnection(context);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("user_id", userId);
        jsonObject.addProperty("request_id", request_id);
        Call<JsonObject> call = apiInterface.reachedToDecedentApi(loginResponse.getData().getToken(), jsonObject);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                try {
                    dialog.dismiss();
                    if (response.code() >= 200 && response.code() <= 210) {
                        JSONObject object = new JSONObject(response.body().toString());
                        if (object.optString("success").equalsIgnoreCase("true"))
                            setDecendentDetailsDroppedData();
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

    void dropRequestApi(String userId, final String status, final String requestId) {
        final LoadingProgressDialog dialog = new LoadingProgressDialog(context, "please Wait!");
        dialog.setCancelable(false);
        dialog.show();
        ApiInterface apiInterface = ApiClients.getConnection(context);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("user_id", userId);
        jsonObject.addProperty("status", status);
        jsonObject.addProperty("request_id", requestId);
        Call<RemovalSplDetialsResponse> call = apiInterface.acceptRejectApi(loginResponse.getData().getToken(), jsonObject);
        call.enqueue(new Callback<RemovalSplDetialsResponse>() {
            @Override
            public void onResponse(Call<RemovalSplDetialsResponse> call, Response<RemovalSplDetialsResponse> response) {
                dialog.dismiss();
                try {
                    if (response.code() >= 200 && response.code() <= 210) {
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        if (response.body().getSuccess().equalsIgnoreCase("true")) {
                            GlobalValues.getInstance().setRequestStatus(null);
                            Fragment fragment;

                            fragment = HomeFragment.newInstance();
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
                dialog.dismiss();
            }
        });
    }

    private void markerDisplayOnMap(double lat, double longit) {
        LatLng latLong = new LatLng(lat, longit);
        try {
            mMap.clear();
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            mMap.addMarker(
                    new MarkerOptions()
                            .position(latLong)
                            .title(rs_address)
                            .icon(BitmapDescriptorFactory.fromBitmap(createCustomMarker(context, time, "MIN")))
                            .zIndex(9)
            );
            // mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney);
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLong, 18.0f));
        } catch (Exception e) {

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

    public static Bitmap createCustomMarker(Context context, String time, String text) {

        View marker = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker_layout, null);
        ImageView imageView = marker.findViewById(R.id.iv_marker);
        imageView.setImageResource(R.drawable.ic_map_marker);
        CommonUtils.setInterpolation(context, imageView);
       /* TextView txt_name = (TextView)marker.findViewById(R.id.time);
        TextView txt_text = (TextView)marker.findViewById(R.id.time_min);
         txt_name.setText(time);
        txt_text.setText(text);*/

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

    private void openGPS() {
        new GpsUtils(context).turnGPSOn(new GpsUtils.onGpsListener() {
            @Override
            public void gpsStatus(boolean isGPSEnable) {
                // turn on GPS
                isGPS = isGPSEnable;
                //createLocationCallback();

            }
        });
    }

}
