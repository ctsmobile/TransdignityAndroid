package com.transdignity.deathserviceprovider.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.transdignity.deathserviceprovider.MainActivity;
import com.transdignity.deathserviceprovider.R;
import com.transdignity.deathserviceprovider.databinding.FragmentDecendentDetailBinding;
import com.transdignity.deathserviceprovider.fragments.DetailFormPage.DescendantViewDetailPageFragment;
import com.transdignity.deathserviceprovider.fragments.DetailFormPage.LimoDetailsFormPageFragment;
import com.transdignity.deathserviceprovider.fragments.ServicesRequestPayment.ServicesPaymentFragment;
import com.transdignity.deathserviceprovider.fragments.bankDetailsFragments.ClientPaymentIntegrationFragment;
import com.transdignity.deathserviceprovider.models.auth.LoginResponse;
import com.transdignity.deathserviceprovider.models.request.ServiceDetailPage.DescendantDetailPageModel;
import com.transdignity.deathserviceprovider.models.requestDetails.Data;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class DecendentDetailFragment extends Fragment implements View.OnClickListener {
    Context context;
    Activity activity;
    String requestid, descMobNo, rsMobno, cdMobNo,pageScreen;
    FragmentDecendentDetailBinding binding;
    Data data;
    LoginResponse loginResponse;
    TextView tv_estimate_amount;
    String str_estimate_amount;
    public DecendentDetailFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static DecendentDetailFragment newInstance(String id,String screen) {
        DecendentDetailFragment fragment = new DecendentDetailFragment();
        fragment.requestid = id;
        fragment.pageScreen = id;
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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_decendent_detail, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // takePictureButton.setEnabled(false);
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 0);
        }
        loginResponse = (LoginResponse)GlobalValues.getPreferenceManager().getObject(PreferenceManager.LoginResponseKey,LoginResponse.class);

        binding.tvBack.setOnClickListener(this);
        binding.llPayNow.setOnClickListener(this);
        binding.tvDetailFormArrow.setOnClickListener(this);
        binding.llPickupTrack.setOnClickListener(this);
        binding.tvDectCallBtn.setOnClickListener(this);
        binding.tvRemovalCallBtn.setOnClickListener(this);
        binding.tvCabDriverCallBtn.setOnClickListener(this);
        binding.llDetailView.setOnClickListener(this);
        getProfileDetails();
    }


    void getProfileDetails() {
        final LoadingProgressDialog dialog = new LoadingProgressDialog(context, "Please wait...");
        dialog.setCancelable(false);
        dialog.show();

        ApiInterface apiInterface = ApiClients.getClient(context).create(ApiInterface.class);
        Call<DescendantDetailPageModel> call = apiInterface.getRequestDetailsApi(loginResponse.getData().getToken(), requestid);
        call.enqueue(new Callback<DescendantDetailPageModel>() {
            @Override
            public void onResponse(Call<DescendantDetailPageModel> call, Response<DescendantDetailPageModel> response) {
                try {
                    dialog.dismiss();
                    if (response.code() >= 200 && response.code() <= 210) {
                        if (response.body().getSuccess().equalsIgnoreCase("true")) {
                            Log.e(TAG, "onResponse: " + response.body().getMessage());
                           // data = response.body().getData();
                            //Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            String name = "";
                            if (response.body().getData().getDecendantFirstName() != null) {
                                name = name + response.body().getData().getDecendantFirstName();
                            }
                            if (response.body().getData().getDecendantMiddleName() != null) {
                                name = name + " " + response.body().getData().getDecendantMiddleName();
                            }
                            if (response.body().getData().getDecendantLastName() != null) {
                                name = name + " " + response.body().getData().getDecendantLastName();
                            }

                            binding.tvDecendentName.setText(name);
                            binding.tvRemovedFromAddress.setText(response.body().getData().getRemovedFromAddress());
                            binding.tvTransferToAddress.setText(response.body().getData().getTransferredToAddress());
                            binding.tvStatusRunning.setText(response.body().getData().getDspStatus());
                            binding.tvDate.setText(response.body().getData().getFomatRequestDate());
                            binding.tvTime.setText(response.body().getData().getFormatTimeOfDeath());
                            binding.tvDectMobileNo.setText(response.body().getData().getNextOfKinPhone());
                            binding.tvEstimateAmount.setText("$"+" "+response.body().getData().getTotalCharge());
                            str_estimate_amount=response.body().getData().getTotalCharge();
                            descMobNo = response.body().getData().getNextOfKinPhone();
                            rsMobno = response.body().getData().getRsPhone();
                            cdMobNo = response.body().getData().getCdPhone();
                            if(response.body().getData().getStartpaymentdetail()!=null){
                                String payment_status=response.body().getData().getStartpaymentdetail().getPaymentStatus().toString();
                                if(payment_status.equals("pending")){
                                    binding.llPayNow.setVisibility(View.VISIBLE);
                                }else if(payment_status.equals("complete")){
                                    binding.llPayNow.setVisibility(View.GONE);

                                }
                            }else {
                                binding.llPayNow.setVisibility(View.GONE);

                            }
                            if (response.body().getData().getDspStatus().equalsIgnoreCase("Pending")) {
                                // holder.binding.tvStatus.setBackgroundTintList(ColorStateList.valueOf(R.color.colorPrimaryDark));
                                binding.tvStatusRunning.setBackgroundResource(R.drawable.bg_primary_30corn);

                            } else if (response.body().getData().getDspStatus().equalsIgnoreCase("Rejected")) {
                                binding.tvStatusRunning.setBackgroundResource(R.drawable.bg_red_30corn);
                            }  else if (response.body().getData().getDspStatus().equalsIgnoreCase("Completed")) {
                                binding.tvStatusRunning.setBackgroundResource(R.drawable.bg_red_30corn);
                            }
                            else {
                                binding.tvStatusRunning.setBackgroundResource(R.drawable.bg_green_30corn);
                            }

                            if (response.body().getData().getRsName() != null) {
                                binding.llRemovalSplDetails.setVisibility(View.VISIBLE);
                                binding.tvRemovalSplName.setText(response.body().getData().getRsName());
                            } else {
                                binding.llRemovalSplDetails.setVisibility(View.GONE);
                            }
                            if (response.body().getData().getCdName() != null) {
                                binding.tvCabDriverName.setText(response.body().getData().getCdName());
                                binding.llCabDriverDetails.setVisibility(View.VISIBLE);
                            } else {
                                binding.llCabDriverDetails.setVisibility(View.GONE);
                            }
                            if (response.body().getData().getCabNo() != null) {
                                binding.tvCabName.setText(response.body().getData().getCabName());
                                binding.tvCabNumber.setText(response.body().getData().getCabNo());
                                binding.llCabsDetails.setVisibility(View.VISIBLE);
                                binding.llEstimateAmount.setVisibility(View.VISIBLE);
                               // binding.llPayNow.setVisibility(View.VISIBLE);
                            } else {
                                binding.llCabsDetails.setVisibility(View.GONE);
                                binding.llEstimateAmount.setVisibility(View.GONE);
                               // binding.llPayNow.setVisibility(View.GONE);

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
            public void onFailure(Call<DescendantDetailPageModel> call, Throwable t) {
                dialog.dismiss();
                Log.e(TAG, "onResponse: " + t.getMessage());
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tv_back:
                Fragment fragment5;
                fragment5 = new HomeFragment();
                String fragmtStatusTag = fragment5.getClass().getName();
                GlobalValues.getInstance().setFramgentTag(fragmtStatusTag);
                ((MainActivity) activity).selectedNavItem(R.id.navigation_home);
                CommonUtils.loadFragment(context, fragment5, fragmtStatusTag);
               /* if(pageScreen.equalsIgnoreCase("Home")){
                    Fragment fragment2;
                    fragment2 = new HomeFragment();
                    String fragmtStatusTag = fragment2.getClass().getName();
                    GlobalValues.getInstance().setFramgentTag(fragmtStatusTag);
                    ((MainActivity) activity).selectedNavItem(R.id.navigation_home);
                    CommonUtils.loadFragment(context, fragment2, fragmtStatusTag);
                }else if(pageScreen.equalsIgnoreCase("Descendent")){
                    Fragment fragment2;
                    fragment2 = new NewRequestFragment();
                    String fragmtStatusTag = fragment2.getClass().getName();
                    GlobalValues.getInstance().setFramgentTag(fragmtStatusTag);
                    ((MainActivity) activity).selectedNavItem(R.id.navigation_home);
                    CommonUtils.loadFragment(context, fragment2, fragmtStatusTag);
                }*/
                break;

            case R.id.ll_pay_now:
             /*   Fragment fragment3 = ServicesPaymentFragment.newInstance(requestid);
                String fragmtStatusTag3 = fragment3.getClass().getName();
                GlobalValues.getInstance().setFramgentTag(fragmtStatusTag3);
                ((MainActivity) activity).selectedNavItem(R.id.navigation_home);
                CommonUtils.loadFragment(context, fragment3, fragmtStatusTag3); */
                Fragment fragment3 = ClientPaymentIntegrationFragment.newInstance(requestid,str_estimate_amount,"0");
                String fragmtStatusTag3 = fragment3.getClass().getName();
                GlobalValues.getInstance().setFramgentTag(fragmtStatusTag3);
                ((MainActivity) activity).selectedNavItem(R.id.navigation_home);
                CommonUtils.loadFragment(context, fragment3, fragmtStatusTag3);
                break;
            case R.id.tv_detail_form_arrow:
                Fragment fragment1 = DescendantViewDetailPageFragment.newInstance(requestid);
                String fragmtStatusTag1 = fragment1.getClass().getName();
                GlobalValues.getInstance().setFramgentTag(fragmtStatusTag1);
                ((MainActivity) activity).selectedNavItem(R.id.navigation_home);
                CommonUtils.loadFragment(context, fragment1, fragmtStatusTag1);
                break;
            case R.id.ll_detail_view:
                Fragment fragment4 = DescendantViewDetailPageFragment.newInstance(requestid);
                String fragmtStatusTag4 = fragment4.getClass().getName();
                GlobalValues.getInstance().setFramgentTag(fragmtStatusTag4);
                ((MainActivity) activity).selectedNavItem(R.id.navigation_home);
                CommonUtils.loadFragment(context, fragment4, fragmtStatusTag4);
                break;

            case R.id.ll_pickup_track:
                Fragment fragment2 = TrackFragment.newInstance(requestid);
                String fragmtStatusTag2 = fragment2.getClass().getName();
                GlobalValues.getInstance().setFramgentTag(fragmtStatusTag2);
                //((MainActivity) activity).selectedNavItem(R.id.navigation_home);
                CommonUtils.loadFragment(context, fragment2, fragmtStatusTag2);
                break;

            case R.id.tv_dect_call_btn:
                try {
                    if (descMobNo != null) {
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:" + descMobNo));
                        startActivity(callIntent);
                    } else {
                        Toast.makeText(context, "Invalid Mobile Number", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                }
                break;
            case R.id.tv_removal_call_btn:
                try {
                    if (rsMobno != null) {
                        Intent callrsIntent = new Intent(Intent.ACTION_CALL);
                        callrsIntent.setData(Uri.parse("tel:" + rsMobno));
                        startActivity(callrsIntent);
                    } else {
                        Toast.makeText(context, "Invalid Mobile Number", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                }
                break;
            case R.id.tv_cab_driver_call_btn:
                try {
                    if (cdMobNo != null) {
                        Intent callcdIntent = new Intent(Intent.ACTION_CALL);
                        callcdIntent.setData(Uri.parse("tel:" + cdMobNo));
                        startActivity(callcdIntent);
                    } else {
                        Toast.makeText(context, "Invalid Mobile Number", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                }
                break;

        }
    }
}
