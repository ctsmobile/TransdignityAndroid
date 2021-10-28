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
import com.transdignity.deathserviceprovider.fragments.ServicesDetailsFragments.AshesDetiailsPageFragment;
import com.transdignity.deathserviceprovider.models.auth.LoginResponse;
import com.transdignity.deathserviceprovider.models.requestDetails.Data;
import com.transdignity.deathserviceprovider.models.tracking.AshesTrackingModel;
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


public class AshesTrackFragment extends Fragment {
    Context context;
    Activity activity;
    Data data;
    LoginResponse loginResponse;
    String id;
    View view;
    TextView tv_decendent_name,tv_pickup_location,tv_drop_location,tv_back;
    ImageView iv_requested_accepted,iv_reached_on_pickup_location,iv_ashes_pickup,
            iv_reached_on_drop_location,iv_ashes_drop_location_active,iv_removal_specialist_assign,
            iv_Driver_assign;
    TextView tv_requested_accepted,tv_reached_on_pickup_location,tv_ashes_pickup,
            tv_reached_on_drop_location,tv_ashes_drop_location_active,tv_removal_specialist_assign,
            tv_Driver_assign;
    public AshesTrackFragment() {
        // Required empty public constructor
    }
    public static AshesTrackFragment newInstance(String id) {
        AshesTrackFragment fragment = new AshesTrackFragment();
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
        view= inflater.inflate(R.layout.fragment_ashes_track, container, false);
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
        iv_requested_accepted=view.findViewById(R.id.iv_requested_accepted);
        iv_reached_on_pickup_location=view.findViewById(R.id.iv_reached_on_pickup_location);
        iv_ashes_pickup=view.findViewById(R.id.iv_ashes_pickup);
        iv_reached_on_drop_location=view.findViewById(R.id.iv_reached_on_drop_location);
        iv_ashes_drop_location_active=view.findViewById(R.id.iv_ashes_drop_location_active);
        iv_removal_specialist_assign=view.findViewById(R.id.iv_removal_specialist_assign);
        iv_Driver_assign=view.findViewById(R.id.iv_Driver_assign);

        tv_requested_accepted=view.findViewById(R.id.tv_requested_accepted);
        tv_reached_on_pickup_location=view.findViewById(R.id.tv_reached_on_pickup_location);
        tv_ashes_pickup=view.findViewById(R.id.tv_ashes_pickup);
        tv_reached_on_drop_location=view.findViewById(R.id.tv_reached_on_drop_location);
        tv_ashes_drop_location_active=view.findViewById(R.id.tv_ashes_drop_location_active);
        tv_removal_specialist_assign=view.findViewById(R.id.tv_removal_specialist_assign);
        tv_Driver_assign=view.findViewById(R.id.tv_Driver_assign);
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment2;
                fragment2 =  AshesDetiailsPageFragment.newInstance(id,"Home");
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
        Call<AshesTrackingModel> call = apiInterface.trackAshesStatusApi(loginResponse.getData().getToken(), id);
        call.enqueue(new Callback<AshesTrackingModel>() {
            @Override
            public void onResponse(Call<AshesTrackingModel> call, Response<AshesTrackingModel> response) {
                try {

                    if (response.code() >= 200 && response.code() <= 210) {
                        if (response.body().getSuccess().equalsIgnoreCase("true")) {
                            tv_decendent_name.setText(response.body().getData().getDecendantName());
                            tv_pickup_location.setText(response.body().getData().getRemovedFromAddress());
                            tv_drop_location.setText(response.body().getData().getTransferredToAddress());

                            String removal_specialists_assign_time=response.body().getData().getRemovalSpecialistsAssignTime();
                            String cab_driver_assign_time=response.body().getData().getCabDriverAssignTime();
                            String reached_on_rs_location_time=response.body().getData().getReachedOnRsLocationTime();
                            String pickup_rs_time=response.body().getData().getPickupRsTime();
                            String reached_on_decendant_pickup_location_time=response.body().getData().getReachedOnDecendantPickupLocationTime();
                            String pickup_decendent_time=response.body().getData().getPickupDecendentTime();
                            String drop_decendant_time=response.body().getData().getDropDecendantTime();

                           if(removal_specialists_assign_time!=null){
                               iv_requested_accepted.setImageResource(R.drawable.circle_fill);
                               tv_requested_accepted.setText(removal_specialists_assign_time);
                               iv_removal_specialist_assign.setImageResource(R.drawable.circle_fill);
                               tv_removal_specialist_assign.setText(removal_specialists_assign_time);

                           }
                            if(cab_driver_assign_time!=null){
                                iv_requested_accepted.setImageResource(R.drawable.circle_fill);
                                tv_requested_accepted.setText(removal_specialists_assign_time);
                                iv_removal_specialist_assign.setImageResource(R.drawable.circle_fill);
                                tv_removal_specialist_assign.setText(removal_specialists_assign_time);
                                iv_Driver_assign.setImageResource(R.drawable.circle_fill);
                                tv_Driver_assign.setText(cab_driver_assign_time);
                            }
                            if(reached_on_rs_location_time!=null){
                                iv_requested_accepted.setImageResource(R.drawable.circle_fill);
                                tv_requested_accepted.setText(removal_specialists_assign_time);
                                iv_removal_specialist_assign.setImageResource(R.drawable.circle_fill);
                                tv_removal_specialist_assign.setText(removal_specialists_assign_time);
                                iv_Driver_assign.setImageResource(R.drawable.circle_fill);
                                tv_Driver_assign.setText(cab_driver_assign_time);
                                iv_reached_on_pickup_location.setImageResource(R.drawable.circle_fill);
                                tv_reached_on_pickup_location.setText(reached_on_rs_location_time);
                            }
                            if(pickup_rs_time!=null){
                                iv_requested_accepted.setImageResource(R.drawable.circle_fill);
                                tv_requested_accepted.setText(removal_specialists_assign_time);
                                iv_removal_specialist_assign.setImageResource(R.drawable.circle_fill);
                                tv_removal_specialist_assign.setText(removal_specialists_assign_time);
                                iv_Driver_assign.setImageResource(R.drawable.circle_fill);
                                tv_Driver_assign.setText(cab_driver_assign_time);
                                iv_reached_on_pickup_location.setImageResource(R.drawable.circle_fill);
                                tv_reached_on_pickup_location.setText(reached_on_rs_location_time);
                                iv_ashes_pickup.setImageResource(R.drawable.circle_fill);
                                tv_ashes_pickup.setText(pickup_rs_time);
                            }
                            if(reached_on_decendant_pickup_location_time!=null){
                                iv_requested_accepted.setImageResource(R.drawable.circle_fill);
                                tv_requested_accepted.setText(removal_specialists_assign_time);
                                iv_removal_specialist_assign.setImageResource(R.drawable.circle_fill);
                                tv_removal_specialist_assign.setText(removal_specialists_assign_time);
                                iv_Driver_assign.setImageResource(R.drawable.circle_fill);
                                tv_Driver_assign.setText(cab_driver_assign_time);
                                iv_reached_on_pickup_location.setImageResource(R.drawable.circle_fill);
                                tv_reached_on_pickup_location.setText(reached_on_rs_location_time);
                                iv_ashes_pickup.setImageResource(R.drawable.circle_fill);
                                tv_ashes_pickup.setText(pickup_rs_time);
                                iv_reached_on_drop_location.setImageResource(R.drawable.circle_fill);
                                tv_reached_on_drop_location.setText(reached_on_decendant_pickup_location_time);
                            }
                            if(drop_decendant_time!=null){
                                iv_requested_accepted.setImageResource(R.drawable.circle_fill);
                                tv_requested_accepted.setText(removal_specialists_assign_time);
                                iv_removal_specialist_assign.setImageResource(R.drawable.circle_fill);
                                tv_removal_specialist_assign.setText(removal_specialists_assign_time);
                                iv_Driver_assign.setImageResource(R.drawable.circle_fill);
                                tv_Driver_assign.setText(cab_driver_assign_time);
                                iv_reached_on_pickup_location.setImageResource(R.drawable.circle_fill);
                                tv_reached_on_pickup_location.setText(reached_on_rs_location_time);
                                iv_ashes_pickup.setImageResource(R.drawable.circle_fill);
                                tv_ashes_pickup.setText(pickup_rs_time);
                                iv_reached_on_drop_location.setImageResource(R.drawable.circle_fill);
                                tv_reached_on_drop_location.setText(reached_on_decendant_pickup_location_time);
                                iv_ashes_drop_location_active.setImageResource(R.drawable.circle_fill);
                                tv_ashes_drop_location_active.setText(drop_decendant_time);

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
            public void onFailure(Call<AshesTrackingModel> call, Throwable t) {


            }
        });
    }

}