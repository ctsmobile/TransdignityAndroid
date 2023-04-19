package com.transdignity.deathserviceprovider.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.transdignity.deathserviceprovider.BuildConfig;
import com.transdignity.deathserviceprovider.MainActivity;
import com.transdignity.deathserviceprovider.R;
import com.transdignity.deathserviceprovider.activities.WebviewActivity;
import com.transdignity.deathserviceprovider.databinding.FragmentSettingsBinding;
import com.transdignity.deathserviceprovider.fragments.bankDetailsFragments.AddBankDetailsFragment;
import com.transdignity.deathserviceprovider.fragments.bankDetailsFragments.ShowBankDetailFragment;
import com.transdignity.deathserviceprovider.models.Demo;
import com.transdignity.deathserviceprovider.models.auth.AboutUsResponse;
import com.transdignity.deathserviceprovider.models.auth.LoginResponse;
import com.transdignity.deathserviceprovider.models.auth.LogoutRequest;
import com.transdignity.deathserviceprovider.models.auth.LogoutResponse;
import com.transdignity.deathserviceprovider.models.auth.ProfileDetailsResponse;
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

public class SettingsFragment extends Fragment implements View.OnClickListener {
    Context context;
    Activity activity;
    TextView tvBack;
    LinearLayout llProfile, llLogout, llChangePassword,ll_account_details,ll_show_account_details,ll_delete_account;
    Demo demo;
    FragmentSettingsBinding binding;
    String userId, url;
    LoginResponse loginResponse;

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
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);

        loginResponse = (LoginResponse) GlobalValues.getPreferenceManager().getObject(PreferenceManager.LoginResponseKey, LoginResponse.class);
        if (loginResponse != null) {
            userId = loginResponse.getData().getId();
            aboutUsApi();
            getProfileDetails();
        }

        tvBack.setOnClickListener(this);
        llProfile.setOnClickListener(this);
        llLogout.setOnClickListener(this);
        ll_account_details.setOnClickListener(this);
        ll_show_account_details.setOnClickListener(this);
        llChangePassword.setOnClickListener(this);
        ll_delete_account.setOnClickListener(this);
        binding.llAboutPages.setOnClickListener(this);
        binding.llShare.setOnClickListener(this);

        // ((MainActivity)getActivity()).selectedNavItem(R.id.navigaton_settings);
    }

    void initView(View view) {
        tvBack = view.findViewById(R.id.tv_back);
        llProfile = view.findViewById(R.id.ll_profile);
        llLogout = view.findViewById(R.id.ll_logout);
        llChangePassword = view.findViewById(R.id.ll_change_password);
        ll_account_details = view.findViewById(R.id.ll_account_details);
        ll_show_account_details = view.findViewById(R.id.ll_show_account_details);
        ll_delete_account = view.findViewById(R.id.ll_delete_account);
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
                ((MainActivity) getActivity()).selectedNavItem(R.id.navigation_contactus);
                GlobalValues.getInstance().setFramgentTag(fragmtStatusTag);
                CommonUtils.loadFragment(context, fragment, fragmtStatusTag);
                break;
            case R.id.ll_profile:
                Fragment fragmentp = new ProfileFragment();
                String fragmtStatusTagP = fragmentp.getClass().getName();
                ((MainActivity) getActivity()).selectedNavItem(R.id.navigaton_settings);
                GlobalValues.getInstance().setFramgentTag(fragmtStatusTagP);
                CommonUtils.loadFragment(context, fragmentp, fragmtStatusTagP);
                break;
            case R.id.ll_account_details:
                Fragment fragmentac= new AddBankDetailsFragment();
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
            case R.id.ll_change_password:
                Fragment fragmentChangePassword = new ChangePasswordFragment();
                String fragmtChangePass = fragmentChangePassword.getClass().getName();
                ((MainActivity) getActivity()).selectedNavItem(R.id.navigaton_settings);
                GlobalValues.getInstance().setFramgentTag(fragmtChangePass);
                CommonUtils.loadFragment(context, fragmentChangePassword, fragmtChangePass);
                break;
            case R.id.ll_about_pages:
                if (url != null) {
                    Intent intent = new Intent(context, WebviewActivity.class);
                    intent.putExtra("url", url);
                    CommonUtils.switchActivityWithIntent(activity, intent);
                }
                break;
            case R.id.ll_share:
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Death Service Provider");
                    String shareMessage = "\nLet me recommend you this application\n\n";
                    shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch (Exception e) {
                    //e.toString();
                }
                break;
            case R.id.ll_delete_account:
                deleteAccountApi();
                break;
            case R.id.ll_logout:
                logOutApi();

                break;
        }
    }

    void aboutUsApi() {
        ApiInterface apiInterface = ApiClients.getClient(context).create(ApiInterface.class);

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
    void deleteAccountApi() {
        final LoadingProgressDialog dialog = new LoadingProgressDialog(context, "Please wait...");
        dialog.setCancelable(false);
        dialog.show();
        ApiInterface apiInterface = ApiClients.getClient(context).create(ApiInterface.class);
        Call<JsonObject> call = apiInterface.deleteAccountApi(loginResponse.getData().getToken(),userId);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                String message="";
                try {
                    dialog.dismiss();
                    if (response.code() >= 200 && response.code() <= 210) {
                         JsonObject jsonObject = response.body();
                        assert jsonObject != null;
                        message = jsonObject.get("message").getAsString();
                        if (jsonObject.get("success").getAsString().equalsIgnoreCase("true")) {
                            CommonUtils.logoutSession(activity);
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
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
                            String mess = jsonObjectError.optString("message");
                            Toast.makeText(context, mess, Toast.LENGTH_LONG).show();
                            if (jsonObjectError.optString("token_valid").equalsIgnoreCase("false")) {
                                CommonUtils.logoutSession(activity);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    Toast.makeText(context,message, Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(context, "Something went wrong!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
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
        ApiInterface apiInterface = ApiClients.getClient(context).create(ApiInterface.class);

        Call<LogoutResponse> call = apiInterface.logoutApi(loginResponse.getData().getToken(), request);
        call.enqueue(new Callback<LogoutResponse>() {
            @Override
            public void onResponse(Call<LogoutResponse> call, Response<LogoutResponse> response) {
                try {
                    dialog.dismiss();
                    if (response.code() >= 200 && response.code() <= 210) {
                        if (response.body().getSuccess().equalsIgnoreCase("true")) {
                            CommonUtils.logoutSession(activity);
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
                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<LogoutResponse> call, Throwable t) {
                Toast.makeText(context, "Something went wrong!", Toast.LENGTH_SHORT).show();

            }
        });
    }

    void getProfileDetails() {
        final LoadingProgressDialog dialog = new LoadingProgressDialog(context, "Please wait...");
        dialog.setCancelable(false);
        dialog.show();

        ApiInterface apiInterface = ApiClients.getClient(context).create(ApiInterface.class);
        Call<ProfileDetailsResponse> call = apiInterface.getProfileDetailsApi(loginResponse.getData().getToken(), userId, "2");
        call.enqueue(new Callback<ProfileDetailsResponse>() {
            @Override
            public void onResponse(Call<ProfileDetailsResponse> call, Response<ProfileDetailsResponse> response) {
                try {
                    dialog.dismiss();
                    if (response.code() >= 200 && response.code() <= 210) {
                        if (response.body().getSuccess().equalsIgnoreCase("true")) {

                            Log.e(TAG, "onResponse: " + response.body().getMessage());
                            // Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            binding.tvUsername.setText(response.body().getData().getFullName());
                            // binding.tvEmail.setText(response.body().getData().getEmail());
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
                            //  Toast.makeText(context, message, Toast.LENGTH_LONG).show();
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
                //  Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
