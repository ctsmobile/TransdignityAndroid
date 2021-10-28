package com.transdignity.deathserviceprovider.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.transdignity.deathserviceprovider.MainActivity;
import com.transdignity.deathserviceprovider.R;
import com.transdignity.deathserviceprovider.databinding.FragmentTrackBinding;
import com.transdignity.deathserviceprovider.models.TrackResponse;
import com.transdignity.deathserviceprovider.models.auth.LoginResponse;
import com.transdignity.deathserviceprovider.models.notifications.NotificationCountResponse;
import com.transdignity.deathserviceprovider.models.requestDetails.Data;
import com.transdignity.deathserviceprovider.models.tracking.DecsendantTrakingModel;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TrackFragment extends Fragment implements View.OnClickListener {
    String id;
    FragmentTrackBinding binding;
    Context context;
    Activity activity;
    Data data;
    LoginResponse loginResponse;
    ImageView iv_decendent_pickup;
    TextView tv_decendent_pickup;
    public static TrackFragment newInstance(String id) {
        TrackFragment fragment = new TrackFragment();
        fragment.id = id;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_track, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.tvBack.setOnClickListener(this);
        loginResponse = (LoginResponse) GlobalValues.getPreferenceManager().getObject(PreferenceManager.LoginResponseKey, LoginResponse.class);
              /*  binding.tvDecendentName.setText(data.getDecendantFirstName());
        binding.tvRemovedFromAddress.setText(data.getRemovedFromAddress());
        binding.tvTransferToAddress.setText(data.getTransferredToAddress());*/
        iv_decendent_pickup=view.findViewById(R.id.iv_decendent_pickup);
        tv_decendent_pickup=view.findViewById(R.id.tv_decendent_pickup);
        trackStatus();
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

    void trackStatus() {
        ApiInterface apiInterface = ApiClients.getClient(context).create(ApiInterface.class);
        Call<DecsendantTrakingModel> call = apiInterface.trackStatusApi(loginResponse.getData().getToken(), id);
        call.enqueue(new Callback<DecsendantTrakingModel>() {
            @Override
            public void onResponse(Call<DecsendantTrakingModel> call, Response<DecsendantTrakingModel> response) {
                try {

                    if (response.code() >= 200 && response.code() <= 210) {
                        if (response.body().getSuccess().equalsIgnoreCase("true")) {
                            binding.tvDecendentName.setText(response.body().getData().getDecendantName());
                            binding.tvRemovedFromAddress.setText(response.body().getData().getRemovedFromAddress());
                            binding.tvTransferToAddress.setText(response.body().getData().getTransferredToAddress());
                            String removal_specialists_assign_time=response.body().getData().getRemovalSpecialistsAssignTime();
                            String cab_driver_assign_time=response.body().getData().getCabDriverAssignTime();
                            String reached_on_rs_location_time=response.body().getData().getReachedOnRsLocationTime();
                            String pickup_rs_time=response.body().getData().getPickupRsTime();
                            String reached_on_decendant_pickup_location_time=response.body().getData().getReachedOnDecendantPickupLocationTime();
                            String pickup_decendent_time=response.body().getData().getPickupDecendentTime();
                            String drop_decendant_time=response.body().getData().getDropDecendantTime();
                            if (removal_specialists_assign_time!=null) {
                                binding.ivRemovalSpecialistAssigned.setImageResource(R.drawable.circle_fill);
                                binding.tvRemovalSpecialistAssignedTime.setText(response.body().getData().getRemovalSpecialistsAssignTime());
                            }
                            if (cab_driver_assign_time!=null) {
                                binding.ivDriverAssignedActive.setImageResource(R.drawable.circle_fill);
                                binding.ivRemovalSpecialistAssigned.setImageResource(R.drawable.circle_fill);
                                binding.tvRemovalSpecialistAssignedTime.setText(response.body().getData().getRemovalSpecialistsAssignTime());
                                binding.tvDriverAssignedTime.setText(response.body().getData().getCabDriverAssignTime());
                            }
                             if (reached_on_rs_location_time!=null) {
                                binding.ivDriverAssignedActive.setImageResource(R.drawable.circle_fill);
                                binding.ivRemovalSpecialistAssigned.setImageResource(R.drawable.circle_fill);
                                binding.ivDriverReachedSpecialistLocationActive.setImageResource(R.drawable.circle_fill);
                                binding.tvRemovalSpecialistAssignedTime.setText(response.body().getData().getRemovalSpecialistsAssignTime());
                                binding.tvDriverAssignedTime.setText(response.body().getData().getCabDriverAssignTime());
                                binding.tvDriverReachedSpecialistLocationTime.setText(response.body().getData().getReachedOnRsLocationTime());
                            }
                              if (pickup_rs_time!=null) {
                                binding.ivDriverAssignedActive.setImageResource(R.drawable.circle_fill);
                                binding.ivRemovalSpecialistAssigned.setImageResource(R.drawable.circle_fill);
                                binding.ivDriverReachedSpecialistLocationActive.setImageResource(R.drawable.circle_fill);
                                binding.ivPickupSpecialistActive.setImageResource(R.drawable.circle_fill);
                                binding.tvRemovalSpecialistAssignedTime.setText(response.body().getData().getRemovalSpecialistsAssignTime());
                                binding.tvDriverAssignedTime.setText(response.body().getData().getCabDriverAssignTime());
                                binding.tvDriverReachedSpecialistLocationTime.setText(response.body().getData().getReachedOnRsLocationTime());
                                binding.tvPickupSpecialistTime.setText(response.body().getData().getPickupRsTime());
                            }
                               if (reached_on_decendant_pickup_location_time!=null) {
                                binding.ivDriverAssignedActive.setImageResource(R.drawable.circle_fill);
                                binding.ivRemovalSpecialistAssigned.setImageResource(R.drawable.circle_fill);
                                binding.ivDriverReachedSpecialistLocationActive.setImageResource(R.drawable.circle_fill);
                                binding.ivPickupSpecialistActive.setImageResource(R.drawable.circle_fill);
                                binding.ivReachedOnDecendentPickupLocationActive.setImageResource(R.drawable.circle_fill);
                                binding.tvRemovalSpecialistAssignedTime.setText(response.body().getData().getRemovalSpecialistsAssignTime());
                                binding.tvDriverAssignedTime.setText(response.body().getData().getCabDriverAssignTime());
                                binding.tvDriverReachedSpecialistLocationTime.setText(response.body().getData().getReachedOnRsLocationTime());
                                binding.tvPickupSpecialistTime.setText(response.body().getData().getPickupRsTime());
                                binding.tvReachedOnDecendentPickupLocationTime.setText(response.body().getData().getReachedOnDecendantPickupLocationTime());
                            }
                            if (pickup_decendent_time!=null) {
                                binding.ivDriverAssignedActive.setImageResource(R.drawable.circle_fill);
                                binding.ivRemovalSpecialistAssigned.setImageResource(R.drawable.circle_fill);
                                binding.ivDriverReachedSpecialistLocationActive.setImageResource(R.drawable.circle_fill);
                                binding.ivPickupSpecialistActive.setImageResource(R.drawable.circle_fill);
                                binding.ivReachedOnDecendentPickupLocationActive.setImageResource(R.drawable.circle_fill);
                                iv_decendent_pickup.setImageResource(R.drawable.circle_fill);
                                binding.tvRemovalSpecialistAssignedTime.setText(response.body().getData().getRemovalSpecialistsAssignTime());
                                binding.tvDriverAssignedTime.setText(response.body().getData().getCabDriverAssignTime());
                                binding.tvDriverReachedSpecialistLocationTime.setText(response.body().getData().getReachedOnRsLocationTime());
                                binding.tvPickupSpecialistTime.setText(response.body().getData().getPickupRsTime());
                                binding.tvReachedOnDecendentPickupLocationTime.setText(response.body().getData().getReachedOnDecendantPickupLocationTime());
                                tv_decendent_pickup.setText(pickup_decendent_time);
                            }

                                if (drop_decendant_time!=null) {
                                binding.ivDriverAssignedActive.setImageResource(R.drawable.circle_fill);
                                binding.ivRemovalSpecialistAssigned.setImageResource(R.drawable.circle_fill);
                                binding.ivDriverReachedSpecialistLocationActive.setImageResource(R.drawable.circle_fill);
                                binding.ivPickupSpecialistActive.setImageResource(R.drawable.circle_fill);
                                binding.ivReachedOnDecendentPickupLocationActive.setImageResource(R.drawable.circle_fill);
                                iv_decendent_pickup.setImageResource(R.drawable.circle_fill);

                                binding.ivDropLocationActive.setImageResource(R.drawable.circle_fill);

                                binding.tvRemovalSpecialistAssignedTime.setText(response.body().getData().getRemovalSpecialistsAssignTime());
                                binding.tvDriverAssignedTime.setText(response.body().getData().getCabDriverAssignTime());
                                binding.tvDriverReachedSpecialistLocationTime.setText(response.body().getData().getReachedOnRsLocationTime());
                                binding.tvPickupSpecialistTime.setText(response.body().getData().getPickupRsTime());
                                binding.tvReachedOnDecendentPickupLocationTime.setText(response.body().getData().getReachedOnDecendantPickupLocationTime());
                                tv_decendent_pickup.setText(pickup_decendent_time);

                                binding.tvDropLocationTime.setText(response.body().getData().getDropDecendantTime());
                            }
//2651  883
                        } else {
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

                }
            }

            @Override
            public void onFailure(Call<DecsendantTrakingModel> call, Throwable t) {


            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_back:
                Fragment fragment = DecendentDetailFragment.newInstance(id,"");
                String fragmtStatusTag2 = fragment.getClass().getName();
                GlobalValues.getInstance().setFramgentTag(fragmtStatusTag2);
                ((MainActivity) activity).selectedNavItem(R.id.navigation_home);
                CommonUtils.loadFragment(context, fragment, fragmtStatusTag2);
                break;
        }
    }
}
