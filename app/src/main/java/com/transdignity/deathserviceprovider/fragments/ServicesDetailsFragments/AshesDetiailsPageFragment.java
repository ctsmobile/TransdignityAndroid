package com.transdignity.deathserviceprovider.fragments.ServicesDetailsFragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.transdignity.deathserviceprovider.fragments.AshesServiceFragment;
import com.transdignity.deathserviceprovider.fragments.DetailFormPage.AshesViewDetialPageFragment;
import com.transdignity.deathserviceprovider.fragments.DetailFormPage.CouriorViewDetailPageFragment;
import com.transdignity.deathserviceprovider.fragments.DetailFormPage.DescendantViewDetailPageFragment;
import com.transdignity.deathserviceprovider.fragments.HomeFragment;
import com.transdignity.deathserviceprovider.fragments.ServicesRequestPayment.ServicesPaymentFragment;
import com.transdignity.deathserviceprovider.fragments.TrackFragment;
import com.transdignity.deathserviceprovider.fragments.bankDetailsFragments.ClientPaymentIntegrationFragment;
import com.transdignity.deathserviceprovider.fragments.servicesTrackStatusFragments.AshesTrackFragment;
import com.transdignity.deathserviceprovider.models.auth.LoginResponse;
import com.transdignity.deathserviceprovider.models.request.ServiceDetailPage.AshesDetailPageMOdel;
import com.transdignity.deathserviceprovider.models.request.ServiceDetailPage.LimoDetailPageModel;
import com.transdignity.deathserviceprovider.models.requestDetails.RequestDetailResponse;
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


public class AshesDetiailsPageFragment extends Fragment implements View.OnClickListener {
    View view;
    String requestid,pageScreen;
    Context context;
    Activity activity;
    LoginResponse loginResponse;
    TextView tv_decendent_name,tv_pick_address,tv_drop_address,tv_date,tv_time,
            tv_description,tv_mobile_no,tv_estimate_amount,bt_pay,tv_status_running;
   TextView tv_detail_form_arrow,tv_back;
    LinearLayout ll_pickup_track,ll_detail_view;
    String str_estimate_amount;
    LinearLayout ll_removal_spl_details;
    TextView tv_removal_spl_name,tv_removal_call_btn,tv_descendent_call_btn;
    LinearLayout ll_cab_driver_details;
    TextView tv_cab_driver_name,tv_cab_driver_call_btn;
    LinearLayout ll_cabs_details;
    TextView tv_cab_name,tv_cab_number;
    String descendent_call,removal_call,driver_call;
    public AshesDetiailsPageFragment() {
        // Required empty public constructor
    }


