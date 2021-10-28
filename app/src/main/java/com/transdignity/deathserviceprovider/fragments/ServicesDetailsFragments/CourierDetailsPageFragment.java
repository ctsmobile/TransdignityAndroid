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
import com.transdignity.deathserviceprovider.fragments.CourierServiceFragment;
import com.transdignity.deathserviceprovider.fragments.DetailFormPage.CouriorViewDetailPageFragment;
import com.transdignity.deathserviceprovider.fragments.HomeFragment;
import com.transdignity.deathserviceprovider.fragments.bankDetailsFragments.ClientPaymentIntegrationFragment;
import com.transdignity.deathserviceprovider.fragments.servicesTrackStatusFragments.CourierTrackFragment;
import com.transdignity.deathserviceprovider.models.auth.LoginResponse;
import com.transdignity.deathserviceprovider.models.request.ServiceDetailPage.CourierDetailPageModel;
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


public class CourierDetailsPageFragment extends Fragment implements View.OnClickListener {
    View view;
    String requestid,pageScreen;
    Context context;
    Activity activity;
    LoginResponse loginResponse;
    TextView bt_pay;
    TextView tv_detail_form_arrow,tv_back;
    TextView tv_decendent_name,tv_pick_up_address,tv_drop_address,tv_date,tv_time,tv_description,
            tv_receiver_name,tv_sender_mobile_no,tv_estimate_amount,tv_status_running;
    LinearLayout ll_pickup_track,ll_detail_view;
    String str_estimate_amount;

    public CourierDetailsPageFragment() {
        // Required empty public constructor9-
    }


    public static CourierDetailsPageFragment newInstance(String id,String screen) {
        CourierDetailsPageFragment fragment = new CourierDetailsPageFragment();
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
        view= inflater.inflate(R.layout.fragment_courier_details_page, container, false);
        loginResponse = (LoginResponse) GlobalValues.getPreferenceManager().getObject(PreferenceManager.LoginResponseKey,LoginResponse.class);
        init();
        getProfileDetails();
        return view;
    }
    public void init(){
        bt_pay=view.findViewById(R.id.bt_pay);
        tv_detail_form_arrow=view.findViewById(R.id.tv_detail_form_arrow);
        tv_decendent_name=view.findViewById(R.id.tv_decendent_name);
        tv_pick_up_address=view.findViewById(R.id.tv_pick_up_address);
        tv_drop_address=view.findViewById(R.id.tv_drop_address);
        tv_date=view.findViewById(R.id.tv_date);
        tv_time=view.findViewById(R.id.tv_time);
        tv_description=view.findViewById(R.id.tv_description);
        tv_receiver_name=view.findViewById(R.id.tv_receiver_name);
        tv_sender_mobile_no=view.findViewById(R.id.tv_sender_mobile_no);
        tv_estimate_amount=view.findViewById(R.id.tv_estimate_amount);
        tv_back=view.findViewById(R.id.tv_back);
        ll_pickup_track=view.findViewById(R.id.ll_pickup_track);
        ll_detail_view=view.findViewById(R.id.ll_detail_view);
        tv_status_running=view.findViewById(R.id.tv_status_running);

        click();
    }
    public void click(){
        bt_pay.setOnClickListener(this);
        tv_detail_form_arrow.setOnClickListener(this);
        tv_back.setOnClickListener(this);
        ll_pickup_track.setOnClickListener(this);
        ll_detail_view.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
       switch (v.getId()){
           case R.id.bt_pay:
               Fragment fragment = ClientPaymentIntegrationFragment.newInstance(requestid,str_estimate_amount,"0");
               String fragmtStatusTag2 = fragment.getClass().getName();
               GlobalValues.getInstance().setFramgentTag(fragmtStatusTag2);
               ((MainActivity) activity).selectedNavItem(R.id.navigation_home);
               CommonUtils.loadFragment(context, fragment, fragmtStatusTag2);
               break;
           case R.id.tv_detail_form_arrow:
               Fragment fragment4 = CouriorViewDetailPageFragment.newInstance(requestid);
               String fragmtStatusTag4 = fragment4.getClass().getName();
               GlobalValues.getInstance().setFramgentTag(fragmtStatusTag4);
               ((MainActivity) activity).selectedNavItem(R.id.navigation_home);
               CommonUtils.loadFragment(context, fragment4, fragmtStatusTag4);
               break;
           case R.id.ll_detail_view:
               Fragment fragment1 = CouriorViewDetailPageFragment.newInstance(requestid);
               String fragmtStatusTag1 = fragment1.getClass().getName();
               GlobalValues.getInstance().setFramgentTag(fragmtStatusTag1);
               ((MainActivity) activity).selectedNavItem(R.id.navigation_home);
               CommonUtils.loadFragment(context, fragment1, fragmtStatusTag1);
               break;
           case R.id.ll_pickup_track:
               Fragment fragment_track = CourierTrackFragment.newInstance(requestid);
               String fragmtStatusTrack = fragment_track.getClass().getName();
               GlobalValues.getInstance().setFramgentTag(fragmtStatusTrack);
               ((MainActivity) activity).selectedNavItem(R.id.navigation_home);
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
               }else if(pageScreen.equalsIgnoreCase("Courier")){
                   Fragment fragment2;
                   fragment2 = new CourierServiceFragment();
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
        Call<CourierDetailPageModel> call = apiInterface.getRequestCourierPageApi(loginResponse.getData().getToken(), requestid);
        call.enqueue(new Callback<CourierDetailPageModel>() {
            @Override
            public void onResponse(Call<CourierDetailPageModel> call, Response<CourierDetailPageModel> response) {
                try {
                    dialog.dismiss();
                    if (response.code() >= 200 && response.code() <= 210) {
                        if (response.body().getSuccess().equalsIgnoreCase("true")) {
                            String name = "";
                            if (response.body().getData().getSenderFirstName() != null) {
                                name = name + response.body().getData().getSenderFirstName();
                            }
                           /* if (response.body().getData().getS() != null) {
                                name = name + " " + response.body().getData().getMiddleName();
                            }*/
                            if (response.body().getData().getSenderLastName() != null) {
                                name = name + " " + response.body().getData().getSenderLastName();
                            }
                            tv_decendent_name.setText(name);

                            tv_pick_up_address.setText(response.body().getData().getPickupLocation());
                            tv_drop_address.setText(response.body().getData().getDropLocation());
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
                            tv_description.setText(response.body().getData().getDescription());
                            tv_receiver_name.setText(response.body().getData().getRecieverFirstName());
                            tv_sender_mobile_no.setText(response.body().getData().getSenderFirstName());
                            tv_estimate_amount.setText(response.body().getData().getmTotalCharge());
                            str_estimate_amount=response.body().getData().getmTotalCharge();

                            tv_status_running.setText(response.body().getData().getDspStatus());
                            if(response.body().getData().getStartpaymentdetail()!=null){
                                String payment_status=response.body().getData().getStartpaymentdetail().getPaymentStatus().toString();
                                if(payment_status.equals("pending")){
                                    bt_pay.setVisibility(View.VISIBLE);
                                }else if(payment_status.equals("complete")){
                                    bt_pay.setVisibility(View.GONE);

                                }
                            }else {
                                bt_pay.setVisibility(View.GONE);

                            }
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
            public void onFailure(Call<CourierDetailPageModel> call, Throwable t) {
                dialog.dismiss();
                Log.e(TAG, "onResponse: " + t.getMessage());
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}