package com.cts.removalspecialist.fragments;

import android.content.Context;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cts.removalspecialist.MainActivity;
import com.cts.removalspecialist.R;
import com.cts.removalspecialist.databinding.FragmentPaymentDetailsBinding;
import com.cts.removalspecialist.utilities.CommonUtils;
import com.cts.removalspecialist.utilities.GlobalValues;


public class PaymentDetailsFragment extends Fragment {
    FragmentPaymentDetailsBinding binding;
    // TODO: Rename and change types and number of parameters
    public static PaymentDetailsFragment newInstance(String param1, String param2) {
        PaymentDetailsFragment fragment = new PaymentDetailsFragment();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_payment_details,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment;
                fragment = new PaymentFragment();
                String fragmtStatusTag = fragment.getClass().getName();
                GlobalValues.getInstance().setFramgentTag(fragmtStatusTag);
                ((MainActivity)getActivity()).selectedNavItem(R.id.navigation_payment);
                CommonUtils.loadFragment(getActivity(), fragment, fragmtStatusTag);
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
