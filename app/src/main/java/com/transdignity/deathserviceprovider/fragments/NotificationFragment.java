package com.transdignity.deathserviceprovider.fragments;

import android.app.Activity;
import android.content.Context;

import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.transdignity.deathserviceprovider.MainActivity;
import com.transdignity.deathserviceprovider.R;
import com.transdignity.deathserviceprovider.adapters.NotificationAdapter;
import com.transdignity.deathserviceprovider.databinding.FragmentNotificationBinding;
import com.transdignity.deathserviceprovider.models.auth.LoginResponse;
import com.transdignity.deathserviceprovider.models.notifications.NotificationListResponse;
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


public class NotificationFragment extends Fragment {
    NotificationAdapter notificationAdapter;
    LinearLayoutManager linearLayoutManager;
    List<NotificationListResponse.DataItem> dataItems = new ArrayList<>();
    String userId;
    Integer page = 0;
    Context context;
    Activity activity;
    Boolean loading = false;
    FragmentNotificationBinding binding;
    LoginResponse loginResponse;

    public static NotificationFragment newInstance() {
        NotificationFragment fragment = new NotificationFragment();
        return fragment;
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_notification, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loginResponse = (LoginResponse) GlobalValues.getPreferenceManager().getObject(PreferenceManager.LoginResponseKey, LoginResponse.class);
        userId = loginResponse.getData().getId();
        if (userId != null) {
            linearLayoutManager = new LinearLayoutManager(context);
            binding.recycleNotification.setLayoutManager(linearLayoutManager);
            notificationAdapter = new NotificationAdapter(dataItems, getContext());
            binding.recycleNotification.setAdapter(notificationAdapter);
            notificationListApi(userId, "" + page);
            swiprefresh();
            pagination();
        }
        binding.tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment;
                fragment = new HomeFragment();
                String fragmtStatusTag = fragment.getClass().getName();
                GlobalValues.getInstance().setFramgentTag(fragmtStatusTag);
                ((MainActivity) getActivity()).selectedNavItem(R.id.navigation_home);
                CommonUtils.loadFragment(getActivity(), fragment, fragmtStatusTag);
            }
        });
    }

    void swiprefresh() {
        binding.swiprefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                dataItems.clear();
                //  isFilter = false;
                notificationListApi(userId, "" + 0);
                binding.swiprefresh.setRefreshing(false);
            }
        });
    }

    void notificationListApi(String userId, String page) {
        final LoadingProgressDialog dialog = new LoadingProgressDialog(context, "Please wait...");
        dialog.setCancelable(false);
        dialog.show();
        ApiInterface apiInterface = ApiClients.getClient(context).create(ApiInterface.class);
        Call<NotificationListResponse> call = apiInterface.notificationListApi(loginResponse.getData().getToken(), userId, page);
        call.enqueue(new Callback<NotificationListResponse>() {
            @Override
            public void onResponse(Call<NotificationListResponse> call, Response<NotificationListResponse> response) {

                dialog.dismiss();
                try {

                    if (response.code() >= 200 && response.code() <= 210) {
                        if (response.body().getSuccess().equalsIgnoreCase("true")) {
                            if (response.body().getData().size() > 0) {
                                dataItems.addAll(response.body().getData());
                                notificationAdapter.notifyDataSetChanged();
                                loading = true;
                            } else {
                                loading = false;
                            }
                            if (dataItems.size() > 0) {
                                binding.recycleNotification.setVisibility(View.VISIBLE);
                                binding.emptyLayout.setVisibility(View.GONE);
                            } else {
                                binding.recycleNotification.setVisibility(View.GONE);
                                binding.emptyLayout.setVisibility(View.VISIBLE);
                            }
                        } else {
                            binding.recycleNotification.setVisibility(View.GONE);
                            binding.emptyLayout.setVisibility(View.VISIBLE);
                            Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    } else {
                        binding.recycleNotification.setVisibility(View.GONE);
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
                    binding.recycleNotification.setVisibility(View.GONE);
                    binding.emptyLayout.setVisibility(View.VISIBLE);

                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<NotificationListResponse> call, Throwable t) {
                binding.recycleNotification.setVisibility(View.GONE);
                binding.emptyLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    void pagination() {
        binding.recycleNotification.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                            notificationListApi(userId, "" + page);
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

}
