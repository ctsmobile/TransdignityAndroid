package com.transdignity.deathserviceprovider.fragments.bankDetailsFragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.transdignity.deathserviceprovider.MainActivity;
import com.transdignity.deathserviceprovider.R;
import com.transdignity.deathserviceprovider.fragments.HomeFragment;
import com.transdignity.deathserviceprovider.models.auth.LoginResponse;
import com.transdignity.deathserviceprovider.models.bankDetail.ClientPaymentModel;
import com.transdignity.deathserviceprovider.models.bankDetail.SendClientPaymentResponseRequestModel;
import com.transdignity.deathserviceprovider.models.bankDetail.SendClientPaymentResponseResponseModel;
import com.transdignity.deathserviceprovider.network.ApiClients;
import com.transdignity.deathserviceprovider.network.ApiInterface;
import com.transdignity.deathserviceprovider.network.paymentApiClients;
import com.transdignity.deathserviceprovider.utilities.CommonUtils;
import com.transdignity.deathserviceprovider.utilities.GlobalValues;
import com.transdignity.deathserviceprovider.utilities.LoadingProgressDialog;
import com.transdignity.deathserviceprovider.utilities.PreferenceManager;

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


public class ClientPaymentIntegrationFragment extends Fragment {
     View view;
    Context context;
    Activity activity;
     EditText ed_amount,ed_account_number,ed_customer_address,
             ed_zip_code,ed_cvv_num,ed_exp_date,ed_payment_mode,
             ed_first_name,ed_lastname,ed_user_mail;
    TextView ll_submit;
    String userId;
    String userGroupId;
    LoginResponse loginResponse;
    String request_id,amount,terms;
    TextView tv_amount;
    TextView tv_back;
    public ClientPaymentIntegrationFragment() {
        // Required empty public constructor
    }


    public static ClientPaymentIntegrationFragment newInstance(String request_id,String amount,String terms) {
        ClientPaymentIntegrationFragment fragment = new ClientPaymentIntegrationFragment();
        Bundle args = new Bundle();
        fragment.request_id = request_id;
        fragment.amount = amount;
        fragment.terms = terms;

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
        view= inflater.inflate(R.layout.fragment_client_payment_integration, container, false);
        loginResponse = (LoginResponse) GlobalValues.getPreferenceManager().getObject(PreferenceManager.LoginResponseKey, LoginResponse.class);

        if (loginResponse != null) {
            userId = loginResponse.getData().getId();
            userGroupId = loginResponse.getData().getUserGroupId();
        }
        init();
        return view;
    }

    public void init(){
        tv_back=view.findViewById(R.id.tv_back);
        ed_amount=view.findViewById(R.id.ed_amount);
        ed_account_number=view.findViewById(R.id.ed_account_number);
        ed_customer_address=view.findViewById(R.id.ed_customer_address);
        ed_zip_code=view.findViewById(R.id.ed_zip_code);
        ed_cvv_num=view.findViewById(R.id.ed_cvv_num);
        ed_exp_date=view.findViewById(R.id.ed_exp_date);
        ed_payment_mode=view.findViewById(R.id.ed_payment_mode);
        ed_first_name=view.findViewById(R.id.ed_first_name);
        ed_lastname=view.findViewById(R.id.ed_lastname);
        ed_user_mail=view.findViewById(R.id.ed_user_mail);
        tv_amount=view.findViewById(R.id.tv_amount);
        ll_submit=view.findViewById(R.id.ll_submit);
        tv_amount.setText("Amount : "+amount);
        ll_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerApi();
                // addMarchentApi();
            }
        });
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment;
                fragment = new HomeFragment();
                String fragmtStatusTag = fragment.getClass().getName();
                GlobalValues.getInstance().setFramgentTag(fragmtStatusTag);
                ((MainActivity) activity).selectedNavItem(R.id.navigation_home);
                CommonUtils.loadFragment(context, fragment, fragmtStatusTag);
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
        fieldsMap.put("amount", amount);
        fieldsMap.put("accountnum", ed_account_number.getText().toString());
        fieldsMap.put("customeraddress", ed_customer_address.getText().toString());
        fieldsMap.put("zipcode", ed_zip_code.getText().toString());
        fieldsMap.put("cvvnum", ed_cvv_num.getText().toString());
        fieldsMap.put("expdate", ed_exp_date.getText().toString());
        fieldsMap.put("mode", ed_payment_mode.getText().toString());
        fieldsMap.put("firstname", ed_first_name.getText().toString());
        fieldsMap.put("lastname", ed_lastname.getText().toString());
        fieldsMap.put("usermail", ed_user_mail.getText().toString());
        ApiInterface apiInterface = paymentApiClients.getClient(getActivity()).create(ApiInterface.class);
        Call<ClientPaymentModel> call = apiInterface.paymentApi(fieldsMap);
        call.enqueue(new Callback<ClientPaymentModel>() {
            @Override
            public void onResponse(Call<ClientPaymentModel> call, Response<ClientPaymentModel> response) {
                try {
                    progressDialog.dismiss();
                    if (response.code() >= 200 && response.code() <= 210) {
                        String status = response.body().getStatus().toString();
                        if(status.equals("success")){
                            sendPaymentResponseApi(response.body().getRef().toString(),response.body().getStatus().toString());
                            //Toast.makeText(getActivity(), "payment Successfully", Toast.LENGTH_SHORT).show();
                           // addBankDetailApi(response.body().getMerchantNumber().toString());

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
            public void onFailure(Call<ClientPaymentModel> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(),"failed"+t.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void sendPaymentResponseApi(String transaction_id,String status) {
        final LoadingProgressDialog dialog = new LoadingProgressDialog(context, "Please wait...");
        dialog.setCancelable(false);
        dialog.show();
        ApiInterface apiInterface = ApiClients.getClient(context).create(ApiInterface.class);
        SendClientPaymentResponseRequestModel req = new SendClientPaymentResponseRequestModel();

        req.setUserId(userId);
        req.setUserGroupId(userGroupId);
        req.setRequestId(request_id);
        req.setAmount(amount);
        req.setPaymentStatus(status);
        req.setTransactionId(transaction_id);
        req.setGateway("cloudnine");
        req.setTerm(terms);



        Call<SendClientPaymentResponseResponseModel> call = apiInterface.sendMerchantPaymentResponse(loginResponse.getData().getToken(), req);

        call.enqueue(new Callback<SendClientPaymentResponseResponseModel>() {
            @Override
            public void onResponse(Call<SendClientPaymentResponseResponseModel> call, Response<SendClientPaymentResponseResponseModel> response) {
                dialog.dismiss();
                try {
                    if (response.code() >= 200 && response.code() <= 210) {
                        if (response.body().getSuccess().equalsIgnoreCase("true")) {
                            Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            showAlert();

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
                                //CommonUtils.logoutSession(activity);
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
            public void onFailure(Call<SendClientPaymentResponseResponseModel> call, Throwable t) {
                Toast.makeText(context, "Something Went Wrong!", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });

    }

    public void showAlert(){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.service_payment_complete_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));



        TextView bt_ok = dialog.findViewById(R.id.bt_ok);
        bt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment;
                fragment = new HomeFragment();
                String fragmtStatusTag = fragment.getClass().getName();
                GlobalValues.getInstance().setFramgentTag(fragmtStatusTag);
                ((MainActivity) activity).selectedNavItem(R.id.navigation_home);
                CommonUtils.loadFragment(context, fragment, fragmtStatusTag);
                dialog.setCancelable(true);
                dialog.dismiss();

            }
        });

        dialog.show();

    }

}