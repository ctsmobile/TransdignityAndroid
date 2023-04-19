package com.transdignity.deathserviceprovider.fragments.DetailFormPage;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.transdignity.deathserviceprovider.R;
import com.transdignity.deathserviceprovider.fragments.ServicesDetailsFragments.AirportRemovalDetailsFragment;
import com.transdignity.deathserviceprovider.models.auth.LoginResponse;
import com.transdignity.deathserviceprovider.models.request.ServiceDetailPage.AirportRemovalDetailPageModel;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class AirportRemovalDetailPageFragment extends Fragment {
    View view;
    TextView tv_date,tv_requested_by,tv_date_of_request,tv_requester_contact_no,tv_descendant_name,
            tv_date_of_birth,tv_date_time_of_death,tv_place_of_death,tv_descendant_mobile_no,
            tv_estimate_date_of_arrival,tv_transferred_address,tv_descendant_address_to_be_picked_from,
            tv_flight_from,tv_flight_to,tv_date_journey,tv_date_of_departure,tv_time_of_arrival,
            tv_flight_name,tv_flight_no,tv_prn_no;
    String requestid,pageScreen;
    Context context;
    Activity activity;
    LoginResponse loginResponse;
    TextView tv_back;
    public AirportRemovalDetailPageFragment() {
        // Required empty public constructor
    }


    public static AirportRemovalDetailPageFragment newInstance(String id,String screen) {
        AirportRemovalDetailPageFragment fragment = new AirportRemovalDetailPageFragment();
        fragment.requestid = id;
        fragment.pageScreen = screen;
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
        view= inflater.inflate(R.layout.fragment_airport_removal_detail_page, container, false);
        loginResponse = (LoginResponse) GlobalValues.getPreferenceManager().getObject(PreferenceManager.LoginResponseKey,LoginResponse.class);
        init();
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = AirportRemovalDetailsFragment.newInstance(requestid,"Home");
                String tagFragment = fragment.getClass().getName();
                GlobalValues.getInstance().setFramgentTag(tagFragment);
                CommonUtils.loadFragment(context, fragment, tagFragment);
            }
        });
        return view;
    }

    public void init(){
        tv_back=view.findViewById(R.id.tv_back);
        tv_date=view.findViewById(R.id.tv_date);
        tv_requested_by=view.findViewById(R.id.tv_requested_by);
        tv_date_of_request=view.findViewById(R.id.tv_date_of_request);
        tv_requester_contact_no=view.findViewById(R.id.tv_requester_contact_no);
        tv_descendant_name=view.findViewById(R.id.tv_descendant_name);
        tv_date_of_birth=view.findViewById(R.id.tv_date_of_birth);
        tv_date_time_of_death=view.findViewById(R.id.tv_date_time_of_death);
        tv_place_of_death=view.findViewById(R.id.tv_place_of_death);
        tv_descendant_mobile_no=view.findViewById(R.id.tv_descendant_mobile_no);
        tv_estimate_date_of_arrival=view.findViewById(R.id.tv_estimate_date_of_arrival);
        tv_transferred_address=view.findViewById(R.id.tv_transferred_address);
        tv_descendant_address_to_be_picked_from=view.findViewById(R.id.tv_descendant_address_to_be_picked_from);
        tv_flight_from=view.findViewById(R.id.tv_flight_from);
        tv_flight_to=view.findViewById(R.id.tv_flight_to);
        tv_date_journey=view.findViewById(R.id.tv_date_journey);
        tv_date_of_departure=view.findViewById(R.id.tv_date_of_departure);
        tv_time_of_arrival=view.findViewById(R.id.tv_time_of_arrival);
        tv_flight_name=view.findViewById(R.id.tv_flight_name);
        tv_flight_no=view.findViewById(R.id.tv_flight_no);
        tv_prn_no=view.findViewById(R.id.tv_prn_no);
        getProfileDetails();
    }

    void getProfileDetails() {
        final LoadingProgressDialog dialog = new LoadingProgressDialog(context, "Please wait...");
        dialog.setCancelable(false);
        dialog.show();

        ApiInterface apiInterface = ApiClients.getClient(context).create(ApiInterface.class);
        Call<AirportRemovalDetailPageModel> call = apiInterface.getRequestAirportRemovalApi(loginResponse.getData().getToken(), requestid);
        call.enqueue(new Callback<AirportRemovalDetailPageModel>() {
            @Override
            public void onResponse(Call<AirportRemovalDetailPageModel> call, Response<AirportRemovalDetailPageModel> response) {
                try {
                    dialog.dismiss();
                    if (response.code() >= 200 && response.code() <= 210) {
                        if (response.body().getSuccess().equalsIgnoreCase("true")) {

                            tv_date.setText(response.body().getData().getRequestDate());
                            tv_requested_by.setText(response.body().getData().getRequestedBy());
                            tv_date_of_request.setText(response.body().getData().getRequestDate());
                            tv_requester_contact_no.setText(response.body().getData().getRequestorContactNumber());
                            tv_descendant_name.setText(response.body().getData().getDecendentName());
                            tv_date_of_birth.setText(response.body().getData().getDob());
                            tv_date_time_of_death.setText(response.body().getData().getDateOfDeath()+" "+response.body().getData().getTimeOfDeath());
                            tv_place_of_death.setText(response.body().getData().getPlaceOfDeath());
                           // tv_descendant_mobile_no.setText(response.body().getData().get());
                            tv_estimate_date_of_arrival.setText(response.body().getData().getEstimatedDateArrivalOfDecendent());
                            tv_transferred_address.setText(response.body().getData().getTransferredAddress());
                            tv_descendant_address_to_be_picked_from.setText(response.body().getData().getDecendentPickedaddressOfbody());
                            tv_flight_from.setText(response.body().getData().getFlightdetail().getFromAddress());
                            tv_flight_to.setText(response.body().getData().getFlightdetail().getToAddress());
                            tv_date_journey.setText(response.body().getData().getFlightdetail().getDateOfJourney());
                            tv_date_of_departure.setText(response.body().getData().getFlightdetail().getTimeOfDeparture());
                            tv_time_of_arrival.setText(response.body().getData().getFlightdetail().getTimeOfArrival());
                            tv_flight_name.setText(response.body().getData().getFlightdetail().getFlightName());
                            tv_flight_no.setText(response.body().getData().getFlightdetail().getFlightNumber());
                            tv_prn_no.setText(response.body().getData().getFlightdetail().getFlightPnr());

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
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<AirportRemovalDetailPageModel> call, Throwable t) {
                dialog.dismiss();
                Log.e(TAG, "onResponse: " + t.getMessage());
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}