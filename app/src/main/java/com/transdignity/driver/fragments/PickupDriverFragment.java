package com.transdignity.driver.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.transdignity.driver.models.auth.LoginResponse;
import com.transdignity.driver.models.requestService.AllServiceRequestDetailResponse;
import com.transdignity.driver.models.requestService.DecedentDetailsResponse;
import com.transdignity.driver.models.requestService.PickupDriverRequest;
import com.transdignity.driver.models.requestService.PickupDriverResponse;
import com.transdignity.driver.models.requestService.ReachedDriverRequestModel;
import com.transdignity.driver.models.requestService.ReachedDriverResponseModel;
import com.transdignity.driver.models.requestService.RemovalSplDetialsResponse;
import com.transdignity.driver.models.rideDetail.RideDetailResponseModel;
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

import static androidx.constraintlayout.widget.Constraints.TAG;
import static androidx.databinding.DataBindingUtil.inflate;


public class PickupDriverFragment extends Fragment implements View.OnClickListener, OnMapReadyCallback {
    Context context;
    Activity activity;
    private GoogleMap mMap;
    String rs_number = "",  lat, lng, time = "", request_id;
    String nev_lat,nav_lng;
    String rs_address = "", status;
    LoginResponse loginResponse;
    boolean mLocationPermissionGranted = false;
    String userId;
    private boolean isGPS = false;
    TextView tvToolbar,details_header;
    AllServiceRequestDetailResponse allServiceRequestDetailResponse;
    View view;
    TextView tv_dec_name,tv_dec_phone_no,tv_dec_address,tv_drop_location;
    CardView ll_pickup,btn_dropped,btn_navigate,btn__pick_navigate,
            ll_admin_pickup,ll_admin_reached,ll_admin_descendant_pickup,ll_admin_descendant_drop,
            ll_admin_burial_boat;
    LinearLayout ll_navigateview;
    TextView tv_place_holder_pickup,tv_place_holder_drop;
    public PickupDriverFragment() {
        // Required empty public constructor
    }

