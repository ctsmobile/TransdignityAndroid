package com.transdignity.deathserviceprovider.fragments.DetailFormPage;

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

import com.transdignity.deathserviceprovider.MainActivity;
import com.transdignity.deathserviceprovider.R;
import com.transdignity.deathserviceprovider.fragments.ServicesDetailsFragments.LimoDetailPageFragment;
import com.transdignity.deathserviceprovider.fragments.ServicesDetailsFragments.UsVeteranDeatilFragment;
import com.transdignity.deathserviceprovider.models.auth.LoginResponse;
import com.transdignity.deathserviceprovider.models.request.ServiceDetailPage.LimoDetailPageModel;
import com.transdignity.deathserviceprovider.models.request.ServiceDetailPage.UsVeteranDetailPagesModel;
import com.transdignity.deathserviceprovider.network.ApiClients;
import com.transdignity.deathserviceprovider.network.ApiInterface;
import com.transdignity.deathserviceprovider.utilities.CommonUtils;
import com.transdignity.deathserviceprovider.utilities.GlobalValues;
import com.transdignity.deathserviceprovider.utilities.LoadingProgressDialog;
import com.transdignity.deathserviceprovider.utilities.PreferenceManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class UsVeteranDetailPageFragment extends Fragment {
    View view;
    String requestid;
    Context context;
    Activity activity;
    LoginResponse loginResponse;
    TextView tv_back,tv_date,tv_country,tv_us_veteran_name,
            tv_branch_of_military,tv_type,tv_request_by,
            tv_request_date,tv_requested_time,tv_completed_time,
            tv_pickup_location,tv_drop_location,tv_date_of_birth_age,
            tv_date_time_of_death,tv_next_of_kin_phone,tv_release_body,tv_personal_effect,
            tv_extra_item_request;

    public UsVeteranDetailPageFragment() {
        // Required empty public constructor
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

    public static UsVeteranDetailPageFragment newInstance(String id) {
        UsVeteranDetailPageFragment fragment = new UsVeteranDetailPageFragment();
        fragment.requestid = id;
        return fragment;
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
        view= inflater.inflate(R.layout.fragment_us_veteran_detail_page, container, false);
        loginResponse = (LoginResponse) GlobalValues.getPreferenceManager().getObject(PreferenceManager.LoginResponseKey,LoginResponse.class);
        init();
        return view;
    }

    public void init(){
        tv_back=view.findViewById(R.id.tv_back);
        tv_date=view.findViewById(R.id.tv_date);
        tv_country=view.findViewById(R.id.tv_country);
        tv_us_veteran_name=view.findViewById(R.id.tv_us_veteran_name);
        tv_branch_of_military=view.findViewById(R.id.tv_branch_of_military);
        tv_type=view.findViewById(R.id.tv_type);
        tv_request_by=view.findViewById(R.id.tv_request_by);
        tv_request_date=view.findViewById(R.id.tv_request_date);
        tv_requested_time=view.findViewById(R.id.tv_requested_time);
        tv_completed_time=view.findViewById(R.id.tv_completed_time);
        tv_pickup_location=view.findViewById(R.id.tv_pickup_location);
        tv_drop_location=view.findViewById(R.id.tv_drop_location);
        tv_date_of_birth_age=view.findViewById(R.id.tv_date_of_birth_age);
        tv_date_time_of_death=view.findViewById(R.id.tv_date_time_of_death);
        tv_next_of_kin_phone=view.findViewById(R.id.tv_next_of_kin_phone);
        tv_release_body=view.findViewById(R.id.tv_release_body);
        tv_personal_effect=view.findViewById(R.id.tv_personal_effect);
        tv_extra_item_request=view.findViewById(R.id.tv_extra_item_request);
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment2;
                fragment2 =  UsVeteranDeatilFragment.newInstance(requestid,"Home");
                String fragmtStatusTag = fragment2.getClass().getName();
                GlobalValues.getInstance().setFramgentTag(fragmtStatusTag);
                ((MainActivity) activity).selectedNavItem(R.id.navigation_home);
                CommonUtils.loadFragment(context, fragment2, fragmtStatusTag);

            }
        });
        getDetails();
    }

    void getDetails() {
        final LoadingProgressDialog dialog = new LoadingProgressDialog(context, "Please wait...");
        dialog.setCancelable(false);
        dialog.show();

        ApiInterface apiInterface = ApiClients.getClient(context).create(ApiInterface.class);
        Call<UsVeteranDetailPagesModel> call = apiInterface.getRequestUSVeteranApi(loginResponse.getData().getToken(), requestid);
        call.enqueue(new Callback<UsVeteranDetailPagesModel>() {
            @Override
            public void onResponse(Call<UsVeteranDetailPagesModel> call, Response<UsVeteranDetailPagesModel> response) {
                try {
                    dialog.dismiss();
                    if (response.code() >= 200 && response.code() <= 210) {
                        if (response.body().getSuccess().equalsIgnoreCase("true")) {
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
                            tv_date.setText(response.body().getData().getRequestDate());
                            tv_country.setText(response.body().getData().getCountry());
                            tv_us_veteran_name.setText(name);
                            tv_branch_of_military.setText(response.body().getData().getBranchOfmilitary());
                            tv_type.setText(response.body().getData().getTripType());
                            tv_request_by.setText(response.body().getData().getRequestedBy());
                            tv_request_date.setText(response.body().getData().getRequestDate());
                            tv_requested_time.setText(response.body().getData().getTimeReceived());
                            tv_date.setText(response.body().getData().getRequestDate());
                            tv_completed_time.setText(response.body().getData().getTimeCompleted());
                            tv_pickup_location.setText(response.body().getData().getRemovedFromAddress());
                            tv_drop_location.setText(response.body().getData().getTransferredToAddress());
                            tv_date_of_birth_age.setText(response.body().getData().getDateOfDeath()+" "+response.body().getData().getAge());
                            tv_date_time_of_death.setText(response.body().getData().getDateOfDeath()+" "+response.body().getData().getTimeOfDeath());
                            tv_next_of_kin_phone.setText(response.body().getData().getNextOfKinPhone());
                            if(response.body().getData().getRequestedItemsLists().equals("")){
                                tv_extra_item_request.setText("No");
                            }else {
                                tv_extra_item_request.setText(response.body().getData().getRequestedItemsLists());

                            }

                            if(response.body().getData().getBodyRelease().equals("")||response.body().getData().getBodyRelease().equals("0")){
                                tv_release_body.setText("No");
                            }else if(response.body().getData().getBodyRelease().equals("1")){
                                tv_release_body.setText("Yes");


                            }else {
                                tv_release_body.setText(response.body().getData().getBodyRelease());

                            }

                            if(response.body().getData().getPersonalEffectsLists().equals("")){
                                tv_personal_effect.setText("No");
                            }else {
                                tv_personal_effect.setText(response.body().getData().getPersonalEffectsLists());

                            }
                            //tv_sender_name.setText(name);
                            //tv_sender_mobile_no.setText(response.body().getData().getMobileNumber());
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
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<UsVeteranDetailPagesModel> call, Throwable t) {
                dialog.dismiss();
                Log.e(TAG, "onResponse: " + t.getMessage());
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}