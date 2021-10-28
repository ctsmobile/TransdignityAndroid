package com.cts.removalspecialist.fragments.viewDetails;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cts.removalspecialist.MainActivity;
import com.cts.removalspecialist.R;
import com.cts.removalspecialist.fragments.DecendentDetailFragment;
import com.cts.removalspecialist.models.auth.LoginResponse;
import com.cts.removalspecialist.models.viewDetails.DescendantDetailPageModel;
import com.cts.removalspecialist.network.ApiClients;
import com.cts.removalspecialist.network.ApiInterface;
import com.cts.removalspecialist.utilities.CommonUtils;
import com.cts.removalspecialist.utilities.GlobalValues;
import com.cts.removalspecialist.utilities.LoadingProgressDialog;
import com.cts.removalspecialist.utilities.PreferenceManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class DescendantViewDetailPageFragment extends Fragment {
    View view;
    String requestid,service_id;
    Context context;
    Activity activity;
    LoginResponse loginResponse;
    TextView tv_pickup_location,tv_drop_location,tv_bill_to,tv_requested_by,tv_name_deceased,tv_removed_address,
            tv_transfer_address,tv_time_of_death,tv_type_of_hospital,tv_physician_phone_no,tv_date,tv_descendent_name,tv_back;



    public DescendantViewDetailPageFragment() {
        // Required empty public constructor
    }


    public static DescendantViewDetailPageFragment newInstance(String id, String service_id) {
        DescendantViewDetailPageFragment fragment = new DescendantViewDetailPageFragment();
        fragment.requestid = id;
        fragment.service_id = service_id;

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
        view= inflater.inflate(R.layout.fragment_descendant_view_detail_page, container, false);
        loginResponse = (LoginResponse) GlobalValues.getPreferenceManager().getObject(PreferenceManager.LoginResponseKey,LoginResponse.class);
        init();
        getProfileDetails();
        return view;
    }
    public void init(){
        tv_pickup_location=view.findViewById(R.id.tv_pickup_location);
        tv_drop_location=view.findViewById(R.id.tv_drop_location);
        tv_bill_to=view.findViewById(R.id.tv_bill_to);
        tv_requested_by=view.findViewById(R.id.tv_requested_by);
        tv_name_deceased=view.findViewById(R.id.tv_name_deceased);
        tv_removed_address=view.findViewById(R.id.tv_removed_address);
        tv_transfer_address=view.findViewById(R.id.tv_transfer_address);
        tv_time_of_death=view.findViewById(R.id.tv_time_of_death);
        tv_type_of_hospital=view.findViewById(R.id.tv_type_of_hospital);
        tv_physician_phone_no=view.findViewById(R.id.tv_physician_phone_no);
        tv_date=view.findViewById(R.id.tv_date);
        tv_descendent_name=view.findViewById(R.id.tv_descendent_name);
        tv_back=view.findViewById(R.id.tv_back);
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment5;
                fragment5 =  DecendentDetailFragment.newInstance(requestid,service_id);
                String fragmtStatusTag = fragment5.getClass().getName();
                GlobalValues.getInstance().setFramgentTag(fragmtStatusTag);
                ((MainActivity) activity).selectedNavItem(R.id.navigation_home);
                CommonUtils.loadFragment(context, fragment5, fragmtStatusTag);
            }
        });
    }
    void getProfileDetails() {
        final LoadingProgressDialog dialog = new LoadingProgressDialog(context, "Please wait...");
        dialog.setCancelable(false);
        dialog.show();

        ApiInterface apiInterface = ApiClients.getClient(context).create(ApiInterface.class);
        Call<DescendantDetailPageModel> call = apiInterface.getRequestDesDetailsApi(loginResponse.getData().getToken(), requestid);
        call.enqueue(new Callback<DescendantDetailPageModel>() {
            @Override
            public void onResponse(Call<DescendantDetailPageModel> call, Response<DescendantDetailPageModel> response) {
                try {
                    dialog.dismiss();
                    if (response.code() >= 200 && response.code() <= 210) {
                        if (response.body().getSuccess().equalsIgnoreCase("true")) {
                            Log.e(TAG, "onResponse: " + response.body().getMessage());
                            // data = response.body().getData();
                            //Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            String name = "";
                            if (response.body().getData().getDecendantFirstName() != null) {
                                name = name + response.body().getData().getDecendantFirstName();
                            }
                            if (response.body().getData().getDecendantMiddleName() != null) {
                                name = name + " " + response.body().getData().getDecendantMiddleName();
                            }
                            if (response.body().getData().getDecendantLastName() != null) {
                                name = name + " " + response.body().getData().getDecendantLastName();
                            }

                            tv_descendent_name.setText(name);
                            tv_removed_address.setText(response.body().getData().getRemovedFromAddress());
                            tv_transfer_address.setText(response.body().getData().getTransferredToAddress());
                            //binding.tvStatusRunning.setText(response.body().getData().getDspStatus());
                            tv_date.setText(response.body().getData().getFomatRequestDate()+" "+response.body().getData().getStartTime());
                            tv_bill_to.setText(response.body().getData().getBillTo());
                            tv_requested_by.setText(response.body().getData().getRequestCreatedBy());
                            tv_pickup_location.setText(response.body().getData().getRemovalSpecialistPickupLocation());
                            tv_drop_location.setText(response.body().getData().getTransferredToAddress());
                            tv_time_of_death.setText(response.body().getData().getTimeOfDeath());
                            tv_type_of_hospital.setText(response.body().getData().getHospitalType());
                            tv_physician_phone_no.setText(response.body().getData().getPhysicianPhone());
                            // binding.tvEstimateAmount.setText("$"+" "+response.body().getData().getTotalCharge());
                       /*     descMobNo = response.body().getData().getNextOfKinPhone();
                            rsMobno = response.body().getData().getRsPhone();
                            cdMobNo = response.body().getData().getCdPhone();*/


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
                                //CommonUtils.logoutSession(activity);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<DescendantDetailPageModel> call, Throwable t) {
                dialog.dismiss();
                Log.e(TAG, "onResponse: " + t.getMessage());
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}