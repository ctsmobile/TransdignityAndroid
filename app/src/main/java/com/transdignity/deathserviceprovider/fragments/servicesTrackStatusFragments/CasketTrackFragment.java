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
import com.transdignity.deathserviceprovider.fragments.ServicesDetailsFragments.CasketDetailsPageFragment;
import com.transdignity.deathserviceprovider.models.TrackResponse;
import com.transdignity.deathserviceprovider.models.auth.LoginResponse;
import com.transdignity.deathserviceprovider.models.requestDetails.Data;
import com.transdignity.deathserviceprovider.models.tracking.CasketTrackingModel;
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

public class CasketTrackFragment extends Fragment {
    Context context;
    Activity activity;
    Data data;
    LoginResponse loginResponse;
    String id;
    View view;
    TextView tv_decendent_name,tv_pickup_location,tv_drop_location;
    ImageView iv_request_accepted,iv_on_the_way,iv_driver_reached_on_pickup_point,iv_casket_pickup,iv_driver_on_the_way,
            iv_drop_decendent_to_thier_drop_location_active,iv_deliver_casket;
    TextView tv_request_accepted,tv_on_the_way,tv_driver_reached_on_pickup_point,tv_casket_pickup,tv_driver_on_the_way,
            tv_drop_decendent_to_thier_drop_location_active,tv_deliver_casket;
    TextView tv_back;
    public CasketTrackFragment() {
        // Required empty public constructor
    }


    public static CasketTrackFragment newInstance(String id) {
        CasketTrackFragment fragment = new CasketTrackFragment();
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
        view= inflater.inflate(R.layout.fragment_casket_track, container, false);
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
        iv_on_the_way=view.findViewById(R.id.iv_on_the_way);
        iv_driver_reached_on_pickup_point=view.findViewById(R.id.iv_driver_reached_on_pickup_point);
        iv_casket_pickup=view.findViewById(R.id.iv_casket_pickup);
        iv_driver_on_the_way=view.findViewById(R.id.iv_driver_on_the_way);
        iv_drop_decendent_to_thier_drop_location_active=view.findViewById(R.id.iv_drop_decendent_to_thier_drop_location_active);
        iv_deliver_casket=view.findViewById(R.id.iv_deliver_casket);

        tv_request_accepted=view.findViewById(R.id.tv_request_accepted);
        tv_on_the_way=view.findViewById(R.id.tv_on_the_way);
        tv_driver_reached_on_pickup_point=view.findViewById(R.id.tv_driver_reached_on_pickup_point);
        tv_casket_pickup=view.findViewById(R.id.tv_casket_pickup);
        tv_driver_on_the_way=view.findViewById(R.id.tv_driver_on_the_way);
        tv_drop_decendent_to_thier_drop_location_active=view.findViewById(R.id.tv_drop_decendent_to_thier_drop_location_active);
        tv_deliver_casket=view.findViewById(R.id.tv_deliver_casket);
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment2;
                fragment2 =  CasketDetailsPageFragment.newInstance(id,"Home");
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
        Call<CasketTrackingModel> call = apiInterface.trackCasketStatusApi(loginResponse.getData().getToken(), id);
        call.enqueue(new Callback<CasketTrackingModel>() {
            @Override
            public void onResponse(Call<CasketTrackingModel> call, Response<CasketTrackingModel> response) {
                try {

                    if (response.code() >= 200 && response.code() <= 210) {
                        if (response.body().getSuccess().equalsIgnoreCase("true")) {
                            tv_decendent_name.setText(response.body().getData().getDecendantName());
                            tv_pickup_location.setText(response.body().getData().getRemovedFromAddress());
                            tv_drop_location.setText(response.body().getData().getTransferredToAddress());
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
                                iv_driver_reached_on_pickup_point.setImageResource(R.drawable.circle_fill);
                                tv_driver_reached_on_pickup_point.setText(rq_pickupLocation);
                            }
                            if(rq_pickup!=null){
                                iv_request_accepted.setImageResource(R.drawable.circle_fill);
                                iv_on_the_way.setImageResource(R.drawable.circle_fill);
                                tv_request_accepted.setText(rq_accept);
                                iv_driver_reached_on_pickup_point.setImageResource(R.drawable.circle_fill);
                                tv_driver_reached_on_pickup_point.setText(rq_pickupLocation);
                                iv_casket_pickup.setImageResource(R.drawable.circle_fill);
                                tv_casket_pickup.setText(rq_pickup);
                            }
                            if(rq_drop!=null){
                                iv_request_accepted.setImageResource(R.drawable.circle_fill);
                                iv_on_the_way.setImageResource(R.drawable.circle_fill);
                                tv_request_accepted.setText(rq_accept);
                                iv_driver_reached_on_pickup_point.setImageResource(R.drawable.circle_fill);
                                tv_driver_reached_on_pickup_point.setText(rq_pickupLocation);
                                iv_casket_pickup.setImageResource(R.drawable.circle_fill);
                                tv_casket_pickup.setText(rq_pickup);
                                iv_driver_on_the_way.setImageResource(R.drawable.circle_fill);
                                iv_drop_decendent_to_thier_drop_location_active.setImageResource(R.drawable.circle_fill);
                                tv_drop_decendent_to_thier_drop_location_active.setText(rq_drop);

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
            public void onFailure(Call<CasketTrackingModel> call, Throwable t) {


            }
        });
    }

}