package com.cts.removalspecialist.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cts.removalspecialist.R;
import com.cts.removalspecialist.models.rating.RatignLIstResponseModel;
;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class RatingListAdapter extends RecyclerView.Adapter<RatingListAdapter.MyViewHolder>
{
    private Context mContext;
    private List<RatignLIstResponseModel.Datum> dataItems;
    View view;


    public RatingListAdapter(Context context, List<RatignLIstResponseModel.Datum> dataItems)
    {
        this.mContext=context;
        this.dataItems=dataItems;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.rating_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position)

    {
        holder.tv_name.setText(dataItems.get(position).getDecendantFirstName()+" "+dataItems.get(position).getDecendantLastName());
        holder.tv_comment.setText(dataItems.get(position).getComments());
        String ratings = dataItems.get(position).getRatings();
        if (ratings==null){

        }else {
            holder.rating_star.setRating(Float.parseFloat(ratings));

        }
        String str_request_date = dataItems.get(position).getDateTime();
        try {
            DateFormat f = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date d = f.parse(str_request_date);
            DateFormat date = new SimpleDateFormat("d MMM yyyy");
            DateFormat time = new SimpleDateFormat("hh:mm aa");
            holder.tv_date.setText(date.format(d)+" "+time.format(d));
            System.out.println("Date: " + date.format(d));
            System.out.println("Time: " + time.format(d));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount()
    {
        return dataItems.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView tv_name,tv_service,tv_date,tv_comment;
        RatingBar rating_star;
        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);

            tv_name = itemView.findViewById(R.id.tv_name);
            tv_service = itemView.findViewById(R.id.tv_service);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_comment = itemView.findViewById(R.id.tv_comment);
            rating_star = itemView.findViewById(R.id.rating_star);








        }
    }
}




