package com.transdignity.driver.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonObject;
import com.transdignity.driver.MainActivity;
import com.transdignity.driver.R;
import com.transdignity.driver.activities.SignupActivity;
import com.transdignity.driver.adapters.spinnerAdapter;
import com.transdignity.driver.models.auth.LoginResponse;
import com.transdignity.driver.models.vehicle.VehicleBrandResponseModel;
import com.transdignity.driver.models.vehicle.VehicleColorResponseModel;
import com.transdignity.driver.models.vehicle.VehicleModelResponseModel;
import com.transdignity.driver.models.vehicle.VehicleTypeResponseModel;
import com.transdignity.driver.network.ApiClients;
import com.transdignity.driver.network.ApiInterface;
import com.transdignity.driver.utilities.CommonUtils;
import com.transdignity.driver.utilities.GlobalValues;
import com.transdignity.driver.utilities.PreferenceManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;


public class VehicleDetailsFragment extends Fragment implements View.OnClickListener{
    View view;
    Context context;
    Activity activity;
    String userId;
    String userGroupId;
    LoginResponse loginResponse;
    Spinner spinner_vehicle_type,spinner_vehicle_brand,spinner_vehicle_model,spinner_vehicle_color;
    EditText ed_registration_number,ed_vin_number,ed_vehicle_licence_number,ed_insurance,ed_policy_number;
    EditText ed_owner,ed_caption,ed_boat_registration_number,ed_boat_insurance,ed_boat_name,ed_vessel_number,ed_location;
    ImageView im_yes,im_no;
    LinearLayout card_doc3;
    TextView tv_date_of_expi,btn_submit,btn_submit1;
    LinearLayout ll_expr_date;
    TextView tvBack, tvToolbar;
    LinearLayout ll_vehicle,ll_boat;
    List<VehicleTypeResponseModel.Datum> dataItems_type = new ArrayList<>();
    List<VehicleBrandResponseModel.Datum> dataItems_brand = new ArrayList<>();
    List<VehicleModelResponseModel.Datum> dataItems_model = new ArrayList<>();
    List<VehicleColorResponseModel.Datum> dataItems_color = new ArrayList<>();
    List<String> vehicle_type_ArrayList = new ArrayList<>();
    List<String> vehicle_brand_ArrayList = new ArrayList<>();
    List<String> vehicle_model_ArrayList = new ArrayList<>();
    List<String> vehicle_color_ArrayList = new ArrayList<>();
    List<String> objEmpty;
    String vehicle_type,vehicle_brand,vehicle_model,vehicle_color;
    String vehicle_type_id,vehicle_brand_id,vehicle_model_id,vehicle_color_id;

    String type;
    private Bitmap bitmap;
    ImageView im_doc1,im_doc2;
    String str_doc1,str_doc2;
    MultipartBody.Part mult_doc1,mult_doc2,mult_doc3;
    String str_cab_id="";
    LinearLayout ll_poluttion,ll_registration,ll_basrform;
    private static int RESULT_LOAD_IMAGE = 1;

    public VehicleDetailsFragment() {
        // Required empty public constructor
    }


