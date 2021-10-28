package com.cts.removalspecialist.fragments;

import android.Manifest;
import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;

import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cts.removalspecialist.MainActivity;
import com.cts.removalspecialist.adapters.HomeAdapter;
import com.cts.removalspecialist.databinding.FragmentHomeBinding;
import com.cts.removalspecialist.models.auth.LoginResponse;
import com.cts.removalspecialist.models.requestList.RequestListResponse;
import com.cts.removalspecialist.network.ApiClients;
import com.cts.removalspecialist.network.ApiInterface;
import com.cts.removalspecialist.utilities.CommonUtils;
import com.cts.removalspecialist.utilities.GlobalValues;
import com.cts.removalspecialist.R;
import com.cts.removalspecialist.utilities.LoadingProgressDialog;
import com.cts.removalspecialist.utilities.MyConstants;
import com.cts.removalspecialist.utilities.PreferenceManager;
import com.google.gson.JsonObject;

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

public class HomeFragment extends Fragment implements View.OnClickListener, HomeAdapter.ChangeStatusPos {

    Context context;
    Activity activity;
    FragmentHomeBinding binding;
    String userId, startDate, endDate, status;
    HomeAdapter homeAdapter;
    LinearLayoutManager linearLayoutManager;
    Boolean loading = false, isFilter = false;
    Integer page = 0;
    List<RequestListResponse.DataItem> dataItems = new ArrayList<>();
    LoginResponse loginResponse;

