package com.transdignity.driver.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.transdignity.driver.R;
import com.transdignity.driver.activities.ImageShowActivity;
import com.transdignity.driver.models.auth.LoginResponse;
import com.transdignity.driver.models.vehicle.VehicleDetailsResponseModel;
import com.transdignity.driver.network.ApiClients;
import com.transdignity.driver.network.ApiInterface;
import com.transdignity.driver.utilities.CommonUtils;
import com.transdignity.driver.utilities.GlobalValues;
import com.transdignity.driver.utilities.PreferenceManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ShowVehicleDetailsFragment extends Fragment {
    View view;
    Context context;
    Activity activity;
    String userId;
    String userGroupId;
    LoginResponse loginResponse;
    TextView tvBack, tvToolbar,btn_update;
    TextView tv_vehicle_type,tv_cab_name,tv_cab_number,tv_registration_no,tv_insurance_no,
            tv_pollution_doc,tv_registration_doc,tv_owner,tv_caption,tv_location;
    String str_cab_id;
    TextView btn_reg_view1,btn_reg_view2,btn_brsa;
    String images1,image2,images3;
    LinearLayout im_1,im_2,im_3;
    public ShowVehicleDetailsFragment() {
        // Required empty public constructor
    }


    public static ShowVehicleDetailsFragment newInstance(String param1, String param2) {
        ShowVehicleDetailsFragment fragment = new ShowVehicleDetailsFragment();
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
        view= inflater.inflate(R.layout.fragment_show_vehicle_details, container, false);
        loginResponse = (LoginResponse) GlobalValues.getPreferenceManager().getObject(PreferenceManager.LoginResponseKey, LoginResponse.class);

        if (loginResponse != null) {
            userId = loginResponse.getData().getId();
            userGroupId = loginResponse.getData().getUserGroupId();
        }
        tvToolbar = activity.findViewById(R.id.tv_toolbar);
        tvToolbar.setText("Vehicle Details");
        init();
        geVehicleDetailsApi();
        btn_reg_view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), ImageShowActivity.class);
                intent.putExtra("image",images1);
                startActivity(intent);
            }
        });
        btn_reg_view2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), ImageShowActivity.class);
                intent.putExtra("image",image2);
                startActivity(intent);
            }
        });
        btn_brsa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), ImageShowActivity.class);
                intent.putExtra("image",images3);
                startActivity(intent);
            }
        });
        return view;
    }

    public void init(){
        tv_vehicle_type=view.findViewById(R.id.tv_vehicle_type);
        tv_owner=view.findViewById(R.id.tv_owner);
        tv_caption=view.findViewById(R.id.tv_caption);
        tv_location=view.findViewById(R.id.tv_location);
        tv_cab_name=view.findViewById(R.id.tv_cab_name);
        tv_cab_number=view.findViewById(R.id.tv_cab_number);
        tv_registration_no=view.findViewById(R.id.tv_registration_no);
        tv_insurance_no=view.findViewById(R.id.tv_insurance_no);
        tv_pollution_doc=view.findViewById(R.id.tv_pollution_doc);
        tv_registration_doc=view.findViewById(R.id.tv_registration_doc);
        btn_update=view.findViewById(R.id.btn_update);
        btn_reg_view1=view.findViewById(R.id.btn_reg_view1);
        btn_reg_view2=view.findViewById(R.id.btn_reg_view2);
        btn_brsa=view.findViewById(R.id.btn_brsa);
        im_1=view.findViewById(R.id.im_1);
        im_2=view.findViewById(R.id.im_2);
        im_3=view.findViewById(R.id.im_3);

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment_update=  VehicleDetailsFragment.newInstance(str_cab_id);
                String fragmtStatusTag_update = fragment_update.getClass().getName();
               // ((MainActivity) getActivity()).selectedNavItem(R.id.navigaton_account);
                GlobalValues.getInstance().setFramgentTag(fragmtStatusTag_update);
                CommonUtils.loadFragment(context, fragment_update, fragmtStatusTag_update);

            }
        });
    }
    void geVehicleDetailsApi() {

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading.....");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(true);
        progressDialog.show();


        ApiInterface apiInterface = ApiClients.getClient(getActivity()).create(ApiInterface.class);
        Call<VehicleDetailsResponseModel> call = null;

        call = apiInterface.vehicleDetailList(loginResponse.getData().getToken(),userId);

        call.enqueue(new Callback<VehicleDetailsResponseModel>() {
            @Override
            public void onResponse(Call<VehicleDetailsResponseModel> call, Response<VehicleDetailsResponseModel> response) {
                try {
                    progressDialog.dismiss();
                    if (response.code() >= 200 && response.code() <= 210) {
                        String cab_name=response.body().getData().getCabName().toString();
                        str_cab_id=response.body().getData().getCabId();
                        if(response.body().getData().getVehicleTypeId().equals("1")){
                            tv_vehicle_type.setText("Vehicle Type :"+"Removal Service");
                            tv_owner.setVisibility(View.GONE);
                            tv_caption.setVisibility(View.GONE);
                            tv_location.setVisibility(View.GONE);
                            im_1.setVisibility(View.VISIBLE);
                            im_2.setVisibility(View.VISIBLE);
                            im_3.setVisibility(View.GONE);
                        }else if(response.body().getData().getVehicleTypeId().equals("2")){
                            tv_vehicle_type.setText("Vehicle Type :"+"Independent Licenced Removal Service");
                            tv_owner.setVisibility(View.GONE);
                            tv_caption.setVisibility(View.GONE);
                            tv_location.setVisibility(View.GONE);
                            im_1.setVisibility(View.VISIBLE);
                            im_2.setVisibility(View.VISIBLE);
                            im_3.setVisibility(View.GONE);
                        }else if(response.body().getData().getVehicleTypeId().equals("3")){
                            tv_vehicle_type.setText("Vehicle Type :"+"Licenced Funeral Transport");
                            tv_owner.setVisibility(View.GONE);
                            tv_caption.setVisibility(View.GONE);
                            tv_location.setVisibility(View.GONE);
                            im_1.setVisibility(View.VISIBLE);
                            im_2.setVisibility(View.VISIBLE);
                            im_3.setVisibility(View.GONE);
                        } else if(response.body().getData().getVehicleTypeId().equals("4")){
                            tv_vehicle_type.setText("Vehicle Type :"+"Licenced Limo Provider");
                            tv_owner.setVisibility(View.GONE);
                            tv_caption.setVisibility(View.GONE);
                            tv_location.setVisibility(View.GONE);
                            im_1.setVisibility(View.VISIBLE);
                            im_2.setVisibility(View.VISIBLE);
                            im_3.setVisibility(View.GONE);
                        } else if(response.body().getData().getVehicleTypeId().equals("5")){
                            tv_vehicle_type.setText("Vehicle Type :"+"Courier Vehicle");
                            tv_owner.setVisibility(View.GONE);
                            tv_caption.setVisibility(View.GONE);
                            tv_location.setVisibility(View.GONE);
                            im_1.setVisibility(View.VISIBLE);
                            im_2.setVisibility(View.VISIBLE);
                            im_3.setVisibility(View.GONE);
                        } else if(response.body().getData().getVehicleTypeId().equals("6")){
                            tv_vehicle_type.setText("Vehicle Type :"+"Licensed Boat or Vessel Caption");
                            tv_owner.setVisibility(View.VISIBLE);
                            tv_caption.setVisibility(View.VISIBLE);
                            tv_location.setVisibility(View.VISIBLE);
                            im_1.setVisibility(View.GONE);
                            im_2.setVisibility(View.GONE);
                            im_3.setVisibility(View.VISIBLE);
                            tv_owner.setText("Owner :"+response.body().getData().getOwnerName());
                            tv_caption.setText("Caption :"+response.body().getData().getCaptainName());
                            tv_location.setText("Location :"+response.body().getData().getLocation());
                        } else if(response.body().getData().getVehicleTypeId().equals("7")){
                            tv_vehicle_type.setText("Vehicle Type :"+"Licensed A Heroes Tribute");
                            tv_owner.setVisibility(View.GONE);
                            tv_caption.setVisibility(View.GONE);
                            tv_location.setVisibility(View.GONE);
                            im_1.setVisibility(View.VISIBLE);
                            im_2.setVisibility(View.VISIBLE);
                            im_3.setVisibility(View.GONE);
                        }
                    tv_cab_name.setText("Cab Name :"+cab_name);
                    tv_cab_number.setText("Cab Number :"+response.body().getData().getCabNo().toString());
                    tv_registration_no.setText("Registration Number :"+response.body().getData().getRegistrationNo().toString());
                   // tv_insurance_no.setText("Insurance Number :"+response.body().getData().getInsuranceNo().toString());
                    //tv_pollution_doc.setText(response.body().getData().getPollutionDoc().toString());
                    //tv_registration_doc.setText(response.body().getData().getRegistrationDoc().toString());
                    images1=response.body().getData().getPollutionDoc();
                    image2=response.body().getData().getRegistrationDoc();
                    images3=response.body().getData().getRegistrationDoc();
                    }else if(response.code() >= 400){


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
            public void onFailure(Call<VehicleDetailsResponseModel> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();


            }
        });

    }

}