package com.transdignity.deathserviceprovider.adapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import androidx.databinding.DataBindingUtil;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.transdignity.deathserviceprovider.R;
import com.transdignity.deathserviceprovider.databinding.PaymentListDesignBinding;

import java.util.ArrayList;
import java.util.List;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.MyViewHolder> {

    Context context;
    Activity activity;
    List<String> datalist;
    private LayoutInflater layoutInflater;

    public PaymentAdapter(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
        datalist = new ArrayList<>();
    }

    public void getData(List<String> datalist){
        this.datalist = datalist;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(viewGroup.getContext());
        }
        PaymentListDesignBinding binding= DataBindingUtil.inflate(layoutInflater,R.layout.payment_list_design, viewGroup, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
           /* if (i == 0){
                holder.binding.cvContainer.setPadding(0,20,0,5);
            }*/
        holder.binding.tvRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(true);
                dialog.setContentView(R.layout.rating_dialog_view);
                dialog.show();

            }
        });
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        PaymentListDesignBinding binding;
        public MyViewHolder(@NonNull PaymentListDesignBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;

        }
    }

    @Override
    public int getItemCount() {
        return 10;
    }
}



