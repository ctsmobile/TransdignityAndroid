package com.transdignity.deathserviceprovider.fragments.ServicesDetailsFragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.transdignity.deathserviceprovider.MainActivity;
import com.transdignity.deathserviceprovider.R;
import com.transdignity.deathserviceprovider.fragments.AirportArrivalFragment;
import com.transdignity.deathserviceprovider.fragments.DetailFormPage.AirportRemovalDetailPageFragment;
import com.transdignity.deathserviceprovider.fragments.HomeFragment;
import com.transdignity.deathserviceprovider.fragments.bankDetailsFragments.ClientPaymentIntegrationFragment;
import com.transdignity.deathserviceprovider.fragments.servicesTrackStatusFragments.AirportRemovalTrackFragment;
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class AirportRemovalDetailsFragment extends Fragment implements View.OnClickListener {
    View view;
    String requestid,pageScreen;
    Context context;
    Activity activity;
    LoginResponse loginResponse;
    TextView tv_decendent_name,tv_pick_address,tv_drop_address,tv_date,tv_time,
            tv_description,tv_mobile_no,tv_estimate_amount,bt_pay,tv_status_running,tv_sender_mobile_no,tv_receiver_name;
    TextView tv_detail_form_arrow,tv_back;
    TextView tv_flight_details,bt_pay_next;
    LinearLayout ll_pickup_track,ll_detail_view;
    String str_estimate_amount;
    TextView tv_flight_name,tv_flight_number,tv_flight_prn_number;
    LinearLayout ll_flight;
    public AirportRemovalDetailsFragment() {
        // Required empty public constructor
    }

    public static AirportRemovalDetailsFragment newInstance(String id,String screen) {
        AirportRemovalDetailsFragment fragment = new AirportRemovalDetailsFragment();
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
        view= inflater.inflate(R.layout.fragment_airport_removal_details, container, false);
        loginResponse = (LoginResponse) GlobalValues.getPreferenceManager().getObject(PreferenceManager.LoginResponseKey,LoginResponse.class);
        init();
        getProfileDetails();
        return view;

    }
    public void init(){
        tv_decendent_name=view.findViewById(R.id.tv_decendent_name);
        tv_pick_address=view.findViewById(R.id.tv_pick_up_address);
        tv_drop_address=view.findViewById(R.id.tv_drop_address);
        tv_date=view.findViewById(R.id.tv_date);
        tv_time=view.findViewById(R.id.tv_time);
        tv_description=view.findViewById(R.id.tv_description);
        tv_mobile_no=view.findViewById(R.id.tv_mobile_no);
        tv_estimate_amount=view.findViewById(R.id.tv_estimate_amount);
        bt_pay=view.findViewById(R.id.bt_pay);
        tv_back=view.findViewById(R.id.tv_back);
        tv_detail_form_arrow=view.findViewById(R.id.tv_detail_form_arrow);
        ll_pickup_track=view.findViewById(R.id.ll_pickup_track);
        ll_detail_view=view.findViewById(R.id.ll_detail_view);
        tv_status_running=view.findViewById(R.id.tv_status_running);
        tv_sender_mobile_no=view.findViewById(R.id.tv_sender_mobile_no);
        tv_receiver_name=view.findViewById(R.id.tv_receiver_name);
        tv_flight_details=view.findViewById(R.id.tv_flight_details);
        bt_pay_next=view.findViewById(R.id.bt_pay_next);
        tv_flight_name=view.findViewById(R.id.tv_flight_name);
        tv_flight_number=view.findViewById(R.id.tv_flight_number);
        ll_flight=view.findViewById(R.id.ll_flight);
        tv_flight_prn_number=view.findViewById(R.id.tv_flight_prn_number);
        click();
    }
    public void click(){
        bt_pay.setOnClickListener(this);
        tv_detail_form_arrow.setOnClickListener(this);
        tv_back.setOnClickListener(this);
        ll_pickup_track.setOnClickListener(this);
        ll_detail_view.setOnClickListener(this);
        bt_pay_next.setOnClickListener(this);
    }
    @Override
    public void onDetach() {
        super.onDetach();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_pay:
                Fragment fragment = ClientPaymentIntegrationFragment.newInstance(requestid,tv_estimate_amount.getText().toString(),"0");
                String fragmtStatusTag2 = fragment.getClass().getName();
                GlobalValues.getInstance().setFramgentTag(fragmtStatusTag2);
                ((MainActivity) activity).selectedNavItem(R.id.navigation_home);
                CommonUtils.loadFragment(context, fragment, fragmtStatusTag2);
                break;
            case R.id.bt_pay_next:
                Fragment fragment_next = ClientPaymentIntegrationFragment.newInstance(requestid,tv_estimate_amount.getText().toString(),"1");
                String fragmtStatusTag2_next = fragment_next.getClass().getName();
                GlobalValues.getInstance().setFramgentTag(fragmtStatusTag2_next);
                ((MainActivity) activity).selectedNavItem(R.id.navigation_home);
                CommonUtils.loadFragment(context, fragment_next, fragmtStatusTag2_next);
                break;
            case R.id.tv_detail_form_arrow:
                Fragment fragment4 = AirportRemovalDetailPageFragment.newInstance(requestid,"");
                String fragmtStatusTag4 = fragment4.getClass().getName();
                GlobalValues.getInstance().setFramgentTag(fragmtStatusTag4);
                ((MainActivity) activity).selectedNavItem(R.id.navigation_home);
                CommonUtils.loadFragment(context, fragment4, fragmtStatusTag4);
                break;
            case R.id.ll_detail_view:
                Fragment fragment1 = AirportRemovalDetailPageFragment.newInstance(requestid,"");
                String fragmtStatusTag1 = fragment1.getClass().getName();
                GlobalValues.getInstance().setFramgentTag(fragmtStatusTag1);
                ((MainActivity) activity).selectedNavItem(R.id.navigation_home);
                CommonUtils.loadFragment(context, fragment1, fragmtStatusTag1);
                break;
            case R.id.ll_pickup_track:
                Fragment fragment_track = AirportRemovalTrackFragment.newInstance(requestid);
                String fragmtStatusTrack = fragment_track.getClass().getName();
                GlobalValues.getInstance().setFramgentTag(fragmtStatusTrack);
                //((MainActivity) activity).selectedNavItem(R.id.navigation_home);
                CommonUtils.loadFragment(context, fragment_track, fragmtStatusTrack);
                break;
            case R.id.tv_back:

                if(pageScreen.equalsIgnoreCase("Home")){
                    Fragment fragment2;
                    fragment2 = new HomeFragment();
                    String fragmtStatusTag = fragment2.getClass().getName();
                    GlobalValues.getInstance().setFramgentTag(fragmtStatusTag);
                    ((MainActivity) activity).selectedNavItem(R.id.navigation_home);
                    CommonUtils.loadFragment(context, fragment2, fragmtStatusTag);
                }else if(pageScreen.equalsIgnoreCase("Airport")){
                    Fragment fragment2;
                    fragment2 = new AirportArrivalFragment();
                    String fragmtStatusTag = fragment2.getClass().getName();
                    GlobalValues.getInstance().setFramgentTag(fragmtStatusTag);
                    ((MainActivity) activity).selectedNavItem(R.id.navigation_home);
                    CommonUtils.loadFragment(context, fragment2, fragmtStatusTag);
                }

                break;
        }
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
                            String name = "";
                            /*if (response.body().getData().getFirstName() != null) {
                                name = name + response.body().getData().getFirstName();
                            }
                            if (response.body().getData().getMiddleName() != null) {
                                name = name + " " + response.body().getData().getMiddleName();
                            }
                            if (response.body().getData().getLastName() != null) {
                                name = name + " " + response.body().getData().getLastName();
                            }*/
                            tv_decendent_name.setText(response.body().getData().getDecendentName());
                            // tv_description.setText(response.body().getData().get);
                            tv_sender_mobile_no.setText(response.body().getData().getRequestorContactNumber());
                          tv_receiver_name.setText(response.body().getData().getRequestedBy());
                            tv_pick_address.setText(response.body().getData().getDecendentPickedaddressOfbody());
                            tv_drop_address.setText(response.body().getData().getTransferredAddress());
                           // tv_estimate_amount.setText(response.body().getData().getStartpaymentdetail().getTotalCharge());

                            String str_request_date = response.body().getData().getRequestDate();
                            try {
                                DateFormat f = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                                Date d = f.parse(str_request_date);
                                DateFormat date = new SimpleDateFormat("d MMM yyyy");
                                DateFormat time = new SimpleDateFormat("hh:mm aa");
                                tv_date.setText(date.format(d));
                                tv_time.setText(time.format(d));
                                System.out.println("Date: " + date.format(d));
                                System.out.println("Time: " + time.format(d));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            tv_status_running.setText(response.body().getData().getDspStatus());
                           // str_estimate_amount=response.body().getData().getStartpaymentdetail().getTotalCharge();
                            if (response.body().getData().getDspStatus().equalsIgnoreCase("Pending")) {
                                // holder.binding.tvStatus.setBackgroundTintList(ColorStateList.valueOf(R.color.colorPrimaryDark));
                                tv_status_running.setBackgroundResource(R.drawable.bg_primary_30corn);

                            } else if (response.body().getData().getDspStatus().equalsIgnoreCase("Rejected")) {
                                tv_status_running.setBackgroundResource(R.drawable.bg_red_30corn);
                            }else if (response.body().getData().getDspStatus().equalsIgnoreCase("Completed")) {
                                tv_status_running.setBackgroundResource(R.drawable.bg_red_30corn);
                            } else {
                                tv_status_running.setBackgroundResource(R.drawable.bg_green_30corn);
                            }

                            if(response.body().getData().getStartpaymentdetail()!=null||!response.body().getData().getStartpaymentdetail().equals("null")){
                                String payment_status=response.body().getData().getStartpaymentdetail().getPaymentStatus().toString();
                                if(payment_status.equals("pending")){
                                    bt_pay.setVisibility(View.VISIBLE);
                                    bt_pay_next.setVisibility(View.GONE);
                                    tv_pick_address.setText(response.body().getData().getDecendentPickedaddressOfbody());
                                    tv_drop_address.setText(response.body().getData().getTransferredAddress());
                                    tv_estimate_amount.setText(response.body().getData().getStartpaymentdetail().getTotalCharge());


                                }else if(payment_status.equals("complete")){
                                    bt_pay.setVisibility(View.GONE);
                                    bt_pay_next.setVisibility(View.VISIBLE);
                                    tv_flight_details.setVisibility(View.VISIBLE);
                                    ll_flight.setVisibility(View.VISIBLE);
                                    tv_flight_name.setText(response.body().getData().getFlightdetail().getFlightName());
                                    tv_flight_number.setText(response.body().getData().getFlightdetail().getFlightNumber());
                                    tv_flight_prn_number.setText(response.body().getData().getFlightdetail().getFlightPnr());
                                    tv_pick_address.setText(response.body().getData().getFlightdetail().getFromAddress());
                                    tv_drop_address.setText(response.body().getData().getFlightdetail().getToAddress());
                                    tv_estimate_amount.setText(response.body().getData().getEndpaymentdetail().getTotalCharge());

                                }
                            }else if(response.body().getData().getStartpaymentdetail()==null){
                                bt_pay.setVisibility(View.GONE);
                                tv_pick_address.setText(response.body().getData().getDecendentPickedaddressOfbody());
                                tv_drop_address.setText(response.body().getData().getTransferredAddress());
                                tv_pick_address.setText(response.body().getData().getDecendentPickedaddressOfbody());
                                tv_drop_address.setText(response.body().getData().getTransferredAddress());
                                tv_estimate_amount.setText(response.body().getData().getStartpaymentdetail().getTotalCharge());

                            }
                            if(response.body().getData().getEndpaymentdetail()!=null){
                                String payment_status_start=response.body().getData().getStartpaymentdetail().getPaymentStatus().toString();

                                String payment_status=response.body().getData().getEndpaymentdetail().getPaymentStatus().toString();
                                if(payment_status_start.equals("pending")&&payment_status.equals("pending")){
                                    bt_pay_next.setVisibility(View.GONE);
                                    bt_pay.setVisibility(View.VISIBLE);

                                }else if(payment_status_start.equals("complete")&&payment_status.equals("pending")){
                                    bt_pay_next.setVisibility(View.VISIBLE);
                                    bt_pay.setVisibility(View.GONE);
                                }else if(payment_status_start.equals("complete")&&payment_status.equals("complete")){
                                    bt_pay_next.setVisibility(View.GONE);
                                    tv_flight_details.setVisibility(View.VISIBLE);
                                    ll_flight.setVisibility(View.VISIBLE);
                                    tv_flight_name.setText(response.body().getData().getFlightdetail().getFlightName());
                                    tv_flight_number.setText(response.body().getData().getFlightdetail().getFlightNumber());
                                    tv_flight_prn_number.setText(response.body().getData().getFlightdetail().getFlightPnr());
                                    tv_pick_address.setText(response.body().getData().getFlightdetail().getFromAddress());
                                    tv_drop_address.setText(response.body().getData().getFlightdetail().getToAddress());
                                    tv_estimate_amount.setText(response.body().getData().getEndpaymentdetail().getTotalCharge());

                                }}

                            // tv_time.setText(response.body().getData().g());

                           /* tv_sender_name.setText(name);
                            tv_sender_mobile_no.setText(response.body().getData().getMobileNumber());
*/
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