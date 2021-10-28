package com.transdignity.driver.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.transdignity.driver.MainActivity;
import com.transdignity.driver.R;
import com.transdignity.driver.databinding.FragmentContactUsBinding;
import com.transdignity.driver.models.auth.LoginResponse;
import com.transdignity.driver.models.contactus.ContactUsResponse;
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


public class ContactUsFragment extends Fragment implements View.OnClickListener {
    Context context;
    Activity activity;
    TextView tvBack, tvToolbar;
    LinearLayout llSend;
    FragmentContactUsBinding binding;
    String userID;
    LoginResponse loginResponse;

    public ContactUsFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ContactUsFragment newInstance(String param1, String param2) {
        ContactUsFragment fragment = new ContactUsFragment();

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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_contact_us, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //((MainActivity)getActivity()).selectedNavItem(R.id.navigation_contactus);
        initView(view);
        loginResponse = (LoginResponse) GlobalValues.getPreferenceManager().getObject(PreferenceManager.LoginResponseKey, LoginResponse.class);
        if (loginResponse != null) {
            userID = loginResponse.getData().getId();
        }
        tvBack.setOnClickListener(this);
        llSend.setOnClickListener(this);
    }

    void initView(View view) {
        tvToolbar = activity.findViewById(R.id.tv_toolbar);
        tvToolbar.setText(R.string.contact_us);
        tvBack = activity.findViewById(R.id.tv_back);
        tvBack.setVisibility(View.VISIBLE);
        llSend = view.findViewById(R.id.ll_send);
    }

    void contactUsApi() {
        if (TextUtils.isEmpty(binding.etSubject.getText().toString().trim())) {
            binding.etSubject.setError("Please Enter Subject");
            binding.etSubject.requestFocus();
            return;
        } else if (TextUtils.isEmpty(binding.etMessage.getText().toString().trim())) {
            binding.etMessage.setError("Please Enter Message");
            binding.etMessage.requestFocus();
            return;
        }

        final LoadingProgressDialog dialog = new LoadingProgressDialog(context, "Please wait...");
        dialog.setCancelable(false);
        dialog.show();

        ApiInterface apiInterface = ApiClients.getConnection(context);
        JsonObject jsonObject = new JsonObject();
        try {
            jsonObject.addProperty("device_id", CommonUtils.getDeviceId(context));
            jsonObject.addProperty("fcm_key", GlobalValues.getPreferenceManager().getFcmString(PreferenceManager.FCMKEY));
            jsonObject.addProperty("device", MyConstants.DEVICE);
            jsonObject.addProperty("subject", binding.etSubject.getText().toString().trim());
            jsonObject.addProperty("message", binding.etMessage.getText().toString().trim());
            jsonObject.addProperty("user_id", userID);
            jsonObject.addProperty("user_group_id", MyConstants.GROUP_ID);

        } catch (Exception e) {
            e.printStackTrace();
        }
        Call<ContactUsResponse> call = apiInterface.callContactUsApi(loginResponse.getData().getToken(), jsonObject);
        call.enqueue(new Callback<ContactUsResponse>() {
            @Override
            public void onResponse(Call<ContactUsResponse> call, Response<ContactUsResponse> response) {
                try {
                    dialog.dismiss();
                    if (response.code() >= 200 && response.code() <= 210) {
                        if (response.body().getSuccess().equalsIgnoreCase("true")) {
                            binding.etSubject.setText("");
                            binding.etMessage.setText("");
                            // Log.e(TAG, "onResponse: " + response.body().getMessage());
                            // Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            successDialog(response.body().getMessage(), context);

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
            public void onFailure(Call<ContactUsResponse> call, Throwable t) {
                dialog.dismiss();
                Log.e(TAG, "onResponse: " + t.getMessage());
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void successDialog(String succesMsg, final Context context) {

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
                dialog.dismiss();
                Fragment fragment = new SettingsFragment();
                String fragmtStatusTag = fragment.getClass().getName();
                ((MainActivity) context).selectedNavItem(R.id.navigaton_account);
                CommonUtils.loadFragment(context, fragment, fragmtStatusTag);
            }
        });
        dialog.show();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        tvBack.setVisibility(View.GONE);
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
            case R.id.ll_send:
                contactUsApi();
                break;
        }
    }
}
