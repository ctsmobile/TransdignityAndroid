package com.transdignity.driver.activities;

import androidx.databinding.DataBindingUtil;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.transdignity.driver.R;
import com.transdignity.driver.databinding.ActivityProfileBinding;
import com.transdignity.driver.models.auth.LoginResponse;
import com.transdignity.driver.models.auth.ProfileDetailsResponse;
import com.transdignity.driver.models.auth.pofileEdit.EditProfileRequest;
import com.transdignity.driver.models.auth.pofileEdit.EditProfileResponse;
import com.transdignity.driver.network.ApiClients;
import com.transdignity.driver.network.ApiInterface;
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

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityProfileBinding binding;
    LoginResponse loginResponse;
    String userId;
    String android_id, fcm_token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile);
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
        android_id = Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);

        Log.e(TAG, "onViewCreated: fcmmmm>>>>  " + fcm_token);
        getProfileDetails();
        //Demo demo = getIntent().getParcelableExtra("id");

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

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.ll_submit:
                editProfileApi();
                break;
            case R.id.tv_edit:
                editMode();
                break;
        }
    }

    void getProfileDetails() {
        final LoadingProgressDialog dialog = new LoadingProgressDialog(this, "Please wait...");
        dialog.setCancelable(false);
        dialog.show();
        ApiInterface apiInterface = ApiClients.getConnection(this);
        Call<ProfileDetailsResponse> call = apiInterface.getProfileDetailsApi(loginResponse.getData().getToken(), userId, MyConstants.GROUP_ID);
        call.enqueue(new Callback<ProfileDetailsResponse>() {
            @Override
            public void onResponse(Call<ProfileDetailsResponse> call, Response<ProfileDetailsResponse> response) {
                try {
                    dialog.dismiss();
                    if (response.code() >= 200 && response.code() <= 210) {
                        if (response.body().getSuccess().equalsIgnoreCase("true")) {

                            Log.e(TAG, "onResponse: " + response.body().getMessage());
                            Toast.makeText(ProfileActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(ProfileActivity.this, message, Toast.LENGTH_LONG).show();

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
                Toast.makeText(ProfileActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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

        final LoadingProgressDialog dialog = new LoadingProgressDialog(this, "Please wait...");
        dialog.setCancelable(false);
        dialog.show();

        ApiInterface apiInterface = ApiClients.getConnection(this);
        EditProfileRequest request = new EditProfileRequest();
        request.setUserId(Integer.parseInt(userId));
        request.setUserGroupId(MyConstants.GROUP_ID);
        request.setDeviceId(android_id);
        request.setDevice("Android");
        request.setFcmKey(GlobalValues.getPreferenceManager().getFcmString(PreferenceManager.FCMKEY));
        request.setNameOfBusiness(binding.etFullname.getText().toString().trim());
        request.setPrimaryPersonalPhone(binding.etMobileNumber.getText().toString().trim());
        request.setAddress(binding.etEnterAddress.getText().toString().trim());


        Call<EditProfileResponse> call = apiInterface.editProfileApi(loginResponse.getData().getToken(), request);
        call.enqueue(new Callback<EditProfileResponse>() {
            @Override
            public void onResponse(Call<EditProfileResponse> call, Response<EditProfileResponse> response) {
                try {
                    dialog.dismiss();
                    if (response.code() >= 200 && response.code() <= 210) {
                        if (response.body().getSuccess().equalsIgnoreCase("true")) {
                            viewMode();
                            Log.e(TAG, "onResponse: " + response.body().getMessage());
                            Toast.makeText(ProfileActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(ProfileActivity.this, message, Toast.LENGTH_LONG).show();

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
                Toast.makeText(ProfileActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
