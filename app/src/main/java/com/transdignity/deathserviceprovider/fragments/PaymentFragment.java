package com.transdignity.deathserviceprovider.fragments;

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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.transdignity.deathserviceprovider.MainActivity;
import com.transdignity.deathserviceprovider.R;
import com.transdignity.deathserviceprovider.adapters.EarningListAdapter;
import com.transdignity.deathserviceprovider.adapters.PaymentAdapter;
import com.transdignity.deathserviceprovider.models.auth.LoginResponse;
import com.transdignity.deathserviceprovider.models.payment.PaymentHistoryModel;
import com.transdignity.deathserviceprovider.models.payment.PaymentHistoryResponseModel;
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
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class PaymentFragment extends Fragment {
    Context context;
    Activity activity;
    TextView tvToolbar;
    LoginResponse loginResponse;
    String userId,user_group_id;
    List<PaymentHistoryResponseModel.Datum> dataItems = new ArrayList<>();
    EarningListAdapter earningListAdapter;
    LinearLayoutManager linearLayoutManager;
    TextView tvBack;
    SwipeRefreshLayout swiprefresh;
    RecyclerView earning_recycler;
    CardView empty_layout;
    Boolean loading = false;
    Integer page = 1;

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

        getPaymentListApi(page);
        pagination();
        swiprefresh();
        if (userId != null) {
            linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            earningListAdapter = new EarningListAdapter(getActivity(),dataItems);
            earning_recycler.setLayoutManager(linearLayoutManager);
            earning_recycler.setAdapter(earningListAdapter);
        }
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment;
                fragment = new HomeFragment();
                String fragmtStatusTag = fragment.getClass().getName();
                ((MainActivity)getActivity()).selectedNavItem(R.id.navigation_home);
                CommonUtils.loadFragment(context, fragment, fragmtStatusTag);

            }
        });
    }

    void initView(View view) {
        tvBack = view.findViewById(R.id.tv_back);
        earning_recycler = view.findViewById(R.id.earning_recycler);
        empty_layout = view.findViewById(R.id.empty_layout);
        swiprefresh = view.findViewById(R.id.swiprefresh);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    void swiprefresh() {
        swiprefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                dataItems.clear();
                //requestListApi(userId, startDate, endDate, status, "" + 0);
                //  coutNotification();
                getPaymentListApi(1) ;
                swiprefresh.setRefreshing(false);

            }
        });
    }

    void pagination() {
        earning_recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) //check for scroll down
                {
                    int visibleItemCount = linearLayoutManager.getChildCount();
                    int totalItemCount = linearLayoutManager.getItemCount();
                    int pastVisiblesItems = linearLayoutManager.findFirstVisibleItemPosition();

                    if (loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            loading = false;
                            //   Log.v("...", "Last Item Wow !");
                            // Log.v("...", "Last Item Wow !");
                            //Do pagination.. i.e. fetch new data
                            page = page + 1;
                            getPaymentListApi(page) ;
                        }
                    }
                }
            }
        });
    }

    void getPaymentListApi(int page) {
        dataItems.clear();
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading.....");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(true);
        progressDialog.show();


        ApiInterface apiInterface = ApiClients.getClient(getActivity()).create(ApiInterface.class);
        Call<PaymentHistoryResponseModel> call = null;

        call = apiInterface.paymentHistory(loginResponse.getData().getToken(),user_group_id,userId,page);

        call.enqueue(new Callback<PaymentHistoryResponseModel>() {
            @Override
            public void onResponse(Call<PaymentHistoryResponseModel> call, Response<PaymentHistoryResponseModel> response) {
                try {
                    progressDialog.dismiss();
                    if (response.code() >= 200 && response.code() <= 210) {

                        if (response.body().getData().size() > 0) {
                            earning_recycler.setVisibility(View.VISIBLE);
                            empty_layout.setVisibility(View.GONE);
                            dataItems.addAll(response.body().getData());
                            earningListAdapter.notifyDataSetChanged();



                        } else {
                            earning_recycler.setVisibility(View.GONE);
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
            public void onFailure(Call<PaymentHistoryResponseModel> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();


            }
        });

    }


}
