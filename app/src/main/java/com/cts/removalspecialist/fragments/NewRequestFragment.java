package com.cts.removalspecialist.fragments;

import android.app.Activity;
import android.content.Context;

import androidx.databinding.DataBindingUtil;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cts.removalspecialist.MainActivity;
import com.cts.removalspecialist.models.auth.LoginResponse;
import com.cts.removalspecialist.models.requestDetails.RequestDetailsResponse;
import com.cts.removalspecialist.utilities.CommonUtils;
import com.cts.removalspecialist.utilities.GlobalValues;
import com.cts.removalspecialist.R;
import com.cts.removalspecialist.databinding.FragmentNewRequestBinding;
import com.cts.removalspecialist.utilities.PreferenceManager;

import java.util.ArrayList;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class NewRequestFragment extends Fragment implements View.OnClickListener {
    FragmentNewRequestBinding binding;
    Context context;
    Activity activity;
    String bodyRelease, personelEffect, extraItem, strHospital;
    Integer intHospitalId = 0;
    // List<HospitalTypeResponse.DataItem> hospitaltypeList = new ArrayList<>();
    String userId;
    LoginResponse loginResponse;
    RequestDetailsResponse.Data data;
    double latitude;
    double longitude;
    String latLonSource, latLongDestination, latLongStatus = "from";
    Boolean isViewMode = false;
    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 100;

    // TODO: Rename and change types and number of parameters
    public static NewRequestFragment newInstance(RequestDetailsResponse.Data data) {
        NewRequestFragment fragment = new NewRequestFragment();
        fragment.data = data;
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_new_request, container, false);
        View v = binding.getRoot();
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.llNewReqFirst.setVisibility(View.VISIBLE);
        binding.llNewReqSecond.setVisibility(View.GONE);
        binding.llNext.setOnClickListener(this);
        binding.llSubmit.setOnClickListener(this);
        binding.llCancel.setOnClickListener(this);
        binding.tvBack.setOnClickListener(this);
        loginResponse = (LoginResponse) GlobalValues.getPreferenceManager().getObject(PreferenceManager.LoginResponseKey, LoginResponse.class);

        if (loginResponse != null) {
            userId = loginResponse.getData().getId();
        }
        setFieldData();
        radioButtonManage(view);

    }

    void setFieldData() {
        if (data != null) {
            intHospitalId = Integer.parseInt(data.getHospitalTypeId());
            binding.etDate.setText(data.getFomatRequestDate());
            binding.etTimeRecieved.setText(data.getTimeReceived());
            binding.etTimeCompleted.setText(data.getTimeCompleted());
            binding.etBillTo.setText(data.getBillTo());
            binding.rtReqBy.setText(data.getRequestedBy());

            binding.etFirstName.setText(data.getDecendantFirstName());
            binding.etMidName.setText(data.getDecendantMiddleName());
            binding.etLastName.setText(data.getDecendantLastName());

            binding.etRemovedFromAddress.setText(data.getRemovedFromAddress());
            binding.etTransToAddress.setText(data.getTransferredToAddress());
            binding.etDateOfDeath.setText(data.getDateOfDeath());
            binding.etTimeOfDeath.setText(data.getTimeOfDeath());
            // binding.sprTypeOfHospital.setText(data.getTimeReceived());
            binding.etPhysisianName.setText(data.getPhysicianName());
            binding.etPhysisianPhoneNumber.setText(data.getPhysicianPhone());
            binding.etRelationship.setText(data.getNextOfKinRelationship());
            binding.etRelationshipPhoneNumber.setText(data.getNextOfKinPhone());
            binding.tvTypeOfHospital.setText(data.getHospitalType());
            binding.etDateOfBirth.setText(data.getDob());
            binding.etAge.setText(data.getAge());
            binding.etPersonelEffectList.setText(data.getPersonalEffectsLists());
            binding.etExtraList.setText(data.getRequestedItemsLists());
            if (data.getBodyRelease() != null) {
                if (data.getBodyRelease().equalsIgnoreCase("1")) {
                    ((RadioButton) binding.rgBodyRelease.getChildAt(0)).setChecked(true);
                } else {
                    ((RadioButton) binding.rgBodyRelease.getChildAt(1)).setChecked(true);
                }
            }
            if (data.getPersonalEffects() != null) {

                if (data.getPersonalEffects().equalsIgnoreCase("1")) {
                    ((RadioButton) binding.rgPersonelEffect.getChildAt(0)).setChecked(true);
                    binding.etPersonelEffectList.setVisibility(View.VISIBLE);
                } else {
                    ((RadioButton) binding.rgPersonelEffect.getChildAt(1)).setChecked(true);
                    binding.etPersonelEffectList.setVisibility(View.GONE);
                }
            }
            if (data.getRequestedItems() != null) {
                if (data.getRequestedItems().equalsIgnoreCase("1")) {
                    ((RadioButton) binding.rgExtraRequest.getChildAt(0)).setChecked(true);
                    binding.etExtraList.setVisibility(View.VISIBLE);
                } else {
                    ((RadioButton) binding.rgExtraRequest.getChildAt(1)).setChecked(true);
                    binding.etExtraList.setVisibility(View.GONE);
                }
            }

            binding.etDate.setFocusableInTouchMode(false);
            binding.etTimeRecieved.setFocusableInTouchMode(false);
            binding.etTimeCompleted.setFocusableInTouchMode(false);
            binding.etBillTo.setFocusableInTouchMode(false);
            binding.rtReqBy.setFocusableInTouchMode(false);
            binding.etFirstName.setFocusableInTouchMode(false);
            binding.etMidName.setFocusableInTouchMode(false);
            binding.etLastName.setFocusableInTouchMode(false);
            binding.etRemovedFromAddress.setFocusableInTouchMode(false);
            binding.etTransToAddress.setFocusableInTouchMode(false);
            binding.etTimeOfDeath.setFocusableInTouchMode(false);
            binding.etPhysisianName.setFocusableInTouchMode(false);
            binding.etPhysisianPhoneNumber.setFocusableInTouchMode(false);
            binding.etRelationship.setFocusableInTouchMode(false);
            binding.etRelationshipPhoneNumber.setFocusableInTouchMode(false);
            binding.etDateOfBirth.setFocusableInTouchMode(false);
            binding.etDateOfDeath.setFocusableInTouchMode(false);
            binding.etAge.setFocusableInTouchMode(false);
            binding.etPersonelEffectList.setFocusableInTouchMode(false);
            binding.etExtraList.setFocusableInTouchMode(false);

            binding.llSubmitCancel.setVisibility(View.INVISIBLE);

        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_next:
                binding.llNewReqFirst.setVisibility(View.GONE);
                binding.llNewReqSecond.setVisibility(View.VISIBLE);

                break;
            case R.id.ll_submit:
                successDialog(R.string.new_req_mag_sent, activity, context);
                break;
            case R.id.ll_cancel:
                Fragment fragmenthome = new HomeFragment();
                String fragmtStatusTag = fragmenthome.getClass().getName();
                GlobalValues.getInstance().setFramgentTag(fragmtStatusTag);
                ((MainActivity) activity).selectedNavItem(R.id.navigation_home);
                CommonUtils.loadFragment(context, fragmenthome, fragmtStatusTag);
                break;
            case R.id.tv_back:
                if (binding.llNewReqSecond.isShown()) {
                    binding.llNewReqFirst.setVisibility(View.VISIBLE);
                    binding.llNewReqSecond.setVisibility(View.GONE);
                } else {
                   /* Fragment fragment = DecendentDetailFragment.newInstance(data.getRequestId());
                    String fragmtStatusTag2 = fragment.getClass().getName();
                    GlobalValues.getInstance().setFramgentTag(fragmtStatusTag2);
                    ((MainActivity) activity).selectedNavItem(R.id.navigation_home);
                    CommonUtils.loadFragment(context, fragment, fragmtStatusTag2);*/
                }
                break;
        }
    }

    void radioButtonManage(View v) {

        RadioButton rbbodyRelease, rbPersonelEffect, rbExtraItem;
        int intbodyRelease, intPersonelEffect, intExtraItem;
        intbodyRelease = binding.rgBodyRelease.getCheckedRadioButtonId();
        intPersonelEffect = binding.rgPersonelEffect.getCheckedRadioButtonId();
        intExtraItem = binding.rgExtraRequest.getCheckedRadioButtonId();
        rbbodyRelease = (RadioButton) v.findViewById(intbodyRelease);
        rbPersonelEffect = (RadioButton) v.findViewById(intPersonelEffect);
        rbExtraItem = (RadioButton) v.findViewById(intExtraItem);
        bodyRelease = rbbodyRelease.getText().toString().trim();
        personelEffect = rbPersonelEffect.getText().toString().trim();
        extraItem = rbExtraItem.getText().toString().trim();
        binding.rgBodyRelease.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View radioButton = binding.rgBodyRelease.findViewById(checkedId);
                int index = binding.rgBodyRelease.indexOfChild(radioButton);
                switch (index) {
                    case 0: // first button
                        bodyRelease = "Yes";
                        break;
                    case 1: // secondbutton
                        bodyRelease = "No";
                        break;
                }
            }
        });
        binding.rgPersonelEffect.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View radioButton = binding.rgPersonelEffect.findViewById(checkedId);
                int index = binding.rgPersonelEffect.indexOfChild(radioButton);
                switch (index) {
                    case 0: // first button
                        personelEffect = "Yes";
                        binding.etPersonelEffectList.setVisibility(View.VISIBLE);
                        break;
                    case 1: // secondbutton
                        personelEffect = "No";
                        binding.etPersonelEffectList.setVisibility(View.GONE);
                        break;
                }
            }
        });
        binding.rgExtraRequest.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View radioButton = binding.rgExtraRequest.findViewById(checkedId);
                int index = binding.rgExtraRequest.indexOfChild(radioButton);
                switch (index) {
                    case 0: // first button
                        extraItem = "Yes";
                        binding.etExtraList.setVisibility(View.VISIBLE);
                        break;
                    case 1: // secondbutton
                        extraItem = "No";
                        binding.etExtraList.setVisibility(View.GONE);
                        break;
                }
            }
        });
      /*  Log.e(TAG, "radioButtonManage: rbbodyRelease.getText()>> " + bodyRelease);
        Log.e(TAG, "radioButtonManage: personelEffect>> " + personelEffect);
        Log.e(TAG, "radioButtonManage: extraItem>> " + extraItem);*/


    }

    public static boolean successDialog(int succesMsg, final Activity activity, final Context context) {


        LayoutInflater inflater = LayoutInflater.from(activity);
        View viewCustom = inflater.inflate(R.layout.forget_verifylink_sent_dialog, null);
        final AlertDialog dialog = new AlertDialog.Builder(activity).create();
        dialog.setView(viewCustom);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView succesText = viewCustom.findViewById(R.id.msg);
        succesText.setText(succesMsg);
        LinearLayout done = viewCustom.findViewById(R.id.ok_forgot);
        dialog.setCancelable(false);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Fragment fragment = new HomeFragment();
                String fragmtStatusTag = fragment.getClass().getName();
                GlobalValues.getInstance().setFramgentTag(fragmtStatusTag);
                ((MainActivity) activity).selectedNavItem(R.id.navigation_home);
                CommonUtils.loadFragment(context, fragment, fragmtStatusTag);
                //activity.overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
            }
        });
        dialog.show();
        return true;
    }
}
