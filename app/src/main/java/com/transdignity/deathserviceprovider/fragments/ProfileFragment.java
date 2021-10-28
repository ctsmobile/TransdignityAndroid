package com.transdignity.deathserviceprovider.fragments;

import android.app.Activity;
import android.content.Context;

import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.transdignity.deathserviceprovider.MainActivity;
import com.transdignity.deathserviceprovider.R;
import com.transdignity.deathserviceprovider.databinding.FragmentProfileBinding;
import com.transdignity.deathserviceprovider.models.auth.LoginResponse;
import com.transdignity.deathserviceprovider.models.auth.pofileEdit.EditProfileRequest;
import com.transdignity.deathserviceprovider.models.auth.pofileEdit.EditProfileResponse;
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


public class ProfileFragment extends Fragment implements View.OnClickListener {
    Activity activity;
    Context context;
    FragmentProfileBinding binding;
    LoginResponse loginResponse;
    String userId;
    String android_id,fcm_token;

    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewMode();
        binding.tvBack.setOnClickListener(this);
        binding.tvEdit.setOnClickListener(this);
        binding.llSubmit.setOnClickListener(this);
        loginResponse = (LoginResponse) GlobalValues.getPreferenceManager().getObject(PreferenceManager.LoginResponseKey, LoginResponse.class);
        if (loginResponse != null) {
            userId = loginResponse.getData().getId();
        } else {
            userId = null;
        }
        android_id = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);

        Log.e(TAG, "onViewCreated: fcmmmm>>>>  "+fcm_token );
        getProfileDetails();

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tv_back:
                Fragment fragment;
                fragment = new SettingsFragment();
                String fragmtStatusTag = fragment.getClass().getName();
                ((MainActivity) getActivity()).selectedNavItem(R.id.navigaton_settings);
                CommonUtils.loadFragment(context, fragment, fragmtStatusTag);

                break;
            case R.id.ll_submit:
                editProfileApi();
                break;
            case R.id.tv_edit:
                editMode();
                break;
        }
    }

    void viewMode() {
        binding.tvEdit.setVisibility(View.VISIBLE);
        binding.cvSubmit.setVisibility(View.INVISIBLE);
        binding.etFullname.setFocusableInTouchMode(false);
        binding.etMobileNumber.setFocusableInTouchMode(false);
        binding.etEnterAddress.setFocusableInTouchMode(false);
        binding.etFullname.setFocusable(false);
        binding.etMobileNumber.setFocusable(false);
        binding.etEnterAddress.setFocusable(false);
    }

    void editMode() {
        binding.tvEdit.setVisibility(View.INVISIBLE);
        binding.cvSubmit.setVisibility(View.VISIBLE);
        binding.etFullname.setFocusableInTouchMode(true);
        binding.etMobileNumber.setFocusableInTouchMode(true);
        binding.etEnterAddress.setFocusableInTouchMode(true);
        binding.etFullname.setFocusable(true);
        binding.etMobileNumber.setFocusable(true);
        binding.etEnterAddress.setFocusable(true);
    }

    void getProfileDetails() {
        final LoadingProgressDialog dialog = new LoadingProgressDialog(context, "Please wait...");
        dialog.setCancelable(false);
        dialog.show();

        ApiInterface apiInterface = ApiClients.getClient(context).create(ApiInterface.class);
        Call<ProfileDetailsResponse> call = apiInterface.getProfileDetailsApi(loginResponse.getData().getToken(),userId,"2");
        call.enqueue(new Callback<ProfileDetailsResponse>() {
            @Override
            public void onResponse(Call<ProfileDetailsResponse> call, Response<ProfileDetailsResponse> response) {
                try {
                    dialog.dismiss();
                    if (response.code() >= 200 && response.code() <= 210) {
                        if (response.body().getSuccess().equalsIgnoreCase("true")) {

                            Log.e(TAG, "onResponse: " + response.body().getMessage());
                            Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            binding.etFullname.setText(response.body().getData().getFullName());
                            binding.etMobileNumber.setText(response.body().getData().getPhoneNumber());
                            binding.etEnterEmailid.setText(response.body().getData().getEmail());
                            binding.etEnterAddress.setText(response.body().getData().getAddress());

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
            public void onFailure(Call<ProfileDetailsResponse> call, Throwable t) {
                dialog.dismiss();
                Log.e(TAG, "onResponse: " + t.getMessage());
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    void editProfileApi() {
        if (TextUtils.isEmpty(binding.etFullname.getText().toString().trim())) {
            binding.etFullname.setError("Please Enter Name");
            binding.etFullname.requestFocus();
            return;
        } else if (TextUtils.isEmpty(binding.etMobileNumber.getText().toString().trim())) {
            binding.etMobileNumber.setError("Please Enter Mobile Number");
            binding.etMobileNumber.requestFocus();
            return;
        } else if (TextUtils.isEmpty(binding.etEnterAddress.getText().toString().trim())) {
            binding.etEnterAddress.setError("Please Enter Mobile Number");
            binding.etEnterAddress.requestFocus();
            return;
        }

        final LoadingProgressDialog dialog = new LoadingProgressDialog(context, "Please wait...");
        dialog.setCancelable(false);
        dialog.show();

        ApiInterface apiInterface = ApiClients.getClient(context).create(ApiInterface.class);
        EditProfileRequest request = new EditProfileRequest();
        request.setUserId(Integer.parseInt(userId));
        request.setUserGroupId(2);
        request.setDeviceId(android_id);
        request.setDevice("Android");
        request.setFcmKey(GlobalValues.getPreferenceManager().getFcmString(PreferenceManager.FCMKEY));
        request.setNameOfBusiness(binding.etFullname.getText().toString().trim());
        request.setPrimaryPersonalPhone(binding.etMobileNumber.getText().toString().trim());
        request.setAddress(binding.etEnterAddress.getText().toString().trim());


        Call<EditProfileResponse> call = apiInterface.editProfileApi(loginResponse.getData().getToken(),request);
        call.enqueue(new Callback<EditProfileResponse>() {
            @Override
            public void onResponse(Call<EditProfileResponse> call, Response<EditProfileResponse> response) {
                try {
                    dialog.dismiss();
                    if (response.code() >= 200 && response.code() <= 210) {
                        if (response.body().getSuccess().equalsIgnoreCase("true")) {
                            viewMode();
                            Log.e(TAG, "onResponse: " + response.body().getMessage());
                            Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            binding.etFullname.setText(response.body().getData().getBusinessName());
                            binding.etMobileNumber.setText(response.body().getData().getPersonalPhone());
                            binding.etEnterEmailid.setText(response.body().getData().getEmail());
                            binding.etEnterAddress.setText(response.body().getData().getAddress());

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
            public void onFailure(Call<EditProfileResponse> call, Throwable t) {
                dialog.dismiss();
                Log.e(TAG, "onResponse: " + t.getMessage());
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
