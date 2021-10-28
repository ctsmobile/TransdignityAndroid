package com.transdignity.driver.adapters;

import android.content.Context;
import androidx.databinding.DataBindingUtil;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.transdignity.driver.R;
import com.transdignity.driver.databinding.NotificationListDesignBinding;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {

    Context context;
    private LayoutInflater layoutInflater;

    public NotificationAdapter(Context context) {
        this.context = context;


    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(viewGroup.getContext());
        }
        NotificationListDesignBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.notification_list_design,viewGroup,false);

       // View view = LayoutInflater.from(context).inflate(R.layout.payment_list_design, viewGroup, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        NotificationListDesignBinding binding;
        public MyViewHolder(NotificationListDesignBinding binding) {
            super(binding.getRoot());
            this.binding =binding;
        }
    }

    @Override
    public int getItemCount() {
        return 10;
    }
}
