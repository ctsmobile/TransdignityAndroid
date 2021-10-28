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

import com.transdignity.deathserviceprovider.R;
import com.transdignity.deathserviceprovider.fragments.ServicesDetailsFragments.AirportRemovalDetailsFragment;
import com.transdignity.deathserviceprovider.models.auth.LoginResponse;
import com.transdignity.deathserviceprovider.models.requestDetails.Data;
import com.transdignity.deathserviceprovider.models.tracking.AirportTrackingModel;
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

public class AirportRemovalTrackFragment extends Fragment {
    Context context;
    Activity activity;
    Data data;
    LoginResponse loginResponse;
    String id;
    View view;
    TextView tv_decendent_name,tv_pickup_location,tv_drop_location;
    TextView tv_request_accepted_time,tv_removal_specialist_assigned_time,
            tv_driver_assigned_time,tv_driver_reached_on_specialist_location_time,
            tv_pickup_removal_specialist_time,tv_reached_on_decendent_pickup_location,
            tv_drop_decendent_to_their_drop_location_time;
    ImageView iv_request_accepted,iv_removal_specialist_assigned_time,
            iv_driver_assigned_time,iv_driver_reached_on_specialist_location_time,
            iv_pickup_removal_specialist_time,iv_reached_on_decendent_pickup_location,
            iv_drop_decendent_to_their_drop_location_time;

