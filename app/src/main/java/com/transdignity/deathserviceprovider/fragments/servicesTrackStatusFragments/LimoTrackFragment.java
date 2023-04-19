package com.transdignity.deathserviceprovider.fragments.servicesTrackStatusFragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.transdignity.deathserviceprovider.MainActivity;
import com.transdignity.deathserviceprovider.R;
import com.transdignity.deathserviceprovider.fragments.ServicesDetailsFragments.LimoDetailPageFragment;
import com.transdignity.deathserviceprovider.models.TrackResponse;
import com.transdignity.deathserviceprovider.models.auth.LoginResponse;
import com.transdignity.deathserviceprovider.models.requestDetails.Data;
import com.transdignity.deathserviceprovider.models.tracking.LimooTrackingModel;
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

public class LimoTrackFragment extends Fragment {
    Context context;
    Activity activity;
    Data data;
    LoginResponse loginResponse;
    String id;
    View view;
    TextView tv_decendent_name,tv_pickup,tv_drop;
    ImageView iv_request_accepted,iv_on_the_way,iv_driver_reached,iv_pickup_specialist,iv_drop_location_active;
    TextView tv_request_accepted,tv_on_the_way,tv_driver_reached,tv_pickup_specialist,tv_drop_location_active;
    TextView tv_back;
    public LimoTrackFragment() {
        // Required empty public constructor
    }


    public static LimoTrackFragment newInstance(String id) {
        LimoTrackFragment fragment = new LimoTrackFragment();
        fragment.id = id;
        return fragment;
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
        view= inflater.inflate(R.layout.fragment_limo_track, container, false);
        loginResponse = (LoginResponse) GlobalValues.getPreferenceManager().getObject(PreferenceManager.LoginResponseKey, LoginResponse.class);
        inti();
        trackStatus();
        return view;
    }

