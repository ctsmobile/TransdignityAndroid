package com.transdignity.driver.fragments;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;


import com.transdignity.driver.MainActivity;
import com.transdignity.driver.R;
import com.transdignity.driver.databinding.FragmentChangePasswordBinding;
import com.transdignity.driver.models.auth.ChangePasswordReq;
import com.transdignity.driver.models.auth.ChangePasswordResponse;
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


public class ChangePasswordFragment extends Fragment implements View.OnClickListener {
    Activity activity;
    Context context;
    FragmentChangePasswordBinding binding;
    String userId = "";
    String android_id;
    LoginResponse loginResponse;
    TextView tvToolbar,tvBack;
    // TODO: Rename and change types and number of parameters
    public static ChangePasswordFragment newInstance(String param1, String param2) {
        ChangePasswordFragment fragment = new ChangePasswordFragment();
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_change_password, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvToolbar = activity.findViewById(R.id.tv_toolbar);
        tvToolbar.setText(R.string.change_password);
        tvBack = activity.findViewById(R.id.tv_back);
        tvBack.setVisibility(View.VISIBLE);
        tvBack.setOnClickListener(this);
        binding.llChange.setOnClickListener(this);
        android_id = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
         loginResponse = (LoginResponse) GlobalValues.getPreferenceManager().getObject(PreferenceManager.LoginResponseKey, LoginResponse.class);
        userId = loginResponse.getData().getId();
       /* if (userId != null) {
            // requestListApi(userId);
        }*/
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tv_back:
                Fragment fragment;
                fragment = new SettingsFragment();
                String fragmtStatusTag = fragment.getClass().getName();
                ((MainActivity) getActivity()).selectedNavItem(R.id.navigaton_account);
                CommonUtils.loadFragment(context, fragment, fragmtStatusTag);
                break;
            case R.id.ll_change:
                if (userId != null)
                    validation();
                break;
        }
    }

    void validation() {
        if (TextUtils.isEmpty(binding.etCurrentPass.getText().toString().trim())) {
            binding.etCurrentPass.setError("Please Current Password");
            binding.etCurrentPass.requestFocus();
        } else if ((binding.etCurrentPass.getText().toString().trim().length()) < 5) {
            binding.etCurrentPass.setError("Password Should be min 5");
            binding.etCurrentPass.requestFocus();
        } else if (TextUtils.isEmpty(binding.etNewPass.getText().toString().trim())) {
            binding.etNewPass.setError("Please Current Password");
            binding.etNewPass.requestFocus();
        } else if ((binding.etNewPass.getText().toString().trim().length()) < 5) {
            binding.etNewPass.setError("Password Should be min 5");
            binding.etNewPass.requestFocus();
        } else {
            callChangeApi();
        }
    }

    void callChangeApi() {
        final LoadingProgressDialog dialog = new LoadingProgressDialog(context, "Please wait...");
        dialog.setCancelable(false);
        dialog.show();
        final ChangePasswordReq req = new ChangePasswordReq();
        req.setUserId(userId);
        req.setFcmKey(GlobalValues.getPreferenceManager().getFcmString(PreferenceManager.FCMKEY));
        req.setDeviceId(android_id);
        req.setDevice(MyConstants.DEVICE);
        req.setUserGroupId(MyConstants.GROUP_ID);
        req.setOldPassword(binding.etCurrentPass.getText().toString().trim());
        req.setpassword(binding.etNewPass.getText().toString().trim());
        ApiInterface apiInterface = ApiClients.getConnection(context);
        Call<ChangePasswordResponse> call = apiInterface.changePasswordApi(loginResponse.getData().getToken(),req);
        call.enqueue(new Callback<ChangePasswordResponse>() {
            @Override
            public void onResponse(Call<ChangePasswordResponse> call, Response<ChangePasswordResponse> response) {
                try {
                    dialog.dismiss();
                    if (response.code() >= 200 && response.code() <= 210) {
                        if (response.body().getSuccess().equalsIgnoreCase("true")) {
                            String msg = response.body().getMessage();
                            successDialog(msg, activity);
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

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ChangePasswordResponse> call, Throwable t) {
                dialog.dismiss();
            }
        });
    }

    public void successDialog(String succesMsg, final Activity activity) {

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
                Fragment fragment;
                fragment = new SettingsFragment();
                String fragmtStatusTag = fragment.getClass().getName();
                ((MainActivity) getActivity()).selectedNavItem(R.id.navigaton_account);
                CommonUtils.loadFragment(context, fragment, fragmtStatusTag);
            }
        });
        dialog.show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        tvBack.setVisibility(View.GONE);
    }
}
