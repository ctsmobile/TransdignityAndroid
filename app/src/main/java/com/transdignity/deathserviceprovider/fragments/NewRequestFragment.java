package com.transdignity.deathserviceprovider.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.transdignity.deathserviceprovider.MainActivity;
import com.transdignity.deathserviceprovider.R;
import com.transdignity.deathserviceprovider.adapters.HospitalTypeAdapter;
import com.transdignity.deathserviceprovider.databinding.FragmentNewRequestBinding;
import com.transdignity.deathserviceprovider.models.auth.LoginResponse;
import com.transdignity.deathserviceprovider.models.request.newRequest.HospitalTypeResponse;
import com.transdignity.deathserviceprovider.models.request.newRequest.NewRequestReq;
import com.transdignity.deathserviceprovider.models.request.newRequest.NewRequestResponse;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class NewRequestFragment extends Fragment implements View.OnClickListener {
    FragmentNewRequestBinding binding;
    Context context;
    Activity activity;
    String bodyRelease, personelEffect, extraItem, strHospital;
    Integer intHospitalId = 0;
    List<HospitalTypeResponse.DataItem> hospitaltypeList = new ArrayList<>();
    String userId;
    LoginResponse loginResponse;
    Data data;
    double latitude;
    double longitude;
    String latLonSource, latLongDestination, latLongStatus = "from";
    Boolean isViewMode = false;
    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 100;
    String request_generate_date_time;
    LinearProgressIndicator indicator;
    FrameLayout frame;
    // TODO: Rename and change types and number of parameters
    public static NewRequestFragment newInstance(Data data, Boolean isViewMode) {
        NewRequestFragment fragment = new NewRequestFragment();
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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_new_request, container, false);
        View v = binding.getRoot();

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loginResponse = (LoginResponse) GlobalValues.getPreferenceManager().getObject(PreferenceManager.LoginResponseKey, LoginResponse.class);
        frame=view.findViewById(R.id.frame);
        indicator=view.findViewById(R.id.indicator);
        if (loginResponse != null) {
            userId = loginResponse.getData().getId();
            Toast.makeText(context, ""+userId, Toast.LENGTH_SHORT).show();

        }
        if (isViewMode) {
            setFieldData();
        } else {
            radioButtonManage(view);
        }
        //TODO: Initialize Place Api
        if (!Places.isInitialized()) {
            Places.initialize(context, MyConstants.PLACEAPIKEY);
        }

        getHospitalTypeListApi();
        step1ContainerVisible();

        if (!isViewMode) {
            binding.etDate.setOnClickListener(this);
            binding.etDateOfBirth.setOnClickListener(this);
            binding.etTimeCompleted.setOnClickListener(this);
            binding.etTimeRecieved.setOnClickListener(this);
            binding.etTimeOfDeath.setOnClickListener(this);
            binding.etDateOfDeath.setOnClickListener(this);
           /* binding.llSourcseLatLong.setOnClickListener(this);
            binding.llDestLatlong.setOnClickListener(this);*/
            binding.llSourceAddress.setOnClickListener(this);
            binding.llDestinationAddress.setOnClickListener(this);
            binding.etRemovedFromAddress.setOnClickListener(this);
            binding.etTransToAddress.setOnClickListener(this);
        }
        binding.tvBack.setOnClickListener(this);
        binding.llNext.setOnClickListener(this);
        binding.llSubmit.setOnClickListener(this);
        binding.llCancel.setOnClickListener(this);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    private void callPlaceSearchIntent() {
        /*Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                        .build(AddEventActivity.this);
        startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);*/


        List<com.google.android.libraries.places.api.model.Place.Field> fields = Arrays.asList(Place.Field.TYPES, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG);
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
                binding.etRemovedFromAddress.setText(address.toString());
                //Toast.makeText(context, "" + latLonSource, Toast.LENGTH_SHORT).show();
            } else {
                latLongDestination = String.valueOf(latitude) + "," + String.valueOf(longitude);
                binding.etTransToAddress.setText(address.toString());
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

    void setFieldData() {
        if (data != null) {
            intHospitalId = Integer.parseInt(data.getHospitalTypeId());
            binding.etDate.setText(data.getFomatRequestDate());
            binding.etTimeRecieved.setText(data.getTimeReceived());
            binding.etTimeCompleted.setText(data.getTimeCompleted());
            binding.etBillTo.setText(data.getBillTo());
            binding.rtReqBy.setText(data.getRequestedBy());

            binding.etFirstName.setText(data.getDecendantFirstName());
            binding.etMidName.setText(data.getDecendantMiddleName());
            binding.etLastName.setText(data.getDecendantLastName());

            binding.etRemovedFromAddress.setText(data.getRemovedFromAddress());
            binding.etTransToAddress.setText(data.getTransferredToAddress());
            binding.etDateOfDeath.setText(data.getDateOfDeath());
            binding.etTimeOfDeath.setText(data.getTimeOfDeath());
            // binding.sprTypeOfHospital.setText(data.getTimeReceived());
            binding.etPhysisianName.setText(data.getPhysicianName());
            binding.etPhysisianPhoneNumber.setText(data.getPhysicianPhone());
            binding.etRelationship.setText(data.getNextOfKinRelationship());
            binding.etRelationshipPhoneNumber.setText(data.getNextOfKinPhone());
            binding.etDateOfBirth.setText(data.getDob());
            binding.etAge.setText(data.getAge());
            binding.etPersonelEffectList.setText(data.getPersonalEffectsLists());
            binding.etExtraList.setText(data.getRequestedItemsLists());
            if (data.getBodyRelease() != null) {
                if (data.getBodyRelease().equalsIgnoreCase("1")) {
                    ((RadioButton) binding.rgBodyRelease.getChildAt(0)).setChecked(true);
                } else {
                    ((RadioButton) binding.rgBodyRelease.getChildAt(1)).setChecked(true);
                }
            }
            if (data.getPersonalEffects() != null) {

                if (data.getPersonalEffects().equalsIgnoreCase("1")) {
                    ((RadioButton) binding.rgPersonelEffect.getChildAt(0)).setChecked(true);
                    binding.etPersonelEffectList.setVisibility(View.VISIBLE);
                } else {
                    ((RadioButton) binding.rgPersonelEffect.getChildAt(1)).setChecked(true);
                    binding.etPersonelEffectList.setVisibility(View.GONE);
                }
            }
            if (data.getRequestedItems() != null) {
                if (data.getRequestedItems().equalsIgnoreCase("1")) {
                    ((RadioButton) binding.rgExtraRequest.getChildAt(0)).setChecked(true);
                    binding.etExtraList.setVisibility(View.VISIBLE);
                } else {
                    ((RadioButton) binding.rgExtraRequest.getChildAt(1)).setChecked(true);
                    binding.etExtraList.setVisibility(View.GONE);
                }
            }

            binding.etDate.setFocusableInTouchMode(false);
            binding.etTimeRecieved.setFocusableInTouchMode(false);
            binding.etTimeCompleted.setFocusableInTouchMode(false);
            binding.etBillTo.setFocusableInTouchMode(false);
            binding.rtReqBy.setFocusableInTouchMode(false);
            binding.etFirstName.setFocusableInTouchMode(false);
            binding.etMidName.setFocusableInTouchMode(false);
            binding.etLastName.setFocusableInTouchMode(false);
            binding.etRemovedFromAddress.setFocusableInTouchMode(false);
            binding.etTransToAddress.setFocusableInTouchMode(false);
            binding.etTimeOfDeath.setFocusableInTouchMode(false);
            binding.etPhysisianName.setFocusableInTouchMode(false);
            binding.etPhysisianPhoneNumber.setFocusableInTouchMode(false);
            binding.etRelationship.setFocusableInTouchMode(false);
            binding.etRelationshipPhoneNumber.setFocusableInTouchMode(false);
            binding.etDateOfBirth.setFocusableInTouchMode(false);
            binding.etDateOfDeath.setFocusableInTouchMode(false);
            binding.etAge.setFocusableInTouchMode(false);
            binding.etPersonelEffectList.setFocusableInTouchMode(false);
            binding.etExtraList.setFocusableInTouchMode(false);

            binding.llSubmitCancel.setVisibility(View.INVISIBLE);

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.et_date:
                CommonUtils.getDatepicker(context, binding.etDate);
                break;
            case R.id.et_date_of_birth:
                getDatepicker(context, binding.etDateOfBirth);
                break;
            case R.id.et_date_of_death:
                CommonUtils.getDatepickerDOB(context, binding.etDateOfDeath);
                break;
            case R.id.et_time_of_death:
                CommonUtils.getTime(context, binding.etTimeOfDeath);
                break;
            case R.id.et_time_recieved:
                CommonUtils.getTime(context, binding.etTimeRecieved);

                break;
            case R.id.et_time_completed:
                CommonUtils.getTime(context, binding.etTimeCompleted);

                break;
            case R.id.ll_next:
                setValidationSet1();
                break;
            case R.id.ll_source_address:
                latLongStatus = "from";
                callPlaceSearchIntent();
                break;
            case R.id.et_removed_from_address:
                latLongStatus = "from";
                callPlaceSearchIntent();
                break;
            case R.id.ll_destination_address:
                latLongStatus = "to";
                callPlaceSearchIntent();
                break;
            case R.id.et_trans_to_address:
                latLongStatus = "to";
                callPlaceSearchIntent();
                break;
            case R.id.ll_submit:
                setValidationSet2();

                break;
            case R.id.ll_cancel:
                Fragment fragmenthome = new HomeFragment();
                String fragmtStatusTag = fragmenthome.getClass().getName();
                GlobalValues.getInstance().setFramgentTag(fragmtStatusTag);
                ((MainActivity) activity).selectedNavItem(R.id.navigation_home);
                CommonUtils.loadFragment(context, fragmenthome, fragmtStatusTag);
                break;
            case R.id.tv_back:
                if (binding.llNewReqSecond.isShown()) {
                    step1ContainerVisible();
                } else if (isViewMode) {
                    Fragment fragment = DecendentDetailFragment.newInstance(data.getRequestId(),"Descendent");
                    String fragmtStatusTag2 = fragment.getClass().getName();
                    GlobalValues.getInstance().setFramgentTag(fragmtStatusTag2);
                    ((MainActivity) activity).selectedNavItem(R.id.navigation_home);
                    CommonUtils.loadFragment(context, fragment, fragmtStatusTag2);
                } else {
                    Fragment fragment = new HomeFragment();
                    String fragmtStatusTag2 = fragment.getClass().getName();
                    GlobalValues.getInstance().setFramgentTag(fragmtStatusTag2);
                    ((MainActivity) activity).selectedNavItem(R.id.navigation_home);
                    CommonUtils.loadFragment(context, fragment, fragmtStatusTag2);
                }
                break;
        }
    }

    void step1ContainerVisible() {
        binding.llNewReqFirst.setVisibility(View.VISIBLE);
        binding.llNewReqSecond.setVisibility(View.GONE);
    }

    void step2ContainerVisible() {
        binding.llNewReqFirst.setVisibility(View.GONE);
        binding.llNewReqSecond.setVisibility(View.VISIBLE);
    }

    void setValidationSet1() {
        if (TextUtils.isEmpty(binding.etDate.getText().toString().trim())) {
            binding.etDate.setError("Please Select Date!");
            binding.etDate.requestFocus();
        } else if (TextUtils.isEmpty(binding.etTimeRecieved.getText().toString().trim())) {
            binding.etTimeRecieved.setError("Please Select Date!");
            binding.etTimeRecieved.requestFocus();
        } else if (TextUtils.isEmpty(binding.etTimeRecieved.getText().toString().trim())) {
            binding.etTimeRecieved.setError("Please Select Time Recieved!");
            binding.etTimeRecieved.requestFocus();
        }/* else if (TextUtils.isEmpty(binding.tvTimeCompleted.getText().toString().trim())) {
            binding.tvTimeCompleted.setError("Please Select Time Completed!");
            binding.tvTimeCompleted.requestFocus();
        }*/ else if (TextUtils.isEmpty(binding.etBillTo.getText().toString().trim())) {
            binding.etBillTo.setError("Please Fill this Field!");
            binding.etBillTo.requestFocus();
        } else if (TextUtils.isEmpty(binding.rtReqBy.getText().toString().trim())) {
            binding.rtReqBy.setError("Please Fill this Field!");
            binding.rtReqBy.requestFocus();
        } else if (TextUtils.isEmpty(binding.etFirstName.getText().toString().trim())) {
            binding.etFirstName.setError("Please Enter Fisrt Name!");
            binding.etFirstName.requestFocus();
        } else if (TextUtils.isEmpty(binding.etLastName.getText().toString().trim())) {
            binding.etLastName.setError("Please Enter Last Name!");
            binding.etLastName.requestFocus();
        } else {
            step2ContainerVisible();
        }

    }

    void setValidationSet2() {
        if (TextUtils.isEmpty(binding.etRemovedFromAddress.getText().toString().trim())) {
            binding.etRemovedFromAddress.setError("Please Enter Removed Address!");
            binding.etRemovedFromAddress.requestFocus();
        }else if (latLonSource == null ) {
            binding.etRemovedFromAddress.setError("Please pick Source google location!");
            binding.etRemovedFromAddress.requestFocus();
        }
        else if (TextUtils.isEmpty(binding.etTransToAddress.getText().toString().trim())) {
            binding.etTransToAddress.setError("Please Enter Transferred Address!");
            binding.etTransToAddress.requestFocus();
        }else if (latLongDestination == null ) {
            binding.etRemovedFromAddress.setError("Please pick destination google location!");
            binding.etRemovedFromAddress.requestFocus();
        }
        else if (TextUtils.isEmpty(binding.etDateOfDeath.getText().toString().trim())) {
            binding.etDateOfDeath.setError("Please Select Date of Death!");
            binding.etDateOfDeath.requestFocus();
        } else if (TextUtils.isEmpty(binding.etTimeOfDeath.getText().toString().trim())) {
            binding.etTimeOfDeath.setError("Please Select Time of Death!");
            binding.etTimeOfDeath.requestFocus();
        } else if (TextUtils.isEmpty(binding.etPhysisianName.getText().toString().trim())) {
            binding.etPhysisianName.setError("Please Enter Physician Name!");
            binding.etPhysisianName.requestFocus();
        } else if (TextUtils.isEmpty(binding.etPhysisianPhoneNumber.getText().toString().trim())) {
            binding.etPhysisianPhoneNumber.setError("Please Fill this Field!");
            binding.etPhysisianPhoneNumber.requestFocus();
        } else if (binding.etPhysisianPhoneNumber.getText().toString().trim().length() < 10 || binding.etPhysisianPhoneNumber.getText().toString().trim().length() > 12) {
            binding.etPhysisianPhoneNumber.setError("Please Enter Valid Mobile Number!");
            binding.etPhysisianPhoneNumber.requestFocus();
        } else if (TextUtils.isEmpty(binding.etRelationship.getText().toString().trim())) {
            binding.etRelationship.setError("Please Enter relationship Name!");
            binding.etRelationship.requestFocus();
        } else if (TextUtils.isEmpty(binding.etRelationshipPhoneNumber.getText().toString().trim())) {
            binding.etRelationshipPhoneNumber.setError("Please Enter Phone Number!");
            binding.etRelationshipPhoneNumber.requestFocus();
        } else if (binding.etRelationshipPhoneNumber.getText().toString().trim().length() < 10 || binding.etRelationshipPhoneNumber.getText().toString().trim().length() > 12) {
            binding.etRelationshipPhoneNumber.setError("Please Enter valid Phone Number!");
            binding.etRelationshipPhoneNumber.requestFocus();
        } else if (TextUtils.isEmpty(binding.etDateOfBirth.getText().toString().trim())) {
            binding.etDateOfBirth.setError("Please Select DOB!");
            binding.etDateOfBirth.requestFocus();
        } else if (TextUtils.isEmpty(binding.etAge.getText().toString().trim())) {
            binding.etDateOfBirth.setError("Please Select DOB!");
            binding.etDateOfBirth.requestFocus();
        } else {
            if (!isViewMode)
                callApi();
        }
    }

    void callApi() {
        indicator.setVisibility(View.VISIBLE);
        frame.setVisibility(View.VISIBLE);
        long date = System.currentTimeMillis();
        SimpleDateFormat date1 = new SimpleDateFormat("dd/MM/yyyy HH:mm"); // for current date
        SimpleDateFormat time1 = new SimpleDateFormat("kk:mm:ss");
        request_generate_date_time = date1.format(date);    //This will return current date in 31-12-2018 format
        String timeString1 = time1.format(date);
        final LoadingProgressDialog dialog = new LoadingProgressDialog(context, "Please wait...");
        dialog.setCancelable(false);
        dialog.show();
        ApiInterface apiInterface = ApiClients.getClient(context).create(ApiInterface.class);
        final NewRequestReq req = new NewRequestReq();
        req.setFcmKey(GlobalValues.getPreferenceManager().getFcmString(PreferenceManager.FCMKEY));
        req.setDevice("Android");
        req.setDeviceId(CommonUtils.getDeviceId(context));
       /* req.setFromAddressLatlong("28.6222331,77.3830289");
        req.setToAddressLatlong("27.867,131.8789");*/
        req.setFromAddressLatlong(latLonSource);
        req.setToAddressLatlong(latLongDestination);

        req.setRequestCreatedBy(Integer.parseInt(userId));
        //req.setRequestDate(binding.etDate.getText().toString().trim());
        req.setRequestDate(request_generate_date_time);
        req.setTimeReceived(binding.etTimeRecieved.getText().toString().trim());
        req.setTimeCompleted(binding.etTimeCompleted.getText().toString().trim());
        req.setBillTo(binding.etBillTo.getText().toString().trim());
        req.setRequestedBy(binding.rtReqBy.getText().toString().trim());
        req.setFirstName(binding.etFirstName.getText().toString().trim());
        req.setMiddleName(binding.etMidName.getText().toString().trim());
        req.setLastName(binding.etLastName.getText().toString().trim());
        req.setRemovedFromAddress(binding.etRemovedFromAddress.getText().toString().trim());
        req.setTransferredToAddress(binding.etTransToAddress.getText().toString().trim());
        req.setDateOfDeath(binding.etDateOfDeath.getText().toString().trim());
        req.setTimeOfDeath(binding.etTimeOfDeath.getText().toString().trim());
        req.setHospitalTypeId(intHospitalId);
        req.setPhysicianName(binding.etPhysisianName.getText().toString().trim());
        req.setPhysicianPhone(binding.etPhysisianPhoneNumber.getText().toString().trim());
        req.setNextOfKinRelationship(binding.etRelationship.getText().toString().trim());
        req.setNextOfKinPhone(binding.etRelationshipPhoneNumber.getText().toString().trim());
        req.setDob(binding.etDateOfBirth.getText().toString().trim());
        req.setAge(Integer.parseInt(binding.etAge.getText().toString().trim()));
        req.setServiceId(1);
        //TODO: manage Radio button--------
        if (bodyRelease.equalsIgnoreCase("Yes")) {
            req.setBodyRelease(1);
        } else {
            req.setBodyRelease(0);
        }

        if (personelEffect.equalsIgnoreCase("Yes")) {
            req.setPersonalEffects(1);
            req.setPersonalEffectsLists(binding.etPersonelEffectList.getText().toString().trim());
        } else {
            req.setPersonalEffects(0);
            req.setPersonalEffectsLists(null);
        }

        if (extraItem.equalsIgnoreCase("Yes")) {
            req.setRequestedItems(1);
            req.setRequestedItemsLists(binding.etExtraList.getText().toString().trim());
        } else {
            req.setRequestedItems(0);
            req.setRequestedItemsLists(null);
        }
        Call<NewRequestResponse> call = apiInterface.newRequestApi(loginResponse.getData().getToken(), req);

        call.enqueue(new Callback<NewRequestResponse>() {
            @Override
            public void onResponse(Call<NewRequestResponse> call, Response<NewRequestResponse> response) {
                dialog.dismiss();
                indicator.setVisibility(View.GONE);
                frame.setVisibility(View.GONE);
                try {
                    if (response.code() >= 200 && response.code() <= 210) {
                        if (response.body().getSuccess().equalsIgnoreCase("true")) {
                            Toast.makeText(context, response.body().getData().getRequestId().toString(), Toast.LENGTH_SHORT).show();
                             String requesrId = response.body().getData().getRequestId().toString();
                            Fragment fragment = DecendentDetailFragment.newInstance(requesrId,"Descendent");
                            String fragmtStatusTag2 = fragment.getClass().getName();
                            GlobalValues.getInstance().setFramgentTag(fragmtStatusTag2);
                            ((MainActivity) activity).selectedNavItem(R.id.navigation_home);
                            CommonUtils.loadFragment(context, fragment, fragmtStatusTag2);
                           /* Fragment fragment = new HomeFragment();
                            String fragmtStatusTag2 = fragment.getClass().getName();
                            GlobalValues.getInstance().setFramgentTag(fragmtStatusTag2);
                            ((MainActivity) activity).selectedNavItem(R.id.navigation_home);
                            CommonUtils.loadFragment(context, fragment, fragmtStatusTag2);*/

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
            public void onFailure(Call<NewRequestResponse> call, Throwable t) {
                Toast.makeText(context, "Something Went Wrong!", Toast.LENGTH_LONG).show();
                dialog.dismiss();
                indicator.setVisibility(View.GONE);
                frame.setVisibility(View.GONE);
            }
        });

    }


    public static boolean successDialog(int succesMsg, final Activity activity, final Context context) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        View viewCustom = inflater.inflate(R.layout.forget_verifylink_sent_dialog, null);
        final AlertDialog dialog = new AlertDialog.Builder(activity).create();
        dialog.setView(viewCustom);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView succesText = viewCustom.findViewById(R.id.msg);
        succesText.setText(succesMsg);
        LinearLayout done = viewCustom.findViewById(R.id.ok_forgot);
        dialog.setCancelable(false);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Fragment fragment = new HomeFragment();
                String fragmtStatusTag = fragment.getClass().getName();
                GlobalValues.getInstance().setFramgentTag(fragmtStatusTag);
                ((MainActivity) activity).selectedNavItem(R.id.navigation_home);
                CommonUtils.loadFragment(context, fragment, fragmtStatusTag);
                //activity.overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
            }
        });
        dialog.show();
        return true;
    }

    void radioButtonManage(View v) {

        RadioButton rbbodyRelease, rbPersonelEffect, rbExtraItem;
        int intbodyRelease, intPersonelEffect, intExtraItem;
        intbodyRelease = binding.rgBodyRelease.getCheckedRadioButtonId();
        intPersonelEffect = binding.rgPersonelEffect.getCheckedRadioButtonId();
        intExtraItem = binding.rgExtraRequest.getCheckedRadioButtonId();
        rbbodyRelease = (RadioButton) v.findViewById(intbodyRelease);
        rbPersonelEffect = (RadioButton) v.findViewById(intPersonelEffect);
        rbExtraItem = (RadioButton) v.findViewById(intExtraItem);
        bodyRelease = rbbodyRelease.getText().toString().trim();
        personelEffect = rbPersonelEffect.getText().toString().trim();
        extraItem = rbExtraItem.getText().toString().trim();
        binding.rgBodyRelease.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View radioButton = binding.rgBodyRelease.findViewById(checkedId);
                int index = binding.rgBodyRelease.indexOfChild(radioButton);
                switch (index) {
                    case 0: // first button
                        bodyRelease = "Yes";
                        Log.e(TAG, "radioButtonManage: rbbodyRelease.getText()>> " + bodyRelease);
                        // Log.e(TAG, "radioButtonManage: rbbodyRelease.getText()>> " + bodyRelease);

                        Toast.makeText(context, "Selected button number " + index, Toast.LENGTH_SHORT).show();
                        break;
                    case 1: // secondbutton
                        bodyRelease = "No";
                        Log.e(TAG, "radioButtonManage: rbbodyRelease.getText()>> " + bodyRelease);
                        // Toast.makeText(context, "Selected button number " + index, Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
        binding.rgPersonelEffect.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View radioButton = binding.rgPersonelEffect.findViewById(checkedId);
                int index = binding.rgPersonelEffect.indexOfChild(radioButton);
                switch (index) {
                    case 0: // first button
                        personelEffect = "Yes";
                        binding.etPersonelEffectList.setVisibility(View.VISIBLE);
                        Log.e(TAG, "radioButtonManage: personelEffect>> " + personelEffect);

                        // Toast.makeText(context, "Selected button number " + index, Toast.LENGTH_SHORT).show();
                        break;
                    case 1: // secondbutton
                        personelEffect = "No";
                        binding.etPersonelEffectList.setVisibility(View.GONE);
                        Log.e(TAG, "radioButtonManage: personelEffect>> " + personelEffect);
                        // Toast.makeText(context, "Selected button number " + index, Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
        binding.rgExtraRequest.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View radioButton = binding.rgExtraRequest.findViewById(checkedId);
                int index = binding.rgExtraRequest.indexOfChild(radioButton);
                switch (index) {
                    case 0: // first button
                        extraItem = "Yes";
                        Log.e(TAG, "radioButtonManage: extraItem>> " + extraItem);
                        binding.etExtraList.setVisibility(View.VISIBLE);
                        //  Toast.makeText(context, "Selected button number " + index, Toast.LENGTH_SHORT).show();
                        break;
                    case 1: // secondbutton
                        extraItem = "No";
                        binding.etExtraList.setVisibility(View.GONE);
                        Log.e(TAG, "radioButtonManage: extraItem>> " + extraItem);
                        // Toast.makeText(context, "Selected button number " + index, Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
      /*  Log.e(TAG, "radioButtonManage: rbbodyRelease.getText()>> " + bodyRelease);
        Log.e(TAG, "radioButtonManage: personelEffect>> " + personelEffect);
        Log.e(TAG, "radioButtonManage: extraItem>> " + extraItem);*/


    }

    public void getDatepicker(Context context, final EditText editText) {
        // Get Current Date
        int mYear, mMonth, mDay;

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        String day= String.valueOf(Integer.valueOf(dayOfMonth));
                        String month= String.valueOf(Integer.valueOf(monthOfYear));
                        if(day.length()==1){
                            String curDate = "0"+dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                            editText.setText(curDate);
                            getAge(year, monthOfYear, dayOfMonth);

                        }else if(month.length()==1){
                            String curDate = dayOfMonth + "/" + "0"+(monthOfYear + 1) + "/" + year;
                            editText.setText(curDate);
                            getAge(year, monthOfYear, dayOfMonth);

                        }else if((day.length()==1)&&(month.length()==1)){
                            String curDate = "0"+dayOfMonth + "/" + "0"+(monthOfYear + 1) + "/" + year;
                            editText.setText(curDate);
                            getAge(year, monthOfYear, dayOfMonth);

                        }else {
                            String curDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                            editText.setText(curDate);
                            getAge(year, monthOfYear, dayOfMonth);

                        }
                        if((day.length()==1)&&(month.length()==1)){
                            String curDate = "0"+dayOfMonth + "/" + "0"+(monthOfYear + 1) + "/" + year;
                            editText.setText(curDate);
                            getAge(year, monthOfYear, dayOfMonth);

                        }

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMaxDate(c.getTimeInMillis());

        datePickerDialog.show();

    }

    private String getAge(int year, int month, int day) {
        //calculating age from dob
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();
        dob.set(year, month, day);
        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }
        binding.etAge.setText(String.valueOf(age));
        return String.valueOf(age);
    }

    void getHospitalTypeListApi() {
        final LoadingProgressDialog dialog = new LoadingProgressDialog(context, "Please wait...");
        dialog.setCancelable(false);
        dialog.show();
        ApiInterface apiInterface = ApiClients.getClient(context).create(ApiInterface.class);
        Call<HospitalTypeResponse> call = apiInterface.hospitalTypeListApi(loginResponse.getData().getToken());
        call.enqueue(new Callback<HospitalTypeResponse>() {
            @Override
            public void onResponse(Call<HospitalTypeResponse> call, Response<HospitalTypeResponse> response) {
                dialog.dismiss();
                try {

                    if (response.code() >= 200 && response.code() <= 210) {
                        if (response.body().getSuccess().equalsIgnoreCase("true")) {
                            Log.e(ContentValues.TAG, "onResponse: list size>>>>>" + response.body().getData().size());
                            if (response.body().getData().size() > 0) {
                                hospitaltypeList = response.body().getData();
                                if (hospitaltypeList.size() > 0) {
                                    callHospitalTypeSpinner();
                                }
                            }
                        }
                    }
                } catch (Exception e) {

                }

            }

            @Override
            public void onFailure(Call<HospitalTypeResponse> call, Throwable t) {
                dialog.dismiss();
            }
        });
    }

    void callHospitalTypeSpinner() {
        Log.e(TAG, "callRoleSpinner datumList.size(): " + hospitaltypeList.size());
        final HospitalTypeAdapter adapter;
        adapter = new HospitalTypeAdapter(context, android.R.layout.simple_spinner_item, hospitaltypeList);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);

        //Setting the ArrayAdapter data on the Spinner
        binding.sprTypeOfHospital.setAdapter(adapter);
        if (intHospitalId > 0) {
            int pos = -1;
            for (int i = 0; i < hospitaltypeList.size(); i++) {
                int vId = Integer.parseInt(hospitaltypeList.get(i).getHospitalTypeId());
                if (vId == intHospitalId) {
                    pos = i;
                    break;
                }
            }
            binding.sprTypeOfHospital.setSelection(pos);
        }
        binding.sprTypeOfHospital.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //strRole = parent.getItemAtPosition(position).toString().trim();
                HospitalTypeResponse.DataItem datum = adapter.getItem(position);
                strHospital = datum.getHospitalType();
                intHospitalId = Integer.parseInt(datum.getHospitalTypeId());
                Log.e(TAG, "onItemSelected:strEmpName " + strHospital + " intempId: " + intHospitalId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

}