    public static VehicleDetailsFragment newInstance(String cab_id) {
        VehicleDetailsFragment fragment = new VehicleDetailsFragment();
        fragment.str_cab_id = cab_id;

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
        view= inflater.inflate(R.layout.fragment_vehicle_details, container, false);
        loginResponse = (LoginResponse) GlobalValues.getPreferenceManager().getObject(PreferenceManager.LoginResponseKey, LoginResponse.class);

        if (loginResponse != null) {
            userId = loginResponse.getData().getId();
            userGroupId = loginResponse.getData().getUserGroupId();
        }
        init();
        tvToolbar = activity.findViewById(R.id.tv_toolbar);
        tvToolbar.setText("Vehicle Details");
        objEmpty = new ArrayList<String>();
        objEmpty.add("Select item");

        geVehicleTypeListApi();
        geVehicleBrandListApi();
        geVehicleModelListApi();
        geVehicleColorListApi();
        spinner_vehicle_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {


                if (spinner_vehicle_type.getSelectedItem() == "Select Vehicle Type" || spinner_vehicle_type.getSelectedItem() == "Select item") {

                } else {
                    vehicle_type = spinner_vehicle_type.getItemAtPosition(spinner_vehicle_type.getSelectedItemPosition()).toString();
                    vehicle_type_id = dataItems_type.get(position).getVehicleTypeId();
                    // Toast.makeText(getActivity(), ""+storeId, Toast.LENGTH_SHORT).show();
                    // CarBrand(storeId);
                    if(vehicle_type.equalsIgnoreCase("Licensed Boat or Vessel Captain")){
                        ll_vehicle.setVisibility(View.GONE);
                        ll_boat.setVisibility(View.VISIBLE);
                        tvToolbar.setText("Burial See Details");

                    }else {
                        ll_vehicle.setVisibility(View.VISIBLE);
                        ll_boat.setVisibility(View.GONE);
                        tvToolbar.setText("Vehicle Details");

                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_vehicle_brand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {


                if (spinner_vehicle_brand.getSelectedItem() == "Select Vehicle Brand" || spinner_vehicle_brand.getSelectedItem() == "Select item") {

                } else {
                    vehicle_brand = spinner_vehicle_brand.getItemAtPosition(spinner_vehicle_brand.getSelectedItemPosition()).toString();
                    vehicle_brand_id = dataItems_brand.get(position).getVehicleBrandId();
                    // Toast.makeText(getActivity(), ""+storeId, Toast.LENGTH_SHORT).show();
                    // CarBrand(storeId);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_vehicle_model.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {


                if (spinner_vehicle_model.getSelectedItem() == "Select Vehicle Model" || spinner_vehicle_model.getSelectedItem() == "Select item") {

                } else {
                    vehicle_model = spinner_vehicle_model.getItemAtPosition(spinner_vehicle_model.getSelectedItemPosition()).toString();
                    vehicle_model_id = dataItems_model.get(position).getVehicleModelId();
                    // Toast.makeText(getActivity(), ""+storeId, Toast.LENGTH_SHORT).show();
                    // CarBrand(storeId);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_vehicle_color.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {


                if (spinner_vehicle_color.getSelectedItem() == "Select Vehicle Color" || spinner_vehicle_color.getSelectedItem() == "Select item") {

                } else {
                    vehicle_color = spinner_vehicle_color.getItemAtPosition(spinner_vehicle_color.getSelectedItemPosition()).toString();
                    vehicle_color_id = dataItems_color.get(position).getVehicleColorId();
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
        spinner_vehicle_type=view.findViewById(R.id.spinner_vehicle_type);
        spinner_vehicle_brand=view.findViewById(R.id.spinner_vehicle_brand);
        spinner_vehicle_model=view.findViewById(R.id.spinner_vehicle_model);
        spinner_vehicle_color=view.findViewById(R.id.spinner_vehicle_color);
        ed_registration_number=view.findViewById(R.id.ed_registration_number);
        ed_vin_number=view.findViewById(R.id.ed_vin_number);
        ed_vehicle_licence_number=view.findViewById(R.id.ed_vehicle_licence_number);
        ed_insurance=view.findViewById(R.id.ed_insurance);
        ed_policy_number=view.findViewById(R.id.ed_policy_number);
        tv_date_of_expi=view.findViewById(R.id.tv_date_of_expi);
        ll_expr_date=view.findViewById(R.id.ll_expr_date);
        im_yes=view.findViewById(R.id.im_yes);
        im_no=view.findViewById(R.id.im_no);
        btn_submit=view.findViewById(R.id.btn_submit);
        im_doc1=view.findViewById(R.id.im_doc1);
        im_doc2=view.findViewById(R.id.im_doc2);
        ll_vehicle=view.findViewById(R.id.ll_vehicle);
        ll_boat=view.findViewById(R.id.ll_boat);
        ed_owner=view.findViewById(R.id.ed_owner);
        ed_caption=view.findViewById(R.id.ed_caption);
        ed_boat_registration_number=view.findViewById(R.id.ed_boat_registration_number);
        ed_boat_insurance=view.findViewById(R.id.ed_boat_insurance);
        ed_boat_name=view.findViewById(R.id.ed_boat_name);
        ed_vessel_number=view.findViewById(R.id.ed_vessel_number);
        ed_location=view.findViewById(R.id.ed_location);
        card_doc3=view.findViewById(R.id.card_doc3);
        btn_submit1=view.findViewById(R.id.btn_submit1);
        ll_poluttion=view.findViewById(R.id.ll_poluttion);
        ll_registration=view.findViewById(R.id.ll_registration);
        ll_basrform=view.findViewById(R.id.ll_basrform);
        click();
    }
    public void click(){
        btn_submit.setOnClickListener(this);
        btn_submit1.setOnClickListener(this);
        im_yes.setOnClickListener(this);
        im_no.setOnClickListener(this);
        ll_expr_date.setOnClickListener(this);
        im_doc1.setOnClickListener(this);
        im_doc2.setOnClickListener(this);
        ll_poluttion.setOnClickListener(this);
        ll_registration.setOnClickListener(this);
        ll_basrform.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_submit:
                validation1();
                break;
                case R.id.btn_submit1:
                validation2();
                break;
            case R.id.im_yes:
                card_doc3.setVisibility(View.VISIBLE);
                im_yes.setImageResource(R.drawable.radio_selected);
                im_no.setImageResource(R.drawable.radio_unselect);
                break;
            case R.id.im_no:
                card_doc3.setVisibility(View.GONE);
                im_yes.setImageResource(R.drawable.radio_unselect);
                im_no.setImageResource(R.drawable.radio_selected);
                break;
            case R.id.ll_expr_date:
                getDatepicker(context, tv_date_of_expi);

                break;
            case R.id.ll_poluttion:
                Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
                type="doc1";
                break;
            case R.id.ll_registration:
                Intent i1 = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i1, RESULT_LOAD_IMAGE);
                type="doc2";
                break;
            case R.id.ll_basrform:
                Intent i2 = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i2, RESULT_LOAD_IMAGE);
                type="doc2";
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getActivity().getContentResolver().query(selectedImage,filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            if(type.equals("doc1")){
                im_doc1.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                bitmap = BitmapFactory.decodeFile(picturePath);
                str_doc1 = encodeImage(bitmap);
                File imageFile = new File(picturePath);
                RequestBody requestFile =
                        RequestBody.create(MediaType.parse("multipart/form-data"), imageFile);

                mult_doc1 = MultipartBody.Part.createFormData(
                        "pollution_docfile",
                        imageFile.getName(),
                        requestFile
                );
            }else if(type.equals("doc2")){
                im_doc2.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                bitmap = BitmapFactory.decodeFile(picturePath);
                str_doc2 = encodeImage(bitmap);
                File imageFile = new File(picturePath);
                RequestBody requestFile =
                        RequestBody.create(MediaType.parse("multipart/form-data"), imageFile);

                mult_doc2 = MultipartBody.Part.createFormData(
                        "registration_docfile",
                        imageFile.getName(),
                        requestFile
                );
                mult_doc3 = MultipartBody.Part.createFormData(
                        "basrform_docfile",
                        imageFile.getName(),
                        requestFile
                );
            }


        }
    }
    private String encodeImage(Bitmap bm)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);

        return encImage;
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
                            //getAge(year, monthOfYear, dayOfMonth);
                        }else if(month.length()==1){
                            String curDate = dayOfMonth + "/" + "0"+(monthOfYear + 1) + "/" + year;
                            editText.setText(curDate);
                            //getAge(year, monthOfYear, dayOfMonth);
                        }else if((day.length()==1)&&(month.length()==1)){
                            String curDate = "0"+dayOfMonth + "/" + "0"+(monthOfYear + 1) + "/" + year;
                            editText.setText(curDate);
                            //getAge(year, monthOfYear, dayOfMonth);
                        }else {
                            String curDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                            editText.setText(curDate);
                            //getAge(year, monthOfYear, dayOfMonth);
                        }
                        if((day.length()==1)&&(month.length()==1)){
                            String curDate = "0"+dayOfMonth + "/" + "0"+(monthOfYear + 1) + "/" + year;
                            editText.setText(curDate);
                            //getAge(year, monthOfYear, dayOfMonth);
                        }
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();

    }

    void geVehicleTypeListApi() {
        dataItems_type.clear();
        dataItems_brand.clear();
        dataItems_model.clear();
        dataItems_color.clear();
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading.....");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(true);
        progressDialog.show();


        ApiInterface apiInterface = ApiClients.getClient(getActivity()).create(ApiInterface.class);
        Call<VehicleTypeResponseModel> call = null;

        call = apiInterface.vehicleTypeList(loginResponse.getData().getToken(),userId);

        call.enqueue(new Callback<VehicleTypeResponseModel>() {
            @Override
            public void onResponse(Call<VehicleTypeResponseModel> call, Response<VehicleTypeResponseModel> response) {
                try {
                    progressDialog.dismiss();
                    if (response.code() >= 200 && response.code() <= 210) {

                        if (response.body().getData().size() > 0) {

                            dataItems_type.addAll(response.body().getData());
                            for (VehicleTypeResponseModel.Datum objj : dataItems_type) {
                                vehicle_type_ArrayList.add(objj.getVehicleType());}
                            spinnerAdapter adapterBrand = new spinnerAdapter(getActivity(), android.R.layout.simple_list_item_1);


                            adapterBrand.addAll(vehicle_type_ArrayList);
                            adapterBrand.add("Select Vehicle Type");
                            spinner_vehicle_type.setAdapter(adapterBrand);
                            spinner_vehicle_type.setSelection(adapterBrand.getCount());

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
                        dataItems_type.addAll(response.body().getData());
                        for (VehicleTypeResponseModel.Datum objj : dataItems_type) {
                            vehicle_type_ArrayList.add(objj.getVehicleType());}
                        spinnerAdapter adapterBrand = new spinnerAdapter(getActivity(), android.R.layout.simple_list_item_1);


                        adapterBrand.addAll(vehicle_type_ArrayList);
                        adapterBrand.add("Select Vehicle Type");
                        spinner_vehicle_type.setAdapter(adapterBrand);
                        spinner_vehicle_type.setSelection(adapterBrand.getCount());

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
            public void onFailure(Call<VehicleTypeResponseModel> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();


            }
        });

    }

    void geVehicleBrandListApi() {
        dataItems_type.clear();
        dataItems_brand.clear();
        dataItems_model.clear();
        dataItems_color.clear();
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading.....");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(true);
        progressDialog.show();


        ApiInterface apiInterface = ApiClients.getClient(getActivity()).create(ApiInterface.class);
        Call<VehicleBrandResponseModel> call = null;

        call = apiInterface.vehicleBrandList(loginResponse.getData().getToken(),userId);

        call.enqueue(new Callback<VehicleBrandResponseModel>() {
            @Override
            public void onResponse(Call<VehicleBrandResponseModel> call, Response<VehicleBrandResponseModel> response) {
                try {
                    progressDialog.dismiss();
                    if (response.code() >= 200 && response.code() <= 210) {

                        if (response.body().getData().size() > 0) {

                            dataItems_brand.addAll(response.body().getData());
                            for (VehicleBrandResponseModel.Datum objj : dataItems_brand) {
                                vehicle_brand_ArrayList.add(objj.getVehicleBrand());}
                            spinnerAdapter adapterBrand = new spinnerAdapter(getActivity(), android.R.layout.simple_list_item_1);


                            adapterBrand.addAll(vehicle_brand_ArrayList);
                            adapterBrand.add("Select Vehicle Brand");
                            spinner_vehicle_brand.setAdapter(adapterBrand);
                            spinner_vehicle_brand.setSelection(adapterBrand.getCount());

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
                        dataItems_brand.addAll(response.body().getData());
                        for (VehicleBrandResponseModel.Datum objj : dataItems_brand) {
                            vehicle_brand_ArrayList.add(objj.getVehicleBrand());}
                        spinnerAdapter adapterBrand = new spinnerAdapter(getActivity(), android.R.layout.simple_list_item_1);


                        adapterBrand.addAll(vehicle_brand_ArrayList);
                        adapterBrand.add("Select Vehicle Type");
                        spinner_vehicle_brand.setAdapter(adapterBrand);
                        spinner_vehicle_brand.setSelection(adapterBrand.getCount());

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
            public void onFailure(Call<VehicleBrandResponseModel> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();


            }
        });

    }

    void geVehicleModelListApi() {
        dataItems_type.clear();
        dataItems_brand.clear();
        dataItems_model.clear();
        dataItems_color.clear();        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading.....");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(true);
        progressDialog.show();


        ApiInterface apiInterface = ApiClients.getClient(getActivity()).create(ApiInterface.class);
        Call<VehicleModelResponseModel> call = null;

        call = apiInterface.vehicleModelList(loginResponse.getData().getToken(),userId);

        call.enqueue(new Callback<VehicleModelResponseModel>() {
            @Override
            public void onResponse(Call<VehicleModelResponseModel> call, Response<VehicleModelResponseModel> response) {
                try {
                    progressDialog.dismiss();
                    if (response.code() >= 200 && response.code() <= 210) {

                        if (response.body().getData().size() > 0) {

                            dataItems_model.addAll(response.body().getData());
                            for (VehicleModelResponseModel.Datum objj : dataItems_model) {
                                vehicle_model_ArrayList.add(objj.getVehicleModel());}
                            spinnerAdapter adapterBrand = new spinnerAdapter(getActivity(), android.R.layout.simple_list_item_1);


                            adapterBrand.addAll(vehicle_model_ArrayList);
                            adapterBrand.add("Select Vehicle Model");
                            spinner_vehicle_model.setAdapter(adapterBrand);
                            spinner_vehicle_model.setSelection(adapterBrand.getCount());

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
                        dataItems_model.addAll(response.body().getData());
                        for (VehicleModelResponseModel.Datum objj : dataItems_model) {
                            vehicle_model_ArrayList.add(objj.getVehicleModel());}
                        spinnerAdapter adapterBrand = new spinnerAdapter(getActivity(), android.R.layout.simple_list_item_1);


                        adapterBrand.addAll(vehicle_model_ArrayList);
                        adapterBrand.add("Select Vehicle Model");
                        spinner_vehicle_model.setAdapter(adapterBrand);
                        spinner_vehicle_model.setSelection(adapterBrand.getCount());

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
            public void onFailure(Call<VehicleModelResponseModel> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();


            }
        });

    }

    void geVehicleColorListApi() {
        dataItems_type.clear();
        dataItems_brand.clear();
        dataItems_model.clear();
        dataItems_color.clear();
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading.....");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(true);
        progressDialog.show();


        ApiInterface apiInterface = ApiClients.getClient(getActivity()).create(ApiInterface.class);
        Call<VehicleColorResponseModel> call = null;

        call = apiInterface.vehicleColorList(loginResponse.getData().getToken(),userId);

        call.enqueue(new Callback<VehicleColorResponseModel>() {
            @Override
            public void onResponse(Call<VehicleColorResponseModel> call, Response<VehicleColorResponseModel> response) {
                try {
                    progressDialog.dismiss();
                    if (response.code() >= 200 && response.code() <= 210) {

                        if (response.body().getData().size() > 0) {

                            dataItems_color.addAll(response.body().getData());
                            for (VehicleColorResponseModel.Datum objj : dataItems_color) {
                                vehicle_color_ArrayList.add(objj.getVehicleColor());}
                            spinnerAdapter adapterBrand = new spinnerAdapter(getActivity(), android.R.layout.simple_list_item_1);


                            adapterBrand.addAll(vehicle_color_ArrayList);
                            adapterBrand.add("Select Vehicle Color");
                            spinner_vehicle_color.setAdapter(adapterBrand);
                            spinner_vehicle_color.setSelection(adapterBrand.getCount());

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
                        dataItems_color.addAll(response.body().getData());
                        for (VehicleColorResponseModel.Datum objj : dataItems_color) {
                            vehicle_color_ArrayList.add(objj.getVehicleColor());}
                        spinnerAdapter adapterBrand = new spinnerAdapter(getActivity(), android.R.layout.simple_list_item_1);


                        adapterBrand.addAll(vehicle_color_ArrayList);
                        adapterBrand.add("Select Vehicle Color");
                        spinner_vehicle_color.setAdapter(adapterBrand);
                        spinner_vehicle_color.setSelection(adapterBrand.getCount());

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
            public void onFailure(Call<VehicleColorResponseModel> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();


            }
        });

    }


    public void validation1(){
        if (spinner_vehicle_type.getSelectedItem().equals("Select Vehicle Type")){
            Snackbar.make(view.findViewById(R.id.ll_vehicle_head),"Please Select Vehicle Type", Snackbar.LENGTH_LONG).setAction("Action",null).show();
        }else if (spinner_vehicle_brand.getSelectedItem().equals("Select Vehicle Brand")){
            Snackbar.make(view.findViewById(R.id.ll_vehicle_head),"Please Select Vehicle Brand", Snackbar.LENGTH_LONG).setAction("Action",null).show();
        }else if (spinner_vehicle_model.getSelectedItem().equals("Select Vehicle Model")){
            Snackbar.make(view.findViewById(R.id.ll_vehicle_head),"Please Select Vehicle Model", Snackbar.LENGTH_LONG).setAction("Action",null).show();
        }else if (spinner_vehicle_color.getSelectedItem().equals("Select Vehicle Color")){
            Snackbar.make(view.findViewById(R.id.ll_vehicle_head),"Please Select Vehicle Color", Snackbar.LENGTH_LONG).setAction("Action",null).show();
        }else if (TextUtils.isEmpty(ed_registration_number.getText().toString())){
            Snackbar.make(view.findViewById(R.id.ll_vehicle_head),"Please Enter Registration No", Snackbar.LENGTH_LONG).setAction("Action",null).show();
        }else if (TextUtils.isEmpty(ed_vin_number.getText().toString())){
            Snackbar.make(view.findViewById(R.id.ll_vehicle_head),"Please Enter VIN Number", Snackbar.LENGTH_LONG).setAction("Action",null).show();
        }else if (TextUtils.isEmpty(ed_vehicle_licence_number.getText().toString())){
            Snackbar.make(view.findViewById(R.id.ll_vehicle_head),"Please Enter Vehicle Licence Number", Snackbar.LENGTH_LONG).setAction("Action",null).show();
        }else if (TextUtils.isEmpty(ed_insurance.getText().toString())){
            Snackbar.make(view.findViewById(R.id.ll_vehicle_head),"Please Enter Insurance Carrier", Snackbar.LENGTH_LONG).setAction("Action",null).show();
        }else if (TextUtils.isEmpty(ed_policy_number.getText().toString())){
            Snackbar.make(view.findViewById(R.id.ll_vehicle_head),"Please Enter Policy Number", Snackbar.LENGTH_LONG).setAction("Action",null).show();
        }else if (TextUtils.isEmpty(tv_date_of_expi.getText().toString())){
            Snackbar.make(view.findViewById(R.id.ll_vehicle_head),"Please Enter Expiry Date", Snackbar.LENGTH_LONG).setAction("Action",null).show();
        }else {
            addVehicleDetailsApi();
        }
    }

    public void validation2(){
        if (TextUtils.isEmpty(ed_owner.getText().toString())){
            Snackbar.make(view.findViewById(R.id.ll_vehicle_head),"Please Enter Owner Name", Snackbar.LENGTH_LONG).setAction("Action",null).show();
        }else if (TextUtils.isEmpty(ed_caption.getText().toString())){
            Snackbar.make(view.findViewById(R.id.ll_vehicle_head),"Please Enter Captain Name", Snackbar.LENGTH_LONG).setAction("Action",null).show();
        }else if (TextUtils.isEmpty(ed_boat_name.getText().toString())){
            Snackbar.make(view.findViewById(R.id.ll_vehicle_head),"Please Enter Boat Name", Snackbar.LENGTH_LONG).setAction("Action",null).show();
        }else if (TextUtils.isEmpty(ed_location.getText().toString())){
            Snackbar.make(view.findViewById(R.id.ll_vehicle_head),"Please Enter Location", Snackbar.LENGTH_LONG).setAction("Action",null).show();
        }else {
            addVehicleDetailsApi();
        }
    }
    void addVehicleDetailsApi() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading.....");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(true);
        progressDialog.show();
        RequestBody vehicle_brand_id1 = null;
        RequestBody vehicle_model_id1 = null;
        RequestBody vehicle_color_id1 = null;
        RequestBody cab_name = null;
        RequestBody cab_no = null;
        RequestBody registration_no = null;
        RequestBody insurance_no = null;
        RequestBody user_id = RequestBody.create(MediaType.parse("text/plain"), userId);
        RequestBody user_group_id = RequestBody.create(MediaType.parse("text/plain"), userGroupId);

        RequestBody vehicle_type_id1 = RequestBody.create(MediaType.parse("text/plain"), vehicle_type_id);

        if(vehicle_type.equalsIgnoreCase("Licensed Boat or Vessel Captain")){
            cab_name = RequestBody.create(MediaType.parse("text/plain"), ed_boat_name.getText().toString());

            cab_no = RequestBody.create(MediaType.parse("text/plain"), ed_vessel_number.getText().toString());
             registration_no = RequestBody.create(MediaType.parse("text/plain"), ed_boat_registration_number.getText().toString());
             insurance_no = RequestBody.create(MediaType.parse("text/plain"), ed_boat_insurance.getText().toString());


        }else {
             vehicle_brand_id1 = RequestBody.create(MediaType.parse("text/plain"), vehicle_brand_id);
             vehicle_model_id1 = RequestBody.create(MediaType.parse("text/plain"), vehicle_model_id);
             vehicle_color_id1 = RequestBody.create(MediaType.parse("text/plain"), vehicle_color_id);
             cab_name = RequestBody.create(MediaType.parse("text/plain"), vehicle_brand);
            cab_no = RequestBody.create(MediaType.parse("text/plain"), ed_vin_number.getText().toString());
            registration_no = RequestBody.create(MediaType.parse("text/plain"), ed_registration_number.getText().toString());
            insurance_no = RequestBody.create(MediaType.parse("text/plain"), ed_insurance.getText().toString());


        }

        RequestBody pollution_doc = RequestBody.create(MediaType.parse("text/plain"), "khjh");
        RequestBody registration_doc = RequestBody.create(MediaType.parse("text/plain"), "");
       // RequestBody cab_id = RequestBody.create(MediaType.parse("text/plain"), str_cab_id);
        RequestBody cab_id = RequestBody.create(MediaType.parse("text/plain"), str_cab_id);
        RequestBody owner_name = RequestBody.create(MediaType.parse("text/plain"), ed_owner.getText().toString());
        RequestBody captain_name = RequestBody.create(MediaType.parse("text/plain"), ed_caption.getText().toString());
        RequestBody location = RequestBody.create(MediaType.parse("text/plain"), ed_location.getText().toString());
        RequestBody basrform = RequestBody.create(MediaType.parse("text/plain"), "");

        ApiInterface apiInterface = ApiClients.getClient(getActivity()).create(ApiInterface.class);
        Call<JsonObject> call = apiInterface.addVehicleDetails(loginResponse.getData().getToken(),mult_doc1,mult_doc2,mult_doc3,
                user_id,user_group_id,cab_no,registration_no,insurance_no,vehicle_brand_id1,
                vehicle_model_id1,vehicle_type_id1,vehicle_color_id1,pollution_doc
                ,registration_doc,cab_name,cab_id,owner_name,captain_name,location,basrform);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                try {
                    progressDialog.dismiss();
                    if (response.code() >= 200 && response.code() <= 210) {
                        JSONObject object = new JSONObject(response.body().toString());

                        String succ = object.optString("success").toString();
                        if(succ.equals("true")) {
                            Toast.makeText(context, object.optString("message").toString(), Toast.LENGTH_SHORT).show();
                            Fragment fragment;
                            fragment = new SettingsFragment();
                            String fragmtStatusTag = fragment.getClass().getName();
                            GlobalValues.getInstance().setFramgentTag(fragmtStatusTag);
                            ((MainActivity) activity).selectedNavItem(R.id.navigation_home);
                            CommonUtils.loadFragment(context, fragment, fragmtStatusTag);
                        } else if(succ.equals("false")){

                            //  Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
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
                             Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                            //Snackbar.make(findViewById(R.id.login),""+error,Snackbar.LENGTH_LONG).setAction("Action",null).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }




}