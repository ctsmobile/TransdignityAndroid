package com.transdignity.deathserviceprovider.fragments.bankDetailsFragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.transdignity.deathserviceprovider.MainActivity;
import com.transdignity.deathserviceprovider.R;
import com.transdignity.deathserviceprovider.fragments.SettingsFragment;
import com.transdignity.deathserviceprovider.models.auth.LoginResponse;
import com.transdignity.deathserviceprovider.models.bankDetail.ShowAccountDetailResponseModel;
import com.transdignity.deathserviceprovider.models.tracking.LimooTrackingModel;
import com.transdignity.deathserviceprovider.network.ApiClients;
import com.transdignity.deathserviceprovider.network.ApiInterface;
import com.transdignity.deathserviceprovider.utilities.CommonUtils;
import com.transdignity.deathserviceprovider.utilities.GlobalValues;
import com.transdignity.deathserviceprovider.utilities.PreferenceManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ShowBankDetailFragment extends Fragment implements View.OnClickListener {
    View view;
    Context context;
    Activity activity;
    LoginResponse loginResponse;
    String userId;
    TextView tv_acc_holder_name,tv_mobile_no,tv_bank_name,tv_acc_number,tv_ifsc_code,tv_date;
    TextView tv_back;
    public ShowBankDetailFragment() {
        // Required empty public constructor
    }

    public static ShowBankDetailFragment newInstance(String param1, String param2) {
        ShowBankDetailFragment fragment = new ShowBankDetailFragment();

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
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_select_bank, container, false);
        loginResponse = (LoginResponse) GlobalValues.getPreferenceManager().getObject(PreferenceManager.LoginResponseKey, LoginResponse.class);

        if (loginResponse != null) {
            userId = loginResponse.getData().getId();
        }
        init();
        trackStatus();
        return view;
    }

    public void init(){
        tv_back=view.findViewById(R.id.tv_back);

        tv_acc_holder_name=view.findViewById(R.id.tv_acc_holder_name);
        tv_mobile_no=view.findViewById(R.id.tv_mobile_no);
        tv_bank_name=view.findViewById(R.id.tv_bank_name);
        tv_acc_number=view.findViewById(R.id.tv_acc_number);
        tv_ifsc_code=view.findViewById(R.id.tv_ifsc_code);
        tv_date=view.findViewById(R.id.tv_date);
        click();
    }
    public void click(){
        tv_back.setOnClickListener(this);
    }

    void trackStatus() {
        ApiInterface apiInterface = ApiClients.getClient(context).create(ApiInterface.class);
        Call<ShowAccountDetailResponseModel> call = apiInterface.accountDetailApi(loginResponse.getData().getToken(), userId);
        call.enqueue(new Callback<ShowAccountDetailResponseModel>() {
            @Override
            public void onResponse(Call<ShowAccountDetailResponseModel> call, Response<ShowAccountDetailResponseModel> response) {
                try {

                    if (response.code() >= 200 && response.code() <= 210) {
                        if (response.body().getSuccess().equalsIgnoreCase("true")) {
                           tv_acc_holder_name.setText(response.body().getData().getAccountHolderName());
                           tv_mobile_no.setText(response.body().getData().getMobileNumber());
                           tv_bank_name.setText(response.body().getData().getBankName());
                           tv_acc_number.setText(response.body().getData().getAccountNumber());
                           tv_ifsc_code.setText(response.body().getData().getIfscCode());
                           tv_date.setText(response.body().getData().getDateAdded());
                        } else {
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

                }
            }

            @Override
            public void onFailure(Call<ShowAccountDetailResponseModel> call, Throwable t) {


            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_back:
                Fragment fragment;
                fragment = new SettingsFragment();
                String fragmtStatusTag = fragment.getClass().getName();
                GlobalValues.getInstance().setFramgentTag(fragmtStatusTag);
                ((MainActivity) activity).selectedNavItem(R.id.navigation_home);
                CommonUtils.loadFragment(context, fragment, fragmtStatusTag);

                break;
        }
    }
}