package com.transdignity.deathserviceprovider.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.android.material.snackbar.Snackbar;
import com.transdignity.deathserviceprovider.MainActivity;
import com.transdignity.deathserviceprovider.R;
import com.transdignity.deathserviceprovider.fragments.ServicesDetailsFragments.AshesDetiailsPageFragment;
import com.transdignity.deathserviceprovider.fragments.ServicesDetailsFragments.LimoDetailPageFragment;
import com.transdignity.deathserviceprovider.models.auth.LoginResponse;
import com.transdignity.deathserviceprovider.models.request.Services.AshesRequestModel;
import com.transdignity.deathserviceprovider.models.request.Services.AshesResponseModel;
import com.transdignity.deathserviceprovider.models.requestDetails.Data;
import com.transdignity.deathserviceprovider.network.ApiClients;
import com.transdignity.deathserviceprovider.network.ApiInterface;
import com.transdignity.deathserviceprovider.utilities.CommonUtils;
import com.transdignity.deathserviceprovider.utilities.GlobalValues;
import com.transdignity.deathserviceprovider.utilities.LoadingProgressDialog;
import com.transdignity.deathserviceprovider.utilities.MyConstants;
import com.transdignity.deathserviceprovider.utilities.PreferenceManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class AshesServiceFragment extends Fragment implements View.OnClickListener {
    View view;
    Data data;
    Boolean isViewMode = false;
    Context context;
    Activity activity;
    TextView tv_pick_up_location,tv_drop_location,tv_submit,tv_cancel;
    ImageView im_pick_up_location,im_drop_location;
    EditText ed_descendant_first_name,ed_descendant_last_name,ed_mobile_no,ed_comments;
    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 100;
    double latitude;
    double longitude;
    String latLonSource, latLongDestination, latLongStatus = "from";
    String userId;
    LoginResponse loginResponse;
    TextView tv_back;
    LinearLayout ll_pickup,ll_drop;
    LinearProgressIndicator indicator;
    FrameLayout frame;
    public AshesServiceFragment() {
        // Required empty public constructor
    }

    public static AshesServiceFragment newInstance(Data data, Boolean isViewMode) {
        AshesServiceFragment fragment = new AshesServiceFragment();
        fragment.data = data;
        fragment.isViewMode = isViewMode;
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
        view= inflater.inflate(R.layout.fragment_ashes_service, container, false);
        init();
        loginResponse = (LoginResponse) GlobalValues.getPreferenceManager().getObject(PreferenceManager.LoginResponseKey, LoginResponse.class);

        if (loginResponse != null) {
            userId = loginResponse.getData().getId();
        }
        //TODO: Initialize Place Api
        if (!Places.isInitialized()) {
            Places.initialize(context, MyConstants.PLACEAPIKEY);
        }
        return view;
    }

    public void init(){
        frame=view.findViewById(R.id.frame);
        indicator=view.findViewById(R.id.indicator);
        ll_pickup=view.findViewById(R.id.ll_pickup);
        ll_drop=view.findViewById(R.id.ll_drop);
        tv_pick_up_location=view.findViewById(R.id.tv_pick_up_location);
        tv_drop_location=view.findViewById(R.id.tv_drop_location);
        im_pick_up_location=view.findViewById(R.id.im_pick_up_location);
        im_drop_location=view.findViewById(R.id.im_drop_location);
        ed_descendant_first_name=view.findViewById(R.id.ed_descendant_first_name);
        ed_descendant_last_name=view.findViewById(R.id.ed_descendant_last_name);
        ed_mobile_no=view.findViewById(R.id.ed_mobile_no);
        ed_comments=view.findViewById(R.id.ed_comments);
        tv_submit=view.findViewById(R.id.tv_submit);
        tv_cancel=view.findViewById(R.id.tv_cancel);
        tv_back=view.findViewById(R.id.tv_back);

        click();
    }

    public void click(){
        im_pick_up_location.setOnClickListener(this);
        im_drop_location.setOnClickListener(this);
        tv_submit.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
        tv_back.setOnClickListener(this);
        ll_pickup.setOnClickListener(this);
        ll_drop.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_pickup:
                latLongStatus = "from";
                callPlaceSearchIntent();
                break;
            case R.id.ll_drop:
                latLongStatus = "to";
                callPlaceSearchIntent();
                break;
            case R.id.tv_submit:
                validation();
                break;
            case R.id.tv_cancel:
                Fragment fragment1 = new HomeFragment();
                String fragmtStatusTag2 = fragment1.getClass().getName();
                GlobalValues.getInstance().setFramgentTag(fragmtStatusTag2);
                ((MainActivity) activity).selectedNavItem(R.id.navigation_home);
                CommonUtils.loadFragment(context, fragment1, fragmtStatusTag2);
                break;
            case R.id.tv_back:
                Fragment fragment;
                fragment = new HomeFragment();
                String fragmtStatusTag = fragment.getClass().getName();
                GlobalValues.getInstance().setFramgentTag(fragmtStatusTag);
                ((MainActivity) activity).selectedNavItem(R.id.navigation_home);
                CommonUtils.loadFragment(context, fragment, fragmtStatusTag);
                break;
        }
    }
    private void callPlaceSearchIntent() {
        /*Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                        .build(AddEventActivity.this);
        startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);*/


        List<Place.Field> fields = Arrays.asList(Place.Field.TYPES, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG);
        // Start the autocomplete intent.
        Intent intent = new Autocomplete.IntentBuilder(
                AutocompleteActivityMode.FULLSCREEN, fields)
                .build(context);
        startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Place place = Autocomplete.getPlaceFromIntent(data);
            // final Place place = PlacePicker.getPlace(AddEventActivity.this, data);
            final CharSequence name = place.getName();
            final List<Place.Type> types = place.getTypes();
            final CharSequence address = place.getAddress();
            LatLng latLng = place.getLatLng();
            latitude = latLng.latitude;
            longitude = latLng.longitude;
            if (latLongStatus.equalsIgnoreCase("from")) {
                latLonSource = String.valueOf(latitude) + "," + String.valueOf(longitude);
                tv_pick_up_location.setText(address.toString());
                //Toast.makeText(context, "" + latLonSource, Toast.LENGTH_SHORT).show();
            } else {
                latLongDestination = String.valueOf(latitude) + "," + String.valueOf(longitude);
                tv_drop_location.setText(address.toString());
                // Toast.makeText(context, "" + latLongDestination, Toast.LENGTH_SHORT).show();
            }
            Log.e(TAG, "onActivityResult: latlong>>> " + latLonSource + "////" + latLongDestination);
            //  binding.etVenueAddress.setText(address.toString());

         /*   MapsTimeZone maps=new MapsTimeZone();
            try {
               String  timezone11= maps.getTimezone111(address.toString());
               Log.e("timezone11",timezone11);
            } catch (Exception e) {
                e.printStackTrace();
            }*/
            // getGoogleTimeZoneApi(latitude,longitude);

            try {
                Geocoder mGeocoder = new Geocoder(context, Locale.getDefault());
                // getCityNameByCoordinates(latitude, longitude, mGeocoder);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public void validation(){
        if (TextUtils.isEmpty(tv_pick_up_location.getText().toString())){
            Snackbar.make(view.findViewById(R.id.ll_ashes),"Please enter Pick up location",Snackbar.LENGTH_LONG).setAction("Action",null).show();
        }else if (TextUtils.isEmpty(tv_drop_location.getText().toString())){
            Snackbar.make(view.findViewById(R.id.ll_ashes),"Please enter Drop Location",Snackbar.LENGTH_LONG).setAction("Action",null).show();
        }else if (TextUtils.isEmpty(ed_descendant_first_name.getText().toString())){
            Snackbar.make(view.findViewById(R.id.ll_ashes),"Please enter Descendant First Name",Snackbar.LENGTH_LONG).setAction("Action",null).show();
        }else if (TextUtils.isEmpty(ed_descendant_last_name.getText().toString())){
            Snackbar.make(view.findViewById(R.id.ll_ashes),"Please enter Descendant Last Name",Snackbar.LENGTH_LONG).setAction("Action",null).show();
        }else if (TextUtils.isEmpty(ed_mobile_no.getText().toString())){
            Snackbar.make(view.findViewById(R.id.ll_ashes),"Please enter  Mobile No",Snackbar.LENGTH_LONG).setAction("Action",null).show();
        }else if (TextUtils.isEmpty(ed_comments.getText().toString())){
            Snackbar.make(view.findViewById(R.id.ll_ashes),"Please enter Comments",Snackbar.LENGTH_LONG).setAction("Action",null).show();
        }else {
            AshesServiceApi();
        }}

    public void AshesServiceApi() {
        long date = System.currentTimeMillis();
        SimpleDateFormat date1 = new SimpleDateFormat("dd/MM/yyyy HH:mm"); // for current date
        SimpleDateFormat time1 = new SimpleDateFormat("kk:mm:ss");
        String request_generate_date_time = date1.format(date);
        indicator.setVisibility(View.VISIBLE);
        frame.setVisibility(View.VISIBLE);
        final LoadingProgressDialog dialog = new LoadingProgressDialog(context, "Please wait...");
        dialog.setCancelable(false);
        dialog.show();
        ApiInterface apiInterface = ApiClients.getClient(context).create(ApiInterface.class);
        final AshesRequestModel req = new AshesRequestModel();
        req.setFcmKey(GlobalValues.getPreferenceManager().getFcmString(PreferenceManager.FCMKEY));
        req.setDevice("Android");
        req.setRequestDate(request_generate_date_time);
        req.setDeviceId(CommonUtils.getDeviceId(context));
       /* req.setFromAddressLatlong("28.6222331,77.3830289");
        req.setToAddressLatlong("27.867,131.8789");*/
        req.setPickupLocationLatlong(latLonSource);
        req.setDropLocationLatlong(latLongDestination);

        req.setRequestCreatedBy(Integer.parseInt(userId));
        req.setPickupLocation(tv_pick_up_location.getText().toString().trim());
        req.setDropLocation(tv_drop_location.getText().toString().trim());
        req.setFirstName(ed_descendant_first_name.getText().toString().trim());
        req.setLastName(ed_descendant_last_name.getText().toString().trim());
        req.setMobileNumber(ed_mobile_no.getText().toString().trim());
        req.setDescription(ed_comments.getText().toString().trim());

        req.setServiceId(5);

        Call<AshesResponseModel> call = apiInterface.AshesServiceApi(loginResponse.getData().getToken(), req);

        call.enqueue(new Callback<AshesResponseModel>() {
            @Override
            public void onResponse(Call<AshesResponseModel> call, Response<AshesResponseModel> response) {
                dialog.dismiss();
                indicator.setVisibility(View.GONE);
                frame.setVisibility(View.GONE);
                try {
                    if (response.code() >= 200 && response.code() <= 210) {
                        if (response.body().getSuccess().equalsIgnoreCase("true")) {
                            Toast.makeText(context, response.body().getData().getmRequestId().toString(), Toast.LENGTH_SHORT).show();
                            String requesrId = response.body().getData().getmRequestId().toString();
                            Fragment fragment = AshesDetiailsPageFragment.newInstance(requesrId,"Ashes");
                            String fragmtStatusTag2 = fragment.getClass().getName();
                            GlobalValues.getInstance().setFramgentTag(fragmtStatusTag2);
                            ((MainActivity) activity).selectedNavItem(R.id.navigation_home);
                            CommonUtils.loadFragment(context, fragment, fragmtStatusTag2);

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
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<AshesResponseModel> call, Throwable t) {
                Toast.makeText(context, "Something Went Wrong!", Toast.LENGTH_LONG).show();
                dialog.dismiss();
                indicator.setVisibility(View.GONE);
                frame.setVisibility(View.GONE);
            }
        });

    }

}