    public static AshesDetiailsPageFragment newInstance(String id,String screen) {
        AshesDetiailsPageFragment fragment = new AshesDetiailsPageFragment();
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
        view= inflater.inflate(R.layout.fragment_ashes_detiails_page, container, false);
        loginResponse = (LoginResponse) GlobalValues.getPreferenceManager().getObject(PreferenceManager.LoginResponseKey,LoginResponse.class);
        init();
        getProfileDetails();
        return view;
    }
    public void init(){
        tv_decendent_name=view.findViewById(R.id.tv_decendent_name);
        tv_pick_address=view.findViewById(R.id.tv_pick_address);
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

        ll_removal_spl_details=view.findViewById(R.id.ll_removal_spl_details);
        tv_removal_spl_name=view.findViewById(R.id.tv_removal_spl_name);
        tv_removal_call_btn=view.findViewById(R.id.tv_removal_call_btn);
        ll_cab_driver_details=view.findViewById(R.id.ll_cab_driver_details);
        tv_cab_driver_name=view.findViewById(R.id.tv_cab_driver_name);
        tv_cab_driver_call_btn=view.findViewById(R.id.tv_cab_driver_call_btn);
        ll_cabs_details=view.findViewById(R.id.ll_cabs_details);
        tv_cab_name=view.findViewById(R.id.tv_cab_name);
        tv_cab_number=view.findViewById(R.id.tv_cab_number);
        tv_descendent_call_btn=view.findViewById(R.id.tv_descendent_call_btn);
        click();
    }
    public void click(){
        bt_pay.setOnClickListener(this);
        tv_detail_form_arrow.setOnClickListener(this);
        tv_back.setOnClickListener(this);
        ll_pickup_track.setOnClickListener(this);
        ll_detail_view.setOnClickListener(this);
        tv_descendent_call_btn.setOnClickListener(this);
        tv_removal_call_btn.setOnClickListener(this);
        tv_cab_driver_call_btn.setOnClickListener(this);
    }
    @Override
    public void onDetach() {
        super.onDetach();
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
                 Fragment fragment4 = AshesViewDetialPageFragment.newInstance(requestid);
                 String fragmtStatusTag4 = fragment4.getClass().getName();
                 GlobalValues.getInstance().setFramgentTag(fragmtStatusTag4);
                 ((MainActivity) activity).selectedNavItem(R.id.navigation_home);
                 CommonUtils.loadFragment(context, fragment4, fragmtStatusTag4);
                 break;
             case R.id.ll_detail_view:
                 Fragment fragment1 = AshesViewDetialPageFragment.newInstance(requestid);
                 String fragmtStatusTag1 = fragment1.getClass().getName();
                 GlobalValues.getInstance().setFramgentTag(fragmtStatusTag1);
                 ((MainActivity) activity).selectedNavItem(R.id.navigation_home);
                 CommonUtils.loadFragment(context, fragment1, fragmtStatusTag1);
                 break;
                 case R.id.ll_pickup_track:
                     Fragment fragment_track = AshesTrackFragment.newInstance(requestid);
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
                 }else if(pageScreen.equalsIgnoreCase("Ashes")){
                     Fragment fragment2;
                     fragment2 = new AshesServiceFragment();
                     String fragmtStatusTag = fragment2.getClass().getName();
                     GlobalValues.getInstance().setFramgentTag(fragmtStatusTag);
                     ((MainActivity) activity).selectedNavItem(R.id.navigation_home);
                     CommonUtils.loadFragment(context, fragment2, fragmtStatusTag);
                 }
                 break;
             case R.id.tv_descendent_call_btn:
                 try {
                 if (descendent_call != null) {
                     Intent callIntent = new Intent(Intent.ACTION_CALL);
                     callIntent.setData(Uri.parse("tel:" + descendent_call));
                     startActivity(callIntent);
                 } else {
                     Toast.makeText(context, "Invalid Mobile Number", Toast.LENGTH_SHORT).show();
                 }
         } catch (Exception e) {
        }
                 break;
             case R.id.tv_removal_call_btn:
                 try {
                     if (removal_call != null) {
                         Intent callIntent = new Intent(Intent.ACTION_CALL);
                         callIntent.setData(Uri.parse("tel:" + removal_call));
                         startActivity(callIntent);
                     } else {
                         Toast.makeText(context, "Invalid Mobile Number", Toast.LENGTH_SHORT).show();
                     }
                 } catch (Exception e) {
                 }
                 break;
             case R.id.tv_cab_driver_call_btn:
                 try {
                     if (driver_call != null) {
                         Intent callIntent = new Intent(Intent.ACTION_CALL);
                         callIntent.setData(Uri.parse("tel:" + driver_call));
                         startActivity(callIntent);
                     } else {
                         Toast.makeText(context, "Invalid Mobile Number", Toast.LENGTH_SHORT).show();
                     }
                 } catch (Exception e) {
                 }
                 break;
         }
    }
    void getProfileDetails() {
        final LoadingProgressDialog dialog = new LoadingProgressDialog(context, "Please wait...");
        dialog.setCancelable(false);
        dialog.show();

        ApiInterface apiInterface = ApiClients.getClient(context).create(ApiInterface.class);
        Call<AshesDetailPageMOdel> call = apiInterface.getRequestAshesPageApi(loginResponse.getData().getToken(), requestid);
        call.enqueue(new Callback<AshesDetailPageMOdel>() {
            @Override
            public void onResponse(Call<AshesDetailPageMOdel> call, Response<AshesDetailPageMOdel> response) {
                try {
                    dialog.dismiss();
                    if (response.code() >= 200 && response.code() <= 210) {
                        if (response.body().getSuccess().equalsIgnoreCase("true")) {
                            String name = "";
                            if (response.body().getData().getFirstName() != null) {
                                name = name + response.body().getData().getFirstName();
                            }
                            if (response.body().getData().getMiddleName() != null) {
                                name = name + " " + response.body().getData().getMiddleName();
                            }
                            if (response.body().getData().getLastName() != null) {
                                name = name + " " + response.body().getData().getLastName();
                            }
                            tv_decendent_name.setText(name);
                            tv_pick_address.setText(response.body().getData().getPickupLocation());
                            tv_drop_address.setText(response.body().getData().getDropLocation());
                            tv_description.setText(response.body().getData().getDescription());
                            tv_mobile_no.setText(response.body().getData().getMobileNumber());
                            descendent_call=response.body().getData().getMobileNumber();
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
                            str_estimate_amount=response.body().getData().getmTotalCharge();
                            tv_estimate_amount.setText(response.body().getData().getmTotalCharge());
                            if(response.body().getData().getStartassineddata()!=null){
                                if(response.body().getData().getStartassineddata().getRsName().toString().equals("")){
                                    ll_removal_spl_details.setVisibility(View.GONE);
                                }else {
                                    ll_removal_spl_details.setVisibility(View.VISIBLE);
                                    tv_removal_spl_name.setText(response.body().getData().getStartassineddata().getRsName().toString());
                                    removal_call=response.body().getData().getStartassineddata().getRsPhone().toString();
                                }
                                if(response.body().getData().getStartassineddata().getCdName().toString().equals("")){
                                    ll_cab_driver_details.setVisibility(View.GONE);
                                }else {
                                    ll_cab_driver_details.setVisibility(View.VISIBLE);
                                    tv_cab_driver_name.setText(response.body().getData().getStartassineddata().getCdName().toString());
                                    driver_call=response.body().getData().getStartassineddata().getCdPhone().toString();

                                }
                                                           }
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
                            } else if (response.body().getData().getDspStatus().equalsIgnoreCase("Completed")) {
                                tv_status_running.setBackgroundResource(R.drawable.bg_red_30corn);
                            } else {
                                tv_status_running.setBackgroundResource(R.drawable.bg_green_30corn);
                            }

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
            public void onFailure(Call<AshesDetailPageMOdel> call, Throwable t) {
                dialog.dismiss();
                Log.e(TAG, "onResponse: " + t.getMessage());
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}