package com.transdignity.driver.adapters;

import android.app.Activity;
import android.content.Context;
import androidx.databinding.DataBindingUtil;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.transdignity.driver.R;
import com.transdignity.driver.databinding.HomeListDesignBinding;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {

    Context context;
    Activity activity;
    private LayoutInflater layoutInflater;

    public HomeAdapter(Context context, Activity activity) {
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
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        holder.binding.llMainContainerHomeDesign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Fragment fragment = new DecendentDetailFragment();
                String tagFragment = fragment.getClass().getName();
                GlobalValues.getInstance().setFramgentTag(tagFragment);
                CommonUtils.loadFragment(context, fragment, tagFragment);*/
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
        return 10;
    }
}