    public void inti(){
        tv_back=view.findViewById(R.id.tv_back);
        tv_decendent_name=view.findViewById(R.id.tv_decendent_name);
        tv_pickup=view.findViewById(R.id.tv_pickup);
        tv_drop=view.findViewById(R.id.tv_drop);
        iv_request_accepted=view.findViewById(R.id.iv_request_accepted);
        iv_on_the_way=view.findViewById(R.id.iv_on_the_way);
        iv_driver_reached=view.findViewById(R.id.iv_driver_reached);
        iv_pickup_specialist=view.findViewById(R.id.iv_pickup_specialist);
        iv_drop_location_active=view.findViewById(R.id.iv_drop_location_active);

        tv_request_accepted=view.findViewById(R.id.tv_request_accepted);
        tv_on_the_way=view.findViewById(R.id.tv_on_the_way);
        tv_driver_reached=view.findViewById(R.id.tv_driver_reached);
        tv_pickup_specialist=view.findViewById(R.id.tv_pickup_specialist);
        tv_drop_location_active=view.findViewById(R.id.tv_drop_location_active);
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment2;
                fragment2 =  LimoDetailPageFragment.newInstance(id,"Home");
                String fragmtStatusTag = fragment2.getClass().getName();
                GlobalValues.getInstance().setFramgentTag(fragmtStatusTag);
                ((MainActivity) activity).selectedNavItem(R.id.navigation_home);
                CommonUtils.loadFragment(context, fragment2, fragmtStatusTag);
            }
        });
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
        Call<LimooTrackingModel> call = apiInterface.trackLimoStatusApi(loginResponse.getData().getToken(), id);
        call.enqueue(new Callback<LimooTrackingModel>() {
            @Override
            public void onResponse(Call<LimooTrackingModel> call, Response<LimooTrackingModel> response) {
                try {

                    if (response.code() >= 200 && response.code() <= 210) {
                        if (response.body().getSuccess().equalsIgnoreCase("true")) {
                            tv_decendent_name.setText(response.body().getData().getDecendantName());
                            tv_pickup.setText(response.body().getData().getRemovedFromAddress());
                            tv_drop.setText(response.body().getData().getTransferredToAddress());
                            String rq_accept = response.body().getData().getRequestAcceptedTitme();
                            String rq_pickupLocation = response.body().getData().getReachedOnDecendantPickupLocationTime();
                            String rq_pickup = response.body().getData().getmPickupDecendentTime();
                            String rq_drop = response.body().getData().getDropDecendantTime();
                            if(rq_accept!=null){
                                iv_request_accepted.setImageResource(R.drawable.circle_fill);
                                iv_on_the_way.setImageResource(R.drawable.circle_fill);
                                tv_request_accepted.setText(rq_accept);


                            }
                            if(rq_pickupLocation!=null){
                                iv_request_accepted.setImageResource(R.drawable.circle_fill);
                                iv_on_the_way.setImageResource(R.drawable.circle_fill);
                                tv_request_accepted.setText(rq_accept);
                                iv_driver_reached.setImageResource(R.drawable.circle_fill);
                                tv_driver_reached.setText(rq_pickupLocation);




                            }
                            if(rq_pickup!=null){
                                iv_request_accepted.setImageResource(R.drawable.circle_fill);
                                iv_on_the_way.setImageResource(R.drawable.circle_fill);
                                tv_request_accepted.setText(rq_accept);
                                iv_driver_reached.setImageResource(R.drawable.circle_fill);
                                tv_driver_reached.setText(rq_pickupLocation);
                                iv_pickup_specialist.setImageResource(R.drawable.circle_fill);
                                tv_pickup_specialist.setText(rq_pickup);


                            }
                            if(rq_drop!=null){
                                iv_request_accepted.setImageResource(R.drawable.circle_fill);
                                iv_on_the_way.setImageResource(R.drawable.circle_fill);
                                tv_request_accepted.setText(rq_accept);
                                iv_driver_reached.setImageResource(R.drawable.circle_fill);
                                tv_driver_reached.setText(rq_pickupLocation);
                                iv_pickup_specialist.setImageResource(R.drawable.circle_fill);
                                tv_pickup_specialist.setText(rq_pickup);
                                iv_drop_location_active.setImageResource(R.drawable.circle_fill);
                                tv_drop_location_active.setText(rq_drop);

                            }






/*
                            if(response.body().getData().getSteps().equals("1")){
                                iv_request_accepted.setImageResource(R.drawable.circle_fill);
                                tv_request_accepted.setText(response.body().getData().getRequestAcceptedTitme().toString());
                            }else if(response.body().getData().getSteps().equals("2")){
                                iv_request_accepted.setImageResource(R.drawable.circle_fill);
                                iv_on_the_way.setImageResource(R.drawable.circle_fill);
                                tv_request_accepted.setText(response.body().getData().getRequestAcceptedTitme().toString());
                            }else if(response.body().getData().getSteps().equals("3")){
                                iv_request_accepted.setImageResource(R.drawable.circle_fill);
                                iv_on_the_way.setImageResource(R.drawable.circle_fill);
                                iv_driver_reached.setImageResource(R.drawable.circle_fill);
                                tv_request_accepted.setText(response.body().getData().getRequestAcceptedTitme().toString());
                                tv_driver_reached.setText(response.body().getData().getReachedOnDecendantPickupLocationTime().toString());
                            }else if(response.body().getData().getSteps().equals("4")){
                                iv_request_accepted.setImageResource(R.drawable.circle_fill);
                                iv_on_the_way.setImageResource(R.drawable.circle_fill);
                                iv_driver_reached.setImageResource(R.drawable.circle_fill);
                                iv_pickup_specialist.setImageResource(R.drawable.circle_fill);
                                tv_request_accepted.setText(response.body().getData().getRequestAcceptedTitme().toString());
                                tv_driver_reached.setText(response.body().getData().getReachedOnDecendantPickupLocationTime().toString());

                            }else if(response.body().getData().getSteps().equals("5")){
                                iv_request_accepted.setImageResource(R.drawable.circle_fill);
                                iv_on_the_way.setImageResource(R.drawable.circle_fill);
                                iv_driver_reached.setImageResource(R.drawable.circle_fill);
                                iv_pickup_specialist.setImageResource(R.drawable.circle_fill);
                                iv_drop_location_active.setImageResource(R.drawable.circle_fill);
                                tv_request_accepted.setText(response.body().getData().getRequestAcceptedTitme().toString());
                                tv_driver_reached.setText(response.body().getData().getReachedOnDecendantPickupLocationTime().toString());
                                tv_drop_location_active.setText(response.body().getData().getDropDecendantTime().toString());

                            }
*/
                          //  tv_drop.setText(response.body().getData().getTransferedToAddress());
                       /*     binding.tvDecendentName.setText(response.body().getData().getDecendantName());
                            binding.tvRemovedFromAddress.setText(response.body().getData().getRemovedFromAddress());
                            binding.tvTransferToAddress.setText(response.body().getData().getTransferedToAddress());

                            if (response.body().getData().getSteps() == 1) {
                                binding.ivRemovalSpecialistAssigned.setImageResource(R.drawable.circle_fill);
                                binding.tvRemovalSpecialistAssignedTime.setText(response.body().getData().getRemovalSpecialistsAssignTime());
                            } else if (response.body().getData().getSteps() == 2) {
                                binding.ivDriverAssignedActive.setImageResource(R.drawable.circle_fill);
                                binding.ivRemovalSpecialistAssigned.setImageResource(R.drawable.circle_fill);
                                binding.tvRemovalSpecialistAssignedTime.setText(response.body().getData().getRemovalSpecialistsAssignTime());
                                binding.tvDriverAssignedTime.setText(response.body().getData().getCabDriverAssignTime());
                            } else if (response.body().getData().getSteps() == 3) {
                                binding.ivDriverAssignedActive.setImageResource(R.drawable.circle_fill);
                                binding.ivRemovalSpecialistAssigned.setImageResource(R.drawable.circle_fill);
                                binding.ivDriverReachedSpecialistLocationActive.setImageResource(R.drawable.circle_fill);
                                binding.tvRemovalSpecialistAssignedTime.setText(response.body().getData().getRemovalSpecialistsAssignTime());
                                binding.tvDriverAssignedTime.setText(response.body().getData().getCabDriverAssignTime());
                                binding.tvDriverReachedSpecialistLocationTime.setText(response.body().getData().getReachedOnRsLocationTime());
                            } else if (response.body().getData().getSteps() == 4) {
                                binding.ivDriverAssignedActive.setImageResource(R.drawable.circle_fill);
                                binding.ivRemovalSpecialistAssigned.setImageResource(R.drawable.circle_fill);
                                binding.ivDriverReachedSpecialistLocationActive.setImageResource(R.drawable.circle_fill);
                                binding.ivPickupSpecialistActive.setImageResource(R.drawable.circle_fill);
                                binding.tvRemovalSpecialistAssignedTime.setText(response.body().getData().getRemovalSpecialistsAssignTime());
                                binding.tvDriverAssignedTime.setText(response.body().getData().getCabDriverAssignTime());
                                binding.tvDriverReachedSpecialistLocationTime.setText(response.body().getData().getReachedOnRsLocationTime());
                                binding.tvPickupSpecialistTime.setText(response.body().getData().getPickupRsTime());
                            } else if (response.body().getData().getSteps() == 5) {
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
                            } else if (response.body().getData().getSteps() == 6) {
                                binding.ivDriverAssignedActive.setImageResource(R.drawable.circle_fill);
                                binding.ivRemovalSpecialistAssigned.setImageResource(R.drawable.circle_fill);
                                binding.ivDriverReachedSpecialistLocationActive.setImageResource(R.drawable.circle_fill);
                                binding.ivPickupSpecialistActive.setImageResource(R.drawable.circle_fill);
                                binding.ivReachedOnDecendentPickupLocationActive.setImageResource(R.drawable.circle_fill);
                                binding.ivDropLocationActive.setImageResource(R.drawable.circle_fill);
                                binding.tvRemovalSpecialistAssignedTime.setText(response.body().getData().getRemovalSpecialistsAssignTime());
                                binding.tvDriverAssignedTime.setText(response.body().getData().getCabDriverAssignTime());
                                binding.tvDriverReachedSpecialistLocationTime.setText(response.body().getData().getReachedOnRsLocationTime());
                                binding.tvPickupSpecialistTime.setText(response.body().getData().getPickupRsTime());
                                binding.tvReachedOnDecendentPickupLocationTime.setText(response.body().getData().getReachedOnDecendantPickupLocationTime());
                                binding.tvDropLocationTime.setText(response.body().getData().getDropDecendantTime());
                            }*/
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
            public void onFailure(Call<LimooTrackingModel> call, Throwable t) {


            }
        });
    }

}