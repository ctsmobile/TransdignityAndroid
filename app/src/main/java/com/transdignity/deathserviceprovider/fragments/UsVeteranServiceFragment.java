package com.transdignity.deathserviceprovider.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
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
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
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
import com.transdignity.deathserviceprovider.adapters.spinnerAdapter;
import com.transdignity.deathserviceprovider.fragments.ServicesDetailsFragments.AshesDetiailsPageFragment;
import com.transdignity.deathserviceprovider.fragments.ServicesDetailsFragments.FlowerDetailPageFragment;
import com.transdignity.deathserviceprovider.fragments.ServicesDetailsFragments.UsVeteranDeatilFragment;
import com.transdignity.deathserviceprovider.models.auth.LoginResponse;
import com.transdignity.deathserviceprovider.models.country.CountriesModel;
import com.transdignity.deathserviceprovider.models.payment.PaymentHistoryModel;
import com.transdignity.deathserviceprovider.models.request.Services.FlowerRequestModel;
import com.transdignity.deathserviceprovider.models.request.Services.FlowerResponseModel;
import com.transdignity.deathserviceprovider.models.request.Services.UsVeteranRequestModel;
import com.transdignity.deathserviceprovider.models.request.Services.UsVeteranResponseModel;
import com.transdignity.deathserviceprovider.models.request.Services.UsVetranServiceRequestModel;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class UsVeteranServiceFragment extends Fragment implements View.OnClickListener {
    View view;
    Data data;
    Boolean isViewMode = false;
    Context context;
    Activity activity;
    TextView tv_back;
    EditText ed_name_of_us_veteran,ed_branch_of_military,ed_requested_by,ed_next_of_kin,ed_next_of_kin_relationship;
    Spinner spinner_country,spinner_trip_type;
    TextView tv_start_date,tv_received_time,tv_completed_time,tv_pickup,tv_drop,tv_date_of_birth,tv_age;
    TextView tv_date_of_death,tv_time_of_death;
    RadioButton rb_yes,rb_no,rbp_yes,rbp_no,rbextra_yes,rbextra_no;
    EditText et_personel_effect_list,et_extra_list;
    TextView bt_submit,bt_cancel,bt_next;
    LinearLayout ll_date,ll_received_time,ll_completed_time,ll_pickup,
            ll_drop,ll_date_of_birth,ll_date_of_death,ll_time_of_death;
    String latLongStatus="";
    String latlang_from,latlang_to;
    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 100;
    double latitude;
    double longitude;
    String userId;
    LoginResponse loginResponse;
    LinearLayout ll_first, ll_second;
    List<CountriesModel.Datum> dataItems = new ArrayList<>();
    List<String> countries_ArrayList = new ArrayList<>();
    List<String> objEmpty;
    String country_name;
    ImageView im_body_release_yes,im_body_release_no,im_personal_effect_yes,
            im_personal_effect_no,im_extra_item_yes,im_extra_item_no;
    String release_body_status="0";
    String personal_effect_status="0";
    String extra_item_status="0";
    String request_generate_date_time;
    LinearProgressIndicator indicator;
    FrameLayout frame;
    public UsVeteranServiceFragment() {
        // Required empty public constructor
    }

    public static UsVeteranServiceFragment newInstance(Data data, Boolean isViewMode) {
        UsVeteranServiceFragment fragment = new UsVeteranServiceFragment();

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
        view= inflater.inflate(R.layout.fragment_us_veteran_service, container, false);
        init();
        loginResponse = (LoginResponse) GlobalValues.getPreferenceManager().getObject(PreferenceManager.LoginResponseKey, LoginResponse.class);

        if (loginResponse != null) {
            userId = loginResponse.getData().getId();
        }
        //TODO: Initialize Place Api
        if (!Places.isInitialized()) {
            Places.initialize(context, MyConstants.PLACEAPIKEY);
        }
        getCountryListApi();
        objEmpty = new ArrayList<String>();
        objEmpty.add("Select item");
        spinner_country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {


                if (spinner_country.getSelectedItem() == "Select Country" || spinner_country.getSelectedItem() == "Select item") {

                } else {
                    country_name = spinner_country.getItemAtPosition(spinner_country.getSelectedItemPosition()).toString();
                    //String  storeId = dataItems.get(position).getId();
                   // Toast.makeText(getActivity(), ""+storeId, Toast.LENGTH_SHORT).show();
                   // CarBrand(storeId);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }

    public void init(){
        frame=view.findViewById(R.id.frame);
        indicator=view.findViewById(R.id.indicator);
        tv_back = view.findViewById(R.id.tv_back);
        spinner_country = view.findViewById(R.id.spinner_country);
        ll_first = view.findViewById(R.id.ll_first);
        ll_second = view.findViewById(R.id.ll_second);
        ed_name_of_us_veteran = view.findViewById(R.id.ed_name_of_us_veteran);
        ed_branch_of_military = view.findViewById(R.id.ed_branch_of_military);
        ed_requested_by = view.findViewById(R.id.ed_requested_by);

        spinner_trip_type = view.findViewById(R.id.spinner_trip_type);

        tv_start_date = view.findViewById(R.id.tv_start_date);
        tv_received_time = view.findViewById(R.id.tv_received_time);
        tv_completed_time = view.findViewById(R.id.tv_completed_time);
        tv_pickup = view.findViewById(R.id.tv_pickup);
        tv_drop = view.findViewById(R.id.tv_drop);
        tv_date_of_birth = view.findViewById(R.id.tv_date_of_birth);
        tv_age = view.findViewById(R.id.tv_age);
        tv_date_of_death = view.findViewById(R.id.tv_date_of_death);
        tv_time_of_death = view.findViewById(R.id.tv_time_of_death);
        ed_next_of_kin_relationship = view.findViewById(R.id.ed_next_of_kin_relationship);
        ed_next_of_kin = view.findViewById(R.id.ed_next_of_kin);
        rb_yes = view.findViewById(R.id.rb_yes);
        rb_no = view.findViewById(R.id.rb_no);
        rbp_yes = view.findViewById(R.id.rbp_yes);
        rbp_no = view.findViewById(R.id.rbp_no);
        rbextra_yes = view.findViewById(R.id.rbextra_yes);
        rbextra_no = view.findViewById(R.id.rbextra_no);
        et_personel_effect_list = view.findViewById(R.id.et_personel_effect_list);
        et_extra_list = view.findViewById(R.id.et_extra_list);

        bt_submit = view.findViewById(R.id.bt_submit);
        bt_cancel = view.findViewById(R.id.bt_cancel);
        bt_next = view.findViewById(R.id.bt_next);

        ll_date = view.findViewById(R.id.ll_date);
        ll_received_time = view.findViewById(R.id.ll_received_time);
        ll_completed_time = view.findViewById(R.id.ll_completed_time);
        ll_pickup = view.findViewById(R.id.ll_pickup);
        ll_drop = view.findViewById(R.id.ll_drop);
        ll_date_of_birth = view.findViewById(R.id.ll_date_of_birth);
        ll_date_of_death = view.findViewById(R.id.ll_date_of_death);
        ll_time_of_death = view.findViewById(R.id.ll_time_of_death);

        im_body_release_yes = view.findViewById(R.id.im_body_release_yes);
        im_body_release_no = view.findViewById(R.id.im_body_release_no);
        im_personal_effect_yes = view.findViewById(R.id.im_personal_effect_yes);
        im_personal_effect_no = view.findViewById(R.id.im_personal_effect_no);
        im_extra_item_yes = view.findViewById(R.id.im_extra_item_yes);
        im_extra_item_no = view.findViewById(R.id.im_extra_item_no);
        click();
    }
    public void click(){
        tv_back.setOnClickListener(this);
        bt_submit.setOnClickListener(this);
        bt_cancel.setOnClickListener(this);
        bt_next.setOnClickListener(this);
        ll_date.setOnClickListener(this);
        ll_received_time.setOnClickListener(this);
        ll_completed_time.setOnClickListener(this);
        ll_pickup.setOnClickListener(this);
        ll_drop.setOnClickListener(this);
        ll_date_of_birth.setOnClickListener(this);
        ll_date_of_death.setOnClickListener(this);
        ll_time_of_death.setOnClickListener(this);
        im_body_release_yes.setOnClickListener(this);
        im_body_release_no.setOnClickListener(this);
        im_personal_effect_yes.setOnClickListener(this);
        im_personal_effect_no.setOnClickListener(this);
        im_extra_item_yes.setOnClickListener(this);
        im_extra_item_no.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_back:
                Fragment fragment;
                fragment = new HomeFragment();
                String fragmtStatusTag = fragment.getClass().getName();
                GlobalValues.getInstance().setFramgentTag(fragmtStatusTag);
                ((MainActivity) activity).selectedNavItem(R.id.navigation_home);
                CommonUtils.loadFragment(context, fragment, fragmtStatusTag);
                break;
            case R.id.bt_submit:
                validationSecond();
                break;
            case R.id.bt_cancel:
                break;
            case R.id.bt_next:
                validationFirst();
                break;
            case R.id.ll_date:
                //getDatepicker(context, tv_start_date);
                CommonUtils.getDatepicker(context, tv_start_date);

                break;
            case R.id.ll_received_time:
                getTime(context, tv_received_time);
                break;
            case R.id.ll_completed_time:
                getTime(context, tv_completed_time);
                break;
            case R.id.ll_pickup:
                latLongStatus="from";
                callPlaceSearchIntent();
                break;
            case R.id.ll_drop:
                latLongStatus="to";
                callPlaceSearchIntent();
                break;
            case R.id.ll_date_of_birth:
                getDatepicker(context, tv_date_of_birth);
                break;
            case R.id.ll_date_of_death:
                getDatepicker(context, tv_date_of_death);
                break;
            case R.id.ll_time_of_death:
                getTime(context, tv_time_of_death);
                break;
            case R.id.im_body_release_yes:
                release_body_status="1";
                im_body_release_yes.setBackgroundResource(R.drawable.radio_selected);
                im_body_release_no.setBackgroundResource(R.drawable.radio_unselect);
                break;
            case R.id.im_body_release_no:
                release_body_status="0";
                im_body_release_yes.setBackgroundResource(R.drawable.radio_unselect);
                im_body_release_no.setBackgroundResource(R.drawable.radio_selected);

                break;
            case R.id.im_personal_effect_yes:
                personal_effect_status="1";
                et_personel_effect_list.setVisibility(View.VISIBLE);
                im_personal_effect_yes.setBackgroundResource(R.drawable.radio_selected);
                im_personal_effect_no.setBackgroundResource(R.drawable.radio_unselect);
                break;
            case R.id.im_personal_effect_no:
                personal_effect_status="0";
                et_personel_effect_list.setVisibility(View.GONE);
                im_personal_effect_yes.setBackgroundResource(R.drawable.radio_unselect);
                im_personal_effect_no.setBackgroundResource(R.drawable.radio_selected);
                break;
            case R.id.im_extra_item_yes:
                extra_item_status="1";
                et_extra_list.setVisibility(View.VISIBLE);
                im_extra_item_yes.setBackgroundResource(R.drawable.radio_selected);
                im_extra_item_no.setBackgroundResource(R.drawable.radio_unselect);
                break;
            case R.id.im_extra_item_no:
                extra_item_status="0";
                et_extra_list.setVisibility(View.GONE);
                im_extra_item_yes.setBackgroundResource(R.drawable.radio_unselect);
                im_extra_item_no.setBackgroundResource(R.drawable.radio_selected);
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

    private String getAge(int year, int month, int day) {
        //calculating age from dob
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();
        dob.set(year, month, day);
        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }
        tv_age.setText(String.valueOf(age));
        return String.valueOf(age);
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
             if(latLongStatus.equalsIgnoreCase("from")) {
                latlang_from = String.valueOf(latitude) + "," + String.valueOf(longitude);
                tv_pickup.setText(address.toString());
                // Toast.makeText(context, "" + latLongDestination, Toast.LENGTH_SHORT).show();
            }else if(latLongStatus.equalsIgnoreCase("to")) {
                latlang_to = String.valueOf(latitude) + "," + String.valueOf(longitude);
                tv_drop.setText(address.toString());
                // Toast.makeText(context, "" + latLongDestination, Toast.LENGTH_SHORT).show();
            }


            try {
                Geocoder mGeocoder = new Geocoder(context, Locale.getDefault());
                // getCityNameByCoordinates(latitude, longitude, mGeocoder);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public void validationFirst(){
        if (TextUtils.isEmpty(ed_name_of_us_veteran.getText().toString())){
            Snackbar.make(view.findViewById(R.id.l_layout),"Please Enter Name df US Veteran",Snackbar.LENGTH_LONG).setAction("Action",null).show();
        }else if (TextUtils.isEmpty(ed_branch_of_military.getText().toString())){
            Snackbar.make(view.findViewById(R.id.l_layout),"Please Enter Branch of Military",Snackbar.LENGTH_LONG).setAction("Action",null).show();
        }else if (spinner_trip_type.getSelectedItem()==null){
            Snackbar.make(view.findViewById(R.id.l_layout),"Please Enter Type",Snackbar.LENGTH_LONG).setAction("Action",null).show();
        }else if (TextUtils.isEmpty(ed_requested_by.getText().toString())) {
            Snackbar.make(view.findViewById(R.id.l_layout), "Please Enter Requested By", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        }else if (TextUtils.isEmpty(tv_start_date.getText().toString())){
            Snackbar.make(view.findViewById(R.id.l_layout),"Please Enter Date",Snackbar.LENGTH_LONG).setAction("Action",null).show();
        }else if (TextUtils.isEmpty(tv_received_time.getText().toString())){
            Snackbar.make(view.findViewById(R.id.l_layout),"Please Enter Received Time",Snackbar.LENGTH_LONG).setAction("Action",null).show();
        }/*else if (TextUtils.isEmpty(tv_completed_time.getText().toString())){
            Snackbar.make(view.findViewById(R.id.l_layout),"Please Enter Completed Time",Snackbar.LENGTH_LONG).setAction("Action",null).show();
        }*/ else if (TextUtils.isEmpty(tv_pickup.getText().toString())){
            Snackbar.make(view.findViewById(R.id.l_layout),"Please Enter Pickup Location",Snackbar.LENGTH_LONG).setAction("Action",null).show();
        }else if (TextUtils.isEmpty(tv_drop.getText().toString())){
            Snackbar.make(view.findViewById(R.id.l_layout),"Please Enter Drop Location",Snackbar.LENGTH_LONG).setAction("Action",null).show();
        }else if (TextUtils.isEmpty(tv_date_of_birth.getText().toString())){
            Snackbar.make(view.findViewById(R.id.l_layout),"Please Enter Date of Birth",Snackbar.LENGTH_LONG).setAction("Action",null).show();
        }else {
            ll_first.setVisibility(View.GONE);
            ll_second.setVisibility(View.VISIBLE);
        }
    }

    public void validationSecond(){
        if (TextUtils.isEmpty(tv_date_of_death.getText().toString())){
            Snackbar.make(view.findViewById(R.id.l_layout),"Please Enter Date of Death",Snackbar.LENGTH_LONG).setAction("Action",null).show();
        }else if (TextUtils.isEmpty(tv_time_of_death.getText().toString())){
            Snackbar.make(view.findViewById(R.id.l_layout),"Please Enter Time Of Death",Snackbar.LENGTH_LONG).setAction("Action",null).show();
        } else if (TextUtils.isEmpty(ed_next_of_kin_relationship.getText().toString())){
            Snackbar.make(view.findViewById(R.id.l_layout),"Please Enter Next of Kin Relationship",Snackbar.LENGTH_LONG).setAction("Action",null).show();
        } else if (TextUtils.isEmpty(ed_next_of_kin.getText().toString())){
            Snackbar.make(view.findViewById(R.id.l_layout),"Please Enter Next of Kin &amp; Phone no",Snackbar.LENGTH_LONG).setAction("Action",null).show();
        }else {
            serviceApi();
        }
    }

    void getCountryListApi() {
        dataItems.clear();
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading.....");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(true);
        progressDialog.show();


        ApiInterface apiInterface = ApiClients.getClient(getActivity()).create(ApiInterface.class);
        Call<CountriesModel> call = null;

        call = apiInterface.countryList(loginResponse.getData().getToken(),userId);

        call.enqueue(new Callback<CountriesModel>() {
            @Override
            public void onResponse(Call<CountriesModel> call, Response<CountriesModel> response) {
                try {
                    progressDialog.dismiss();
                    if (response.code() >= 200 && response.code() <= 210) {

                        if (response.body().getData().size() > 0) {

                            dataItems.addAll(response.body().getData());
                            for (CountriesModel.Datum objj : dataItems) {
                                countries_ArrayList.add(objj.getCountryName());}
                            spinnerAdapter adapterBrand = new spinnerAdapter(getActivity(), android.R.layout.simple_list_item_1);


                            adapterBrand.addAll(countries_ArrayList);
                            adapterBrand.add("Select Country");
                            spinner_country.setAdapter(adapterBrand);
                            spinner_country.setSelection(adapterBrand.getCount());

                        } else {


                        }
                        if (response.body().getSuccess().equals("true")) {
                            Log.e(ContentValues.TAG, "onResponse: list size>>>>>" + response.body().getData().size());

                            /*if (dataItems.size() > 0) {
                                event_recycler_view.setVisibility(View.VISIBLE);
                               // binding.emptyLayout.setVisibility(View.GONE);
                            } else {
                                event_recycler_view.setVisibility(View.GONE);
                                //binding.emptyLayout.setVisibility(View.VISIBLE);
                            }*/
                        } else {
                            // event_recycler_view.setVisibility(View.GONE);
                            //binding.emptyLayout.setVisibility(View.VISIBLE);
                            //Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }else if(response.code() >= 400){
                        dataItems.addAll(response.body().getData());
                        for (CountriesModel.Datum objj : dataItems) {
                            countries_ArrayList.add(objj.getCountryName());}
                        spinnerAdapter adapterBrand = new spinnerAdapter(getActivity(), android.R.layout.simple_list_item_1);


                        adapterBrand.addAll(countries_ArrayList);
                        adapterBrand.add("Select Country");
                        spinner_country.setAdapter(adapterBrand);
                        spinner_country.setSelection(adapterBrand.getCount());
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


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<CountriesModel> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();


            }
        });

    }

    public void serviceApi() {

        indicator.setVisibility(View.VISIBLE);
        frame.setVisibility(View.VISIBLE);
        long date = System.currentTimeMillis();
        SimpleDateFormat date1 = new SimpleDateFormat("dd/MM/yyyy HH:mm"); // for current date
        SimpleDateFormat time1 = new SimpleDateFormat("kk:mm:ss");
        request_generate_date_time = date1.format(date);
        final LoadingProgressDialog dialog = new LoadingProgressDialog(context, "Please wait...");
        dialog.setCancelable(false);
        dialog.show();
        ApiInterface apiInterface = ApiClients.getClient(context).create(ApiInterface.class);
        final UsVeteranRequestModel req = new UsVeteranRequestModel();
          req.setRequestCreatedBy(userId);
          req.setServiceId("7");
          req.setFirstName(ed_name_of_us_veteran.getText().toString());
          req.setBranchOfmilitary(ed_branch_of_military.getText().toString());
          req.setResponderType(spinner_trip_type.getSelectedItem().toString());
          req.setDob(tv_date_of_birth.getText().toString());
          req.setAge(tv_age.getText().toString());
          req.setCountry(spinner_country.getSelectedItem().toString());
          req.setTimeOfDeath(tv_time_of_death.getText().toString());
          req.setDateOfDeath(tv_date_of_death.getText().toString());
          req.setBodyRelease(release_body_status);
          req.setPersonalEffects(personal_effect_status);
          req.setPersonalEffectsLists(et_personel_effect_list.getText().toString());
          req.setRequestedItems(extra_item_status);
          req.setRequestedItemsLists(et_extra_list.getText().toString());
          req.setDate(tv_start_date.getText().toString());
          req.setTimeReceived(tv_received_time.getText().toString());
          req.setTimeCompleted(tv_completed_time.getText().toString());
          req.setRequestedBy(ed_requested_by.getText().toString());
          req.setNextOfKinRelationship(ed_next_of_kin_relationship.getText().toString());
          req.setNextOfKinPhone(ed_next_of_kin.getText().toString());
          req.setRequestDate(request_generate_date_time);
          req.setRemovedFromAddress(tv_pickup.getText().toString());
          req.setFromAddressLatlong(latlang_from);
          req.setTransferredToAddress(tv_drop.getText().toString());
          req.setToAddressLatlong(latlang_to);

        Call<UsVeteranResponseModel> call = apiInterface.UsVeteranServiceApi(loginResponse.getData().getToken(), req);

        call.enqueue(new Callback<UsVeteranResponseModel>() {
            @Override
            public void onResponse(Call<UsVeteranResponseModel> call, Response<UsVeteranResponseModel> response) {
                dialog.dismiss();
                indicator.setVisibility(View.GONE);
                frame.setVisibility(View.GONE);
                try {
                    if (response.code() >= 200 && response.code() <= 210) {
                        if (response.body().getSuccess().equalsIgnoreCase("true")) {
                            Toast.makeText(context, response.body().getData().getRequestId().toString(), Toast.LENGTH_SHORT).show();
                            String requesrId = response.body().getData().getRequestId().toString();
                            Fragment fragment = UsVeteranDeatilFragment.newInstance(requesrId,"Ashes");
                            String fragmtStatusTag2 = fragment.getClass().getName();
                            GlobalValues.getInstance().setFramgentTag(fragmtStatusTag2);
                            ((MainActivity) activity).selectedNavItem(R.id.navigation_home);
                            CommonUtils.loadFragment(context, fragment, fragmtStatusTag2);
                         /*   String req_sent_driver=response.body().getData().getRequestSentTodriver();

                            if(req_sent_driver.equals("1")){
                                String requesrId = response.body().getData().getRequestId().toString();
                                Fragment fragment = FlowerDetailPageFragment.newInstance(requesrId,"Flower");
                                String fragmtStatusTag2 = fragment.getClass().getName();
                                GlobalValues.getInstance().setFramgentTag(fragmtStatusTag2);
                                ((MainActivity) activity).selectedNavItem(R.id.navigation_home);
                                CommonUtils.loadFragment(context, fragment, fragmtStatusTag2);
                            }else if(req_sent_driver.equals("0"))  {
                                request_id = response.body().getData().getRequestId().toString();
                                tv_retry.setVisibility(View.VISIBLE);
                                tv_submit.setVisibility(View.GONE);
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
            public void onFailure(Call<UsVeteranResponseModel> call, Throwable t) {
                Toast.makeText(context, "Something Went Wrong!", Toast.LENGTH_LONG).show();
                dialog.dismiss();
                indicator.setVisibility(View.GONE);
                frame.setVisibility(View.GONE);
            }
        });

    }


}