package com.transdignity.driver.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.transdignity.driver.R;
import com.transdignity.driver.models.payment.EarningListModel;

import java.util.List;


public class EarningListAdapter extends RecyclerView.Adapter<EarningListAdapter.MyViewHolder>
{
    private Context mContext;
    private List<EarningListModel.Datum> dataItems;
    View view;


    public EarningListAdapter(Context context, List<EarningListModel.Datum> dataItems)
    {
        this.mContext=context;
        this.dataItems=dataItems;

    }

    @NonNull
    @Override
    public EarningListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.payment_list_design, parent, false);
        return new EarningListAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final EarningListAdapter.MyViewHolder holder, final int position)

    {
        holder.tv_descendant_name.setText(dataItems.get(position).getDecendantFirstName());
        String transfer_status = dataItems.get(position).getTransferStatus();
        if(transfer_status==null){
            holder.tv_service_status.setText("Unpaid");
            holder.tv_service_status.setBackgroundResource(R.drawable.bg_primary_gradient);

        }else {
            holder.tv_service_status.setText("Paid");
            holder.tv_service_status.setBackgroundResource(R.drawable.bg_red_30corn);

        }
        holder.tv_pickup_location.setText(dataItems.get(position).getPickupLocation());
        holder.tv_drop_location.setText(dataItems.get(position).getDropLocation());
        holder.tv_date.setText(dataItems.get(position).getDsppaymentDate());
        holder.tv_payment_amount.setText("$"+dataItems.get(position).getEarning());
        String service = dataItems.get(position).getServiceId();

        if(service.equals("1")){
            holder.tv_service_name.setText("Descendant Removal & Transport");
        }else if(service.equals("2")){
            holder.tv_service_name.setText("Limo Transport");
        }else if(service.equals("3")){
            holder.tv_service_name.setText("Casket Pickup & Delivery");
        }else if(service.equals("4")){
            holder.tv_service_name.setText("Flower Pickup & Delivery");
        }else if(service.equals("5")){
            holder.tv_service_name.setText("Ashes & Urn Pickup & Delivery");
        }else if(service.equals("6")){
            holder.tv_service_name.setText("Courier Transport");
        }else if(service.equals("7")){
            holder.tv_service_name.setText("Us Veteran & First Responder");
        }else if(service.equals("8")){
            holder.tv_service_name.setText("Airport Arrival & Departure");
        }else if(service.equals("9")){
            holder.tv_service_name.setText("Burial at the sea ");
        }





    }

    @Override
    public int getItemCount()
    {
        return dataItems.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
       TextView tv_descendant_name,tv_service_status,tv_pickup_location,tv_drop_location,
               tv_payment_status,tv_service_name,tv_date,tv_time,tv_payment_amount;

        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);

            tv_descendant_name = itemView.findViewById(R.id.tv_descendant_name);
            tv_service_status = itemView.findViewById(R.id.tv_service_status);
            tv_pickup_location = itemView.findViewById(R.id.tv_pickup_location);
            tv_drop_location = itemView.findViewById(R.id.tv_drop_location);
            tv_payment_status = itemView.findViewById(R.id.tv_payment_status);
            tv_service_name = itemView.findViewById(R.id.tv_service_name);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_payment_amount = itemView.findViewById(R.id.tv_payment_amount);








        }
    }
}