    public HomeFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);

        //  ((MainActivity)getActivity()).selectedNavItem(R.id.navigation_home);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       /* binding.recycleHome.setLayoutManager(new LinearLayoutManager(context));
        binding.recycleHome.setAdapter(new HomeAdapter(context, activity));*/
        binding.tvFilter.setOnClickListener(this);
        binding.tvNotification.setOnClickListener(this);
        loginResponse = (LoginResponse) GlobalValues.getPreferenceManager().getObject(PreferenceManager.LoginResponseKey, LoginResponse.class);
        if (loginResponse != null)
            userId = loginResponse.getData().getId();
       // Toast.makeText(context, ""+userId, Toast.LENGTH_SHORT).show();
        if (userId != null) {
            linearLayoutManager = new LinearLayoutManager(context);
            homeAdapter = new HomeAdapter(dataItems, context, activity, this);
            binding.recycleHome.setLayoutManager(linearLayoutManager);
            binding.recycleHome.setAdapter(homeAdapter);
            requestListApi(userId, startDate, endDate, status, "" + 0);
            pagination();
            //  coutNotification();
            swiprefresh();
            onlineAvailabilityStatus();
            setOnlineOfflineStatus();
            switchCopact();
        }
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // takePictureButton.setEnabled(false);
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 0);
        }
    }

    void swiprefresh() {
        binding.swiprefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                dataItems.clear();
                isFilter = false;
                requestListApi(userId, startDate, endDate, status, "" + 0);
                //  coutNotification();
                binding.swiprefresh.setRefreshing(false);
            }
        });
    }

    void requestListApi(String userId, String startDate, String endDate, String status, String page) {
        final LoadingProgressDialog dialog = new LoadingProgressDialog(context, "Please wait...");
        dialog.setCancelable(false);
        dialog.show();
        ApiInterface apiInterface = ApiClients.getConnection(context);
        Call<RequestListResponse> call;
        if (isFilter) {
            call = apiInterface.filterRequestListApi(loginResponse.getData().getToken(), userId, startDate, endDate, status, page);
        } else {
            call = apiInterface.requestListApi(loginResponse.getData().getToken(), userId, page);
        }

        call.enqueue(new Callback<RequestListResponse>() {
            @Override
            public void onResponse(Call<RequestListResponse> call, Response<RequestListResponse> response) {
                dialog.dismiss();
                try {

                    if (response.code() >= 200 && response.code() <= 210) {
                        if (response.body().getSuccess().equalsIgnoreCase("true")) {
                            Log.e(TAG, "onResponse: list size>>>>>" + response.body().getData().size());
                            if (response.body().getData().size() > 0) {
                                dataItems.addAll(response.body().getData());
                                homeAdapter.notifyDataSetChanged();
                                loading = true;
                            } else {
                                loading = false;
                            }
                            if (dataItems.size() > 0) {
                                binding.recycleHome.setVisibility(View.VISIBLE);
                                binding.emptyLayout.setVisibility(View.GONE);
                            } else {
                                binding.recycleHome.setVisibility(View.GONE);
                                binding.emptyLayout.setVisibility(View.VISIBLE);
                            }
                        } else {
                            binding.recycleHome.setVisibility(View.GONE);
                            binding.emptyLayout.setVisibility(View.VISIBLE);
                            Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    } else {
                        binding.recycleHome.setVisibility(View.GONE);
                        binding.emptyLayout.setVisibility(View.VISIBLE);
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
                            //    Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
                            if (jsonObjectError.optString("token_valid").equalsIgnoreCase("false")) {
                              //  CommonUtils.logoutSession(activity);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                } catch (Exception e) {
                    binding.recycleHome.setVisibility(View.GONE);
                    binding.emptyLayout.setVisibility(View.VISIBLE);
                    Log.e(TAG, "onResponse: " + e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<RequestListResponse> call, Throwable t) {
                dialog.dismiss();
                binding.recycleHome.setVisibility(View.GONE);
                binding.emptyLayout.setVisibility(View.VISIBLE);
            }
        });
        isFilter = false;
    }

    void pagination() {
        binding.recycleHome.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                            requestListApi(userId, startDate, endDate, status, "" + page);
                        }
                    }
                }
            }
        });
    }

    void manageNotification() {
        MyConstants.isMainActivityPageOpen = true;
        if (MyConstants.isNewTask) {
            String requestId = GlobalValues.getPreferenceManager().getString(PreferenceManager.REQUESTID);
            //String pickuplocation = GlobalValues.getPreferenceManager().getString(PreferenceManager.PICKUPLOCATION);
            try {

                if (requestId != null) {
                    MyConstants.isNewTask = false;
                    homeAdapter.notifyDataSetChanged();
                    clearNotifications(getActivity());

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    void onlineAvailabilityStatus() {
        final LoadingProgressDialog dialog = new LoadingProgressDialog(context, "please Wait!");
        dialog.setCancelable(false);
        dialog.show();
        ApiInterface apiInterface = ApiClients.getConnection(context);
        Call<JsonObject> call = apiInterface.onlineAvailablityStatusApi(loginResponse.getData().getToken());
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                try {
                    dialog.dismiss();
                    if (response.code() >= 200 && response.code() <= 210) {
                        JSONObject jsonObj = new JSONObject(response.body().toString());
                        if (jsonObj.optString("success").equalsIgnoreCase("true")) {
                            if (jsonObj.getJSONObject("data") != null) {
                                String status = jsonObj.getJSONObject("data").optString("online");
                                if (status.equalsIgnoreCase("1")) {
                                    GlobalValues.getPreferenceManager().setBoolean(PreferenceManager.ISONLINE, true);
                                } else {
                                    GlobalValues.getPreferenceManager().setBoolean(PreferenceManager.ISONLINE, false);
                                }
                                setOnlineOfflineStatus();
                            }
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
                            //    Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
                            if (jsonObjectError.optString("token_valid").equalsIgnoreCase("false")) {
                               // CommonUtils.logoutSession(activity);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                dialog.dismiss();
            }
        });
    }

    void setOnlineOfflineStatus() {
        Boolean status = GlobalValues.getPreferenceManager().getBoolean(PreferenceManager.ISONLINE);
        if (status) {
            //tvOnlineStatus.setText("Online");
            binding.driverStatusSwitch.setChecked(true);
        } else {
            //  tvOnlineStatus.setText("Offline");
            binding.driverStatusSwitch.setChecked(false);
        }
    }

    void switchCopact() {
        binding.driverStatusSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean check) {
                if (check) {
                    updateOnlineStatus("1");
                } else {
                    updateOnlineStatus("0");
                }
            }
        });
    }

    void updateOnlineStatus(String status) {
        final LoadingProgressDialog dialog = new LoadingProgressDialog(context, "please Wait!");
        dialog.setCancelable(false);
        dialog.show();
        ApiInterface apiInterface = ApiClients.getConnection(context);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("user_id", userId);
        jsonObject.addProperty("user_group_id", MyConstants.GROUP_ID);
        jsonObject.addProperty("is_online", status);
        Call<JsonObject> call = apiInterface.updateOfflineStatusApi(loginResponse.getData().getToken(), jsonObject);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                try {
                    dialog.dismiss();
                    if (response.code() >= 200 && response.code() <= 210) {
                        JSONObject jsonObj = new JSONObject(response.body().toString());
                        if (jsonObj.optString("success").equalsIgnoreCase("true")) {
                            if (jsonObj.getJSONObject("data") != null) {
                                String status = jsonObj.getJSONObject("data").optString("online");
                                if (status.equalsIgnoreCase("1")) {
                                    GlobalValues.getPreferenceManager().setBoolean(PreferenceManager.ISONLINE, true);
                                } else {
                                    GlobalValues.getPreferenceManager().setBoolean(PreferenceManager.ISONLINE, false);
                                }
                                setOnlineOfflineStatus();
                            }
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
                              //  CommonUtils.logoutSession(activity);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        manageNotification();
    }

    @Override
    public void onStop() {
        super.onStop();
        //  MyConstants.isMainActivityPageOpen = false;
    }

    private void clearNotifications(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_filter:
                successDialog(getContext());
                break;
            case R.id.tv_notification:
                Fragment fragment;
                fragment = new NotificationFragment();
                String fragmtStatusTag = fragment.getClass().getName();
                GlobalValues.getInstance().setFramgentTag(fragmtStatusTag);
                ((MainActivity) getActivity()).selectedNavItem(R.id.navigation_home);
                CommonUtils.loadFragment(getActivity(), fragment, fragmtStatusTag);
                break;
        }
    }

    public void successDialog(final Context context) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View viewCustom = inflater.inflate(R.layout.home_filter_dialog, null);
        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.setView(viewCustom);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        LinearLayout apply = viewCustom.findViewById(R.id.ll_aaply);
        LinearLayout cancel = viewCustom.findViewById(R.id.ll_cancel);
        final TextView etstartDate = viewCustom.findViewById(R.id.et_start_date);
        final TextView etEndDate = viewCustom.findViewById(R.id.et_end_date);
        Spinner spinner = viewCustom.findViewById(R.id.sp_status);
        statusSpinner(spinner);
        dialog.setCancelable(false);
        etstartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonUtils.getDatepickerTextView(context, etstartDate);

            }
        });
        etEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonUtils.getDatepickerTextView(context, etEndDate);

            }
        });
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDate = etstartDate.getText().toString().trim();
                endDate = etEndDate.getText().toString().trim();
                if (TextUtils.isEmpty(etstartDate.getText().toString().trim())) {
                    etstartDate.setError("Please Select Start Date");
                    etstartDate.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(etEndDate.getText().toString().trim())) {
                    etEndDate.setError("Please Select End Date");
                    etEndDate.requestFocus();
                    return;
                }
                dataItems.clear();
                homeAdapter.notifyDataSetChanged();
                requestListApi(userId, startDate, endDate, status, "" + page);
                dialog.dismiss();
                //activity.overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                //activity.overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
            }
        });
        dialog.show();

    }

    void statusSpinner(Spinner spinner) {
        final ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Pending");
        arrayList.add("Accepted");
        arrayList.add("Ongoing");
        arrayList.add("Completed");
        arrayList.add("Cancel");
        arrayList.add("Rejected");
        ArrayAdapter adapter = new ArrayAdapter(context, android.R.layout.simple_spinner_item, arrayList);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                status = parent.getItemAtPosition(position).toString();
                isFilter = true;
                Log.e(TAG, "onItemSelected: Spinner>>>> " + status);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    void changeStatusApi(final String requestId, String status) {
        final LoadingProgressDialog dialog = new LoadingProgressDialog(context, "Please wait...");
        dialog.setCancelable(false);
        dialog.show();
        ApiInterface apiInterface = ApiClients.getConnection(context);
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("user_id", userId);
        jsonObject.addProperty("request_id", requestId);
        jsonObject.addProperty("status", status);

        Call<JsonObject> call = apiInterface.changeStatusApi(loginResponse.getData().getToken(), jsonObject);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                dialog.dismiss();
                try {
                    if (response.code() >= 200 && response.code() <= 210) {
                        JSONObject object = new JSONObject(response.body().toString());
                        if (object != null) {
                            if (object.getString("success").equalsIgnoreCase("true")) {
                               // Toast.makeText(context, object.getString("message"), Toast.LENGTH_SHORT).show();
                                Fragment fragment = DecendentDetailFragment.newInstance(requestId,"");
                                String tagFragment = fragment.getClass().getName();
                                GlobalValues.getInstance().setFramgentTag(tagFragment);
                                CommonUtils.loadFragment(context, fragment, tagFragment);

                                homeAdapter.notifyDataSetChanged();
                            }
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
                            //    Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
                            if (jsonObjectError.optString("token_valid").equalsIgnoreCase("false")) {
                               // CommonUtils.logoutSession(activity);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(context, "Something went wrong!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void changeStatus(String requestId, String status) {
        changeStatusApi(requestId, status);
    }
}
