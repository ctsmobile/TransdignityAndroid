package com.cts.removalspecialist.adapters;

import android.content.Context;
import androidx.databinding.DataBindingUtil;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.cts.removalspecialist.R;
import com.cts.removalspecialist.databinding.NotificationListDesignBinding;
import com.cts.removalspecialist.models.notifications.NotificationListResponse;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {

    Context context;
    private LayoutInflater layoutInflater;
    List<NotificationListResponse.DataItem> dataItems;

    public NotificationAdapter(List<NotificationListResponse.DataItem> dataItems, Context context) {
        this.context = context;
        this.dataItems = dataItems;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(viewGroup.getContext());
        }
        NotificationListDesignBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.notification_list_design, viewGroup, false);

        // View view = LayoutInflater.from(context).inflate(R.layout.payment_list_design, viewGroup, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        holder.binding.tvMesaage.setText(dataItems.get(i).getNotificationMessage());
//holder.binding.tvDate.setText(dataItems.get(i).);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        NotificationListDesignBinding binding;

        public MyViewHolder(NotificationListDesignBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @Override
    public int getItemCount() {
        return dataItems.size();
    }
}

