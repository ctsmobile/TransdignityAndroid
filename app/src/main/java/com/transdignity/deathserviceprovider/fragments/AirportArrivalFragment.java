package com.transdignity.deathserviceprovider.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import com.google.gson.JsonObject;
import com.transdignity.deathserviceprovider.MainActivity;
import com.transdignity.deathserviceprovider.R;
import com.transdignity.deathserviceprovider.fragments.ServicesDetailsFragments.AirportRemovalDetailsFragment;
import com.transdignity.deathserviceprovider.fragments.ServicesDetailsFragments.AshesDetiailsPageFragment;
import com.transdignity.deathserviceprovider.fragments.ServicesDetailsFragments.LimoDetailPageFragment;
import com.transdignity.deathserviceprovider.models.auth.LoginResponse;
import com.transdignity.deathserviceprovider.models.request.Services.AirportArrivalDepartureRequestModel;
import com.transdignity.deathserviceprovider.models.request.Services.AirportArrivalDepartureResponseModel;
import com.transdignity.deathserviceprovider.models.request.Services.LimoRequestModel;
import com.transdignity.deathserviceprovider.models.request.Services.LimoResponseModel;
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
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class AirportArrivalFragment extends Fragment implements View.OnClickListener{
    View view;
    Boolean isViewMode = false;
    Context context;
    Activity activity;
    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 100;
    double latitude;
    double longitude;
    String latLonSource, latLongDestination,latlang_from,latlang_to, latLongStatus = "";
    String userId;
    LoginResponse loginResponse;

    EditText ed_request_by,ed_requestor_contact_no,
            ed_name,ed_place_of_death;
    EditText ed_flight_name,ed_flight_number,ed_pnr_number;
    TextView tv_date_of_request,tv_date_of_birth,tv_time_of_death,tv_date_of_death,tv_estimate_date,tv_transfer_location,tv_pickup_location,tv_from,tv_to,tv_date_journey,tv_departure_time,tv_arrival_time;
    ImageView im_select_date_of_request,im_select_date_of_birth,im_time_of_death,im_date_of_death,im_estimate_date,im_transfer_location,im_pickup_location,im_from,im_to,im_date_journey,im_departure_time,im_arrival_time;
    TextView bt_next,bt_prev,bt_next1,bt_submit,bt_cancel,tv_back;
    LinearLayout ll_first,ll_second,ll_third;
    LinearLayout ll_transfer,ll_pickup,ll_from,ll_to;
    LinearProgressIndicator indicator;
    FrameLayout frame;
    public AirportArrivalFragment() {
        // Required empty public constructor
    }


    public static AirportArrivalFragment newInstance(String param1, Boolean isViewMode) {
        AirportArrivalFragment fragment = new AirportArrivalFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
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
        view= inflater.inflate(R.layout.fragment_airport_arrival, container, false);
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
        // first screen
        frame=view.findViewById(R.id.frame);
        indicator=view.findViewById(R.id.indicator);
        tv_back=view.findViewById(R.id.tv_back);
        ll_first=view.findViewById(R.id.ll_first);
        ed_request_by=view.findViewById(R.id.ed_request_by);
        tv_date_of_request=view.findViewById(R.id.tv_date_of_request);
        im_select_date_of_request=view.findViewById(R.id.im_select_date_of_request);
        ed_requestor_contact_no=view.findViewById(R.id.ed_requestor_contact_no);
        ed_name=view.findViewById(R.id.ed_name);
        tv_date_of_birth=view.findViewById(R.id.tv_date_of_birth);
        im_select_date_of_birth=view.findViewById(R.id.im_select_date_of_birth);
        ed_place_of_death=view.findViewById(R.id.ed_place_of_death);
        tv_date_of_death=view.findViewById(R.id.tv_date_of_death);
        im_date_of_death=view.findViewById(R.id.im_date_of_death);
        tv_time_of_death=view.findViewById(R.id.tv_time_of_death);
        im_time_of_death=view.findViewById(R.id.im_time_of_death);
        bt_next=view.findViewById(R.id.bt_next);
        // second screen
        ll_second=view.findViewById(R.id.ll_second);
        tv_estimate_date=view.findViewById(R.id.tv_estimate_date);
        im_estimate_date=view.findViewById(R.id.im_estimate_date);
        tv_transfer_location=view.findViewById(R.id.tv_transfer_location);
        tv_pickup_location=view.findViewById(R.id.tv_pickup_location);
        im_transfer_location=view.findViewById(R.id.im_transfer_location);
        im_pickup_location=view.findViewById(R.id.im_pickup_location);
        bt_prev=view.findViewById(R.id.bt_prev);
        bt_next1=view.findViewById(R.id.bt_next1);
        // third screen
        ll_third=view.findViewById(R.id.ll_third);
        ed_flight_name=view.findViewById(R.id.ed_flight_name);
        ed_flight_number=view.findViewById(R.id.ed_flight_number);
        ed_pnr_number=view.findViewById(R.id.ed_pnr_number);
        tv_from=view.findViewById(R.id.tv_from);
        tv_to=view.findViewById(R.id.tv_to);
        tv_date_journey=view.findViewById(R.id.tv_date_journey);
        tv_departure_time=view.findViewById(R.id.tv_departure_time);
        tv_arrival_time=view.findViewById(R.id.tv_arrival_time);
        im_from=view.findViewById(R.id.im_from);
        im_to=view.findViewById(R.id.im_to);
        im_date_journey=view.findViewById(R.id.im_date_journey);
        im_departure_time=view.findViewById(R.id.im_departure_time);
        im_arrival_time=view.findViewById(R.id.im_arrival_time);
        bt_submit=view.findViewById(R.id.bt_submit);
        bt_cancel=view.findViewById(R.id.bt_cancel);
        ll_transfer=view.findViewById(R.id.ll_transfer);
        ll_pickup=view.findViewById(R.id.ll_pickup);
        ll_from=view.findViewById(R.id.ll_from);
        ll_to=view.findViewById(R.id.ll_to);
        click();
    }

    public void click(){
        tv_back.setOnClickListener(this);
        ll_first.setOnClickListener(this);
        ll_second.setOnClickListener(this);
        ll_third.setOnClickListener(this);
        im_select_date_of_request.setOnClickListener(this);
        im_select_date_of_birth.setOnClickListener(this);
        im_time_of_death.setOnClickListener(this);
        bt_next.setOnClickListener(this);
        bt_prev.setOnClickListener(this);
        bt_next1.setOnClickListener(this);
        bt_submit.setOnClickListener(this);
        bt_cancel.setOnClickListener(this);
        im_transfer_location.setOnClickListener(this);
        im_pickup_location.setOnClickListener(this);
        im_from.setOnClickListener(this);
        im_to.setOnClickListener(this);
        im_date_journey.setOnClickListener(this);
        im_departure_time.setOnClickListener(this);
        im_arrival_time.setOnClickListener(this);
        im_estimate_date.setOnClickListener(this);
        im_date_of_death.setOnClickListener(this);
        tv_date_of_request.setOnClickListener(this);
        tv_date_of_birth.setOnClickListener(this);
        tv_date_of_death.setOnClickListener(this);
        tv_time_of_death.setOnClickListener(this);
        tv_estimate_date.setOnClickListener(this);
        ll_transfer.setOnClickListener(this);
        ll_pickup.setOnClickListener(this);
        ll_from.setOnClickListener(this);
        ll_to.setOnClickListener(this);
        tv_date_journey.setOnClickListener(this);
        tv_departure_time.setOnClickListener(this);
        tv_arrival_time.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
       switch (v.getId()){
           case R.id.ll_first:
               break;
           case R.id.ll_second:
               break;
           case R.id.ll_third:
               break;
           case R.id.im_select_date_of_request:
               //getDatepicker(context, tv_date_of_request);
               CommonUtils.getDatepicker(context, tv_date_of_request);
               break;
           case R.id.im_select_date_of_birth:
               getDatepicker(context, tv_date_of_birth);
               break;
           case R.id.im_date_of_death:
               getDatepicker(context, tv_date_of_death);
               break;
           case R.id.im_time_of_death:
               getTime(context, tv_time_of_death);
               break;

           case R.id.tv_date_of_request:
               //getDatepicker(context, tv_date_of_request);
               CommonUtils.getDatepicker(context, tv_date_of_request);

               break;
           case R.id.tv_date_of_birth:
               getDatepicker(context, tv_date_of_birth);
               break;
           case R.id.tv_date_of_death:
               getDatepicker(context, tv_date_of_death);
               break;
           case R.id.tv_time_of_death:
               getTime(context, tv_time_of_death);
               break;
           case R.id.bt_next:
               validationFirst();
               break;
           case R.id.bt_prev:
               ll_first.setVisibility(View.VISIBLE);
               ll_second.setVisibility(View.GONE);
               break;
           case R.id.bt_next1:
               validationSecond();
               break;
           case R.id.bt_submit:
               validationThird();
               break;
           case R.id.bt_cancel:
               break;
           case R.id.im_estimate_date:
               getDatepicker(context, tv_estimate_date);
               break;
           case R.id.tv_estimate_date:
               getDatepicker(context, tv_estimate_date);
               break;
           case R.id.ll_transfer:
                latLongStatus="transfer_location";
                callPlaceSearchIntent();
               break;
           case R.id.ll_pickup:
               latLongStatus="pickup_location";
               callPlaceSearchIntent();
               break;
           case R.id.ll_from:
               latLongStatus="from";
               callPlaceSearchIntent();
               break;
           case R.id.ll_to:
               latLongStatus="to";
               callPlaceSearchIntent();
               break;
           case R.id.im_date_journey:
               getDatepicker(context, tv_date_journey);
               break;
           case R.id.im_departure_time:
               getTime(context, tv_departure_time);
               break;
           case R.id.im_arrival_time:
               getTime(context, tv_arrival_time);
               break;
           case R.id.tv_date_journey:
               getDatepicker(context, tv_date_journey);
               break;
           case R.id.tv_departure_time:
               getTime(context, tv_departure_time);
               break;
           case R.id.tv_arrival_time:
               getTime(context, tv_arrival_time);
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
            if (latLongStatus.equalsIgnoreCase("transfer_location")) {
                latLonSource = String.valueOf(latitude) + "," + String.valueOf(longitude);
                tv_transfer_location.setText(address.toString());
                //Toast.makeText(context, "" + latLonSource, Toast.LENGTH_SHORT).show();
            } else if(latLongStatus.equalsIgnoreCase("pickup_location")) {
                latLongDestination = String.valueOf(latitude) + "," + String.valueOf(longitude);
                tv_pickup_location.setText(address.toString());
                // Toast.makeText(context, "" + latLongDestination, Toast.LENGTH_SHORT).show();
            }else if(latLongStatus.equalsIgnoreCase("from")) {
                latlang_from = String.valueOf(latitude) + "," + String.valueOf(longitude);
                tv_from.setText(address.toString());
                // Toast.makeText(context, "" + latLongDestination, Toast.LENGTH_SHORT).show();
            }else if(latLongStatus.equalsIgnoreCase("to")) {
                latlang_to = String.valueOf(latitude) + "," + String.valueOf(longitude);
                tv_to.setText(address.toString());
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


    public void validationFirst(){
        if (TextUtils.isEmpty(ed_request_by.getText().toString())){
            Snackbar.make(view.findViewById(R.id.l_layout),"Please Enter Requested By",Snackbar.LENGTH_LONG).setAction("Action",null).show();
        }else if (TextUtils.isEmpty(tv_date_of_request.getText().toString())){
            Snackbar.make(view.findViewById(R.id.l_layout),"Please Enter Date Of Request",Snackbar.LENGTH_LONG).setAction("Action",null).show();
        }else if (TextUtils.isEmpty(ed_requestor_contact_no.getText().toString())){
            Snackbar.make(view.findViewById(R.id.l_layout),"Please Enter Requestor Contact No",Snackbar.LENGTH_LONG).setAction("Action",null).show();
        }else if (TextUtils.isEmpty(ed_name.getText().toString())){
            Snackbar.make(view.findViewById(R.id.l_layout),"Please Enter Name",Snackbar.LENGTH_LONG).setAction("Action",null).show();
        }else if (TextUtils.isEmpty(tv_date_of_birth.getText().toString())){
            Snackbar.make(view.findViewById(R.id.l_layout),"Please Enter Death Of Birth",Snackbar.LENGTH_LONG).setAction("Action",null).show();
        }else if (TextUtils.isEmpty(ed_place_of_death.getText().toString())){
            Snackbar.make(view.findViewById(R.id.l_layout),"Please Enter Place Of Death",Snackbar.LENGTH_LONG).setAction("Action",null).show();
        }else if (TextUtils.isEmpty(tv_date_of_death.getText().toString())){
            Snackbar.make(view.findViewById(R.id.l_layout),"Please Enter Date Of Death",Snackbar.LENGTH_LONG).setAction("Action",null).show();
        }else if (TextUtils.isEmpty(tv_time_of_death.getText().toString())){
            Snackbar.make(view.findViewById(R.id.l_layout),"Please Enter Time Of Death",Snackbar.LENGTH_LONG).setAction("Action",null).show();
        }else {
            ll_first.setVisibility(View.GONE);
            ll_second.setVisibility(View.VISIBLE);
        }
    }

    public void validationSecond(){
        if (TextUtils.isEmpty(tv_estimate_date.getText().toString())){
            Snackbar.make(view.findViewById(R.id.l_layout),"Please Enter Estimated Body",Snackbar.LENGTH_LONG).setAction("Action",null).show();
        }else if (TextUtils.isEmpty(tv_transfer_location.getText().toString())){
            Snackbar.make(view.findViewById(R.id.l_layout),"Please Enter Transfer Location",Snackbar.LENGTH_LONG).setAction("Action",null).show();
        }else if (TextUtils.isEmpty(tv_pickup_location.getText().toString())){
            Snackbar.make(view.findViewById(R.id.l_layout),"Please Enter Pickup Location",Snackbar.LENGTH_LONG).setAction("Action",null).show();
        }else {
            ll_second.setVisibility(View.GONE);
            ll_third.setVisibility(View.VISIBLE);
        }
    }

    public void validationThird(){
        if (TextUtils.isEmpty(tv_from.getText().toString())){
            Snackbar.make(view.findViewById(R.id.l_layout),"Please Enter From Address",Snackbar.LENGTH_LONG).setAction("Action",null).show();
        }else if (TextUtils.isEmpty(tv_to.getText().toString())){
            Snackbar.make(view.findViewById(R.id.l_layout),"Please Enter To Address",Snackbar.LENGTH_LONG).setAction("Action",null).show();
        }else if (TextUtils.isEmpty(tv_date_journey.getText().toString())){
            Snackbar.make(view.findViewById(R.id.l_layout),"Please Enter Date Of Journey",Snackbar.LENGTH_LONG).setAction("Action",null).show();
        }else if (TextUtils.isEmpty(tv_departure_time.getText().toString())){
            Snackbar.make(view.findViewById(R.id.l_layout),"Please Enter Time Of Departure ",Snackbar.LENGTH_LONG).setAction("Action",null).show();
        }else if (TextUtils.isEmpty(tv_arrival_time.getText().toString())){
            Snackbar.make(view.findViewById(R.id.l_layout),"Please Enter Time Of Arrival ",Snackbar.LENGTH_LONG).setAction("Action",null).show();
        }else if (TextUtils.isEmpty(ed_flight_name.getText().toString())){
            Snackbar.make(view.findViewById(R.id.l_layout),"Please Enter Flight Name",Snackbar.LENGTH_LONG).setAction("Action",null).show();
        }else if (TextUtils.isEmpty(ed_flight_number.getText().toString())){
            Snackbar.make(view.findViewById(R.id.l_layout),"Please Enter Flight Number",Snackbar.LENGTH_LONG).setAction("Action",null).show();
        }else if (TextUtils.isEmpty(ed_pnr_number.getText().toString())){
            Snackbar.make(view.findViewById(R.id.l_layout),"Please Enter PNR Number",Snackbar.LENGTH_LONG).setAction("Action",null).show();
        }else {
            long date = System.currentTimeMillis();
            SimpleDateFormat date1 = new SimpleDateFormat("dd/MM/yyyy HH:mm"); // for current date
            SimpleDateFormat time1 = new SimpleDateFormat("kk:mm:ss");
           String request_generate_date_time = date1.format(date);
            ll_first.setVisibility(View.GONE);
            ll_second.setVisibility(View.GONE);
            ll_third.setVisibility(View.VISIBLE);
            JsonObject jsonObject_flight = new JsonObject();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("request_created_by",userId);
            jsonObject.addProperty("service_id","8");
            jsonObject.addProperty("requested_by",ed_request_by.getText().toString());
            jsonObject.addProperty("request_date",request_generate_date_time);
            jsonObject.addProperty("date_of_request",tv_date_of_request.getText().toString());
            jsonObject.addProperty("requestor_contact_number",ed_requestor_contact_no.getText().toString());
            jsonObject.addProperty("decendent_name",ed_name.getText().toString());
            jsonObject.addProperty("decendent_dob",tv_date_of_birth.getText().toString());
            jsonObject.addProperty("decendent_pod",ed_place_of_death.getText().toString());
            jsonObject.addProperty("decendent_dod",tv_date_of_death.getText().toString());
            jsonObject.addProperty("decendent_tod",tv_time_of_death.getText().toString());
            jsonObject.addProperty("estimated_date_arrival_of_decendent",tv_estimate_date.getText().toString());
            jsonObject.addProperty("transferred_address",tv_transfer_location.getText().toString());
            jsonObject.addProperty("transferred_address_latlong",latLonSource);
            jsonObject.addProperty("decendent_pickedaddress_ofbody",tv_pickup_location.getText().toString());
            jsonObject.addProperty("decendent_pickedaddress_ofbody_latlong",latLongDestination);
            jsonObject.add("flightdetail",jsonObject_flight);
            jsonObject_flight.addProperty("from_address",tv_from.getText().toString());
            jsonObject_flight.addProperty("from_address_latlong",latlang_from);
            jsonObject_flight.addProperty("to_address",tv_to.getText().toString());
            jsonObject_flight.addProperty("to_address_latlong",latlang_to);
            jsonObject_flight.addProperty("date_of_journey",tv_date_journey.getText().toString());
            jsonObject_flight.addProperty("time_of_departure",tv_departure_time.getText().toString());
            jsonObject_flight.addProperty("time_of_arrival",tv_arrival_time.getText().toString());
            jsonObject_flight.addProperty("flight_name",ed_flight_name.getText().toString());
            jsonObject_flight.addProperty("flight_number",ed_flight_number.getText().toString());
            jsonObject_flight.addProperty("flight_pnr",ed_pnr_number.getText().toString());
            airportRemovalApi(jsonObject);
        }
    }

    public void airportRemovalApi(JsonObject jsonObject) {
        indicator.setVisibility(View.VISIBLE);
        frame.setVisibility(View.VISIBLE);
        final LoadingProgressDialog dialog = new LoadingProgressDialog(context, "Please wait...");
        dialog.setCancelable(false);
        dialog.show();
        ApiInterface apiInterface = ApiClients.getClient(context).create(ApiInterface.class);
        final AirportArrivalDepartureRequestModel req = new AirportArrivalDepartureRequestModel();


        Call<AirportArrivalDepartureResponseModel> call = apiInterface.AirportRemovalServiceApi(loginResponse.getData().getToken(),jsonObject );

        call.enqueue(new Callback<AirportArrivalDepartureResponseModel>() {
            @Override
            public void onResponse(Call<AirportArrivalDepartureResponseModel> call, Response<AirportArrivalDepartureResponseModel> response) {
                dialog.dismiss();
                indicator.setVisibility(View.GONE);
                frame.setVisibility(View.GONE);
                try {
                    if (response.code() >= 200 && response.code() <= 210) {
                        if (response.body().getSuccess().equalsIgnoreCase("true")) {
                            Toast.makeText(context, response.body().getData().getRequestId().toString(), Toast.LENGTH_SHORT).show();
                            String requesrId = response.body().getData().getRequestId().toString();
                            Fragment fragment = AirportRemovalDetailsFragment.newInstance(requesrId,"Airport");
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
            public void onFailure(Call<AirportArrivalDepartureResponseModel> call, Throwable t) {
                Toast.makeText(context, "Something Went Wrong!", Toast.LENGTH_LONG).show();
                dialog.dismiss();
                indicator.setVisibility(View.GONE);
                frame.setVisibility(View.GONE);
            }
        });

    }

}