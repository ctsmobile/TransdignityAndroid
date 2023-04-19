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

import com.transdignity.deathserviceprovider.MainActivity;
import com.transdignity.deathserviceprovider.R;
import com.transdignity.deathserviceprovider.fragments.ServicesDetailsFragments.CasketDetailsPageFragment;
import com.transdignity.deathserviceprovider.fragments.ServicesDetailsFragments.CourierDetailsPageFragment;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class CouriorViewDetailPageFragment extends Fragment {
    View view;
    String requestid;
    Context context;
    Activity activity;
    LoginResponse loginResponse;
    TextView tv_back,tv_pickup_location,tv_sender_name,tv_sender_mobile_no,tv_date_time,tv_drop_location,
            tv_receiver_name,tv_receiver_mobile_no,tv_description,tv_instruction,tv_date;
    public CouriorViewDetailPageFragment() {
        // Required empty public constructor
    }


    public static CouriorViewDetailPageFragment newInstance(String id) {
        CouriorViewDetailPageFragment fragment = new CouriorViewDetailPageFragment();
        fragment.requestid = id;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_courior_view_detail_page, container, false);
        loginResponse = (LoginResponse) GlobalValues.getPreferenceManager().getObject(PreferenceManager.LoginResponseKey,LoginResponse.class);
        init();
        return view;
    }

    public void init(){
        tv_back = view.findViewById(R.id.tv_back);
        tv_pickup_location = view.findViewById(R.id.tv_pickup_location);
        tv_sender_name = view.findViewById(R.id.tv_sender_name);
        tv_sender_mobile_no = view.findViewById(R.id.tv_sender_mobile_no);
        tv_date_time = view.findViewById(R.id.tv_date_time);
        tv_drop_location = view.findViewById(R.id.tv_drop_location);
        tv_receiver_name = view.findViewById(R.id.tv_receiver_name);
        tv_receiver_mobile_no = view.findViewById(R.id.tv_receiver_mobile_no);
        tv_description = view.findViewById(R.id.tv_description);
        tv_instruction = view.findViewById(R.id.tv_instruction);
        tv_date = view.findViewById(R.id.tv_date);
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment2;
                fragment2 =  CourierDetailsPageFragment.newInstance(requestid,"");
                String fragmtStatusTag = fragment2.getClass().getName();
                GlobalValues.getInstance().setFramgentTag(fragmtStatusTag);
                ((MainActivity) activity).selectedNavItem(R.id.navigation_home);
                CommonUtils.loadFragment(context, fragment2, fragmtStatusTag);
            }
        });
        getProfileDetails();
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
                            tv_sender_name.setText(name);
                            tv_pickup_location.setText(response.body().getData().getPickupLocation());
                            tv_drop_location.setText(response.body().getData().getDropLocation());
                            tv_date_time.setText(response.body().getData().getRequestDate());
                            tv_description.setText(response.body().getData().getDescription());
                            tv_instruction.setText(response.body().getData().getInstructuionToCarryitem());
                            tv_receiver_name.setText(response.body().getData().getRecieverFirstName());
                            tv_sender_mobile_no.setText(response.body().getData().getSenderFirstName());
                            tv_date.setText(response.body().getData().getRequestDate());
                            //tv_estimate_amount.setText(response.body().getData().getmTotalCharge());

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