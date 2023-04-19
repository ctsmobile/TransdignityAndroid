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
import com.transdignity.deathserviceprovider.fragments.ServicesDetailsFragments.BurialSeaDetailFragment;
import com.transdignity.deathserviceprovider.fragments.ServicesDetailsFragments.FlowerDetailPageFragment;
import com.transdignity.deathserviceprovider.models.auth.LoginResponse;
import com.transdignity.deathserviceprovider.models.tracking.BurialTrackingModel;
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


public class BurialAtSeaTrackingFragment extends Fragment {
    Context context;
    Activity activity;
    LoginResponse loginResponse;
    String id;
    View view;
    TextView tv_decendent_name,tv_pickup_location,tv_drop_location,tv_back;
    ImageView iv_request_accepted,iv_removal_specialist_assigned,iv_driver_assigned,
            iv_driver_reached_on_removal_specialist_location,im_pick_up_removal_specialist,
            im_reached_on_burial_body_pick_up_location,im_body_transfer_to_drop_location,im_request_completed;
    ImageView im_boat_provider_assigned,im_driver_reached_at_boat_provider_location,im_pick_up_human_remains,
            im_burial_of_the_human_remains_in_the_sea;

    TextView tv_request_accepted,tv_removal_specialist_assigned,tv_driver_assigned,
            tv_driver_reached_on_removal_specialist_location,tv_pick_up_removal_specialist,
            tv_reached_on_us_veteran_body_pick_up_location,tv_body_transfer_to_drop_location,tv_request_completed;
    TextView tv_boat_provider_assigned,tv_driver_reached_on_boat_provider_location,tv_pick_up_human_remains,
            tv_burial_of_the_human_remains_in_the_sea;


    public BurialAtSeaTrackingFragment() {
        // Required empty public constructor
    }


    public static BurialAtSeaTrackingFragment newInstance(String id) {
        BurialAtSeaTrackingFragment fragment = new BurialAtSeaTrackingFragment();
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
        view= inflater.inflate(R.layout.fragment_burial_at_sea_tracking, container, false);
        loginResponse = (LoginResponse) GlobalValues.getPreferenceManager().getObject(PreferenceManager.LoginResponseKey, LoginResponse.class);
        init();
        return view;
    }

