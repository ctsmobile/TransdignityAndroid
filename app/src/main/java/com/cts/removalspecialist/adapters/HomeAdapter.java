package com.cts.removalspecialist.adapters;

import android.app.Activity;
import android.content.Context;

import androidx.databinding.DataBindingUtil;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cts.removalspecialist.fragments.viewDetails.AirportRemovalDetailPageFragment;
import com.cts.removalspecialist.models.requestList.RequestListResponse;
import com.cts.removalspecialist.utilities.CommonUtils;
import com.cts.removalspecialist.utilities.GlobalValues;
import com.cts.removalspecialist.R;
import com.cts.removalspecialist.databinding.HomeListDesignBinding;
import com.cts.removalspecialist.fragments.DecendentDetailFragment;

import java.util.List;


public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {

    Context context;
    Activity activity;
    List<RequestListResponse.DataItem> dataItems;
    ChangeStatusPos changeStatusPos;
    private LayoutInflater layoutInflater;

    public HomeAdapter(List<RequestListResponse.DataItem> dataItems, Context context, Activity activity,ChangeStatusPos changeStatusPos) {
        this.dataItems = dataItems;
        this.context = context;
        this.activity = activity;
        this.changeStatusPos = changeStatusPos;
    }
    public  interface ChangeStatusPos{
        void changeStatus(String requestId,String status);
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(viewGroup.getContext());
        }
        HomeListDesignBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.home_list_design, viewGroup, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int i) {

        String name = "";
        if (dataItems.get(i).getDecendantFirstName() != null) {
            name = name + dataItems.get(i).getDecendantFirstName();
        }
        if (dataItems.get(i).getDecendantMiddleName() != null) {
            name = name + " " + dataItems.get(i).getDecendantMiddleName();
        }
        if (dataItems.get(i).getDecendantLastName() != null) {
            name = name + " " + dataItems.get(i).getDecendantLastName();
        }
        holder.binding.tvDecendentName.setText(name);
        holder.binding.tvRemovedFrom.setText(dataItems.get(i).getRemovedFromAddress());
        holder.binding.tvTransferToAddress.setText(dataItems.get(i).getTransferredToAddress());
        holder.binding.tvDate.setText(dataItems.get(i).getRequestDate());
        holder.binding.tvTime.setText(dataItems.get(i).getTimeOfDeath());
        if(dataItems.get(i).getServiceId().equalsIgnoreCase("1")){
            holder.binding.tvServiceName.setText("Service Name : Descendant Removal Transport ");

        }else if(dataItems.get(i).getServiceId().equalsIgnoreCase("5")){
            holder.binding.tvServiceName.setText("Service Name : Ashes & URN Pickup");

        }else if(dataItems.get(i).getServiceId().equalsIgnoreCase("7")){
            holder.binding.tvServiceName.setText("Service Name : US Veteran/First Responder");

        }else if(dataItems.get(i).getServiceId().equalsIgnoreCase("8")){
            holder.binding.tvServiceName.setText("Service Name : Airport Arrival/Departure");

        }else if(dataItems.get(i).getServiceId().equalsIgnoreCase("9")){
            holder.binding.tvServiceName.setText("Service Name : Burial at the sea");

        }

        if (dataItems.get(i).getCdName() != null) {
            holder.binding.llDriverDetails.setVisibility(View.VISIBLE);
            holder.binding.tvCabDriverName.setText(dataItems.get(i).getCdName());
        } else {
            holder.binding.llDriverDetails.setVisibility(View.GONE);
        }
        if (dataItems.get(i).getCabName() != null) {
            holder.binding.llCabDetails.setVisibility(View.VISIBLE);
            holder.binding.tvCabName.setText(dataItems.get(i).getCabName());
            holder.binding.tvCabNumber.setText(dataItems.get(i).getCabNo());
        } else {
            holder.binding.llCabDetails.setVisibility(View.GONE);
        }

        holder.binding.tvStatusRunning.setText(dataItems.get(i).getRsStatus());
        holder.binding.tvStatusRunning.setVisibility(View.VISIBLE);
        if (dataItems.get(i).getRsStatus().equalsIgnoreCase("Pending")) {
            // holder.binding.tvStatus.setBackgroundTintList(ColorStateList.valueOf(R.color.colorPrimaryDark));
            holder.binding.tvStatusRunning.setBackgroundResource(R.drawable.bg_primary_30corn);
            holder.binding.llAcceptRejectBtn.setVisibility(View.VISIBLE);

        }  else if (dataItems.get(i).getRsStatus().equalsIgnoreCase("accepted")) {
            holder.binding.tvStatusRunning.setBackgroundResource(R.drawable.bg_primary_30corn);
            holder.binding.llAcceptRejectBtn.setVisibility(View.GONE);

        }else if (dataItems.get(i).getRsStatus().equalsIgnoreCase("Rejected")) {
            holder.binding.tvStatusRunning.setBackgroundResource(R.drawable.bg_red_30corn);
            holder.binding.llAcceptRejectBtn.setVisibility(View.GONE);

        } else if (dataItems.get(i).getRsStatus().equalsIgnoreCase("Completed")) {
            holder.binding.tvStatusRunning.setBackgroundResource(R.drawable.bg_red_30corn);
            holder.binding.llAcceptRejectBtn.setVisibility(View.GONE);
        } else if (dataItems.get(i).getRsStatus().equalsIgnoreCase("Ongoing")) {
            holder.binding.tvStatusRunning.setBackgroundResource(R.drawable.bg_green_30corn);
            holder.binding.llAcceptRejectBtn.setVisibility(View.GONE);
        } else if (dataItems.get(i).getRsStatus().equalsIgnoreCase("Cancel")) {
            holder.binding.tvStatusRunning.setBackgroundResource(R.drawable.bg_red_30corn);
            holder.binding.llAcceptRejectBtn.setVisibility(View.GONE);
        }

        final String cdMobileNo = dataItems.get(i).getCdPhone();
        holder.binding.llMainContainerHomeDesign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = DecendentDetailFragment.newInstance(dataItems.get(i).getRequestId(),dataItems.get(i).getServiceId());
                String tagFragment = fragment.getClass().getName();
                GlobalValues.getInstance().setFramgentTag(tagFragment);
                CommonUtils.loadFragment(context, fragment, tagFragment);
            }
        });
        holder.binding.llAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeStatusPos.changeStatus(dataItems.get(i).getRequestId(),"accept");
            }
        });
        holder.binding.llReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeStatusPos.changeStatus(dataItems.get(i).getRequestId(),"reject");
            }
        });
        holder.binding.llCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (cdMobileNo != null) {
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:" + cdMobileNo));
                        context.startActivity(callIntent);
                    } else {
                        Toast.makeText(context, "Mobile Number is not available", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        HomeListDesignBinding binding;

        public MyViewHolder(@NonNull HomeListDesignBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }
    }

    @Override
    public int getItemCount() {
        return dataItems.size();
    }
}
