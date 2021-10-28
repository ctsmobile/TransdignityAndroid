package com.transdignity.driver.activities;

import android.app.Activity;

import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

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

import com.google.gson.Gson;
import com.transdignity.driver.MainActivity;
import com.transdignity.driver.R;
import com.transdignity.driver.databinding.ActivityLoginBinding;
import com.transdignity.driver.models.auth.ForgetRequest;
import com.transdignity.driver.models.auth.ForgetResponse;
import com.transdignity.driver.models.auth.LoginRequest;
import com.transdignity.driver.models.auth.LoginResponse;
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

import static com.transdignity.driver.utilities.MyConstants.emailPattern;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityLoginBinding binding;
    String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.llSigninContainer.setVisibility(View.VISIBLE);
        binding.llForgotContainer.setVisibility(View.GONE);
        binding.llLogin.setOnClickListener(this);
        binding.tvForgot.setOnClickListener(this);
        binding.llSend.setOnClickListener(this);
        binding.tvNewAccount.setOnClickListener(this);

        // CommonUtils.setInterpolation(this,binding.llLogin);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.ll_login:
                loginValidation();
                /*CommonUtils.switchActivity(this, MainActivity.class);
                finish();*/
                break;
            case R.id.tv_forgot:
                binding.llSigninContainer.setVisibility(View.GONE);
                binding.llForgotContainer.setVisibility(View.VISIBLE);
                break;
            case R.id.ll_send:
                if (CommonUtils.isNetwork(getApplicationContext())) {
                    callForgetPassword();
                }else {
                    Toast.makeText(this, "Please check internet connection!", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.tv_new_account:
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
                //This is for left to right animation:
                this.overridePendingTransition(R.anim.animation_enter,
                        R.anim.animation_leave);
                //This is for right to left animation:
                /*this.overridePendingTransition(R.anim.animation_leave,
                        R.anim.animation_enter);*/
                break;
        }
    }

    void loginValidation() {
        if (TextUtils.isEmpty(binding.etEmail.getText().toString().trim())) {
            binding.etEmail.setError("Please enter email address.");
            binding.etEmail.requestFocus();
        } else if (!binding.etEmail.getText().toString().matches(emailPattern)) {
            binding.etEmail.setError("Please enter valid email address.");
            binding.etEmail.requestFocus();
        } else if (TextUtils.isEmpty(binding.etPassword.getText().toString().trim())) {
            binding.etPassword.setError("Please enter password.");
            binding.etPassword.requestFocus();
        } else {
            if (CommonUtils.isNetwork(getApplicationContext())) {
                callLoginApi();
            } else {
                Toast.makeText(this, "Please check internet connection!", Toast.LENGTH_LONG).show();
            }
        }
    }

    void callLoginApi() {
        final LoadingProgressDialog dialog = new LoadingProgressDialog(this, "Please wait...");
        dialog.setCancelable(false);
        dialog.show();

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setDevice("Android");
        loginRequest.setDeviceId(CommonUtils.getDeviceId(this));
        Log.e(TAG, "callLoginApi: FCM " + GlobalValues.getPreferenceManager().getFcmString(PreferenceManager.FCMKEY));
        loginRequest.setFcmKey(GlobalValues.getPreferenceManager().getFcmString(PreferenceManager.FCMKEY));

        //loginRequest.setFcmKey("skdjfsklfhsfg");
        loginRequest.setUserGroupId(MyConstants.GROUP_ID);
        loginRequest.setEmail(binding.etEmail.getText().toString().trim());
        loginRequest.setPassword(binding.etPassword.getText().toString().trim());

        ApiInterface apiInterface = ApiClients.getConnection(this);
        Call<LoginResponse> call = apiInterface.loginApi(loginRequest);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                try {
                    dialog.dismiss();
                    if (response.code() >= 200 && response.code() <= 210) {
                        if (response.body().getSuccess().equalsIgnoreCase("true")) {
                            GlobalValues.getPreferenceManager().setObject(PreferenceManager.LoginResponseKey, new Gson().toJson(response.body()));
                            GlobalValues.getPreferenceManager().setloginPref(true);
                            // GlobalValues.getPreferenceManager().setBoolean(PreferenceManager.ISONLINE, false);

                            Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                            CommonUtils.switchActivity(LoginActivity.this, MainActivity.class);
                            finish();
                        } else {
                            //GlobalValues.getPreferenceManager().setloginPref(false);
                            Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                dialog.dismiss();
                GlobalValues.getPreferenceManager().setloginPref(false);
                Log.e(TAG, "onResponse: " + t.getMessage());
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    void callForgetPassword() {
        if (TextUtils.isEmpty(binding.etEmailForget.getText().toString().trim())) {
            binding.etEmailForget.setError("Please enter email address.");
            binding.etEmailForget.requestFocus();
        } else if (!binding.etEmailForget.getText().toString().matches(emailPattern)) {
            binding.etEmailForget.setError("Please enter valid email address.");
            binding.etEmailForget.requestFocus();
        } else {
            final LoadingProgressDialog dialog = new LoadingProgressDialog(this, "Please wait...");
            dialog.setCancelable(false);
            dialog.show();
            ForgetRequest forgetRequest = new ForgetRequest(binding.etEmailForget.getText().toString().trim(), MyConstants.GROUP_ID);
            ApiInterface apiInterface = ApiClients.getConnection(this);
            Call<ForgetResponse> call = apiInterface.forgetApi(forgetRequest);
            call.enqueue(new Callback<ForgetResponse>() {
                @Override
                public void onResponse(Call<ForgetResponse> call, Response<ForgetResponse> response) {
                    try {
                        dialog.dismiss();
                        if (response.code() >= 200 && response.code() <= 210) {
                            if (response.body().getSuccess().equalsIgnoreCase("true")) {
                                successDialog(binding, response.body().getMessage(), LoginActivity.this);
                            } else {
                                Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (Exception e) {
                        dialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<ForgetResponse> call, Throwable t) {
                    dialog.dismiss();
                }
            });

        }
    }

    public static boolean successDialog(final ActivityLoginBinding binding, String succesMsg, final Activity activity) {

        LayoutInflater inflater = LayoutInflater.from(activity);
        View viewCustom = inflater.inflate(R.layout.forget_verifylink_sent_dialog, null);
        final AlertDialog dialog = new AlertDialog.Builder(activity).create();
        dialog.setView(viewCustom);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView succesText = viewCustom.findViewById(R.id.msg);
        succesText.setText(succesMsg);
        LinearLayout done = viewCustom.findViewById(R.id.ok_forgot);
        dialog.setCancelable(false);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                binding.llSigninContainer.setVisibility(View.VISIBLE);
                binding.llForgotContainer.setVisibility(View.GONE);
                //activity.overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
            }
        });
        dialog.show();
        return true;
    }

    @Override
    public void onBackPressed() {

       /* if (binding.llForgotContainer.isShown()) {
            binding.llSigninContainer.setVisibility(View.VISIBLE);
            binding.llForgotContainer.setVisibility(View.GONE);
        } else {
            Toast.makeText(this, "Back press", Toast.LENGTH_LONG).show();

            super.onBackPressed();
        }*/
    }
}
