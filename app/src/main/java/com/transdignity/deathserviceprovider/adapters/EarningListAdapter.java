package com.transdignity.deathserviceprovider.adapters;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.transdignity.deathserviceprovider.MainActivity;
import com.transdignity.deathserviceprovider.R;
import com.transdignity.deathserviceprovider.fragments.HomeFragment;
import com.transdignity.deathserviceprovider.fragments.ServicesDetailsFragments.BurialSeaDetailFragment;
import com.transdignity.deathserviceprovider.models.auth.LoginResponse;
import com.transdignity.deathserviceprovider.models.payment.PaymentHistoryModel;
import com.transdignity.deathserviceprovider.models.payment.PaymentHistoryResponseModel;
import com.transdignity.deathserviceprovider.models.rating.RatingRequestModel;
import com.transdignity.deathserviceprovider.models.rating.RatingResponseModel;
import com.transdignity.deathserviceprovider.models.request.Services.BurealSeeRequestModel;
import com.transdignity.deathserviceprovider.models.request.Services.BurialSeeResponseModel;
import com.transdignity.deathserviceprovider.network.ApiClients;
import com.transdignity.deathserviceprovider.network.ApiInterface;
import com.transdignity.deathserviceprovider.utilities.CommonUtils;
import com.transdignity.deathserviceprovider.utilities.GlobalValues;
import com.transdignity.deathserviceprovider.utilities.LoadingProgressDialog;
import com.transdignity.deathserviceprovider.utilities.PreferenceManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EarningListAdapter extends RecyclerView.Adapter<EarningListAdapter.MyViewHolder>
{
    private Context mContext;
    private List<PaymentHistoryResponseModel.Datum> dataItems;
    View view;
    String userId;
    LoginResponse loginResponse;
    Dialog dialog_rating=null;
    public EarningListAdapter(Context context, List<PaymentHistoryResponseModel.Datum> dataItems)
    {
        this.mContext=context;
        this.dataItems=dataItems;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.payment_list_design, parent, false);
        loginResponse = (LoginResponse) GlobalValues.getPreferenceManager().getObject(PreferenceManager.LoginResponseKey, LoginResponse.class);

        if (loginResponse != null) {
            userId = loginResponse.getData().getId();
        }
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position)

    {
        holder.tv_descendant_name.setText(dataItems.get(position).getDecendantFirstName());
        holder.tv_service_status.setText(dataItems.get(position).getPaymentStatus());
        holder.tv_pickup_location.setText(dataItems.get(position).getPickupLocation());
        holder.tv_drop_location.setText(dataItems.get(position).getDropLocation());
        holder.tv_date.setText(dataItems.get(position).getDsppaymentDate());
        holder.tv_payment_amount.setText("$"+dataItems.get(position).getTotalCharge());
        String service = dataItems.get(position).getServiceId();
        //String service ="1";
        String ratings = dataItems.get(position).getRatings();
        if (dataItems.get(position).getPaymentStatus().equalsIgnoreCase("Pending")) {
            // holder.binding.tvStatus.setBackgroundTintList(ColorStateList.valueOf(R.color.colorPrimaryDark));
            holder.tv_service_status.setBackgroundResource(R.drawable.bg_primary_30corn);
            holder.rating_star.setVisibility(View.GONE);
            holder.tv_rate.setVisibility(View.GONE);

        } else if (dataItems.get(position).getPaymentStatus().equalsIgnoreCase("Rejected")) {
            holder.tv_service_status.setBackgroundResource(R.drawable.bg_red_30corn);
        }else if (dataItems.get(position).getPaymentStatus().equalsIgnoreCase("Complete")) {
            holder.tv_service_status.setBackgroundResource(R.drawable.bg_red_30corn);
            holder.rating_star.setVisibility(View.VISIBLE);
            holder.tv_rate.setVisibility(View.VISIBLE);
            if(ratings==null){
                holder.tv_rate.setVisibility(View.VISIBLE);
                holder.rating_star.setVisibility(View.GONE);
            }else {
                holder.tv_rate.setVisibility(View.GONE);
                holder.rating_star.setVisibility(View.VISIBLE);
                holder.rating_star.setRating(Float.parseFloat(ratings));
            }
        } else {
            holder.tv_service_status.setBackgroundResource(R.drawable.bg_green_30corn);
        }

        final String req_id = dataItems.get(position).getRequestId();
        if(service.equals("1")){
            holder.tv_service_name.setText("Descendent Removal & Transport");
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

        holder.tv_rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 dialog_rating = new Dialog(mContext);
                dialog_rating.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog_rating.setCancelable(true);
                dialog_rating.setCanceledOnTouchOutside(true);
                dialog_rating.setContentView(R.layout.rating_dialog_view);
                RatingBar ratingBar=dialog_rating.findViewById(R.id.ratingBar1);
                final TextView show_rating = dialog_rating.findViewById(R.id.show_rating);
                //final String[] s = new String[1];
                Float ratingvalue;
                ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                        Float ratingvalue = (Float) ratingBar.getRating();
                        String s = Float.toString(ratingvalue);
                        show_rating.setText(s);
                        Toast.makeText(mContext, " Ratings1 : " + ratingvalue + "", Toast.LENGTH_SHORT).show();
                    }
                });
                final EditText comment = dialog_rating.findViewById(R.id.ed_comment);
                TextView tv_submit=dialog_rating.findViewById(R.id.tv_submit);
                tv_submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                      giveRatingApi(req_id, show_rating.getText().toString(),comment.getText().toString());
                    }
                });
                dialog_rating.show();

            }
        });



    }

    @Override
    public int getItemCount()
    {
        return dataItems.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
       TextView tv_descendant_name,tv_service_status,tv_pickup_location,tv_drop_location,
               tv_payment_status,tv_service_name,tv_date,tv_time,tv_payment_amount,tv_rate;
       RatingBar rating_star;
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
            tv_rate = itemView.findViewById(R.id.tv_rate);
            rating_star = itemView.findViewById(R.id.rating_star);








        }
    }

    public void giveRatingApi(String request_id,String rating,String comment) {

        final LoadingProgressDialog dialog = new LoadingProgressDialog(mContext, "Please wait...");
        dialog.setCancelable(false);
        dialog.show();
        ApiInterface apiInterface = ApiClients.getClient(mContext).create(ApiInterface.class);
        final RatingRequestModel req = new RatingRequestModel();
        req.setUserId(userId);
        req.setRequestId(request_id);
        req.setRatings(rating);
        req.setComments(comment);



        Call<RatingResponseModel> call = apiInterface.giveRatingApi(loginResponse.getData().getToken(), req);

        call.enqueue(new Callback<RatingResponseModel>() {
            @Override
            public void onResponse(Call<RatingResponseModel> call, Response<RatingResponseModel> response) {
                dialog.dismiss();
                try {
                    if (response.code() >= 200 && response.code() <= 210) {
                        if (response.body().getSuccess().equalsIgnoreCase("true")) {
                            Toast.makeText(mContext, response.body().getMessage().toString(), Toast.LENGTH_SHORT).show();
                            dialog_rating.dismiss();
                            notifyDataSetChanged();
                            EarningListAdapter.this.notify();
                            Fragment fragment2;
                            fragment2 =  HomeFragment.newInstance("Home");
                            String fragmtStatusTag = fragment2.getClass().getName();
                            GlobalValues.getInstance().setFramgentTag(fragmtStatusTag);
                            ((MainActivity) mContext).selectedNavItem(R.id.navigation_home);
                            CommonUtils.loadFragment(mContext, fragment2, fragmtStatusTag);

                        } else {
                            Toast.makeText(mContext, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        BufferedReader reader = null;
                        StringBuilder sb = new StringBuilder();
                        try {
                            reader = new BufferedReader(new InputStreamReader(response.errorBody().byteStream()));
                            String line;
                            try {
                                while ((line = reader.readLine()) != null) {
                                    sb.append(line);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            String finallyError = sb.toString();
                            JSONObject jsonObjectError = new JSONObject(finallyError);
                            String message = jsonObjectError.optString("message");
                            Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
                            if (jsonObjectError.optString("token_valid").equalsIgnoreCase("false")) {
                                //CommonUtils.logoutSession(mContext);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<RatingResponseModel> call, Throwable t) {
                Toast.makeText(mContext, "Something Went Wrong!", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });

    }

}