    public void init(){
        tv_decendent_name=view.findViewById(R.id.tv_decendent_name);
        tv_pickup_location=view.findViewById(R.id.tv_pickup_location);
        tv_drop_location=view.findViewById(R.id.tv_drop_location);
        tv_back=view.findViewById(R.id.tv_back);

        iv_request_accepted=view.findViewById(R.id.iv_request_accepted);
        iv_removal_specialist_assigned=view.findViewById(R.id.iv_removal_specialist_assigned);
        iv_driver_assigned=view.findViewById(R.id.iv_driver_assigned);
        iv_driver_reached_on_removal_specialist_location=view.findViewById(R.id.iv_driver_reached_on_removalspecialist_location);
        im_pick_up_removal_specialist=view.findViewById(R.id.im_pick_up_removal_specialist);
        im_reached_on_burial_body_pick_up_location=view.findViewById(R.id.im_reached_on_burial_body_pick_up_location);
        im_body_transfer_to_drop_location=view.findViewById(R.id.im_body_transfer_to_drop_location);
        im_boat_provider_assigned=view.findViewById(R.id.im_boat_provider_assigned);
        im_driver_reached_at_boat_provider_location=view.findViewById(R.id.im_driver_reached_at_boat_provider_location);
        im_pick_up_human_remains=view.findViewById(R.id.im_pick_up_human_remains);
        im_burial_of_the_human_remains_in_the_sea=view.findViewById(R.id.im_burial_of_the_human_remains_in_the_sea);
        im_request_completed=view.findViewById(R.id.im_request_completed);

        tv_request_accepted=view.findViewById(R.id.tv_request_accepted);
        tv_removal_specialist_assigned=view.findViewById(R.id.tv_removal_specialist_assigned);
        tv_driver_assigned=view.findViewById(R.id.tv_driver_assigned);
        tv_driver_reached_on_removal_specialist_location=view.findViewById(R.id.tv_driver_reached_on_removal_specialist_location);
        tv_pick_up_removal_specialist=view.findViewById(R.id.tv_pick_up_removal_specialist);
        tv_reached_on_us_veteran_body_pick_up_location=view.findViewById(R.id.tv_reached_on_us_veteran_body_pick_up_location);
        tv_body_transfer_to_drop_location=view.findViewById(R.id.tv_body_transfer_to_drop_location);
        tv_boat_provider_assigned=view.findViewById(R.id.tv_boat_provider_assigned);
        tv_driver_reached_on_boat_provider_location=view.findViewById(R.id.tv_driver_reached_on_boat_provider_location);
        tv_pick_up_human_remains=view.findViewById(R.id.tv_pick_up_human_remains);
        tv_burial_of_the_human_remains_in_the_sea=view.findViewById(R.id.tv_burial_of_the_human_remains_in_the_sea);
        tv_request_completed=view.findViewById(R.id.tv_request_completed);
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment2;
                fragment2 =  BurialSeaDetailFragment.newInstance(id,"Home");
                String fragmtStatusTag = fragment2.getClass().getName();
                GlobalValues.getInstance().setFramgentTag(fragmtStatusTag);
                ((MainActivity) activity).selectedNavItem(R.id.navigation_home);
                CommonUtils.loadFragment(context, fragment2, fragmtStatusTag);
            }
        });
        trackStatus();
    }
    void trackStatus() {
        ApiInterface apiInterface = ApiClients.getClient(context).create(ApiInterface.class);
        Call<BurialTrackingModel> call = apiInterface.trackBurialStatusApi(loginResponse.getData().getToken(), id);
        call.enqueue(new Callback<BurialTrackingModel>() {
            @Override
            public void onResponse(Call<BurialTrackingModel> call, Response<BurialTrackingModel> response) {
                try {

                    if (response.code() >= 200 && response.code() <= 210) {
                        if (response.body().getSuccess().equalsIgnoreCase("true")) {
                            tv_decendent_name.setText(response.body().getData().getDecendantName());
                            tv_pickup_location.setText(response.body().getData().getRemovedFromAddress());
                            tv_drop_location.setText(response.body().getData().getTransferredToAddress());
                            String removal_specialists_assign_time = response.body().getData().getRemovalSpecialistsAssignTime();
                            String cab_driver_assign_time = response.body().getData().getCabDriverAssignTime();
                            String reached_on_rs_location_time = response.body().getData().getReachedOnRsLocationTime();
                            String pickup_rs_time = response.body().getData().getPickupRsTime();
                            String reached_on_decendant_pickup_location_time = response.body().getData().getReachedOnDecendantPickupLocationTime();
                            String pickup_decendent_time = response.body().getData().getPickupDecendentTime();
                            String drop_decendant_time = response.body().getData().getDropDecendantTime();
                            String boat_driver_assign_time = response.body().getData().getBoatDriverAssignTime();
                            String boat_driver_reachedbody_time = response.body().getData().getBoatDriverReachedbodyTime();
                            String pickup_body_time_boat = response.body().getData().getPickupBodyTimeBoat();
                            String burial_time_insea = response.body().getData().getBurialTimeInsea();
                            String drop_decendant_time_boat = response.body().getData().getDropDecendantTimeBoat();
                            if(removal_specialists_assign_time!=null){
                                iv_removal_specialist_assigned.setImageResource(R.drawable.circle_fill);
                                tv_removal_specialist_assigned.setText(removal_specialists_assign_time);

                            }
                            if(cab_driver_assign_time!=null){
                                iv_removal_specialist_assigned.setImageResource(R.drawable.circle_fill);
                                tv_removal_specialist_assigned.setText(removal_specialists_assign_time);
                                iv_driver_assigned.setImageResource(R.drawable.circle_fill);
                                tv_driver_assigned.setText(cab_driver_assign_time);


                            }
                            if(reached_on_rs_location_time!=null){
                                iv_removal_specialist_assigned.setImageResource(R.drawable.circle_fill);
                                tv_removal_specialist_assigned.setText(removal_specialists_assign_time);
                                iv_driver_assigned.setImageResource(R.drawable.circle_fill);
                                tv_driver_assigned.setText(cab_driver_assign_time);
                                iv_driver_reached_on_removal_specialist_location.setImageResource(R.drawable.circle_fill);
                                tv_driver_reached_on_removal_specialist_location.setText(reached_on_rs_location_time);


                            }
                            if(pickup_rs_time!=null){
                                iv_removal_specialist_assigned.setImageResource(R.drawable.circle_fill);
                                tv_removal_specialist_assigned.setText(removal_specialists_assign_time);
                                iv_driver_assigned.setImageResource(R.drawable.circle_fill);
                                tv_driver_assigned.setText(cab_driver_assign_time);
                                iv_driver_reached_on_removal_specialist_location.setImageResource(R.drawable.circle_fill);
                                tv_driver_reached_on_removal_specialist_location.setText(reached_on_rs_location_time);
                                im_pick_up_removal_specialist.setImageResource(R.drawable.circle_fill);
                                tv_pick_up_removal_specialist.setText(pickup_rs_time);


                            }
                            if(reached_on_decendant_pickup_location_time!=null){
                                iv_removal_specialist_assigned.setImageResource(R.drawable.circle_fill);
                                tv_removal_specialist_assigned.setText(removal_specialists_assign_time);
                                iv_driver_assigned.setImageResource(R.drawable.circle_fill);
                                tv_driver_assigned.setText(cab_driver_assign_time);
                                iv_driver_reached_on_removal_specialist_location.setImageResource(R.drawable.circle_fill);
                                tv_driver_reached_on_removal_specialist_location.setText(reached_on_rs_location_time);
                                im_pick_up_removal_specialist.setImageResource(R.drawable.circle_fill);
                                tv_pick_up_removal_specialist.setText(pickup_rs_time);
                                im_reached_on_burial_body_pick_up_location.setImageResource(R.drawable.circle_fill);
                                tv_reached_on_us_veteran_body_pick_up_location.setText(reached_on_decendant_pickup_location_time);


                            }
                            if(drop_decendant_time!=null){
                                iv_removal_specialist_assigned.setImageResource(R.drawable.circle_fill);
                                tv_removal_specialist_assigned.setText(removal_specialists_assign_time);
                                iv_driver_assigned.setImageResource(R.drawable.circle_fill);
                                tv_driver_assigned.setText(cab_driver_assign_time);
                                iv_driver_reached_on_removal_specialist_location.setImageResource(R.drawable.circle_fill);
                                tv_driver_reached_on_removal_specialist_location.setText(reached_on_rs_location_time);
                                im_pick_up_removal_specialist.setImageResource(R.drawable.circle_fill);
                                tv_pick_up_removal_specialist.setText(pickup_rs_time);
                                im_reached_on_burial_body_pick_up_location.setImageResource(R.drawable.circle_fill);
                                tv_reached_on_us_veteran_body_pick_up_location.setText(reached_on_decendant_pickup_location_time);
                                im_body_transfer_to_drop_location.setImageResource(R.drawable.circle_fill);
                                tv_body_transfer_to_drop_location.setText(drop_decendant_time);


                            }
                            if(boat_driver_assign_time!=null){
                                iv_removal_specialist_assigned.setImageResource(R.drawable.circle_fill);
                                tv_removal_specialist_assigned.setText(removal_specialists_assign_time);
                                iv_driver_assigned.setImageResource(R.drawable.circle_fill);
                                tv_driver_assigned.setText(cab_driver_assign_time);
                                iv_driver_reached_on_removal_specialist_location.setImageResource(R.drawable.circle_fill);
                                tv_driver_reached_on_removal_specialist_location.setText(reached_on_rs_location_time);
                                im_pick_up_removal_specialist.setImageResource(R.drawable.circle_fill);
                                tv_pick_up_removal_specialist.setText(pickup_rs_time);
                                im_reached_on_burial_body_pick_up_location.setImageResource(R.drawable.circle_fill);
                                tv_reached_on_us_veteran_body_pick_up_location.setText(reached_on_decendant_pickup_location_time);
                                im_body_transfer_to_drop_location.setImageResource(R.drawable.circle_fill);
                                tv_body_transfer_to_drop_location.setText(drop_decendant_time);
                                im_boat_provider_assigned.setImageResource(R.drawable.circle_fill);
                                tv_boat_provider_assigned.setText(boat_driver_assign_time);

                            }
                            if(boat_driver_reachedbody_time!=null){
                                iv_removal_specialist_assigned.setImageResource(R.drawable.circle_fill);
                                tv_removal_specialist_assigned.setText(removal_specialists_assign_time);
                                iv_driver_assigned.setImageResource(R.drawable.circle_fill);
                                tv_driver_assigned.setText(cab_driver_assign_time);
                                iv_driver_reached_on_removal_specialist_location.setImageResource(R.drawable.circle_fill);
                                tv_driver_reached_on_removal_specialist_location.setText(reached_on_rs_location_time);
                                im_pick_up_removal_specialist.setImageResource(R.drawable.circle_fill);
                                tv_pick_up_removal_specialist.setText(pickup_rs_time);
                                im_reached_on_burial_body_pick_up_location.setImageResource(R.drawable.circle_fill);
                                tv_reached_on_us_veteran_body_pick_up_location.setText(reached_on_decendant_pickup_location_time);
                                im_body_transfer_to_drop_location.setImageResource(R.drawable.circle_fill);
                                tv_body_transfer_to_drop_location.setText(drop_decendant_time);
                                im_boat_provider_assigned.setImageResource(R.drawable.circle_fill);
                                tv_boat_provider_assigned.setText(boat_driver_assign_time);

                                im_driver_reached_at_boat_provider_location.setImageResource(R.drawable.circle_fill);
                                tv_driver_reached_on_boat_provider_location.setText(boat_driver_reachedbody_time);



                            }
                            if(pickup_body_time_boat!=null){
                                iv_removal_specialist_assigned.setImageResource(R.drawable.circle_fill);
                                tv_removal_specialist_assigned.setText(removal_specialists_assign_time);
                                iv_driver_assigned.setImageResource(R.drawable.circle_fill);
                                tv_driver_assigned.setText(cab_driver_assign_time);
                                iv_driver_reached_on_removal_specialist_location.setImageResource(R.drawable.circle_fill);
                                tv_driver_reached_on_removal_specialist_location.setText(reached_on_rs_location_time);
                                im_pick_up_removal_specialist.setImageResource(R.drawable.circle_fill);
                                tv_pick_up_removal_specialist.setText(pickup_rs_time);
                                im_reached_on_burial_body_pick_up_location.setImageResource(R.drawable.circle_fill);
                                tv_reached_on_us_veteran_body_pick_up_location.setText(reached_on_decendant_pickup_location_time);
                                im_body_transfer_to_drop_location.setImageResource(R.drawable.circle_fill);
                                tv_body_transfer_to_drop_location.setText(drop_decendant_time);
                                im_boat_provider_assigned.setImageResource(R.drawable.circle_fill);
                                tv_boat_provider_assigned.setText(boat_driver_assign_time);

                                im_driver_reached_at_boat_provider_location.setImageResource(R.drawable.circle_fill);
                                tv_driver_reached_on_boat_provider_location.setText(boat_driver_reachedbody_time);

                                im_pick_up_human_remains.setImageResource(R.drawable.circle_fill);
                                tv_pick_up_human_remains.setText(pickup_body_time_boat);

                                im_burial_of_the_human_remains_in_the_sea.setImageResource(R.drawable.circle_fill);
                                tv_burial_of_the_human_remains_in_the_sea.setText("");

                                im_request_completed.setImageResource(R.drawable.circle_fill);
                                tv_request_completed.setText(drop_decendant_time_boat);

                            }
                            if(burial_time_insea!=null){
                                iv_removal_specialist_assigned.setImageResource(R.drawable.circle_fill);
                                tv_removal_specialist_assigned.setText(removal_specialists_assign_time);
                                iv_driver_assigned.setImageResource(R.drawable.circle_fill);
                                tv_driver_assigned.setText(cab_driver_assign_time);
                                iv_driver_reached_on_removal_specialist_location.setImageResource(R.drawable.circle_fill);
                                tv_driver_reached_on_removal_specialist_location.setText(reached_on_rs_location_time);
                                im_pick_up_removal_specialist.setImageResource(R.drawable.circle_fill);
                                tv_pick_up_removal_specialist.setText(pickup_rs_time);
                                im_reached_on_burial_body_pick_up_location.setImageResource(R.drawable.circle_fill);
                                tv_reached_on_us_veteran_body_pick_up_location.setText(reached_on_decendant_pickup_location_time);
                                im_body_transfer_to_drop_location.setImageResource(R.drawable.circle_fill);
                                tv_body_transfer_to_drop_location.setText(drop_decendant_time);
                                im_boat_provider_assigned.setImageResource(R.drawable.circle_fill);
                                tv_boat_provider_assigned.setText(boat_driver_assign_time);

                                im_driver_reached_at_boat_provider_location.setImageResource(R.drawable.circle_fill);
                                tv_driver_reached_on_boat_provider_location.setText(boat_driver_reachedbody_time);

                                im_pick_up_human_remains.setImageResource(R.drawable.circle_fill);
                                tv_pick_up_human_remains.setText(pickup_body_time_boat);

                                im_burial_of_the_human_remains_in_the_sea.setImageResource(R.drawable.circle_fill);
                                tv_burial_of_the_human_remains_in_the_sea.setText(burial_time_insea);

                                im_request_completed.setImageResource(R.drawable.circle_fill);
                                tv_request_completed.setText(drop_decendant_time_boat);

                            }
                            if(drop_decendant_time_boat!=null){
                                iv_removal_specialist_assigned.setImageResource(R.drawable.circle_fill);
                                tv_removal_specialist_assigned.setText(removal_specialists_assign_time);
                                iv_driver_assigned.setImageResource(R.drawable.circle_fill);
                                tv_driver_assigned.setText(cab_driver_assign_time);
                                iv_driver_reached_on_removal_specialist_location.setImageResource(R.drawable.circle_fill);
                                tv_driver_reached_on_removal_specialist_location.setText(reached_on_rs_location_time);
                                im_pick_up_removal_specialist.setImageResource(R.drawable.circle_fill);
                                tv_pick_up_removal_specialist.setText(pickup_rs_time);
                                im_reached_on_burial_body_pick_up_location.setImageResource(R.drawable.circle_fill);
                                tv_reached_on_us_veteran_body_pick_up_location.setText(reached_on_decendant_pickup_location_time);
                                im_body_transfer_to_drop_location.setImageResource(R.drawable.circle_fill);
                                tv_body_transfer_to_drop_location.setText(drop_decendant_time);
                                im_boat_provider_assigned.setImageResource(R.drawable.circle_fill);
                                tv_boat_provider_assigned.setText(boat_driver_assign_time);

                                im_driver_reached_at_boat_provider_location.setImageResource(R.drawable.circle_fill);
                                tv_driver_reached_on_boat_provider_location.setText(boat_driver_reachedbody_time);

                                im_pick_up_human_remains.setImageResource(R.drawable.circle_fill);
                                tv_pick_up_human_remains.setText(pickup_body_time_boat);

                                im_burial_of_the_human_remains_in_the_sea.setImageResource(R.drawable.circle_fill);
                                tv_burial_of_the_human_remains_in_the_sea.setText(burial_time_insea);

                                im_request_completed.setImageResource(R.drawable.circle_fill);
                                tv_request_completed.setText(drop_decendant_time_boat);

                            }





                            //  tv_drop.setText(response.body().getData().getTransferedToAddress());

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
            public void onFailure(Call<BurialTrackingModel> call, Throwable t) {


            }
        });
    }

}