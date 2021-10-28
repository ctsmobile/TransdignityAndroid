package com.cts.removalspecialist.adapters;

import android.app.Activity;
import android.content.Context;
import androidx.databinding.DataBindingUtil;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cts.removalspecialist.R;
import com.cts.removalspecialist.databinding.PaymentListDesignBinding;
import com.cts.removalspecialist.fragments.PaymentDetailsFragment;
import com.cts.removalspecialist.utilities.CommonUtils;
import com.cts.removalspecialist.utilities.GlobalValues;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.MyViewHolder> {

    Context context;
    Activity activity;
    private LayoutInflater layoutInflater;

    public PaymentAdapter(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(viewGroup.getContext());
        }
        PaymentListDesignBinding binding = DataBindingUtil.inflate(layoutInflater,R.layout.payment_list_design,viewGroup,false);

       // View view = LayoutInflater.from(context).inflate(R.layout.payment_list_design, viewGroup, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
           /* holder.binding.llMian.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment fragment = new PaymentDetailsFragment();
                    String tagFragment = fragment.getClass().getName();
                    GlobalValues.getInstance().setFramgentTag(tagFragment);
                    CommonUtils.loadFragment(context, fragment, tagFragment);
                }
            });*/
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        PaymentListDesignBinding binding;
        public MyViewHolder(PaymentListDesignBinding binding) {
            super(binding.getRoot());
            this.binding =binding;
        }
    }

    @Override
    public int getItemCount() {
        return 10;
    }
}
