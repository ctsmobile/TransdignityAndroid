package com.cts.removalspecialist.activities;

import androidx.databinding.DataBindingUtil;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.cts.removalspecialist.R;
import com.cts.removalspecialist.databinding.ActivityProfileBinding;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile);
        binding.tvBack.setOnClickListener(this);
        binding.tvEdit.setOnClickListener(this);
        binding.llSubmit.setOnClickListener(this);

        //Demo demo = getIntent().getParcelableExtra("id");

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.ll_submit:
                break;
            case R.id.tv_edit:
                break;
        }
    }
}
