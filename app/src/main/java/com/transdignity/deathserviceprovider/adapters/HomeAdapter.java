package com.transdignity.deathserviceprovider.adapters;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;

import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.transdignity.deathserviceprovider.R;
import com.transdignity.deathserviceprovider.databinding.HomeListDesignBinding;
import com.transdignity.deathserviceprovider.fragments.DecendentDetailFragment;
import com.transdignity.deathserviceprovider.fragments.ServicesDetailsFragments.AirportRemovalDetailsFragment;
import com.transdignity.deathserviceprovider.fragments.ServicesDetailsFragments.AshesDetiailsPageFragment;
import com.transdignity.deathserviceprovider.fragments.ServicesDetailsFragments.BurialSeaDetailFragment;
import com.transdignity.deathserviceprovider.fragments.ServicesDetailsFragments.CasketDetailsPageFragment;
import com.transdignity.deathserviceprovider.fragments.ServicesDetailsFragments.CourierDetailsPageFragment;
import com.transdignity.deathserviceprovider.fragments.ServicesDetailsFragments.FlowerDetailPageFragment;
import com.transdignity.deathserviceprovider.fragments.ServicesDetailsFragments.LimoDetailPageFragment;
import com.transdignity.deathserviceprovider.fragments.ServicesDetailsFragments.UsVeteranDeatilFragment;
import com.transdignity.deathserviceprovider.models.request.DataItem;
import com.transdignity.deathserviceprovider.utilities.CommonUtils;
import com.transdignity.deathserviceprovider.utilities.GlobalValues;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {

    Context context;
    Activity activity;
    private LayoutInflater layoutInflater;
    private List<DataItem> dataItems;

    public HomeAdapter(List<DataItem> dataItems, Context context, Activity activity) {
        this.dataItems = dataItems;
        this.context = context;
        this.activity = activity;
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
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") final int i) {
        holder.binding.decedentName.setText(dataItems.get(i).getDecendantName());
        holder.binding.tvStatus.setText(dataItems.get(i).getStatus());
        holder.binding.tvRemoveFrom.setText(dataItems.get(i).getRemovedFromAddress());
        holder.binding.tvTransferTo.setText(dataItems.get(i).getTransferredToAddress());
       // holder.binding.tvDate.setText(dataItems.get(i).getRequestDate());
        String str_request_date = dataItems.get(i).getDBrequestDatetime();
        try {
            DateFormat f = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
            DateFormat nf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss", Locale.ENGLISH);
            nf.setTimeZone(TimeZone.getTimeZone("UTC"));
           // Toast.makeText(context, ""+str_request_date, Toast.LENGTH_SHORT).show();

            //String new_local_date=f.format(str_request_date);
            //Log.d("local",new_local_date);
            Date d = nf.parse(str_request_date);
            nf.setTimeZone(TimeZone.getDefault());

            DateFormat date = new SimpleDateFormat("d MMM yyyy");
            DateFormat time = new SimpleDateFormat("hh:mm aa");
            holder.binding.tvDate.setText(date.format(d));
            holder.binding.tvTime.setText(time.format(d));
            System.out.println("Date: " + date.format(d));
            System.out.println("Time: " + time.format(d));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (dataItems.get(i).getStatus().equalsIgnoreCase("Pending")) {
            // holder.binding.tvStatus.setBackgroundTintList(ColorStateList.valueOf(R.color.colorPrimaryDark));
            holder.binding.tvStatus.setBackgroundResource(R.drawable.bg_primary_30corn);

        } else if (dataItems.get(i).getStatus().equalsIgnoreCase("Rejected")) {
            holder.binding.tvStatus.setBackgroundResource(R.drawable.bg_red_30corn);
        }else if (dataItems.get(i).getStatus().equalsIgnoreCase("Completed")) {
            holder.binding.tvStatus.setBackgroundResource(R.drawable.bg_red_30corn);
        } else {
            holder.binding.tvStatus.setBackgroundResource(R.drawable.bg_green_30corn);
        }

        holder.binding.llMainContainerHomeDesign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dataItems.get(i).getmServiceId().equalsIgnoreCase("1")){
                    Fragment fragment = DecendentDetailFragment.newInstance(dataItems.get(i).getRequestId(),"Home");
                    String tagFragment = fragment.getClass().getName();
                    GlobalValues.getInstance().setFramgentTag(tagFragment);
                    CommonUtils.loadFragment(context, fragment, tagFragment);
                }else  if(dataItems.get(i).getmServiceId().equalsIgnoreCase("2")){
                    Fragment fragment = LimoDetailPageFragment.newInstance(dataItems.get(i).getRequestId(),"Home");
                    String tagFragment = fragment.getClass().getName();
                    GlobalValues.getInstance().setFramgentTag(tagFragment);
                    CommonUtils.loadFragment(context, fragment, tagFragment);
                }else  if(dataItems.get(i).getmServiceId().equalsIgnoreCase("3")){
                    Fragment fragment = CasketDetailsPageFragment.newInstance(dataItems.get(i).getRequestId(),"Home");
                    String tagFragment = fragment.getClass().getName();
                    GlobalValues.getInstance().setFramgentTag(tagFragment);
                    CommonUtils.loadFragment(context, fragment, tagFragment);
                }else  if(dataItems.get(i).getmServiceId().equalsIgnoreCase("4")){
                    Fragment fragment = FlowerDetailPageFragment.newInstance(dataItems.get(i).getRequestId(),"Home");
                    String tagFragment = fragment.getClass().getName();
                    GlobalValues.getInstance().setFramgentTag(tagFragment);
                    CommonUtils.loadFragment(context, fragment, tagFragment);
                }else  if(dataItems.get(i).getmServiceId().equalsIgnoreCase("5")){
                    Fragment fragment = AshesDetiailsPageFragment.newInstance(dataItems.get(i).getRequestId(),"Home");
                    String tagFragment = fragment.getClass().getName();
                    GlobalValues.getInstance().setFramgentTag(tagFragment);
                    CommonUtils.loadFragment(context, fragment, tagFragment);
                }else  if(dataItems.get(i).getmServiceId().equalsIgnoreCase("6")){
                    Fragment fragment = CourierDetailsPageFragment.newInstance(dataItems.get(i).getRequestId(),"Home");
                    String tagFragment = fragment.getClass().getName();
                    GlobalValues.getInstance().setFramgentTag(tagFragment);
                    CommonUtils.loadFragment(context, fragment, tagFragment);
                }else  if(dataItems.get(i).getmServiceId().equalsIgnoreCase("7")){
                    Fragment fragment = UsVeteranDeatilFragment.newInstance(dataItems.get(i).getRequestId(),"Home");
                    String tagFragment = fragment.getClass().getName();
                    GlobalValues.getInstance().setFramgentTag(tagFragment);
                    CommonUtils.loadFragment(context, fragment, tagFragment);
                }else  if(dataItems.get(i).getmServiceId().equalsIgnoreCase("8")){
                    Fragment fragment = AirportRemovalDetailsFragment.newInstance(dataItems.get(i).getRequestId(),"Home");
                    String tagFragment = fragment.getClass().getName();
                    GlobalValues.getInstance().setFramgentTag(tagFragment);
                    CommonUtils.loadFragment(context, fragment, tagFragment);
                }else  if(dataItems.get(i).getmServiceId().equalsIgnoreCase("9")){
                    Fragment fragment = BurialSeaDetailFragment.newInstance(dataItems.get(i).getRequestId(),"Home");
                    String tagFragment = fragment.getClass().getName();
                    GlobalValues.getInstance().setFramgentTag(tagFragment);
                    CommonUtils.loadFragment(context, fragment, tagFragment);
                }

            }
        });

        if(dataItems.get(i).getmServiceId().equalsIgnoreCase("1")){
            holder.binding.tvServiceName.setText("Descendent Removal Transport");
            holder.binding.llView.setVisibility(View.VISIBLE);
            holder.binding.llDrop.setVisibility(View.VISIBLE);
        }else  if(dataItems.get(i).getmServiceId().equalsIgnoreCase("2")){
            holder.binding.tvServiceName.setText("Limo Transport");
            holder.binding.llView.setVisibility(View.VISIBLE);
            holder.binding.llDrop.setVisibility(View.VISIBLE);

        }else  if(dataItems.get(i).getmServiceId().equalsIgnoreCase("3")){
            holder.binding.tvServiceName.setText("Casket Pickup & Delivery");
            holder.binding.llView.setVisibility(View.VISIBLE);
            holder.binding.llDrop.setVisibility(View.VISIBLE);
        }else  if(dataItems.get(i).getmServiceId().equalsIgnoreCase("4")){
            holder.binding.tvServiceName.setText("Flower Pickup & Delivery");
            holder.binding.llView.setVisibility(View.VISIBLE);
            holder.binding.llDrop.setVisibility(View.VISIBLE);
        }else  if(dataItems.get(i).getmServiceId().equalsIgnoreCase("5")){
            holder.binding.tvServiceName.setText("Ashes & URN Pickup Delivery");
            holder.binding.llView.setVisibility(View.VISIBLE);
            holder.binding.llDrop.setVisibility(View.VISIBLE);
        }else  if(dataItems.get(i).getmServiceId().equalsIgnoreCase("6")){
            holder.binding.tvServiceName.setText("Courier Transfer");
            holder.binding.llView.setVisibility(View.VISIBLE);
            holder.binding.llDrop.setVisibility(View.VISIBLE);
        }else  if(dataItems.get(i).getmServiceId().equalsIgnoreCase("7")){
            holder.binding.tvServiceName.setText("US Veteran/first Responder");
            holder.binding.llView.setVisibility(View.VISIBLE);
            holder.binding.llDrop.setVisibility(View.VISIBLE);
        }else  if(dataItems.get(i).getmServiceId().equalsIgnoreCase("8")){
            holder.binding.tvServiceName.setText("Airport Arrival/Departure");
            holder.binding.llView.setVisibility(View.VISIBLE);
            holder.binding.llDrop.setVisibility(View.VISIBLE);
        }else  if(dataItems.get(i).getmServiceId().equalsIgnoreCase("9")){
            holder.binding.tvServiceName.setText("Burial at the Sea Boat");
            holder.binding.llView.setVisibility(View.VISIBLE);
            holder.binding.llDrop.setVisibility(View.VISIBLE);
        }
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
