package com.transdignity.deathserviceprovider.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;

import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.transdignity.deathserviceprovider.R;

import com.transdignity.deathserviceprovider.adapters.HomeAdapter;
import com.transdignity.deathserviceprovider.databinding.FragmentHomeBinding;
import com.transdignity.deathserviceprovider.models.auth.LoginResponse;
import com.transdignity.deathserviceprovider.models.notifications.NotificationCountResponse;
import com.transdignity.deathserviceprovider.models.request.DataItem;
import com.transdignity.deathserviceprovider.models.request.RequestListResponse;
import com.transdignity.deathserviceprovider.network.ApiClients;
import com.transdignity.deathserviceprovider.network.ApiInterface;
import com.transdignity.deathserviceprovider.utilities.CommonUtils;
import com.transdignity.deathserviceprovider.utilities.GlobalValues;
import com.transdignity.deathserviceprovider.utilities.LoadingProgressDialog;
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

public class HomeFragment extends Fragment implements View.OnClickListener {

    Context context;
    Activity activity;
    RecyclerView recyclerView;
    RelativeLayout rlFabBtn;
    FragmentHomeBinding binding;
    String userId, startDate, endDate, status;
    HomeAdapter homeAdapter;
    LinearLayoutManager linearLayoutManager;
    Boolean loading = false, isFilter = false;
    Integer page = 0;
    List<DataItem> dataItems = new ArrayList<>();
    LoginResponse loginResponse;

