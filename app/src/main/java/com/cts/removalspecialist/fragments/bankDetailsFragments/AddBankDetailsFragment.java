package com.cts.removalspecialist.fragments.bankDetailsFragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.cts.removalspecialist.MainActivity;
import com.cts.removalspecialist.R;
import com.cts.removalspecialist.fragments.SettingsFragment;
import com.cts.removalspecialist.models.auth.LoginResponse;
import com.cts.removalspecialist.models.bankDetail.AddAccountDetailRequestModel;
import com.cts.removalspecialist.models.bankDetail.AddAccountDetailResponseModel;
import com.cts.removalspecialist.network.ApiClients;
import com.cts.removalspecialist.network.ApiInterface;
import com.cts.removalspecialist.utilities.CommonUtils;
import com.cts.removalspecialist.utilities.GlobalValues;
import com.cts.removalspecialist.utilities.LoadingProgressDialog;
import com.cts.removalspecialist.utilities.PreferenceManager;
import com.google.android.material.snackbar.Snackbar;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddBankDetailsFragment extends Fragment implements View.OnClickListener{
    View view;
    Spinner spinner_trip_type;
    public String[] triptypeArray;
    public String strTripType;
    Boolean isViewMode = false;
    String userId;
    String userGroupId;
    Context context;
    Activity activity;
    TextView tv_back;

    LoginResponse loginResponse;
    EditText ed_account_no,ed_confirm_account_no,ed_ifsc_code,ed_account_holder,ed_phone_no;
    TextView ll_submit,ll_cancel,ll_retry;
    public AddBankDetailsFragment() {
        // Required empty public constructor
    }


    public static AddBankDetailsFragment newInstance(String param1, String param2) {
        AddBankDetailsFragment fragment = new AddBankDetailsFragment();

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
        view= inflater.inflate(R.layout.fragment_add_bank_details, container, false);
        loginResponse = (LoginResponse) GlobalValues.getPreferenceManager().getObject(PreferenceManager.LoginResponseKey, LoginResponse.class);

        if (loginResponse != null) {
            userId = loginResponse.getData().getId();
            userGroupId = loginResponse.getData().getUserGroupId();
        }
        init();
        tripType();
        return view;
    }

    public void init(){
        tv_back=view.findViewById(R.id.tv_back);
        ed_account_no=view.findViewById(R.id.ed_account_no);
        ed_confirm_account_no=view.findViewById(R.id.ed_confirm_account_no);
        ed_ifsc_code=view.findViewById(R.id.ed_ifsc_code);
        ed_account_holder=view.findViewById(R.id.ed_account_holder);
        ed_phone_no=view.findViewById(R.id.ed_phone_no);
        ll_submit=view.findViewById(R.id.ll_submit);
        ll_cancel=view.findViewById(R.id.ll_cancel);
        spinner_trip_type=view.findViewById(R.id.spinner_trip_type);

        click();
    }
    public void click(){
        tv_back.setOnClickListener(this);
        ll_submit.setOnClickListener(this);
        ll_cancel.setOnClickListener(this);
    }
    public void tripType(){
        triptypeArray = spinner_trip_type.getResources().getStringArray(R.array.Select_Bank);
        ArrayAdapter<String> adapterOwner = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, triptypeArray);
        spinner_trip_type.setAdapter(adapterOwner);
        spinner_trip_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                strTripType = spinner_trip_type.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_back:
                Fragment fragment;
                fragment = new SettingsFragment();
                String fragmtStatusTag = fragment.getClass().getName();
                GlobalValues.getInstance().setFramgentTag(fragmtStatusTag);
                ((MainActivity) activity).selectedNavItem(R.id.navigation_home);
                CommonUtils.loadFragment(context, fragment, fragmtStatusTag);

                break;
            case R.id.ll_submit:
                validation();
                break;
            case R.id.ll_cancel:
                Fragment fragment1;
                fragment1 = new SettingsFragment();
                String fragmtStatusTa = fragment1.getClass().getName();
                GlobalValues.getInstance().setFramgentTag(fragmtStatusTa);
                ((MainActivity) activity).selectedNavItem(R.id.navigation_home);
                CommonUtils.loadFragment(context, fragment1, fragmtStatusTa);
                break;
        }
    }

    public void validation(){
        if (spinner_trip_type.getSelectedItemPosition()==0){
            Snackbar.make(view.findViewById(R.id.llayout),"Please enter Bank", Snackbar.LENGTH_LONG).setAction("Action",null).show();
        }else if (TextUtils.isEmpty(ed_account_no.getText().toString())){
            Snackbar.make(view.findViewById(R.id.llayout),"Please enter Account No", Snackbar.LENGTH_LONG).setAction("Action",null).show();
        }else if (TextUtils.isEmpty(ed_confirm_account_no.getText().toString())){
            Snackbar.make(view.findViewById(R.id.llayout),"Please enter Confirm Account No", Snackbar.LENGTH_LONG).setAction("Action",null).show();
        }else if (TextUtils.isEmpty(ed_ifsc_code.getText().toString())){
            Snackbar.make(view.findViewById(R.id.llayout),"Please enter IFSC code", Snackbar.LENGTH_LONG).setAction("Action",null).show();
        }else if (TextUtils.isEmpty(ed_account_holder.getText().toString())){
            Snackbar.make(view.findViewById(R.id.llayout),"Please enter Account Holder Name", Snackbar.LENGTH_LONG).setAction("Action",null).show();
        }else {
            addBankDetailApi();
        }
    }

    public void addBankDetailApi() {
        final LoadingProgressDialog dialog = new LoadingProgressDialog(context, "Please wait...");
        dialog.setCancelable(false);
        dialog.show();
        ApiInterface apiInterface = ApiClients.getClient(context).create(ApiInterface.class);
        final AddAccountDetailRequestModel req = new AddAccountDetailRequestModel();
       /* req.setDevice("Android");
        req.setDeviceId(CommonUtils.getDeviceId(context));*/
        req.setUserId(Integer.parseInt(userId));
        req.setUserGroupId(Integer.parseInt(userGroupId));
        req.setAccountNumber(ed_account_no.getText().toString().trim());
        req.setIfscCode(ed_ifsc_code.getText().toString().trim());
        req.setMobileNumber(ed_phone_no.getText().toString().trim());
        req.setAccountHolderName(ed_account_holder.getText().toString().trim());
        req.setBankName(strTripType);



        Call<AddAccountDetailResponseModel> call = apiInterface.addAccountDetail(loginResponse.getData().getToken(), req);

        call.enqueue(new Callback<AddAccountDetailResponseModel>() {
            @Override
            public void onResponse(Call<AddAccountDetailResponseModel> call, Response<AddAccountDetailResponseModel> response) {
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
            public void onFailure(Call<AddAccountDetailResponseModel> call, Throwable t) {
                Toast.makeText(context, "Something Went Wrong!", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });

    }

}