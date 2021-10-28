package com.cts.removalspecialist.fragments;

import android.app.Activity;
import android.content.Context;

import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cts.removalspecialist.MainActivity;
import com.cts.removalspecialist.databinding.FragmentDecendentDetailBinding;
import com.cts.removalspecialist.fragments.viewDetails.AirportRemovalDetailPageFragment;
import com.cts.removalspecialist.fragments.viewDetails.AshesViewDetialPageFragment;
import com.cts.removalspecialist.fragments.viewDetails.BurialAtSeaDetailPageFragment;
import com.cts.removalspecialist.fragments.viewDetails.DescendantViewDetailPageFragment;
import com.cts.removalspecialist.fragments.viewDetails.UsVeteranDetailPageFragment;
import com.cts.removalspecialist.models.auth.LoginResponse;
import com.cts.removalspecialist.models.requestDetails.RequestDetailsResponse;
import com.cts.removalspecialist.models.requestList.RequestListResponse;
import com.cts.removalspecialist.network.ApiClients;
import com.cts.removalspecialist.network.ApiInterface;
import com.cts.removalspecialist.utilities.CommonUtils;
import com.cts.removalspecialist.utilities.GlobalValues;
import com.cts.removalspecialist.R;
import com.cts.removalspecialist.utilities.LoadingProgressDialog;
import com.cts.removalspecialist.utilities.PreferenceManager;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class DecendentDetailFragment extends Fragment implements View.OnClickListener {
    Context context;
    Activity activity;
    FragmentDecendentDetailBinding binding;
    RequestDetailsResponse.Data dataItems;
    String cdMobileNo, requestId,service_id;
    LoginResponse loginResponse;
    LinearLayout ll_view_detail;
    String user_id;
    public DecendentDetailFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number otv_datef parameters
    public static DecendentDetailFragment newInstance(String requestId,String service_id) {
        DecendentDetailFragment fragment = new DecendentDetailFragment();
        fragment.requestId = requestId;
        fragment.service_id = service_id;
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_decendent_detail, container, false);

        View view = binding.getRoot();
        ll_view_detail=view.findViewById(R.id.ll_view_detail);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loginResponse = (LoginResponse) GlobalValues.getPreferenceManager().getObject(PreferenceManager.LoginResponseKey, LoginResponse.class);
        user_id=loginResponse.getData().getId().toString();
        if (requestId != null)
            getProfileDetails();
        binding.tvBack.setOnClickListener(this);
        binding.llComplete.setOnClickListener(this);
        binding.llCall.setOnClickListener(this);
        binding.tvViewFormArrow.setOnClickListener(this);
        ll_view_detail.setOnClickListener(this);
    }

    void getProfileDetails() {
        final LoadingProgressDialog dialog = new LoadingProgressDialog(context, "Please wait...");
        dialog.setCancelable(false);
        dialog.show();

        ApiInterface apiInterface = ApiClients.getConnection(context);
        Call<RequestDetailsResponse> call = apiInterface.getRequestDetailsApi(loginResponse.getData().getToken(),user_id, requestId);
        call.enqueue(new Callback<RequestDetailsResponse>() {
            @Override
            public void onResponse(Call<RequestDetailsResponse> call, Response<RequestDetailsResponse> response) {
                dialog.dismiss();
                try {

                    if (response.code() >= 200 && response.code() <= 210) {
                        if (response.body().getSuccess().equalsIgnoreCase("true")) {
                            dataItems = response.body().getData();
                            String name = "";
                            if (dataItems.getDecendantFirstName() != null) {
                                name = name + dataItems.getDecendantFirstName();
                            }
                            if (dataItems.getDecendantMiddleName() != null) {
                                name = name + " " + dataItems.getDecendantMiddleName();
                            }
                            if (dataItems.getDecendantLastName() != null) {
                                name = name + " " + dataItems.getDecendantLastName();
                            }

                            binding.tvDecendentName.setText(name);
                            binding.tvRemovedFromAddress.setText(dataItems.getRemovedFromAddress());
                            binding.tvTransferToAddress.setText(dataItems.getTransferredToAddress());
                            binding.tvDate.setText(dataItems.getFomatRequestDate());
                            binding.tvTime.setText(dataItems.getTimeOfDeath());

                            if (dataItems.getCdName() != null) {
                                binding.llDriverDetails.setVisibility(View.VISIBLE);
                                binding.tvCabDriverName.setText(dataItems.getCdName());
                            } else {
                                binding.llDriverDetails.setVisibility(View.GONE);
                            }
                            if (dataItems.getCabNo() != null) {
                                binding.llCabDetails.setVisibility(View.VISIBLE);
                                binding.tvCabName.setText(dataItems.getCabName());
                                binding.tvCabNumber.setText(dataItems.getCabNo());
                            } else {
                                binding.llCabDetails.setVisibility(View.GONE);
                            }
                            binding.tvStatusRunning.setText(dataItems.getRsStatus());
                            binding.tvStatusRunning.setVisibility(View.VISIBLE);
                            if (dataItems.getRsStatus().equalsIgnoreCase("Ongoing")) {
                                binding.tvStatusRunning.setBackgroundResource(R.drawable.bg_green_30corn);
                                binding.cvComplete.setVisibility(View.VISIBLE);
                            } else if (dataItems.getRsStatus().equalsIgnoreCase("Completed") ||
                                    dataItems.getRsStatus().equalsIgnoreCase("Rejected") ||
                                    dataItems.getRsStatus().equalsIgnoreCase("Cencel")) {
                                binding.tvStatusRunning.setBackgroundResource(R.drawable.bg_red_30corn);
                                binding.cvComplete.setVisibility(View.GONE);
                            } else {
                                binding.tvStatusRunning.setBackgroundResource(R.drawable.bg_primary_30corn);
                                binding.cvComplete.setVisibility(View.GONE);

                            }
                            cdMobileNo = dataItems.getCdPhone();
                        }
                    }else {
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
                                CommonUtils.logoutSession(activity);
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
            public void onFailure(Call<RequestDetailsResponse> call, Throwable t) {
                dialog.dismiss();
                Log.e(TAG, "onResponse: " + t.getMessage());
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tv_back:
                Fragment fragment = new HomeFragment();
                String fragmtStatusTag = fragment.getClass().getName();
                GlobalValues.getInstance().setFramgentTag(fragmtStatusTag);
                ((MainActivity) activity).selectedNavItem(R.id.navigation_home);
                CommonUtils.loadFragment(context, fragment, fragmtStatusTag);
                break;
            case R.id.ll_view_detail:
                if (dataItems != null) {
                    if(service_id.equalsIgnoreCase("1")){
                        Fragment fragment1 = DescendantViewDetailPageFragment.newInstance(requestId,"Home");
                        String tagFragment = fragment1.getClass().getName();
                        GlobalValues.getInstance().setFramgentTag(tagFragment);
                        CommonUtils.loadFragment(context, fragment1, tagFragment);
                    }else if(service_id.equalsIgnoreCase("5")){
                        Fragment fragment1 = AshesViewDetialPageFragment.newInstance(requestId,"Home");
                        String tagFragment = fragment1.getClass().getName();
                        GlobalValues.getInstance().setFramgentTag(tagFragment);
                        CommonUtils.loadFragment(context, fragment1, tagFragment);
                    }else if(service_id.equalsIgnoreCase("8")){
                        Fragment fragment1 = AirportRemovalDetailPageFragment.newInstance(requestId,"Home");
                        String tagFragment = fragment1.getClass().getName();
                        GlobalValues.getInstance().setFramgentTag(tagFragment);
                        CommonUtils.loadFragment(context, fragment1, tagFragment);
                    }
                }
                break;
            case R.id.tv_view_form_arrow:
                if (dataItems != null) {
                    if(service_id.equalsIgnoreCase("1")){
                        Fragment fragment1 = DescendantViewDetailPageFragment.newInstance(requestId,"1");
                        String tagFragment = fragment1.getClass().getName();
                        GlobalValues.getInstance().setFramgentTag(tagFragment);
                        CommonUtils.loadFragment(context, fragment1, tagFragment);
                    }else if(service_id.equalsIgnoreCase("5")){
                        Fragment fragment1 = AshesViewDetialPageFragment.newInstance(requestId,"5");
                        String tagFragment = fragment1.getClass().getName();
                        GlobalValues.getInstance().setFramgentTag(tagFragment);
                        CommonUtils.loadFragment(context, fragment1, tagFragment);
                    }else if(service_id.equalsIgnoreCase("7")){
                        Fragment fragment1 = UsVeteranDetailPageFragment.newInstance(requestId,"7");
                        String tagFragment = fragment1.getClass().getName();
                        GlobalValues.getInstance().setFramgentTag(tagFragment);
                        CommonUtils.loadFragment(context, fragment1, tagFragment);
                    }else if(service_id.equalsIgnoreCase("8")){
                        Fragment fragment1 = AirportRemovalDetailPageFragment.newInstance(requestId,"8");
                        String tagFragment = fragment1.getClass().getName();
                        GlobalValues.getInstance().setFramgentTag(tagFragment);
                        CommonUtils.loadFragment(context, fragment1, tagFragment);
                    }else if(service_id.equalsIgnoreCase("9")){
                        Fragment fragment1 = BurialAtSeaDetailPageFragment.newInstance(requestId,"9");
                        String tagFragment = fragment1.getClass().getName();
                        GlobalValues.getInstance().setFramgentTag(tagFragment);
                        CommonUtils.loadFragment(context, fragment1, tagFragment);
                    }
                }
                break;
            case R.id.ll_complete:
                changeStatusApi(requestId, "complete");
                break;
            case R.id.ll_call:
                try {
                    if (cdMobileNo != null) {
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:" + cdMobileNo));
                        context.startActivity(callIntent);
                    } else {
                        Toast.makeText(context, "Invalid Mobile Number", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    void changeStatusApi(final String requestId, String status) {
        final LoadingProgressDialog dialog = new LoadingProgressDialog(context, "Please wait...");
        dialog.setCancelable(false);
        dialog.show();
        ApiInterface apiInterface = ApiClients.getConnection(context);
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("user_id", loginResponse.getData().getId());
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
                                Toast.makeText(context, object.getString("message"), Toast.LENGTH_SHORT).show();
                                getProfileDetails();
                                /*Fragment fragment = DecendentDetailFragment.newInstance(requestId);
                                String tagFragment = fragment.getClass().getName();
                                GlobalValues.getInstance().setFramgentTag(tagFragment);
                                CommonUtils.loadFragment(context, fragment, tagFragment);*/

                            }
                        }
                    }else {
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
                                //CommonUtils.logoutSession(activity);
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

}
