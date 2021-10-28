package com.cts.removalspecialist.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.appcompat.widget.SwitchCompat;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cts.removalspecialist.MainActivity;
import com.cts.removalspecialist.activities.LoginActivity;
import com.cts.removalspecialist.activities.ProfileActivity;
import com.cts.removalspecialist.activities.WebviewActivity;
import com.cts.removalspecialist.databinding.FragmentSettingsBinding;
import com.cts.removalspecialist.fragments.bankDetailsFragments.AddBankDetailsFragment;
import com.cts.removalspecialist.fragments.bankDetailsFragments.NewAddBankDetailsFragment;
import com.cts.removalspecialist.fragments.bankDetailsFragments.ShowBankDetailFragment;
import com.cts.removalspecialist.models.auth.AboutUsResponse;
import com.cts.removalspecialist.models.auth.LoginResponse;
import com.cts.removalspecialist.models.auth.ProfileDetailsResponse;
import com.cts.removalspecialist.models.bankDetail.ModeOfPaymentRequest;
import com.cts.removalspecialist.models.bankDetail.ModeOfPaymentResponse;
import com.cts.removalspecialist.network.ApiClients;
import com.cts.removalspecialist.network.ApiInterface;
import com.cts.removalspecialist.utilities.CommonUtils;
import com.cts.removalspecialist.R;
import com.cts.removalspecialist.utilities.GlobalValues;
import com.cts.removalspecialist.utilities.LoadingProgressDialog;
import com.cts.removalspecialist.utilities.MyConstants;
import com.cts.removalspecialist.utilities.PreferenceManager;
import com.google.gson.JsonObject;

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
    TextView tvBack;
    LinearLayout llProfile;
    //Demo demo;
    FragmentSettingsBinding binding;
    String userId, url,userGroupId;
    LoginResponse loginResponse;
    LinearLayout ll_account_details,ll_show_account_details;
    SwitchCompat cashPaySwitch,onlinePaySwitch;

    public SettingsFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance(String param1, String param2) {
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
        View v = binding.getRoot();
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        ll_account_details = view.findViewById(R.id.ll_account_details);
        ll_show_account_details = view.findViewById(R.id.ll_show_account_details);
        cashPaySwitch = view.findViewById(R.id.cashPaySwitch);
        onlinePaySwitch = view.findViewById(R.id.onlinePaySwitch);
        loginResponse = (LoginResponse) GlobalValues.getPreferenceManager().getObject(PreferenceManager.LoginResponseKey, LoginResponse.class);
        if (loginResponse != null) {
            userId = loginResponse.getData().getId();
            userGroupId = loginResponse.getData().getUserGroupId();

            binding.tvName.setText(loginResponse.getData().getUsername());
            aboutUsApi();
        }

        tvBack.setOnClickListener(this);
        binding.llLogout.setOnClickListener(this);
        binding.llChangePassword.setOnClickListener(this);
        llProfile.setOnClickListener(this);
        binding.llAboutusPages.setOnClickListener(this);
        ll_account_details.setOnClickListener(this);
        ll_show_account_details.setOnClickListener(this);
        // ((MainActivity)getActivity()).selectedNavItem(R.id.navigaton_settings);

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
        getProfileDetails();
    }

    void initView(View view) {
        tvBack = view.findViewById(R.id.tv_back);
        llProfile = view.findViewById(R.id.ll_profile);
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
                Fragment fragment;
                fragment = new ContactUsFragment();
                String fragmtStatusTag = fragment.getClass().getName();
                GlobalValues.getInstance().setFramgentTag(fragmtStatusTag);
                ((MainActivity) getActivity()).selectedNavItem(R.id.navigation_contactus);
                CommonUtils.loadFragment(context, fragment, fragmtStatusTag);
                break;
            case R.id.ll_logout:

                if (CommonUtils.isNetwork(context)) {
                    logOutApi();
                } else
                    Toast.makeText(context, "Please check your internet connection!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_change_password:
                Fragment fragmentChangePassword = new ChangePasswordFragment();
                String fragmtChangePass = fragmentChangePassword.getClass().getName();
                ((MainActivity) getActivity()).selectedNavItem(R.id.navigaton_settings);
                GlobalValues.getInstance().setFramgentTag(fragmtChangePass);
                CommonUtils.loadFragment(context, fragmentChangePassword, fragmtChangePass);
                break;
            case R.id.ll_profile:
                Fragment fragmentp = new ProfileFragment();
                String fragmtStatusTagP = fragmentp.getClass().getName();
                ((MainActivity) getActivity()).selectedNavItem(R.id.navigaton_settings);
                GlobalValues.getInstance().setFramgentTag(fragmtStatusTagP);
                CommonUtils.loadFragment(context, fragmentp, fragmtStatusTagP);
                break;
            case R.id.ll_account_details:
                Fragment fragmentac= new NewAddBankDetailsFragment();
                String fragmtStatusTagAc = fragmentac.getClass().getName();
                ((MainActivity) getActivity()).selectedNavItem(R.id.navigaton_settings);
                GlobalValues.getInstance().setFramgentTag(fragmtStatusTagAc);
                CommonUtils.loadFragment(context, fragmentac, fragmtStatusTagAc);
                break;
            case R.id.ll_show_account_details:
                Fragment fragmentsac= new ShowBankDetailFragment();
                String fragmtStatusTagSAc = fragmentsac.getClass().getName();
                ((MainActivity) getActivity()).selectedNavItem(R.id.navigaton_settings);
                GlobalValues.getInstance().setFramgentTag(fragmtStatusTagSAc);
                CommonUtils.loadFragment(context, fragmentsac, fragmtStatusTagSAc);
                break;
            case R.id.ll_aboutus_pages:
                if (url != null) {
                    Intent intent = new Intent(context, WebviewActivity.class);
                    intent.putExtra("url", url);
                    CommonUtils.switchActivityWithIntent(activity, intent);
                }
                break;
        }
    }

    void aboutUsApi() {
        ApiInterface apiInterface = ApiClients.getConnection(context);

        Call<AboutUsResponse> call = apiInterface.aboutUstApi(loginResponse.getData().getToken());
        call.enqueue(new Callback<AboutUsResponse>() {
            @Override
            public void onResponse(Call<AboutUsResponse> call, Response<AboutUsResponse> response) {
                try {

                    if (response.code() >= 200 && response.code() <= 210) {
                        if (response.body().getSuccess().equalsIgnoreCase("true")) {
                            url = response.body().getData().get(0).getPageUrl();
                        }
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
                            //    Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
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

    void logOutApi() {
        final LoadingProgressDialog dialog = new LoadingProgressDialog(context, "Please wait...");
        dialog.setCancelable(false);
        dialog.show();
       /* LogoutRequest request = new LogoutRequest();
        request.setDevice("Android");
        request.setDeviceId(CommonUtils.getDeviceId(context));
        request.setFcmKey(GlobalValues.getPreferenceManager().getFcmString(PreferenceManager.FCMKEY));
        request.setUserId(Integer.parseInt(userId));*/

        ApiInterface apiInterface = ApiClients.getConnection(context);
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("user_id", userId);


        Call<JsonObject> call = apiInterface.logoutApi(loginResponse.getData().getToken(), jsonObject);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                try {
                    dialog.dismiss();
                    if (response.code() >= 200 && response.code() <= 210) {
                        JSONObject object = new JSONObject(response.body().toString());
                        if (object != null) {
                            if (object.getString("success").equalsIgnoreCase("true")) {
                                CommonUtils.logoutSession(activity);
                                Toast.makeText(context, object.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        }
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
                    //Toast.makeText(context,object.getString("message"), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(context, "Something went wrong!", Toast.LENGTH_SHORT).show();
                CommonUtils.logoutSession(activity);
            }
        });
    }

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
    void getProfileDetails() {
        final LoadingProgressDialog dialog = new LoadingProgressDialog(context, "Please wait...");
        dialog.setCancelable(false);
        dialog.show();

        ApiInterface apiInterface = ApiClients.getConnection(context);
        Call<ProfileDetailsResponse> call = apiInterface.getProfileDetailsApi(loginResponse.getData().getToken(),userId, MyConstants.GROUP_ID);
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

                            // CommonUtils.switchActivity(activity, MainActivity.class);

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
                            //    Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
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
            public void onFailure(Call<ProfileDetailsResponse> call, Throwable t) {
                dialog.dismiss();
                Log.e(TAG, "onResponse: " + t.getMessage());
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
