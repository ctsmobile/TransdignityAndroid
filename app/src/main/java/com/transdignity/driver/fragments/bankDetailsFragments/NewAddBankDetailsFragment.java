package com.transdignity.driver.fragments.bankDetailsFragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

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
import com.google.gson.JsonObject;
import com.transdignity.driver.MainActivity;
import com.transdignity.driver.R;
import com.transdignity.driver.fragments.SettingsFragment;
import com.transdignity.driver.models.auth.LoginResponse;
import com.transdignity.driver.models.bankDetail.AddAccountDetailRequestModel;
import com.transdignity.driver.models.bankDetail.AddAccountDetailResponseModel;
import com.transdignity.driver.models.bankDetail.NewAddBankDetailModel;
import com.transdignity.driver.models.bankDetail.SendClientAddBankMerchantResponseResponseModel;
import com.transdignity.driver.models.bankDetail.SendClintAddBankMerchantResponseRequestModel;
import com.transdignity.driver.network.ApiClients;
import com.transdignity.driver.network.ApiInterface;
import com.transdignity.driver.network.Url;
import com.transdignity.driver.network.VolleySingleton;
import com.transdignity.driver.network.paymentApiClients;
import com.transdignity.driver.utilities.CommonUtils;
import com.transdignity.driver.utilities.GlobalValues;
import com.transdignity.driver.utilities.LoadingProgressDialog;
import com.transdignity.driver.utilities.PreferenceManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
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
    TextView ll_submit,tv_back;
    String userId;
    String userGroupId;
    LoginResponse loginResponse;

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
        tv_back = view.findViewById(R.id.tv_back);
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment;
                fragment = new SettingsFragment();
                String fragmtStatusTag = fragment.getClass().getName();
                GlobalValues.getInstance().setFramgentTag(fragmtStatusTag);
                ((MainActivity) activity).selectedNavItem(R.id.navigation_home);
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

    public void addMarchentApi(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url.CITY,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);



                            Toast.makeText(getActivity(), "response"+obj.getString("merchantNumber"), Toast.LENGTH_SHORT).show();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(getActivity(), "error"+error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                params.put("api-action", "register-merchant");
                return params;
            }
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> fieldsMap = new HashMap<String, String>();
                // params.put("make", brandData);
                fieldsMap.put("accountroutingnumber", ed_account_routing_number.getText().toString());
                fieldsMap.put("accountnumber", ed_account_number.getText().toString());
                fieldsMap.put("merchantNumber", ed_merchant_number.getText().toString());
                fieldsMap.put("merchantName", ed_merchant_name.getText().toString());
                fieldsMap.put("merchantAddress", ed_merchant_address.getText().toString());
                fieldsMap.put("merchantzipNumber", ed_merchant_zip_number.getText().toString());
                fieldsMap.put("merchantCity", ed_merchant_city.getText().toString());
                fieldsMap.put("merchantState", ed_merchant_state.getText().toString());
                fieldsMap.put("merchantcontactNumber", ed_merchant_contact_number.getText().toString());
                fieldsMap.put("merchantEmail", ed_merchant_email.getText().toString());
                return fieldsMap;
            }
        };
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
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
                            GlobalValues.getInstance().setFramgentTag(fragmtStatusTag);
                            ((MainActivity) activity).selectedNavItem(R.id.navigation_home);
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