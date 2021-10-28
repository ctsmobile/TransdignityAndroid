package com.cts.removalspecialist.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;

import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cts.removalspecialist.MainActivity;
import com.cts.removalspecialist.adapters.EarningListAdapter;
import com.cts.removalspecialist.models.auth.LoginResponse;
import com.cts.removalspecialist.models.payment.EarningListModel;
import com.cts.removalspecialist.network.ApiClients;
import com.cts.removalspecialist.network.ApiInterface;
import com.cts.removalspecialist.utilities.CommonUtils;
import com.cts.removalspecialist.R;
import com.cts.removalspecialist.utilities.GlobalValues;
import com.cts.removalspecialist.utilities.PreferenceManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class PaymentFragment extends Fragment {
    Context context;
    Activity activity;
    TextView tvBack;
    RecyclerView recyclerView;
    LoginResponse loginResponse;
    String userId,user_group_id;
    List<EarningListModel.Datum> dataItems = new ArrayList<>();
    EarningListAdapter earningListAdapter;
    LinearLayoutManager linearLayoutManager;
    CardView empty_layout;
    public PaymentFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static PaymentFragment newInstance(String param1, String param2) {
        PaymentFragment fragment = new PaymentFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        View view = DataBindingUtil.inflate(inflater, R.layout.fragment_payment, container, false).getRoot();
        initView(view);
        loginResponse = (LoginResponse) GlobalValues.getPreferenceManager().getObject(PreferenceManager.LoginResponseKey, LoginResponse.class);
        if (loginResponse != null) {
            userId = loginResponse.getData().getId();
            user_group_id = loginResponse.getData().getUserGroupId();
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

       /* recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new PaymentAdapter(context, activity));*/
        //((MainActivity)getActivity()).selectedNavItem(R.id.navigation_payment);
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment;
                fragment = new HomeFragment();
                String fragmtStatusTag = fragment.getClass().getName();
                GlobalValues.getInstance().setFramgentTag(fragmtStatusTag);
                ((MainActivity)getActivity()).selectedNavItem(R.id.navigation_home);
                CommonUtils.loadFragment(context, fragment, fragmtStatusTag);

            }
        });
        getPaymentListApi();
        if (userId != null) {
            linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            earningListAdapter = new EarningListAdapter(getActivity(),dataItems);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(earningListAdapter);
        }
    }

    void initView(View view) {
        tvBack = view.findViewById(R.id.tv_back);
        recyclerView = view.findViewById(R.id.recycle_payment);
        empty_layout = view.findViewById(R.id.empty_layout);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
    void getPaymentListApi() {
        dataItems.clear();
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading.....");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(true);
        progressDialog.show();


        ApiInterface apiInterface = ApiClients.getClient(getActivity()).create(ApiInterface.class);
        Call<EarningListModel> call = null;

        call = apiInterface.paymentHistory(loginResponse.getData().getToken(),user_group_id,userId);

        call.enqueue(new Callback<EarningListModel>() {
            @Override
            public void onResponse(Call<EarningListModel> call, Response<EarningListModel> response) {
                try {
                    progressDialog.dismiss();
                    if (response.code() >= 200 && response.code() <= 210) {

                        if (response.body().getData().size() > 0) {
                            recyclerView.setVisibility(View.VISIBLE);
                            empty_layout.setVisibility(View.GONE);
                            dataItems.addAll(response.body().getData());
                            earningListAdapter.notifyDataSetChanged();



                        } else {
                            recyclerView.setVisibility(View.GONE);
                            empty_layout.setVisibility(View.VISIBLE);

                        }
                        if (response.body().getSuccess().equals("true")) {
                            Log.e(TAG, "onResponse: list size>>>>>" + response.body().getData().size());

                            /*if (dataItems.size() > 0) {
                                event_recycler_view.setVisibility(View.VISIBLE);
                               // binding.emptyLayout.setVisibility(View.GONE);
                            } else {
                                event_recycler_view.setVisibility(View.GONE);
                                //binding.emptyLayout.setVisibility(View.VISIBLE);
                            }*/
                        } else {
                            // event_recycler_view.setVisibility(View.GONE);
                            //binding.emptyLayout.setVisibility(View.VISIBLE);
                            //Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    } else {
                        //GlobalValues.getPreferenceManager().setloginPref(false);
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
                            JSONObject jsonArray = jsonObjectError.getJSONObject("data");
                            String error =jsonArray.optString("error");


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<EarningListModel> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();


            }
        });

    }

}
