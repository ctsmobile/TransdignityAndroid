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
import com.transdignity.deathserviceprovider.fragments.ServicesDetailsFragments.FlowerDetailPageFragment;
import com.transdignity.deathserviceprovider.fragments.ServicesDetailsFragments.LimoDetailPageFragment;
import com.transdignity.deathserviceprovider.models.TrackResponse;
import com.transdignity.deathserviceprovider.models.auth.LoginResponse;
import com.transdignity.deathserviceprovider.models.requestDetails.Data;
import com.transdignity.deathserviceprovider.models.tracking.FlowerTrackingModel;
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


public class FlowerTrackFragment extends Fragment {
    Context context;
    Activity activity;
    Data data;
    LoginResponse loginResponse;
    String id;
    View view;
    TextView tv_decendent_name,tv_pickup_location,tv_drop_location,tv_back;
    ImageView iv_request_accepted,iv_flower_pickup,iv_on_the_way,iv_flower_deliver;
    TextView tv_request_accepted,tv_flower_pickup,tv_on_the_way,tv_flower_deliver;
    String pickup_location;
    public FlowerTrackFragment() {
        // Required empty public constructor
    }
    public static FlowerTrackFragment newInstance(String id,String pickup_location) {
        FlowerTrackFragment fragment = new FlowerTrackFragment();
        fragment.id = id;
        fragment.pickup_location = pickup_location;
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
        view= inflater.inflate(R.layout.fragment_flower_track, container, false);
        loginResponse = (LoginResponse) GlobalValues.getPreferenceManager().getObject(PreferenceManager.LoginResponseKey, LoginResponse.class);
        init();
        trackStatus();
        return view;
    }

    public void init(){
        tv_back=view.findViewById(R.id.tv_back);
        tv_decendent_name=view.findViewById(R.id.tv_decendent_name);
        tv_pickup_location=view.findViewById(R.id.tv_pickup_location);
        tv_drop_location=view.findViewById(R.id.tv_drop_location);
        iv_request_accepted=view.findViewById(R.id.iv_request_accepted);
        iv_flower_pickup=view.findViewById(R.id.iv_flower_pickup);
        iv_on_the_way=view.findViewById(R.id.iv_on_the_way);
        iv_flower_deliver=view.findViewById(R.id.iv_flower_deliver);
        tv_request_accepted=view.findViewById(R.id.tv_request_accepted);
        tv_flower_pickup=view.findViewById(R.id.tv_flower_pickup);
        tv_on_the_way=view.findViewById(R.id.tv_on_the_way);
        tv_flower_deliver=view.findViewById(R.id.tv_flower_deliver);
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment2;
                fragment2 =  FlowerDetailPageFragment.newInstance(id,"Home");
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
        Call<FlowerTrackingModel> call = apiInterface.trackFlowerStatusApi(loginResponse.getData().getToken(), id);
        call.enqueue(new Callback<FlowerTrackingModel>() {
            @Override
            public void onResponse(Call<FlowerTrackingModel> call, Response<FlowerTrackingModel> response) {
                try {

                    if (response.code() >= 200 && response.code() <= 210) {
                        if (response.body().getSuccess().equalsIgnoreCase("true")) {
                            tv_decendent_name.setText(response.body().getData().getDecendantName());
                            tv_pickup_location.setText(pickup_location);
                            tv_drop_location.setText(response.body().getData().getTransferredToAddress());
                            String rq_accept = response.body().getData().getRequestAcceptedTitme();
                            String rq_pickupLocation = response.body().getData().getmReachedOnDecendantPickupLocationTime();
                            String rq_pickup = response.body().getData().getmPickupDecendentTime();
                            String rq_drop = response.body().getData().getDropDecendantTime();
                            if(rq_accept!=null){
                                iv_request_accepted.setImageResource(R.drawable.circle_fill);
                                tv_request_accepted.setText(rq_accept);

                            }
                            if(rq_pickup!=null){
                                iv_request_accepted.setImageResource(R.drawable.circle_fill);
                                tv_request_accepted.setText(rq_accept);
                                iv_flower_pickup.setImageResource(R.drawable.circle_fill);
                                tv_flower_pickup.setText(rq_pickup);
                            }
                            if(rq_drop!=null){
                                iv_request_accepted.setImageResource(R.drawable.circle_fill);
                                tv_request_accepted.setText(rq_accept);
                                iv_flower_pickup.setImageResource(R.drawable.circle_fill);
                                tv_flower_pickup.setText(rq_pickup);
                                iv_on_the_way.setImageResource(R.drawable.circle_fill);
                                iv_flower_deliver.setImageResource(R.drawable.circle_fill);
                                tv_flower_deliver.setText(rq_drop);
                            }

/*
                            if(response.body().getData().getSteps().equals("1")){
                                iv_request_accepted.setImageResource(R.drawable.circle_fill);
                                tv_request_accepted.setText(response.body().getData().getRequestAcceptedTitme().toString());
                            }else if(response.body().getData().getSteps().equals("2")){
                                iv_request_accepted.setImageResource(R.drawable.circle_fill);
                                iv_flower_pickup.setImageResource(R.drawable.circle_fill);
                                tv_request_accepted.setText(response.body().getData().getRequestAcceptedTitme().toString());
                                tv_flower_pickup.setText(response.body().getData().getFlowerPickupTime().toString());

                            }else if(response.body().getData().getSteps().equals("3")){
                                iv_request_accepted.setImageResource(R.drawable.circle_fill);
                                iv_flower_pickup.setImageResource(R.drawable.circle_fill);
                                iv_on_the_way.setImageResource(R.drawable.circle_fill);
                                tv_request_accepted.setText(response.body().getData().getRequestAcceptedTitme().toString());
                                tv_flower_pickup.setText(response.body().getData().getFlowerPickupTime().toString());

                            }else if(response.body().getData().getSteps().equals("4")){
                                iv_request_accepted.setImageResource(R.drawable.circle_fill);
                                iv_flower_pickup.setImageResource(R.drawable.circle_fill);
                                iv_on_the_way.setImageResource(R.drawable.circle_fill);
                                iv_flower_deliver.setImageResource(R.drawable.circle_fill);
                                tv_request_accepted.setText(response.body().getData().getRequestAcceptedTitme().toString());
                                tv_flower_pickup.setText(response.body().getData().getFlowerPickupTime().toString());
                                tv_flower_deliver.setText(response.body().getData().getFlowerDropTime().toString());

                            }
*/
                           // tv_drop_location.setText(response.body().getData().getTransferedToAddress());
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
            public void onFailure(Call<FlowerTrackingModel> call, Throwable t) {


            }
        });
    }

}