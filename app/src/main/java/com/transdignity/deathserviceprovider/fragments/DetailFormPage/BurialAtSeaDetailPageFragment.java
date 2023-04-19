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
import com.transdignity.deathserviceprovider.fragments.ServicesDetailsFragments.BurialSeaDetailFragment;
import com.transdignity.deathserviceprovider.models.auth.LoginResponse;
import com.transdignity.deathserviceprovider.models.request.ServiceDetailPage.BurialSeaDetailPageModel;
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


public class BurialAtSeaDetailPageFragment extends Fragment {
    View view;
    String requestid;
    Context context;
    Activity activity;
    LoginResponse loginResponse;
    TextView tv_date,tv_back,tv_request_by,tv_date_of_request,tv_requester_contact_no,tv_name,tv_date_of_birth,
            tv_place_of_death,tv_time_of_death,tv_estimated_date,tv_transferred_address,tv_Descendant_Pick_address;

    public BurialAtSeaDetailPageFragment() {
        // Required empty public constructor
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
    public static BurialAtSeaDetailPageFragment newInstance(String id) {
        BurialAtSeaDetailPageFragment fragment = new BurialAtSeaDetailPageFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_burial_at_sea_detail_page, container, false);
        loginResponse = (LoginResponse) GlobalValues.getPreferenceManager().getObject(PreferenceManager.LoginResponseKey,LoginResponse.class);
        init();
        return view;
    }

    public void init(){
        tv_date =view.findViewById(R.id.tv_date);
        tv_back =view.findViewById(R.id.tv_back);
        tv_request_by =view.findViewById(R.id.tv_request_by);
        tv_date_of_request =view.findViewById(R.id.tv_date_of_request);
        tv_requester_contact_no =view.findViewById(R.id.tv_requester_contact_no);
        tv_name =view.findViewById(R.id.tv_name);
        tv_date_of_birth =view.findViewById(R.id.tv_date_of_birth);
        tv_place_of_death =view.findViewById(R.id.tv_place_of_death);
        tv_time_of_death =view.findViewById(R.id.tv_time_of_death);
        tv_estimated_date =view.findViewById(R.id.tv_estimated_date);
        tv_transferred_address =view.findViewById(R.id.tv_transferred_address);
        tv_Descendant_Pick_address =view.findViewById(R.id.tv_Descendant_Pick_address);
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment2;
                fragment2 =  BurialSeaDetailFragment.newInstance(requestid,"Home");
                String fragmtStatusTag = fragment2.getClass().getName();
                GlobalValues.getInstance().setFramgentTag(fragmtStatusTag);
                ((MainActivity) activity).selectedNavItem(R.id.navigation_home);
                CommonUtils.loadFragment(context, fragment2, fragmtStatusTag);
            }
        });
        getDetails();
    }

    void getDetails() {
        final LoadingProgressDialog dialog = new LoadingProgressDialog(context, "Please wait...");
        dialog.setCancelable(false);
        dialog.show();

        ApiInterface apiInterface = ApiClients.getClient(context).create(ApiInterface.class);
        Call<BurialSeaDetailPageModel> call = apiInterface.getRequestBurialAtSeaApi(loginResponse.getData().getToken(), requestid);
        call.enqueue(new Callback<BurialSeaDetailPageModel>() {
            @Override
            public void onResponse(Call<BurialSeaDetailPageModel> call, Response<BurialSeaDetailPageModel> response) {
                try {
                    dialog.dismiss();
                    if (response.code() >= 200 && response.code() <= 210) {
                        if (response.body().getSuccess().equalsIgnoreCase("true")) {
                            String name = "";
                            tv_date.setText(response.body().getData().getRequestDate());
                            tv_request_by.setText(response.body().getData().getRequestedBy());
                            tv_date_of_request.setText(response.body().getData().getRequestDate());
                            tv_requester_contact_no.setText(response.body().getData().getRequestorContactNumber());
                            tv_name.setText(response.body().getData().getDecendentName());
                            tv_date_of_birth.setText(response.body().getData().getDob());
                            tv_place_of_death.setText(response.body().getData().getPlaceOfDeath());
                            tv_time_of_death.setText(response.body().getData().getTimeOfDeath());
                            tv_estimated_date.setText(response.body().getData().getEstimatedDateArrivalOfDecendent());
                            tv_transferred_address.setText(response.body().getData().getTransferredAddress());
                            tv_Descendant_Pick_address.setText(response.body().getData().getDecendentPickedaddressOfbody());


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
            public void onFailure(Call<BurialSeaDetailPageModel> call, Throwable t) {
                dialog.dismiss();
                Log.e(TAG, "onResponse: " + t.getMessage());
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}