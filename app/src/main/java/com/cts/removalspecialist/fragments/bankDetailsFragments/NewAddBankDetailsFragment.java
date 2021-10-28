package com.cts.removalspecialist.fragments.bankDetailsFragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.cts.removalspecialist.MainActivity;
import com.cts.removalspecialist.R;
import com.cts.removalspecialist.fragments.SettingsFragment;
import com.cts.removalspecialist.models.auth.LoginResponse;
import com.cts.removalspecialist.models.bankDetail.NewAddBankDetailModel;
import com.cts.removalspecialist.models.bankDetail.SendClientAddBankMerchantResponseResponseModel;
import com.cts.removalspecialist.models.bankDetail.SendClintAddBankMerchantResponseRequestModel;
import com.cts.removalspecialist.network.ApiClients;
import com.cts.removalspecialist.network.ApiInterface;
import com.cts.removalspecialist.network.paymentApiClients;
import com.cts.removalspecialist.utilities.CommonUtils;
import com.cts.removalspecialist.utilities.GlobalValues;
import com.cts.removalspecialist.utilities.LoadingProgressDialog;
import com.cts.removalspecialist.utilities.PreferenceManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NewAddBankDetailsFragment extends Fragment {
    View view;
    Context context;
    Activity activity;
    EditText ed_account_routing_number,ed_account_number,ed_merchant_number,ed_merchant_name,
            ed_merchant_address,ed_merchant_zip_number,ed_merchant_city,ed_merchant_state,
            ed_merchant_contact_number,ed_merchant_email;
    TextView ll_submit;
    String userId;
    String userGroupId;
    LoginResponse loginResponse;
    TextView tv_back;

    public NewAddBankDetailsFragment() {
        // Required empty public constructor
    }


    public static NewAddBankDetailsFragment newInstance(String param1, String param2) {
        NewAddBankDetailsFragment fragment = new NewAddBankDetailsFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
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
        view= inflater.inflate(R.layout.fragment_new_add_bank_details, container, false);
        loginResponse = (LoginResponse) GlobalValues.getPreferenceManager().getObject(PreferenceManager.LoginResponseKey, LoginResponse.class);

        if (loginResponse != null) {
            userId = loginResponse.getData().getId();
            userGroupId = loginResponse.getData().getUserGroupId();
        }
        init();
        return view;
    }
    public void init(){
        tv_back = view.findViewById(R.id.tv_back);
        ed_account_routing_number = view.findViewById(R.id.ed_account_routing_number);
        ed_account_number = view.findViewById(R.id.ed_account_number);
        ed_merchant_number = view.findViewById(R.id.ed_merchant_number);
        ed_merchant_name = view.findViewById(R.id.ed_merchant_name);
        ed_merchant_address = view.findViewById(R.id.ed_merchant_address);
        ed_merchant_zip_number = view.findViewById(R.id.ed_merchant_zip_number);
        ed_merchant_city = view.findViewById(R.id.ed_merchant_city);
        ed_merchant_state = view.findViewById(R.id.ed_merchant_state);
        ed_merchant_contact_number = view.findViewById(R.id.ed_merchant_contact_number);
        ed_merchant_email = view.findViewById(R.id.ed_merchant_email);
        ll_submit = view.findViewById(R.id.ll_submit);
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment;
                fragment = new SettingsFragment();
                String fragmtStatusTag = fragment.getClass().getName();
                ((MainActivity) getActivity()).selectedNavItem(R.id.navigaton_settings);
                CommonUtils.loadFragment(context, fragment, fragmtStatusTag);

            }
        });
        ll_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerApi();
                // addMarchentApi();
            }
        });
    }
    void registerApi() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading.....");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();
       /* GetQrRequestModel request = new GetQrRequestModel();
        request.setEmail(email);*/
        Map<String, String> fieldsMap = new HashMap<>();
        fieldsMap.put("accountroutingnumber", ed_account_routing_number.getText().toString());
        fieldsMap.put("accountnumber", ed_account_number.getText().toString());
        fieldsMap.put("merchantNumber", userId);
        fieldsMap.put("merchantName", ed_merchant_name.getText().toString());
        fieldsMap.put("merchantAddress", ed_merchant_address.getText().toString());
        fieldsMap.put("merchantzipNumber", ed_merchant_zip_number.getText().toString());
        fieldsMap.put("merchantCity", ed_merchant_city.getText().toString());
        fieldsMap.put("merchantState", ed_merchant_state.getText().toString());
        fieldsMap.put("merchantcontactNumber", ed_merchant_contact_number.getText().toString());
        fieldsMap.put("merchantEmail", ed_merchant_email.getText().toString());
        ApiInterface apiInterface = paymentApiClients.getClient(getActivity()).create(ApiInterface.class);
        Call<NewAddBankDetailModel> call = apiInterface.paymentApi(fieldsMap);
        call.enqueue(new Callback<NewAddBankDetailModel>() {
            @Override
            public void onResponse(Call<NewAddBankDetailModel> call, Response<NewAddBankDetailModel> response) {
                try {
                    progressDialog.dismiss();
                    if (response.code() >= 200 && response.code() <= 210) {
                        String status = response.body().getStatus().toString();
                        if(status.equals("true")){
                           // Toast.makeText(getActivity(), "Bank Details Add Successfully", Toast.LENGTH_SHORT).show();
                            addBankDetailApi(response.body().getMerchantNumber().toString());

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
                            Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<NewAddBankDetailModel> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(),"failed"+t.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void addBankDetailApi(String merchant_number) {
        final LoadingProgressDialog dialog = new LoadingProgressDialog(context, "Please wait...");
        dialog.setCancelable(false);
        dialog.show();
        ApiInterface apiInterface = ApiClients.getClient(context).create(ApiInterface.class);
        SendClintAddBankMerchantResponseRequestModel req = new SendClintAddBankMerchantResponseRequestModel();

        req.setUserId(userId);
        req.setUserGroupId(userGroupId);
        req.setRefnumber("");
        req.setAccountroutingnumber(ed_account_routing_number.getText().toString());
        req.setAccountnumber(ed_account_number.getText().toString());
        req.setMerchantNumber(userId);
        req.setMerchantName(ed_merchant_name.getText().toString());
        req.setMerchantAddress(ed_merchant_address.getText().toString());
        req.setMerchantzipNumber(ed_merchant_zip_number.getText().toString());
        req.setMerchantCity(ed_merchant_city.getText().toString());
        req.setMerchantState(ed_merchant_state.getText().toString());
        req.setMerchantcontactNumber(ed_merchant_contact_number.getText().toString());
        req.setMerchantEmail(ed_merchant_email.getText().toString());
        req.setMerchantNumberGateway(merchant_number);



        Call<SendClientAddBankMerchantResponseResponseModel> call = apiInterface.sendMerchantAddBankResponse(loginResponse.getData().getToken(), req);

        call.enqueue(new Callback<SendClientAddBankMerchantResponseResponseModel>() {
            @Override
            public void onResponse(Call<SendClientAddBankMerchantResponseResponseModel> call, Response<SendClientAddBankMerchantResponseResponseModel> response) {
                dialog.dismiss();
                try {
                    if (response.code() >= 200 && response.code() <= 210) {
                        if (response.body().getSuccess().equalsIgnoreCase("true")) {
                            Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            Fragment fragment;
                            fragment = new SettingsFragment();
                            String fragmtStatusTag = fragment.getClass().getName();
                            ((MainActivity) getActivity()).selectedNavItem(R.id.navigaton_settings);
                            CommonUtils.loadFragment(context, fragment, fragmtStatusTag);


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
            public void onFailure(Call<SendClientAddBankMerchantResponseResponseModel> call, Throwable t) {
                Toast.makeText(context, "Something Went Wrong!", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });

    }


}