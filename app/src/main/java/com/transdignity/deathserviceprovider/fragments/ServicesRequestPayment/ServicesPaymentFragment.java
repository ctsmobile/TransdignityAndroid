package com.transdignity.deathserviceprovider.fragments.ServicesRequestPayment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.transdignity.deathserviceprovider.MainActivity;
import com.transdignity.deathserviceprovider.R;
import com.transdignity.deathserviceprovider.fragments.HomeFragment;
import com.transdignity.deathserviceprovider.models.auth.LoginResponse;
import com.transdignity.deathserviceprovider.utilities.CommonUtils;
import com.transdignity.deathserviceprovider.utilities.GlobalValues;
import com.transdignity.deathserviceprovider.utilities.PreferenceManager;


public class ServicesPaymentFragment extends Fragment implements View.OnClickListener {
    View view;
    Context context;
    Activity activity;
    LoginResponse loginResponse;
    String requestid;
    TextView tv_add_more;
    LinearLayout ll_add_more_details;
    int count=0;
    TextView bt_pay;
    public ServicesPaymentFragment() {
        // Required empty public constructor
    }


    public static ServicesPaymentFragment newInstance(String id) {
        ServicesPaymentFragment fragment = new ServicesPaymentFragment();
        fragment.requestid = id;
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
        view= inflater.inflate(R.layout.fragment_services_payment, container, false);
        loginResponse = (LoginResponse) GlobalValues.getPreferenceManager().getObject(PreferenceManager.LoginResponseKey,LoginResponse.class);
        init();
        return view;
    }

    public void init(){
        tv_add_more=view.findViewById(R.id.tv_add_more);
        ll_add_more_details=view.findViewById(R.id.ll_add_more_details);
        bt_pay=view.findViewById(R.id.bt_pay);
        click();
    }
    public void click(){
        tv_add_more.setOnClickListener(this);
        bt_pay.setOnClickListener(this);
    }
    public void showAlert(){


        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View alertDialogView = inflater.inflate(R.layout.service_payment_complete_dialog, null);
        alertDialog.setView(alertDialogView);
        TextView bt_ok = alertDialogView.findViewById(R.id.bt_ok);
        bt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment;
                fragment = new HomeFragment();
                String fragmtStatusTag = fragment.getClass().getName();
                GlobalValues.getInstance().setFramgentTag(fragmtStatusTag);
                ((MainActivity) activity).selectedNavItem(R.id.navigation_home);
                CommonUtils.loadFragment(context, fragment, fragmtStatusTag);
                alertDialog.setCancelable(true);

            }
        });

        alertDialog.show();

    }

    @Override
    public void onClick(View v) {
       switch (v.getId()){
           case R.id.tv_add_more:
               if(count==0){
                   ll_add_more_details.setVisibility(View.VISIBLE);
                   count=1;
               }else if(count==1) {
                   ll_add_more_details.setVisibility(View.GONE);
                   count=0;
               }
           break;
           case R.id.bt_pay:
               showAlert();
               break;
       }
    }
}