package com.transdignity.driver.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.transdignity.driver.R;
import com.transdignity.driver.adapters.EarningListAdapter;
import com.transdignity.driver.databinding.FragmentEarningBinding;
import com.transdignity.driver.models.auth.LoginResponse;
import com.transdignity.driver.models.payment.EarningListModel;
import com.transdignity.driver.network.ApiClients;
import com.transdignity.driver.network.ApiInterface;
import com.transdignity.driver.utilities.GlobalValues;
import com.transdignity.driver.utilities.PreferenceManager;

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

public class EarningFragment extends Fragment {
   Context context;
   Activity activity;
   FragmentEarningBinding binding;
   TextView tvToolbar;
    LoginResponse loginResponse;
    String userId,user_group_id;
    List<EarningListModel.Datum> dataItems = new ArrayList<>();
    EarningListAdapter earningListAdapter;
    LinearLayoutManager linearLayoutManager;

    // TODO: Rename and change types and number of parameters
    public static EarningFragment newInstance() {
        EarningFragment fragment = new EarningFragment();

        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context= context;
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
         binding = DataBindingUtil.inflate(inflater,R.layout.fragment_earning, container, false);
        loginResponse = (LoginResponse) GlobalValues.getPreferenceManager().getObject(PreferenceManager.LoginResponseKey, LoginResponse.class);
        if (loginResponse != null) {
            userId = loginResponse.getData().getId();
            user_group_id = loginResponse.getData().getUserGroupId();
        }

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvToolbar = activity.findViewById(R.id.tv_toolbar);
        tvToolbar.setText("Earnings");
        setCalenderView();
       /* binding.earningRecycler.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        binding.earningRecycler.setAdapter(new EarningAdapter(context,activity));*/
        getPaymentListApi();
        if (userId != null) {
            //Toast.makeText(getActivity(), ""+userId, Toast.LENGTH_SHORT).show();
            linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            earningListAdapter = new EarningListAdapter(getActivity(),dataItems);
            binding.earningRecycler.setLayoutManager(linearLayoutManager);
            binding.earningRecycler.setAdapter(earningListAdapter);
        }
    }

void setCalenderView(){
        binding.cvCalender.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String msg = "Selected date is " + dayOfMonth + "/" + (month + 1) + "/" + year;
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
            }
        });
}
    void setCalender(){
    CalendarView calendarView = new CalendarView(context);
    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    layoutParams.setMargins(90, 40, 80, 40);
    calendarView.setLayoutParams(layoutParams);

    calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
        @Override
        public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
            //Note that months are indexed from 0. So, 0 means january, 1 means February, 2 means march etc.
            String msg = "Selected date is " + dayOfMonth + "/" + (month + 1) + "/" + year;
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        }
    });


    if (binding.llCalender != null) {
        binding.llCalender.addView(calendarView);
    }

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
                            dataItems.addAll(response.body().getData());
                            earningListAdapter.notifyDataSetChanged();



                        } else {


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
