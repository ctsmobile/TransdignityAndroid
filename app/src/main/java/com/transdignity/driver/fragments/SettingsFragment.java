package com.transdignity.driver.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.transdignity.driver.MainActivity;
import com.transdignity.driver.R;
import com.transdignity.driver.activities.HtmlDocsViewerActivity;
import com.transdignity.driver.activities.LoginActivity;
import com.transdignity.driver.activities.ProfileActivity;
import com.transdignity.driver.activities.WebviewActivity;
import com.transdignity.driver.databinding.FragmentSettingsBinding;
import com.transdignity.driver.fragments.bankDetailsFragments.AddBankDetailsFragment;
import com.transdignity.driver.fragments.bankDetailsFragments.NewAddBankDetailsFragment;
import com.transdignity.driver.fragments.bankDetailsFragments.ShowBankDetailFragment;
import com.transdignity.driver.models.Demo;
import com.transdignity.driver.models.auth.AboutUsResponse;
import com.transdignity.driver.models.auth.LoginResponse;
import com.transdignity.driver.models.auth.LogoutRequest;
import com.transdignity.driver.models.auth.LogoutResponse;
import com.transdignity.driver.models.auth.ProfileDetailsResponse;
import com.transdignity.driver.models.bankDetail.AddAccountDetailRequestModel;
import com.transdignity.driver.models.bankDetail.AddAccountDetailResponseModel;
import com.transdignity.driver.models.bankDetail.ModeOfPaymentRequest;
import com.transdignity.driver.models.bankDetail.ModeOfPaymentResponse;
import com.transdignity.driver.models.vehicle.VehicleDetailsResponseModel;
import com.transdignity.driver.network.ApiClients;
import com.transdignity.driver.network.ApiInterface;
import com.transdignity.driver.utilities.CommonUtils;
import com.transdignity.driver.utilities.GlobalValues;
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

public class SettingsFragment extends Fragment implements View.OnClickListener {
    Context context;
    Activity activity;
    TextView tvBack, tvToolbar;
    LinearLayout llProfile,ll_account_details,ll_show_account_details;
    Demo demo;
    FragmentSettingsBinding binding;
    String userId, url,userGroupId;
    LoginResponse loginResponse;
    SwitchCompat cashPaySwitch,onlinePaySwitch;
    String mode_type;
    LinearLayout ll_vehicle_details,ll_add_vehicle;
    public SettingsFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
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
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvToolbar = activity.findViewById(R.id.tv_toolbar);
        ll_account_details = view.findViewById(R.id.ll_account_details);
        ll_show_account_details = view.findViewById(R.id.ll_show_account_details);
        cashPaySwitch = view.findViewById(R.id.cashPaySwitch);
        ll_vehicle_details = view.findViewById(R.id.ll_vehicle_details);
        ll_add_vehicle = view.findViewById(R.id.ll_add_vehicle);
        onlinePaySwitch = view.findViewById(R.id.onlinePaySwitch);
        tvToolbar.setText(R.string.settings);
        loginResponse = (LoginResponse) GlobalValues.getPreferenceManager().getObject(PreferenceManager.LoginResponseKey, LoginResponse.class);
        if (loginResponse != null) {
            userId = loginResponse.getData().getId();
            userGroupId = loginResponse.getData().getUserGroupId();
            aboutUsApi();
            binding.tvName.setText(loginResponse.getData().getUsername());
            // getProfileDetails();
        }
        //tvBack.setOnClickListener(this);
        // llProfile.setOnClickListener(this);
        // ((MainActivity)getActivity()).selectedNavItem(R.id.navigaton_settings);
        binding.llLogout.setOnClickListener(this);
        binding.llAboutUs.setOnClickListener(this);
        binding.llProfile.setOnClickListener(this);
        binding.llHelp.setOnClickListener(this);
        binding.llChangePassword.setOnClickListener(this);
        binding.llDocuments.setOnClickListener(this);
        ll_account_details.setOnClickListener(this);
        ll_show_account_details.setOnClickListener(this);
        ll_vehicle_details.setOnClickListener(this);
        ll_add_vehicle.setOnClickListener(this);

        cashPaySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean check) {
                if (check) {
                    modeOfPaymentApi("CASH");
                    onlinePaySwitch.setChecked(false);
                } else {
                    onlinePaySwitch.setChecked(true);
                }
            }
        });

        onlinePaySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean check) {
                if (check) {
                    modeOfPaymentApi("THROUGH_BANK");
                    cashPaySwitch.setChecked(false);
                } else {
                    cashPaySwitch.setChecked(true);
                }
            }
        });
        getVehicleDetailsApi();
        getProfileDetails();
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tv_back:
                /*Fragment fragment;
                fragment = new ContactUsFragment();
                String fragmtStatusTag = fragment.getClass().getName();
                ((MainActivity)getActivity()).selectedNavItem(R.id.navigation_rating);
                CommonUtils.loadFragment(context, fragment, fragmtStatusTag);*/
                break;
            case R.id.ll_help:
                Fragment fragment = new ContactUsFragment();
                String fragmtStatusTag = fragment.getClass().getName();
                ((MainActivity) getActivity()).selectedNavItem(R.id.navigaton_account);
                CommonUtils.loadFragment(context, fragment, fragmtStatusTag);
                break;
            case R.id.ll_about_us:
                if (url != null) {
                    Intent intent = new Intent(context, WebviewActivity.class);
                    intent.putExtra("url", url);
                    CommonUtils.switchActivityWithIntent(activity, intent);
                }
                break;
            case R.id.ll_documents:
                documentsDisplayApi();
                break;
            case R.id.ll_profile:
                Intent intent = new Intent(context, ProfileActivity.class);
                //   intent.putExtra("id", demo);
                CommonUtils.switchActivity(activity, ProfileActivity.class);
                break;
            case R.id.ll_change_password:
                Fragment fragmentPassword = new ChangePasswordFragment();
                String fragmtPasswordTag = fragmentPassword.getClass().getName();
                ((MainActivity) getActivity()).selectedNavItem(R.id.navigaton_account);
                CommonUtils.loadFragment(context, fragmentPassword, fragmtPasswordTag);
                break;

            case R.id.ll_account_details:
                Fragment fragmentac= new NewAddBankDetailsFragment();
                String fragmtStatusTagAc = fragmentac.getClass().getName();
                ((MainActivity) getActivity()).selectedNavItem(R.id.navigaton_account);
                GlobalValues.getInstance().setFramgentTag(fragmtStatusTagAc);
                CommonUtils.loadFragment(context, fragmentac, fragmtStatusTagAc);
                break;
            case R.id.ll_show_account_details:
                Fragment fragmentsac= new ShowBankDetailFragment();
                String fragmtStatusTagSAc = fragmentsac.getClass().getName();
                ((MainActivity) getActivity()).selectedNavItem(R.id.navigaton_account);
                GlobalValues.getInstance().setFramgentTag(fragmtStatusTagSAc);
                CommonUtils.loadFragment(context, fragmentsac, fragmtStatusTagSAc);
                break;
            case R.id.ll_add_vehicle:
                Fragment fragment_vehicle= VehicleDetailsFragment.newInstance("");
                String fragmtStatusTag_vehicle = fragment_vehicle.getClass().getName();
                ((MainActivity) getActivity()).selectedNavItem(R.id.navigaton_account);
                GlobalValues.getInstance().setFramgentTag(fragmtStatusTag_vehicle);
                CommonUtils.loadFragment(context, fragment_vehicle, fragmtStatusTag_vehicle);
                break;
            case R.id.ll_vehicle_details:
                Fragment fragment_vehicle_detail= new ShowVehicleDetailsFragment();
                String fragmtStatusTag_vehicle_detail = fragment_vehicle_detail.getClass().getName();
                ((MainActivity) getActivity()).selectedNavItem(R.id.navigaton_account);
                GlobalValues.getInstance().setFramgentTag(fragmtStatusTag_vehicle_detail);
                CommonUtils.loadFragment(context, fragment_vehicle_detail, fragmtStatusTag_vehicle_detail);
                break;
            case R.id.ll_logout:
                if (CommonUtils.isNetwork(context)) {
                    logOutApi();
                }else {
                    Toast.makeText(context,"Please Check your Internet Connection!",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    void logOutApi() {
        final LoadingProgressDialog dialog = new LoadingProgressDialog(context, "Please wait...");
        dialog.setCancelable(false);
        dialog.show();
        LogoutRequest request = new LogoutRequest();
        request.setDevice("Android");
        request.setDeviceId(CommonUtils.getDeviceId(context));
        request.setFcmKey(GlobalValues.getPreferenceManager().getFcmString(PreferenceManager.FCMKEY));
        request.setUserId(Integer.parseInt(userId));
        ApiInterface apiInterface = ApiClients.getConnection(context);

        Call<LogoutResponse> call = apiInterface.logoutApi(loginResponse.getData().getToken(), request);
        call.enqueue(new Callback<LogoutResponse>() {
            @Override
            public void onResponse(Call<LogoutResponse> call, Response<LogoutResponse> response) {
                try {
                    dialog.dismiss();
                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    CommonUtils.logoutSession(activity);
                    Intent intentlogin = new Intent(activity, LoginActivity.class);
                    intentlogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intentlogin.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    CommonUtils.switchActivityWithIntent(activity, intentlogin);
                } catch (Exception e) {

                   // CommonUtils.logoutSession(activity);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<LogoutResponse> call, Throwable t) {
               // CommonUtils.logoutSession(activity);
                //Toast.makeText(context, "Something went wrong!", Toast.LENGTH_SHORT).show();

            }
        });
    }

    void aboutUsApi() {
        ApiInterface apiInterface = ApiClients.getConnection(context);

        Call<AboutUsResponse> call = apiInterface.aboutUstApi(loginResponse.getData().getToken());
        call.enqueue(new Callback<AboutUsResponse>() {
            @Override
            public void onResponse(Call<AboutUsResponse> call, Response<AboutUsResponse> response) {
                try {

                    if (response.code() >= 200 && response.code() <= 210) {
                        url = response.body().getData().get(0).getPageUrl();
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
            public void onFailure(Call<AboutUsResponse> call, Throwable t) {

            }
        });
    }

    void documentsDisplayApi() {
        String docUrl = MyConstants.BASE_URL + "home/document_setting/" + loginResponse.getData().getId() + "/" + loginResponse.getData().getToken();
        Intent intent = new Intent(context, WebviewActivity.class);
        intent.putExtra("url", docUrl);
        startActivity(intent);
    }

    /*void logoutSession() {
        GlobalValues.getPreferenceManager().logout();
        Intent intentlogin = new Intent(context, LoginActivity.class);
        intentlogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intentlogin.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        CommonUtils.switchActivityWithIntent(activity, intentlogin);
    }*/

    public void modeOfPaymentApi(String mode) {

        final LoadingProgressDialog dialog = new LoadingProgressDialog(context, "Please wait...");
        dialog.setCancelable(false);
        dialog.show();
        ApiInterface apiInterface = ApiClients.getClient(context).create(ApiInterface.class);
        final ModeOfPaymentRequest req = new ModeOfPaymentRequest();
       /* req.setDevice("Android");
        req.setDeviceId(CommonUtils.getDeviceId(context));*/
        req.setUserId(userId);
        req.setUserGroupId(userGroupId);
        req.setModeOfPayment(mode);




        Call<ModeOfPaymentResponse> call = apiInterface.modeOfPayment(loginResponse.getData().getToken(), req);

        call.enqueue(new Callback<ModeOfPaymentResponse>() {
            @Override
            public void onResponse(Call<ModeOfPaymentResponse> call, Response<ModeOfPaymentResponse> response) {
                dialog.dismiss();
                try {
                    if (response.code() >= 200 && response.code() <= 210) {
                        if (response.body().getSuccess().equalsIgnoreCase("true")) {
                            Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();


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
            public void onFailure(Call<ModeOfPaymentResponse> call, Throwable t) {
                Toast.makeText(context, "Something Went Wrong!", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });

    }

    void getVehicleDetailsApi() {

       /* final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Loading.....");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(true);
        progressDialog.show();*/


        ApiInterface apiInterface = ApiClients.getClient(getActivity()).create(ApiInterface.class);
        Call<VehicleDetailsResponseModel> call = null;

        call = apiInterface.vehicleDetailList(loginResponse.getData().getToken(),userId);

        call.enqueue(new Callback<VehicleDetailsResponseModel>() {
            @Override
            public void onResponse(Call<VehicleDetailsResponseModel> call, Response<VehicleDetailsResponseModel> response) {
                try {
                    //progressDialog.dismiss();
                    if (response.code() >= 200 && response.code() <= 210) {
                       ll_add_vehicle.setVisibility(View.GONE);
                    }else if(response.code() >= 400){
                        ll_add_vehicle.setVisibility(View.VISIBLE);



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
                            Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    //progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<VehicleDetailsResponseModel> call, Throwable t) {
                //progressDialog.dismiss();
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();


            }
        });

    }
    void getProfileDetails() {
        final LoadingProgressDialog dialog = new LoadingProgressDialog(getActivity(), "Please wait...");
        dialog.setCancelable(false);
        dialog.show();
        ApiInterface apiInterface = ApiClients.getConnection(getActivity());
        Call<ProfileDetailsResponse> call = apiInterface.getProfileDetailsApi(loginResponse.getData().getToken(), userId, MyConstants.GROUP_ID);
        call.enqueue(new Callback<ProfileDetailsResponse>() {
            @Override
            public void onResponse(Call<ProfileDetailsResponse> call, Response<ProfileDetailsResponse> response) {
                try {
                    dialog.dismiss();
                    if (response.code() >= 200 && response.code() <= 210) {
                        if (response.body().getSuccess().equalsIgnoreCase("true")) {

                            Log.e(TAG, "onResponse: " + response.body().getMessage());
                            String mode_of_payment=response.body().getData().getModeOfPayment().toString();
                             if(mode_of_payment.equals("CASH")){
                                 cashPaySwitch.setChecked(true);
                                 onlinePaySwitch.setChecked(false);

                             }else if(mode_of_payment.equals("THROUGH_BANK")){
                                 onlinePaySwitch.setChecked(true);
                                 cashPaySwitch.setChecked(false);


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
            public void onFailure(Call<ProfileDetailsResponse> call, Throwable t) {
                dialog.dismiss();
                Log.e(TAG, "onResponse: " + t.getMessage());
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