    ImageView iv_descendant_pickup,iv_removal_specialist_assigned_at_airport,iv_driver_assigned_at_airport,
            iv_driver_reached_on_specialist_location_at_airport,iv_pickup_removal_specialist_at_airport,
            iv_reached_on_decendent_pickup_location_at_airport,iv_descendant_pickup_at_airport,
            iv_drop_decendent_to_their_drop_location_at_airport;
    TextView tv_descendant_pickup,tv_removal_specialist_assigned_at_airport,tv_driver_assigned_at_airport,
            tv_driver_reached_on_specialist_location_at_airport,tv_pickup_removal_specialist_at_airport,
            tv_reached_on_decendent_pickup_location_at_airport,tv_descendant_pickup_at_airport,
            tv_drop_decendent_to_their_drop_location_at_airport;
    TextView tv_back;
    public AirportRemovalTrackFragment() {
        // Required empty public constructor
    }
    public static AirportRemovalTrackFragment newInstance(String id) {
        AirportRemovalTrackFragment fragment = new AirportRemovalTrackFragment();
        fragment.id = id;

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
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_airport_removal_track, container, false);
        loginResponse = (LoginResponse) GlobalValues.getPreferenceManager().getObject(PreferenceManager.LoginResponseKey, LoginResponse.class);
        init();
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = AirportRemovalDetailsFragment.newInstance(id,"Home");
                String tagFragment = fragment.getClass().getName();
                GlobalValues.getInstance().setFramgentTag(tagFragment);
                CommonUtils.loadFragment(context, fragment, tagFragment);
            }
        });
        return view;
    }

    public void init(){
        tv_back=view.findViewById(R.id.tv_back);
        tv_decendent_name=view.findViewById(R.id.tv_decendent_name);
        tv_pickup_location=view.findViewById(R.id.tv_pickup_location);
        tv_drop_location=view.findViewById(R.id.tv_drop_location);
        tv_request_accepted_time=view.findViewById(R.id.tv_request_accepted_time);
        tv_removal_specialist_assigned_time=view.findViewById(R.id.tv_removal_specialist_assigned_time);
        tv_driver_assigned_time=view.findViewById(R.id.tv_driver_assigned_time);
        tv_driver_reached_on_specialist_location_time=view.findViewById(R.id.tv_driver_reached_on_specialist_location_time);
        tv_pickup_removal_specialist_time=view.findViewById(R.id.tv_pickup_removal_specialist_time);
        tv_reached_on_decendent_pickup_location=view.findViewById(R.id.tv_reached_on_decendent_pickup_location);
        tv_drop_decendent_to_their_drop_location_time=view.findViewById(R.id.tv_drop_decendent_to_their_drop_location_time);
        tv_descendant_pickup=view.findViewById(R.id.tv_descendant_pickup);
        tv_removal_specialist_assigned_at_airport=view.findViewById(R.id.tv_removal_specialist_assigned_at_airport);
        tv_driver_assigned_at_airport=view.findViewById(R.id.tv_driver_assigned_at_airport);
        tv_driver_reached_on_specialist_location_at_airport=view.findViewById(R.id.tv_driver_reached_on_specialist_location_at_airport);
        tv_pickup_removal_specialist_at_airport=view.findViewById(R.id.tv_pickup_removal_specialist_at_airport);
        tv_reached_on_decendent_pickup_location_at_airport=view.findViewById(R.id.tv_reached_on_decendent_pickup_location_at_airport);
        tv_descendant_pickup_at_airport=view.findViewById(R.id.tv_descendant_pickup_at_airport);
        tv_drop_decendent_to_their_drop_location_at_airport=view.findViewById(R.id.tv_drop_decendent_to_their_drop_location_at_airport);

        iv_request_accepted=view.findViewById(R.id.iv_request_accepted);
        iv_removal_specialist_assigned_time=view.findViewById(R.id.iv_removal_specialist_assigned_time);
        iv_driver_assigned_time=view.findViewById(R.id.iv_driver_assigned_time);
        iv_driver_reached_on_specialist_location_time=view.findViewById(R.id.iv_driver_reached_on_specialist_location_time);
        iv_pickup_removal_specialist_time=view.findViewById(R.id.iv_pickup_removal_specialist_time);
        iv_reached_on_decendent_pickup_location=view.findViewById(R.id.iv_reached_on_decendent_pickup_location);
        iv_drop_decendent_to_their_drop_location_time=view.findViewById(R.id.iv_drop_decendent_to_their_drop_location_time);
        iv_descendant_pickup=view.findViewById(R.id.iv_descendant_pickup);
        iv_removal_specialist_assigned_at_airport=view.findViewById(R.id.iv_removal_specialist_assigned_at_airport);
        iv_driver_assigned_at_airport=view.findViewById(R.id.iv_driver_assigned_at_airport);
        iv_driver_reached_on_specialist_location_at_airport=view.findViewById(R.id.iv_driver_reached_on_specialist_location_at_airport);
        iv_pickup_removal_specialist_at_airport=view.findViewById(R.id.iv_pickup_removal_specialist_at_airport);
        iv_reached_on_decendent_pickup_location_at_airport=view.findViewById(R.id.iv_reached_on_decendent_pickup_location_at_airport);
        iv_descendant_pickup_at_airport=view.findViewById(R.id.iv_descendant_pickup_at_airport);
        iv_drop_decendent_to_their_drop_location_at_airport=view.findViewById(R.id.iv_drop_decendent_to_their_drop_location_at_airport);


        trackStatus();
    }

    void trackStatus() {
        ApiInterface apiInterface = ApiClients.getClient(context).create(ApiInterface.class);
        Call<AirportTrackingModel> call = apiInterface.trackAirportStatusApi(loginResponse.getData().getToken(), id);
        call.enqueue(new Callback<AirportTrackingModel>() {
            @Override
            public void onResponse(Call<AirportTrackingModel> call, Response<AirportTrackingModel> response) {
                try {

                    if (response.code() >= 200 && response.code() <= 210) {
                        if (response.body().getSuccess().equalsIgnoreCase("true")) {
                            tv_decendent_name.setText(response.body().getData().getDecendantName());
                            tv_pickup_location.setText(response.body().getData().getRemovedFromAddress());
                            tv_drop_location.setText(response.body().getData().getTransferredToAddress());

                            String removal_specialist_assign_time=response.body().getData().getRemovalSpecialistsAssignTime();
                            String cab_driver_assign_time=response.body().getData().getCabDriverAssignTime();
                            String reached_on_rs_location_time=response.body().getData().getReachedOnRsLocationTime();
                            String pickup_rs_time=response.body().getData().getPickupRsTime();
                            String reached_on_decendant_pickup_location_time=response.body().getData().getReachedOnDecendantPickupLocationTime();
                            String pickup_decendent_time=response.body().getData().getPickupDecendentTime();
                            String drop_airport=response.body().getData().getDropAirportTime();

                            String rs_assign_airport_time=response.body().getData().getRemovalAssignTimeAirpoert();
                            String cab_driver_assign_time_from_airport=response.body().getData().getCabDriverAssignTimeAirpoert();
                            String reached_on_rs_location_time_airport=response.body().getData().getReachedOnRsLocationTimeAirport();
                            String pickup_rs_time_airport=response.body().getData().getPickupRsTimeAirport();
                            String reached_on_decendant_pickup_location_at_airport=response.body().getData().getReachedOnAirportPickupLocationTime();
                            String pickup_decendent_at_airport=response.body().getData().getPickupDecendentTimeAirport();
                            String drop_decendant_time=response.body().getData().getDropDecendantTime();

                            if(removal_specialist_assign_time!=null){
                                iv_request_accepted.setImageResource(R.drawable.circle_fill);
                                iv_removal_specialist_assigned_time.setImageResource(R.drawable.circle_fill);
                                tv_removal_specialist_assigned_time.setText(removal_specialist_assign_time);
                            }
                            if(cab_driver_assign_time!=null){
                                iv_request_accepted.setImageResource(R.drawable.circle_fill);
                                iv_removal_specialist_assigned_time.setImageResource(R.drawable.circle_fill);
                                tv_removal_specialist_assigned_time.setText(removal_specialist_assign_time);
                                iv_driver_assigned_time.setImageResource(R.drawable.circle_fill);
                                tv_driver_assigned_time.setText(cab_driver_assign_time);
                            }
                            if(reached_on_rs_location_time!=null){
                                iv_request_accepted.setImageResource(R.drawable.circle_fill);
                                iv_removal_specialist_assigned_time.setImageResource(R.drawable.circle_fill);
                                tv_removal_specialist_assigned_time.setText(removal_specialist_assign_time);
                                iv_driver_assigned_time.setImageResource(R.drawable.circle_fill);
                                tv_driver_assigned_time.setText(cab_driver_assign_time);
                                iv_driver_reached_on_specialist_location_time.setImageResource(R.drawable.circle_fill);
                                tv_driver_reached_on_specialist_location_time.setText(reached_on_rs_location_time);
                            }
                            if(pickup_rs_time!=null){
                                iv_request_accepted.setImageResource(R.drawable.circle_fill);
                                iv_removal_specialist_assigned_time.setImageResource(R.drawable.circle_fill);
                                tv_removal_specialist_assigned_time.setText(removal_specialist_assign_time);
                                iv_driver_assigned_time.setImageResource(R.drawable.circle_fill);
                                tv_driver_assigned_time.setText(cab_driver_assign_time);
                                iv_driver_reached_on_specialist_location_time.setImageResource(R.drawable.circle_fill);
                                tv_driver_reached_on_specialist_location_time.setText(reached_on_rs_location_time);
                                iv_pickup_removal_specialist_time.setImageResource(R.drawable.circle_fill);
                                tv_pickup_removal_specialist_time.setText(pickup_rs_time);

                            }
                            if(reached_on_decendant_pickup_location_time!=null){
                                iv_request_accepted.setImageResource(R.drawable.circle_fill);
                                iv_removal_specialist_assigned_time.setImageResource(R.drawable.circle_fill);
                                tv_removal_specialist_assigned_time.setText(removal_specialist_assign_time);
                                iv_driver_assigned_time.setImageResource(R.drawable.circle_fill);
                                tv_driver_assigned_time.setText(cab_driver_assign_time);
                                iv_driver_reached_on_specialist_location_time.setImageResource(R.drawable.circle_fill);
                                tv_driver_reached_on_specialist_location_time.setText(reached_on_rs_location_time);
                                iv_pickup_removal_specialist_time.setImageResource(R.drawable.circle_fill);
                                tv_pickup_removal_specialist_time.setText(pickup_rs_time);
                                iv_reached_on_decendent_pickup_location.setImageResource(R.drawable.circle_fill);
                                tv_reached_on_decendent_pickup_location.setText(reached_on_decendant_pickup_location_time);

                            }
                            if(pickup_decendent_time!=null){
                                iv_request_accepted.setImageResource(R.drawable.circle_fill);
                                iv_removal_specialist_assigned_time.setImageResource(R.drawable.circle_fill);
                                tv_removal_specialist_assigned_time.setText(removal_specialist_assign_time);
                                iv_driver_assigned_time.setImageResource(R.drawable.circle_fill);
                                tv_driver_assigned_time.setText(cab_driver_assign_time);
                                iv_driver_reached_on_specialist_location_time.setImageResource(R.drawable.circle_fill);
                                tv_driver_reached_on_specialist_location_time.setText(reached_on_rs_location_time);
                                iv_pickup_removal_specialist_time.setImageResource(R.drawable.circle_fill);
                                tv_pickup_removal_specialist_time.setText(pickup_rs_time);
                                iv_reached_on_decendent_pickup_location.setImageResource(R.drawable.circle_fill);
                                tv_reached_on_decendent_pickup_location.setText(reached_on_decendant_pickup_location_time);
                                iv_descendant_pickup.setImageResource(R.drawable.circle_fill);
                                tv_descendant_pickup.setText(pickup_decendent_time);

                            }
                            if(drop_airport!=null){
                                iv_request_accepted.setImageResource(R.drawable.circle_fill);
                                iv_removal_specialist_assigned_time.setImageResource(R.drawable.circle_fill);
                                tv_removal_specialist_assigned_time.setText(removal_specialist_assign_time);
                                iv_driver_assigned_time.setImageResource(R.drawable.circle_fill);
                                tv_driver_assigned_time.setText(cab_driver_assign_time);
                                iv_driver_reached_on_specialist_location_time.setImageResource(R.drawable.circle_fill);
                                tv_driver_reached_on_specialist_location_time.setText(reached_on_rs_location_time);
                                iv_pickup_removal_specialist_time.setImageResource(R.drawable.circle_fill);
                                tv_pickup_removal_specialist_time.setText(pickup_rs_time);
                                iv_reached_on_decendent_pickup_location.setImageResource(R.drawable.circle_fill);
                                tv_reached_on_decendent_pickup_location.setText(reached_on_decendant_pickup_location_time);
                                tv_descendant_pickup.setText(pickup_decendent_time);
                                iv_drop_decendent_to_their_drop_location_time.setImageResource(R.drawable.circle_fill);
                                iv_descendant_pickup.setImageResource(R.drawable.circle_fill);
                                tv_drop_decendent_to_their_drop_location_time.setText(drop_airport);

                            }
                            if(rs_assign_airport_time!=null){
                                iv_request_accepted.setImageResource(R.drawable.circle_fill);
                                iv_removal_specialist_assigned_time.setImageResource(R.drawable.circle_fill);
                                tv_removal_specialist_assigned_time.setText(removal_specialist_assign_time);
                                iv_driver_assigned_time.setImageResource(R.drawable.circle_fill);
                                tv_driver_assigned_time.setText(cab_driver_assign_time);
                                iv_driver_reached_on_specialist_location_time.setImageResource(R.drawable.circle_fill);
                                tv_driver_reached_on_specialist_location_time.setText(reached_on_rs_location_time);
                                iv_pickup_removal_specialist_time.setImageResource(R.drawable.circle_fill);
                                tv_pickup_removal_specialist_time.setText(pickup_rs_time);
                                iv_reached_on_decendent_pickup_location.setImageResource(R.drawable.circle_fill);
                                tv_reached_on_decendent_pickup_location.setText(reached_on_decendant_pickup_location_time);
                                iv_descendant_pickup.setImageResource(R.drawable.circle_fill);
                                tv_drop_decendent_to_their_drop_location_time.setText(drop_decendant_time);
                                iv_removal_specialist_assigned_at_airport.setImageResource(R.drawable.circle_fill);
                                tv_descendant_pickup.setText(pickup_decendent_time);
                                tv_removal_specialist_assigned_at_airport.setText(rs_assign_airport_time);

                            }
                            if(cab_driver_assign_time_from_airport!=null){
                                iv_request_accepted.setImageResource(R.drawable.circle_fill);
                                iv_removal_specialist_assigned_time.setImageResource(R.drawable.circle_fill);
                                tv_removal_specialist_assigned_time.setText(removal_specialist_assign_time);
                                iv_driver_assigned_time.setImageResource(R.drawable.circle_fill);
                                tv_driver_assigned_time.setText(cab_driver_assign_time);
                                iv_driver_reached_on_specialist_location_time.setImageResource(R.drawable.circle_fill);
                                tv_driver_reached_on_specialist_location_time.setText(reached_on_rs_location_time);
                                iv_pickup_removal_specialist_time.setImageResource(R.drawable.circle_fill);
                                tv_pickup_removal_specialist_time.setText(pickup_rs_time);
                                iv_reached_on_decendent_pickup_location.setImageResource(R.drawable.circle_fill);
                                tv_reached_on_decendent_pickup_location.setText(reached_on_decendant_pickup_location_time);
                                iv_descendant_pickup.setImageResource(R.drawable.circle_fill);
                                tv_drop_decendent_to_their_drop_location_time.setText(drop_decendant_time);
                                iv_removal_specialist_assigned_at_airport.setImageResource(R.drawable.circle_fill);
                                tv_descendant_pickup.setText(pickup_decendent_time);
                                tv_removal_specialist_assigned_at_airport.setText(rs_assign_airport_time);
                                iv_drop_decendent_to_their_drop_location_time.setImageResource(R.drawable.circle_fill);
                                iv_driver_assigned_at_airport.setImageResource(R.drawable.circle_fill);
                                tv_driver_assigned_at_airport.setText(cab_driver_assign_time_from_airport);

                            }
                            if(reached_on_rs_location_time_airport!=null){
                                iv_request_accepted.setImageResource(R.drawable.circle_fill);
                                iv_removal_specialist_assigned_time.setImageResource(R.drawable.circle_fill);
                                tv_removal_specialist_assigned_time.setText(removal_specialist_assign_time);
                                iv_driver_assigned_time.setImageResource(R.drawable.circle_fill);
                                tv_driver_assigned_time.setText(cab_driver_assign_time);
                                iv_driver_reached_on_specialist_location_time.setImageResource(R.drawable.circle_fill);
                                tv_driver_reached_on_specialist_location_time.setText(reached_on_rs_location_time);
                                iv_pickup_removal_specialist_time.setImageResource(R.drawable.circle_fill);
                                tv_pickup_removal_specialist_time.setText(pickup_rs_time);
                                iv_reached_on_decendent_pickup_location.setImageResource(R.drawable.circle_fill);
                                tv_reached_on_decendent_pickup_location.setText(reached_on_decendant_pickup_location_time);
                                iv_descendant_pickup.setImageResource(R.drawable.circle_fill);
                                tv_drop_decendent_to_their_drop_location_time.setText(drop_decendant_time);
                                iv_removal_specialist_assigned_at_airport.setImageResource(R.drawable.circle_fill);
                                tv_descendant_pickup.setText(pickup_decendent_time);
                                tv_removal_specialist_assigned_at_airport.setText(rs_assign_airport_time);
                                iv_drop_decendent_to_their_drop_location_time.setImageResource(R.drawable.circle_fill);
                                iv_driver_assigned_at_airport.setImageResource(R.drawable.circle_fill);
                                tv_driver_assigned_at_airport.setText(cab_driver_assign_time_from_airport);
                                iv_driver_reached_on_specialist_location_at_airport.setImageResource(R.drawable.circle_fill);
                                tv_driver_reached_on_specialist_location_at_airport.setText(reached_on_rs_location_time_airport);

                            }
                            if(pickup_rs_time_airport!=null){
                                iv_request_accepted.setImageResource(R.drawable.circle_fill);
                                iv_removal_specialist_assigned_time.setImageResource(R.drawable.circle_fill);
                                tv_removal_specialist_assigned_time.setText(removal_specialist_assign_time);
                                iv_driver_assigned_time.setImageResource(R.drawable.circle_fill);
                                tv_driver_assigned_time.setText(cab_driver_assign_time);
                                iv_driver_reached_on_specialist_location_time.setImageResource(R.drawable.circle_fill);
                                tv_driver_reached_on_specialist_location_time.setText(reached_on_rs_location_time);
                                iv_pickup_removal_specialist_time.setImageResource(R.drawable.circle_fill);
                                tv_pickup_removal_specialist_time.setText(pickup_rs_time);
                                iv_reached_on_decendent_pickup_location.setImageResource(R.drawable.circle_fill);
                                tv_reached_on_decendent_pickup_location.setText(reached_on_decendant_pickup_location_time);
                                iv_descendant_pickup.setImageResource(R.drawable.circle_fill);
                                tv_drop_decendent_to_their_drop_location_time.setText(drop_decendant_time);
                                iv_removal_specialist_assigned_at_airport.setImageResource(R.drawable.circle_fill);
                                tv_descendant_pickup.setText(pickup_decendent_time);
                                tv_removal_specialist_assigned_at_airport.setText(rs_assign_airport_time);
                                iv_drop_decendent_to_their_drop_location_time.setImageResource(R.drawable.circle_fill);
                                iv_driver_assigned_at_airport.setImageResource(R.drawable.circle_fill);
                                tv_driver_assigned_at_airport.setText(cab_driver_assign_time_from_airport);
                                iv_driver_reached_on_specialist_location_at_airport.setImageResource(R.drawable.circle_fill);
                                tv_driver_reached_on_specialist_location_at_airport.setText(reached_on_rs_location_time_airport);
                                iv_pickup_removal_specialist_at_airport.setImageResource(R.drawable.circle_fill);
                                tv_pickup_removal_specialist_at_airport.setText(pickup_rs_time_airport);

                            }
                            if(reached_on_decendant_pickup_location_at_airport!=null){
                                iv_request_accepted.setImageResource(R.drawable.circle_fill);
                                iv_removal_specialist_assigned_time.setImageResource(R.drawable.circle_fill);
                                tv_removal_specialist_assigned_time.setText(removal_specialist_assign_time);
                                iv_driver_assigned_time.setImageResource(R.drawable.circle_fill);
                                tv_driver_assigned_time.setText(cab_driver_assign_time);
                                iv_driver_reached_on_specialist_location_time.setImageResource(R.drawable.circle_fill);
                                tv_driver_reached_on_specialist_location_time.setText(reached_on_rs_location_time);
                                iv_pickup_removal_specialist_time.setImageResource(R.drawable.circle_fill);
                                tv_pickup_removal_specialist_time.setText(pickup_rs_time);
                                iv_reached_on_decendent_pickup_location.setImageResource(R.drawable.circle_fill);
                                tv_reached_on_decendent_pickup_location.setText(reached_on_decendant_pickup_location_time);
                                iv_descendant_pickup.setImageResource(R.drawable.circle_fill);
                                tv_drop_decendent_to_their_drop_location_time.setText(drop_decendant_time);
                                iv_removal_specialist_assigned_at_airport.setImageResource(R.drawable.circle_fill);
                                tv_descendant_pickup.setText(pickup_decendent_time);
                                tv_removal_specialist_assigned_at_airport.setText(rs_assign_airport_time);
                                iv_drop_decendent_to_their_drop_location_time.setImageResource(R.drawable.circle_fill);
                                iv_driver_assigned_at_airport.setImageResource(R.drawable.circle_fill);
                                tv_driver_assigned_at_airport.setText(cab_driver_assign_time_from_airport);
                                iv_driver_reached_on_specialist_location_at_airport.setImageResource(R.drawable.circle_fill);
                                tv_driver_reached_on_specialist_location_at_airport.setText(reached_on_rs_location_time_airport);
                                iv_pickup_removal_specialist_at_airport.setImageResource(R.drawable.circle_fill);
                                tv_pickup_removal_specialist_at_airport.setText(pickup_rs_time_airport);
                                iv_reached_on_decendent_pickup_location_at_airport.setImageResource(R.drawable.circle_fill);
                                tv_reached_on_decendent_pickup_location_at_airport.setText(reached_on_decendant_pickup_location_at_airport);

                            }
                            if(pickup_decendent_at_airport!=null){
                                iv_request_accepted.setImageResource(R.drawable.circle_fill);
                                iv_removal_specialist_assigned_time.setImageResource(R.drawable.circle_fill);
                                tv_removal_specialist_assigned_time.setText(removal_specialist_assign_time);
                                iv_driver_assigned_time.setImageResource(R.drawable.circle_fill);
                                tv_driver_assigned_time.setText(cab_driver_assign_time);
                                iv_driver_reached_on_specialist_location_time.setImageResource(R.drawable.circle_fill);
                                tv_driver_reached_on_specialist_location_time.setText(reached_on_rs_location_time);
                                iv_pickup_removal_specialist_time.setImageResource(R.drawable.circle_fill);
                                tv_pickup_removal_specialist_time.setText(pickup_rs_time);
                                iv_reached_on_decendent_pickup_location.setImageResource(R.drawable.circle_fill);
                                tv_reached_on_decendent_pickup_location.setText(reached_on_decendant_pickup_location_time);
                                iv_descendant_pickup.setImageResource(R.drawable.circle_fill);
                                tv_drop_decendent_to_their_drop_location_time.setText(drop_decendant_time);
                                iv_removal_specialist_assigned_at_airport.setImageResource(R.drawable.circle_fill);
                                tv_descendant_pickup.setText(pickup_decendent_time);
                                tv_removal_specialist_assigned_at_airport.setText(rs_assign_airport_time);
                                iv_drop_decendent_to_their_drop_location_time.setImageResource(R.drawable.circle_fill);
                                iv_driver_assigned_at_airport.setImageResource(R.drawable.circle_fill);
                                tv_driver_assigned_at_airport.setText(cab_driver_assign_time_from_airport);
                                iv_driver_reached_on_specialist_location_at_airport.setImageResource(R.drawable.circle_fill);
                                tv_driver_reached_on_specialist_location_at_airport.setText(reached_on_rs_location_time_airport);
                                iv_pickup_removal_specialist_at_airport.setImageResource(R.drawable.circle_fill);
                                tv_pickup_removal_specialist_at_airport.setText(pickup_rs_time_airport);
                                iv_reached_on_decendent_pickup_location_at_airport.setImageResource(R.drawable.circle_fill);
                                tv_reached_on_decendent_pickup_location_at_airport.setText(reached_on_decendant_pickup_location_at_airport);
                                iv_descendant_pickup_at_airport.setImageResource(R.drawable.circle_fill);
                                tv_descendant_pickup_at_airport.setText(pickup_decendent_at_airport);

                            }
                            if(drop_decendant_time!=null){
                                iv_request_accepted.setImageResource(R.drawable.circle_fill);
                                iv_removal_specialist_assigned_time.setImageResource(R.drawable.circle_fill);
                                tv_removal_specialist_assigned_time.setText(removal_specialist_assign_time);
                                iv_driver_assigned_time.setImageResource(R.drawable.circle_fill);
                                tv_driver_assigned_time.setText(cab_driver_assign_time);
                                iv_driver_reached_on_specialist_location_time.setImageResource(R.drawable.circle_fill);
                                tv_driver_reached_on_specialist_location_time.setText(reached_on_rs_location_time);
                                iv_pickup_removal_specialist_time.setImageResource(R.drawable.circle_fill);
                                tv_pickup_removal_specialist_time.setText(pickup_rs_time);
                                iv_reached_on_decendent_pickup_location.setImageResource(R.drawable.circle_fill);
                                tv_reached_on_decendent_pickup_location.setText(reached_on_decendant_pickup_location_time);
                                iv_descendant_pickup.setImageResource(R.drawable.circle_fill);
                                tv_drop_decendent_to_their_drop_location_time.setText(drop_decendant_time);
                                iv_removal_specialist_assigned_at_airport.setImageResource(R.drawable.circle_fill);
                                tv_descendant_pickup.setText(pickup_decendent_time);
                                tv_removal_specialist_assigned_at_airport.setText(rs_assign_airport_time);
                                iv_drop_decendent_to_their_drop_location_time.setImageResource(R.drawable.circle_fill);
                                iv_driver_assigned_at_airport.setImageResource(R.drawable.circle_fill);
                                tv_driver_assigned_at_airport.setText(cab_driver_assign_time_from_airport);
                                iv_driver_reached_on_specialist_location_at_airport.setImageResource(R.drawable.circle_fill);
                                tv_driver_reached_on_specialist_location_at_airport.setText(reached_on_rs_location_time_airport);
                                iv_pickup_removal_specialist_at_airport.setImageResource(R.drawable.circle_fill);
                                tv_pickup_removal_specialist_at_airport.setText(pickup_rs_time_airport);
                                iv_reached_on_decendent_pickup_location_at_airport.setImageResource(R.drawable.circle_fill);
                                tv_reached_on_decendent_pickup_location_at_airport.setText(reached_on_decendant_pickup_location_at_airport);
                                iv_descendant_pickup_at_airport.setImageResource(R.drawable.circle_fill);
                                tv_descendant_pickup_at_airport.setText(pickup_decendent_at_airport);
                                iv_drop_decendent_to_their_drop_location_at_airport.setImageResource(R.drawable.circle_fill);
                                tv_drop_decendent_to_their_drop_location_at_airport.setText(drop_decendant_time);

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
            public void onFailure(Call<AirportTrackingModel> call, Throwable t) {


            }
        });
    }

}