    public static PickupDriverFragment newInstance(String status) {
        PickupDriverFragment fragment = new PickupDriverFragment();
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
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_pickup_driver, container, false);
        init();
        return view;
    }

    public void init(){
        details_header=view.findViewById(R.id.details_header);
        tv_dec_name=view.findViewById(R.id.tv_dec_name);
        tv_dec_phone_no=view.findViewById(R.id.tv_dec_phone_no);
        tv_dec_address=view.findViewById(R.id.tv_dec_address);
        tv_drop_location=view.findViewById(R.id.tv_drop_location);
        ll_pickup=view.findViewById(R.id.ll_pickup);
        ll_navigateview=view.findViewById(R.id.ll_navigateview);
        btn_dropped=view.findViewById(R.id.btn_dropped);
        btn_navigate=view.findViewById(R.id.btn_navigate);
        tv_place_holder_pickup=view.findViewById(R.id.tv_place_holder_pickup);
        tv_place_holder_drop=view.findViewById(R.id.tv_place_holder_drop);
        btn__pick_navigate=view.findViewById(R.id.btn__pick_navigate);
        ll_admin_pickup=view.findViewById(R.id.ll_admin_pickup);
        ll_admin_reached=view.findViewById(R.id.ll_admin_reached);
        ll_admin_descendant_pickup=view.findViewById(R.id.ll_admin_descendant_pickup);
        ll_admin_descendant_drop=view.findViewById(R.id.ll_admin_descendant_drop);
        ll_admin_burial_boat=view.findViewById(R.id.ll_admin_burial_boat);
        click();
    }

    public void click(){
        ll_pickup.setOnClickListener(this);
        btn_dropped.setOnClickListener(this);
        btn_navigate.setOnClickListener(this);
        btn__pick_navigate.setOnClickListener(this);

        ll_admin_pickup.setOnClickListener(this);
        ll_admin_reached.setOnClickListener(this);
        ll_admin_descendant_pickup.setOnClickListener(this);
        ll_admin_descendant_drop.setOnClickListener(this);
        ll_admin_burial_boat.setOnClickListener(this);
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
            if (status.equalsIgnoreCase("pickup")) {
                ll_pickup.setVisibility(View.VISIBLE);
                ll_navigateview.setVisibility(View.GONE);
            }else if(status.equalsIgnoreCase("navigate")){
                ll_navigateview.setVisibility(View.VISIBLE);
                ll_pickup.setVisibility(View.GONE);
            }
        }

        //getAllServiceRequestDetailApi();
        getDriverAllServiceRequestDetailApi();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_pickup:
                reachedPickupLocationApi();
                break;
                case R.id.btn_dropped:
                    dropRequestApi(userId,"complete",request_id);
                break;
                case R.id.btn_navigate:
                    //getAllServiceRequestDetailApi();
                    getDriverAllServiceRequestDetailApi();
                    CommonUtils.navigateToGMap(context,nev_lat, nav_lng);
                   // status="navigate";
                     break;
                case R.id.btn__pick_navigate:
                    //getAllServiceRequestDetailApi();
                    getDriverAllServiceRequestDetailApi();
                    CommonUtils.navigateToGMap(context,nev_lat, nav_lng);
                   // status="navigate";
                     break;
                case R.id.ll_admin_pickup:
                    pickedRemovalSplApi(userId,request_id);
                     break;
                case R.id.ll_admin_reached:
                    reachedToDecedentApi();
                     break;
                case R.id.ll_admin_descendant_pickup:
                    reachedDescendantPickupLocationApi();
                     break;
               case R.id.ll_admin_descendant_drop:
                   dropDescendantApi(userId, MyConstants.COMPLETE, request_id);
                   break;
            case R.id.ll_admin_burial_boat:
                doneBurialApi(userId,  request_id);
                break;
        }
    }

    //request details api
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
                            tv_dec_address.setText(allServiceRequestDetailResponse.getData().getPickupLocation());
                            tv_drop_location.setText(allServiceRequestDetailResponse.getData().getDropLocation());
                            String decendent_mob_no = allServiceRequestDetailResponse.getData().getMobileNumber();
                            String reciver_mob_no = allServiceRequestDetailResponse.getData().getmRecieverMobileNumber();
                            String decandant_name= allServiceRequestDetailResponse.getData().getFirstName()+" "+allServiceRequestDetailResponse.getData().getLastName();
                            String str_reciver_name= allServiceRequestDetailResponse.getData().getmRecieverFirstName()+" "+allServiceRequestDetailResponse.getData().getmRecieverLastName();
                            String dec_first_name = allServiceRequestDetailResponse.getData().getFirstName();
                            if(dec_first_name==null){
                                tv_dec_name.setText(str_reciver_name);
                                tv_dec_phone_no.setText(reciver_mob_no);

                            }else {
                                tv_dec_name.setText(decandant_name);
                                tv_dec_phone_no.setText(decendent_mob_no);

                            }
                            String service_id = allServiceRequestDetailResponse.getData().getServiceId();
                            String pickup_lat_long=allServiceRequestDetailResponse.getData().getPickupLocationLatlong();
                            String drop_lat_long=allServiceRequestDetailResponse.getData().getDropLocationLatlong();
                            String[] lat_long = pickup_lat_long.split(",");
                            String[] droplat_long = drop_lat_long.split(",");
                            if(status.equalsIgnoreCase("pickup")){
                                lat = lat_long [0];
                                lng = lat_long [1];
                                markerDisplayOnMap(Double.valueOf(lat), Double.valueOf(lng));

                            }else if(status.equalsIgnoreCase("navigate")){
                                nev_lat = droplat_long [0];
                                nav_lng = droplat_long [1];
                                markerDisplayOnMap(Double.valueOf(nev_lat), Double.valueOf(nav_lng));

                            }else {
                                lat = lat_long [0];
                                lng = lat_long [1];
                                markerDisplayOnMap(Double.valueOf(lat), Double.valueOf(lng));
                            }

                            String status=allServiceRequestDetailResponse.getData().getDspStatus();
                            if(status.equals("Ongoing")){

                            }
                          /*  if(service_id.equals("2")){
                                binding.llOtherService.setVisibility(View.VISIBLE);
                                binding.llForDescendantService.setVisibility(View.GONE);
                                binding.tvReached.setText("Reached On Pick up Location");
                            }else if(service_id.equals("1")){
                                binding.llForDescendantService.setVisibility(View.VISIBLE);

                                binding.llOtherService.setVisibility(View.GONE);

                            }else {
                                binding.llForDescendantService.setVisibility(View.VISIBLE);
                                binding.llOtherService.setVisibility(View.VISIBLE);

                            }*/
                            /* rs_lat = removalSplDetialsResponse.getData().getLatitude();
                            rs_long = removalSplDetialsResponse.getData().getLongitude();*/
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

    // pickup driver api
    void reachedPickupLocationApi() {


        final LoadingProgressDialog dialog = new LoadingProgressDialog(getActivity(), "Please wait...");
        dialog.setCancelable(false);
        dialog.show();

        ApiInterface apiInterface = ApiClients.getConnection(getActivity());
        PickupDriverRequest request = new PickupDriverRequest();
        request.setUserId(userId);
        request.setRequestId(request_id);
        request.setStatus("accept");


        Call<PickupDriverResponse> call = apiInterface.pickupDriverApi(loginResponse.getData().getToken(), request);
        call.enqueue(new Callback<PickupDriverResponse>() {
            @Override
            public void onResponse(Call<PickupDriverResponse> call, Response<PickupDriverResponse> response) {
                try {
                    dialog.dismiss();
                    if (response.code() >= 200 && response.code() <= 210) {
                        if (response.body().getSuccess().equalsIgnoreCase("true")) {
                            Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            status="descendant1";
                            getDriverAllServiceRequestDetailApi();
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
            public void onFailure(Call<PickupDriverResponse> call, Throwable t) {
                dialog.dismiss();
                Log.e(TAG, "onResponse: " + t.getMessage());
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //driver droped api
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



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        updateLocationUI();
        if (lat != null && lng != null)
            markerDisplayOnMap(Double.valueOf(lat), Double.valueOf(lng));
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

    public static void navigateToGMap(Context context, String latitude, String longitude) {
        String strUri = "google.navigation:q= " + latitude + "," + longitude;
        // String strUri = "http://maps.google.com/maps?q=loc:" + latitude + "," + longitude + " (" + "Label which you want" + ")";
        Intent mapIntent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(strUri));
        mapIntent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
        if (mapIntent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(mapIntent);
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


                                if(status.equals("removal")){
                                    details_header.setText("Removal Specialist Details");
                                    tv_place_holder_pickup.setText("Pickup Location :");
                                    tv_place_holder_drop.setVisibility(View.GONE);
                                    ll_pickup.setVisibility(View.GONE);
                                    ll_admin_pickup.setVisibility(View.VISIBLE);

                                    tv_dec_name.setText(response.body().getData().getRsName());
                                    tv_dec_phone_no.setText(response.body().getData().getRsPhone());
                                    tv_dec_address.setText(response.body().getData().getRemovalSpecialistPickupLocation());
                                    String removed_from_address=response.body().getData().getPickupLocation();
                                    String transferred_to_address=response.body().getData().getDropLocation();
                                    String pickup_lat_long=response.body().getData().getPickupLocationLatlong();
                                    String drop_lat_long=response.body().getData().getDropLocationLatlong();
                                    String[] lat_long = pickup_lat_long.split(",");
                                    String[] droplat_long = drop_lat_long.split(",");
                                    if(status.equalsIgnoreCase("removal")){

                                        lat = response.body().getData().getRsLatitude();
                                        lng = response.body().getData().getRsLongitude();
                                        markerDisplayOnMap(Double.valueOf(lat), Double.valueOf(lng));


                                        nev_lat = response.body().getData().getRsLatitude();
                                        nav_lng = response.body().getData().getRsLongitude();
                                        markerDisplayOnMap(Double.valueOf(nev_lat), Double.valueOf(nav_lng));

                                    }else if(status.equalsIgnoreCase("removal")){

                                    }else {
                                        lat = lat_long [0];
                                        lng = lat_long [1];
                                        markerDisplayOnMap(Double.valueOf(lat), Double.valueOf(lng));
                                    }
                                }
                                else if(status.equals("descendant")){
                                    details_header.setText("Descendent Details");
                                    tv_place_holder_pickup.setText("Pickup Location :");
                                    tv_place_holder_drop.setVisibility(View.VISIBLE);
                                    ll_pickup.setVisibility(View.GONE);
                                    ll_admin_pickup.setVisibility(View.GONE);
                                    ll_admin_reached.setVisibility(View.VISIBLE);

                                    tv_dec_name.setText(response.body().getData().getDecendentFirstName());
                                    tv_dec_phone_no.setText(response.body().getData().getDecendantMobileNumber());
                                    tv_dec_address.setText(response.body().getData().getPickupLocation());
                                    tv_drop_location.setText(response.body().getData().getDropLocation());

                                    String removed_from_address=response.body().getData().getPickupLocation();
                                    String transferred_to_address=response.body().getData().getDropLocation();
                                    String pickup_lat_long=response.body().getData().getPickupLocationLatlong();
                                    String drop_lat_long=response.body().getData().getDropLocationLatlong();
                                    String[] lat_long = pickup_lat_long.split(",");
                                    String[] droplat_long = drop_lat_long.split(",");
                                    if(status.equalsIgnoreCase("descendant")){
                                        lat = lat_long [0];
                                        lng = lat_long [1];
                                        markerDisplayOnMap(Double.valueOf(lat), Double.valueOf(lng));

                                        nev_lat = lat_long [0];
                                        nav_lng = lat_long [1];
                                        markerDisplayOnMap(Double.valueOf(nev_lat), Double.valueOf(nav_lng));

                                    }else if(status.equalsIgnoreCase("navigate")){

                                    }else {
                                        lat = lat_long [0];
                                        lng = lat_long [1];
                                        markerDisplayOnMap(Double.valueOf(lat), Double.valueOf(lng));
                                    }
                                }
                                else if(status.equals("descendant1")){
                                    details_header.setText("Descendent Details");
                                    tv_place_holder_pickup.setText("Pickup Location :");
                                    tv_place_holder_drop.setVisibility(View.VISIBLE);
                                    ll_pickup.setVisibility(View.GONE);
                                    ll_admin_pickup.setVisibility(View.GONE);
                                    ll_admin_reached.setVisibility(View.GONE);
                                    ll_admin_descendant_pickup.setVisibility(View.VISIBLE);


                                    tv_dec_name.setText(response.body().getData().getDecendentFirstName());
                                    tv_dec_phone_no.setText(response.body().getData().getDecendantMobileNumber());
                                    tv_dec_address.setText(response.body().getData().getPickupLocation());
                                    tv_drop_location.setText(response.body().getData().getDropLocation());

                                    String removed_from_address=response.body().getData().getPickupLocation();
                                    String transferred_to_address=response.body().getData().getDropLocation();
                                    String pickup_lat_long=response.body().getData().getPickupLocationLatlong();
                                    String drop_lat_long=response.body().getData().getDropLocationLatlong();
                                    String[] lat_long = pickup_lat_long.split(",");
                                    String[] droplat_long = drop_lat_long.split(",");
                                    if(status.equalsIgnoreCase("descendant1")){
                                        lat = lat_long [0];
                                        lng = lat_long [1];
                                        markerDisplayOnMap(Double.valueOf(lat), Double.valueOf(lng));

                                        nev_lat = lat_long [0];
                                        nav_lng = lat_long [1];
                                        markerDisplayOnMap(Double.valueOf(nev_lat), Double.valueOf(nav_lng));

                                    }else if(status.equalsIgnoreCase("navigate")){

                                    }else {
                                        lat = lat_long [0];
                                        lng = lat_long [1];
                                        markerDisplayOnMap(Double.valueOf(lat), Double.valueOf(lng));
                                    }
                                }
                                else if(status.equals("descendant2")){
                                    details_header.setText("Descendent Details");
                                    tv_place_holder_pickup.setText("Pickup Location :");
                                    tv_place_holder_drop.setVisibility(View.VISIBLE);
                                    ll_pickup.setVisibility(View.GONE);
                                    ll_admin_pickup.setVisibility(View.GONE);
                                    ll_admin_reached.setVisibility(View.GONE);
                                    ll_admin_descendant_pickup.setVisibility(View.GONE);
                                    ll_admin_descendant_drop.setVisibility(View.VISIBLE);


                                    tv_dec_name.setText(response.body().getData().getDecendentFirstName());
                                    tv_dec_phone_no.setText(response.body().getData().getDecendantMobileNumber());
                                    tv_dec_address.setText(response.body().getData().getPickupLocation());
                                    tv_drop_location.setText(response.body().getData().getDropLocation());

                                    String removed_from_address=response.body().getData().getPickupLocation();
                                    String transferred_to_address=response.body().getData().getDropLocation();
                                    String pickup_lat_long=response.body().getData().getPickupLocationLatlong();
                                    String drop_lat_long=response.body().getData().getDropLocationLatlong();
                                    String[] lat_long = pickup_lat_long.split(",");
                                    String[] droplat_long = drop_lat_long.split(",");
                                    if(status.equalsIgnoreCase("descendant2")){
                                        lat = droplat_long [0];
                                        lng = droplat_long [1];
                                        markerDisplayOnMap(Double.valueOf(lat), Double.valueOf(lng));

                                        nev_lat = droplat_long [0];
                                        nav_lng = droplat_long [1];
                                        markerDisplayOnMap(Double.valueOf(nev_lat), Double.valueOf(nav_lng));

                                    }else if(status.equalsIgnoreCase("navigate")){

                                    }else {
                                        lat = lat_long [0];
                                        lng = lat_long [1];
                                        markerDisplayOnMap(Double.valueOf(lat), Double.valueOf(lng));
                                    }
                                }

                            }
                            else if(service_id.equals("2")){
                                if(status.equals("descendant")){
                                    details_header.setText("Descendent Details");
                                    tv_place_holder_pickup.setText("Pickup Location :");
                                    tv_place_holder_drop.setVisibility(View.VISIBLE);
                                    ll_pickup.setVisibility(View.VISIBLE);
                                    tv_dec_name.setText(response.body().getData().getDecendentFirstName());
                                    tv_dec_phone_no.setText(response.body().getData().getDecendantMobileNumber());
                                    tv_dec_address.setText(response.body().getData().getPickupLocation());
                                    tv_drop_location.setText(response.body().getData().getDropLocation());
                                    String removed_from_address=response.body().getData().getPickupLocation();
                                    String transferred_to_address=response.body().getData().getDropLocation();
                                    String pickup_lat_long=response.body().getData().getPickupLocationLatlong();
                                    String drop_lat_long=response.body().getData().getDropLocationLatlong();
                                    String[] lat_long = pickup_lat_long.split(",");
                                    String[] droplat_long = drop_lat_long.split(",");
                                    if(status.equalsIgnoreCase("descendant")){

                                        lat = lat_long [0];
                                        lng = lat_long [1];
                                        markerDisplayOnMap(Double.valueOf(lat), Double.valueOf(lng));

                                        nev_lat = lat_long [0];
                                        nav_lng = lat_long [1];
                                        markerDisplayOnMap(Double.valueOf(nev_lat), Double.valueOf(nav_lng));

                                    }
                                }
                                else if(status.equals("descendant1")){
                                    details_header.setText("Descendent Details");
                                    tv_place_holder_pickup.setText("Pickup Location :");
                                    tv_place_holder_drop.setVisibility(View.VISIBLE);
                                    ll_pickup.setVisibility(View.GONE);
                                    btn_dropped.setVisibility(View.VISIBLE);
                                    tv_dec_name.setText(response.body().getData().getDecendentFirstName());
                                    tv_dec_phone_no.setText(response.body().getData().getDecendantMobileNumber());
                                    tv_dec_address.setText(response.body().getData().getPickupLocation());
                                    tv_drop_location.setText(response.body().getData().getDropLocation());
                                    String removed_from_address=response.body().getData().getPickupLocation();
                                    String transferred_to_address=response.body().getData().getDropLocation();
                                    String pickup_lat_long=response.body().getData().getPickupLocationLatlong();
                                    String drop_lat_long=response.body().getData().getDropLocationLatlong();
                                    String[] lat_long = pickup_lat_long.split(",");
                                    String[] droplat_long = drop_lat_long.split(",");
                                    if(status.equalsIgnoreCase("descendant1")){

                                        lat = droplat_long [0];
                                        lng = droplat_long [1];
                                        markerDisplayOnMap(Double.valueOf(lat), Double.valueOf(lng));

                                        nev_lat = droplat_long [0];
                                        nav_lng = droplat_long [1];
                                        markerDisplayOnMap(Double.valueOf(nev_lat), Double.valueOf(nav_lng));

                                    }
                                }
                            }
                            else if(service_id.equals("3")){
                                if(status.equals("descendant")){
                                    details_header.setText("Sender Details");
                                    tv_place_holder_pickup.setText("Pickup Location :");
                                    tv_place_holder_drop.setVisibility(View.VISIBLE);
                                    ll_pickup.setVisibility(View.VISIBLE);
                                    tv_dec_name.setText(response.body().getData().getDecendentFirstName());
                                    tv_dec_phone_no.setText(response.body().getData().getDecendantMobileNumber());
                                    tv_dec_address.setText(response.body().getData().getPickupLocation());
                                    tv_drop_location.setText(response.body().getData().getDropLocation());
                                    String removed_from_address=response.body().getData().getPickupLocation();
                                    String transferred_to_address=response.body().getData().getDropLocation();
                                    String pickup_lat_long=response.body().getData().getPickupLocationLatlong();
                                    String drop_lat_long=response.body().getData().getDropLocationLatlong();
                                    String[] lat_long = pickup_lat_long.split(",");
                                    String[] droplat_long = drop_lat_long.split(",");
                                    if(status.equalsIgnoreCase("descendant")){

                                        lat = lat_long [0];
                                        lng = lat_long [1];
                                        markerDisplayOnMap(Double.valueOf(lat), Double.valueOf(lng));

                                        nev_lat = lat_long [0];
                                        nav_lng = lat_long [1];
                                        markerDisplayOnMap(Double.valueOf(nev_lat), Double.valueOf(nav_lng));

                                    }
                                }
                                else if(status.equals("descendant1")){
                                    details_header.setText("Receiver Details");
                                    tv_place_holder_pickup.setText("Pickup Location :");
                                    tv_place_holder_drop.setVisibility(View.VISIBLE);
                                    ll_pickup.setVisibility(View.GONE);
                                    btn_dropped.setVisibility(View.VISIBLE);
                                    tv_dec_name.setText(response.body().getData().getRecieverFirstName());
                                    tv_dec_phone_no.setText(response.body().getData().getRecieverMobileNumber());
                                    tv_dec_address.setText(response.body().getData().getPickupLocation());
                                    tv_drop_location.setText(response.body().getData().getDropLocation());
                                    String removed_from_address=response.body().getData().getPickupLocation();
                                    String transferred_to_address=response.body().getData().getDropLocation();
                                    String pickup_lat_long=response.body().getData().getPickupLocationLatlong();
                                    String drop_lat_long=response.body().getData().getDropLocationLatlong();
                                    String[] lat_long = pickup_lat_long.split(",");
                                    String[] droplat_long = drop_lat_long.split(",");
                                    if(status.equalsIgnoreCase("descendant1")){

                                        lat = droplat_long [0];
                                        lng = droplat_long [1];
                                        markerDisplayOnMap(Double.valueOf(lat), Double.valueOf(lng));

                                        nev_lat = droplat_long [0];
                                        nav_lng = droplat_long [1];
                                        markerDisplayOnMap(Double.valueOf(nev_lat), Double.valueOf(nav_lng));

                                    }
                                }

                            }
                            else if(service_id.equals("4")){
                                if(status.equals("descendant")){
                                    details_header.setText("Descendent Details");
                                    tv_place_holder_pickup.setText("Pickup Location :");
                                    tv_place_holder_drop.setVisibility(View.VISIBLE);
                                    ll_pickup.setVisibility(View.VISIBLE);
                                    tv_dec_name.setText(response.body().getData().getDecendentFirstName());
                                    tv_dec_phone_no.setText(response.body().getData().getDecendantMobileNumber());
                                    tv_dec_address.setText(response.body().getData().getPickupLocation());
                                    tv_drop_location.setText(response.body().getData().getDropLocation());
                                    String removed_from_address=response.body().getData().getPickupLocation();
                                    String transferred_to_address=response.body().getData().getDropLocation();
                                    String pickup_lat_long=response.body().getData().getPickupLocationLatlong();
                                    String drop_lat_long=response.body().getData().getDropLocationLatlong();
                                    String[] lat_long = pickup_lat_long.split(",");
                                    String[] droplat_long = drop_lat_long.split(",");
                                    if(status.equalsIgnoreCase("descendant")){

                                        lat = lat_long [0];
                                        lng = lat_long [1];
                                        markerDisplayOnMap(Double.valueOf(lat), Double.valueOf(lng));

                                        nev_lat = lat_long [0];
                                        nav_lng = lat_long [1];
                                        markerDisplayOnMap(Double.valueOf(nev_lat), Double.valueOf(nav_lng));

                                    }
                                }
                                else if(status.equals("descendant1")){
                                    details_header.setText("Descendent Details");
                                    tv_place_holder_pickup.setText("Pickup Location :");
                                    tv_place_holder_drop.setVisibility(View.VISIBLE);
                                    ll_pickup.setVisibility(View.GONE);
                                    btn_dropped.setVisibility(View.VISIBLE);
                                    tv_dec_name.setText(response.body().getData().getDecendentFirstName());
                                    tv_dec_phone_no.setText(response.body().getData().getDecendantMobileNumber());
                                    tv_dec_address.setText(response.body().getData().getPickupLocation());
                                    tv_drop_location.setText(response.body().getData().getDropLocation());
                                    String removed_from_address=response.body().getData().getPickupLocation();
                                    String transferred_to_address=response.body().getData().getDropLocation();
                                    String pickup_lat_long=response.body().getData().getPickupLocationLatlong();
                                    String drop_lat_long=response.body().getData().getDropLocationLatlong();
                                    String[] lat_long = pickup_lat_long.split(",");
                                    String[] droplat_long = drop_lat_long.split(",");
                                    if(status.equalsIgnoreCase("descendant1")){

                                        lat = droplat_long [0];
                                        lng = droplat_long [1];
                                        markerDisplayOnMap(Double.valueOf(lat), Double.valueOf(lng));

                                        nev_lat = droplat_long [0];
                                        nav_lng = droplat_long [1];
                                        markerDisplayOnMap(Double.valueOf(nev_lat), Double.valueOf(nav_lng));

                                    }
                                }
                            }
                            else if(service_id.equals("5")){
                                if(status.equals("removal")){
                                    details_header.setText("Removal Specialist Details");
                                    tv_place_holder_pickup.setText("Pickup Location :");
                                    tv_place_holder_drop.setVisibility(View.GONE);
                                    ll_pickup.setVisibility(View.GONE);
                                    ll_admin_pickup.setVisibility(View.VISIBLE);

                                    tv_dec_name.setText(response.body().getData().getRsName());
                                    tv_dec_phone_no.setText(response.body().getData().getRsPhone());
                                    tv_dec_address.setText(response.body().getData().getRemovalSpecialistPickupLocation());
                                    String removed_from_address=response.body().getData().getPickupLocation();
                                    String transferred_to_address=response.body().getData().getDropLocation();
                                    String pickup_lat_long=response.body().getData().getPickupLocationLatlong();
                                    String drop_lat_long=response.body().getData().getDropLocationLatlong();
                                    String[] lat_long = pickup_lat_long.split(",");
                                    String[] droplat_long = drop_lat_long.split(",");
                                    if(status.equalsIgnoreCase("removal")){

                                        lat = response.body().getData().getRsLatitude();
                                        lng = response.body().getData().getRsLongitude();
                                        markerDisplayOnMap(Double.valueOf(lat), Double.valueOf(lng));


                                        nev_lat = response.body().getData().getRsLatitude();
                                        nav_lng = response.body().getData().getRsLongitude();
                                        markerDisplayOnMap(Double.valueOf(nev_lat), Double.valueOf(nav_lng));

                                    }else if(status.equalsIgnoreCase("removal")){

                                    }else {
                                        lat = lat_long [0];
                                        lng = lat_long [1];
                                        markerDisplayOnMap(Double.valueOf(lat), Double.valueOf(lng));
                                    }
                                }
                                else if(status.equals("descendant")){
                                    details_header.setText("Descendent Details");
                                    tv_place_holder_pickup.setText("Pickup Location :");
                                    tv_place_holder_drop.setVisibility(View.VISIBLE);
                                    ll_pickup.setVisibility(View.GONE);
                                    ll_admin_pickup.setVisibility(View.GONE);
                                    ll_admin_reached.setVisibility(View.VISIBLE);

                                    tv_dec_name.setText(response.body().getData().getDecendentFirstName());
                                    tv_dec_phone_no.setText(response.body().getData().getDecendantMobileNumber());
                                    tv_dec_address.setText(response.body().getData().getPickupLocation());
                                    tv_drop_location.setText(response.body().getData().getDropLocation());

                                    String removed_from_address=response.body().getData().getPickupLocation();
                                    String transferred_to_address=response.body().getData().getDropLocation();
                                    String pickup_lat_long=response.body().getData().getPickupLocationLatlong();
                                    String drop_lat_long=response.body().getData().getDropLocationLatlong();
                                    String[] lat_long = pickup_lat_long.split(",");
                                    String[] droplat_long = drop_lat_long.split(",");
                                    if(status.equalsIgnoreCase("descendant")){
                                        lat = lat_long [0];
                                        lng = lat_long [1];
                                        markerDisplayOnMap(Double.valueOf(lat), Double.valueOf(lng));

                                        nev_lat = lat_long [0];
                                        nav_lng = lat_long [1];
                                        markerDisplayOnMap(Double.valueOf(nev_lat), Double.valueOf(nav_lng));

                                    }else if(status.equalsIgnoreCase("navigate")){

                                    }else {
                                        lat = lat_long [0];
                                        lng = lat_long [1];
                                        markerDisplayOnMap(Double.valueOf(lat), Double.valueOf(lng));
                                    }
                                }
                                else if(status.equals("descendant1")){
                                    details_header.setText("Descendent Details");
                                    tv_place_holder_pickup.setText("Pickup Location :");
                                    tv_place_holder_drop.setVisibility(View.VISIBLE);
                                    ll_pickup.setVisibility(View.GONE);
                                    ll_admin_pickup.setVisibility(View.GONE);
                                    ll_admin_reached.setVisibility(View.GONE);
                                    ll_admin_descendant_pickup.setVisibility(View.VISIBLE);


                                    tv_dec_name.setText(response.body().getData().getDecendentFirstName());
                                    tv_dec_phone_no.setText(response.body().getData().getDecendantMobileNumber());
                                    tv_dec_address.setText(response.body().getData().getPickupLocation());
                                    tv_drop_location.setText(response.body().getData().getDropLocation());

                                    String removed_from_address=response.body().getData().getPickupLocation();
                                    String transferred_to_address=response.body().getData().getDropLocation();
                                    String pickup_lat_long=response.body().getData().getPickupLocationLatlong();
                                    String drop_lat_long=response.body().getData().getDropLocationLatlong();
                                    String[] lat_long = pickup_lat_long.split(",");
                                    String[] droplat_long = drop_lat_long.split(",");
                                    if(status.equalsIgnoreCase("descendant1")){
                                        lat = lat_long [0];
                                        lng = lat_long [1];
                                        markerDisplayOnMap(Double.valueOf(lat), Double.valueOf(lng));

                                        nev_lat = lat_long [0];
                                        nav_lng = lat_long [1];
                                        markerDisplayOnMap(Double.valueOf(nev_lat), Double.valueOf(nav_lng));

                                    }else if(status.equalsIgnoreCase("navigate")){

                                    }else {
                                        lat = lat_long [0];
                                        lng = lat_long [1];
                                        markerDisplayOnMap(Double.valueOf(lat), Double.valueOf(lng));
                                    }
                                }
                                else if(status.equals("descendant2")){
                                    details_header.setText("Descendent Details");
                                    tv_place_holder_pickup.setText("Pickup Location :");
                                    tv_place_holder_drop.setVisibility(View.VISIBLE);
                                    ll_pickup.setVisibility(View.GONE);
                                    ll_admin_pickup.setVisibility(View.GONE);
                                    ll_admin_reached.setVisibility(View.GONE);
                                    ll_admin_descendant_pickup.setVisibility(View.GONE);
                                    ll_admin_descendant_drop.setVisibility(View.VISIBLE);


                                    tv_dec_name.setText(response.body().getData().getDecendentFirstName());
                                    tv_dec_phone_no.setText(response.body().getData().getDecendantMobileNumber());
                                    tv_dec_address.setText(response.body().getData().getPickupLocation());
                                    tv_drop_location.setText(response.body().getData().getDropLocation());

                                    String removed_from_address=response.body().getData().getPickupLocation();
                                    String transferred_to_address=response.body().getData().getDropLocation();
                                    String pickup_lat_long=response.body().getData().getPickupLocationLatlong();
                                    String drop_lat_long=response.body().getData().getDropLocationLatlong();
                                    String[] lat_long = pickup_lat_long.split(",");
                                    String[] droplat_long = drop_lat_long.split(",");
                                    if(status.equalsIgnoreCase("descendant2")){
                                        lat = droplat_long [0];
                                        lng = droplat_long [1];
                                        markerDisplayOnMap(Double.valueOf(lat), Double.valueOf(lng));

                                        nev_lat = droplat_long [0];
                                        nav_lng = droplat_long [1];
                                        markerDisplayOnMap(Double.valueOf(nev_lat), Double.valueOf(nav_lng));

                                    }else if(status.equalsIgnoreCase("navigate")){

                                    }else {
                                        lat = lat_long [0];
                                        lng = lat_long [1];
                                        markerDisplayOnMap(Double.valueOf(lat), Double.valueOf(lng));
                                    }
                                }
                            }
                            else if(service_id.equals("6")){
                                if(status.equals("descendant")){
                                    details_header.setText("Sender Details");
                                    tv_place_holder_pickup.setText("Pickup Location :");
                                    tv_place_holder_drop.setVisibility(View.VISIBLE);
                                    ll_pickup.setVisibility(View.VISIBLE);
                                    tv_dec_name.setText(response.body().getData().getDecendentFirstName());
                                    tv_dec_phone_no.setText(response.body().getData().getDecendantMobileNumber());
                                    tv_dec_address.setText(response.body().getData().getPickupLocation());
                                    tv_drop_location.setText(response.body().getData().getDropLocation());
                                    String removed_from_address=response.body().getData().getPickupLocation();
                                    String transferred_to_address=response.body().getData().getDropLocation();
                                    String pickup_lat_long=response.body().getData().getPickupLocationLatlong();
                                    String drop_lat_long=response.body().getData().getDropLocationLatlong();
                                    String[] lat_long = pickup_lat_long.split(",");
                                    String[] droplat_long = drop_lat_long.split(",");
                                    if(status.equalsIgnoreCase("descendant")){

                                        lat = lat_long [0];
                                        lng = lat_long [1];
                                        markerDisplayOnMap(Double.valueOf(lat), Double.valueOf(lng));

                                        nev_lat = lat_long [0];
                                        nav_lng = lat_long [1];
                                        markerDisplayOnMap(Double.valueOf(nev_lat), Double.valueOf(nav_lng));

                                    }
                                }
                                else if(status.equals("descendant1")){
                                    details_header.setText("Receiver Details");
                                    tv_place_holder_pickup.setText("Pickup Location :");
                                    tv_place_holder_drop.setVisibility(View.VISIBLE);
                                    ll_pickup.setVisibility(View.GONE);
                                    btn_dropped.setVisibility(View.VISIBLE);
                                    tv_dec_name.setText(response.body().getData().getRecieverFirstName());
                                    tv_dec_phone_no.setText(response.body().getData().getRecieverMobileNumber());
                                    tv_dec_address.setText(response.body().getData().getPickupLocation());
                                    tv_drop_location.setText(response.body().getData().getDropLocation());
                                    String removed_from_address=response.body().getData().getPickupLocation();
                                    String transferred_to_address=response.body().getData().getDropLocation();
                                    String pickup_lat_long=response.body().getData().getPickupLocationLatlong();
                                    String drop_lat_long=response.body().getData().getDropLocationLatlong();
                                    String[] lat_long = pickup_lat_long.split(",");
                                    String[] droplat_long = drop_lat_long.split(",");
                                    if(status.equalsIgnoreCase("descendant1")){

                                        lat = droplat_long [0];
                                        lng = droplat_long [1];
                                        markerDisplayOnMap(Double.valueOf(lat), Double.valueOf(lng));

                                        nev_lat = droplat_long [0];
                                        nav_lng = droplat_long [1];
                                        markerDisplayOnMap(Double.valueOf(nev_lat), Double.valueOf(nav_lng));

                                    }
                                }
                            }
                            else if(service_id.equals("7")){
                                if(status.equals("removal")){
                                    details_header.setText("Removal Specialist Details");
                                    tv_place_holder_pickup.setText("Pickup Location :");
                                    tv_place_holder_drop.setVisibility(View.GONE);
                                    ll_pickup.setVisibility(View.GONE);
                                    ll_admin_pickup.setVisibility(View.VISIBLE);

                                    tv_dec_name.setText(response.body().getData().getRsName());
                                    tv_dec_phone_no.setText(response.body().getData().getRsPhone());
                                    tv_dec_address.setText(response.body().getData().getRemovalSpecialistPickupLocation());
                                    String removed_from_address=response.body().getData().getPickupLocation();
                                    String transferred_to_address=response.body().getData().getDropLocation();
                                    String pickup_lat_long=response.body().getData().getPickupLocationLatlong();
                                    String drop_lat_long=response.body().getData().getDropLocationLatlong();
                                    String[] lat_long = pickup_lat_long.split(",");
                                    String[] droplat_long = drop_lat_long.split(",");
                                    if(status.equalsIgnoreCase("removal")){

                                        lat = response.body().getData().getRsLatitude();
                                        lng = response.body().getData().getRsLongitude();
                                        markerDisplayOnMap(Double.valueOf(lat), Double.valueOf(lng));


                                        nev_lat = response.body().getData().getRsLatitude();
                                        nav_lng = response.body().getData().getRsLongitude();
                                        markerDisplayOnMap(Double.valueOf(nev_lat), Double.valueOf(nav_lng));

                                    }else if(status.equalsIgnoreCase("removal")){

                                    }else {
                                        lat = lat_long [0];
                                        lng = lat_long [1];
                                        markerDisplayOnMap(Double.valueOf(lat), Double.valueOf(lng));
                                    }
                                }
                                else if(status.equals("descendant")){
                                    details_header.setText("Descendent Details");
                                    tv_place_holder_pickup.setText("Pickup Location :");
                                    tv_place_holder_drop.setVisibility(View.VISIBLE);
                                    ll_pickup.setVisibility(View.GONE);
                                    ll_admin_pickup.setVisibility(View.GONE);
                                    ll_admin_reached.setVisibility(View.VISIBLE);

                                    tv_dec_name.setText(response.body().getData().getDecendentFirstName());
                                    tv_dec_phone_no.setText(response.body().getData().getDecendantMobileNumber());
                                    tv_dec_address.setText(response.body().getData().getPickupLocation());
                                    tv_drop_location.setText(response.body().getData().getDropLocation());

                                    String removed_from_address=response.body().getData().getPickupLocation();
                                    String transferred_to_address=response.body().getData().getDropLocation();
                                    String pickup_lat_long=response.body().getData().getPickupLocationLatlong();
                                    String drop_lat_long=response.body().getData().getDropLocationLatlong();
                                    String[] lat_long = pickup_lat_long.split(",");
                                    String[] droplat_long = drop_lat_long.split(",");
                                    if(status.equalsIgnoreCase("descendant")){
                                        lat = lat_long [0];
                                        lng = lat_long [1];
                                        markerDisplayOnMap(Double.valueOf(lat), Double.valueOf(lng));

                                        nev_lat = lat_long [0];
                                        nav_lng = lat_long [1];
                                        markerDisplayOnMap(Double.valueOf(nev_lat), Double.valueOf(nav_lng));

                                    }else if(status.equalsIgnoreCase("navigate")){

                                    }else {
                                        lat = lat_long [0];
                                        lng = lat_long [1];
                                        markerDisplayOnMap(Double.valueOf(lat), Double.valueOf(lng));
                                    }
                                }
                                else if(status.equals("descendant1")){
                                    details_header.setText("Descendent Details");
                                    tv_place_holder_pickup.setText("Pickup Location :");
                                    tv_place_holder_drop.setVisibility(View.VISIBLE);
                                    ll_pickup.setVisibility(View.GONE);
                                    ll_admin_pickup.setVisibility(View.GONE);
                                    ll_admin_reached.setVisibility(View.GONE);
                                    ll_admin_descendant_pickup.setVisibility(View.VISIBLE);


                                    tv_dec_name.setText(response.body().getData().getDecendentFirstName());
                                    tv_dec_phone_no.setText(response.body().getData().getDecendantMobileNumber());
                                    tv_dec_address.setText(response.body().getData().getPickupLocation());
                                    tv_drop_location.setText(response.body().getData().getDropLocation());

                                    String removed_from_address=response.body().getData().getPickupLocation();
                                    String transferred_to_address=response.body().getData().getDropLocation();
                                    String pickup_lat_long=response.body().getData().getPickupLocationLatlong();
                                    String drop_lat_long=response.body().getData().getDropLocationLatlong();
                                    String[] lat_long = pickup_lat_long.split(",");
                                    String[] droplat_long = drop_lat_long.split(",");
                                    if(status.equalsIgnoreCase("descendant1")){
                                        lat = lat_long [0];
                                        lng = lat_long [1];
                                        markerDisplayOnMap(Double.valueOf(lat), Double.valueOf(lng));

                                        nev_lat = lat_long [0];
                                        nav_lng = lat_long [1];
                                        markerDisplayOnMap(Double.valueOf(nev_lat), Double.valueOf(nav_lng));

                                    }else if(status.equalsIgnoreCase("navigate")){

                                    }else {
                                        lat = lat_long [0];
                                        lng = lat_long [1];
                                        markerDisplayOnMap(Double.valueOf(lat), Double.valueOf(lng));
                                    }
                                }
                                else if(status.equals("descendant2")){
                                    details_header.setText("Descendent Details");
                                    tv_place_holder_pickup.setText("Pickup Location :");
                                    tv_place_holder_drop.setVisibility(View.VISIBLE);
                                    ll_pickup.setVisibility(View.GONE);
                                    ll_admin_pickup.setVisibility(View.GONE);
                                    ll_admin_reached.setVisibility(View.GONE);
                                    ll_admin_descendant_pickup.setVisibility(View.GONE);
                                    ll_admin_descendant_drop.setVisibility(View.VISIBLE);


                                    tv_dec_name.setText(response.body().getData().getDecendentFirstName());
                                    tv_dec_phone_no.setText(response.body().getData().getDecendantMobileNumber());
                                    tv_dec_address.setText(response.body().getData().getPickupLocation());
                                    tv_drop_location.setText(response.body().getData().getDropLocation());

                                    String removed_from_address=response.body().getData().getPickupLocation();
                                    String transferred_to_address=response.body().getData().getDropLocation();
                                    String pickup_lat_long=response.body().getData().getPickupLocationLatlong();
                                    String drop_lat_long=response.body().getData().getDropLocationLatlong();
                                    String[] lat_long = pickup_lat_long.split(",");
                                    String[] droplat_long = drop_lat_long.split(",");
                                    if(status.equalsIgnoreCase("descendant2")){
                                        lat = droplat_long [0];
                                        lng = droplat_long [1];
                                        markerDisplayOnMap(Double.valueOf(lat), Double.valueOf(lng));

                                        nev_lat = droplat_long [0];
                                        nav_lng = droplat_long [1];
                                        markerDisplayOnMap(Double.valueOf(nev_lat), Double.valueOf(nav_lng));

                                    }else if(status.equalsIgnoreCase("navigate")){

                                    }else {
                                        lat = lat_long [0];
                                        lng = lat_long [1];
                                        markerDisplayOnMap(Double.valueOf(lat), Double.valueOf(lng));
                                    }
                                }
                            }
                            else if(service_id.equals("8")){
                                if(status.equals("removal")){
                                    details_header.setText("REMOVAL SPECIALIST DETAILS");
                                    tv_place_holder_pickup.setText("Pickup Location :");
                                    tv_place_holder_drop.setVisibility(View.GONE);
                                    ll_pickup.setVisibility(View.GONE);
                                    ll_admin_pickup.setVisibility(View.VISIBLE);

                                    tv_dec_name.setText(response.body().getData().getRsName());
                                    tv_dec_phone_no.setText(response.body().getData().getRsPhone());
                                    tv_dec_address.setText(response.body().getData().getRemovalSpecialistPickupLocation());
                                    String removed_from_address=response.body().getData().getPickupLocation();
                                    String transferred_to_address=response.body().getData().getDropLocation();
                                    String pickup_lat_long=response.body().getData().getPickupLocationLatlong();
                                    String drop_lat_long=response.body().getData().getDropLocationLatlong();
                                    String[] lat_long = pickup_lat_long.split(",");
                                    String[] droplat_long = drop_lat_long.split(",");
                                    if(status.equalsIgnoreCase("removal")){

                                        lat = response.body().getData().getRsLatitude();
                                        lng = response.body().getData().getRsLongitude();
                                        markerDisplayOnMap(Double.valueOf(lat), Double.valueOf(lng));


                                        nev_lat = response.body().getData().getRsLatitude();
                                        nav_lng = response.body().getData().getRsLongitude();
                                        markerDisplayOnMap(Double.valueOf(nev_lat), Double.valueOf(nav_lng));

                                    }else if(status.equalsIgnoreCase("removal")){

                                    }else {
                                        lat = lat_long [0];
                                        lng = lat_long [1];
                                        markerDisplayOnMap(Double.valueOf(lat), Double.valueOf(lng));
                                    }
                                }
                                else if(status.equals("descendant")){
                                    details_header.setText("Descendent Details");
                                    tv_place_holder_pickup.setText("Pickup Location :");
                                    tv_place_holder_drop.setVisibility(View.VISIBLE);
                                    ll_pickup.setVisibility(View.GONE);
                                    ll_admin_pickup.setVisibility(View.GONE);
                                    ll_admin_reached.setVisibility(View.VISIBLE);

                                    tv_dec_name.setText(response.body().getData().getDecendentFirstName());
                                    tv_dec_phone_no.setText(response.body().getData().getDecendantMobileNumber());
                                    tv_dec_address.setText(response.body().getData().getPickupLocation());
                                    tv_drop_location.setText(response.body().getData().getDropLocation());

                                    String removed_from_address=response.body().getData().getPickupLocation();
                                    String transferred_to_address=response.body().getData().getDropLocation();
                                    String pickup_lat_long=response.body().getData().getPickupLocationLatlong();
                                    String drop_lat_long=response.body().getData().getDropLocationLatlong();
                                    String[] lat_long = pickup_lat_long.split(",");
                                    String[] droplat_long = drop_lat_long.split(",");
                                    if(status.equalsIgnoreCase("descendant")){
                                        lat = lat_long [0];
                                        lng = lat_long [1];
                                        markerDisplayOnMap(Double.valueOf(lat), Double.valueOf(lng));

                                        nev_lat = lat_long [0];
                                        nav_lng = lat_long [1];
                                        markerDisplayOnMap(Double.valueOf(nev_lat), Double.valueOf(nav_lng));

                                    }else if(status.equalsIgnoreCase("navigate")){

                                    }else {
                                        lat = lat_long [0];
                                        lng = lat_long [1];
                                        markerDisplayOnMap(Double.valueOf(lat), Double.valueOf(lng));
                                    }
                                }
                                else if(status.equals("descendant1")){
                                    details_header.setText("Descendent Details");
                                    tv_place_holder_pickup.setText("Pickup Location :");
                                    tv_place_holder_drop.setVisibility(View.VISIBLE);
                                    ll_pickup.setVisibility(View.GONE);
                                    ll_admin_pickup.setVisibility(View.GONE);
                                    ll_admin_reached.setVisibility(View.GONE);
                                    ll_admin_descendant_pickup.setVisibility(View.VISIBLE);


                                    tv_dec_name.setText(response.body().getData().getDecendentFirstName());
                                    tv_dec_phone_no.setText(response.body().getData().getDecendantMobileNumber());
                                    tv_dec_address.setText(response.body().getData().getPickupLocation());
                                    tv_drop_location.setText(response.body().getData().getDropLocation());

                                    String removed_from_address=response.body().getData().getPickupLocation();
                                    String transferred_to_address=response.body().getData().getDropLocation();
                                    String pickup_lat_long=response.body().getData().getPickupLocationLatlong();
                                    String drop_lat_long=response.body().getData().getDropLocationLatlong();
                                    String[] lat_long = pickup_lat_long.split(",");
                                    String[] droplat_long = drop_lat_long.split(",");
                                    if(status.equalsIgnoreCase("descendant1")){
                                        lat = lat_long [0];
                                        lng = lat_long [1];
                                        markerDisplayOnMap(Double.valueOf(lat), Double.valueOf(lng));

                                        nev_lat = lat_long [0];
                                        nav_lng = lat_long [1];
                                        markerDisplayOnMap(Double.valueOf(nev_lat), Double.valueOf(nav_lng));

                                    }else if(status.equalsIgnoreCase("navigate")){

                                    }else {
                                        lat = lat_long [0];
                                        lng = lat_long [1];
                                        markerDisplayOnMap(Double.valueOf(lat), Double.valueOf(lng));
                                    }
                                }
                                else if(status.equals("descendant2")){
                                    details_header.setText("Descendent Details");
                                    tv_place_holder_pickup.setText("Pickup Location :");
                                    tv_place_holder_drop.setVisibility(View.VISIBLE);
                                    ll_pickup.setVisibility(View.GONE);
                                    ll_admin_pickup.setVisibility(View.GONE);
                                    ll_admin_reached.setVisibility(View.GONE);
                                    ll_admin_descendant_pickup.setVisibility(View.GONE);
                                    ll_admin_descendant_drop.setVisibility(View.VISIBLE);


                                    tv_dec_name.setText(response.body().getData().getDecendentFirstName());
                                    tv_dec_phone_no.setText(response.body().getData().getDecendantMobileNumber());
                                    tv_dec_address.setText(response.body().getData().getPickupLocation());
                                    tv_drop_location.setText(response.body().getData().getDropLocation());

                                    String removed_from_address=response.body().getData().getPickupLocation();
                                    String transferred_to_address=response.body().getData().getDropLocation();
                                    String pickup_lat_long=response.body().getData().getPickupLocationLatlong();
                                    String drop_lat_long=response.body().getData().getDropLocationLatlong();
                                    String[] lat_long = pickup_lat_long.split(",");
                                    String[] droplat_long = drop_lat_long.split(",");
                                    if(status.equalsIgnoreCase("descendant2")){
                                        lat = droplat_long [0];
                                        lng = droplat_long [1];
                                        markerDisplayOnMap(Double.valueOf(lat), Double.valueOf(lng));

                                        nev_lat = droplat_long [0];
                                        nav_lng = droplat_long [1];
                                        markerDisplayOnMap(Double.valueOf(nev_lat), Double.valueOf(nav_lng));

                                    }else if(status.equalsIgnoreCase("navigate")){

                                    }else {
                                        lat = lat_long [0];
                                        lng = lat_long [1];
                                        markerDisplayOnMap(Double.valueOf(lat), Double.valueOf(lng));
                                    }
                                }
                            }
                            else if(service_id.equals("9")){
                                if(status.equals("removal")){
                                    details_header.setText("REMOVAL SPECIALIST DETAILS");
                                    tv_place_holder_pickup.setText("Pickup Location :");
                                    tv_place_holder_drop.setVisibility(View.GONE);
                                    ll_pickup.setVisibility(View.GONE);
                                    ll_admin_pickup.setVisibility(View.VISIBLE);

                                    tv_dec_name.setText(response.body().getData().getRsName());
                                    tv_dec_phone_no.setText(response.body().getData().getRsPhone());
                                    tv_dec_address.setText(response.body().getData().getRemovalSpecialistPickupLocation());
                                    String removed_from_address=response.body().getData().getPickupLocation();
                                    String transferred_to_address=response.body().getData().getDropLocation();
                                    String pickup_lat_long=response.body().getData().getPickupLocationLatlong();
                                    String drop_lat_long=response.body().getData().getDropLocationLatlong();
                                    String[] lat_long = pickup_lat_long.split(",");
                                    String[] droplat_long = drop_lat_long.split(",");
                                    if(status.equalsIgnoreCase("removal")){

                                        lat = response.body().getData().getRsLatitude();
                                        lng = response.body().getData().getRsLongitude();
                                        markerDisplayOnMap(Double.valueOf(lat), Double.valueOf(lng));


                                        nev_lat = response.body().getData().getRsLatitude();
                                        nav_lng = response.body().getData().getRsLongitude();
                                        markerDisplayOnMap(Double.valueOf(nev_lat), Double.valueOf(nav_lng));

                                    }else if(status.equalsIgnoreCase("removal")){

                                    }else {
                                        lat = lat_long [0];
                                        lng = lat_long [1];
                                        markerDisplayOnMap(Double.valueOf(lat), Double.valueOf(lng));
                                    }
                                }
                                else if(status.equals("descendant")){
                                    details_header.setText("Descendent Details");
                                    tv_place_holder_pickup.setText("Pickup Location :");
                                    tv_place_holder_drop.setVisibility(View.VISIBLE);
                                    ll_pickup.setVisibility(View.GONE);
                                    ll_admin_pickup.setVisibility(View.GONE);
                                    ll_admin_reached.setVisibility(View.VISIBLE);

                                    tv_dec_name.setText(response.body().getData().getDecendentFirstName());
                                    tv_dec_phone_no.setText(response.body().getData().getDecendantMobileNumber());
                                    tv_dec_address.setText(response.body().getData().getPickupLocation());
                                    tv_drop_location.setText(response.body().getData().getDropLocation());

                                    String removed_from_address=response.body().getData().getPickupLocation();
                                    String transferred_to_address=response.body().getData().getDropLocation();
                                    String pickup_lat_long=response.body().getData().getPickupLocationLatlong();
                                    String drop_lat_long=response.body().getData().getDropLocationLatlong();
                                    String[] lat_long = pickup_lat_long.split(",");
                                    String[] droplat_long = drop_lat_long.split(",");
                                    if(status.equalsIgnoreCase("descendant")){
                                        lat = lat_long [0];
                                        lng = lat_long [1];
                                        markerDisplayOnMap(Double.valueOf(lat), Double.valueOf(lng));

                                        nev_lat = lat_long [0];
                                        nav_lng = lat_long [1];
                                        markerDisplayOnMap(Double.valueOf(nev_lat), Double.valueOf(nav_lng));

                                    }else if(status.equalsIgnoreCase("navigate")){

                                    }else {
                                        lat = lat_long [0];
                                        lng = lat_long [1];
                                        markerDisplayOnMap(Double.valueOf(lat), Double.valueOf(lng));
                                    }
                                }
                                else if(status.equals("descendant1")){
                                    details_header.setText("Descendent Details");
                                    tv_place_holder_pickup.setText("Pickup Location :");
                                    tv_place_holder_drop.setVisibility(View.VISIBLE);
                                    ll_pickup.setVisibility(View.GONE);
                                    ll_admin_pickup.setVisibility(View.GONE);
                                    ll_admin_reached.setVisibility(View.GONE);
                                    ll_admin_descendant_pickup.setVisibility(View.VISIBLE);


                                    tv_dec_name.setText(response.body().getData().getDecendentFirstName());
                                    tv_dec_phone_no.setText(response.body().getData().getDecendantMobileNumber());
                                    tv_dec_address.setText(response.body().getData().getPickupLocation());
                                    tv_drop_location.setText(response.body().getData().getDropLocation());

                                    String removed_from_address=response.body().getData().getPickupLocation();
                                    String transferred_to_address=response.body().getData().getDropLocation();
                                    String pickup_lat_long=response.body().getData().getPickupLocationLatlong();
                                    String drop_lat_long=response.body().getData().getDropLocationLatlong();
                                    String[] lat_long = pickup_lat_long.split(",");
                                    String[] droplat_long = drop_lat_long.split(",");
                                    if(status.equalsIgnoreCase("descendant1")){
                                        lat = lat_long [0];
                                        lng = lat_long [1];
                                        markerDisplayOnMap(Double.valueOf(lat), Double.valueOf(lng));

                                        nev_lat = lat_long [0];
                                        nav_lng = lat_long [1];
                                        markerDisplayOnMap(Double.valueOf(nev_lat), Double.valueOf(nav_lng));

                                    }else if(status.equalsIgnoreCase("navigate")){

                                    }else {
                                        lat = lat_long [0];
                                        lng = lat_long [1];
                                        markerDisplayOnMap(Double.valueOf(lat), Double.valueOf(lng));
                                    }
                                }
                                else if(status.equals("descendant2")){
                                    details_header.setText("Descendent Details");
                                    tv_place_holder_pickup.setText("Pickup Location :");
                                    tv_place_holder_drop.setVisibility(View.VISIBLE);
                                    ll_pickup.setVisibility(View.GONE);
                                    ll_admin_pickup.setVisibility(View.GONE);
                                    ll_admin_reached.setVisibility(View.GONE);
                                    ll_admin_descendant_pickup.setVisibility(View.GONE);
                                    ll_admin_descendant_drop.setVisibility(View.VISIBLE);


                                    tv_dec_name.setText(response.body().getData().getDecendentFirstName());
                                    tv_dec_phone_no.setText(response.body().getData().getDecendantMobileNumber());
                                    tv_dec_address.setText(response.body().getData().getPickupLocation());
                                    tv_drop_location.setText(response.body().getData().getDropLocation());

                                    String removed_from_address=response.body().getData().getPickupLocation();
                                    String transferred_to_address=response.body().getData().getDropLocation();
                                    String pickup_lat_long=response.body().getData().getPickupLocationLatlong();
                                    String drop_lat_long=response.body().getData().getDropLocationLatlong();
                                    String[] lat_long = pickup_lat_long.split(",");
                                    String[] droplat_long = drop_lat_long.split(",");
                                    if(status.equalsIgnoreCase("descendant2")){
                                        lat = droplat_long [0];
                                        lng = droplat_long [1];
                                        markerDisplayOnMap(Double.valueOf(lat), Double.valueOf(lng));

                                        nev_lat = droplat_long [0];
                                        nav_lng = droplat_long [1];
                                        markerDisplayOnMap(Double.valueOf(nev_lat), Double.valueOf(nav_lng));

                                    }else if(status.equalsIgnoreCase("navigate")){

                                    }else {
                                        lat = lat_long [0];
                                        lng = lat_long [1];
                                        markerDisplayOnMap(Double.valueOf(lat), Double.valueOf(lng));
                                    }
                                }
                                else if(status.equals("boat")){
                                    details_header.setText("Descendent Details");
                                    tv_place_holder_pickup.setText("Pickup Location :");
                                    tv_place_holder_drop.setVisibility(View.GONE);
                                    ll_pickup.setVisibility(View.GONE);
                                    ll_admin_pickup.setVisibility(View.GONE);
                                    ll_admin_reached.setVisibility(View.VISIBLE);

                                    tv_dec_name.setText(response.body().getData().getDecendentFirstName());
                                    tv_dec_phone_no.setText(response.body().getData().getDecendantMobileNumber());
                                    tv_dec_address.setText(response.body().getData().getDropLocation());
                                   // tv_drop_location.setText(response.body().getData().getDropLocation());

                                    String removed_from_address=response.body().getData().getPickupLocation();
                                    String transferred_to_address=response.body().getData().getDropLocation();
                                    String pickup_lat_long=response.body().getData().getDropLocationLatlong();
                                    String drop_lat_long=response.body().getData().getDropLocationLatlong();
                                    String[] lat_long = pickup_lat_long.split(",");
                                    String[] droplat_long = drop_lat_long.split(",");
                                    if(status.equalsIgnoreCase("boat")){
                                        lat = lat_long [0];
                                        lng = lat_long [1];
                                        markerDisplayOnMap(Double.valueOf(lat), Double.valueOf(lng));

                                        nev_lat = lat_long [0];
                                        nav_lng = lat_long [1];
                                        markerDisplayOnMap(Double.valueOf(nev_lat), Double.valueOf(nav_lng));

                                    }else if(status.equalsIgnoreCase("navigate")){

                                    }else {
                                        lat = lat_long [0];
                                        lng = lat_long [1];
                                        markerDisplayOnMap(Double.valueOf(lat), Double.valueOf(lng));
                                    }
                                }
                                else if(status.equals("boat0")){
                                    details_header.setText("Descendent Details");
                                    tv_place_holder_pickup.setText("Pickup Location :");
                                    tv_place_holder_drop.setVisibility(View.GONE);
                                    ll_pickup.setVisibility(View.GONE);
                                    ll_admin_pickup.setVisibility(View.GONE);
                                    ll_admin_reached.setVisibility(View.GONE);
                                    ll_admin_descendant_pickup.setVisibility(View.VISIBLE);


                                    tv_dec_name.setText(response.body().getData().getDecendentFirstName());
                                    tv_dec_phone_no.setText(response.body().getData().getDecendantMobileNumber());
                                    tv_dec_address.setText(response.body().getData().getDropLocation());
                                    //tv_drop_location.setText(response.body().getData().getDropLocation());

                                    String removed_from_address=response.body().getData().getPickupLocation();
                                    String transferred_to_address=response.body().getData().getDropLocation();
                                    String pickup_lat_long=response.body().getData().getDropLocationLatlong();
                                    String drop_lat_long=response.body().getData().getDropLocationLatlong();
                                    String[] lat_long = pickup_lat_long.split(",");
                                    String[] droplat_long = drop_lat_long.split(",");
                                    if(status.equalsIgnoreCase("boat0")){
                                        lat = lat_long [0];
                                        lng = lat_long [1];
                                        markerDisplayOnMap(Double.valueOf(lat), Double.valueOf(lng));

                                        nev_lat = lat_long [0];
                                        nav_lng = lat_long [1];
                                        markerDisplayOnMap(Double.valueOf(nev_lat), Double.valueOf(nav_lng));

                                    }else if(status.equalsIgnoreCase("navigate")){

                                    }else {
                                        lat = lat_long [0];
                                        lng = lat_long [1];
                                        markerDisplayOnMap(Double.valueOf(lat), Double.valueOf(lng));
                                    }
                                }
                                else if(status.equals("boat1")){
                                    details_header.setText("Descendent Details");
                                    tv_place_holder_pickup.setText("Pickup Location :");
                                    tv_place_holder_pickup.setVisibility(View.GONE);
                                    tv_place_holder_drop.setVisibility(View.GONE);
                                    ll_pickup.setVisibility(View.GONE);
                                    ll_admin_pickup.setVisibility(View.GONE);
                                    ll_admin_reached.setVisibility(View.GONE);
                                    ll_admin_descendant_pickup.setVisibility(View.GONE);
                                    ll_admin_burial_boat.setVisibility(View.VISIBLE);
                                    ll_navigateview.setVisibility(View.GONE);


                                    tv_dec_name.setText(response.body().getData().getDecendentFirstName());
                                    tv_dec_phone_no.setText(response.body().getData().getDecendantMobileNumber());
                                    //tv_dec_address.setText(response.body().getData().getPickupLocation());
                                    //tv_drop_location.setText(response.body().getData().getDropLocation());

                                    String removed_from_address=response.body().getData().getPickupLocation();
                                    String transferred_to_address=response.body().getData().getDropLocation();
                                    String pickup_lat_long=response.body().getData().getDropLocationLatlong();
                                    String drop_lat_long=response.body().getData().getDropLocationLatlong();
                                    String[] lat_long = pickup_lat_long.split(",");
                                    String[] droplat_long = drop_lat_long.split(",");
                                   /* if(status.equalsIgnoreCase("descendant1")){
                                        lat = lat_long [0];
                                        lng = lat_long [1];
                                        markerDisplayOnMap(Double.valueOf(lat), Double.valueOf(lng));

                                        nev_lat = lat_long [0];
                                        nav_lng = lat_long [1];
                                        markerDisplayOnMap(Double.valueOf(nev_lat), Double.valueOf(nav_lng));

                                    }else if(status.equalsIgnoreCase("navigate")){

                                    }else {
                                        lat = lat_long [0];
                                        lng = lat_long [1];
                                        markerDisplayOnMap(Double.valueOf(lat), Double.valueOf(lng));
                                    }*/
                                }
                                else if(status.equals("boat2")){
                                    details_header.setText("Descendent Details");
                                    tv_place_holder_pickup.setText("Pickup Location :");
                                    tv_place_holder_pickup.setVisibility(View.GONE);
                                    tv_place_holder_drop.setVisibility(View.VISIBLE);
                                    tv_place_holder_pickup.setVisibility(View.VISIBLE);
                                    ll_pickup.setVisibility(View.GONE);
                                    ll_admin_pickup.setVisibility(View.GONE);
                                    ll_admin_reached.setVisibility(View.GONE);
                                    ll_admin_descendant_pickup.setVisibility(View.GONE);
                                    ll_admin_burial_boat.setVisibility(View.GONE);
                                    ll_navigateview.setVisibility(View.VISIBLE);

                                    ll_admin_descendant_drop.setVisibility(View.VISIBLE);


                                    tv_dec_name.setText(response.body().getData().getDecendentFirstName());
                                    tv_dec_phone_no.setText(response.body().getData().getDecendantMobileNumber());
                                   // tv_dec_address.setText(response.body().getData().getDropLocation());
                                    tv_drop_location.setText(response.body().getData().getDropLocation());

                                    String removed_from_address=response.body().getData().getPickupLocation();
                                    String transferred_to_address=response.body().getData().getDropLocation();
                                    String pickup_lat_long=response.body().getData().getDropLocationLatlong();
                                    String drop_lat_long=response.body().getData().getDropLocationLatlong();
                                    String[] lat_long = pickup_lat_long.split(",");
                                    String[] droplat_long = drop_lat_long.split(",");
                                    if(status.equalsIgnoreCase("boat2")){
                                        lat = droplat_long [0];
                                        lng = droplat_long [1];
                                        markerDisplayOnMap(Double.valueOf(lat), Double.valueOf(lng));

                                        nev_lat = droplat_long [0];
                                        nav_lng = droplat_long [1];
                                        markerDisplayOnMap(Double.valueOf(nev_lat), Double.valueOf(nav_lng));

                                    }else if(status.equalsIgnoreCase("navigate")){

                                    }else {
                                        lat = lat_long [0];
                                        lng = lat_long [1];
                                        markerDisplayOnMap(Double.valueOf(lat), Double.valueOf(lng));
                                    }
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

    // for admin 1,5,7,8,9
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
                            status="descendant";
                            getDriverAllServiceRequestDetailApi();
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
                            status="descendant1";
                            getDriverAllServiceRequestDetailApi();
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
    void reachedDescendantPickupLocationApi() {


        final LoadingProgressDialog dialog = new LoadingProgressDialog(getActivity(), "Please wait...");
        dialog.setCancelable(false);
        dialog.show();

        ApiInterface apiInterface = ApiClients.getConnection(getActivity());
        PickupDriverRequest request = new PickupDriverRequest();
        request.setUserId(userId);
        request.setRequestId(request_id);
        request.setStatus("accept");


        Call<PickupDriverResponse> call = apiInterface.pickupDriverApi(loginResponse.getData().getToken(), request);
        call.enqueue(new Callback<PickupDriverResponse>() {
            @Override
            public void onResponse(Call<PickupDriverResponse> call, Response<PickupDriverResponse> response) {
                try {
                    dialog.dismiss();
                    if (response.code() >= 200 && response.code() <= 210) {
                        if (response.body().getSuccess().equalsIgnoreCase("true")) {
                            Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            status="descendant2";
                            getDriverAllServiceRequestDetailApi();
                             Intent intent = new Intent(getActivity(),MainActivity.class);
                             startActivity(intent);

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
            public void onFailure(Call<PickupDriverResponse> call, Throwable t) {
                dialog.dismiss();
                Log.e(TAG, "onResponse: " + t.getMessage());
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    void dropDescendantApi(String userId, final String status, final String requestId) {
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


    void doneBurialApi(String userId,String requestId) {
        final LoadingProgressDialog dialog = new LoadingProgressDialog(context, "please Wait!");
        dialog.setCancelable(false);
        dialog.show();
        ApiInterface apiInterface = ApiClients.getConnection(context);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("user_id", userId);
        jsonObject.addProperty("request_id", requestId);
        Call<JsonObject> call = apiInterface.doneBurialApi(loginResponse.getData().getToken(), jsonObject);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                try {
                    dialog.dismiss();
                    if (response.code() >= 200 && response.code() <= 210) {
                        JSONObject object = new JSONObject(response.body().toString());
                        if (object.optString("success").equalsIgnoreCase("true"))
                            status="boat2";
                            getDriverAllServiceRequestDetailApi();
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


}