    public HomeFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1) {
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
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loginResponse = (LoginResponse) GlobalValues.getPreferenceManager().getObject(PreferenceManager.LoginResponseKey, LoginResponse.class);
        userId = loginResponse.getData().getId();
        if (userId != null) {
            linearLayoutManager = new LinearLayoutManager(context);
            homeAdapter = new HomeAdapter(dataItems, context, activity);
            binding.recycleHome.setLayoutManager(linearLayoutManager);
            binding.recycleHome.setAdapter(homeAdapter);
           // Toast.makeText(context, ""+userId, Toast.LENGTH_SHORT).show();
            requestListApi(userId, startDate, endDate, status, "" + page);
            pagination();
            //  coutNotification();
            swiprefresh();
        }

        binding.rlFabButton.setOnClickListener(this);
        binding.notification.setOnClickListener(this);
        binding.filter.setOnClickListener(this);
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
            case R.id.rl_fab_button:
               /* Fragment fragment = NewRequestFragment.newInstance(null, false);
                String tagFragment = fragment.getClass().getName();
                GlobalValues.getInstance().setFramgentTag(tagFragment);
                CommonUtils.loadFragment(context, fragment, tagFragment);*/

                final Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(true);
                dialog.setContentView(R.layout.all_services_popup);
                //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                final TextView bt_descendant=dialog.findViewById(R.id.tv_descendant);
                final TextView bt_limo=dialog.findViewById(R.id.tv_limo);
                final TextView bt_casket=dialog.findViewById(R.id.tv_casket);
                final TextView bt_flower=dialog.findViewById(R.id.tv_flower);
                final TextView bt_ashes=dialog.findViewById(R.id.tv_ashes);
                final TextView bt_courier=dialog.findViewById(R.id.tv_courier);
                final TextView bt_us=dialog.findViewById(R.id.tv_us);
                final TextView bt_ap=dialog.findViewById(R.id.tv_airport);
                final TextView bt_burial=dialog.findViewById(R.id.tv_Burial);
                bt_descendant.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bt_descendant.setBackgroundResource(R.drawable.button_shape);
                        bt_limo.setBackgroundResource(R.drawable.button_unselected);
                        bt_casket.setBackgroundResource(R.drawable.button_unselected);
                        bt_flower.setBackgroundResource(R.drawable.button_unselected);
                        bt_ashes.setBackgroundResource(R.drawable.button_unselected);
                        bt_courier.setBackgroundResource(R.drawable.button_unselected);
                        bt_us.setBackgroundResource(R.drawable.button_unselected);
                       Fragment fragment = NewRequestFragment.newInstance(null, false);
                String tagFragment = fragment.getClass().getName();
                GlobalValues.getInstance().setFramgentTag(tagFragment);
                CommonUtils.loadFragment(context, fragment, tagFragment);
                dialog.dismiss();
                    }
                });

                bt_limo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bt_descendant.setBackgroundResource(R.drawable.button_unselected);
                        bt_limo.setBackgroundResource(R.drawable.button_shape);
                        bt_casket.setBackgroundResource(R.drawable.button_unselected);
                        bt_flower.setBackgroundResource(R.drawable.button_unselected);
                        bt_ashes.setBackgroundResource(R.drawable.button_unselected);
                        bt_courier.setBackgroundResource(R.drawable.button_unselected);
                        bt_us.setBackgroundResource(R.drawable.button_unselected);
                        Fragment fragment = LimoDetailFragment.newInstance(null,false);
                        String tagFragment = fragment.getClass().getName();
                        GlobalValues.getInstance().setFramgentTag(tagFragment);
                        CommonUtils.loadFragment(context, fragment, tagFragment);
                        dialog.dismiss();
                    }
                });

                bt_casket.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bt_descendant.setBackgroundResource(R.drawable.button_unselected);
                        bt_limo.setBackgroundResource(R.drawable.button_unselected);
                        bt_casket.setBackgroundResource(R.drawable.button_shape);
                        bt_flower.setBackgroundResource(R.drawable.button_unselected);
                        bt_ashes.setBackgroundResource(R.drawable.button_unselected);
                        bt_courier.setBackgroundResource(R.drawable.button_unselected);
                        bt_us.setBackgroundResource(R.drawable.button_unselected);

                        Fragment fragment = CasketServiceFragment.newInstance(null,false);
                        String tagFragment = fragment.getClass().getName();
                        GlobalValues.getInstance().setFramgentTag(tagFragment);
                        CommonUtils.loadFragment(context, fragment, tagFragment);
                        dialog.dismiss();
                    }
                });

                bt_flower.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bt_descendant.setBackgroundResource(R.drawable.button_unselected);
                        bt_limo.setBackgroundResource(R.drawable.button_unselected);
                        bt_casket.setBackgroundResource(R.drawable.button_unselected);
                        bt_flower.setBackgroundResource(R.drawable.button_shape);
                        bt_ashes.setBackgroundResource(R.drawable.button_unselected);
                        bt_courier.setBackgroundResource(R.drawable.button_unselected);
                        bt_us.setBackgroundResource(R.drawable.button_unselected);

                        Fragment fragment = FlowerServiceFragment.newInstance(null,false);
                        String tagFragment = fragment.getClass().getName();
                        GlobalValues.getInstance().setFramgentTag(tagFragment);
                        CommonUtils.loadFragment(context, fragment, tagFragment);
                        dialog.dismiss();
                    }
                });

                bt_ashes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bt_descendant.setBackgroundResource(R.drawable.button_unselected);
                        bt_limo.setBackgroundResource(R.drawable.button_unselected);
                        bt_casket.setBackgroundResource(R.drawable.button_unselected);
                        bt_flower.setBackgroundResource(R.drawable.button_unselected);
                        bt_ashes.setBackgroundResource(R.drawable.button_shape);
                        bt_courier.setBackgroundResource(R.drawable.button_unselected);
                        bt_us.setBackgroundResource(R.drawable.button_unselected);
                        Fragment fragment = AshesServiceFragment.newInstance(null,false);
                        String tagFragment = fragment.getClass().getName();
                        GlobalValues.getInstance().setFramgentTag(tagFragment);
                        CommonUtils.loadFragment(context, fragment, tagFragment);
                        dialog.dismiss();
                    }
                });

                bt_courier.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bt_descendant.setBackgroundResource(R.drawable.button_unselected);
                        bt_limo.setBackgroundResource(R.drawable.button_unselected);
                        bt_casket.setBackgroundResource(R.drawable.button_unselected);
                        bt_flower.setBackgroundResource(R.drawable.button_unselected);
                        bt_ashes.setBackgroundResource(R.drawable.button_unselected);
                        bt_courier.setBackgroundResource(R.drawable.button_shape);
                        bt_us.setBackgroundResource(R.drawable.button_unselected);

                        Fragment fragment = CourierServiceFragment.newInstance(null,false);
                        String tagFragment = fragment.getClass().getName();
                        GlobalValues.getInstance().setFramgentTag(tagFragment);
                        CommonUtils.loadFragment(context, fragment, tagFragment);
                        dialog.dismiss();
                    }
                });

                bt_us.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bt_descendant.setBackgroundResource(R.drawable.button_unselected);
                        bt_limo.setBackgroundResource(R.drawable.button_unselected);
                        bt_casket.setBackgroundResource(R.drawable.button_unselected);
                        bt_flower.setBackgroundResource(R.drawable.button_unselected);
                        bt_ashes.setBackgroundResource(R.drawable.button_unselected);
                        bt_courier.setBackgroundResource(R.drawable.button_unselected);
                        bt_us.setBackgroundResource(R.drawable.button_shape);

                        Fragment fragment = UsVeteranServiceFragment.newInstance(null,false);
                        String tagFragment = fragment.getClass().getName();
                        GlobalValues.getInstance().setFramgentTag(tagFragment);
                        CommonUtils.loadFragment(context, fragment, tagFragment);
                        dialog.dismiss();
                    }
                });
                bt_ap.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Fragment fragment = AirportArrivalFragment.newInstance(null,false);
                        String tagFragment = fragment.getClass().getName();
                        GlobalValues.getInstance().setFramgentTag(tagFragment);
                        CommonUtils.loadFragment(context, fragment, tagFragment);
                        dialog.dismiss();
                    }
                });
                bt_burial.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Fragment fragment = BurialServiceFragment.newInstance(null,false);
                        String tagFragment = fragment.getClass().getName();
                        GlobalValues.getInstance().setFramgentTag(tagFragment);
                        CommonUtils.loadFragment(context, fragment, tagFragment);
                        dialog.dismiss();
                    }
                });
                dialog.show();

                break;
            case R.id.notification:
                Fragment fragmentnotf = new NotificationFragment();
                String tagFragmentnotf = fragmentnotf.getClass().getName();
                GlobalValues.getInstance().setFramgentTag(tagFragmentnotf);
                CommonUtils.loadFragment(context, fragmentnotf, tagFragmentnotf);
                break;
            case R.id.filter:
                successDialog(getContext());
                break;
        }

    }

    void requestListApi(String userId, String startDate, String endDate, String status, String page) {
        final LoadingProgressDialog dialog = new LoadingProgressDialog(context, "Please wait...");
        dialog.setCancelable(false);
        dialog.show();
        ApiInterface apiInterface = ApiClients.getClient(context).create(ApiInterface.class);
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
                            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                            if (jsonObjectError.optString("token_valid").equalsIgnoreCase("false")) {
                                CommonUtils.logoutSession(activity);
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

    void coutNotification() {
        ApiInterface apiInterface = ApiClients.getClient(context).create(ApiInterface.class);
        Call<NotificationCountResponse> call = apiInterface.notificationCountApi(loginResponse.getData().getToken(),userId);
        call.enqueue(new Callback<NotificationCountResponse>() {
            @Override
            public void onResponse(Call<NotificationCountResponse> call, Response<NotificationCountResponse> response) {
                try {

                    if (response.code() >= 200 && response.code() <= 210) {
                        if (response.body().getSuccess().equalsIgnoreCase("true")) {
                            if (response.body().getData() > 0) {
                                binding.tvCountNotfcn.setVisibility(View.VISIBLE);
                                binding.tvCountNotfcn.setText("" + response.body().getData());
                            } else {
                                binding.tvCountNotfcn.setVisibility(View.GONE);
                            }
                        } else {
                            binding.tvCountNotfcn.setVisibility(View.GONE);
                        }

                    } else {
                        binding.tvCountNotfcn.setVisibility(View.GONE);
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
                    binding.tvCountNotfcn.setVisibility(View.GONE);

                }

            }

            @Override
            public void onFailure(Call<NotificationCountResponse> call, Throwable t) {
                binding.tvCountNotfcn.setVisibility(View.GONE);

            }
        });
    }

    public void successDialog(final Context context) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View viewCustom = inflater.inflate(R.layout.home_filter_dialog, null);
        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.setView(viewCustom);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
//        succesText.setText(msg);
        LinearLayout apply = viewCustom.findViewById(R.id.ll_aaply);
        LinearLayout cancel = viewCustom.findViewById(R.id.ll_cancel);
        final TextView etstartDate = viewCustom.findViewById(R.id.et_start_date);
        final TextView etEndDate = viewCustom.findViewById(R.id.et_end_date);
        Spinner spinner = viewCustom.findViewById(R.id.sp_status);
        statusSpinner(spinner);
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
}
