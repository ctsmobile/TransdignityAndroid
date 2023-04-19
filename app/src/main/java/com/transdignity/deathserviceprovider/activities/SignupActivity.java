package com.transdignity.deathserviceprovider.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.provider.Settings;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.transdignity.deathserviceprovider.R;
import com.transdignity.deathserviceprovider.databinding.ActivitySignupBinding;
import com.transdignity.deathserviceprovider.models.auth.SignupRequest;
import com.transdignity.deathserviceprovider.models.auth.SignupResponse;
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

import static com.transdignity.deathserviceprovider.utilities.MyConstants.emailPattern;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {
    ActivitySignupBinding binding;
    String TAG = "SignupActivity";
    String android_id,fcm_token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup);
        android_id = Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        fcm_token =GlobalValues.getPreferenceManager().getFcmString(PreferenceManager.FCMKEY);
        step1ContainerVisible();
        binding.step2Btn.setOnClickListener(this);
        binding.step3Btn.setOnClickListener(this);
        binding.llSignupBtn.setOnClickListener(this);
        binding.tvLogin.setOnClickListener(this);
        binding.skipSignupStep.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.step2_btn:
                step1Validation();
                // step2ContainerVisible();
                break;
            case R.id.step3_btn:
                step2Validation();
                //step3ContainerVisible();
                break;
            case R.id.ll_signup_btn:
                step3Validation();
               /* Intent intent = new Intent(this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                CommonUtils.switchActivityWithIntent(this, intent);*/
                break;
            case R.id.skip_signup_step:
                Intent intent1 = new Intent(this, LoginActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                CommonUtils.switchActivityWithIntent(this, intent1);
                break;
            case R.id.tv_login:
                Intent intent2 = new Intent(this, LoginActivity.class);
                intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                CommonUtils.switchActivityWithIntent(this, intent2);
                break;


        }
    }

    void step1Validation() {
        if (TextUtils.isEmpty(binding.etNameofbusiness.getText().toString().trim())) {
            binding.etNameofbusiness.setError("Please enter Name of Business!");
            binding.etNameofbusiness.requestFocus();
        } else if (TextUtils.isEmpty(binding.etAddressOfbusiness.getText().toString().trim())) {
            binding.etAddressOfbusiness.setError("Please enter address of business!");
            binding.etAddressOfbusiness.requestFocus();
        }  else if (TextUtils.isEmpty(binding.etYrInBusiness.getText().toString().trim())) {
            binding.etYrInBusiness.setError("Please enter Year of Business!");
            binding.etYrInBusiness.requestFocus();
        }  else if (TextUtils.isEmpty(binding.etLicense.getText().toString().trim())) {
            binding.etLicense.setError("Please enter License Number!");
            binding.etLicense.requestFocus();
        } else if (TextUtils.isEmpty(binding.etPassword.getText().toString().trim())) {
            binding.etPassword.setError("Please enter Password!");
            binding.etPassword.requestFocus();
        } else if (binding.etPassword.getText().toString().trim().length() < 5) {
            binding.etPassword.setError("Please enter valid 5 digit Password!");
            binding.etPassword.requestFocus();
        } else if (binding.etConfPassword.getText().toString().trim().length() < 5) {
            binding.etConfPassword.setError("Please enter Valid 5 Confirm Password!");
            binding.etConfPassword.requestFocus();
        } else if (!binding.etPassword.getText().toString().trim().equals(binding.etConfPassword.getText().toString().trim())) {
            binding.etConfPassword.setError("Password Mismatched!");
            binding.etConfPassword.requestFocus();
        } else {
            step2ContainerVisible();
        }
    }

    void step2Validation() {
        if (TextUtils.isEmpty(binding.etPriContName.getText().toString().trim())) {
            binding.etPriContName.setError("Please enter Primary Name!");
            binding.etPriContName.requestFocus();
        } else if (TextUtils.isEmpty(binding.etPriTitle.getText().toString().trim())) {
            binding.etPriTitle.setError("Please enter Title!");
            binding.etPriTitle.requestFocus();
        } else if (TextUtils.isEmpty(binding.etPriOfficePhone.getText().toString().trim())) {
            binding.etPriOfficePhone.setError("Please enter Office Phone!");
            binding.etPriOfficePhone.requestFocus();
        } else if (binding.etPriOfficePhone.getText().toString().trim().length() < 10 || binding.etPriOfficePhone.getText().toString().trim().length() > 12) {
            binding.etPriOfficePhone.setError("Please enter Valid Office Phone!");
            binding.etPriOfficePhone.requestFocus();
        } else if (TextUtils.isEmpty(binding.etpriCellPhone.getText().toString().trim())) {
            binding.etpriCellPhone.setError("Please enter Cell Phone Number!");
            binding.etpriCellPhone.requestFocus();
        } else if (binding.etpriCellPhone.getText().toString().trim().length() < 10 || binding.etpriCellPhone.getText().toString().trim().length() > 12) {
            binding.etpriCellPhone.setError("Please enter Valid Cell Phone!");
            binding.etpriCellPhone.requestFocus();
        } else if (TextUtils.isEmpty(binding.etPriEmail.getText().toString().trim())) {
            binding.etPriEmail.setError("Please enter Email!");
            binding.etPriEmail.requestFocus();
        } else if (!binding.etPriEmail.getText().toString().trim().matches(emailPattern)) {
            binding.etPriEmail.setError("Please enter Valid Email!");
            binding.etPriEmail.requestFocus();
        } else {
            step3ContainerVisible();
        }
    }

    void step3Validation() {
        if (TextUtils.isEmpty(binding.etSecContName.getText().toString().trim())) {
            binding.etSecContName.setError("Please enter Secondary Name!");
            binding.etSecContName.requestFocus();
        } else if (TextUtils.isEmpty(binding.etSecTitle.getText().toString().trim())) {
            binding.etSecTitle.setError("Please enter Title!");
            binding.etSecTitle.requestFocus();
        } else if (TextUtils.isEmpty(binding.etSecOfficePhone.getText().toString().trim())) {
            binding.etSecOfficePhone.setError("Please enter Office Phone!");
            binding.etSecOfficePhone.requestFocus();
        } else if (binding.etSecOfficePhone.getText().toString().trim().length() < 10 || binding.etSecOfficePhone.getText().toString().trim().length() > 12) {
            binding.etSecOfficePhone.setError("Please enter Valid Office Phone!");
            binding.etSecOfficePhone.requestFocus();
        } else if (TextUtils.isEmpty(binding.etSecCellPhone.getText().toString().trim())) {
            binding.etSecCellPhone.setError("Please enter Cell Phone Number!");
            binding.etSecCellPhone.requestFocus();
        } else if (binding.etSecCellPhone.getText().toString().trim().length() < 10 || binding.etSecCellPhone.getText().toString().trim().length() > 12) {
            binding.etSecCellPhone.setError("Please enter Valid Cell Phone!");
            binding.etSecCellPhone.requestFocus();
        } else if (TextUtils.isEmpty(binding.etSecEmail.getText().toString().trim())) {
            binding.etSecEmail.setError("Please enter Password!");
            binding.etSecEmail.requestFocus();
        } else if (!binding.etSecEmail.getText().toString().trim().matches(emailPattern)) {
            binding.etSecEmail.setError("Please enter Valid Email!");
            binding.etSecEmail.requestFocus();
        } else {
            callSignUpApi();
        }
    }

    void callSignUpApi() {
        final LoadingProgressDialog dialog = new LoadingProgressDialog(this, "Please wait...");
        dialog.setCancelable(false);
        dialog.show();
        SignupRequest request = new SignupRequest();
        request.setDevice("Android");
        request.setDeviceId(android_id);
        request.setFcmKey(fcm_token);

        request.setNameOfBusiness(binding.etNameofbusiness.getText().toString().trim());
        request.setAddress(binding.etAddressOfbusiness.getText().toString().trim());
        request.setYearsInBusiness(Integer.parseInt(binding.etYrInBusiness.getText().toString().trim()));
        request.setLicenseNumber(binding.etLicense.getText().toString().trim());
        request.setPassword(binding.etPassword.getText().toString().trim());
        request.setConfirmPassword(binding.etConfPassword.getText().toString().trim());
        request.setPrimaryContactName(binding.etPriContName.getText().toString().trim());
        request.setPrimaryTitle(binding.etPriTitle.getText().toString().trim());
        request.setPrimaryOfcPhone(binding.etPriOfficePhone.getText().toString().trim());
        request.setPrimaryPersonalPhone(binding.etpriCellPhone.getText().toString().trim());
        request.setPrimaryEmail(binding.etPriEmail.getText().toString().trim());
        request.setSecondaryContactName(binding.etSecContName.getText().toString().trim());
        request.setSecondaryTitle(binding.etSecTitle.getText().toString().trim());
        request.setSecondaryOfcPhone(binding.etSecOfficePhone.getText().toString().trim());
        request.setSecondaryPersonalPhone(binding.etSecCellPhone.getText().toString().trim());
        request.setSecondaryEmail(binding.etSecEmail.getText().toString().trim());
        request.setUserGroupId(2);
        ApiInterface apiInterface = ApiClients.getClient(getApplicationContext()).create(ApiInterface.class);
        Call<SignupResponse> call = apiInterface.signupApi(request);
        call.enqueue(new Callback<SignupResponse>() {
            @Override
            public void onResponse(Call<SignupResponse> call, Response<SignupResponse> response) {
                try {
                    dialog.dismiss();
                    if (response.code() >= 200 && response.code() <= 210) {
                        if (response.body().getSuccess().equalsIgnoreCase("true")) {
                            Log.e(TAG, "onResponse: " + response.body().getMessage());
                            //Toast.makeText(SignupActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            successDialog(response.body().getMessage(), SignupActivity.this, SignupActivity.this);

                            //CommonUtils.switchActivity(SignupActivity.this, MainActivity.class);
                            // finish();
                        } else {
                            Toast.makeText(SignupActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        GlobalValues.getPreferenceManager().setloginPref(false);
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
                            Toast.makeText(SignupActivity.this, message, Toast.LENGTH_LONG).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<SignupResponse> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(SignupActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    void step1ContainerVisible() {
        binding.llStep1Container.setVisibility(View.VISIBLE);
        binding.llStep2Container.setVisibility(View.GONE);
        binding.llStep3Container.setVisibility(View.GONE);
        binding.tvTopMsg.setText(R.string.death_service_provider_registration);
    }

    void step2ContainerVisible() {
        binding.llStep1Container.setVisibility(View.GONE);
        binding.llStep2Container.setVisibility(View.VISIBLE);
        binding.llStep3Container.setVisibility(View.GONE);
        binding.tvTopMsg.setText("Primary Contact Details");
    }

    void step3ContainerVisible() {
        binding.llStep1Container.setVisibility(View.GONE);
        binding.llStep2Container.setVisibility(View.GONE);
        binding.llStep3Container.setVisibility(View.VISIBLE);
        binding.tvTopMsg.setText("Secondary Contact Details");
    }

    public static void successDialog(String succesMsg, final Context context, final Activity activity) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View viewCustom = inflater.inflate(R.layout.forget_verifylink_sent_dialog, null);
        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.setView(viewCustom);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView succesText = viewCustom.findViewById(R.id.msg);

        succesText.setText(succesMsg);
        LinearLayout done = viewCustom.findViewById(R.id.ok_forgot);
        dialog.setCancelable(false);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                CommonUtils.switchActivityWithIntent(activity, intent);
                dialog.dismiss();
                //activity.overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
            }
        });
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        if (binding.llStep2Container.isShown()) {
            step1ContainerVisible();
        } else if (binding.llStep3Container.isShown()) {
            step2ContainerVisible();
        } else {
            super.onBackPressed();
        }

    }
}
