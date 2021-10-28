package com.transdignity.deathserviceprovider.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
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
import com.transdignity.deathserviceprovider.fragments.ServicesDetailsFragments.LimoDetailPageFragment;
import com.transdignity.deathserviceprovider.models.auth.LoginResponse;
import com.transdignity.deathserviceprovider.models.checkDriver.CheckDriverRequestModel;
import com.transdignity.deathserviceprovider.models.checkDriver.CheckDriverResponseModel;
import com.transdignity.deathserviceprovider.models.request.Services.CourierRequestModel;
import com.transdignity.deathserviceprovider.models.request.Services.CourierResponseModel;
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
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class CourierServiceFragment extends Fragment implements View.OnClickListener {

    View view;
    Data data;
    Boolean isViewMode = false;
    Context context;
    Activity activity;
    TextView tv_pick_up_location,tv_drop_location,tv_start_date,tv_select_time;
    ImageView im_pick_up_location,im_drop_location,im_select_date,im_select_time;
    EditText ed_sender_first_name,ed_sender_last_name,ed_sender_mobile_no,
            ed_date,ed_receiver_first_name,ed_receiver_last_name,ed_receiver_mobile_no,ed_description,ed_instruction,ed_item_name;
    TextView ll_submit,ll_cancel,ll_retry;
    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 100;
    double latitude;
    double longitude;
    String latLonSource, latLongDestination, latLongStatus = "from";
    String userId;
    LoginResponse loginResponse;
    TextView tv_back;
    String request_id;
    LinearLayout ll_pickup,ll_drop;
    LinearProgressIndicator indicator;
    FrameLayout frame;
    int count=0;
    String req_sent_driver;
    public CourierServiceFragment() {
        // Required empty public constructor
    }

    public static CourierServiceFragment newInstance(Data data, Boolean isViewMode) {
        CourierServiceFragment fragment = new CourierServiceFragment();
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
        view= inflater.inflate(R.layout.fragment_courier_service, container, false);
        init();
        loginResponse = (LoginResponse) GlobalValues.getPreferenceManager().getObject(PreferenceManager.LoginResponseKey, LoginResponse.class);

        if (loginResponse != null) {
            userId = loginResponse.getData().getId();
        }
        //TODO: Initialize Place Api
        if (!Places.isInitialized()) {
            Places.initialize(context, MyConstants.PLACEAPIKEY);
        }
        final Handler ha=new Handler();
        ha.postDelayed(new Runnable() {

            @Override
            public void run() {
                if(count>0&&count<=9){
                    checkDriverApi(request_id,req_sent_driver);

                }
                ha.postDelayed(this, 10000);
            }
        }, 10000);
        return view;
    }
    public void init(){
        frame=view.findViewById(R.id.frame);
        indicator=view.findViewById(R.id.indicator);
        ll_pickup=view.findViewById(R.id.ll_pickup);
        ll_drop=view.findViewById(R.id.ll_drop);
        tv_start_date=view.findViewById(R.id.tv_start_date);
        tv_select_time=view.findViewById(R.id.tv_select_time);
        im_select_date=view.findViewById(R.id.im_select_date);
        im_select_time=view.findViewById(R.id.im_select_time);

        tv_pick_up_location=view.findViewById(R.id.tv_pick_up_location);
        tv_drop_location=view.findViewById(R.id.tv_drop_location);
        im_pick_up_location=view.findViewById(R.id.im_pick_up_location);
        im_drop_location=view.findViewById(R.id.im_drop_location);
        ed_sender_first_name=view.findViewById(R.id.ed_sender_first_name);
        ed_sender_last_name=view.findViewById(R.id.ed_sender_last_name);
        ed_sender_mobile_no=view.findViewById(R.id.ed_sender_mobile_no);
        ed_date=view.findViewById(R.id.ed_date);
        ed_receiver_first_name=view.findViewById(R.id.ed_receiver_first_name);
        ed_receiver_last_name=view.findViewById(R.id.ed_receiver_last_name);
        ed_receiver_mobile_no=view.findViewById(R.id.ed_receiver_mobile_no);
        ed_description=view.findViewById(R.id.ed_description);
        ed_instruction=view.findViewById(R.id.ed_instruction);
        ll_submit=view.findViewById(R.id.ll_submit);
        ll_cancel=view.findViewById(R.id.ll_cancel);
        tv_back=view.findViewById(R.id.tv_back);
        ed_item_name=view.findViewById(R.id.ed_item_name);
        ll_retry=view.findViewById(R.id.ll_retry);


        click();
    }

    public void click(){
        im_pick_up_location.setOnClickListener(this);
        im_drop_location.setOnClickListener(this);
        ll_submit.setOnClickListener(this);
        ll_cancel.setOnClickListener(this);
        tv_back.setOnClickListener(this);
        ll_retry.setOnClickListener(this);
        im_select_date.setOnClickListener(this);
        im_select_time.setOnClickListener(this);
        ll_pickup.setOnClickListener(this);
        ll_drop.setOnClickListener(this);
        tv_start_date.setOnClickListener(this);
        tv_select_time.setOnClickListener(this);
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
            case R.id.im_select_date:
                getDatepicker(context, tv_start_date);
                break;
            case R.id.tv_start_date:
                getDatepicker(context, tv_start_date);
                break;
            case R.id.im_select_time:
                getTime(context, tv_select_time);
                break;
            case R.id.tv_select_time:
                getTime(context, tv_select_time);
                break;
            case R.id.ll_submit:
                validation();
                break;
            case R.id.ll_retry:
                validation();
                break;
            case R.id.ll_cancel:
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
    public void getDatepicker(Context context, final TextView editText) {
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
                        }else if(month.length()==1){
                            String curDate = dayOfMonth + "/" + "0"+(monthOfYear + 1) + "/" + year;
                            editText.setText(curDate);
                        }else if((day.length()==1)&&(month.length()==1)){
                            String curDate = "0"+dayOfMonth + "/" + "0"+(monthOfYear + 1) + "/" + year;
                            editText.setText(curDate);
                        }else {
                            String curDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                            editText.setText(curDate);
                        }
                        if((day.length()==1)&&(month.length()==1)){
                            String curDate = "0"+dayOfMonth + "/" + "0"+(monthOfYear + 1) + "/" + year;
                            editText.setText(curDate);
                        }

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();

    }

    public static void getTime(Context context, final TextView editText) {
        int mHour, mMinute;
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(context,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        // String  strTime = hourOfDay + ":" + minute;
                        String hr= String.valueOf(Integer.valueOf(hourOfDay));
                        String mn= String.valueOf(Integer.valueOf(minute));
                        if(hr.length()==1){
                            editText.setText("0"+hourOfDay + ":" + minute);
                        }else if(mn.length()==1){
                            editText.setText(hourOfDay + ":" + "0"+minute);
                        }else if(hr.length()==1&&mn.length()==1){
                            editText.setText("0"+hourOfDay + ":" + "0"+minute);
                        }else {
                            editText.setText(hourOfDay + ":" + minute);

                        }
                        if(hr.length()==1&&mn.length()==1){
                            editText.setText("0"+hourOfDay + ":" + "0"+minute);
                        }
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
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
            Snackbar.make(view.findViewById(R.id.ll_courier),"Please enter Pick up location",Snackbar.LENGTH_LONG).setAction("Action",null).show();
        }else if (TextUtils.isEmpty(ed_item_name.getText().toString())){
            Snackbar.make(view.findViewById(R.id.ll_courier),"Please enter Item Name",Snackbar.LENGTH_LONG).setAction("Action",null).show();
        } else if (TextUtils.isEmpty(ed_sender_first_name.getText().toString())){
            Snackbar.make(view.findViewById(R.id.ll_courier),"Please enter Sender First Name",Snackbar.LENGTH_LONG).setAction("Action",null).show();
        }else if (TextUtils.isEmpty(ed_sender_last_name.getText().toString())){
            Snackbar.make(view.findViewById(R.id.ll_courier),"Please enter Sender Last Name",Snackbar.LENGTH_LONG).setAction("Action",null).show();
        }else if (TextUtils.isEmpty(ed_sender_mobile_no.getText().toString())){
            Snackbar.make(view.findViewById(R.id.ll_courier),"Please enter Sender Mobile No",Snackbar.LENGTH_LONG).setAction("Action",null).show();
        }/*else if (TextUtils.isEmpty(tv_start_date.getText().toString())){
            Snackbar.make(view.findViewById(R.id.ll_courier),"Please Enter Select Date ",Snackbar.LENGTH_LONG).setAction("Action",null).show();
        }else if (TextUtils.isEmpty(tv_select_time.getText().toString())){
            Snackbar.make(view.findViewById(R.id.ll_courier),"Please Enter Select Time ",Snackbar.LENGTH_LONG).setAction("Action",null).show();
        }*/else if (TextUtils.isEmpty(tv_drop_location.getText().toString())){
            Snackbar.make(view.findViewById(R.id.ll_courier),"Please enter Drop Location",Snackbar.LENGTH_LONG).setAction("Action",null).show();
        }else if (TextUtils.isEmpty(ed_receiver_first_name.getText().toString())){
            Snackbar.make(view.findViewById(R.id.ll_courier),"Please enter Receiver First Name",Snackbar.LENGTH_LONG).setAction("Action",null).show();
        }else if (TextUtils.isEmpty(ed_receiver_last_name.getText().toString())){
            Snackbar.make(view.findViewById(R.id.ll_courier),"Please enter Receiver Last Name",Snackbar.LENGTH_LONG).setAction("Action",null).show();
        }else if (TextUtils.isEmpty(ed_receiver_mobile_no.getText().toString())){
            Snackbar.make(view.findViewById(R.id.ll_courier),"Please enter Receiver Mobile No",Snackbar.LENGTH_LONG).setAction("Action",null).show();
        }else if (TextUtils.isEmpty(ed_description.getText().toString())){
            Snackbar.make(view.findViewById(R.id.ll_courier),"Please enter Description",Snackbar.LENGTH_LONG).setAction("Action",null).show();
        }else if (TextUtils.isEmpty(ed_instruction.getText().toString())){
            Snackbar.make(view.findViewById(R.id.ll_courier),"Please enter Instructions",Snackbar.LENGTH_LONG).setAction("Action",null).show();
        } else {
            CourierServiceApi();
        }


    }

    public void CourierServiceApi() {
        long date = System.currentTimeMillis();
        SimpleDateFormat date1 = new SimpleDateFormat("dd/MM/yyyy HH:mm"); // for current date
        SimpleDateFormat time1 = new SimpleDateFormat("kk:mm:ss");
        String request_generate_date_time = date1.format(date);
        String current_date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
        final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        String current_time = dateFormat.format(new Date());
        final LoadingProgressDialog dialog = new LoadingProgressDialog(context, "Please wait...");
        dialog.setCancelable(false);
        dialog.show();
        ApiInterface apiInterface = ApiClients.getClient(context).create(ApiInterface.class);
        final CourierRequestModel req = new CourierRequestModel();
        req.setFcmKey(GlobalValues.getPreferenceManager().getFcmString(PreferenceManager.FCMKEY));
        req.setDevice("Android");
        req.setDeviceId(CommonUtils.getDeviceId(context));
       /* req.setFromAddressLatlong("28.6222331,77.3830289");
        req.setToAddressLatlong("27.867,131.8789");*/
        req.setPickupLocationLatlong(latLonSource);
        req.setDropLocationLatlong(latLongDestination);

        req.setRequestCreatedBy(Integer.parseInt(userId));
        req.setPickupLocation(tv_pick_up_location.getText().toString().trim());
        req.setDropLocation(tv_drop_location.getText().toString().trim());
        req.setSenderFirstName(ed_sender_first_name.getText().toString().trim());
        req.setSenderLastName(ed_sender_last_name.getText().toString().trim());
        req.setSenderMobileNumber(ed_sender_mobile_no.getText().toString().trim());
        req.setDateTime(current_date+" "+current_time);
        req.setRecieverFirstName(ed_receiver_first_name.getText().toString().trim());
        req.setRecieverLastName(ed_receiver_last_name.getText().toString().trim());
        req.setRecieverMobileNumber(ed_receiver_mobile_no.getText().toString().trim());
        req.setDescription(ed_description.getText().toString().trim());
        req.setInstructuionToCarryitem(ed_instruction.getText().toString().trim());
        req.setItemName(ed_item_name.getText().toString().trim());
        req.setRequestid(request_id);
        req.setRequestDate(request_generate_date_time);

        req.setServiceId(6);

        Call<CourierResponseModel> call = apiInterface.CourierServiceApi(loginResponse.getData().getToken(), req);

        call.enqueue(new Callback<CourierResponseModel>() {
            @Override
            public void onResponse(Call<CourierResponseModel> call, Response<CourierResponseModel> response) {
                dialog.dismiss();
                try {
                    if (response.code() >= 200 && response.code() <= 210) {
                        if (response.body().getSuccess().equalsIgnoreCase("true")) {
                            Toast.makeText(context, response.body().getData().getRequestId().toString(), Toast.LENGTH_SHORT).show();
                            req_sent_driver=response.body().getData().getRequestSentTodriver();
                            request_id = response.body().getData().getRequestId().toString();
                            if(req_sent_driver.equals("0")){
                                request_id = response.body().getData().getRequestId().toString();
                                ll_retry.setVisibility(View.VISIBLE);
                                ll_submit.setVisibility(View.GONE);
                            }else {
                                checkDriverApi(request_id,req_sent_driver);

                            }
                            /*String req_sent_driver=response.body().getData().getRequestSentTodriver();
                            if(req_sent_driver.equals("1")){
                                String requesrId = response.body().getData().getRequestId().toString();
                                Fragment fragment = LimoDetailPageFragment.newInstance(requesrId,"Courier");
                                String fragmtStatusTag2 = fragment.getClass().getName();
                                GlobalValues.getInstance().setFramgentTag(fragmtStatusTag2);
                                ((MainActivity) activity).selectedNavItem(R.id.navigation_home);
                                CommonUtils.loadFragment(context, fragment, fragmtStatusTag2);
                            }else if(req_sent_driver.equals("0"))  {
                                request_id = response.body().getData().getRequestId().toString();
                                ll_retry.setVisibility(View.VISIBLE);
                                ll_submit.setVisibility(View.GONE);
                            }*/
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
            public void onFailure(Call<CourierResponseModel> call, Throwable t) {
                Toast.makeText(context, "No Driver Found!", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });

    }
    public void checkDriverApi(String req_id,String assign_id) {
        count=count+1;
        indicator.setVisibility(View.VISIBLE);
        frame.setVisibility(View.VISIBLE);
        frame.setEnabled(false);
        ll_cancel.setEnabled(false);
        ll_submit.setEnabled(false);
        ApiInterface apiInterface = ApiClients.getClient(context).create(ApiInterface.class);
        final CheckDriverRequestModel req = new CheckDriverRequestModel();
        req.setRequestId(req_id);
        req.setAssignUserId(assign_id);
        req.setTimes(String.valueOf(count));

        Call<CheckDriverResponseModel> call = apiInterface.checkDriverApi(loginResponse.getData().getToken(), req);

        call.enqueue(new Callback<CheckDriverResponseModel>() {
            @Override
            public void onResponse(Call<CheckDriverResponseModel> call, Response<CheckDriverResponseModel> response) {
                try {
                    if (response.code() >= 200 && response.code() <= 210) {
                        if (response.body().getSuccess().equalsIgnoreCase("true")) {
                            String status=response.body().getmStatus().toString();
                            if(status.equals("reject")){
                                Toast.makeText(context, "Request Rejected by Driver", Toast.LENGTH_SHORT).show();
                                indicator.setVisibility(View.GONE);
                                frame.setVisibility(View.GONE);
                                ll_cancel.setEnabled(true);
                                ll_submit.setEnabled(true);
                            }else if(status.equals("accept")){
                                Toast.makeText(context, "Request Accepted by Driver", Toast.LENGTH_SHORT).show();
                                indicator.setVisibility(View.GONE);
                                frame.setVisibility(View.GONE);
                                ll_cancel.setEnabled(true);
                                ll_submit.setEnabled(true);
                                Fragment fragment = new HomeFragment();
                                String fragmtStatusTag2 = fragment.getClass().getName();
                                GlobalValues.getInstance().setFramgentTag(fragmtStatusTag2);
                                ((MainActivity) activity).selectedNavItem(R.id.navigation_home);
                                CommonUtils.loadFragment(context, fragment, fragmtStatusTag2);
                            }else if(status.equals("notresponded")){
                                Toast.makeText(context, "Not Respond by Driver", Toast.LENGTH_SHORT).show();
                                indicator.setVisibility(View.GONE);
                                frame.setVisibility(View.GONE);
                                ll_cancel.setEnabled(true);
                                ll_submit.setEnabled(true);
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
                            String status = jsonObjectError.optString("status");
                            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                            if(status.equals("notresponded")){
                                // Toast.makeText(context, "Not Respond by Ride Provider", Toast.LENGTH_SHORT).show();
                                indicator.setVisibility(View.GONE);
                                frame.setVisibility(View.GONE);
                                ll_cancel.setEnabled(true);
                                ll_submit.setEnabled(true);
                                count=0;
                                Fragment fragment = new CourierServiceFragment();
                                String fragmtStatusTag2 = fragment.getClass().getName();
                                GlobalValues.getInstance().setFramgentTag(fragmtStatusTag2);
                                ((MainActivity) activity).selectedNavItem(R.id.navigation_home);
                                CommonUtils.loadFragment(context, fragment, fragmtStatusTag2);
                            }else if(status.equals("accept")){
                                // Toast.makeText(context, "Request Accepted by Driver", Toast.LENGTH_SHORT).show();
                                indicator.setVisibility(View.GONE);
                                frame.setVisibility(View.GONE);
                                ll_cancel.setEnabled(true);
                                ll_submit.setEnabled(true);
                                Fragment fragment = new HomeFragment();
                                String fragmtStatusTag2 = fragment.getClass().getName();
                                GlobalValues.getInstance().setFramgentTag(fragmtStatusTag2);
                                ((MainActivity) activity).selectedNavItem(R.id.navigation_home);
                                CommonUtils.loadFragment(context, fragment, fragmtStatusTag2);
                                count=10;
                            }else if(status.equals("reject")){
                                //Toast.makeText(context, "Request Rejected by Driver", Toast.LENGTH_SHORT).show();
                                indicator.setVisibility(View.GONE);
                                frame.setVisibility(View.GONE);
                                ll_cancel.setEnabled(true);
                                ll_submit.setEnabled(true);
                                Fragment fragment = new CourierServiceFragment();
                                String fragmtStatusTag2 = fragment.getClass().getName();
                                GlobalValues.getInstance().setFramgentTag(fragmtStatusTag2);
                                ((MainActivity) activity).selectedNavItem(R.id.navigation_home);
                                CommonUtils.loadFragment(context, fragment, fragmtStatusTag2);
                                count=10;

                            }


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
            public void onFailure(Call<CheckDriverResponseModel> call, Throwable t) {
                Toast.makeText(context, "SNo Driver Found!", Toast.LENGTH_LONG).show();
                indicator.setVisibility(View.GONE);
                frame.setVisibility(View.GONE);

            }
        });

    }

}